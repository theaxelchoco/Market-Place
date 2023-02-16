package com.example.group17project;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)

public class LoginEspressoTest {

    @Rule
    public ActivityScenarioRule<LoginLanding> myRule = new ActivityScenarioRule<>(LoginLanding.class);

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
    public void checkIfLoginPageIsVisible(){
        onView(withId(R.id.emailLogin)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.passwordLogin)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.errorLblLogin)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfEmailIsEmpty(){
        onView(withId(R.id.emailLogin)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.passwordLogin)).perform(typeText("AbC123"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.errorLblLogin)).check(matches(withText(R.string.EMPTY_EMAIL_LOGIN)));
    }

    @Test
    public void checkIfEmailIsInvalid(){
        onView(withId(R.id.emailLogin)).perform(typeText("abc.dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.passwordLogin)).perform(typeText("AbC123"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.errorLblLogin)).check(matches(withText(R.string.INVALID_EMAIL)));
    }

    @Test
    public void checkIfPasswordEmpty(){
        onView(withId(R.id.emailLogin)).perform(typeText("test@dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.passwordLogin)).perform(typeText(" "), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.errorLblLogin)).check(matches(withText(R.string.EMPTY_PASSWORD_LOGIN)));
    }

    @Test
    public void testCorrectCredentialsLogin() {
        // Enter the correct email address
        onView(withId(R.id.emailLogin)).perform(typeText("testUsed@dal.ca"), closeSoftKeyboard());

        // Enter the correct password
        onView(withId(R.id.passwordLogin)).perform(typeText("AbC123"), closeSoftKeyboard());

        // Tap the login button
        onView(withId(R.id.loginBtn)).perform(click());

        // Check if the application opens with the associated profile (currently MainActivity on successful login)
        onView(withId(R.id.mainPageText)).check(matches(withText("test")));
    }

    @Test
    public void testIncorrectCredentialsLogin() {
        // Enter an incorrect email address
        onView(withId(R.id.emailLogin)).perform(typeText("testUsed@dal.ca"), closeSoftKeyboard());

        // Enter an incorrect password
        onView(withId(R.id.passwordLogin)).perform(typeText("xxxxxx"), closeSoftKeyboard());

        // Tap the login button
        onView(withId(R.id.loginBtn)).perform(click());

        // Check if an error message is displayed
        onView(withId(R.id.errorLblLogin)).check(matches(withText("")));

    }

    @Test
    public void checkIfNewLogin(){
        onView(withId(R.id.emailLogin)).perform(typeText("newTest@dal.ca"), closeSoftKeyboard());

        // Enter an incorrect password
        onView(withId(R.id.passwordLogin)).perform(typeText("abc123"), closeSoftKeyboard());

        // Tap the login button
        onView(withId(R.id.loginBtn)).perform(click());

        // Check if an error message is displayed
        onView(withId(R.id.errorLblLogin)).check(matches(withText("")));
    }

}
