package com.vacrodex.jvultr.exceptions;

/**
 * @author Cameron Wolfe
 */
public class InvalidEndpointException extends VultrException {
  
  public InvalidEndpointException() {
    super("Invalid API location. Check the URL that you are using.", 400);
  }

}
