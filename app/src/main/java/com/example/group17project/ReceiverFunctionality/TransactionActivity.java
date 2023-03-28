package com.example.group17project.ReceiverFunctionality;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.group17project.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TransactionActivity extends AppCompatActivity {

    EditText productNameText;
    EditText approxValText;
    RatingBar ratingBar;

    TextView nameErrorText;
    TextView valueErrorText;
    TextView ratingErrorText;

    Button backBtn;
    Button confirmBtn;

    private int price;
    private long dateVal;
    private String name;
    private String type;
    private String exchange;
    private String desc;
    private String location;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        findViewComponents();
        setOnClickMethods();
        setViewFromIntentExtras();
    }

    protected void setViewFromIntentExtras(){
        Date date;
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
            productId = intent.getStringExtra("productId");


            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.CANADA);
            String dateString = sdf.format(date);


        }
    }

    protected void findViewComponents(){


    }

    protected void setOnClickMethods(){

    }

    public boolean isProductNameValid(String name){
        return true;
    }

    public boolean isValidMarketValue(String val){
        return true;
    }

    public boolean isRatingSet(){
        return true;
    }

    public void setRating(float rating){

    }
}