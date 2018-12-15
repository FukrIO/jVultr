package com.vacrodex.jvultr.entities.authorization.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.entities.authorization.Information;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Cameron Wolfe
 */
@Getter
@ToString
public class ImplInformation implements Information {
  
  private String[] acls;
  private String email;
  private String name;
  
  public ImplInformation(JsonNode node) {
    
    JsonNode jacls = node.get("acls");
    acls = new String[node.get("acls").size()];
    
    for (int i = 0; i < jacls.size(); i++) {
      acls[i] = jacls.get(i).asText();
    }
    
    email = node.get("email").asText();
    name = node.get("name").asText();
  }
  
}
