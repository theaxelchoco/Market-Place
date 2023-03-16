package com.example.group17project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.group17project.utils.model.ListAdapter;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.User;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReceiverFragment extends Fragment {
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
    View view = inflater.inflate(R.layout.fragment_receiver, container, false);
    TextView textView = view.findViewById(R.id.text_view);
    textView.setText(User.getInstance().getEmail());
    return view;
  }
}
