package com.cosc3p97.newshub;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
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
    private ImageView bookmarkIcon, settingsIcon, filterIcon;
    private SearchView searchView;

    private PopupWindow sortByPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bookmarkIcon = findViewById(R.id.bookmarkIcon);
        settingsIcon = findViewById(R.id.settingsIcon);
        searchView = findViewById(R.id.searchView);
        filterIcon = findViewById(R.id.filterIcon);

        // Initially hide the filter icon
        //filterIcon.setVisibility(ImageView.GONE);

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
        bookmarkIcon.setOnClickListener(v -> loadFragment(new BookmarkFragment()));

        settingsIcon.setOnClickListener(v -> loadFragment(new SettingsFragment()));

        // Set listener for when the search query is submitted or changed
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Check if the query is empty
                if (query.trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Search query cannot be empty", Toast.LENGTH_SHORT).show();
                    return false; // Prevent further search
                }

                loadSearchFragment(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle live search (if needed)
                return false;
            }
        });

        // Handle filter icon click (for sortBy options)
        filterIcon.setOnClickListener(v -> {
            // Ensure activity is in a valid state before showing the popup
            if (!isFinishing() && !isDestroyed()) {
                showFilters();
            } else {
                Toast.makeText(MainActivity.this, "Activity is not in a valid state to show popup", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFilters() {
        // Check if the activity is still valid and not finishing or destroyed
        if (!isFinishing() && !isDestroyed() && !isChangingConfigurations()) {
            // Inflate the filter layout (sort_by_filter.xml)
            View filterView = LayoutInflater.from(MainActivity.this).inflate(R.layout.sort_by_filter, null);

            // Create the PopupWindow
            sortByPopupWindow = new PopupWindow(filterView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            sortByPopupWindow.setOutsideTouchable(true); // Close when tapping outside
            sortByPopupWindow.setFocusable(true);

            // Find the spinner and button from the inflated view
            Spinner sortBySpinner = filterView.findViewById(R.id.sortBySpinner);
            Button applySortButton = filterView.findViewById(R.id.applySortButton);

            // 1. Define the spinner options (from the string array in strings.xml)
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.sort_by_options, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sortBySpinner.setAdapter(adapter);

            // 2. Set the "Apply" button's onClickListener
            applySortButton.setOnClickListener(v -> {
                String selectedSortOption = sortBySpinner.getSelectedItem().toString();
                // Handle the selected sort option (e.g., update the news feed accordingly)
                applySortOption(selectedSortOption);

                // Dismiss the PopupWindow after applying the filter
                sortByPopupWindow.dismiss();
            });

            // Show the PopupWindow at the location of the filter icon
            int[] location = new int[2];
            filterIcon.getLocationOnScreen(location);
            sortByPopupWindow.showAtLocation(filterView, Gravity.NO_GRAVITY, location[0], location[1] + filterIcon.getHeight());
        } else {
            // Handle the case when the activity is not in a valid state
            Toast.makeText(MainActivity.this, "Activity is not in a valid state to show popup", Toast.LENGTH_SHORT).show();
        }
    }


    private void applySortOption(String sortOption) {
        // Handle the sorting logic based on the selected option
        switch (sortOption) {
            case "publishedAt":
                // Sort news by publish date
                // Call the method to refresh your news list (e.g., with API or local data)
                break;
            case "popularity":
                // Sort news by popularity
                break;
            case "relevancy":
                // Sort news by relevancy
                break;
            default:
                break;
        }

        // You can call a method to refresh the news results here based on the selected sorting criteria
        // For example, you might trigger a network request to fetch news sorted by the selected option.
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

        updateFilterIconVisibility(true);  // Show the filter icon if there are results
    }

    // Method to update the visibility of the filter icon based on search results
    private void updateFilterIconVisibility(boolean hasResults) {
        if (hasResults) {
            filterIcon.setVisibility(ImageView.VISIBLE); // Show filter icon
        } else {
            filterIcon.setVisibility(ImageView.GONE); // Hide filter icon
        }
    }

    // Method to load a fragment dynamically
    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
    }
}
