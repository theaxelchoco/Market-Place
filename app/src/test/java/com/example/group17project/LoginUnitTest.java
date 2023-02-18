package com.example.group17project;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LoginUnitTest {

    static LoginLanding login;

    @BeforeClass
    public static void setLogin() throws Exception{
        login = new LoginLanding();
    }

    @AfterClass
    public static void tearDown(){
        System.gc();
    }

    @Test
    public void checkIfEmailEmpty(){
        assertTrue(login.isEmptyEmail(""));
        assertFalse(login.isEmptyEmail("123@dal.ca"));
    }

    @Test
    public void checkIfEmailValid() {
        assertTrue(login.isValidEmailAddress("qx748086@dal.ca"));
        assertFalse(login.isValidEmailAddress("qx748086dal.ca"));
    }

    @Test
    public void checkIfPasswordEmpty() {
        assertTrue(login.isEmptyPassword(""));
        assertFalse(login.isEmptyPassword("abc12345"));
    }

}
