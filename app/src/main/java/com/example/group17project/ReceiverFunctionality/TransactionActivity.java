/*
Transaction code
Group 17
*/

package com.example.group17project.ReceiverFunctionality;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.group17project.R;
import com.example.group17project.utils.model.ExchangeHistory;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.User;
import com.example.group17project.utils.repository.ExchangeRepository;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private String ownerId;
    Date date;
    User user;

    private String tradeItem;
    private String marketValue;
    private float rating;

    private ProductRepository productRepository;
    private ExchangeRepository exchangeRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
        productRepository = new ProductRepository(database, false);
        exchangeRepository = new ExchangeRepository(database,false);
        user = User.getInstance();

        findViewComponents();
        setOnClickMethods();
        setViewFromIntentExtras();
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
            date = new Date(dateVal);
            price = intent.getIntExtra("price", 0);
            productId = intent.getStringExtra("productId");
            ownerId = intent.getStringExtra("ownerId");

        }
    }

    protected void backButtonOnClick(View view){
        Intent intent = new Intent(TransactionActivity.this, ExpandedReceiverActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("type", type);
        intent.putExtra("exchange", exchange);
        intent.putExtra("location", location);
        intent.putExtra("desc", desc);
        intent.putExtra("date", dateVal);
        intent.putExtra("price", price);
        intent.putExtra("productId", productId);
        intent.putExtra("ownerId", ownerId);
        startActivity(intent);
    }
    @SuppressLint("DefaultLocale")
    protected void confirmButtonOnClick(View view){
        tradeItem = getTradeItem();
        marketValue = getMarketValue();

        if(isRatingSet() && isProductNameValid(tradeItem) && isValidMarketValue(marketValue)){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            Date currentDate = new Date(System.currentTimeMillis());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");

            Product product = new Product(name, ownerId, desc, cal, type, location, exchange, price);
            product.setStatus(Product.Status.SOLD_OUT);
            product.completeTransaction(tradeItem, marketValue, user.getEmail(), currentDate);

            ExchangeHistory history = new ExchangeHistory(ownerId,user.getEmail());
            history.setDetails(String.format("Owner: %s | Owner Item: %s | Location: %s | Owner Price: %d | Date: %s | Buyer: %s| Buyer Item: %s | Buyer Price: %s "
                    ,ownerId,name,location,price,dateFormat.format(date),user.getEmail(),tradeItem,marketValue));

            exchangeRepository.createHistory(history);
            productRepository.updateProduct(productId, product);
        }
        setErrors(tradeItem, marketValue);
    }

    protected void setErrors(String productName, String marketVal) {
        String pNameError = "";
        String marketError = "";
        String ratingError = "";

        if (!isProductNameValid(productName)) {
            pNameError = getString(R.string.PRODUCT_NAME_ERROR);
        }
        if (!isValidMarketValue(marketVal)) {
            if (marketVal.isEmpty()) {
                marketError = getString(R.string.EMPTY_MARKET_VAL);
            } else {
                marketError = getString(R.string.MARKET_VAL_NEED_INT_ERROR);
            }
        }
        if(!isRatingSet()){
            ratingError = "Please enter a rating for this user.";
        }

        nameErrorText.setText(pNameError);
        valueErrorText.setText(marketError);
        ratingErrorText.setText(ratingError);
    }

    protected void findViewComponents(){
        productNameText = findViewById(R.id.transactionItemEditText);
        approxValText = findViewById(R.id.transactionApproxEditText);
        ratingBar = findViewById(R.id.transactionRatingBar);

        nameErrorText = findViewById(R.id.transactionNameError);
        valueErrorText = findViewById(R.id.transactionMarketError);
        ratingErrorText = findViewById(R.id.transactionRatingError);

        backBtn = findViewById(R.id.transactionBackBtn);
        confirmBtn = findViewById(R.id.transactionConfirmBtn);
    }

    protected void setOnClickMethods(){
        confirmBtn.setOnClickListener(this::confirmButtonOnClick);
        backBtn.setOnClickListener(this::backButtonOnClick);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = v;
            }
        });
    }

    public String getTradeItem(){
        return productNameText.getText().toString().trim();
    }

    public String getMarketValue(){
        return approxValText.getText().toString().trim();
    }

    public boolean isProductNameValid(String name){
        return !name.isEmpty();
    }

    public boolean isValidMarketValue(String val){
        if (val.isEmpty()) {
            return false;
        }
        try {
            int intVal = Integer.parseInt(val);
            return intVal > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isRatingSet(){
        return rating > 0;
    }

    public void setRating(float rating){
        this.rating = rating;
    }
}