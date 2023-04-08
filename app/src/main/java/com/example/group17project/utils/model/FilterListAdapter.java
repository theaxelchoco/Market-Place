package com.example.group17project.utils.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.group17project.R;
import com.example.group17project.utils.model.observer.Filter;

import java.util.List;

public class FilterListAdapter extends ArrayAdapter<Filter> {

  public FilterListAdapter(@NonNull Context context, List<Filter> resource) {
    super(context, R.layout.filter_frame, resource);
  }

  /**
   * Method used to format the listview in way particular to our desire. In this case, we want to format our listview
   * to display information about the filter
   * @param position index representing the location of the particular item in the list view
   * @param convertView The view of the screen
   * @param parent The view that holds this one
   * @return list view formatted correctly
   */
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Filter filter = getItem(position);

    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_frame, parent, false);
    }

    TextView filterID = convertView.findViewById(R.id.filterIdFrame);

    filterID.setText(filter.getId());

    return convertView;
  }
}
