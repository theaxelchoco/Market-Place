package com.example.group17project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProviderFragment extends Fragment {
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_provider, container, false);
    TextView textView = view.findViewById(R.id.text_view);
    textView.setText("You are a Provider");

    Button btn = view.findViewById(R.id.addProductBtn);
    btn.setOnClickListener(this::onClick);
    return view;
  }

  public void onClick(View view){
    Intent i = new Intent(getActivity(), AddProductActivity.class);
    startActivity(i);
  }
}
