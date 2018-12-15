package com.vacrodex.jvultr.entities.application;

/**
 * @author Cameron Wolfe
 */
public interface Application {
  
  int getId();
  
  String getName();
  
  String getShortName();
  
  String getDeployName();
  
  double getSurcharge();
}
