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

    private TextView nameView;
    private TextView typeView;
    private TextView descView;
    private String name;
    private String type;
    private String exchange;
    private String desc;
    private String productId;
    private String location;
    private long dateVal;
    private int price;
    private Date date;
    private ProductRepository productRepository;

    private Button backBtn;
    private Button deleteBtn;
    private Button editBtn;

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
            name = intent.getStringExtra("name");
            type = intent.getStringExtra("type");
            exchange = intent.getStringExtra("exchange");
            desc = intent.getStringExtra("desc");
            location = intent.getStringExtra("location");
            dateVal = intent.getLongExtra("date", 0);
            productId = intent.getStringExtra("productId");
            date = new Date(dateVal);
            price = intent.getIntExtra("price", 0);

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
        Intent intent = new Intent(ExpandedProviderActivity.this, AddProductActivity.class);
        intent.putExtra("edit", productId);
        intent.putExtra("name", name);
        intent.putExtra("type", type);
        intent.putExtra("exchange", exchange);
        intent.putExtra("location", location);
        intent.putExtra("desc", desc);
        intent.putExtra("date", dateVal);
        intent.putExtra("price", price);
        startActivity(intent);
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