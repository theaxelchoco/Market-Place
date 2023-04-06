package com.example.group17project.utils.model;

import java.util.Date;
import java.util.Objects;

/**
 * This class is used to store the information of a product
 * in database for alert function.
 * <p>
 * This class implemented hashCode() and equals() method, so that
 * it can be used as a key in HashMap.
 */
public class ProductInfo {
  private final String id;
  private final String name;
  private final long time;
  private boolean isNotified;

  public ProductInfo(Product product) {
    this.id = product.getProductID();
    this.name = product.getName();
    this.time = product.getDateAvailable().getTime();
    isNotified = false;
  }

  public boolean shouldNotify(Date date) {
    return !isNotified && date.getTime() >= time;
  }

  public boolean isNotified() {
    return isNotified;
  }

  public void setNotified(boolean notified) {
    isNotified = notified;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public long getTime() {
    return time;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ProductInfo) {
      return ((ProductInfo) other).getId().equals(id);
    } else if (other instanceof Product) {
      return ((Product) other).getProductID().equals(id);
    }

    return false;
  }

  @Override
  public String toString() {
    return "ProductInfo{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", time=" + time +
        ", isNotified=" + isNotified +
        '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
