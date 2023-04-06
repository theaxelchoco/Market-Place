package com.example.group17project.utils.repository;

import com.example.group17project.utils.alert.ProductAlert;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.ProductInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashSet;
import java.util.Set;

public class AlertRepository {
  private final DatabaseReference databaseRef;

  public AlertRepository(FirebaseDatabase database, boolean isTest) {
    this.databaseRef = database.getReference(isTest ? "test-alert" : "alert");
  }

  public AlertRepository(FirebaseDatabase database) {
    this(database, false);
  }

  public void createAlert(ProductAlert alert) {
    Product product = alert.getProduct();
    ProductInfo productInfo = new ProductInfo(product);
    boolean isAvailable = product.getTransactionDate() == null;
    Set<String> users = alert.getReceiverIDs();

    users.forEach(user -> {
      Set<ProductInfo> productInfoList = new HashSet<>();
      // if the user id is in the database, update the product info list,
      // else push the set with only one productinfo directly
      databaseRef.child(user).get().addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
          if (task.getResult().getValue() == null) {
            productInfoList.add(productInfo);
          } else {
            productInfoList.addAll(task.getResult().getValue(HashSet.class));
            productInfoList.remove(productInfo);
            if (isAvailable) {
              productInfoList.add(productInfo);
            }
          }
          databaseRef.child(user).setValue(productInfoList);
        }
      });
    });
  }

  public DatabaseReference getDatabaseRef() {
    return databaseRef;
  }
}
