package com.vacrodex.jvultr.entities.baremetal;

import com.vacrodex.jvultr.entities.application.Application;
import com.vacrodex.jvultr.entities.bandwidth.Bandwidth;
import com.vacrodex.jvultr.entities.os.OS;
import com.vacrodex.jvultr.entities.regions.Datacenter;
import com.vacrodex.jvultr.entities.regions.Region;
import com.vacrodex.jvultr.entities.system.Memory;
import com.vacrodex.jvultr.entities.system.Status;
import com.vacrodex.jvultr.exceptions.TooSoonException;
import java.util.Date;

/**
 * @author Cameron Wolfe
 */
public interface Baremetal {
  
  int getSubId();
  
  String getLabel();
  
  boolean setLabel(String label);
  
  String getTag();
  
  OS getOS();
  
  String getIp();
  
  Datacenter getDatacenter();
  
  Region getRegion();
  
  Memory getMemory();
  
  String getDefaultPassword();
  
  Date getCreationDate();
  
  Status getStatus();
  
  String getNetmaskv4();
  
  String getGatewayv4();
  
  MetalPlan getPlan();
  
  Bandwidth getBandwidth();
  
  Application getApplication();
  
  boolean halt();
  
  boolean destory() throws TooSoonException;
  
  boolean stop();
  
  boolean restart();
  
  boolean start();
  
  boolean reinstall();
  
  boolean setUserData();
  
  boolean enableIPV6();
  
  boolean refreshInformation();
}
