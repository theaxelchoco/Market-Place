package com.example.group17project.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RangeTest {

  @Test
  public void contains() {
    Range<Integer> range = new Range<>(0, 10);
    assertTrue(range.contains(5));
    assertFalse(range.contains(11));
  }
}