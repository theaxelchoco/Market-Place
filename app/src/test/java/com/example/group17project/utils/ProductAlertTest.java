package com.example.group17project.utils;

import static org.junit.Assert.assertEquals;

import android.content.pm.PackageManager;

import com.example.group17project.utils.alert.ProductAlert;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.observer.Filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ProductAlertTest {
  private ProductAlert productAlert;
  private Product product;
  private Set<Filter> filters;

  @Before
  public void setUp() throws PackageManager.NameNotFoundException {
    makingMockProduct();
    makingMockFilters();

    productAlert = new ProductAlert(product, filters);

    System.out.println(filters);
  }

  private void makingMockProduct() {
    product = new Product("testProduct",
        "testOwner@dal.ca",
        "testDescription",
        Calendar.getInstance(),
        Product.COMPUTERACCESSORIES,
        "halifax",
        Product.MOBILEPHONES,
        100);
  }

  private void makingMockFilters() {
    filters = new HashSet<>();
    Filter filter1 = makingMockFilter(true, "testOwner@dal.ca");
    Filter filter2 = makingMockFilter(false, "testOwner@dal.ca");
    Filter filter3 = makingMockFilter(true, "ta3130@dal.ca");
    Filter filter4 = makingMockFilter(true, "ta3130@dal.ca");
    filters.add(filter1);
    filters.add(filter2);
    filters.add(filter3);
    filters.add(filter4);
  }

  private Filter makingMockFilter(boolean isMatch, String ownerID) {
    Filter filter = Mockito.mock(Filter.class);
    Mockito.when(filter.isMatch(product)).thenReturn(isMatch);
    Mockito.when(filter.getOwnerID()).thenReturn(ownerID);
    return filter;
  }

  @Test
  public void gatherOwnerIDs() {
    Set<String> fakeOwnerIDs = new HashSet<>();
    // explain: the product owner should not be notified, and the same ownerID should not be added twice
    fakeOwnerIDs.add("ta3130@dal.ca");
    assertEquals(fakeOwnerIDs.toString(), productAlert.getReceiverIDs().toString());
  }
}