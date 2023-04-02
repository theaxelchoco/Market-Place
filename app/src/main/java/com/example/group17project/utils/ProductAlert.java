package com.example.group17project.utils;

import com.example.group17project.utils.model.Filter;
import com.example.group17project.utils.model.Product;

import java.util.HashSet;
import java.util.Set;

public class ProductAlert {
  private final Set<String> ownerIDs;
  private final Product product;

  public ProductAlert(Product product, Set<Filter> filters) {
    this.product = product;
    ownerIDs = gatherOwnerIDs(filters);
  }

  public Set<String> getOwnerIDs() {
    return ownerIDs;
  }

  /**
   * This is the attach method for the observer pattern
   *
   * @param filters the list of filters to applied
   * @return the set of ownerIDs that match the product
   */
  public Set<String> gatherOwnerIDs(Set<Filter> filters) {
    Set<String> result = new HashSet<>();

    filters.stream()
        .filter(filter -> filter.isMatch(product))
        .forEach(filter -> result.add(filter.getOwnerID()));

    return result;
  }

  public void alertUsers() {
    ownerIDs.forEach(this::sendNotification);
  }

  private void sendNotification(String ownerID) {
    // TODO: send notification to user
    System.out.println("Sending notification to " + ownerID);
  }
}
