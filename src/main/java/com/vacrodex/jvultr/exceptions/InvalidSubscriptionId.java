package com.vacrodex.jvultr.exceptions;

/**
 * @author Cameron Wolfe
 */
public class InvalidSubscriptionId extends VultrException {
  
  public InvalidSubscriptionId() {
    super("Invalid subscription ID.", 412);
  }
  
}
