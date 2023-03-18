package com.example.group17project;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.group17project.utils.Methods;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.search_view, menu);
    SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.firebase_database_url));
        ProductRepository productRepository = new ProductRepository(database);

        Query searchQuery = productRepository.getDatabaseRef().orderByChild("name").startAt(query).endAt(query + "\uf8ff");
        searchQuery.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            List<Product> searchResult = new ArrayList<>();
            for (DataSnapshot data : snapshot.getChildren()) {
              Product product = data.getValue(Product.class);
              Long dateAvailableMillis = data.child("dateAvailable").child("time").getValue(Long.class);
              assert dateAvailableMillis != null;
              Date dateAvailable = new Date(dateAvailableMillis);
              assert product != null;
              product.setDateAvailable(dateAvailable);

              searchResult.add(product);
            }

            if (searchResult.isEmpty()) {
              Methods.makeAlert("No result found", new AlertDialog.Builder(HomepageActivity.this));
            } else {
              Bundle bundle = new Bundle();
              bundle.putSerializable("searchResult", (Serializable) searchResult);
              Fragment searchResultFragment = new ReceiverFragment();
              searchResultFragment.setArguments(bundle);
              fragmentTransaction(searchResultFragment);
            }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Methods.makeAlert("Error: " + error.getMessage(), new AlertDialog.Builder(HomepageActivity.this));
          }
        });

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