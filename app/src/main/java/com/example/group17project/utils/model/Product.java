package com.example.group17project.utils.model;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class Product {
  private String name;
  private String description;
  private Date dateAvailable;
  private Type type;
  private String ownerID;
  private String locationID;
  private int price;
  private Type preferredExchange;
  private Status status;

  private int imageId;

  private String productID;

  public Product(){

  }

  public Product(String name, String ownerID) {
    this.name = name;
    this.ownerID = ownerID;

    dateAvailable = new Date();
    status = Status.AVAILABLE;
  }

  public Product(String name, String ownerID, String description, Calendar date, String productType, String place, String prefExchange, int price){
    this.name = name;
    this.ownerID = ownerID;
    this.description = description;
    dateAvailable = date.getTime();
    type = mapToProductType(productType);
    locationID = place;
    preferredExchange = mapToProductType(prefExchange);
    this.price = price;
  }

  public Type mapToProductType(String productType){
    switch(productType){
      case "Baby Toys":
        return Type.BABY_TOYS;
      case "Clothes":
        return Type.CLOTHES;
      case "Computer Accessories":
        return Type.COMPUTER_ACCESSORIES;
      case "Mobile Phones":
        return Type.MOBILE_PHONES;
      case "Furniture":
        return Type.FURNITURE;
      default:
        return null;
    }
  }

  public int getImageId(){return imageId;}

  public void setImageId(int imageId){this.imageId = imageId;}
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

  public String getType() {
    switch(type){
      case BABY_TOYS:
        return "Baby Toys";
      case CLOTHES:
        return "Clothes";
      case FURNITURE:
        return "Furniture";
      case MOBILE_PHONES:
        return "Mobile Phones";
      case COMPUTER_ACCESSORIES:
        return "Computer Accessories";
      default:
        return "";
    }
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

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getPreferredExchange() {
    switch(preferredExchange){
      case BABY_TOYS:
        return "Baby Toys";
      case CLOTHES:
        return "Clothes";
      case FURNITURE:
        return "Furniture";
      case MOBILE_PHONES:
        return "Mobile Phones";
      case COMPUTER_ACCESSORIES:
        return "Computer Accessories";
      default:
        return "";
    }
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
