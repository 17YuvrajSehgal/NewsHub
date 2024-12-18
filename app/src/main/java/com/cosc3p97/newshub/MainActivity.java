package com.cosc3p97.newshub;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cosc3p97.newshub.fragments.BookmarkFragment;
import com.cosc3p97.newshub.fragments.EntertainmentFragment;
import com.cosc3p97.newshub.fragments.HealthFragment;
import com.cosc3p97.newshub.fragments.HomeFragment;
import com.cosc3p97.newshub.fragments.ScienceFragment;
import com.cosc3p97.newshub.fragments.SearchFragment;
import com.cosc3p97.newshub.fragments.SettingsFragment;
import com.cosc3p97.newshub.fragments.SportsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView bookmarkIcon;
    private ImageView settingsIcon;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bookmarkIcon = findViewById(R.id.bookmarkIcon);
        settingsIcon = findViewById(R.id.settingsIcon);
        searchView = findViewById(R.id.searchView);

        // Load the default fragment (e.g., HomeFragment)
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            // Switch fragments for each bottom navigation item clicked
            if (item.getItemId() == R.id.nav_home) {
                ft.replace(R.id.content, new HomeFragment());
            } else if (item.getItemId() == R.id.nav_science) {
                ft.replace(R.id.content, new ScienceFragment());
            } else if (item.getItemId() == R.id.nav_sports) {
                ft.replace(R.id.content, new SportsFragment());
            } else if (item.getItemId() == R.id.nav_health) {
                ft.replace(R.id.content, new HealthFragment());
            } else if (item.getItemId() == R.id.nav_entertainment) {
                ft.replace(R.id.content, new EntertainmentFragment());
            }

            ft.commit();
            return true;
        });

        // Handle bookmark icon click in the toolbar
        bookmarkIcon.setOnClickListener(v -> {
            // Open the BookmarkFragment when the bookmark icon is clicked
            loadFragment(new BookmarkFragment());
        });

        settingsIcon.setOnClickListener(v -> {
            loadFragment(new SettingsFragment());
        });

        // Set listener for when the search query is submitted or changed
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Check if the query is empty
                if (query.trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Search query cannot be empty", Toast.LENGTH_SHORT).show();
                    return false; // Prevent further search
                }

                // Proceed to load the search fragment with the query if not empty
                loadSearchFragment(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optionally, you can handle live filtering here if needed.
                // Currently, this only listens for text change but does nothing unless implemented.
                return false;
            }
        });
    }

    // Method to load the SearchFragment and pass the search query
    public void loadSearchFragment(String query) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("query", query);  // Passing the search query
        searchFragment.setArguments(args);  // Setting arguments to the fragment

        // Replace with the SearchFragment displaying the results
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, searchFragment);
        ft.commit();
    }

    // Method to load a fragment dynamically
    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
    }
}
