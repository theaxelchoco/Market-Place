package com.example.group17project;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.group17project.ReceiverFunctionality.AdvanceSearchActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AdvancedSearchEspressoTest {
  @Rule
  public ActivityScenarioRule<AdvanceSearchActivity> myRule = new ActivityScenarioRule<>(AdvanceSearchActivity.class);

  @Test
  public void checkIfAdvancedSearchButtonIsVisible() {
    onView(withId(R.id.advanceSearchSubmitButton)).check(matches(isDisplayed()));
    onView(withId(R.id.advanceSearchCancelButton)).check(matches(isDisplayed()));
    onView(withId(R.id.advanceSearchSaveButton)).check(matches(isDisplayed()));
  }

  @Test
  public void checkIfAdvancedSearchSpinnerIsVisible() {
    onView(withId(R.id.advanceSearchTypeSpinner)).check(matches(isDisplayed()));
    onView(withId(R.id.advanceSearchPrefExchangeSpinner)).check(matches(isDisplayed()));
  }

  @Test
  public void checkIfAdvancedSearchEditTextIsVisible() {
    onView(withId(R.id.advanceSearchValueFromEditView)).check(matches(isDisplayed()));
    onView(withId(R.id.advanceSearchValueToEditView)).check(matches(isDisplayed()));
    onView(withId(R.id.advanceSearchKeywordEditView)).check(matches(isDisplayed()));
  }
}
