/*
HomepageActivity code
Group 17
*/

package com.example.group17project.Homepages;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.group17project.ProviderFunctionality.ProviderFragment;
import com.example.group17project.R;
import com.example.group17project.ReceiverFunctionality.ReceiverFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle toggle;
  private FragmentManager fragmentManager;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_homepage);

    makeNavigation();

    NavigationView navigationView = findViewById(R.id.navigation_view);
    navigationView.setNavigationItemSelectedListener(this);
    fragmentManager = getSupportFragmentManager();

    String fragmentId = getIntent().getStringExtra("fragmentId");
    if (fragmentId != null) {
      if (fragmentId.equals("receiver")) {
        fragmentTransaction(new ReceiverFragment()); // set default fragment
      } else if (fragmentId.equals("provider")) {
        fragmentTransaction(new ProviderFragment());
      }
    } else {
      fragmentTransaction(new ReceiverFragment());
    }

    if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("keyword")) {
      Bundle bundle = getIntent().getExtras();
      ReceiverFragment receiverFragment = new ReceiverFragment();
      receiverFragment.setArguments(bundle);
      fragmentTransaction(receiverFragment);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.search_view, menu);
    SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("keyword", query);
        ReceiverFragment receiverFragment = new ReceiverFragment();
        receiverFragment.setArguments(bundle);
        fragmentTransaction(receiverFragment);

        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return true;
      }
    });

    searchView.setSubmitButtonEnabled(true);
    return true;
  }

  /**
   * A helper method to create navigation menu for homepage
   */
  private void makeNavigation() {
    drawerLayout = findViewById(R.id.drawer_layout);
    toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (toggle.onOptionsItemSelected(item)) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.nav_swap) {
      swapProfile();
    }
    else if(item.getItemId() == R.id.nav_profile){
      //setContentView(R.layout.activity_visualization);
      Intent i = new Intent(HomepageActivity.this, Visualization.class);
      startActivity(i);
    }
    drawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }

  /**
   * A helper method to swap profile
   */
  private void swapProfile() {
    Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
    Fragment newFragment =
        currentFragment instanceof ProviderFragment ? new ReceiverFragment() : new ProviderFragment();

    fragmentTransaction(newFragment);
  }

  /**
   * A helper method to swap fragments
   *
   * @param other the destination fragment
   */
  private void fragmentTransaction(Fragment other) {
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.fragment_container, other);
    fragmentTransaction.commit();
  }
}