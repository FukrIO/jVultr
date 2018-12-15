package com.vacrodex.jvultr.entities.regions;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Cameron Wolfe
 */
public interface RegionList {
  
  Region getRegionById(int id);
  
  Map<Integer, Region> getRegions();
  
  Collection<Region> getRegionList();
  
  Set<Integer> getRegionIds();
  
  Region getRegionByName(String name);
}
