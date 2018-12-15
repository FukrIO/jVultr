package com.vacrodex.jvultr.exceptions;

/**
 * @author Cameron Wolfe
 */
public class InvalidKeyException extends VultrException {
  
  public InvalidKeyException() {
    super("Invalid or missing API key. Check that your API key is present and matches your assigned key.", 403);
  }
  
}
