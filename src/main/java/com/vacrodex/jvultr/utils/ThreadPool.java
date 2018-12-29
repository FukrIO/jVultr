package com.vacrodex.jvultr.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Cameron Wolfe
 */
public class ThreadPool {
  
  private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
  
  private static int CORE_POOL_SIZE = 2;
  private static int MAXIMUM_POOL_SIZE = Integer.MAX_VALUE;
  private static int KEEP_ALIVE_TIME = 60;
  
  private final ExecutorService executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, new SynchronousQueue<>());
  private final ScheduledExecutorService scheduler = Executors
      .newScheduledThreadPool(CORE_POOL_SIZE);
  private final ConcurrentHashMap<String, ExecutorService> executorServiceSingeThreads = new ConcurrentHashMap<>();
  
  public ExecutorService getExecutorService() {
    return executorService;
  }
  
  public ScheduledExecutorService getScheduler() {
    return scheduler;
  }
  
  public ExecutorService getSingleThreadExecutorService(String id) {
    synchronized (executorServiceSingeThreads) {
      ExecutorService service = executorServiceSingeThreads.get(id);
      if (service == null) {
        service = Executors.newSingleThreadExecutor();
        executorServiceSingeThreads.put(id, service);
      }
      return service;
    }
  }

}