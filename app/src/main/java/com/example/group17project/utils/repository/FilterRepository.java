package com.example.group17project.utils.repository;

import com.example.group17project.utils.model.Filter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class FilterRepository {
  private final DatabaseReference databaseRef;

  public FilterRepository(FirebaseDatabase database, boolean isTest) {
    this.databaseRef = database.getReference(isTest ? "test-filters" : "filters");
  }

  public FilterRepository(FirebaseDatabase database) {
    this(database, false);
  }

  /**
   * Creates a new filter in the database.
   *
   * @param filter The filter to create.
   */
  public void createFilter(Filter filter) {
    String key = databaseRef.push().getKey();
    filter.setId(key);
    databaseRef.child(Objects.requireNonNull(key)).setValue(filter);
  }

  /**
   * Delete a filter in the database.
   *
   * @param id The ID of the filter to delete.
   */
  public void deleteFilter(String id) {
    databaseRef.child(id).removeValue();
  }

  public DatabaseReference getDatabaseRef() {
    return databaseRef;
  }
}
