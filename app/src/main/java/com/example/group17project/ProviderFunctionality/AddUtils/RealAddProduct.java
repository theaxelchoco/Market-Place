package com.example.group17project.ProviderFunctionality.AddUtils;

import androidx.annotation.NonNull;

import com.example.group17project.utils.ProductAlert;
import com.example.group17project.utils.model.Filter;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.repository.FilterRepository;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashSet;

public class RealAddProduct implements AddEditProduct {
  private static RealAddProduct instance = null;
  ProductRepository productRepository;
  FilterRepository filterRepository;

  ProductAlert productAlert;

  private RealAddProduct() {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
    productRepository = new ProductRepository(database, false);
    filterRepository = new FilterRepository(database, false);
  }

  public static RealAddProduct getInstance() {
    if (instance == null) {
      instance = new RealAddProduct();
    }
    return instance;
  }


  @Override
  public boolean add(String name, String ownerId, String desc, Calendar date, String productType, String exchangePlace, String prefExchange, String marketVal) {
    Product product = new Product(name, ownerId, desc, date, productType, exchangePlace, prefExchange, Integer.parseInt(marketVal));
    productRepository.createProduct(product);
    alertUsers(product);
    return true;
  }

  @Override
  public boolean edit(String name, String ownerId, String desc, Calendar date, String productType, String exchangePlace, String prefExchange, String marketVal, String productId) {
    Product product = new Product(name, ownerId, desc, date, productType, exchangePlace, prefExchange, Integer.parseInt(marketVal));
    productRepository.updateProduct(productId, product);
    alertUsers(product);
    return true;
  }

  /**
   * This method is used to alert users when a product is added or edited
   *
   * @param product the product that was added or edited
   */
  private void alertUsers(Product product) {
    filterRepository.getDatabaseRef().addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        HashSet<Filter> filters = new HashSet<>();
        for (DataSnapshot data : snapshot.getChildren()) {
          Filter filter = data.getValue(Filter.class);
          filters.add(filter);
        }
        productAlert = new ProductAlert(product, filters);
        productAlert.alertUsers();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
      }
    });
  }
}

