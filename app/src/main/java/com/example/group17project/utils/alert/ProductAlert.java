package com.example.group17project.utils.alert;

import android.util.Log;

import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.ProductInfo;
import com.example.group17project.utils.model.observer.Filter;
import com.example.group17project.utils.model.observer.Observer;
import com.example.group17project.utils.repository.AlertRepository;

import java.util.HashSet;
import java.util.Set;

public class ProductAlert implements AlertManager {
  private final Set<String> receiverIDs;
  private final Product product;

  /**
   * The HashSet filters is all the filters that are currently in the database
   *
   * @param product the product to be alerted
   * @param filters the list of filters to applied
   */
  public ProductAlert(Product product, Set<Filter> filters) {
    this.product = product;
    this.receiverIDs = new HashSet<>();
    gatherOwnerIDs(filters);
  }

  public Product getProduct() {
    return product;
  }

  public Set<String> getReceiverIDs() {
    return receiverIDs;
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
      receiverIDs.add(((Filter) filter).getOwnerID());
    }
  }

  @Override
  public void alertUsers() {
    receiverIDs.forEach(this::sendNotification);
  }

  private void sendNotification(String receiverID) {
    AlertRepository.createAlert(new ProductInfo(product), receiverID);
    Log.println(Log.DEBUG, "", "---------notifying " + receiverID);
  }
}
