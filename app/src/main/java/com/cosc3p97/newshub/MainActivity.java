package com.cosc3p97.newshub;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cosc3p97.newshub.fragments.BookmarkFragment;
import com.cosc3p97.newshub.fragments.EntertainmentFragment;
import com.cosc3p97.newshub.fragments.HealthFragment;
import com.cosc3p97.newshub.fragments.HomeFragment;
import com.cosc3p97.newshub.fragments.ScienceFragment;
import com.cosc3p97.newshub.fragments.SettingsFragment;
import com.cosc3p97.newshub.fragments.SportsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView bookmarkIcon;
    private ImageView settingsIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bookmarkIcon = findViewById(R.id.bookmarkIcon);
        settingsIcon = findViewById(R.id.settingsIcon);

        // Load the default fragment (e.g., HomeFragment)
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            // Replace switch-case with if-else
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

        settingsIcon.setOnClickListener(v ->{
            loadFragment(new SettingsFragment());
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
    }
}
