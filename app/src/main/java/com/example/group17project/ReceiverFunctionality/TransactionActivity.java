package com.example.group17project.ReceiverFunctionality;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.group17project.R;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
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