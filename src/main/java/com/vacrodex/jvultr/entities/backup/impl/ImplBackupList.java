package com.vacrodex.jvultr.entities.backup.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.entities.backup.Backup;
import com.vacrodex.jvultr.entities.backup.BackupList;
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
public class ImplBackupList implements BackupList {
  
  private HashMap<String, Backup> backups;
  
  public ImplBackupList(JsonNode node) {
    backups = new HashMap<>();
    node.fields()
        .forEachRemaining(entry -> backups.put(entry.getKey(), new ImplBackup(entry.getValue())));
  }
  
  @Override
  public Backup getBackupById(String id) {
    return backups.get(id);
  }
  
  @Override
  public Backup getBackupByDescription(String name) {
    return getBackupList().stream().filter(backup -> backup.getDescription().equalsIgnoreCase(name))
                          .findFirst().orElse(null);
  }
  
  @Override
  public Collection<Backup> getBackupList() {
    return backups.values();
  }
  
  @Override
  public Set<String> getBackupIds() {
    return backups.keySet();
  }
  
}
