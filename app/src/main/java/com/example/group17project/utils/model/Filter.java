package com.example.group17project.utils.model;

import android.util.Range;

public class Filter {
  public static final Filter ofDefault = new Filter(null, null, null, null, null);
  private final ProductType type;
  private final ProductType preferredExchange;
  private final Range<Integer> priceRange;
  private final String location;
  private final String ownerID;
  private String id;

  public Filter(String ownerID, ProductType type, ProductType preferredExchange, Range<Integer> priceRange, String location) {
    this.ownerID = ownerID;
    this.type = type;
    this.preferredExchange = preferredExchange;
    this.priceRange = priceRange;
    this.location = location;

    this.id = null;
  }

  public boolean isMatch(Product product) {
    if (type == null) {
      return true;
    }

    boolean result = type == product.getTypeE();
    result &= preferredExchange == product.getPreferredExchangeE();
    result &= priceRange.contains(product.getPrice());
    result &= location.equals(product.getLocationID());
    return result;
  }

  public String getOwnerID() {
    return ownerID;
  }

  public ProductType getType() {
    return type;
  }

  public ProductType getPreferredExchange() {
    return preferredExchange;
  }

  public Range<Integer> getPriceRange() {
    return priceRange;
  }

  public String getLocation() {
    return location;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
