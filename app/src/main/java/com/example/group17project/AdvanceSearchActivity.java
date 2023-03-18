package com.example.group17project;

import android.os.Bundle;
import android.util.Range;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group17project.utils.model.Filter;
import com.example.group17project.utils.model.ProductType;

public class AdvanceSearchActivity extends AppCompatActivity {

  private Filter filter;
  private String keyword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_advance_search);

    Button submitButton = findViewById(R.id.advanceSearchSubmitButton);
    submitButton.setOnClickListener(v -> makeFilter());
  }

  private void makeFilter() {
    EditText keywordEditView = findViewById(R.id.advanceSearchKeywordEditView);
    EditText locationEditView = findViewById(R.id.advanceSearchExchangePlaceEditView);
    EditText minPriceEditView = findViewById(R.id.advanceSearchValueFromEditView);
    EditText maxPriceEditView = findViewById(R.id.advanceSearchValueToEditView);
    Spinner productTypeSpinner = findViewById(R.id.advanceSearchTypeSpinner);
    Spinner preferredProductTypeSpinner = findViewById(R.id.advanceSearchPrefExchangeSpinner);

    this.keyword = keywordEditView.getText().toString();
    String location = locationEditView.getText().toString();
    String minPrice = minPriceEditView.getText().toString();
    String maxPrice = maxPriceEditView.getText().toString();

    String productTypeString = productTypeSpinner.getSelectedItem().toString().toUpperCase().replace(" ", "_");
    ProductType productType = ProductType.valueOf(productTypeString);
    String preferredProductTypeString = preferredProductTypeSpinner.getSelectedItem().toString().toUpperCase().replace(" ", "_");
    ProductType preferredProductType = ProductType.valueOf(preferredProductTypeString);

    filter = new Filter(
        null,
        false,
        productType,
        preferredProductType,
        makePriceRange(minPrice, maxPrice),
        location
    );
  }

  private Range<Integer> makePriceRange(int minPrice, int maxPrice) {
    return new Range<>(minPrice, maxPrice);
  }

  private Range<Integer> makePriceRange(String minPrice, String maxPrice) {
    int minPriceInt = minPrice.isEmpty() ? 0 : Integer.parseInt(minPrice);
    int maxPriceInt = maxPrice.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(maxPrice);
    return makePriceRange(minPriceInt, maxPriceInt);
  }
}