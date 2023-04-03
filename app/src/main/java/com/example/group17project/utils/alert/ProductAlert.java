package com.example.group17project.utils.alert;

import com.example.group17project.utils.model.Filter;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.user.Observer;

import java.util.HashSet;
import java.util.Set;

public class ProductAlert implements AlertManager {
  private final Set<String> ownerIDs;
  private final Product product;

  private final Set<Observer> users;

  public ProductAlert(Product product, Set<Filter> filters) {
    this.product = product;
    this.users = new HashSet<>();
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
        .filter(filter -> !filter.getOwnerID().equals(product.getOwnerID()))
        .forEach(filter -> result.add(filter.getOwnerID()));

    return result;
  }

  @Override
  public void attach(Observer user) {
    users.add(user);
  }

  @Override
  public void alertUsers() {
    ownerIDs.forEach(this::sendNotification);
  }

  private void sendNotification(String ownerID) {
    // TODO: send notification to user
  }
}
