package com.example.group17project.utils.repository;

import com.example.group17project.utils.model.Filter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FilterRepository {
  private final DatabaseReference databaseRef;
  private final Set<Filter> filters;

  public FilterRepository(FirebaseDatabase database, boolean isTest) {
    this.databaseRef = database.getReference(isTest ? "test-filters" : "filters");
    filters = new HashSet<>();
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
    filters.add(filter);
    databaseRef.child(Objects.requireNonNull(key)).setValue(filter);
  }

  /**
   * Delete a filter in the database.
   *
   * @param id The ID of the filter to delete.
   */
  public void deleteFilter(String id) {
    databaseRef.child(id).removeValue();
    filters.removeIf(filter -> filter.getId().equals(id));
  }

  public Set<Filter> getAllFilters() {
    return filters;
  }
}
