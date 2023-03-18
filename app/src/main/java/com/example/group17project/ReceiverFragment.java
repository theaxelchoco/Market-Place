package com.example.group17project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.group17project.utils.Methods;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.User;

import java.util.ArrayList;
import java.util.List;

public class ReceiverFragment extends Fragment {
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_receiver, container, false);
    TextView textView = view.findViewById(R.id.text_view);
    textView.setText(User.getInstance().getEmail());

    List<Product> searchResult = new ArrayList<>();
    Bundle bundle = getArguments();
    if (bundle != null) {
      searchResult = bundle.getParcelableArrayList("searchResult", Product.class);
    }

    Methods.makeAlert(String.valueOf(searchResult.size()), new AlertDialog.Builder(getContext()));
    return view;
  }
}
