package com.example.group17project.utils.repository;

import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.ProductType;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class ProductRepositoryTest {

  private ProductRepository productRepository;

  @Before
  public void setUp() {
    System.out.println("Setting up");

    Product testProduct = new Product("testProduct", "testOwnerID");
    testProduct.setDescription("testDescription");
    testProduct.setDateAvailable(new Date());
    testProduct.setType(ProductType.COMPUTER_ACCESSORIES);
    testProduct.setLocationID("testLocationID");
    testProduct.setPrice(10.0);
    testProduct.setPreferredExchange(ProductType.MOBILE_PHONES);
  }

  @Test
  public void createProduct() {
  }
}