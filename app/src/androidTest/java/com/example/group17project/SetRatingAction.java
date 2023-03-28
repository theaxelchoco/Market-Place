package com.example.group17project;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.RatingBar;

import org.hamcrest.Matcher;

public class SetRatingAction implements ViewAction {
    private float rating;

    public SetRatingAction(float rating) {
        this.rating = rating;
    }

    @Override
    public Matcher<View> getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(RatingBar.class));
    }

    @Override
    public String getDescription() {
        return "Set rating on a RatingBar";
    }

    @Override
    public void perform(UiController uiController, View view) {
        RatingBar ratingBar = (RatingBar) view;
        ratingBar.setRating(rating);
    }
}
