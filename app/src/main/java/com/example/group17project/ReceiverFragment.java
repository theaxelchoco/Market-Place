package com.example.group17project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

  public void onStart() {
    super.onStart();
    searchListView.setClickable(true);
    searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), ExpandedProviderActivity.class);
        intent.putExtra("name", searchList.get(i).getName());
        intent.putExtra("type", searchList.get(i).getType());
        intent.putExtra("exchange", searchList.get(i).getPreferredExchange());
        intent.putExtra("location", searchList.get(i).getLocationID());
        intent.putExtra("desc", searchList.get(i).getDescription());
        intent.putExtra("date", searchList.get(i).getDateAvailable().getTime());
        intent.putExtra("price", searchList.get(i).getPrice());
        intent.putExtra("productId", searchList.get(i).getProductID());
        startActivity(intent);
      }
    });
  }

}
