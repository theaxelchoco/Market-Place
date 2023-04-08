package com.example.group17project.utils.model.observer;


import com.example.group17project.utils.Methods;
import com.example.group17project.utils.Range;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.ProductType;
import com.example.group17project.utils.model.User;

import java.util.Objects;

public class Filter extends Observer {
  public static final Filter ofDefault = new Filter(null, null, null, null, null);
  private final ProductType type;
  private final ProductType preferredExchange;
  private final Range<Integer> priceRange;
  private final String location;
  private String ownerID;
  private String id;

  public Filter(String ownerID, ProductType type, ProductType preferredExchange, Range<Integer> priceRange, String location) {
    this.ownerID = ownerID;
    this.type = type;
    this.preferredExchange = preferredExchange;
    this.priceRange = priceRange;
    this.location = location;
  }

  public Filter() {
    this(null, null, null, null, null);
  }

  public boolean isMatch(Product product) {
    if (type == null) {
      return true;
    }

    boolean result = type == ProductType.valueOf(product.getType().replace(" ", "_").toUpperCase());
    result &= preferredExchange == ProductType.valueOf(product.getPreferredExchange().replace(" ", "_").toUpperCase());
    result &= priceRange.contains(product.getPrice());
    result &= Methods.isSameLocation(location, product.getLocationID());
    return result;
  }

  public String getOwnerID() {
    return ownerID;
  }

  public void setOwnerID(String ownerID) {
    this.ownerID = ownerID;
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

  public ProductType getType() {
    return type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Filter filter = (Filter) o;
    return id.equals(filter.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public void update(String message) {
    //This method should not do anything hence why it is empty
  }
}
