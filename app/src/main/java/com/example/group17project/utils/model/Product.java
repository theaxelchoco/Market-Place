package com.example.group17project.utils.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class Product {
  private String name;
  private String description;
  private Date dateAvailable;
  private Type type;
  private String ownerID;
  private String locationID;
  private double price;
  private Type preferredExchange;
  private Status status;

  private String productID;

  public Product(String name, String ownerID) {
    this.name = name;
    this.ownerID = ownerID;

    dateAvailable = new Date();
    status = Status.AVAILABLE;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDateAvailable() {
    return dateAvailable;
  }

  public void setDateAvailable(Date dateAvailable) {
    this.dateAvailable = dateAvailable;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getOwnerID() {
    return ownerID;
  }

  public void setOwnerID(String ownerID) {
    this.ownerID = ownerID;
  }

  public String getLocationID() {
    return locationID;
  }

  public void setLocationID(String locationID) {
    this.locationID = locationID;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public Type getPreferredExchange() {
    return preferredExchange;
  }

  public void setPreferredExchange(Type preferredExchange) {
    this.preferredExchange = preferredExchange;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public void setProductID(String productID){
    this.productID = productID;
  }

  public String getProductID(){
    return productID;
  }

  @NonNull
  @Override
  public String toString() {
    return "Product{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", dateAvailable=" + dateAvailable +
        ", type=" + type +
        ", ownerID='" + ownerID + '\'' +
        ", locationID='" + locationID + '\'' +
        ", price=" + price +
        ", preferredExchange=" + preferredExchange +
        ", status=" + status +
        '}';
  }

  public enum Type {
    BABY_TOYS,
    CLOTHES,
    COMPUTER_ACCESSORIES,
    MOBILE_PHONES,
    FURNITURE
  }

  public enum Status {
    AVAILABLE,
    SOLD_OUT
  }
}
