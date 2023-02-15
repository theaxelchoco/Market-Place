package com.example.group17project;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)

public class LoginEspressoTest {
    @Test
    public void testCorrectCredentialsLogin() {
        // Enter the correct email address
        onView(withId(R.id.email_edit_text)).perform(typeText("example@email.com"), closeSoftKeyboard());

        // Enter the correct password
        onView(withId(R.id.password_edit_text)).perform(typeText("password"), closeSoftKeyboard());

        // Tap the login button
        onView(withId(R.id.login_button)).perform(click());

        // Check if the application opens with the associated profile
        onView(withId(R.id.profile_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testIncorrectCredentialsLogin() {
        // Enter an incorrect email address
        onView(withId(R.id.email_edit_text)).perform(typeText("incorrect@email.com"), closeSoftKeyboard());

        // Enter an incorrect password
        onView(withId(R.id.password_edit_text)).perform(typeText("incorrect"), closeSoftKeyboard());

        // Tap the login button
        onView(withId(R.id.login_button)).perform(click());

        // Check if an error message is displayed
        onView(withId(R.id.error_text_view)).check(matches(withText("Invalid email/password. Please try again.")));

    }

}
