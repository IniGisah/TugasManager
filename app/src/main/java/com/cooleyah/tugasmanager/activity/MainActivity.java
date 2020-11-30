package com.cooleyah.tugasmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.cooleyah.tugasmanager.JadwalAdd;
import com.cooleyah.tugasmanager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static androidx.core.content.ContextCompat.startActivity;

public class MainActivity extends AppCompatActivity {

    CalendarView kalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        break;
                    case R.id.stats:
                        Intent a = new Intent(MainActivity.this, Stats.class);
                        startActivity(a);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.timeline:
                        Intent b = new Intent(MainActivity.this, Timeline.class);
                        startActivity(b);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.settings:
                        Intent c = new Intent(MainActivity.this, Settings.class);
                        startActivity(c);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
                return false;
            }
        });


        FloatingActionButton floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }

            public void showPopupWindow(final View view) {


                //Create a View object yourself through inflater
                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.add_select_popup, null);

                //Specify the length and width through constants
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;

                //Make Inactive Items Outside Of PopupWindow
                boolean focusable = true;

                //Create a window with our parameters
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                //Set the location of the window on the screen
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                popupWindow.setAnimationStyle(R.style.AnimationPopup);
                popupWindow.update();

                //Initialize the elements of our window, install the handler

                TextView test2 = popupView.findViewById(R.id.titleText);
                test2.setText(R.string.textTitle);

                FrameLayout buttonEdit = popupView.findViewById(R.id.messageButton);
                buttonEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //As an example, display the message
                        //Toast.makeText(view.getContext(), "Wow, popup action button", Toast.LENGTH_SHORT).show();
                        Intent f =  new Intent(MainActivity.this, JadwalAdd.class);
                        startActivity(f);
                    }
                });

                FrameLayout buttonEdit1 = popupView.findViewById(R.id.message1Button);
                buttonEdit1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //As an example, display the message
                        Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                        //Intent f =  new Intent(MainActivity.this, JadwalAdd.class);
                        //startActivity(f);
                    }
                });

                FrameLayout buttonEdit2 = popupView.findViewById(R.id.message2Button);
                buttonEdit2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //As an example, display the message
                        Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                        //Intent f =  new Intent(MainActivity.this, JadwalAdd.class);
                        //startActivity(f);
                    }
                });

                //Handler for clicking on the inactive zone of the window

                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //Close the window when clicked
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });



        /*
        FloatingActionButton floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent d = new Intent(MainActivity.this, JadwalAdd.class);
            startActivity(d);
            finish();
            }
        });
         */
    }
}