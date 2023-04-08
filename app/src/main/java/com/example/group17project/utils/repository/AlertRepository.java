package com.example.group17project.utils.repository;

import android.util.Log;

import com.example.group17project.utils.model.ProductInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * class to interact with firebase to handle alert data
 */
public class AlertRepository {
  private static final DatabaseReference databaseRef =
      FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com")
          .getReference("alerts");

  private AlertRepository() {
  }

  public static void createAlert(ProductInfo product, String receiverID) {
    Log.println(Log.DEBUG, "----------", product.toString());
    databaseRef
        .child(receiverID.replace(".", ","))
        .child(product.getName())
        .setValue(product.getTime());
  }

  public static void deleteAlert(String receiverID, String key) {
    databaseRef
        .child(receiverID.replace(".", ","))
        .child(key)
        .setValue(Long.MAX_VALUE);
  }

  public static DatabaseReference getDatabaseRef() {
    return databaseRef;
  }
}
