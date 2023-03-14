package com.example.group17project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.group17project.utils.model.User;

public class ReceiverFragment extends Fragment {
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_provider, container, false);
    TextView textView = view.findViewById(R.id.text_view);
    textView.setText(User.getInstance().getEmail());
    return view;
  }
}
