package com.example.group17project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.group17project.utils.Methods;
import com.example.group17project.utils.model.Filter;
import com.example.group17project.utils.model.ListAdapter;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ReceiverFragment extends Fragment {
  private ListView searchListView;
  private ArrayList<Product> searchList;
  private ListAdapter productAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    String searchKeyword;
    super.onCreate(savedInstanceState);

    searchList = new ArrayList<>();
    Bundle bundle = getArguments();
    if (bundle != null) {
      searchKeyword = bundle.getString("keyword");
      if (bundle.getBoolean("filter")) {
        Filter filter = Methods.makeFilter(bundle);
        performSearch(searchKeyword, filter);
      } else {
        performSearch(searchKeyword);
      }
    }

    productAdapter = new ListAdapter(getActivity(), searchList);
  }

  private void performSearch(String keyword, Filter filter) {
    FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.firebase_database_url));
    ProductRepository productRepository = new ProductRepository(database);

    Query searchQuery = productRepository.getDatabaseRef();
    searchQuery.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        ArrayList<Product> searchResult = new ArrayList<>();
        for (DataSnapshot data : snapshot.getChildren()) {
          Product product = data.getValue(Product.class);
          Long dateAvailableMillis = data.child("dateAvailable").child("time").getValue(Long.class);
          assert dateAvailableMillis != null;
          Date dateAvailable = new Date(dateAvailableMillis);
          assert product != null;
          product.setDateAvailable(dateAvailable);

          if (isFilterMatch(product, keyword, filter)) {
            searchResult.add(product);
          }
        }

        if (searchResult.isEmpty()) {
          Methods.makeAlert("No result found", new AlertDialog.Builder(getContext()));
        }
        searchList.clear();
        searchList.addAll(searchResult);
        productAdapter.notifyDataSetChanged();

      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Methods.makeAlert("Error: " + error.getMessage(), new AlertDialog.Builder(getContext()));
      }
    });

  }

  private void performSearch(String keyword) {
    performSearch(keyword, Filter.ofDefault);
  }

  private boolean isFilterMatch(Product product, String keyword, Filter filter) {
    if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
      return filter.isMatch(product);
    }
    return false;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_receiver, container, false);

    searchListView = view.findViewById(R.id.searchResultList);
    searchListView.setAdapter(productAdapter);

    Button advancedSearchButton = view.findViewById(R.id.advanceSearchButton);
    advancedSearchButton.setOnClickListener(v -> {
      Intent intent = new Intent(getActivity(), AdvanceSearchActivity.class);
      startActivity(intent);
    });
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    searchListView.setClickable(true);
    searchListView.setOnItemClickListener((adapterView, view, i, l) -> {
      Intent intent = new Intent(getActivity(), ExpandedReceiverActivity.class);
      intent.putExtra("name", searchList.get(i).getName());
      intent.putExtra("type", searchList.get(i).getType());
      intent.putExtra("exchange", searchList.get(i).getPreferredExchange());
      intent.putExtra("location", searchList.get(i).getLocationID());
      intent.putExtra("desc", searchList.get(i).getDescription());
      intent.putExtra("date", searchList.get(i).getDateAvailable().getTime());
      intent.putExtra("price", searchList.get(i).getPrice());
      intent.putExtra("productId", searchList.get(i).getProductID());
      startActivity(intent);
    });
  }

}
