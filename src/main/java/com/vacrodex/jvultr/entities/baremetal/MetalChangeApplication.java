package com.vacrodex.jvultr.entities.baremetal;

import com.vacrodex.jvultr.jVultr;

/**
 * @author Cameron Wolfe
 */
public interface MetalChangeApplication {
  
  jVultr getApi();
  
  boolean isSuccessful();
  
  String getErrorMessage();
  
  int getAppId();
  
  int getSubId();
  
}
