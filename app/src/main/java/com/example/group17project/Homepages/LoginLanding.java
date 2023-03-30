/*
LoginLanding code
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
import com.example.group17project.utils.UserLocation;
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

        Button loginButton = findViewById(R.id.loginBtn);
        Button signupButton = findViewById(R.id.signup);
        signupButton.setOnClickListener(this::signupButtonOnClick);
        loginButton.setOnClickListener(this::onClick);

        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");

    }

    /**
     * Onclick method for signup button, sends user to the signup screen
     * @param view view on screen
     */
    public void signupButtonOnClick(View view){
        Intent i = new Intent(LoginLanding.this, Signup.class);
        startActivity(i);
    }

    /**
     * On click button for login button, validates the users credentials and displays errors if not working. Successful login
     * sends user to homepage
     * @param view view on screen
     */
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

                            int rVal = snapshot.child(encodeUserEmail(email)).child("RValuation").getValue(Integer.class);
                            int pVal = snapshot.child(encodeUserEmail(email)).child("PValuation").getValue(Integer.class);
                            float rating = snapshot.child(encodeUserEmail(email)).child("rating").getValue(Float.class);
                            int numRatings = snapshot.child(encodeUserEmail(email)).child("numRatings").getValue(Integer.class);
                            User.getInstance().setUserDetails(email, rVal, pVal, rating, numRatings);

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

    public static String decodeUserEmail(String email){
        return email.replace(",", ".");
    }

    /**
     * Method used to set the error label with a corresponding error message
     * @param message message to be set in error label
     */
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

    public boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }

    public boolean isValidEmailAddress(String email){
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isEmptyPassword(String password){
        return password.isEmpty();
    }
}