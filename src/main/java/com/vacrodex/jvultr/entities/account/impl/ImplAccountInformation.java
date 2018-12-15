package com.vacrodex.jvultr.entities.account.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.entities.account.AccountInformation;
import com.vacrodex.jvultr.utils.DateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Cameron Wolfe
 */
@Getter
@ToString
public class ImplAccountInformation implements AccountInformation {
  
  private double balance;
  private double pendingCharges;
  private double lastPaymentAmount;
  private Date lastPaymentDate;
  
  public ImplAccountInformation(JsonNode data) {
    
    balance = (data.has("balance") ? data.get("balance").asDouble() : 0.0);
    pendingCharges = (data.has("pending_charges") ? data.get("pending_charges").asDouble() : 0.0);
    lastPaymentAmount = (data.has("last_payment_amount") ? data.get("last_payment_amount").asDouble() : 0.0);
    
    if (data.has("last_payment_date")) {
      try {
        lastPaymentDate = DateUtil.VULTR_DATE.parse(data.get("last_payment_date").asText());
      } catch (ParseException exception) {
        exception.printStackTrace();
      }
    }
  }
  
}
