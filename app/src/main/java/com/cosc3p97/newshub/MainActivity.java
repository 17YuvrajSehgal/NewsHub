package com.cosc3p97.newshub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {

    ReadableBottomBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rb = findViewById(R.id.readableBottomBar);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, new HomeFragment());
        ft.commit();

        rb.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        ft.replace(R.id.content,new HomeFragment());
                        ft.commit();
                        break;

                    case 1:
                        ft.replace(R.id.content,new ScienceFragment());
                        ft.commit();
                        break;

                    case 2:
                        ft.replace(R.id.content,new SportsFragment());
                        ft.commit();
                        break;

                    case 3:
                        ft.replace(R.id.content,new HealthFragment());
                        ft.commit();
                        break;

                    case 4:
                        ft.replace(R.id.content,new EntertainmentFragment());
                        ft.commit();
                        break;
                }
            }
        });
    }
}