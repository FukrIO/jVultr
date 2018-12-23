package com.vacrodex.jvultr.entities.system;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Cameron Wolfe
 */
@Getter
@ToString
public class Disk {
  
  private long disk;
  
  public Disk(long disk) {
    this.disk = disk;
  }
  
  public long getAsGB() {
    return getAsMB() / 1000;
  }
  
  public long getAsMB() {
    return getAsKB() / 1000;
  }
  
  public long getAsKB() {
    return getAsB() / 1000;
  }
  
  public long getAsB() {
    return disk;
  }
  
}