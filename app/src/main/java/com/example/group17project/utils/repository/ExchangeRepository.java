package com.example.group17project.utils.repository;


import com.example.group17project.utils.model.ExchangeHistory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ExchangeRepository {
  private final DatabaseReference databaseRef;


  public ExchangeRepository(FirebaseDatabase database, boolean isTest) {
    this.databaseRef = database.getReference(isTest ? "test-exchange_history" : "exchange_history");
  }

  public ExchangeRepository(FirebaseDatabase database) {
    this(database, false);
  }

  /**
   * Creates a new product in the database.
   * @param ownerID The ID of the owner of the product.
   */
  public void createHistory(String ownerID) {
    String key = databaseRef.push().getKey();
    ExchangeHistory history = new ExchangeHistory(ownerID);
    history.setId(key);
    databaseRef.child(Objects.requireNonNull(key)).setValue(history);
  }

  public void createHistory(ExchangeHistory history) {
    String key = databaseRef.push().getKey();
    history.setId(key);
    history.setDetails(history.getDetails());
    databaseRef.child(Objects.requireNonNull(key)).setValue(history);
  }

  public DatabaseReference getDatabaseRef(){
    return databaseRef;
  }

  /**
   * Deletes a product from the database.
   *
   * @param id The ID of the product to delete.
   */
  public void deleteProduct(String id) {
    databaseRef.child(id).removeValue();
  }

  /**
   * Updates a product in the database.
   *
   * @param id      The ID of the product to update.
   * @param history The product to update.
   */
  public void updateHistory(String id, ExchangeHistory history) {
    databaseRef.child(id).setValue(history);
    databaseRef.child(id).child("ID").setValue(id);
  }

  public void deleteAllProducts() {
    databaseRef.removeValue();
  }
}
