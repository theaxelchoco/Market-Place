package com.example.group17project.utils.model;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class Product {
  private String name;
  private String description;
  private Date dateAvailable;
  private ProductType type;
  private String ownerID;
  private String locationID;
  private double price;
  private ProductType preferredExchange;
  private Status status;

  private String productID;

  public Product(String name, String ownerID) {
    this.name = name;
    this.ownerID = ownerID;

    dateAvailable = new Date();
    status = Status.AVAILABLE;
  }

  public Product(String name, String ownerID, String description, Calendar date, String productType, String place, String prefExchange, int price) {
    this.name = name;
    this.ownerID = ownerID;
    this.description = description;
    dateAvailable = date.getTime();
    type = mapToProductType(productType);
    locationID = place;
    preferredExchange = mapToProductType(prefExchange);
    this.price = price;
  }

  public ProductType mapToProductType(String productType) {
    switch (productType) {
      case "Baby Toys":
        return ProductType.BABY_TOYS;
      case "Clothes":
        return ProductType.CLOTHES;
      case "Computer Accessories":
        return ProductType.COMPUTER_ACCESSORIES;
      case "Mobile Phones":
        return ProductType.MOBILE_PHONES;
      case "Furniture":
        return ProductType.FURNITURE;
      default:
        return null;
    }
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

  public ProductType getType() {
    return type;
  }

  public void setType(ProductType type) {
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

  public ProductType getPreferredExchange() {
    return preferredExchange;
  }

  public void setPreferredExchange(ProductType preferredExchange) {
    this.preferredExchange = preferredExchange;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getProductID() {
    return productID;
  }

  public void setProductID(String productID) {
    this.productID = productID;
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

  public enum Status {
    AVAILABLE,
    SOLD_OUT
  }
}
