package com.example.group17project.utils.alert;

import android.content.Context;

import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.observer.Filter;
import com.example.group17project.utils.model.observer.Observer;

import java.util.HashSet;
import java.util.Set;

public class ProductAlert implements AlertManager {
  private final Set<String> ownerIDs;
  private final Product product;
  private final Context context;

  /**
   * The HashSet filters is all the filters that are currently in the database
   *
   * @param product the product to be alerted
   * @param filters the list of filters to applied
   */
  public ProductAlert(Product product, Set<Filter> filters, Context context) {
    this.product = product;
    this.ownerIDs = new HashSet<>();
    this.context = context;
    gatherOwnerIDs(filters);
  }

  public Set<String> getOwnerIDs() {
    return ownerIDs;
  }

  /**
   * This is the attach method for the observer pattern
   *
   * @param filters the list of filters to applied
   */
  public void gatherOwnerIDs(Set<Filter> filters) {
    filters.stream()
        .filter(filter -> filter.isMatch(product))
        .filter(filter -> !filter.getOwnerID().equals(product.getOwnerID()))
        .forEach(this::attach);
  }

  @Override
  public void attach(Observer filter) {
    if (filter instanceof Filter) {
      ownerIDs.add(((Filter) filter).getOwnerID());
    }
  }

  @Override
  public void alertUsers() {
    ownerIDs.forEach(this::sendNotification);
  }

  private void sendNotification(String ownerID) {
  }
}
