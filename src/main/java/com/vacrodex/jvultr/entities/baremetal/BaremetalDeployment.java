package com.vacrodex.jvultr.entities.baremetal;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.entities.baremetal.impl.ImplBaremetal;
import com.vacrodex.jvultr.entities.os.OS;
import com.vacrodex.jvultr.entities.regions.Datacenter;
import com.vacrodex.jvultr.jVultr;
import com.vacrodex.jvultr.utils.rest.RestEndpoints;
import com.vacrodex.jvultr.utils.rest.RestMethods;
import com.vacrodex.jvultr.utils.rest.RestRequest;
import com.vacrodex.jvultr.utils.rest.RestRequestResult;
import lombok.Getter;

/**
 * @author Cameron Wolfe
 */
public class BaremetalDeployment {
  
  private jVultr jVultr;
  
  public BaremetalDeployment(jVultr jVultr) {
    this.jVultr = jVultr;
  }
  
  public Builder builder(Datacenter datacenter, MetalPlan plan, OS os) {
    return new Builder(datacenter, plan, os);
  }
  
  public Builder builder(int datacenter, int plan, int os) {
    return new Builder(datacenter, plan, os);
  }
  
  public class Builder {
    
    int osId; // Required
    int dcId; // Required
    int planId; // Required
    int appId = -1;
    int scriptId = -1;
    int snapshotId = -1;
    String tag;
    String label;
    String sshKeyId;
    String hostname;
    String userData;
    boolean ipv6 = true;
    boolean notify = false;
    
    public Builder(Datacenter datacenter, MetalPlan plan, OS os) {
      this(datacenter.getId(), plan.getId(), os.getId());
    }
    
    public Builder(int datacenter, int plan, int os) {
      this.dcId = datacenter;
      this.planId = plan;
      this.osId = os;
    }
    
    public Baremetal Create() {
      System.out.println(QueryParams());
      
      JsonNode body = new RestRequest<JsonNode>(jVultr, RestMethods.POST, RestEndpoints.BAREMETAL_CREATE)
          .setBody(QueryParams())
          .execute(RestRequestResult::getJsonBody)
          .exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
          }).join();
      
      if (body.has("SUBID")) {
        return new ImplBaremetal(jVultr, body.get("SUBID").asInt());
      }
      
      return null;
    }
    
    public String QueryParams() {
      
      StringBuilder builder = new StringBuilder();
      builder.append("DCID=").append(dcId);
      builder.append("&METALPLANID=").append(planId);
      builder.append("&OSID=").append(osId);
      
      if (appId != -1) {
        builder.append("&APPID=").append(appId);
      }
      if (scriptId != -1) {
        builder.append("&SCRIPTID=").append(scriptId);
      }
      if (snapshotId != -1) {
        builder.append("&SNAPSHOTID=").append(snapshotId);
      }
      if (tag != null) {
        builder.append("&tag=").append(tag);
      }
      if (label != null) {
        builder.append("&label=").append(label);
      }
      if (sshKeyId != null) {
        builder.append("&SSHKEYID=").append(sshKeyId);
      }
      if (hostname != null) {
        builder.append("&hostname=").append(hostname);
      }
      if (userData != null) {
        builder.append("&userdata=").append(userData); // TODO: base64
      }
      
      builder.append("&enable_ipv6=").append(ipv6);
      builder.append("&notify_activate=").append(notify);
      
      return builder.toString();
    }
    
    public Builder dc(Datacenter datacenter) {
      this.dcId = datacenter.getId();
      return this;
    }
    
    public Builder dc(int id) {
      this.dcId = id;
      return this;
    }
    
    public Builder plan(MetalPlan plan) {
      //      this.planId=plan.
      return this;
    }
    
    public Builder plan(int id) {
      this.planId = id;
      return this;
    }
    
    public Builder os(OS os) {
      //      this.osId = os.getId();
      return this;
    }
    
    public Builder os(int id) {
      this.osId = id;
      return this;
    }
    
    public Builder script(int id) {
      this.scriptId = id;
      return this;
    }
    
    public Builder app(int id) {
      this.appId = id;
      return this;
    }
    
    public Builder snapshot(int id) {
      this.snapshotId = id;
      return this;
    }
    
    public Builder tag(String tag) {
      this.tag = tag;
      return this;
    }
    
    public Builder label(String label) {
      this.label = label;
      return this;
    }
    
    public Builder ssh(String ssh) {
      this.sshKeyId = ssh;
      return this;
    }
    
    public Builder hostname(String hostname) {
      this.hostname = hostname;
      return this;
    }
    
    public Builder userData(String data) {
      this.userData = data;
      return this;
    }
    
    public Builder ipv6(boolean enabled) {
      this.ipv6 = enabled;
      return this;
    }
    
    public Builder notify(boolean notify) {
      this.notify = notify;
      return this;
    }
    
    
  }
  
}
