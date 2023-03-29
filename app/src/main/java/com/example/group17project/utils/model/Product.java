package com.example.group17project.utils.model;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Class represents a product that will be provided or received. Consists of various fields including name, type, and description
 */
public class Product {

  private static final String BABYTOYS = "Baby Toys";
  public static final String CLOTHES = "Clothes";
  public static final String COMPUTERACCESSORIES = "Computer Accessories";
  public static final String MOBILEPHONES = "Mobile Phones";
  public static final String FURNITURE = "Furniture";

  private String name;
  private String description;
  private Date dateAvailable;
  private ProductType type;
  private String ownerID;
  private String locationID;
  private int price;
  private ProductType preferredExchange;
  private Status status;
  private int imageId;
  private String productID;

  private String buyer;
  private String buyerItem;
  private String buyerItemAmount;
  private Date transactionDate;

  public Product() {
  }

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
    status = Status.AVAILABLE;
  }

  /**
   * This method is used to map a string with the corresponding product type. Used as a helper method for other methods
   * @param productType string representation of the product type
   * @return enumeration type of ProductType corresponding to the string passed
   */
  public ProductType mapToProductType(String productType) {
    switch (productType) {
      case BABYTOYS:
        return ProductType.BABY_TOYS;
      case CLOTHES:
        return ProductType.CLOTHES;
      case COMPUTERACCESSORIES:
        return ProductType.COMPUTER_ACCESSORIES;
      case MOBILEPHONES:
        return ProductType.MOBILE_PHONES;
      case FURNITURE:
        return ProductType.FURNITURE;
      default:
        return null;
    }
  }

  public void completeTransaction(String product, String val, String buyerID, Date date){
    buyer = buyerID;
    buyerItem = product;
    buyerItemAmount = val;
    transactionDate = date;
  }

  public String getBuyer() {
    return buyer;
  }

  public void setBuyer(String buyer) {
    this.buyer = buyer;
  }

  public String getBuyerItem() {
    return buyerItem;
  }

  public void setBuyerItem(String buyerItem) {
    this.buyerItem = buyerItem;
  }

  public String getBuyerItemAmount() {
    return buyerItemAmount;
  }

  public void setBuyerItemAmount(String buyerItemAmount) {
    this.buyerItemAmount = buyerItemAmount;
  }

  public Date getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

  public int getImageId() {
    return imageId;
  }

  public void setImageId(int imageId) {
    this.imageId = imageId;
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

  public String getType() {
    switch (type) {
      case BABY_TOYS:
        return BABYTOYS;
      case CLOTHES:
        return CLOTHES;
      case FURNITURE:
        return FURNITURE;
      case MOBILE_PHONES:
        return MOBILEPHONES;
      case COMPUTER_ACCESSORIES:
        return COMPUTERACCESSORIES;
      default:
        return "";
    }
  }

  public void setType(String type) {
    this.type = mapToProductType(type);
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
    switch (preferredExchange) {
      case BABY_TOYS:
        return BABYTOYS;
      case CLOTHES:
        return CLOTHES;
      case FURNITURE:
        return FURNITURE;
      case MOBILE_PHONES:
        return MOBILEPHONES;
      case COMPUTER_ACCESSORIES:
        return COMPUTERACCESSORIES;
      default:
        return "";
    }
  }

  public void setPreferredExchange(String preferredExchange) {
    this.preferredExchange = mapToProductType(preferredExchange);
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
