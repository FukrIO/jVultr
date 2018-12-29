package com.vacrodex.jvultr.utils.ratelimits;

import com.vacrodex.jvultr.exceptions.RateLimitedException;
import com.vacrodex.jvultr.jVultr;
import com.vacrodex.jvultr.utils.rest.RestRequest;
import com.vacrodex.jvultr.utils.rest.RestRequestResult;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Cameron Wolfe
 */
@Slf4j
public class RatelimitManager {
  
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  
  private final Set<RatelimitBucket> buckets = ConcurrentHashMap.newKeySet();
  private final HashMap<RatelimitBucket, ConcurrentLinkedQueue<RestRequest<?>>> queues = new HashMap<>();
  
  private final jVultr api;
  
  public RatelimitManager(jVultr api) {
    this.api = api;
  }
  
  public void queueRequest(RestRequest<?> request) {
    // Get the bucket for the current request type.
    RatelimitBucket bucket = buckets
        .parallelStream()
        .filter(b -> b.getEndpoint().orElse(null) == request.getEndpoint())
        .findAny()
        .orElse(new RatelimitBucket(request.getEndpoint()));
    
    // Add bucket to list with buckets
    buckets.add(bucket);
    
    // Get the queue for the current bucket or create a new one if there's no one already
    ConcurrentLinkedQueue<RestRequest<?>> queue = queues
        .computeIfAbsent(bucket, k -> new ConcurrentLinkedQueue<>());
    
    // Add the request to the queue and check if there's already a scheduler working on the queue
    boolean startScheduler = false;
    synchronized (bucket) {
      synchronized (queue) {
        if (bucket.hasActiveScheduler()) {
          queue.add(request);
        } else {
          bucket.setHasActiveScheduler(true);
          queue.add(request);
          startScheduler = true;
        }
      }
    }
    
    if (!startScheduler) {
      return;
    }
    int delay = bucket.getTimeTillSpaceGetsAvailable();
    if (delay > 0) {
      synchronized (bucket) {
        synchronized (queue) {
          if (request.incrementRetryCounter()) {
            queue.remove(request);
            bucket.setHasActiveScheduler(false);
            return;
          }
        }
      }
      log.debug("Delaying requests to {} for {}ms to prevent hitting ratelimits", bucket, delay);
    }
    // Start a scheduler to work off the queue
    scheduler.schedule(() -> api.getThreadPool().getExecutorService().submit(() -> {
      try {
        while (!queue.isEmpty()) {
          if (!bucket.hasSpace()) {
            synchronized (queue) {
              // Remove if we retried to often
              queue.removeIf(req -> {
                if (req.incrementRetryCounter()) {
                  req.getResult().completeExceptionally(new RateLimitedException(req));
                  return true;
                }
                return false;
              });
              if (queue.isEmpty()) {
                break;
              }
            }
            try {
              int sleepTime = bucket.getTimeTillSpaceGetsAvailable();
              if (!api.isDisableRatelimiter()) {
                if (sleepTime > 0) {
                  log.debug("Delaying requests to {} for {}ms to prevent hitting ratelimits", bucket, sleepTime);
                  Thread.sleep(sleepTime);
                }
              }
            } catch (InterruptedException e) {
              log.warn("We got interrupted while waiting for a rate limit!", e);
            }
          }
          RestRequest<?> restRequest = queue.peek();
          boolean remove = true;
          RestRequestResult rateLimitHeadersSource = null;
          CompletableFuture<RestRequestResult> restRequestResult = restRequest.getResult();
          try {
            RestRequestResult result = restRequest.executeBlocking();
            rateLimitHeadersSource = result;
            restRequestResult.complete(result);
          } catch (Exception e) {
            restRequestResult.completeExceptionally(e);
          } finally {
            try {
              if (rateLimitHeadersSource != null) {
                bucket.setRateLimitRemaining(0);
              }
            } catch (Exception e) {
              if (restRequestResult.isDone()) {
                throw e;
              }
              restRequestResult.completeExceptionally(e);
            }
          }
          if (remove) {
            queue.remove(restRequest);
          }
        }
      } catch (Throwable t) {
        log.error("Exception in RatelimitManager! Please contact the developer!", t);
      } finally {
        synchronized (bucket) {
          bucket.setHasActiveScheduler(false);
        }
      }
    }), delay, TimeUnit.MILLISECONDS);
  }
  
}