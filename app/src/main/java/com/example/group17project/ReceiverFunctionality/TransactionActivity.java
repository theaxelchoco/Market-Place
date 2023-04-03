package com.example.group17project.ReceiverFunctionality;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group17project.Homepages.HomepageActivity;
import com.example.group17project.Homepages.LoginLanding;
import com.example.group17project.R;
import com.example.group17project.utils.model.ExchangeHistory;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.user.User;
import com.example.group17project.utils.repository.ExchangeRepository;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
  private float databaseRating;
  private int databaseNumRatings;

  int ownerPVal = 0;
  int ownerRVal = 0;
  int buyerPVal = 0;
  int buyerRVal = 0;
  String ownerKey = "";
  String buyerKey = "";


  private ProductRepository productRepository;
  private DatabaseReference userDB = null;
  private ExchangeRepository exchangeRepository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transaction);

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
    productRepository = new ProductRepository(database, false);
    userDB = database.getReference("users");

    exchangeRepository = new ExchangeRepository(database, false);
    user = User.getInstance();

    findViewComponents();
    setOnClickMethods();
    setViewFromIntentExtras();

  }

  /**
   * This method is used to get the information about the product the user is trying to buy. Passed in from
   * last activity which is the expandedReceiver
   */
  protected void setViewFromIntentExtras() {

    Intent intent = this.getIntent();
    if (intent != null) {
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

  /**
   * This method returns back to the expandedReceiver view of the item the user is inspecting
   *
   * @param view current view on screen
   */
  protected void backButtonOnClick(View view) {
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

  /**
   * This method initiates the transaction code when the user presses confirm on the button. Makes
   * use of validation methods as well as database methods to successfully make the transaction appear
   *
   * @param view view on the screen
   */

  @SuppressLint("DefaultLocale")
  protected void confirmButtonOnClick(View view) {
    tradeItem = getTradeItem();
    marketValue = getMarketValue();

    if (isRatingSet() && isProductNameValid(tradeItem) && isValidMarketValue(marketValue)) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);

      Date currentDate = new Date(System.currentTimeMillis());
      DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");

      Product product = new Product(name, ownerId, desc, cal, type, location, exchange, price);
      product.setStatus(Product.Status.SOLD_OUT);
      product.completeTransaction(tradeItem, marketValue, user.getEmail(), currentDate);

      ExchangeHistory history = new ExchangeHistory(ownerId, user.getEmail());
      history.setDetails(String.format("Owner: %s | Owner Item: %s | Location: %s | Owner Price: %d | Date: %s | Buyer: %s| Buyer Item: %s | Buyer Price: %s "
          , ownerId, name, location, price, dateFormat.format(date), user.getEmail(), tradeItem, marketValue));

      exchangeRepository.createHistory(history);
      productRepository.updateProduct(productId, product);
      updateValuations(ownerId, user.getEmail());
      updateRating(ownerId);
      Toast.makeText(TransactionActivity.this, "Transaction Successful!", Toast.LENGTH_SHORT).show();
      startActivity(new Intent(TransactionActivity.this, HomepageActivity.class));

    }
    setErrors(tradeItem, marketValue);
  }

  /**
   * This method is used to access the rating of the owner of the product the transaction is being completed
   *
   * @param ownerId the email of the owner
   */
  protected void updateRating(String ownerId) {

    userDB.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        databaseRating = snapshot.child(ownerKey).child("rating").getValue(Float.class);
        databaseNumRatings = snapshot.child(ownerKey).child("numRatings").getValue(Integer.class);

        databaseRating += rating;
        databaseNumRatings++;

        setNewRating(databaseRating, databaseNumRatings);
        userDB.removeEventListener(this);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }

  /**
   * This method is used to set the updated ratings into the database of the owner
   *
   * @param rating new rating total
   * @param num    new number of ratings
   */
  protected void setNewRating(float rating, int num) {
    userDB.child(ownerKey).child("rating").setValue(rating);
    userDB.child(ownerKey).child("numRatings").setValue(num);
  }

  /**
   * This method is used to adjust the P and R valuations of both the receiver and provider in question
   *
   * @param ownerId ID of the owner of the product in question
   * @param buyerId ID of the buyer who wants to trade for the product
   */
  protected void updateValuations(String ownerId, String buyerId) {
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

  /**
   * This method is used to change the values in the Users database according to the price of the provided
   * item and the received item accordingly
   *
   * @param ownerPVal New provider value of owner after trade
   * @param ownerRVal New receiver value of owner after trade
   * @param buyerPVal New provider value of buyer after trade
   * @param buyerRVal New receiver value of buyer after trade
   */
  protected void setValuations(int ownerPVal, int ownerRVal, int buyerPVal, int buyerRVal) {
    userDB.child(ownerKey).child("PValuation").setValue(ownerPVal);
    userDB.child(ownerKey).child("RValuation").setValue(ownerRVal);
    userDB.child(buyerKey).child("PValuation").setValue(buyerPVal);
    userDB.child(buyerKey).child("RValuation").setValue(buyerRVal);
    System.out.println(ownerPVal + " " + ownerRVal + "\n" + buyerPVal + " " + buyerRVal);
    user.setpValuation(buyerPVal);
    user.setrValuation(buyerRVal);
  }

  /**
   * This method is used to set the error labels on the page according to if the user has entered
   * valid information in the form fields
   *
   * @param productName name of the product which must be non-empty
   * @param marketVal   value of product which must be non-empty and a positive integer
   */
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
    if (!isRatingSet()) {
      ratingError = getString(R.string.RATING_ERROR);
    }

    nameErrorText.setText(pNameError);
    valueErrorText.setText(marketError);
    ratingErrorText.setText(ratingError);
  }

  /**
   * This method is used to assign every view component on the screen to a variable to be used
   */
  protected void findViewComponents() {
    productNameText = findViewById(R.id.transactionItemEditText);
    approxValText = findViewById(R.id.transactionApproxEditText);
    ratingBar = findViewById(R.id.transactionRatingBar);

    nameErrorText = findViewById(R.id.transactionNameError);
    valueErrorText = findViewById(R.id.transactionMarketError);
    ratingErrorText = findViewById(R.id.transactionRatingError);

    backBtn = findViewById(R.id.transactionBackBtn);
    confirmBtn = findViewById(R.id.transactionConfirmBtn);
  }

  /**
   * This method is used to assign all the buttons on the screen to onClick methods within the code
   */
  protected void setOnClickMethods() {
    confirmBtn.setOnClickListener(this::confirmButtonOnClick);
    backBtn.setOnClickListener(this::backButtonOnClick);
    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        rating = v;
      }
    });
  }

  public String getTradeItem() {
    return productNameText.getText().toString().trim();
  }

  public String getMarketValue() {
    return approxValText.getText().toString().trim();
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  /**
   * This method is used to validate the product name entered by user
   *
   * @param name product name entered by user
   * @return true if valid, false otherwise
   */
  public boolean isProductNameValid(String name) {
    return !name.isEmpty();
  }

  /**
   * This method is used to validate the market value entered by user
   *
   * @param val market value entered by user
   * @return true if valid, false otherwise
   */
  public boolean isValidMarketValue(String val) {
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

  /**
   * This method is used to validate the rating set by user
   *
   * @return true if rating has been set, false otherwise (invalid)
   */
  public boolean isRatingSet() {
    return rating > 0;
  }


}