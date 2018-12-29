package com.vacrodex.jvultr.entities.application;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Cameron Wolfe
 */
public interface ApplicationList {
  
  Application getApplicationById(int id);
  
  Application getApplicationByName(String name);
  
  Application getApplicationByShortName(String name);
  
  Map<Integer, Application> getApplications();
  
  Collection<Application> getApplicationList();
  
  Set<Integer> getApplicationIds();
  
}
