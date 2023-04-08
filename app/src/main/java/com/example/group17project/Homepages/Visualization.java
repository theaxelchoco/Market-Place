/*
Visualization code
Group 17
*/

package com.example.group17project.Homepages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.ListView;
import android.widget.RatingBar;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.group17project.R;

import com.example.group17project.utils.adapters.ExchangeAdaptor;
import com.example.group17project.utils.model.ExchangeHistory;

import com.example.group17project.utils.model.User;
import com.example.group17project.utils.repository.ExchangeRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Visualization extends Fragment {

    FirebaseDatabase database = null;
    User user;
    private ListView historyList;
    private ArrayList<ExchangeHistory> searchList;
    private ExchangeAdaptor exchangeAdapter;
    private ExchangeRepository exchangeRepository;
    private int numberOfRatings;
    private float totalRating;
    private float averageRating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualization, container, false);

        TextView userName = view.findViewById(R.id.user_name_textview);
        RatingBar rating = view.findViewById(R.id.user_rating_ratingbar);
        TextView valR = view.findViewById(R.id.value_received_textview);
        TextView valP = view.findViewById(R.id.value_provided_textview);


        userName.setText(user.getEmail());

        //The rating that corresponds to a user will be their total rating divided by the number of ratings they have received
        numberOfRatings = user.getNumRatings();
        totalRating = user.getRating();
        averageRating = totalRating/ numberOfRatings;

        rating.setRating(averageRating);
        valR.setText("Value Received: " + String.valueOf(user.getrValuation()));
        valP.setText("Value Provided: " + String.valueOf(user.getpValuation()));

        historyList = view.findViewById(R.id.exchange_history_listview);
        historyList.setAdapter(exchangeAdapter);

        return view;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance(getResources().getString(R.string.firebase_database_url));
        exchangeRepository = new ExchangeRepository(database,false);

        user = User.getInstance();
        searchList = new ArrayList<>();


        exchangeRepository.getDatabaseRef().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                searchList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    ExchangeHistory history = data.getValue(ExchangeHistory.class);

                    //We only want to display the transactions that include the current user in the exchange history
                    if (history.getOwnerId() != null && (history.getOwnerId().equals(user.getEmail()) || history.getBuyerId().equals(user.getEmail()))){
                        searchList.add(history);
                    }
                }
                //when data changed, notify that
                exchangeAdapter.notifyDataSetChanged();
            }

            //error handler
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Method left empty because we don't handle onCancel event
            }
        });

        exchangeAdapter = new ExchangeAdaptor(getActivity(), searchList);

    }

    /**
     * This method is used to get the corresponding user email to display
     * @return email string
     */
    protected String getEmail(){
        return encodeUserEmail(user.getEmail());
    }


    /**
     * Method used to convert an email into a key in the database by replacing period with comma
     * @param email email to be translated
     * @return translated email string
     */
    public static String encodeUserEmail(String email){
        return email.replace(".", ",");
    }



}
