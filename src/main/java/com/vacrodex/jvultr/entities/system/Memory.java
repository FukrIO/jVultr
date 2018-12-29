package com.vacrodex.jvultr.entities.system;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Cameron Wolfe
 */
@Getter
@ToString
public class Memory {
  
  private long memory;
  
  public Memory(long memory) {
    this.memory = memory;
  }
  
  public long getAsGB() {
    return getAsMB() / 1024;
  }
  
  public long getAsMB() {
    return getAsKB() / 1024;
  }
  
  public long getAsKB() {
    return getAsB() / 1024;
  }
  
  public long getAsB() {
    return memory;
  }
  
}