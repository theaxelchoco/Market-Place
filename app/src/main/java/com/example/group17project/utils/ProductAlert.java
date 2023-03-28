package com.example.group17project.utils;

import com.example.group17project.utils.model.Filter;
import com.example.group17project.utils.model.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductAlert {
  private final Set<String> ownerIDs;
  private final Product product;

  public ProductAlert(Product product, List<Filter> filters) {
    this.product = product;
    ownerIDs = gatherOwnerIDs(filters);
  }

  public Set<String> getOwnerIDs() {
    return ownerIDs;
  }

  public Set<String> gatherOwnerIDs(List<Filter> filters) {
    Set<String> result = new HashSet<>();

    filters.stream().parallel()
        .filter(filter -> filter.isMatch(product))
        .forEach(filter -> result.add(filter.getOwnerID()));

    return result;
  }

  public void alertUsers() {
    ownerIDs.forEach(this::sendNotification);
  }

  private void sendNotification(String ownerID) {
    // TODO: send notification to user
  }
}
