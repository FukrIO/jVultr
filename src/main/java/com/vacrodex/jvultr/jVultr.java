package com.vacrodex.jvultr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vacrodex.jvultr.entities.account.AccountInformation;
import com.vacrodex.jvultr.entities.account.impl.ImplAccountInformation;
import com.vacrodex.jvultr.entities.application.Application;
import com.vacrodex.jvultr.entities.application.ApplicationList;
import com.vacrodex.jvultr.entities.application.impl.ImplApplicationList;
import com.vacrodex.jvultr.entities.authorization.Information;
import com.vacrodex.jvultr.entities.authorization.impl.ImplInformation;
import com.vacrodex.jvultr.entities.backup.Backup;
import com.vacrodex.jvultr.entities.backup.BackupList;
import com.vacrodex.jvultr.entities.backup.impl.ImplBackupList;
import com.vacrodex.jvultr.entities.baremetal.MetalApplicationList;
import com.vacrodex.jvultr.entities.baremetal.MetalBandwidth;
import com.vacrodex.jvultr.entities.baremetal.impl.ImplMetalApplicationList;
import com.vacrodex.jvultr.entities.baremetal.impl.ImplMetalBandwidth;
import com.vacrodex.jvultr.entities.regions.Region;
import com.vacrodex.jvultr.entities.regions.RegionList;
import com.vacrodex.jvultr.entities.regions.impl.ImplRegionList;
import com.vacrodex.jvultr.utils.ThreadPool;
import com.vacrodex.jvultr.utils.ratelimits.RatelimitManager;
import com.vacrodex.jvultr.utils.rest.RestEndpoints;
import com.vacrodex.jvultr.utils.rest.RestMethods;
import com.vacrodex.jvultr.utils.rest.RestRequest;
import com.vacrodex.jvultr.utils.rest.RestRequestResult;
import java.util.Collection;
import java.util.Set;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

/**
 * @author Cameron Wolfe
 */
@Slf4j
@Getter
public class jVultr {
  
  private final OkHttpClient httpClient;
  
  private String key;
  private ObjectMapper objectMapper = new ObjectMapper();
  private ThreadPool threadPool;
  private RatelimitManager ratelimitManager;
  
  public jVultr(String key) {
    
    this.key = key;
    
    this.threadPool = new ThreadPool();
    
    this.httpClient = new OkHttpClient.Builder()
        .addInterceptor(chain -> chain.proceed(chain
            .request()
            .newBuilder()
            .addHeader("User-Agent", "jVultr v1.0.0 (https://github.com/Vacrodex/jVultr)")
            .addHeader("API-Key", getKey())
            .build()))
        .build();
    
    this.ratelimitManager = new RatelimitManager(this);
  }
  
  public AccountInformation getAccountInformation() {
    
    JsonNode body = new RestRequest<JsonNode>(this, RestMethods.GET, RestEndpoints.ACCOUNT_INFORMATION)
        .execute(RestRequestResult::getJsonBody)
        .exceptionally(throwable -> {
          throwable.printStackTrace();
          return null;
        })
        .join();
    
    return new ImplAccountInformation(body);
  }
  
  public ApplicationList getApplicationList() {
    
    JsonNode body = new RestRequest<JsonNode>(this, RestMethods.GET, RestEndpoints.APPLICATION_LIST)
        .execute(RestRequestResult::getJsonBody)
        .exceptionally(throwable -> {
          throwable.printStackTrace();
          return null;
        })
        .join();
    
    return new ImplApplicationList(body);
  }
  
  public Application getApplicationById(int id) {
    return getApplicationList().getApplicationById(id);
  }
  
  public Application getApplicationByName(String name) {
    return getApplicationList().getApplicationByName(name);
  }
  
  public Application getApplicationByShortName(String name) {
    return getApplicationList().getApplicationByShortName(name);
  }
  
  public Information getInformation() {
    JsonNode body = new RestRequest<JsonNode>(this, RestMethods.GET, RestEndpoints.AUTHORIZATION_INFORMATION)
        .execute(RestRequestResult::getJsonBody)
        .exceptionally(throwable -> {
          throwable.printStackTrace();
          return null;
        })
        .join();
    
    return new ImplInformation(body);
  }
  
  //todo Support filtering by service on a RESTful level
  public BackupList getBackupList() {
    JsonNode body = new RestRequest<JsonNode>(this, RestMethods.GET, RestEndpoints.BACKUP_LIST)
        .execute(RestRequestResult::getJsonBody)
        .exceptionally(throwable -> {
          throwable.printStackTrace();
          return null;
        })
        .join();
    
    return new ImplBackupList(body);
  }
  
  //todo Support filter by ID on a RESTful level
  public Backup getBackupById(String id) {
    return getBackupList().getBackupById(id);
  }
  
  public Backup getBackupByDescription(String description) {
    return getBackupList().getBackupByDescription(description);
  }
  
  public MetalApplicationList getMetalApplicationList(int subid) {
    
    JsonNode body = new RestRequest<JsonNode>(this, RestMethods.GET, RestEndpoints.BAREMETAL_APPLICATION_CHANGE_LIST)
        .addQueryParameter("SUBID", String.valueOf(subid))
        .execute(RestRequestResult::getJsonBody)
        .exceptionally(throwable -> {
          throwable.printStackTrace();
          return null;
        })
        .join();
    
    return new ImplMetalApplicationList(body);
  }
  
  public Application getMetalApplicationById(int subid, int id) {
    return getMetalApplicationList(subid).getApplicationById(id);
  }
  
  public Application getMetalApplicationByName(int subid, String name) {
    return getMetalApplicationList(subid).getApplicationByName(name);
  }
  
  public Application getMetalApplicationByShortName(int subid, String name) {
    return getMetalApplicationList(subid).getApplicationByShortName(name);
  }
  
  public MetalBandwidth getMetalBandwidth(int subid) {
    JsonNode body = new RestRequest<JsonNode>(this, RestMethods.GET, RestEndpoints.BAREMETAL_BANDWIDTH)
        .setUrlParameters(String.valueOf(subid))
        .execute(RestRequestResult::getJsonBody)
        .exceptionally(throwable -> {
          throwable.printStackTrace();
          return null;
        })
        .join();
    
    return new ImplMetalBandwidth(body);
  }
  
  /**
   * TODO: Implement caching
   * @return
   */
  public RegionList getRegionList() {
    JsonNode body = new RestRequest<JsonNode>(this, RestMethods.GET, RestEndpoints.REGIONS_LIST)
        .execute(RestRequestResult::getJsonBody)
        .exceptionally(throwable -> {
          throwable.printStackTrace();
          return null;
        })
        .join();
    
    return new ImplRegionList(body);
  }
  
  public Region getRegionById(int id) {
    return getRegionList().getRegionById(id);
  }
  
  public Region getRegionByName(String name) {
    return getRegionList().getRegionByName(name);
  }
  
  public Collection<Region> getRegions() {
    return getRegionList().getRegions().values();
  }
  
  public Set<Integer> getRegionIds() {
    return getRegionList().getRegions().keySet();
  }
  
}
