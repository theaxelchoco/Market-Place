package com.example.group17project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.example.group17project.Signup;

public class SignupJUnitTest {
    static Signup signup = new Signup();
    @Test
    public void checkIfEmailEmpty() {
        assertTrue(signup.isEmptyEmail(""));
        assertFalse(signup.isEmptyEmail("123@dal.ca"));
    }

    @Test
    public void checkIfEmailValid() {
        assertTrue(signup.isValidEmailAddress("qx748086@dal.ca"));
        assertFalse(signup.isValidEmailAddress("qx748086dal.ca"));
    }

    @Test
    public void checkIfPasswordEmpty() {
        assertTrue(signup.isEmptyPassword(""));
        assertFalse(signup.isEmptyPassword("abc12345"));
    }

    @Test
    public void checkIfPasswordValid() {
        assertFalse(signup.isValidPassword("12345"));
        assertTrue(signup.isValidPassword("123456"));
    }

    @Test
    public void checkIfConfirmPasswordEmpty() {
        assertTrue(signup.isEmptyConfirmPassword(""));
        assertFalse(signup.isEmptyConfirmPassword("abc12345"));
    }

    @Test
    public void checkIfPasswordMatch() {
        assertTrue(signup.isPasswordMatch("123456","123456"));
        assertFalse(signup.isPasswordMatch("abc12345","ABC"));
    }

    @Test
    public void checkIfEmailIsAlreadyUsed() {
        assertTrue(signup.isEmailAlreadyUsed("testUsed@dal.ca"));
    }
}