package com.example.group17project.utils;

import com.example.group17project.utils.model.Filter;
import com.example.group17project.utils.model.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductAlert {
  private final Set<String> ownerIDs;

  public ProductAlert(Product product, List<Filter> filters) {
    ownerIDs = gatherOwnerIDs(product, filters);
  }

  public Set<String> getOwnerIDs() {
    return ownerIDs;
  }

  private Set<String> gatherOwnerIDs(Product product, List<Filter> filters) {
    Set<String> result = new HashSet<>();

    filters.stream().parallel()
        .filter(filter -> filter.isMatch(product))
        .forEach(filter -> result.add(filter.getOwnerID()));

    return result;
  }
}
