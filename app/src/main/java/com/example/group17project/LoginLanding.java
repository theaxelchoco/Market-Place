package com.example.group17project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginLanding extends AppCompatActivity {

    DatabaseReference databaseRef = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_landing);

        Button signupButton = findViewById(R.id.signup);
        Button loginButton = findViewById(R.id.loginBtn);

        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");

    }

    protected boolean isEmptyEmail(String email){

        return false;
    }

    protected boolean isValidEmailAddress(String email){

        return false;
    }

    protected boolean isEmptyPassword(String password){

        return false;
    }
}