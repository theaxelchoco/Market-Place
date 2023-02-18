package com.example.group17project;

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

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle toggle;
  private FragmentManager fragmentManager; // be aware that this fragment manager is from `androidx`

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_homepage);

    makeNavigation();

    NavigationView navigationView = findViewById(R.id.navigation_view);
    navigationView.setNavigationItemSelectedListener(this);

    fragmentManager = getSupportFragmentManager();
    fragmentTransaction(new ReceiverFragment()); // set default fragment
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.search_view, menu);
    SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        // TODO: Handle search query submission
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        // TODO: Handle search query text changes
        return false;
      }
    });
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