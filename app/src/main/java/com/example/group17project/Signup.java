package com.example.group17project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    private static final String url = "https://w23-csci3130-group-17-default-rtdb.firebaseio.com/";
    FirebaseDatabase database = null;
    DatabaseReference emailRef = null;
    DatabaseReference passwordRef = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(this);

        database = FirebaseDatabase.getInstance(url);
        emailRef = database.getReference("Email") ;
        passwordRef = database.getReference("Password");
    }

    @Override
    public void onClick(View view) {
        String email = getEmail();
        String password = getPassword();
        String confirmPassword = getConfirmPassword();
        String errorMessage;

        if(isEmptyEmail(email)){
            errorMessage = getResources().getString(R.string.EMPTY_EMAIL).trim();
            setMessage(errorMessage);
        }
        else if(!isValidEmailAddress(email)){
            errorMessage = getResources().getString(R.string.INVALID_EMAIL).trim();
            setMessage(errorMessage);
        }
        else if(isEmptyPassword(password)){
            errorMessage = getResources().getString(R.string.EMPTY_PASSWORD).trim();
            setMessage(errorMessage);
        }
        else if(isEmptyConfirmPassword(confirmPassword)){
            errorMessage = getResources().getString(R.string.EMPTY_PASSWORD).trim();
            setMessage(errorMessage);
        }
        else if(!isValidPassword(password)){
            errorMessage = getResources().getString(R.string.INVALID_PASSWORD).trim();
            setMessage(errorMessage);
        }
        else if(!isPasswordMatch(password,confirmPassword)){
            errorMessage = getResources().getString(R.string.DIFFERENT_PASSWORDS).trim();
            setMessage(errorMessage);
        }
        else{
            setMessage("Sign up Successfully!");
        }


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
        return password.length() >= 6 && password.matches("[A-Za-z0-9]*");
    }
    protected boolean isPasswordMatch(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    protected void setMessage(String message){
        TextView errorLabel = findViewById(R.id.errorLabel);
        errorLabel.setText(message);
    }

    //yet to be done
    protected Task<Void> saveEmailToFirebase(String email) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(url);
        DatabaseReference emailRef = database.getReference("Email");

        emailRef.setValue(email);
        return null;
    }

    protected Task<Void> savePasswordToFirebase(String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(url);
        DatabaseReference passwordRef = database.getReference("Password");

        passwordRef.setValue(password);
        return null;
    }

    protected boolean isEmailAlreadyUsed(String email){
        return false;
    }

}