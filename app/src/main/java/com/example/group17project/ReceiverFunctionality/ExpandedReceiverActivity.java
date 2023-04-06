/*
Expanded Receiver code
Group 17
*/

package com.example.group17project.ReceiverFunctionality;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.group17project.Homepages.ChatActivity;
import com.example.group17project.Homepages.HomepageActivity;
import com.example.group17project.Homepages.LoginLanding;
import com.example.group17project.R;
import com.example.group17project.utils.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExpandedReceiverActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView typeView;
    private TextView descView;


    private Button backBtn;
    private Button contactBtn;
    private Button buyBtn;
    private String productId;
    private int price;
    private long dateVal;
    private String name;
    private String type;
    private String exchange;
    private String desc;
    private String location;
    private String ownerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_expanded_receiver);

        findViewComponents();
        setOnClickMethods();
        setViewFromIntentExtras();

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
            date = new Date(dateVal);
            price = intent.getIntExtra("price", 0);
            productId = intent.getStringExtra("productId");
            ownerId = intent.getStringExtra("ownerId");


            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.CANADA);
            String dateString = sdf.format(date);

            String typesString = "Product type: " + type + "\n" + "Preferred: " + exchange;
            String descTimePriceLoc = desc + "\n\n" + "Date Available: " + dateString + "\n" + "Estimated Value: " + price + "\n" + location;


            nameView.setText(name);
            typeView.setText(typesString);
            descView.setText(descTimePriceLoc);

        }
    }

    /**
     * Onclick method for back button, sends user back to receiver screen
     * @param view view on screen
     */
    public void backButtonOnClick(View view){
        Intent intent = new Intent(ExpandedReceiverActivity.this, HomepageActivity.class);
        startActivity(intent);
    }

    public void buyBtnOnClick(View view){
        Intent intent = new Intent(ExpandedReceiverActivity.this, TransactionActivity.class);
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
     * On click method for contact button, currently sends user back to receiver screen but in future will open user to user
     * communication screen
     * @param view view on screen
     */
    public void contactButtonOnClick(View view){
        String newCollection;
        String currentUser = LoginLanding.encodeUserEmail(User.getInstance().getEmail());
        String ownerEmail = LoginLanding.encodeUserEmail(ownerId);

        if (currentUser.compareTo(ownerEmail) <= 0) {
            newCollection = currentUser + "_" + ownerEmail;
        } else {
            newCollection = ownerEmail + "_" + currentUser;
        }
//       passing the chat collection from one activity to another
        Intent chatActivityIntent = new Intent(ExpandedReceiverActivity.this, ChatActivity.class);
        chatActivityIntent.putExtra(ChatActivity.CHAT_COLLECTION, newCollection);
        startActivity(chatActivityIntent);
    }


    protected void findViewComponents(){
        nameView = findViewById(R.id.rExpandedProductName);
        typeView = findViewById(R.id.rExpandedType);
        descView = findViewById(R.id.rExpandedDesc);

        backBtn = findViewById(R.id.rExpandedBackBtn);
        contactBtn = findViewById(R.id.rExpandedContactBtn);
        buyBtn = findViewById(R.id.rExpandedBuyBtn);

    }

    protected void setOnClickMethods(){
        backBtn.setOnClickListener(this::backButtonOnClick);
        contactBtn.setOnClickListener(this::contactButtonOnClick);
        buyBtn.setOnClickListener(this::buyBtnOnClick);
    }
}