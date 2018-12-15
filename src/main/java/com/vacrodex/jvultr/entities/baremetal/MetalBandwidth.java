package com.vacrodex.jvultr.entities.baremetal;

import java.util.Date;

/**
 * @author Cameron Wolfe
 */
public interface MetalBandwidth {
  
  Date[] getDates();
  
  long[] getIncomingBytes();
  
  long[] getOutgoingBytes();
  
  long[] incomingKB();
  
  long[] incomingMB();
  
  long[] incomingGB();
  
  long[] outgoingKB();
  
  long[] outgoingMB();
  
  long[] outgoingGB();
}
