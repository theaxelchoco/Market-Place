package com.example.group17project.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.group17project.R;
import com.example.group17project.utils.model.ExchangeHistory;

import java.util.ArrayList;

public class ExchangeAdaptor extends ArrayAdapter<ExchangeHistory> {

    public ExchangeAdaptor(Context context, ArrayList<ExchangeHistory> list){
        super(context, R.layout.exchange_history, list);
    }

    /**
     * Method used to format the listview in way particular to our desire. In this case, we want to format our listview
     * to display information about the corresponding product
     * @param position index representing the location of the particular item in the list view
     * @param convertView The view of the screen
     * @param parent The view that holds this one
     * @return list view formatted correctly
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ExchangeHistory history = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exchange_history, parent, false);
        }

        TextView output = convertView.findViewById(R.id.itemProductNameFrame);
        String details = history.getDetails();
        output.setText(details);

        return convertView;
    }
}
