package com.vacrodex.jvultr.exceptions;

import lombok.Getter;

/**
 * @author Cameron Wolfe
 */
@Getter
public class MoreDetailedException extends VultrException {
  
  private String message;
  
  public MoreDetailedException(String message) {
    super(message, 412);
    this.message = message;
  }
}
