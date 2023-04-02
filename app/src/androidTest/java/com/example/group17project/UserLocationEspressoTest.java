package com.example.group17project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.location.Location;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import com.example.group17project.utils.UserLocation;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserLocationEspressoTest {

    @Rule
    public ActivityScenarioRule<UserLocation> activityScenarioRule = new ActivityScenarioRule<>(UserLocation.class);

    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    private CountingIdlingResource idlingResource;

    @Before
    public void setUp() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            idlingResource = new CountingIdlingResource("USER_LOCATION");
            IdlingRegistry.getInstance().register(idlingResource);
            idlingResource.increment();
            activity.getCurrentLocation();
            idlingResource.decrement();
        });
    }

    @Test
    public void testUserLocation() {
        // Define the expected location name
        String expectedLocationName = "Mountain View";

        // Obtain a reference to the UserLocation activity
        final UserLocation[] activity = {null};
        activityScenarioRule.getScenario().onActivity(a -> {
            activity[0] = a;
        });
        assertNotNull("Activity is null", activity[0]);

        // Wait for the location to be obtained
        Location location = null;
        for (int i = 0; i < 10; i++) {
            location = activity[0].getUserLocation();
            if (location != null) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertNotNull("Location is null", location);

        // Check the location name
        String actualLocationName = activity[0].getLocationName(location.getLatitude(), location.getLongitude());
        assertEquals(expectedLocationName, actualLocationName);
    }

    @After
    public void tearDown() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
