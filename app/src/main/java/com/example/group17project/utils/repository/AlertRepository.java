package com.example.group17project.utils.repository;

import android.util.Log;

import com.example.group17project.utils.model.ProductInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AlertRepository {
  private static final DatabaseReference databaseRef =
      FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com")
          .getReference("alerts");

  public static void createAlert(ProductInfo product, String receiverID) {
    Log.println(Log.DEBUG, "----------", product.toString());
    //databaseRef.child(receiverID.replace(".", ",")).child(product.getId()).setValue(product);
  }

  public static DatabaseReference getDatabaseRef() {
    return databaseRef;
  }
}
