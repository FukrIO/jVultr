package com.vacrodex.jvultr.entities.bandwidth;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;

/**
 * TODO: Do something with this??
 *
 * @author Cameron Wolfe
 */
@Getter
@ToString
public class Bandwidth {
  
  private long[] incomingBytes;
  private long[] outgoingBytes;
  private Date[] dates;
  
  public Bandwidth(JsonNode node) {
    node.get("incoming_bytes").fields().forEachRemaining(System.out::println);
  }
  
  public long[] incomingKB() {
    return incomingDivide(1000);
  }
  
  public long[] incomingMB() {
    return incomingDivide(1000 * 1000);
  }
  
  public long[] incomingGB() {
    return incomingDivide(1000 * 1000 * 1000);
  }
  
  public long[] outgoingKB() {
    return outgoingDivide(1000);
  }
  
  public long[] outgoingMB() {
    return outgoingDivide(1000 * 1000);
  }
  
  public long[] outgoingGB() {
    return outgoingDivide(1000 * 1000 * 1000);
  }
  
  private long[] divideArray(int numerator, long[] array) {
    long[] result = array.clone();
    for (int i = 0; i < array.length; i++) {
      result[i] = array[i] / numerator;
    }
    return result;
  }
  
  private long[] incomingDivide(int numerator) {
    return divideArray(numerator, incomingBytes);
  }
  
  private long[] outgoingDivide(int numerator) {
    return divideArray(numerator, outgoingBytes);
  }
  
}
