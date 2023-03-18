package com.example.group17project.utils.repository;

import com.example.group17project.utils.model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ProductRepository {
  private final DatabaseReference databaseRef;

  public ProductRepository(FirebaseDatabase database, boolean isTest) {
    this.databaseRef = database.getReference(isTest ? "test-products" : "products");
  }

  public ProductRepository(FirebaseDatabase database) {
    this(database, false);
  }

  /**
   * Creates a new product in the database.
   *
   * @param name    The name of the product.
   * @param ownerID The ID of the owner of the product.
   */
  public void createProduct(String name, String ownerID) {
    String key = databaseRef.push().getKey();
    Product product = new Product(name, ownerID);
    product.setProductID(key);
    databaseRef.child(Objects.requireNonNull(key)).setValue(product);
  }

  public void createProduct(Product product) {
    String key = databaseRef.push().getKey();
    product.setProductID(key);
    databaseRef.child(Objects.requireNonNull(key)).setValue(product);
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
   * @param product The product to update.
   */
  public void updateProduct(String id, Product product) {
    databaseRef.child(id).setValue(product);
  }

  public void deleteAllProducts() {
    databaseRef.removeValue();
  }
  
  public DatabaseReference getDatabaseRef() {
    return databaseRef;
  }

}
