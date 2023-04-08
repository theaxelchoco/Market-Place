/*
Search code
Group 17
*/

package com.example.group17project.ReceiverFunctionality;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group17project.Homepages.HomepageActivity;
import com.example.group17project.R;
import com.example.group17project.utils.Methods;
import com.example.group17project.utils.model.User;
import com.example.group17project.utils.model.observer.Filter;
import com.example.group17project.utils.repository.FilterRepository;
import com.google.firebase.database.FirebaseDatabase;

public class AdvanceSearchActivity extends AppCompatActivity {
  private Bundle filterBundle;

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

    Button cancelButton = findViewById(R.id.advanceSearchCancelButton);
    cancelButton.setOnClickListener(v -> startActivity(intent));

    Button saveButton = findViewById(R.id.advanceSearchSaveButton);

    //If the user chooses to save their filter, we use the filter repository to store the information
    saveButton.setOnClickListener(v -> {
      makeFilter();
      Filter filter = Methods.makeFilter(filterBundle, User.getInstance().getEmail());

      FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.firebase_database_url));
      FilterRepository filterRepository = new FilterRepository(database);
      filterRepository.createFilter(filter);
    });
  }

  //Method grabs all the filtered data the user decides to specify and uses it to filter out the products
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
    String preferredProductTypeString = preferredProductTypeSpinner.getSelectedItem().toString().toUpperCase().replace(" ", "_");

    filterBundle.putString("keyword", keyword);
    filterBundle.putString("location", location);
    filterBundle.putString("minPrice", minPrice);
    filterBundle.putString("maxPrice", maxPrice);
    filterBundle.putString("productType", productTypeString);
    filterBundle.putString("preferredProductType", preferredProductTypeString);
    filterBundle.putString("ownerID", "");
    filterBundle.putBoolean("filter", true);
  }
}