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

public class UserAdapter extends ArrayAdapter<String> {

    public UserAdapter(Context context, ArrayList<String> list){
        super(context, R.layout.item_user, list);
    }


    /**
     * Method used to format the listview in way particular to our desire. In this case, we want to format our listview
     * to display information about the specific user
     * @param position index representing the location of the particular item in the list view
     * @param convertView The view of the screen
     * @param parent The view that holds this one
     * @return list view formatted correctly
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String username = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        TextView output = convertView.findViewById(R.id.usernameTV);
        output.setText(username);

        return convertView;
    }
}