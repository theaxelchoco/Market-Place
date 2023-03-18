package com.example.group17project.utils.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class Product implements Parcelable {
  public static final Creator<Product> CREATOR = new Creator<Product>() {
    @Override
    public Product createFromParcel(Parcel in) {
      return new Product(in);
    }

    @Override
    public Product[] newArray(int size) {
      return new Product[size];
    }
  };
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
  }

  protected Product(Parcel in) {
    name = in.readString();
    description = in.readString();
    ownerID = in.readString();
    locationID = in.readString();
    price = in.readInt();
    imageId = in.readInt();
    productID = in.readString();
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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(@NonNull Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(description);
    dest.writeString(ownerID);
    dest.writeString(locationID);
    dest.writeInt(price);
    dest.writeInt(imageId);
    dest.writeString(productID);
  }

  public enum Status {
    AVAILABLE,
    SOLD_OUT
  }
}
