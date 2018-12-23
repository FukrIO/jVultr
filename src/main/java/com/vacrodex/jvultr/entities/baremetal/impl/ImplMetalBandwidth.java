package com.vacrodex.jvultr.entities.baremetal.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.entities.bandwidth.Bandwidth;
import com.vacrodex.jvultr.entities.baremetal.MetalBandwidth;
import lombok.ToString;

/**
 * @author Cameron Wolfe
 */
@ToString
public class ImplMetalBandwidth extends Bandwidth implements MetalBandwidth {
  
  public ImplMetalBandwidth(JsonNode node) {
    super(node);
  }
  
}
