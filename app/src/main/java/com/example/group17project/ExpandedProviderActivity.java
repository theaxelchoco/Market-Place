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

public class ExpandedProviderActivity extends AppCompatActivity {

    private TextView nameView, typeView, descView;
    private String productId;
    private ProductRepository productRepository;

    private Button backBtn, deleteBtn, editBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_expanded);

        setUp();
        findViewComponents();
        setOnClickMethods();
        setViewFromIntentExtras();

    }

    protected void setUp(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
        productRepository = new ProductRepository(database, false);
    }

    protected void setViewFromIntentExtras(){
        Intent intent = this.getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");
            String type = intent.getStringExtra("type");
            String exchange = intent.getStringExtra("exchange");
            String desc = intent.getStringExtra("desc");
            Long dateVal = intent.getLongExtra("date", 0);
            productId = intent.getStringExtra("productId");
            Date date = new Date(dateVal);
            int price = intent.getIntExtra("price", 0);

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.CANADA);
            String dateString = sdf.format(date);

            String typesString = "Product type: " + type + "\n" + "Preferred: " + exchange;
            String descTimePrice = desc + "\n\n" + "Date Available: " + dateString + "\n" + "Estimated Value: " + price;


            nameView.setText(name);
            typeView.setText(typesString);
            descView.setText(descTimePrice);

        }
    }

    public void backButtonOnClick(View view){
        Intent intent = new Intent(ExpandedProviderActivity.this, HomepageActivity.class);
        intent.putExtra("fragmentId", "provider");
        startActivity(intent);
    }

    public void deleteButtonOnClick(View view){
        productRepository.deleteProduct(productId);
        backButtonOnClick(view);
    }

    public void editButtonOnClick(View view){

    }


    protected void findViewComponents(){
        nameView = findViewById(R.id.pExpandedProductName);
        typeView = findViewById(R.id.pExpandedType);
        descView = findViewById(R.id.pExpandedDesc);

        backBtn = findViewById(R.id.pExpandedBackBtn);
        deleteBtn = findViewById(R.id.pExpandedDeleteBtn);
        editBtn = findViewById(R.id.pExpandedEditBtn);
    }

    protected void setOnClickMethods(){
        backBtn.setOnClickListener(this::backButtonOnClick);
        deleteBtn.setOnClickListener(this::deleteButtonOnClick);
        editBtn.setOnClickListener(this::editButtonOnClick);
    }
}