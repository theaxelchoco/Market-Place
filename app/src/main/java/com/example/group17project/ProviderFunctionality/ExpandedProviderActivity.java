/*
Expanded Provider code
Group 17
*/

package com.example.group17project.ProviderFunctionality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group17project.Homepages.HomepageActivity;
import com.example.group17project.Homepages.LoginLanding;
import com.example.group17project.R;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExpandedProviderActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView typeView;
    private TextView descView;
    private TextView ratingError;
    private RatingBar ratingBar;
    private TextView ratingTitle;
    private String name;
    private String type;
    private String exchange;
    private String desc;
    private String productId;
    private String location;
    private String buyerId;
    private String buyerKey;
    private long dateVal;
    private int price;

    private boolean available;
    private ProductRepository productRepository;

    private Button backBtn;
    private Button deleteBtn;
    private Button editBtn;
    private Button confirmBtn;

    private float rating = 0;
    private float databaseRating;
    private int databaseNumRatings;

    private DatabaseReference userDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_expanded);

        setUp();
        findViewComponents();
        setOnClickMethods();
        setViewFromIntentExtras();
        setViewFromStatus();

    }

    /**
     * This method is used to set the view according to whether the product is available or not. If available, user should
     * see details about the product but if already sold, user should be shown rating for the buyer
     */
    protected void setViewFromStatus(){
        if(available){
            ratingBar.setVisibility(View.GONE);
            ratingTitle.setVisibility(View.GONE);
            confirmBtn.setVisibility(View.GONE);
            ratingError.setVisibility(View.GONE);
        }
        else{
            typeView.setVisibility(View.GONE);
            descView.setVisibility(View.GONE);
            descView.setClickable(false);
            deleteBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);

            ratingTitle.setText("Rate User: " + buyerId);
        }
    }

    /**
     * Method used to set up connection to database to build a productRepository object
     */
    protected void setUp(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
        productRepository = new ProductRepository(database, false);
        userDB = database.getReference("users");
    }

    /**
     * This method is used to grab information passed from the last activity. Information about the clicked item will be displayed
     * in expanded format, based off the passed information
     */
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
            productId = intent.getStringExtra("productId");
            date = new Date(dateVal);
            price = intent.getIntExtra("price", 0);
            available = intent.getBooleanExtra("availability", true);
            if(!available){
                buyerId = intent.getStringExtra("buyerID");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.CANADA);
            String dateString = sdf.format(date);

            String typesString = "Product type: " + type + "\n" + "Preferred: " + exchange;
            String descTimePrice = desc + "\n\n" + "Date Available: " + dateString + "\n" + "Estimated Value: " + price;


            nameView.setText(name);
            typeView.setText(typesString);
            descView.setText(descTimePrice);

        }
    }

    /**
     * Onclick method for back button, sends user back to provider screen
     * @param view view on screen
     */
    public void backButtonOnClick(View view){
        Intent intent = new Intent(ExpandedProviderActivity.this, HomepageActivity.class);
        intent.putExtra("fragmentId", "provider");
        startActivity(intent);
    }

    /**
     * Onclick method for the delete button, accesses product repo to delete item selected
     * @param view view of screen
     */
    public void deleteButtonOnClick(View view){
        productRepository.deleteProduct(productId);
        backButtonOnClick(view);
    }

    /**
     * On click button for the confirm button, calls methods to assign rating to user and delete product
     * @param view view of the screen
     */
    private void confirmButtonOnClick(View view) {
        if(isRatingSet()){
            updateRating(buyerId);
            Toast.makeText(ExpandedProviderActivity.this, R.string.SUCCESSFUL_RATING, Toast.LENGTH_SHORT).show();
            deleteButtonOnClick(view);
        }
        else{
            setError();
        }
    }

    /**
     * Ths method is used to access the rating of the buyer within the database
     * @param buyer the email of the user who bought the particular item
     */
    protected void updateRating(String buyer){
        buyerKey = LoginLanding.encodeUserEmail(buyer);
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseRating = snapshot.child(buyerKey).child("rating").getValue(Float.class);
                databaseNumRatings = snapshot.child(buyerKey).child("numRatings").getValue(Integer.class);

                databaseRating += rating;
                databaseNumRatings++;

                setNewRating(databaseRating, databaseNumRatings);
                userDB.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //This method should not do anything if cancelled, hence why it is empty
            }
        });
    }

    /**
     * This method is used to replace the rating in the database with the new rating
     * @param rating new total rating number
     * @param num new number of ratings
     */
    protected void setNewRating(float rating, int num){
        userDB.child(buyerKey).child("rating").setValue(rating);
        userDB.child(buyerKey).child("numRatings").setValue(num);
    }

    /**
     * This method is used to set the error label if the user forgets to leave a rating
     */
    protected void setError(){
        ratingError.setText(getString(R.string.RATING_ERROR));
    }

    /**
     * Onclick method for the edit button, passes in information about the corresponding product so
     * the form is already filled out with the information, allowing user to pick and choose what to change
     * @param view view of screen
     */
    public void editButtonOnClick(View view){
        if(available){
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
        else{
            Toast.makeText(ExpandedProviderActivity.this, R.string.CANNOT_EDIT_SOLD, Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * This method is used to assign the view components to variables to be used
     */
    protected void findViewComponents(){
        nameView = findViewById(R.id.pExpandedProductName);
        typeView = findViewById(R.id.pExpandedType);
        descView = findViewById(R.id.pExpandedDesc);
        ratingError = findViewById(R.id.pExpandedRateError);

        backBtn = findViewById(R.id.pExpandedBackBtn);
        deleteBtn = findViewById(R.id.pExpandedDeleteBtn);
        editBtn = findViewById(R.id.pExpandedEditBtn);
        confirmBtn = findViewById(R.id.pExpandedConfirmBtn);

        ratingBar = findViewById(R.id.pExpandedRatingBar);
        ratingTitle = findViewById(R.id.pExpandedRateUserTitle);
    }

    /**
     * This method is used to set the onclick methods as well as the rating on change event
     */
    protected void setOnClickMethods(){
        backBtn.setOnClickListener(this::backButtonOnClick);
        deleteBtn.setOnClickListener(this::deleteButtonOnClick);
        editBtn.setOnClickListener(this::editButtonOnClick);
        confirmBtn.setOnClickListener(this::confirmButtonOnClick);

        ratingBar.setOnRatingBarChangeListener((ratingBar, v, b) -> rating = v);
    }

    /**
     * This method is used to see if a rating has been assigned from the user
     * @return returns a true if the rating is above 0, false otherwise
     */
    public boolean isRatingSet(){
        return rating > 0;
    }

    /**
     * This method is used to set a rating value for the buyer
     * @param rating the float number representing the rating
     */
    public void setRating(float rating){
        this.rating = rating;
    }
}