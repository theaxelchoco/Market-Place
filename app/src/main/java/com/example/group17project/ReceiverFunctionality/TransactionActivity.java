package com.example.group17project.ReceiverFunctionality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group17project.Homepages.HomepageActivity;
import com.example.group17project.Homepages.LoginLanding;
import com.example.group17project.R;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.User;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    int ownerPVal = 0;
    int ownerRVal = 0;
    int buyerPVal = 0;
    int buyerRVal = 0;
    String ownerKey = "";
    String buyerKey = "";


    private ProductRepository productRepository;
    private DatabaseReference userDB = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
        productRepository = new ProductRepository(database, false);
        userDB = database.getReference("users");
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
    protected void confirmButtonOnClick(View view){
        tradeItem = getTradeItem();
        marketValue = getMarketValue();

        if(isRatingSet() && isProductNameValid(tradeItem) && isValidMarketValue(marketValue)){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            Date currentDate = new Date(System.currentTimeMillis());

            Product product = new Product(name, ownerId, desc, cal, type, location, exchange, price);
            product.setStatus(Product.Status.SOLD_OUT);
            product.completeTransaction(tradeItem, marketValue, user.getEmail(), currentDate);

            productRepository.updateProduct(productId, product);
            updateValuations(ownerId, user.getEmail());
            Toast.makeText(TransactionActivity.this, "Transaction Successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(TransactionActivity.this, HomepageActivity.class));

        }
        setErrors(tradeItem, marketValue);
    }

    protected void updateValuations(String ownerId, String buyerId){
        ownerKey = LoginLanding.encodeUserEmail(ownerId);
        buyerKey = LoginLanding.encodeUserEmail(buyerId);

        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ownerPVal = snapshot.child(ownerKey).child("PValuation").getValue(Integer.class);
                ownerRVal = snapshot.child(ownerKey).child("RValuation").getValue(Integer.class);

                buyerPVal = snapshot.child(buyerKey).child("PValuation").getValue(Integer.class);
                buyerRVal = snapshot.child(buyerKey).child("RValuation").getValue(Integer.class);

                ownerPVal += price;
                buyerRVal += price;

                ownerRVal += Integer.parseInt(marketValue);
                buyerPVal += Integer.parseInt(marketValue);

                System.out.println("method is called");
                setValuations(ownerPVal, ownerRVal, buyerPVal, buyerRVal);
                userDB.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    protected void setValuations(int ownerPVal, int ownerRVal, int buyerPVal, int buyerRVal) {
        userDB.child(ownerKey).child("PValuation").setValue(ownerPVal);
        userDB.child(ownerKey).child("RValuation").setValue(ownerRVal);
        userDB.child(buyerKey).child("PValuation").setValue(buyerPVal);
        userDB.child(buyerKey).child("RValuation").setValue(buyerRVal);
        System.out.println(ownerPVal + " " + ownerRVal + "\n" + buyerPVal + " " + buyerRVal);
        user.setpValuation(buyerPVal);
        user.setrValuation(buyerRVal);
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