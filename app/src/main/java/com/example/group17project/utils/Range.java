package com.example.group17project.utils;

/**
 * The default range class do not have a empty constructor for firebase.
 *
 * @param <T>
 */

public class Range<T extends Comparable<T>> {
  private T lower;
  private T upper;

  public Range() {
  }

  public Range(T lower, T upper) {
    this.lower = lower;
    this.upper = upper;
  }

  public boolean contains(T value) {
    return value.compareTo(lower) >= 0 && value.compareTo(upper) <= 0;
  }

  public T getLower() {
    return lower;
  }

  public T getUpper() {
    return upper;
  }
}
