package com.vacrodex.jvultr.exceptions;

import com.vacrodex.jvultr.utils.rest.RestRequest;

/**
 * @author Cameron Wolfe
 */
public class RateLimitedException extends VultrException {
  
  public <T> RateLimitedException(RestRequest<T> request) {
    super("Rate limit hit. API requests are limited to an average of 2/s. Try your request again later.", 503);
  }
  
}
