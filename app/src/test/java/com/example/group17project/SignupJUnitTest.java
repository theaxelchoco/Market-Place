package com.example.group17project;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;
import com.example.group17project.Signup;

public class SignupJUnitTest {

    static Signup signup;

    @BeforeClass
    public static void setup() throws Exception{
        signup = new Signup();
    }

    @AfterClass
    public static void tearDown() throws Exception{
        System.gc();
    }
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
        assertFalse(signup.isValidPassword("abc!de"));
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
    public void checkIfPasswordShort(){
        assertTrue(signup.passwordTooShort("a"));
        assertFalse(signup.passwordTooShort("abcdef"));
    }

}