package com.vacrodex.jvultr.exceptions;

/**
 * @author Cameron Wolfe
 */
public class TooSoonException extends VultrException {
  
  public TooSoonException() {
    super("You cannot delete an instance when it has been created within 5 minutes", 412);
  }
}
