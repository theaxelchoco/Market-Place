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

import android.content.Context;
import android.support.test.espresso.ViewAction;
import android.util.Log;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import com.example.group17project.Homepages.HomepageActivity;
import com.example.group17project.Homepages.LoginLanding;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ChatEspressoTest {
    private static final String USER1_EMAIL = "ta3130@dal.ca";
    private static final String USER1_PASSWORD = "ta3130";
    private static final String USER2_EMAIL = "test@dal.ca";
    private static final String USER2_PASSWORD = "abc123";
    private static final String CHAT_MESSAGE = "hello this is message 1";

    @Rule
    public ActivityScenarioRule<LoginLanding> activityScenarioRule =
            new ActivityScenarioRule<>(LoginLanding.class);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.group17project", appContext.getPackageName());
    }

    @Test
    public void chatBackBtnNavbar(){
        onView(withId(R.id.emailLogin)).perform(typeText("hello"), closeSoftKeyboard());
        onView(withId(R.id.passwordLogin)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
    }
    @Test
    public void testSendAndReceiveChatMessage() {
        // Log in as user1
        onView(withId(R.id.emailLogin)).perform(typeText(USER1_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.passwordLogin)).perform(typeText(USER1_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());

        // Navigate to the messages tab and click on the chat with user2
        onView(withId(R.id.drawer_layout)).perform((ViewAction) open());
        onView(withId(R.id.navigation_view)).perform((ViewAction) navigateTo(R.id.nav_messages));
        onView(withId(R.id.usersListView)).perform(
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
        onView(withId(R.id.usersListView)).perform((ViewAction) actionOnItem(
                hasDescendant(withText("ta3130@dal.ca")), (androidx.test.espresso.ViewAction) click()));

        // Check if message 1 is visible
//        onView(withId(R.id.chatRecyclerView)).perform(ViewActions.scrollTo(hasDescendant(withText("hello this is message 1"))));
    }
}