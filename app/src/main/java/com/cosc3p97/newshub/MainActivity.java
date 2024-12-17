package com.cosc3p97.newshub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

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
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
    }
}
