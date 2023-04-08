/*
Signup code
Group 17
*/

package com.example.group17project.Homepages;

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

import com.example.group17project.R;
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
        Button backButton = findViewById(R.id.backButton);
        signupButton.setOnClickListener(this);
        backButton.setOnClickListener(this::backButton);

        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");




    }

    /**
     * Onclick method for back button, returns user back to login screen
     * @param view view on screen
     */
    public void backButton(View view){
        Intent i = new Intent(Signup.this, LoginLanding.class);
        startActivity(i);
    }

    /**
     * Onclick method for the sign up button. Performs validation and displays and error if unsuccessful account creation.
     * If good credentials, user will be created and sent to login page to log in
     * @param view view on screen
     */
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
                        databaseRef.child("users").child(encodeUserEmail(email)).child("RValuation").setValue(0);
                        databaseRef.child("users").child(encodeUserEmail(email)).child("PValuation").setValue(0);
                        databaseRef.child("users").child(encodeUserEmail(email)).child("rating").setValue(0);
                        databaseRef.child("users").child(encodeUserEmail(email)).child("numRatings").setValue(0);
                        loginToast = Toast.makeText(Signup.this, "Sign up Successfully! Login now!", Toast.LENGTH_SHORT);
                        loginToast.show();
                        Intent i = new Intent(Signup.this, LoginLanding.class);
                        startActivity(i);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //This method should not do anything if cancelled, hence why it is empty
                }

            });

        }
        setMessage(errorMessage);
    }

    /**
     * Method used to replace periods in email with commas so firebase database can use them as keys
     * @param email email with periods
     * @return email with commas in place of periods
     */
    public static String encodeUserEmail(String email){
        return email.replace(".", ",");
    }


    //getters for various textViews
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

    /**
     * This method is used to see if the email is empty
     * @param email to be validated
     * @return true if empty, false otherwise
     */
    public boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }

    /**
     * This method is used to see if the password is empty
     * @param password to be validated
     * @return true if empty, false otherwise
     */
    public boolean isEmptyPassword(String password){
        return password.isEmpty();
    }

    /**
     * This method is used to see if the confirm password is empty
     * @param confirmPassword to be validated
     * @return true if empty, false otherwise
     */
    public boolean isEmptyConfirmPassword(String confirmPassword){
        return confirmPassword.isEmpty();
    }

    /**
     * This method is used to check if the email is valid (xxx@xx.xx) for example
     * @param email to be validated
     * @return true if valid, false otherwise
     */
    public boolean isValidEmailAddress(String email){
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * This method is used to check if the password is valid, can only use alphanumeric characters
     * @param password to be validated
     * @return true if valid, false otherwise
     */
    public boolean isValidPassword(String password){
        return password.matches("[A-Za-z0-9]*");
    }

    /**
     * This method is used to check if the password is too short, must be at least 6 chars
     * @param password to be validated
     * @return true if short, false otherwise
     */
    public boolean passwordTooShort(String password){
        return password.length() < 6;
    }

    /**
     * this method is used to check if the password and confirm password are the same
     * @param password to be compared
     * @param confirmPassword to be compared
     * @return true if same, false otherwise
     */
    public boolean isPasswordMatch(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    /**
     * Method used to set the error label on the sign up screen
     * @param message error message to be displayed
     */
    protected void setMessage(String message){
        TextView errorLabel = findViewById(R.id.errorLabelReg);
        errorLabel.setText(message);
    }


}