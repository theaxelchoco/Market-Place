package com.example.group17project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        loginButton.setOnClickListener(this::onClick);

        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");

    }

    public void signupButtonOnClick(View view){
        Intent i = new Intent(LoginLanding.this, Signup.class);
        startActivity(i);
    }
    public void onClick(View view){

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