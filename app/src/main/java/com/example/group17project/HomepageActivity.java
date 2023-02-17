package com.example.group17project;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    // drawer layout instance to toggle the menu icon to open
    // drawer and back button to close drawer
    drawerLayout = findViewById(R.id.drawer_layout);
    toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

    // pass the Open and Close toggle for the drawer layout listener
    // toggle the button
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();
    // make the Navigation drawer icon always appear on the action bar
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    NavigationView navigationView = findViewById(R.id.navigation_view);
    // Set this activity as the listener for item selections in the NavigationView
    navigationView.setNavigationItemSelectedListener(this);

    // be aware that this fragment manager is from package androidx
    fragmentManager = getSupportFragmentManager();
    fragmentTransaction(new ReceiverFragment());
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
      Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
      Fragment newFragment;

      if (currentFragment instanceof ProviderFragment) {
        newFragment = new ReceiverFragment();
      } else {
        newFragment = new ProviderFragment();
      }

      fragmentTransaction(newFragment);
    }

    drawerLayout.closeDrawer(GravityCompat.START);
    return true;
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