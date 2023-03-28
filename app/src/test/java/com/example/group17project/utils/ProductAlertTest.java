package com.example.group17project.utils;

import static org.junit.Assert.assertEquals;

import com.example.group17project.utils.model.Filter;
import com.example.group17project.utils.model.Product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductAlertTest {
  private ProductAlert productAlert;
  private Product product;
  private List<Filter> filters;

  @Before
  public void setUp() throws Exception {
    makingMockProduct();
    makingMockFilters();

    productAlert = new ProductAlert(product, filters);

    System.out.println(filters);
  }

  private void makingMockProduct() {
    product = Mockito.mock(Product.class);
    Mockito.when(product.getType()).thenReturn(Product.COMPUTERACCESSORIES);
    Mockito.when(product.getPreferredExchange()).thenReturn(Product.MOBILEPHONES);
    Mockito.when(product.getPrice()).thenReturn(100);
    Mockito.when(product.getLocationID()).thenReturn("halifax");
    Mockito.when(product.getOwnerID()).thenReturn("testOwner@dal.ca");
  }

  private void makingMockFilters() {
    filters = new ArrayList<>();
    Filter filter1 = Mockito.mock(Filter.class);
    Filter filter2 = Mockito.mock(Filter.class);
    Filter filter3 = Mockito.mock(Filter.class);
    Mockito.when(filter1.isMatch(product)).thenReturn(true);
    Mockito.when(filter2.isMatch(product)).thenReturn(false);
    Mockito.when(filter3.isMatch(product)).thenReturn(true);
    filters.add(filter1);
    filters.add(filter2);
    filters.add(filter3);
  }

  @Test
  public void gatherOwnerIDs() {
    Set<String> fakeOwnerIDs = new HashSet<>();
    fakeOwnerIDs.add("testOwner@dal.ca");
    assertEquals(fakeOwnerIDs, productAlert.gatherOwnerIDs(filters));
  }
}