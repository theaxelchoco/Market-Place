/*
Receiver code
Group 17
*/

package com.example.group17project.ReceiverFunctionality;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.group17project.R;
import com.example.group17project.utils.Methods;
import com.example.group17project.utils.model.Filter;
import com.example.group17project.utils.adapters.ListAdapter;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.User;
import com.example.group17project.utils.model.observer.Filter;
import com.example.group17project.utils.repository.AlertRepository;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReceiverFragment extends Fragment {
  private ListView searchListView;
  private ArrayList<Product> searchList;
  private ListAdapter productAdapter;
  private ProductRepository productRepository;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    String searchKeyword;
    super.onCreate(savedInstanceState);

    makeAlert(this.getContext());

    FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.firebase_database_url));
    productRepository = new ProductRepository(database);

    searchList = new ArrayList<>();
    Bundle bundle = getArguments();
    if (bundle != null) {
      searchKeyword = bundle.getString("keyword");
      if (bundle.getBoolean("filter")) {
        Filter filter = Methods.makeFilter(bundle, null);
        performSearch(searchKeyword, filter);
      } else {
        performSearch(searchKeyword);
      }
    } else {
      productRepository.getDatabaseRef().addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          searchList.clear();
          for (DataSnapshot data : snapshot.getChildren()) {
            Product product = data.getValue(Product.class);
            Long dateAvailableMillis = data.child("dateAvailable").child("time").getValue(Long.class);
            Date dateAvailable = new Date(dateAvailableMillis);
            product.setDateAvailable(dateAvailable);
            //check if the user is identified as a receiver
            //if so, add the product read from database to the productArrayList
            if (product.getOwnerID() != null && !product.getOwnerID().equals(User.getInstance().getEmail()) && product.getStatus().equals(Product.Status.AVAILABLE)) {
              searchList.add(product);
            }
          }
          //when data changed, notify that
          productAdapter.notifyDataSetChanged();
        }

        //error handler
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
      });

    }

    productAdapter = new ListAdapter(getActivity(), searchList);
  }

  private void makeAlert(Context context) {
    AlertRepository
        .getDatabaseRef()
        .child(User.getInstance().getEmail().replace(".", ","))
        .addValueEventListener(new ValueEventListener() {

          /**
           * This method will be called with a snapshot of the data at this location. It will also be called
           * each time that data changes.
           *
           * @param snapshot The current data at the location
           */
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.println(Log.DEBUG, "Alerts", snapshot.toString());
            snapshot.getChildren().forEach(data -> {
              Log.println(Log.DEBUG, "======", data.toString());
              String name = data.getKey();
              long time = data.getValue(Long.class);

              if (Calendar.getInstance().getTimeInMillis() >= time) {
                AlertRepository.deleteAlert(User.getInstance().getEmail(), name);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("You May Be Interested In This Product");
                builder.setMessage("Product: " + name + " is available now");
                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                builder.show();
              }
            });
          }

          /**
           * This method will be triggered in the event that this listener either failed at the server, or
           * is removed as a result of the security and Firebase Database rules. For more information on
           * securing your data, see: <a
           * href="https://firebase.google.com/docs/database/security/quickstart" target="_blank"> Security
           * Quickstart</a>
           *
           * @param error A description of the error that occurred
           */
          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
        });
  }

  private void performSearch(String keyword, Filter filter) {
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

          if (isFilterMatch(product, keyword, filter) && !product.getOwnerID().equals(User.getInstance().getEmail()) && product.getStatus().equals(Product.Status.AVAILABLE)) {
            searchResult.add(product);
          }
        }

        if (searchResult.isEmpty()) {
          Toast.makeText(getContext(), "No product found", Toast.LENGTH_SHORT).show();
        }
        searchList.clear();
        searchList.addAll(searchResult);
        productAdapter.notifyDataSetChanged();

      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });

  }

  private void performSearch(String keyword) {
    performSearch(keyword, Filter.ofDefault);
  }

  private boolean isFilterMatch(Product product, String keyword, Filter filter) {
    keyword = keyword == null ? "" : keyword;
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
    ;


    Button advancedSearchButton = view.findViewById(R.id.advanceSearchButton);
    TextView location = view.findViewById(R.id.locationTextView);
    location.setText(User.getInstance().getUserLocation());

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
      intent.putExtra("ownerId", searchList.get(i).getOwnerID());
      startActivity(intent);
    });
  }

}
