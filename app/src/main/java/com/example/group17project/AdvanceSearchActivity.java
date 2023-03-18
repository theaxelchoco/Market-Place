package com.example.group17project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AdvanceSearchActivity extends AppCompatActivity {
  Bundle filterBundle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_advance_search);
    filterBundle = new Bundle();

    Button submitButton = findViewById(R.id.advanceSearchSubmitButton);
    Intent intent = new Intent(this, HomepageActivity.class);
    submitButton.setOnClickListener(v -> {
      makeFilter();
      intent.putExtras(filterBundle);
      startActivity(intent);
    });
  }

  private void makeFilter() {
    EditText keywordEditView = findViewById(R.id.advanceSearchKeywordEditView);
    EditText locationEditView = findViewById(R.id.advanceSearchExchangePlaceEditView);
    EditText minPriceEditView = findViewById(R.id.advanceSearchValueFromEditView);
    EditText maxPriceEditView = findViewById(R.id.advanceSearchValueToEditView);
    Spinner productTypeSpinner = findViewById(R.id.advanceSearchTypeSpinner);
    Spinner preferredProductTypeSpinner = findViewById(R.id.advanceSearchPrefExchangeSpinner);

    String keyword = keywordEditView.getText().toString();
    String location = locationEditView.getText().toString();
    String minPrice = minPriceEditView.getText().toString();
    String maxPrice = maxPriceEditView.getText().toString();

    String productTypeString = productTypeSpinner.getSelectedItem().toString().toUpperCase().replace(" ", "_");
    // ProductType productType = ProductType.valueOf(productTypeString);
    String preferredProductTypeString = preferredProductTypeSpinner.getSelectedItem().toString().toUpperCase().replace(" ", "_");
    // ProductType preferredProductType = ProductType.valueOf(preferredProductTypeString);

    filterBundle.putString("keyword", keyword);
    filterBundle.putString("location", location);
    filterBundle.putString("minPrice", minPrice);
    filterBundle.putString("maxPrice", maxPrice);
    filterBundle.putString("productType", productTypeString);
    filterBundle.putString("preferredProductType", preferredProductTypeString);
  }
}