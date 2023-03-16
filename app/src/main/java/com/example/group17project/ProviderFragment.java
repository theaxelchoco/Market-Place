package com.example.group17project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.group17project.utils.model.ListAdapter;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ProviderFragment extends Fragment {
  private ListView productListView;
  private ArrayList<Product> productArrayList;
  private ProductRepository productRepository;
  private ListAdapter productAdapter;


  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
    productRepository = new ProductRepository(database, false);
    productArrayList = new ArrayList<>();
    productAdapter = new ListAdapter(getActivity(), productArrayList);

  }
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_provider, container, false);
    productListView = view.findViewById(R.id.productList);

    productListView.setAdapter(productAdapter);

    Button btn = view.findViewById(R.id.addProductBtn);
    btn.setOnClickListener(this::onClick);
    return view;
  }

  public void onClick(View view){
    Intent i = new Intent(getActivity(), AddProductActivity.class);
    startActivity(i);
  }

  public void onStart(){
    super.onStart();
    productRepository.getDatabaseRef().addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        productArrayList.clear();
        for(DataSnapshot data : snapshot.getChildren()){
          Product product = data.getValue(Product.class);
          Long dateAvailableMillis = data.child("dateAvailable").child("time").getValue(Long.class);
          Date dateAvailable = new Date(dateAvailableMillis);
          product.setDateAvailable(dateAvailable);
          productArrayList.add(product);
        }

        productAdapter.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }




}
