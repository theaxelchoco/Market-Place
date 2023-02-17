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
  private NavigationView navigationView;
  private ActionBarDrawerToggle toggle;
  private FragmentManager fragmentManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_homepage);

    drawerLayout = findViewById(R.id.drawer_layout);
    toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    navigationView = findViewById(R.id.navigation_view);

    navigationView.setNavigationItemSelectedListener(this);
    fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.fragment_container, new ReceiverFragment());
    fragmentTransaction.commit();
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

      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.replace(R.id.fragment_container, newFragment);
      fragmentTransaction.commit();
    }
    
    drawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }
}