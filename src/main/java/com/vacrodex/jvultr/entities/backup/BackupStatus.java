package com.vacrodex.jvultr.entities.backup;

/**
 * There's no clear documentation for this, so at a later date we need to rest every possible combo (or open a support ticket asking for more
 * information about this)
 *
 * @author Cameron Wolfe
 */
public enum BackupStatus {
  COMPLETED("completed"),
  UNKNOWN("unknown");
  
  private final String status;
  
  BackupStatus(String status) {
    this.status = status;
  }
  
  public String getStatus() {
    return status;
  }}
