package com.example.group17project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity implements View.OnClickListener {


    DatabaseReference databaseRef = null;
    Toast loginToast;
    Toast userExistToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(this);

        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");


        //Used for testing and constant existing user. Remove during actual app launch
        databaseRef.child("users").removeValue();
        databaseRef.child("users").child("testUsed@dal,ca").child("password").setValue("AbC123");


    }

    public void backButton(View view){
        Intent i = new Intent(Signup.this, LoginLanding.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        String email = getEmail();
        String password = getPassword();
        String confirmPassword = getConfirmPassword();
        String errorMessage = "";

        if(isEmptyEmail(email)){
            errorMessage = getResources().getString(R.string.EMPTY_EMAIL).trim();

        }
        else if(!isValidEmailAddress(email)){
            errorMessage = getResources().getString(R.string.INVALID_EMAIL).trim();

        }
        else if(isEmptyPassword(password)){
            errorMessage = getResources().getString(R.string.EMPTY_PASSWORD).trim();

        }
        else if(passwordTooShort(password)){
            errorMessage = getResources().getString(R.string.SHORT_PASSWORD).trim();

        }
        else if(!isValidPassword(password)){
            errorMessage = getResources().getString(R.string.INVALID_PASSWORD).trim();

        }
        else if(isEmptyConfirmPassword(confirmPassword)){
            errorMessage = getResources().getString(R.string.EMPTY_PASSWORD).trim();

        }
        else if(!isPasswordMatch(password,confirmPassword)){
            errorMessage = getResources().getString(R.string.DIFFERENT_PASSWORDS).trim();

        }
        else{
            databaseRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(encodeUserEmail(email))){
                        userExistToast = Toast.makeText(Signup.this, R.string.USED_EMAIL, Toast.LENGTH_SHORT);
                        userExistToast.show();
                    }
                    else{
                        databaseRef.child("users").child(encodeUserEmail(email)).child("password").setValue(password);
                        loginToast = Toast.makeText(Signup.this, "Sign up Successfully! Login now!", Toast.LENGTH_SHORT);
                        loginToast.show();
                        Intent i = new Intent(Signup.this, LoginLanding.class);
                        startActivity(i);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });

        }
        setMessage(errorMessage);
    }

    static String encodeUserEmail(String email){
        return email.replace(".", ",");
    }

    static String decodeUserEmail(String email){
        return email.replace(",", ".");
    }

    //methods for checking matched-pair
    protected String getEmail(){
        EditText email =findViewById(R.id.emailReg);
        return email.getText().toString().trim();
    }

    protected String getPassword(){
        EditText password =findViewById(R.id.passwordReg);
        return password.getText().toString().trim();
    }

    protected String getConfirmPassword(){
        EditText confirmPassword =findViewById(R.id.passwordConfirm);
        return confirmPassword.getText().toString().trim();
    }

    protected boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }

    protected boolean isEmptyPassword(String password){
        return password.isEmpty();
    }

    protected boolean isEmptyConfirmPassword(String confirmPassword){
        return confirmPassword.isEmpty();
    }

    protected boolean isValidEmailAddress(String email){
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected boolean isValidPassword(String password){
        return password.matches("[A-Za-z0-9]*");
    }

    protected boolean passwordTooShort(String password){
        return password.length() < 6;
    }
    protected boolean isPasswordMatch(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    protected void setMessage(String message){
        TextView errorLabel = findViewById(R.id.errorLabel);
        errorLabel.setText(message);
    }


}