package com.vacrodex.jvultr.entities.regions.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.entities.regions.Region;
import com.vacrodex.jvultr.entities.regions.RegionList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Cameron Wolfe
 */
@Getter
@ToString
public class ImplRegionList implements RegionList {
  
  private HashMap<Integer, Region> regions;
  
  public ImplRegionList(JsonNode node) {
    
    regions = new HashMap<>();
    
    node.fields().forEachRemaining(entry -> {
      int id = Integer.valueOf(entry.getKey());
      Region r = new ImplRegion(id, entry.getValue());
      regions.put(id, r);
    });
  }
  
  @Override
  public Region getRegionById(int id) {
    return getRegionList().stream().filter(region -> region.getId() == id).findFirst().orElse(null);
  }
  
  @Override
  public Collection<Region> getRegionList() {
    return getRegions().values();
  }
  
  @Override
  public Set<Integer> getRegionIds() {
    return getRegions().keySet();
  }
  
  @Override
  public Region getRegionByName(String name) {
    return getRegionList().stream().filter(region -> region.getName().equals(name)).findFirst().orElse(null);
  }
}
