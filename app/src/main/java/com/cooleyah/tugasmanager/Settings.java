package com.cooleyah.tugasmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        //bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent c = new Intent(Settings.this, MainActivity.class);
                        startActivity(c);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.stats:
                        Intent a = new Intent(Settings.this, Stats.class);
                        startActivity(a);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.timeline:
                        Intent b = new Intent(Settings.this, Timeline.class);
                        startActivity(b);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.settings:
                        break;
                }
                return false;
            }
        });
    }
}