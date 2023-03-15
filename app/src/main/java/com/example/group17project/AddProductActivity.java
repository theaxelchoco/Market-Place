package com.example.group17project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class AddProductActivity extends AppCompatActivity {


    private Button submitButton;
    private EditText productName, description, placeOfExchange, marketValue;
    private TextView productNameErrorLbl, exchangeErrorLbl, marketErrorLbl;

    private DatePicker datePicker;

    private Spinner productType, preferredExchange;

    private String selectedProductType, selectedPreferredExchange;

    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        setUp();
        submitButton.setOnClickListener(this::onClick);
    }

    protected void onClick(View view){
        String productName = getProductName();
    }

    protected String getProductName(){
        return productName.getText().toString().trim();
    }

    protected String getDescription(){
        return description.getText().toString().trim();
    }

    protected String getPlaceOfExchange(){
        return placeOfExchange.getText().toString().trim();
    }

    protected String getMarketValue(){
        return marketValue.getText().toString().trim();
    }

    protected Calendar getDate(){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar cal = Calendar.getInstance();
        cal.set(year + 1990, month, day);
        return cal;
    }

    protected boolean validProductName(String name){
        return !name.isEmpty();
    }

    protected boolean validMarketValue(String marketValue){
        if(marketValue.isEmpty()){
            return false;
        }
        try{
            int intVal = Integer.parseInt(marketValue);
            return intVal > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    protected boolean validPlaceOfExchange(String place){
        return !place.isEmpty();
    }

    protected String getType(){
        productType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProductType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return selectedProductType;
    }

    protected String getPreferredExchange(){
        preferredExchange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPreferredExchange = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return selectedPreferredExchange;
    }

    private void setUp(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
        productRepository = new ProductRepository(database, true);

        findViewComponents();
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
    }

    private void findViewComponents(){
        submitButton = findViewById(R.id.submit_button);

        productName = findViewById(R.id.productNameEditText);
        description = findViewById(R.id.description_edittext);
        placeOfExchange = findViewById(R.id.place_of_exchange_edittext);
        marketValue = findViewById(R.id.market_value_edittext);

        productNameErrorLbl = findViewById(R.id.productNameErrorLabel);
        exchangeErrorLbl = findViewById(R.id.exchangeErrorLbl);
        marketErrorLbl = findViewById(R.id.marketValueErrorLbl);

        datePicker = findViewById(R.id.date_picker);
        productType = findViewById(R.id.product_type_spinner);
        preferredExchange = findViewById(R.id.preferred_exchanges_spinner);
    }
}