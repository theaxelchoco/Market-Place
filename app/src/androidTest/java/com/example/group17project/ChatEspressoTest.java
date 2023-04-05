package com.example.group17project;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static androidx.test.espresso.contrib.DrawerActions.open;
import static androidx.test.espresso.contrib.NavigationViewActions.navigateTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.scrollTo;

import android.content.Context;
import android.support.test.espresso.ViewAction;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.matcher.ViewMatchers.Visibility;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.group17project.Homepages.HomepageActivity;
import com.example.group17project.R;
import com.example.group17project.utils.FirebaseConstants;
import com.example.group17project.utils.model.Chat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Rule;
import org.junit.Test;

public class ChatEspressoTest {
    private static final String USER1_EMAIL = "ta3130@dal.ca";
    private static final String USER1_PASSWORD = "ta3130";
    private static final String USER2_EMAIL = "test@dal.ca";
    private static final String USER2_PASSWORD = "abc123";
    private static final String CHAT_MESSAGE = "hello this is message 1";

    @Rule
    public ActivityScenarioRule<HomepageActivity> activityScenarioRule =
            new ActivityScenarioRule<>(HomepageActivity.class);


    @Test
    public void testSendAndReceiveChatMessage() {
        // Log in as user1
        onView(withId(R.id.emailLogin)).perform(typeText(USER1_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.passwordLogin)).perform(typeText(USER1_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());

        // Navigate to the messages tab and click on the chat with user2
        onView(withId(R.id.drawer_layout)).perform((ViewAction) open());
        onView(withId(R.id.navigation_view)).perform((ViewAction) navigateTo(R.id.nav_messages));
        onView(withId(R.id.usersRV)).perform(
                (ViewAction) actionOnItem(hasDescendant(withText(USER2_EMAIL)), (androidx.test.espresso.ViewAction) click()));

        // Type a chat message and send it
        onView(withId(R.id.chatMessageET)).perform(typeText(CHAT_MESSAGE), closeSoftKeyboard());
        onView(withId(R.id.chatSendBtn)).perform(click());

        // Wait for the chat message to appear in the RecyclerView
        onView(withId(R.id.chatRecyclerView)).perform((ViewAction) RecyclerViewActions.scrollTo(hasDescendant(withText(CHAT_MESSAGE))));
        onView(withText(CHAT_MESSAGE)).check(matches(isDisplayed()));
        // Log out of user1
        onView(withId(R.id.drawer_layout)).perform((ViewAction) open());
        onView(withId(R.id.navigation_view)).perform((ViewAction) navigateTo(R.id.nav_logout));

        // Log in as user2
        onView(withId(R.id.emailLogin)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.passwordLogin)).perform(typeText("abc123"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());

        // Open messages tab
        onView(withId(R.id.drawer_layout)).perform((ViewAction) open());
        onView(withId(R.id.navigation_view)).perform((ViewAction) navigateTo(R.id.nav_messages));

        // Open chat with user1
        onView(withId(R.id.usersRV)).perform((ViewAction) actionOnItem(
                hasDescendant(withText("ta3130@dal.ca")), (androidx.test.espresso.ViewAction) click()));

        // Check if message 1 is visible
        onView(withId(R.id.chatRecyclerView)).perform(ViewActions.scrollTo(
                hasDescendant(withText("hello this is message 1"))));
    }
}