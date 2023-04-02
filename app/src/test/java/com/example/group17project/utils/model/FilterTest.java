package com.example.group17project.utils.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class FilterTest {

  @Test
  public void testEquals() {
    Filter filter1 = new Filter();
    filter1.setId("testId");
    Filter filter2 = new Filter();
    filter2.setId("testId");

    assertEquals(filter1, filter2);
  }

  @Test
  public void testCanBeUsedInHashSet() {
    Filter filter1 = new Filter();
    filter1.setId("testId");
    Filter filter2 = new Filter();
    filter2.setId("testId");

    Set<Filter> filters = new HashSet<>();
    filters.add(filter1);
    filters.add(filter2);

    assertEquals(1, filters.size());
  }
}