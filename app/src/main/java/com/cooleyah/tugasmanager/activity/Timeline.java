package com.cooleyah.tugasmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cooleyah.tugasmanager.CatatanAdd;
import com.cooleyah.tugasmanager.GuiHelper;
import com.cooleyah.tugasmanager.JadwalAdd;
import com.cooleyah.tugasmanager.JadwalPelajaran;
import com.cooleyah.tugasmanager.R;
import com.cooleyah.tugasmanager.TugasAdd;
import com.cooleyah.tugasmanager.db.DatabaseHelper;
import com.cooleyah.tugasmanager.db.DatabaseHelperImpl;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class Timeline extends AppCompatActivity{

    private JadwalPelajaran[] allSubjectsInList;
    private ListView daftarpelajaran;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        rootView = findViewById(R.id.timeline);
        daftarpelajaran = findViewById(R.id.daftar_pelajaran);
        initGui(daftarpelajaran);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.timeline);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent b = new Intent(Timeline.this, MainActivity.class);
                        startActivity(b);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.stats:
                        Intent a = new Intent(Timeline.this, Stats.class);
                        startActivity(a);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.timeline:
                        break;
                    case R.id.settings:
                        Intent c = new Intent(Timeline.this, More.class);
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
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                popupWindow.setElevation(20);
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
                        Intent f =  new Intent(Timeline.this, JadwalAdd.class);
                        startActivity(f);
                    }
                });

                FrameLayout buttonEdit1 = popupView.findViewById(R.id.message1Button);
                buttonEdit1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //As an example, display the message
                        //Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                        Intent g =  new Intent(Timeline.this, TugasAdd.class);
                        startActivity(g);
                    }
                });

                FrameLayout buttonEdit2 = popupView.findViewById(R.id.message2Button);
                buttonEdit2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //As an example, display the message
                        //Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                        Intent h =  new Intent(Timeline.this, CatatanAdd.class);
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

    private JadwalPelajaran[] fillListView(View view) {
        DatabaseHelper dbHelper = new DatabaseHelperImpl(view.getContext());

        SimpleAdapter adapter;
        HashMap<String, String> map;
        ArrayList<HashMap<String, String>> mylist;

        ArrayList<String> subjectStrings = new ArrayList<>();
        ArrayList<JadwalPelajaran> subjectArrayList = new ArrayList<>();
        int[] subjectIndices = dbHelper.getIndices(DatabaseHelper.TABLE_PELAJARAN);
        mylist = new ArrayList<HashMap<String, String>>();

        for (int subjectIndex : subjectIndices) {
            JadwalPelajaran subject = dbHelper.getSubjectAtId(subjectIndex);

            map = new HashMap<String, String>();
            map.put("Judul",GuiHelper.extractjudul(subject));
            map.put("Waktu",GuiHelper.extractwaktu(subject));
            map.put("Hari", GuiHelper.extracthari(subject));
            mylist.add(map);
            subjectArrayList.add(subject);

            //subjectStrings.add(GuiHelper.extractGuiString(subject));
            //subjectArrayList.add(subject);
        }

        if (mylist.size() != 0) {
            //GuiHelper.fillListViewFromArray(view, R.id.daftar_pelajaran, subjectStrings.toArray(new String[0]));
            ListView listView = view.findViewById(R.id.daftar_pelajaran);
            //ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, subjectStrings.toArray(new String[0]));

            adapter = new SimpleAdapter(this, mylist, R.layout.layout_jadwal, new String[]{"Judul", "Waktu", "Hari"}, new int[]{R.id.title_pelajaran,R.id.waktu_pelajaran,R.id.hari_pelajaran});
            listView.setAdapter(adapter);

            GuiHelper.setVisibility(rootView, R.id.belum_jadwal, View.GONE);
        } else {
            GuiHelper.setVisibility(rootView, R.id.belum_jadwal, View.VISIBLE);
        }
        return subjectArrayList.toArray(new JadwalPelajaran[0]);
    }
    private void initGui(View view) {
        allSubjectsInList = fillListView(view);
        defineSubjectListOnClick(view);
    }

    private void defineSubjectListOnClick(final View view) {
        ListView subjectList = view.findViewById(R.id.daftar_pelajaran);
        subjectList.setTextFilterEnabled(true);

        subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Intent intent = new Intent(Timeline.this, JadwalAdd.class);
                intent.putExtra("SubjectID", allSubjectsInList[position].getId());
                startActivity(intent);
            }
        });
    }
}