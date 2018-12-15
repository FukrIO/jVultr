package com.vacrodex.jvultr.entities.account;

import java.util.Date;

/**
 * @author Cameron Wolfe
 */
public interface AccountInformation {
  
  double getBalance();
  
  double getPendingCharges();
  
  Date getLastPaymentDate();
  
  double getLastPaymentAmount();
}
