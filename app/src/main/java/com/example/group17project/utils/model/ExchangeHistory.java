package com.example.group17project.utils.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class ExchangeHistory {

  private ArrayList<String> history;
  private String ownerID;

  public ExchangeHistory() {}

  public ExchangeHistory(String ownerID) {
    this.history = new ArrayList<String>();
    this.ownerID = ownerID;
  }

  public String getOwnerID() {
    return ownerID;
  }

  public void setOwnerID(String ownerID) {
    this.ownerID = ownerID;
  }

  public ArrayList<String> getHistory(){return this.history;}

  public void addHistory(String newExchange){
    this.history.add(newExchange);
  }

  @NonNull
  @Override
  public String toString() {
    return this.history.toString();
  }
}
