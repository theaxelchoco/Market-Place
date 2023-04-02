package com.example.group17project;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertEquals;


import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.group17project.Homepages.Visualization;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.FirebaseDatabase;

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

public class VisualizationEspressoTest {
    @Rule
    //public ActivityScenarioRule<Visualization> myRule = new ActivityScenarioRule<>(Visualization.class);

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
    public void checkIfVisualizationPageIsVisible(){
        onView(withId(R.id.exchange_history_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.value_provided_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.value_received_textview)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfRatingbarIsVisible(){
        onView(withId(R.id.user_rating_ratingbar)).check(matches(isDisplayed()));
        onView(withId(R.id.user_name_textview)).check(matches(isDisplayed()));
    }


    @Test
    public void checkIfValueIsVisible(){
        onView(withId(R.id.exchange_history_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.value_received_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.value_provided_textview)).check(matches(isDisplayed()));
    }




}
