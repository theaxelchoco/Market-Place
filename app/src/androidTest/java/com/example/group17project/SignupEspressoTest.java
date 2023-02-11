package com.example.group17project;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignupEspressoTest {

    @Rule
    public ActivityScenarioRule<Signup> myRule = new ActivityScenarioRule<>(Signup.class);

    @Before
    public void setUp() throws Exception{
    }

    @After
    public void tearDown() throws Exception{
    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.group17project", appContext.getPackageName());
    }
    @Test
    public void checkIfSignupPageIsVisible(){
        onView(withId(R.id.emailReg)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.passwordReg)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.passwordConfirm)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.errorLabel)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfEmailIsEmpty(){
        onView(withId(R.id.emailReg)).perform(typeText(""));
        onView(withId(R.id.passwordReg)).perform(typeText("AbC123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("AbC123"));
        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.errorLabel)).check(matches(withText(R.string.EMPTY_EMAIL)));
    }

    @Test
    public void checkIfEmailIsInvalid(){
        onView(withId(R.id.emailReg)).perform(typeText("abc.dal.ca"));
        onView(withId(R.id.passwordReg)).perform(typeText("AbC123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("AbC123"));
        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.errorLabel)).check(matches(withText(R.string.INVALID_EMAIL)));
    }

    @Test
    public void checkIfEmailIsAlreadyUsed(){
        onView(withId(R.id.emailReg)).perform(typeText("testUsed@dal.ca"));
        onView(withId(R.id.passwordReg)).perform(typeText("AbC123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("AbC123"));
        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.errorLabel)).check(matches(withText(R.string.USED_EMAIL)));
    }

    @Test
    public void checkIfPasswordEmpty(){
        onView(withId(R.id.emailReg)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.passwordReg)).perform(typeText(" "));
        onView(withId(R.id.passwordConfirm)).perform(typeText("AbC123"));
        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.errorLabel)).check(matches(withText(R.string.EMPTY_PASSWORD)));
    }

    @Test
    public void checkIfConfirmPasswordEmpty(){
        onView(withId(R.id.emailReg)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.passwordReg)).perform(typeText("AbC123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText(" "));
        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.errorLabel)).check(matches(withText(R.string.EMPTY_PASSWORD)));
    }

    @Test
    public void checkIfPasswordsNotMatching(){
        onView(withId(R.id.emailReg)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.passwordReg)).perform(typeText("AbC123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("DeF456"));
        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.errorLabel)).check(matches(withText(R.string.DIFFERENT_PASSWORDS)));
    }

    @Test
    public void checkIfPasswordInvalid(){
        onView(withId(R.id.emailReg)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.passwordReg)).perform(typeText("zzz"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("zzz"));
        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.errorLabel)).check(matches(withText(R.string.INVALID_PASSWORD)));
    }


}