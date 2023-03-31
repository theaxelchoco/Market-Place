/*
Visualization code
Group 17
*/

package com.example.group17project.Homepages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group17project.R;
import com.example.group17project.ReceiverFunctionality.TransactionActivity;
import com.example.group17project.utils.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Visualization extends AppCompatActivity {

    DatabaseReference databaseRef = null;
    User user;
    TransactionActivity transactionActivity = new TransactionActivity();
    String PValuation = "0";
    String RValuation = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);
        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
        user = User.getInstance();
        String email = "test@dal.ca";

//        databaseRef.child("users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get the String value from the snapshot
//                PValuation = dataSnapshot.getValue(String.class);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle any errors that occur
//            }
//        });





        System.out.println(user.getEmail());
        System.out.println(getEmail());


        //System.out.println(email.getText().toString().trim());

        PValuation= String.valueOf(user.getpValuation());
        RValuation= String.valueOf(user.getrValuation());
        Button backButton = findViewById(R.id.VisualizationBackButton);
        backButton.setOnClickListener(this::backButton);

        TextView valueReceived = findViewById(R.id.value_received_textview);
        valueReceived.setText("Value Received: "+ RValuation);
        TextView valueProvided = findViewById(R.id.value_provided_textview);
        valueProvided.setText("Value Provided: "+ PValuation);
    }


    public void backButton(View view){
        Intent i = new Intent(Visualization.this, HomepageActivity.class);
        startActivity(i);
    }

    protected String getEmail(){
        return encodeUserEmail(user.getEmail());
    }


   /*
    protected int GetRating(){
        databaseRef.child("users").addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){

                if(snapshot.hasChild(encodeUserEmail(email))){

                    String retrievePassword = snapshot.child(encodeUserEmail(email)).child("password").getValue(String.class);

                    if (retrievePassword.equals(password)){
                        Toast correctPasswordToast = Toast.makeText(LoginLanding.this,"Successfully Logged in", Toast.LENGTH_SHORT);
                        correctPasswordToast.show();
                        User.getInstance().setUserDetails(email);

                        startActivity(new Intent( LoginLanding.this, UserLocation.class));
                    }
                    else{

                        Toast incorrectPasswordToast= Toast.makeText(LoginLanding.this,R.string.INCORRECT_PASSWORD_LOGIN, Toast.LENGTH_SHORT);
                        incorrectPasswordToast.show();
                    }

                }
                else {
                    Toast invalidEmailToast = Toast.makeText(LoginLanding.this, R.string.NEW_EMAIL_LOGIN, Toast.LENGTH_SHORT);
                    invalidEmailToast.show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    */

    public static String encodeUserEmail(String email){
        return email.replace(".", ",");
    }

    public static String decodeUserEmail(String email){
        return email.replace(",", ".");
    }


}
