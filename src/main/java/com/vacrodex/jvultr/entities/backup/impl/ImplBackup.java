package com.vacrodex.jvultr.entities.backup.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.entities.backup.Backup;
import com.vacrodex.jvultr.entities.backup.BackupStatus;
import com.vacrodex.jvultr.utils.DateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Cameron Wolfe
 */
@Getter
@ToString
public class ImplBackup implements Backup {
  
  private String id;
  private Date dateCreated;
  private String description;
  private long size;
  private BackupStatus status;
  
  public ImplBackup(JsonNode node) {
    
    id = node.get("BACKUPID").asText();
    try {
      dateCreated = DateUtil.VULTR_DATE.parse(node.get("date_created").asText());
    } catch (ParseException exception) {
      exception.printStackTrace();
    }
    
    description = node.get("description").asText();
    size = node.get("size").asLong();
    
    try {
      status = BackupStatus.valueOf(node.get("status").asText());
    } catch (EnumConstantNotPresentException exception) {
      status = BackupStatus.UNKNOWN;
    }
  }
  
}
