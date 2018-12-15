package com.vacrodex.jvultr.entities.system;

import java.util.stream.Stream;

/**
 * @author Cameron Wolfe
 */
public enum Status {
  ACTIVE,
  PENDING,
  UNKNOWN;
  
  public static Status ofVultr(String vultr) {
    return Stream.of(Status.values()).filter(status -> status.name().equalsIgnoreCase(vultr)).findFirst().orElse(UNKNOWN);
  }
}