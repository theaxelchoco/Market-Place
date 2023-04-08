package com.example.group17project.utils.model;

import androidx.annotation.NonNull;


/**
 * This class represents a particular exchange that will be showcased in the user history
 */
public class ExchangeHistory {

  private String details;
  private String ownerId;
  private String buyerId;
  public String id;

  public ExchangeHistory() {}

  public ExchangeHistory(String ownerId) {
    this.ownerId = ownerId;
  }

  public ExchangeHistory(String ownerId, String buyerId) {
    this.ownerId = ownerId;
    this.buyerId = buyerId;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getBuyerId() {
    return buyerId;
  }

  public void setBuyerId(String buyerId) {
    this.buyerId = buyerId;
  }

  public String getId(){return this.id;}
  public void setId(String id) {
    this.id = id;
  }

  public String getDetails(){return this.details;}
  public void setDetails(String details){this.details = details;}


  @NonNull
  @Override
  public String toString() {
    return this.getDetails();
  }
}
