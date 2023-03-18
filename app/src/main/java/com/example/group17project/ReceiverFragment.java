package com.example.group17project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.group17project.utils.model.ListAdapter;
import com.example.group17project.utils.model.Product;

import java.util.ArrayList;

public class ReceiverFragment extends Fragment {
  private ListView searchListView;
  private ArrayList<Product> searchList;
  private ListAdapter productAdapter;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    searchList = new ArrayList<>();
    Bundle bundle = getArguments();
    if (bundle != null) {
      searchList = (ArrayList<Product>) bundle.getSerializable("searchResult", ArrayList.class);
    }

    productAdapter = new ListAdapter(getActivity(), searchList);
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_receiver, container, false);

    searchListView = view.findViewById(R.id.searchResultList);
    searchListView.setAdapter(productAdapter);
    return view;
  }

}
