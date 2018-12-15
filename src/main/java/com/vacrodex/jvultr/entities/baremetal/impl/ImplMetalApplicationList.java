package com.vacrodex.jvultr.entities.baremetal.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.entities.application.Application;
import com.vacrodex.jvultr.entities.application.impl.ImplApplication;
import com.vacrodex.jvultr.entities.baremetal.MetalApplicationList;
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
public class ImplMetalApplicationList implements MetalApplicationList {
  
  private HashMap<Integer, Application> applications;
  
  public ImplMetalApplicationList(JsonNode node) {
    applications = new HashMap<>();
    node.fields().forEachRemaining(entry -> applications.put(Integer.valueOf(entry.getKey()), new ImplApplication(entry.getValue())));
  }
  
  @Override
  public Application getApplicationById(int id) {
    return applications.get(id);
  }
  
  @Override
  public Application getApplicationByName(String name) {
    return getApplicationList().stream().filter(app -> app.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
  }
  
  @Override
  public Application getApplicationByShortName(String name) {
    return getApplicationList().stream().filter(app -> app.getShortName().equalsIgnoreCase(name)).findFirst().orElse(null);
  }
  
  @Override
  public Collection<Application> getApplicationList() {
    return applications.values();
  }
  
  @Override
  public Set<Integer> getApplicationIds() {
    return applications.keySet();
  }
}
