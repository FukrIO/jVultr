package com.vacrodex.jvultr.entities.backup;

import java.util.Date;

/**
 * @author Cameron Wolfe
 */
public interface Backup {
  
  String getId();
  
  Date getDateCreated();
  
  String getDescription();
  
  long getSize();
  
  BackupStatus getStatus();
  
}
