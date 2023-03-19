package com.example.group17project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExpandedReceiverActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView typeView;
    private TextView descView;


    private Button backBtn;
    private Button contactBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_expanded_receiver);

        findViewComponents();
        setOnClickMethods();
        setViewFromIntentExtras();

    }



    protected void setViewFromIntentExtras(){
        Date date;
        int price;
        long dateVal;
        String name;
        String type;
        String exchange;
        String desc;
        String location;

        Intent intent = this.getIntent();
        if(intent != null){
            name = intent.getStringExtra("name");
            type = intent.getStringExtra("type");
            exchange = intent.getStringExtra("exchange");
            desc = intent.getStringExtra("desc");
            location = intent.getStringExtra("location");
            dateVal = intent.getLongExtra("date", 0);
            date = new Date(dateVal);
            price = intent.getIntExtra("price", 0);

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.CANADA);
            String dateString = sdf.format(date);

            String typesString = "Product type: " + type + "\n" + "Preferred: " + exchange;
            String descTimePriceLoc = desc + "\n\n" + "Date Available: " + dateString + "\n" + "Estimated Value: " + price + "\n" + location;


            nameView.setText(name);
            typeView.setText(typesString);
            descView.setText(descTimePriceLoc);

        }
    }

    public void backButtonOnClick(View view){
        Intent intent = new Intent(ExpandedReceiverActivity.this, HomepageActivity.class);
        startActivity(intent);
    }

    public void contactButtonOnClick(View view){
        backButtonOnClick(view);
    }



    protected void findViewComponents(){
        nameView = findViewById(R.id.rExpandedProductName);
        typeView = findViewById(R.id.rExpandedType);
        descView = findViewById(R.id.rExpandedDesc);

        backBtn = findViewById(R.id.rExpandedBackBtn);
        contactBtn = findViewById(R.id.rExpandedContactBtn);

    }

    protected void setOnClickMethods(){
        backBtn.setOnClickListener(this::backButtonOnClick);
        contactBtn.setOnClickListener(this::contactButtonOnClick);
    }
}