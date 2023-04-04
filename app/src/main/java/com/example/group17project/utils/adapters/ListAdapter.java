package com.example.group17project.utils.adapters;

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
import com.example.group17project.utils.model.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ListAdapter extends ArrayAdapter<Product> {

    public ListAdapter(Context context, ArrayList<Product> productArrayList){
        super(context, R.layout.item_frame, productArrayList);
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
        Product product = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_frame, parent, false);
        }

        TextView productName = convertView.findViewById(R.id.itemProductNameFrame);
        TextView productDescription = convertView.findViewById(R.id.itemProductDescriptionFrame);
        TextView productType = convertView.findViewById(R.id.itemProductTypeFrame);
        TextView productAvailability = convertView.findViewById(R.id.itemAvailabilityText);

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productType.setText(product.getType());
        if(product.getStatus().equals(Product.Status.SOLD_OUT)){
            productAvailability.setText("SOLD");

            Date date = product.getTransactionDate();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.CANADA);
            String dateString = sdf.format(date);

            productDescription.setText("Buyer: " + product.getBuyer() +
                    "\nTrade Item: " + product.getBuyerItem() +
                    "\nValue: " + product.getBuyerItemAmount() +
                    "\nPurchased On: " + dateString);
        }
        else{
            productAvailability.setText("");
        }

        return convertView;
    }
}
