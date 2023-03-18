package com.example.group17project.utils.model;

import android.util.Range;

public class Filter {
  public static final Filter ofDefault = new Filter(null, false, null, null, null, null);
  private final ProductType type;
  private final ProductType preferredExchange;
  private final Range<Integer> priceRange;
  private final String location;
  private final String ownerID;
  private boolean isSaved;
  private boolean isEnabled;
  private String id;

  public Filter(String ownerID, boolean isSaved, ProductType type, ProductType preferredExchange, Range<Integer> priceRange, String location) {
    this.ownerID = ownerID;
    this.isSaved = isSaved;
    this.type = type;
    this.preferredExchange = preferredExchange;
    this.priceRange = priceRange;
    this.location = location;

    this.isEnabled = true;
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

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }

  public String getOwnerID() {
    return ownerID;
  }

  public boolean isSaved() {
    return isSaved;
  }

  public void setSaved(boolean saved) {
    isSaved = saved;
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
