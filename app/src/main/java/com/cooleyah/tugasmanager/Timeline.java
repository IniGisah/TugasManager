package com.cooleyah.tugasmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Timeline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        //bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent b = new Intent(Timeline.this, MainActivity.class);
                        startActivity(b);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.stats:
                        Intent a = new Intent(Timeline.this, Stats.class);
                        startActivity(a);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.timeline:
                        break;
                    case R.id.settings:
                        Intent c = new Intent(Timeline.this, Settings.class);
                        startActivity(c);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
            }
        });
    }
}