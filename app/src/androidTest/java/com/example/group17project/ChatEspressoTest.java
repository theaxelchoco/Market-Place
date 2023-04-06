package com.example.group17project;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.group17project.Homepages.HomepageActivity;
import com.example.group17project.Homepages.LoginLanding;
import com.example.group17project.Homepages.UserChatFragment;
import com.example.group17project.ProviderFunctionality.ProviderFragment;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)

public class ChatEspressoTest {

    public ActivityScenario<LoginLanding> scenario = ActivityScenario.launch(LoginLanding.class);

    @Before
    public void setUp() throws Exception {
        onView(withId(R.id.emailLogin)).perform(typeText("test@dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.passwordLogin)).perform(typeText("abc123"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        Thread.sleep(3000);
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
    public void testSwapToMessageUsers() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_messages));
        onView(withId(R.id.usersListView)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickOnMessageUser(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_messages));
        onView(withId(R.id.usersListView)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.usersListView)).atPosition(0).perform(click());
        onView(withId(R.id.chatRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testChatBackButtonFromNav(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_messages));
        onView(withId(R.id.usersListView)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.usersListView)).atPosition(0).perform(click());
        onView(withId(R.id.chatRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.chatBackBtn)).perform(click());
        onView(withId(R.id.searchResultList)).check(matches(isDisplayed()));
    }

    @Test
    public void testChatFromContact(){
        onView(withId(R.id.searchResultList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.searchResultList)).atPosition(0).perform(click());
        onView(withId(R.id.rExpandedContactBtn)).perform(click());
        onView(withId(R.id.chatRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testChatBackButtonFromContact(){
        onView(withId(R.id.searchResultList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.searchResultList)).atPosition(0).perform(click());
        onView(withId(R.id.rExpandedContactBtn)).perform(click());
        onView(withId(R.id.chatRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.chatBackBtn)).perform(click());
        onView(withId(R.id.searchResultList)).check(matches(isDisplayed()));
    }

    @Test
    public void testSendAndReceiveChatMessage() throws InterruptedException {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_messages));
        onView(withId(R.id.usersListView)).check(matches(isDisplayed()));
        onData(allOf(is(instanceOf(String.class)), Matchers.equalTo("ta3130@dal.ca")))
                .inAdapterView(withId(R.id.usersListView))
                .perform(click());

        //Send first message
        Random random = new Random();
        int randomNum = random.nextInt(5000);
        onView(withId(R.id.chatMessageET)).perform(typeText("Espresso Message" + randomNum), closeSoftKeyboard());
        onView(withId(R.id.chatSendBtn)).perform(click());

        onView(withId(R.id.chatRecyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Espresso Message" + randomNum))));
        onView(withText("Espresso Message" + randomNum)).check(matches(isDisplayed()));

        //Login as user2
        onView(withId(R.id.chatBackBtn)).perform(click());
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_logout));

        onView(withId(R.id.emailLogin)).perform(typeText("ta3130@dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.passwordLogin)).perform(typeText("ta3130"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        Thread.sleep(3000);

        //Check for message
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_messages));
        onView(withId(R.id.usersListView)).check(matches(isDisplayed()));
        onData(allOf(is(instanceOf(String.class)), Matchers.equalTo("test@dal.ca")))
                .inAdapterView(withId(R.id.usersListView))
                .perform(click());

        onView(withId(R.id.chatRecyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Espresso Message" + randomNum))));

    }

}
