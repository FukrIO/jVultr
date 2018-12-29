package com.vacrodex.jvultr.exceptions;

/**
 * @author Cameron Wolfe
 */
public class InvalidHttpMethodException extends VultrException {
  
  public InvalidHttpMethodException() {
    super("Invalid HTTP method. Check that the method (POST|GET) matches what the documentation indicates.", 405);
  }

}
