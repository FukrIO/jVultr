package com.vacrodex.jvultr.entities.application.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.entities.application.Application;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Cameron Wolfe
 */
@Getter
@ToString
public class ImplApplication implements Application {
  
  private int id;
  private String name;
  private String shortName;
  private String deployName;
  private double surcharge;
  
  public ImplApplication(JsonNode node) {
    id = node.get("APPID").asInt();
    name = node.get("name").asText();
    shortName = node.get("short_name").asText();
    deployName = node.get("deploy_name").asText();
    surcharge = node.get("surcharge").asDouble();
  }
}
