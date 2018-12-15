package com.vacrodex.jvultr.entities.backup;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Cameron Wolfe
 */
public interface BackupList {
  
  Backup getBackupById(String id);
  
  Backup getBackupByDescription(String name);
  
  Map<String, Backup> getBackups();
  
  Collection<Backup> getBackupList();
  
  Set<String> getBackupIds();
}
