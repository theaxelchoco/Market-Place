package com.example.group17project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoginUnitTest {
    @Test
    public void testCorrectCredentialsLogin() {
        // Create an instance of the login page
        LoginPage loginPage = new LoginPage();

        // Enter the correct email address
        loginPage.enterEmailAddress("example@email.com");

        // Enter the correct password
        loginPage.enterPassword("password");

        // Tap the login button
        loginPage.clickLoginButton();

        // Check if the application opens with the associated profile
        assertTrue(loginPage.isProfileDisplayed());
    }

    @Test
    public void testIncorrectCredentials() {
        LoginPage loginPage = new LoginPage();

        String email = "incorrect@email.com";
        String password = "incorrect";
        String errorMessage = loginPage.login(email, password);

        assertEquals("Invalid email/password. Please try again.", errorMessage);
    }
}
