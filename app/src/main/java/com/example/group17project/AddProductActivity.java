package com.example.group17project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.User;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Button submitButton, cancelButton;
    private EditText productName, description, placeOfExchange, marketValue;
    private TextView productNameErrorLbl, exchangeErrorLbl, marketErrorLbl;

    private DatePicker datePicker;
    private User user;

    private Spinner productType, preferredExchange;

    private String selectedProductType, selectedPreferredExchange;

    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        setUp();
        user = User.getInstance();
        submitButton.setOnClickListener(this::onClick);
        cancelButton.setOnClickListener(this::cancelButtonOnClick);
    }

    public void cancelButtonOnClick(View view){
        switchBack();
    }
    protected void onClick(View view){
        String productName = getProductName();
        String description = getDescription();
        String productType = getType();
        String prefExchange = getPreferredExchange();
        String exchangePlace = getPlaceOfExchange();
        String marketVal = getMarketValue();
        Calendar date = getDate();


        if(validPlaceOfExchange(exchangePlace) && validMarketValue(marketVal) && validProductName(productName)){
            Product product = new Product(productName, user.getEmail(), description, date, productType, exchangePlace, prefExchange);
            productRepository.createProduct(product);
            switchBack();
        }
        else{
            setErrors(productName, exchangePlace, marketVal);
        }
    }

    public void switchBack(){
        Intent intent = new Intent(AddProductActivity.this, HomepageActivity.class);
        intent.putExtra("fragmentId", "provider");
        startActivity(intent);

    }

    protected void setErrors(String productName, String placeOfExchange, String marketVal){
        String pNameError = "";
        String excError = "";
        String marketError = "";

        if(!validProductName(productName)){
            pNameError = "Please enter a name for your product!";
        }
        if(!validPlaceOfExchange(placeOfExchange)){
            excError = "Please enter a place of exchange!";

        }
        if(!validMarketValue(marketVal)){
            if(marketVal.isEmpty()){
                marketError = "Please enter an approximate market value!";
            }
            else{
                marketError = "Please enter an integer market value!";
            }
        }

        setProductNameErrorLbl(pNameError);
        setExchangeErrorLbl(excError);
        setMarketErrorLbl(marketError);
    }
    protected void setMarketErrorLbl(String message){
        marketErrorLbl.setText(message);
    }

    protected void setProductNameErrorLbl(String message){
        productNameErrorLbl.setText(message);
    }

    protected void setExchangeErrorLbl(String message){
        exchangeErrorLbl.setText(message);
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
        return selectedProductType;
    }

    protected String getPreferredExchange(){
        return selectedPreferredExchange;
    }

    private void setUp(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
        productRepository = new ProductRepository(database, false);

        findViewComponents();
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.product_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productType.setAdapter(adapter);
        preferredExchange.setAdapter(adapter);

        productType.setOnItemSelectedListener(this);
        preferredExchange.setOnItemSelectedListener(this);
    }

    private void findViewComponents(){
        submitButton = findViewById(R.id.submit_button);
        cancelButton = findViewById(R.id.cancel_button);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int viewId = adapterView.getId();
        String selectedItem = adapterView.getItemAtPosition(i).toString();

        if(viewId == R.id.product_type_spinner){
            selectedProductType = selectedItem;
        } else if(viewId == R.id.preferred_exchanges_spinner){
            selectedPreferredExchange = selectedItem;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}