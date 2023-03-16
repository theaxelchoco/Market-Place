package com.example.group17project;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import com.example.group17project.R;



import androidx.core.content.ContextCompat;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MapsEspressoTest {

    @Test
    public void mapDisplayedTest() {
        // Verify that the map fragment is displayed
        Espresso.onView(withId(R.id.map))
                .check(matches(isDisplayed()));
    }
        @Rule
        public ActivityScenarioRule<MapsActivity> activityScenarioRule =
                new ActivityScenarioRule<>(MapsActivity.class);

        /*
        @Test
        public void getLocationPermission_granted() {
            ActivityScenario<MapsActivity> activityScenario = activityScenarioRule.getScenario();

            // Grant location permissions
            Espresso.onView(ViewMatchers.withId(R.id.button_allow))
                    .check(matches(isDisplayed()))
                    .perform(click());

            // Verify that the location permissions were granted and the map was initialized
            Espresso.onView(ViewMatchers.withText(R.string.permissions_granted))
                    .check(matches(isDisplayed()));
        }

    @Test
    public void getLocationPermission_denied() {
        ActivityScenario<MapsActivity> activityScenario = activityScenarioRule.getScenario();

        // Deny location permissions
        Espresso.onView(ViewMatchers.withId(R.id.button_deny))
                .check(matches(isDisplayed()))
                .perform(click());

        // Verify that the location permissions were denied and the map was not initialized
        Espresso.onView(ViewMatchers.withText(R.string.permissions_denied))
                .check(matches(isDisplayed()));
    }
         */

    @Test
    public void checkSearchFunctionality() {
        // Perform a search
        onView(withId(R.id.input_search)).perform(click());
        onView(withId(R.id.input_search)).perform(typeText("New York"), pressImeActionButton());

        // Check if the search location is added as a marker on the map
        onView(withText("New York")).check(matches(isDisplayed()));
    }

    @Test
    public void checkDeviceLocation() {
        // Wait for the device location to be found
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the device location is added as a marker on the map
        onView(withText("current location")).check(matches(isDisplayed()));
    }
}
