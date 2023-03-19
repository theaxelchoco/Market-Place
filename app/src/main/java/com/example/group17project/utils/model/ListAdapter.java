package com.example.group17project.utils.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.group17project.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Product> {

    public ListAdapter(Context context, ArrayList<Product> productArrayList){
        super(context, R.layout.item_frame, productArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_frame, parent, false);
        }

        TextView productName = convertView.findViewById(R.id.itemProductNameFrame);
        TextView productDescription = convertView.findViewById(R.id.itemProductDescriptionFrame);
        TextView productType = convertView.findViewById(R.id.itemProductTypeFrame);

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productType.setText(product.getType());


        return convertView;
    }
}
