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
  
  public long getRamAsGB() {
    return getRamAsMB() / 1024;
  }
  
  public long getRamAsMB() {
    return getRamAsKB() / 1024;
  }
  
  public long getRamAsKB() {
    return getRamAsB() / 1024;
  }
  
  public long getRamAsB() {
    return memory;
  }
}