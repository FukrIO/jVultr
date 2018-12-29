package com.vacrodex.jvultr.utils.ratelimits;

import com.vacrodex.jvultr.utils.rest.RestEndpoints;
import java.util.Optional;

/**
 * @author Cameron Wolfe
 */
public class RatelimitBucket {
  
  private final RestEndpoints endpoint;
  private int rateLimitRemaining = 1;
  private boolean hasActiveScheduler = false;
  
  public RatelimitBucket(RestEndpoints endpoint) {
    this.endpoint = endpoint;
  }
  
  /**
   * Gets the rest endpoint of the bucket.
   *
   * @return The endpoint of the bucket. If it's a global limit, the endpoint will be not be
   *     present.
   */
  public Optional<RestEndpoints> getEndpoint() {
    return Optional.ofNullable(endpoint);
  }
  
  /**
   * Checks if this bucket has an active scheduler.
   *
   * @return Whether this bucket has an active scheduler or not.
   */
  public synchronized boolean hasActiveScheduler() {
    return hasActiveScheduler;
  }
  
  /**
   * Sets if this bucket has an active scheduler.
   *
   * @param hasActiveScheduler Whether this bucket has an active scheduler or not.
   */
  public synchronized void setHasActiveScheduler(boolean hasActiveScheduler) {
    this.hasActiveScheduler = hasActiveScheduler;
  }
  
  /**
   * Checks if there is still "space" in this bucket, which means that you can still send requests
   * without being ratelimited.
   *
   * @return Whether you can send requests without being ratelimited or not.
   */
  public boolean hasSpace() {
    return rateLimitRemaining > 0 || getTimeTillSpaceGetsAvailable() <= 0;
  }
  
  /**
   * Sets the remaining requests till ratelimit.
   *
   * @param rateLimitRemaining The remaining requests till ratelimit.
   */
  public void setRateLimitRemaining(int rateLimitRemaining) {
    this.rateLimitRemaining = rateLimitRemaining;
  }
  
  /**
   * Gets the time in seconds how long you have to wait till there's space in the bucket again.
   *
   * @return The time in seconds how long you have to wait till there's space in the bucket again.
   */
  public int getTimeTillSpaceGetsAvailable() {
    if (rateLimitRemaining > 0) {
      return 0;
    }
    return 500;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof RatelimitBucket)) {
      return false;
    }
    
    RatelimitBucket otherBucket = (RatelimitBucket) obj;
    return otherBucket.endpoint == endpoint;
  }

}