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

import com.example.group17project.utils.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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
        else {

            databaseRef.child("users").addListenerForSingleValueEvent(new ValueEventListener(){

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot){

                    if(snapshot.hasChild(encodeUserEmail(email))){

                        String retrievePassword = snapshot.child(encodeUserEmail(email)).child("password").getValue(String.class);

                        if (retrievePassword.equals(password)){
                           Toast correctPasswordToast = Toast.makeText(LoginLanding.this,"Successfully Logged in", Toast.LENGTH_SHORT);
                            correctPasswordToast.show();
                            User.getInstance().setUserDetails(email);

                            startActivity(new Intent( LoginLanding.this, HomepageActivity.class));
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

        setMessage(errorMessage);
    }

    public static String encodeUserEmail(String email){
        return email.replace(".", ",");
    }

    public static String decodeUserEmail(String email){
        return email.replace(",", ".");
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