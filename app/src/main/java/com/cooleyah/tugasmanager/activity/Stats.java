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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.cooleyah.tugasmanager.CatatanAdd;
import com.cooleyah.tugasmanager.GuiHelper;
import com.cooleyah.tugasmanager.JadwalAdd;
import com.cooleyah.tugasmanager.R;
import com.cooleyah.tugasmanager.Tugas;
import com.cooleyah.tugasmanager.TugasAdd;
import com.cooleyah.tugasmanager.db.DatabaseHelper;
import com.cooleyah.tugasmanager.db.DatabaseHelperImpl;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Stats extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private boolean tabIsToDo;
    private ListView daftartugas;
    private Tugas[] allHomeworkInList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        view = findViewById(R.id.stats);
        daftartugas = findViewById(R.id.daftar_tugas);
        tabIsToDo = true;
        initGui();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.stats);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent a = new Intent(Stats.this, MainActivity.class);
                        startActivity(a);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.stats:
                        break;
                    case R.id.timeline:
                        Intent b = new Intent(Stats.this, Timeline.class);
                        startActivity(b);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.settings:
                        Intent c = new Intent(Stats.this, More.class);
                        startActivity(c);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
                        Intent f =  new Intent(Stats.this, JadwalAdd.class);
                        startActivity(f);
                    }
                });

                FrameLayout buttonEdit1 = popupView.findViewById(R.id.message1Button);
                buttonEdit1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //As an example, display the message
                        //Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                        Intent g =  new Intent(Stats.this, TugasAdd.class);
                        startActivity(g);
                    }
                });

                FrameLayout buttonEdit2 = popupView.findViewById(R.id.message2Button);
                buttonEdit2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //As an example, display the message
                        //Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                        Intent h =  new Intent(Stats.this, CatatanAdd.class);
                        startActivity(h);
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
    }

    private void initGui() {
        GuiHelper.defineButtonOnClickListener(view, R.id.homework_buttonToDo, this);
        GuiHelper.defineButtonOnClickListener(view, R.id.homework_buttonDone, this);

        allHomeworkInList = changeTab();
        defineHomeworkListOnClick(view);
    }

    private void defineHomeworkListOnClick(final View view) {
        ListView homeworkList = view.findViewById(R.id.daftar_tugas);

        homeworkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Intent intent = new Intent(Stats.this, TugasAdd.class);
                intent.putExtra("HomeworkID", allHomeworkInList[position].getId());
                startActivity(intent);
            }
        });
    }

    private Tugas[] changeTab() {
        if (tabIsToDo) {
            GuiHelper.setColorToButton(view, R.id.homework_buttonToDo, 1f);
            GuiHelper.setColorToButton(view, R.id.homework_buttonDone, 0.5f);
            return fillListView();
        } else {
            GuiHelper.setColorToButton(view, R.id.homework_buttonToDo, 0.5f);
            GuiHelper.setColorToButton(view, R.id.homework_buttonDone, 1f);
            return fillListView();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homework_buttonToDo:
                tabIsToDo = true;
                allHomeworkInList = changeTab();
                break;
            case R.id.homework_buttonDone:
                tabIsToDo = false;
                allHomeworkInList = changeTab();
                break;
        }
    }

    private Tugas[] fillListView() {
        DatabaseHelper dbHelper = new DatabaseHelperImpl(view.getContext());

        ArrayList<String> homeworkStrings = new ArrayList<>();
        ArrayList<Tugas> homeworkArrayList = new ArrayList<>();
        int[] homeworkIndices = dbHelper.getIndices(DatabaseHelper.TABLE_TUGAS);

        for (int homeworkIndex : homeworkIndices) {
            Tugas homework = dbHelper.getHomeworkAtId(homeworkIndex);

            if (tabIsToDo && !homework.isDone()) {
                homeworkStrings.add(GuiHelper.extractGuiString(homework, this));
                homeworkArrayList.add(homework);
            } else if (!tabIsToDo && homework.isDone()) {
                homeworkStrings.add(GuiHelper.extractGuiString(homework, this));
                homeworkArrayList.add(homework);
            }
        }

        //  if (homeworkStrings.size() != 0) {
        GuiHelper.fillListViewFromArray(view, R.id.daftar_tugas, homeworkStrings.toArray(new String[0]));
        //}

        return homeworkArrayList.toArray(new Tugas[0]);
    }
}