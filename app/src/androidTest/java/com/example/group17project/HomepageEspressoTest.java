package com.example.group17project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class HomepageEspressoTest {
  public ActivityScenario<HomepageActivity> scenario = ActivityScenario.launch(HomepageActivity.class);

  @Test
  public void testSwapHomepage() {
    onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_swap));

    scenario.onActivity(activity -> {
      FragmentManager fragmentManager = activity.getSupportFragmentManager();
      Fragment current = fragmentManager.findFragmentById(R.id.fragment_container);
      assertTrue(current instanceof ProviderFragment);
    });

    onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_swap));

    scenario.onActivity(activity -> {
      FragmentManager fragmentManager = activity.getSupportFragmentManager();
      Fragment current = fragmentManager.findFragmentById(R.id.fragment_container);
      assertTrue(current instanceof ReceiverFragment);
    });

    scenario.close();
  }
}
