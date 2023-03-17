package com.example.group17project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExpandedProviderActivity extends AppCompatActivity {

    private TextView nameView, typeView, excView, descView, dateView, priceView;

    private Button backBtn, deleteBtn, editBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_expanded);

        findViewComponents();

        Intent intent = this.getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");
            String type = intent.getStringExtra("type");
            String exchange = intent.getStringExtra("exchange");
            String desc = intent.getStringExtra("desc");
            Long dateVal = intent.getLongExtra("date", 0);
            Date date = new Date(dateVal);
            int price = intent.getIntExtra("price", 0);

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.CANADA);
            String dateString = sdf.format(date);
            String priceString;

            String typesString = "Product type: " + type + "\n" + "Preferred: " + exchange;
            String descTimePrice = desc + "\n\n" + "Date Available: " + dateString + "\n" + "Estimated Value: " + price;




            nameView.setText(name);
            typeView.setText(typesString);
            //excView.setText(exchange);
            descView.setText(descTimePrice);
            //dateView.setText(dateString);
           // priceView.setText(priceString);




        }

    }


    protected void findViewComponents(){
        nameView = findViewById(R.id.pExpandedProductName);
        typeView = findViewById(R.id.pExpandedType);
        //excView = findViewById(R.id.pExpandedPref);
        descView = findViewById(R.id.pExpandedDesc);
       // dateView = findViewById(R.id.pExpandedDate);
        //priceView = findViewById(R.id.pExpandedPrice);

        backBtn = findViewById(R.id.pExpandedBackBtn);
        deleteBtn = findViewById(R.id.pExpandedDeleteBtn);
        editBtn = findViewById(R.id.pExpandedEditBtn);
    }
}