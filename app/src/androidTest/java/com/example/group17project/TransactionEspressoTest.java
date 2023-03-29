package com.example.group17project;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertEquals;


import android.content.Context;

import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.group17project.Homepages.HomepageActivity;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)

public class TransactionEspressoTest {

    @Rule
    public ActivityScenarioRule<HomepageActivity> myRule = new ActivityScenarioRule<>(HomepageActivity.class);
    private static ProductRepository productRepo;
    Product product;

    @BeforeClass
    public static void setUpRepo(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
        productRepo = new ProductRepository(database, false);
    }

    @Before
    public void setUp() throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.set(2023, 3, 14);
        product = new Product("Test Transaction", "espresso@dal.ca", "test description", cal, "Mobile Phones", "Halifax", "Clothes", 100);
        productRepo.createProduct(product);

        Thread.sleep(3000);

    }

    @After
    public void tearDown() throws Exception{
        productRepo.deleteProduct(product.getProductID());
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.group17project", appContext.getPackageName());
    }

    @Test
    public void testTapOnTransaction(){
        onData(anything()).inAdapterView(withId(R.id.searchResultList)).atPosition(0).perform(click());
        onView(withId(R.id.rExpandedBuyBtn)).perform(click());
        onView(withId(R.id.transactionConfirmBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testTapOnTransactionBackButton(){
        onData(anything()).inAdapterView(withId(R.id.searchResultList)).atPosition(0).perform(click());
        onView(withId(R.id.rExpandedBuyBtn)).perform(click());
        onView(withId(R.id.transactionBackBtn)).perform(click());
        onView(withId(R.id.rExpandedBuyBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testTransactionSuccessful(){
        onData(anything()).inAdapterView(withId(R.id.searchResultList)).atPosition(0).perform(click());
        onView(withId(R.id.rExpandedBuyBtn)).perform(click());
        onView(withId(R.id.transactionItemEditText)).perform(typeText("Trade Item"), closeSoftKeyboard());
        onView(withId(R.id.transactionApproxEditText)).perform(typeText("80"), closeSoftKeyboard());
        onView(withId(R.id.transactionRatingBar)).perform(click());
        onView(withId(R.id.transactionConfirmBtn)).perform(click());
        onView(withId(R.id.searchResultList)).check(matches(isDisplayed()));
    }

    @Test
    public void testTransactionInvalidName(){
        onData(anything()).inAdapterView(withId(R.id.searchResultList)).atPosition(0).perform(click());
        onView(withId(R.id.rExpandedBuyBtn)).perform(click());
        onView(withId(R.id.transactionItemEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.transactionApproxEditText)).perform(typeText("80"), closeSoftKeyboard());
        onView(withId(R.id.transactionRatingBar)).perform((click()));
        onView(withId(R.id.transactionConfirmBtn)).perform(click());
        onView(withId(R.id.transactionNameError)).check(matches(withText(R.string.PRODUCT_NAME_ERROR)));
    }

    @Test
    public void testTransactionInvalidApproxEmpty(){
        onData(anything()).inAdapterView(withId(R.id.searchResultList)).atPosition(0).perform(click());
        onView(withId(R.id.rExpandedBuyBtn)).perform(click());
        onView(withId(R.id.transactionItemEditText)).perform(typeText("Trade Item"), closeSoftKeyboard());
        onView(withId(R.id.transactionApproxEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.transactionRatingBar)).perform(click());
        onView(withId(R.id.transactionConfirmBtn)).perform(click());
        onView(withId(R.id.transactionMarketError)).check(matches(withText(R.string.EMPTY_MARKET_VAL)));
    }

    @Test
    public void testTransactionInvalidApproxNotInteger(){
        onData(anything()).inAdapterView(withId(R.id.searchResultList)).atPosition(0).perform(click());
        onView(withId(R.id.rExpandedBuyBtn)).perform(click());
        onView(withId(R.id.transactionItemEditText)).perform(typeText("Trade Item"), closeSoftKeyboard());
        onView(withId(R.id.transactionApproxEditText)).perform(typeText("12.3"), closeSoftKeyboard());
        onView(withId(R.id.transactionRatingBar)).perform(click());
        onView(withId(R.id.transactionConfirmBtn)).perform(click());
        onView(withId(R.id.transactionMarketError)).check(matches(withText(R.string.MARKET_VAL_NEED_INT_ERROR)));
    }

    @Test
    public void testTransactionNoRating(){
        onData(anything()).inAdapterView(withId(R.id.searchResultList)).atPosition(0).perform(click());
        onView(withId(R.id.rExpandedBuyBtn)).perform(click());
        onView(withId(R.id.transactionItemEditText)).perform(typeText("Trade Item"), closeSoftKeyboard());
        onView(withId(R.id.transactionApproxEditText)).perform(typeText("80"), closeSoftKeyboard());
        onView(withId(R.id.transactionConfirmBtn)).perform(click());
        onView(withId(R.id.transactionRatingError)).check(matches(withText(R.string.RATING_ERROR)));
    }


}

