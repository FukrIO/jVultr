package com.vacrodex.jvultr.entities.regions.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.entities.regions.Datacenter;
import com.vacrodex.jvultr.entities.regions.Region;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Cameron Wolfe
 */
@Getter
@ToString
public class ImplRegion implements Region {
  
  private int id;
  private Datacenter datacenter;
  private String name;
  private String country;
  private String continent;
  private String state;
  private boolean ddosProtection;
  private boolean blockStorage;
  private String regionCode;
  
  public ImplRegion(int id, JsonNode node) {
    this.id = id;
    
    datacenter = new ImplDatacenter(node.get("DCID").asInt());
    name = node.get("name").asText();
    country = node.get("country").asText();
    continent = node.get("continent").asText();
    state = node.get("state").asText();
    ddosProtection = node.get("ddos_protection").asBoolean();
    blockStorage = node.get("block_storage").asBoolean();
    regionCode = node.get("regioncode").asText();
  }
  
  @Override
  public boolean hasDdosProtection() { return isDdosProtection(); }
  
  @Override
  public boolean hasBlockStorage() { return isBlockStorage(); }
  
}
