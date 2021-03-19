package com.cooleyah.tugasmanager.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.cooleyah.tugasmanager.CatatanAdd;
import com.cooleyah.tugasmanager.GuiHelper;
import com.cooleyah.tugasmanager.JadwalAdd;
import com.cooleyah.tugasmanager.JadwalPelajaran;
import com.cooleyah.tugasmanager.R;
import com.cooleyah.tugasmanager.Tugas;
import com.cooleyah.tugasmanager.TugasAdd;
import com.cooleyah.tugasmanager.db.DatabaseHelper;
import com.cooleyah.tugasmanager.db.DatabaseHelperImpl;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    CalendarView kalender;
    private View rootView;
    private ListView homepelajaran;
    private ListView homepelajaran2;
    private ListView homepelajaran3;
    private JadwalPelajaran[] allSubjectsInList;
    boolean gaadapel;
    boolean gaadatugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homepelajaran = findViewById(R.id.kalender_pelajaran_list);
        homepelajaran2 = findViewById(R.id.event_list);
        homepelajaran3 = findViewById(R.id.tugas_list);
        rootView = findViewById(R.id.homeutama);
        initGui(homepelajaran);
        initGui2(homepelajaran2);
        initGui3(homepelajaran3,25);



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
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.timeline:
                        Intent b = new Intent(MainActivity.this, Timeline.class);
                        startActivity(b);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.settings:
                        Intent c = new Intent(MainActivity.this, More.class);
                        startActivity(c);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                        break;
                }
                return false;
            }
        });

        ImageButton btntugas = (ImageButton) findViewById (R.id.btn_tugas);
        btntugas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                bottomNavigationView.setSelectedItemId(R.id.stats);
                Intent a = new Intent(MainActivity.this, Stats.class);
                startActivity(a);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

        ImageButton btnpel = (ImageButton) findViewById (R.id.btn_jadwal);
        btnpel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                bottomNavigationView.setSelectedItemId(R.id.stats);
                Intent a = new Intent(MainActivity.this, Timeline.class);
                startActivity(a);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

        ImageButton btncat = (ImageButton) findViewById (R.id.btn_catatan);
        btncat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                bottomNavigationView.setSelectedItemId(R.id.stats);
                Intent a = new Intent(MainActivity.this, Stats.class);
                startActivity(a);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
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
                        //Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                        Intent g =  new Intent(MainActivity.this, TugasAdd.class);
                        startActivity(g);
                    }
                });

                FrameLayout buttonEdit2 = popupView.findViewById(R.id.message2Button);
                buttonEdit2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //As an example, display the message
                        //Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                        Intent h =  new Intent(MainActivity.this, CatatanAdd.class);
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

    private void initGui3(View view, int tgl) {
        fillListView2(view, tgl);
    }

    private void initGui2(View view) {
        fillListView3(view);
    }

    private void initGui(View view) {
        fillListView(view);
        defineSubjectListOnClick(view);
        if (gaadatugas && gaadapel) {
            GuiHelper.setVisibility(rootView, R.id.no_event_text, View.VISIBLE);
            GuiHelper.setVisibility(rootView, R.id.pel_hari_ini, View.GONE);
            GuiHelper.setVisibility(rootView, R.id.divider, View.GONE);
        } else {
            GuiHelper.setVisibility(rootView, R.id.no_event_text, View.GONE);
            GuiHelper.setVisibility(rootView, R.id.pel_hari_ini, View.VISIBLE);
            GuiHelper.setVisibility(rootView, R.id.divider, View.VISIBLE);
        }
        //Toast.makeText(view.getContext(), GuiHelper.converthari(), Toast.LENGTH_SHORT).show();
    }
    private void defineSubjectListOnClick(final View view) {
        ListView subjectList = view.findViewById(R.id.kalender_pelajaran_list);
        subjectList.setTextFilterEnabled(true);

        subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(MainActivity.this, JadwalAdd.class);
                //intent.putExtra("SubjectID", position+1);
                //startActivity(intent);
            }
        });
    }

    private JadwalPelajaran[] fillListView(View view) {
        DatabaseHelper dbHelper = new DatabaseHelperImpl(view.getContext());

        SimpleAdapter adapter;
        HashMap<String, String> map;
        HashMap<String, String> map2;
        ArrayList<HashMap<String, String>> mylist;
        ArrayList<HashMap<String, String>> tugaslist;
        ArrayList<HashMap<String, String>> remindlist;

        ArrayList<JadwalPelajaran> subjectArrayList = new ArrayList<>();
        int[] subjectIndices = dbHelper.getIndices(DatabaseHelper.TABLE_PELAJARAN);
        mylist = new ArrayList<HashMap<String, String>>();

        for (int subjectIndex : subjectIndices) {
            JadwalPelajaran subject = dbHelper.getSubjectAtId(subjectIndex);

            if (GuiHelper.extracthari(subject).equals(GuiHelper.converthari())) {
                map = new HashMap<String, String>();
                map.put("Judul", GuiHelper.extractjudul(subject));
                map.put("Waktu",GuiHelper.extractwaktu(subject));
                map.put("WaktuAwal",GuiHelper.extractwaktuawal(subject));
                mylist.add(map);
            }
            //subjectStrings.add(GuiHelper.extractGuiString(subject));
            //subjectArrayList.add(subject);
        }

        if (mylist.size() != 0) {
            //GuiHelper.fillListViewFromArray(view, R.id.daftar_pelajaran, subjectStrings.toArray(new String[0]));
            ListView listView = view.findViewById(R.id.kalender_pelajaran_list);
            //ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, subjectStrings.toArray(new String[0]));

            adapter = new SimpleAdapter(this, mylist, R.layout.layout_daftar_pel_home, new String[]{"Judul", "Waktu", "WaktuAwal"}, new int[]{R.id.judul_pel_home,R.id.waktu_pel_home,R.id.waktuawal_pel_home});
            listView.setAdapter(adapter);

            setListViewHeightBasedOnChildren(listView);

            gaadapel = false;

        } else {
            gaadapel = true;
        }
        return subjectArrayList.toArray(new JadwalPelajaran[0]);
    }


    private Tugas[] fillListView2(View view, int tglsel){
        DatabaseHelper dbHelper = new DatabaseHelperImpl(view.getContext());

        SimpleAdapter adapter;
        HashMap<String, String> map;
        ArrayList<HashMap<String, String>> mylist;
        ArrayList<HashMap<String, String>> tugaslist;
        ArrayList<HashMap<String, String>> remindlist;

        ArrayList<Tugas> tugasArrayList = new ArrayList<>();
        int[] homeworkIndices = dbHelper.getIndices(DatabaseHelper.TABLE_TUGAS);
        mylist = new ArrayList<HashMap<String, String>>();
        remindlist = new ArrayList<HashMap<String, String>>();
        for (int homeworkIndex : homeworkIndices) {
            Tugas tugas = dbHelper.getHomeworkAtId(homeworkIndex);

            if(!GuiHelper.extractisDone(tugas)){
                map = new HashMap<String, String>();
                map.put("Judul", GuiHelper.extractjudul(tugas));
                map.put("Catatan",GuiHelper.extractcatatat(tugas));
                map.put("Deadline",GuiHelper.extractDateGuiString(tugas, this));
                map.put("Pelajaran", GuiHelper.extractPelGuiString(tugas, this));
                mylist.add(map);
            }
        }
        if (mylist.size() != 0) {
            //GuiHelper.fillListViewFromArray(view, R.id.daftar_pelajaran, subjectStrings.toArray(new String[0]));
            ListView listView = view.findViewById(R.id.tugas_list);
            //ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, subjectStrings.toArray(new String[0]));
            adapter = new SimpleAdapter(this, mylist, R.layout.layout_event_tugas, new String[]{"Judul", "Catatan", "Deadline", "Pelajaran"}, new int[]{R.id.nama_pelajaran, R.id.tugas_catatan, R.id.deadline_tugas,R.id.nama_pelajaran1});
            listView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(listView);
            //GuiHelper.setVisibility(rootView, R.id.belum_jadwal, View.GONE);

            gaadatugas = false;
        } else {
            gaadatugas = true;
        }
        return tugasArrayList.toArray(new Tugas[0]);
    }

    private void fillListView3(View view) {
        DatabaseHelper dbHelper = new DatabaseHelperImpl(view.getContext());

        SimpleAdapter adapter;
        HashMap<String, String> map;
        ArrayList<HashMap<String, String>> mylist;

        int[] subjectIndices = dbHelper.getIndices(DatabaseHelper.TABLE_PELAJARAN);
        mylist = new ArrayList<HashMap<String, String>>();

        for (int subjectIndex : subjectIndices) {
            JadwalPelajaran subject = dbHelper.getSubjectAtId(subjectIndex);

            if (GuiHelper.extracthari(subject).equals(GuiHelper.converthari()) &&
                    GuiHelper.extractjamawal(subject) == GuiHelper.extractjamnow() &&
                    GuiHelper.extractmenitawal(subject) >= GuiHelper.extractmenitnow()) {
                map = new HashMap<String, String>();
                map.put("Judul", GuiHelper.extractjudul(subject));
                mylist.add(map);
            }
            //subjectStrings.add(GuiHelper.extractGuiString(subject));
            //subjectArrayList.add(subject);
        }

        if (mylist.size() !=0 ){
            ListView listView2 = view.findViewById(R.id.event_list);

            adapter = new SimpleAdapter(this, mylist, R.layout.layout_event_pel, new String[]{"Judul"}, new int[]{R.id.nama_pelajaran_event});
            listView2.setAdapter(adapter);
            setListViewHeightBasedOnChildren(listView2);
            //GuiHelper.setVisibility(rootView, R.id.belum_jadwal, View.VISIBLE);
        }
    }


    public static void setListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}