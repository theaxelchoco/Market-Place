package com.example.group17project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        String email = getEmail();
        String password = getPassword();
        String errorMessage = "";

        if(isEmptyEmail(email)){
            errorMessage = getResources().getString(R.string.EMPTY_EMAIL_LOGIN).trim();
        }
        else if(!isValidEmailAddress(email)){
            errorMessage = getResources().getString(R.string.INVALID_EMAIL).trim();
        }
        else if(isEmptyPassword(password)){
            errorMessage = getResources().getString(R.string.EMPTY_PASSWORD_LOGIN).trim();
        }

        setMessage(errorMessage);
    }


    protected void setMessage(String message){
        TextView errorLabel = findViewById(R.id.errorLblLogin);
        errorLabel.setText(message);
    }

    protected String getEmail(){
        EditText email =findViewById(R.id.emailLogin);
        return email.getText().toString().trim();
    }

    protected String getPassword(){
        EditText password =findViewById(R.id.passwordLogin);
        return password.getText().toString().trim();
    }

    protected boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }

    protected boolean isValidEmailAddress(String email){
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected boolean isEmptyPassword(String password){
        return password.isEmpty();
    }
}