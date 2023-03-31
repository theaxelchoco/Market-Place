/*
Visualization code
Group 17
*/

package com.example.group17project.Homepages;

import static java.lang.String.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group17project.R;
import com.example.group17project.ReceiverFunctionality.AdvanceSearchActivity;
import com.example.group17project.utils.model.ExchangeAdaptor;
import com.example.group17project.utils.model.ExchangeHistory;
import com.example.group17project.utils.model.ListAdapter;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.User;
import com.example.group17project.utils.repository.ExchangeRepository;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class Visualization extends AppCompatActivity {

    FirebaseDatabase database = null;
    User user;
    private ListView historyList;
    private ArrayList<ExchangeHistory> searchList;
    private ExchangeAdaptor exchangeAdapter;
    private ExchangeRepository exchangeRepository;

    @SuppressLint("DefaultLocale")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.firebase_database_url));
        exchangeRepository = new ExchangeRepository(database,false);

        user = User.getInstance();
        searchList = new ArrayList<>();

        ExchangeHistory history = new ExchangeHistory(user.getEmail());
        history.setDetails(format("Owner: %s | ItemName: %s | Location: %s | Price: %d",user.getEmail(),"TestItem","The moon (Test)",200));

        exchangeRepository.createHistory(history);

        System.out.println("Visualization active");



        TextView userName = findViewById(R.id.user_name_textview);
        RatingBar rating = findViewById(R.id.user_rating_ratingbar);
        TextView valR = findViewById(R.id.value_received_textview);
        TextView valP = findViewById(R.id.value_provided_textview);



        userName.setText(getEmail());
        rating.setRating(user.getRating());
        valR.setText("Value Received: " + String.valueOf(user.getrValuation()));
        valP.setText("Value Provided: " + String.valueOf(user.getpValuation()));

        exchangeRepository.getDatabaseRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                searchList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    ExchangeHistory history = data.getValue(ExchangeHistory.class);

                    if (history.getOwnerId() != null && history.getOwnerId().equals(user.getEmail())){
                        searchList.add(history);
                    }
                }
                //when data changed, notify that
                exchangeAdapter.notifyDataSetChanged();
            }

            //error handler
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        exchangeAdapter = new ExchangeAdaptor(Visualization.this, searchList);
        System.out.println(searchList);

        historyList = findViewById(R.id.exchange_history_listview);
        historyList.setAdapter(exchangeAdapter);

    }
        protected String getEmail(){
        return encodeUserEmail(user.getEmail());
    }


    public static String encodeUserEmail(String email){
        return email.replace(".", ",");
    }

    public static String decodeUserEmail(String email){
        return email.replace(",", ".");
    }


}
