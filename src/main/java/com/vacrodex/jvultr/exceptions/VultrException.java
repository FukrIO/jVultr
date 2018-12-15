package com.vacrodex.jvultr.exceptions;

import lombok.Getter;

/**
 * @author Cameron Wolfe
 */
@Getter
public class VultrException extends Exception {
  
  private final int responseCode;
  
  public VultrException(String cause, int response) {
    super(cause + " | Response Code " + response);
    this.responseCode = response;
  }
  
}
