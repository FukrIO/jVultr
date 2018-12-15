package com.vacrodex.jvultr.entities.regions;

/**
 * @author Cameron Wolfe
 */
public interface Region {
  
  int getId();
  
  Datacenter getDatacenter();
  
  String getName();
  
  String getCountry();
  
  String getContinent();
  
  String getState();
  
  boolean hasDdosProtection();
  
  boolean hasBlockStorage();
  
  String getRegionCode();
  
  
}
