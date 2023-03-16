package com.example.group17project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class AddProductEspressoTest {
    @Rule
    public ActivityScenarioRule<AddProductActivity> myRule = new ActivityScenarioRule<>(AddProductActivity.class);

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
    public void checkIfAddProductPageIsVisible(){
        onView(withId(R.id.productNameEditText)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.description_edittext)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.place_of_exchange_edittext)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.market_value_edittext)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void addProductSuccessfully(){
        onView(withId(R.id.productNameEditText)).perform(ViewActions.typeText("Iphone XR"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.description_edittext)).perform(ViewActions.typeText("working fine, screen has minor damage"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.addProdScroll)).perform(ViewActions.closeSoftKeyboard(), ViewActions.swipeUp(), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.place_of_exchange_edittext)).perform(ViewActions.typeText("Halifax"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.market_value_edittext)).perform(ViewActions.typeText("80"), ViewActions.closeSoftKeyboard());


        onView(withId(R.id.submit_button)).perform(click());
        onView(withId(R.id.addProductBtn)).check(matches(withText("Add Product")));
    }

    @Test
    public void emptyProductName(){
        onView(withId(R.id.productNameEditText)).perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.description_edittext)).perform(ViewActions.typeText("working fine, screen has minor damage"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.addProdScroll)).perform(ViewActions.closeSoftKeyboard(), ViewActions.swipeUp(), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.place_of_exchange_edittext)).perform(ViewActions.typeText("Halifax"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.market_value_edittext)).perform(ViewActions.typeText("80"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.submit_button)).perform(click());
        onView(withId(R.id.productNameErrorLabel)).check(matches(withText(R.string.PRODUCT_NAME_ERROR)));
    }

    @Test
    public void emptyDescription(){
        onView(withId(R.id.productNameEditText)).perform(ViewActions.typeText("Iphone XR"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.description_edittext)).perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.addProdScroll)).perform(ViewActions.closeSoftKeyboard(), ViewActions.swipeUp(), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.place_of_exchange_edittext)).perform(ViewActions.typeText("Halifax"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.market_value_edittext)).perform(ViewActions.typeText("80"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.submit_button)).perform(click());
        onView(withId(R.id.addProductBtn)).check(matches(withText("Add Product")));
    }

    @Test
    public void emptyPlaceOfExchange(){
        onView(withId(R.id.productNameEditText)).perform(ViewActions.typeText("Iphone XR"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.description_edittext)).perform(ViewActions.typeText("working fine, screen has minor damage"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.addProdScroll)).perform(ViewActions.closeSoftKeyboard(), ViewActions.swipeUp(), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.place_of_exchange_edittext)).perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.market_value_edittext)).perform(ViewActions.typeText("80"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.submit_button)).perform(click());
        onView(withId(R.id.exchangeErrorLbl)).check(matches(withText(R.string.EXCHANGE_PLACE_ERROR)));
    }

    @Test
    public void emptyMarketValue(){
        onView(withId(R.id.productNameEditText)).perform(ViewActions.typeText("Iphone XR"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.description_edittext)).perform(ViewActions.typeText("working fine, screen has minor damage"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.addProdScroll)).perform(ViewActions.closeSoftKeyboard(), ViewActions.swipeUp(), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.place_of_exchange_edittext)).perform(ViewActions.typeText("Halifax"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.market_value_edittext)).perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.submit_button)).perform(click());
        onView(withId(R.id.marketValueErrorLbl)).check(matches(withText(R.string.EMPTY_MARKET_VAL)));
    }

    @Test
    public void invalidMarketValue(){
        onView(withId(R.id.productNameEditText)).perform(ViewActions.typeText("Iphone XR"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.description_edittext)).perform(ViewActions.typeText("working fine, screen has minor damage"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.addProdScroll)).perform(ViewActions.closeSoftKeyboard(), ViewActions.swipeUp(), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.place_of_exchange_edittext)).perform(ViewActions.typeText("Halifax"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.market_value_edittext)).perform(ViewActions.typeText("eighty"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.submit_button)).perform(click());
        onView(withId(R.id.marketValueErrorLbl)).check(matches(withText(R.string.MARKET_VAL_NEED_INT_ERROR)));
    }



}
