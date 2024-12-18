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

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.group17project.Homepages.HomepageActivity;
import com.example.group17project.ProviderFunctionality.ProviderFragment;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.User;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
public class ProvidersListEspressoTest {

  private ProviderFragment providerFragment;
  public ActivityScenario<HomepageActivity> scenario = ActivityScenario.launch(HomepageActivity.class);
  private static ProductRepository productRepo;
  Product product;

  @BeforeClass
  public static void setUpRepo() {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
    productRepo = new ProductRepository(database, false);
  }

  @Before
  public void setUp() throws Exception {
    User.getInstance().setUserDetails("test@dal.ca", 0, 0, 0, 0);
    Calendar cal = Calendar.getInstance();
    cal.set(2023, 3, 14);
    product = new Product("Test Iphone 12", "test@dal.ca", "test description", cal, "Mobile Phones", "Halifax", "Clothes", 100);
    productRepo.createProduct(product);

    Thread.sleep(2000);
    onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_swap));


  }

  @After
  public void tearDown() throws Exception {
    productRepo.deleteProduct(product.getProductID());
  }

  @Test
  public void useAppContext() {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    assertEquals("com.example.group17project", appContext.getPackageName());
  }

  @Test
  public void testTapOnItem() {
    onView(withId(R.id.productList)).check(matches(isDisplayed()));
    onData(anything()).inAdapterView(withId(R.id.productList)).atPosition(0).perform(click());
    onView(withId(R.id.pExpandedProductName)).check(matches(withText("Test Iphone 12")));
  }

  @Test
  public void testItemExpandedBackButton() {
    onView(withId(R.id.productList)).check(matches(isDisplayed()));
    onData(anything()).inAdapterView(withId(R.id.productList)).atPosition(0).perform(click());
    onView(withId(R.id.pExpandedBackBtn)).perform(click());
    onView(withId(R.id.productList)).check(matches(isDisplayed()));
  }

  @Test
  public void testItemDelete() {
    onView(withId(R.id.productList)).check(matches(isDisplayed()));
    onData(anything()).inAdapterView(withId(R.id.productList)).atPosition(0).perform(click());
    onView(withId(R.id.pExpandedDeleteBtn)).perform(click());
    onView(withId(R.id.productList)).check(matches(isDisplayed()));
  }

  @Test
  public void testItemEdit() {
    onView(withId(R.id.productList)).check(matches(isDisplayed()));
    onData(anything()).inAdapterView(withId(R.id.productList)).atPosition(0).perform(click());
    onView(withId(R.id.pExpandedEditBtn)).perform(click());
    onView(withId(R.id.addProductTitle)).check(matches(isDisplayed()));
  }


}
