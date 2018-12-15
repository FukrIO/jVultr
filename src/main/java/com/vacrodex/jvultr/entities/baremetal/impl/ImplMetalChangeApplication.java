package com.vacrodex.jvultr.entities.baremetal.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.entities.application.Application;
import com.vacrodex.jvultr.entities.baremetal.MetalChangeApplication;
import com.vacrodex.jvultr.jVultr;
import com.vacrodex.jvultr.utils.rest.RestEndpoints;
import com.vacrodex.jvultr.utils.rest.RestMethods;
import com.vacrodex.jvultr.utils.rest.RestRequest;
import com.vacrodex.jvultr.utils.rest.RestRequestResult;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Cameron Wolfe
 */
@Getter
@ToString(exclude = {"api"})
public class ImplMetalChangeApplication implements MetalChangeApplication {
  
  private jVultr api;
  private int subId;
  private int appId;
  private boolean successful;
  private String errorMessage = null;
  
  public ImplMetalChangeApplication(jVultr api, int subId, Application app) {
    this(api, subId, app.getId());
  }
  
  public ImplMetalChangeApplication(jVultr api, int subId, int appId) {
    this.api = api;
    this.subId = subId;
    this.appId = appId;
    
    new RestRequest<RestRequestResult>(getApi(), RestMethods.POST, RestEndpoints.BAREMETAL_APPLICATION_CHANGE)
        .addQueryParameter("SUBID", String.valueOf(getSubId()))
        .addQueryParameter("APPID", String.valueOf(getAppId()))
        .execute(result -> result).exceptionally(throwable -> {
          throwable.printStackTrace();
          //todo Implement exception handling
          return null;
        }).thenAccept(result -> {
          
          successful = result.getResponse().code() == 200;
          if (!successful) {
            //todo Implement exception handling and result checking
            JsonNode body = result.getJsonBody();
            System.out.println(body);
          }
        });
  }
  
}
