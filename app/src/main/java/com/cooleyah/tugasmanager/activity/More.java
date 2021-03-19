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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cooleyah.tugasmanager.Catatan;
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

public class More extends AppCompatActivity {

    private Catatan[] allCatatanInList;
    private ListView daftacatatan;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        rootView = findViewById(R.id.more);
        daftacatatan = findViewById(R.id.catatan_list);
        initGui(daftacatatan);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent c = new Intent(More.this, MainActivity.class);
                        startActivity(c);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.stats:
                        Intent a = new Intent(More.this, Stats.class);
                        startActivity(a);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.timeline:
                        Intent b = new Intent(More.this, Timeline.class);
                        startActivity(b);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.settings:
                        break;
                }
                return false;
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
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
                        Intent f = new Intent(More.this, JadwalAdd.class);
                        startActivity(f);
                    }
                });

                FrameLayout buttonEdit1 = popupView.findViewById(R.id.message1Button);
                buttonEdit1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //As an example, display the message
                        //Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                        Intent g =  new Intent(More.this, TugasAdd.class);
                        startActivity(g);
                    }
                });

                FrameLayout buttonEdit2 = popupView.findViewById(R.id.message2Button);
                buttonEdit2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //As an example, display the message
                        //Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                        Intent h =  new Intent(More.this, CatatanAdd.class);
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

        FrameLayout daftarkelassync =  (FrameLayout) findViewById(R.id.daftar_kelassync);
        daftarkelassync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //As an example, display the message
                Toast.makeText(view.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                //Intent f =  new Intent(MainActivity.this, JadwalAdd.class);
                //startActivity(f);
            }
        });

        ImageView daftarkelassync2 = (ImageView) findViewById(R.id.add_kelassync);
        daftarkelassync2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView daftarnotes2 = (ImageView) findViewById(R.id.add_notes);
        daftarnotes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Not implemented yet, sorry....", Toast.LENGTH_SHORT).show();
                Intent h =  new Intent(More.this, CatatanAdd.class);
                startActivity(h);
            }
        });

        LinearLayout about =  (LinearLayout) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent f =  new Intent(More.this, About.class);
                startActivity(f);
            }
        });

    }

    private void initGui(View view) {
        allCatatanInList = fillListView(view);
        defineSubjectListOnClick(view);
    }

    private Catatan[] fillListView(View view) {
        DatabaseHelper dbHelper = new DatabaseHelperImpl(view.getContext());

        SimpleAdapter adapter;
        HashMap<String, String> map;
        ArrayList<HashMap<String, String>> mylist;

        ArrayList<String> catatanStrings = new ArrayList<>();
        ArrayList<Catatan> catatanArrayList = new ArrayList<>();
        int[] catatanIndices = dbHelper.getIndices(DatabaseHelper.TABLE_CATATAN);
        mylist = new ArrayList<HashMap<String, String>>();

        for (int notesIndex : catatanIndices){
            Catatan catatan = dbHelper.getCatatanAtId(notesIndex);

            map = new HashMap<String, String>();
            map.put("Judul", GuiHelper.extractjudul(catatan));
            mylist.add(map);
            catatanArrayList.add(catatan);
        }

        if (mylist.size() !=0){
            ListView listView = view.findViewById(R.id.catatan_list);

            adapter = new SimpleAdapter(this, mylist, R.layout.layout_catatan, new String[]{"Judul"}, new int[]{R.id.title_notes});
            listView.setAdapter(adapter);

            setListViewHeightBasedOnChildren(listView);
            GuiHelper.setVisibility(rootView, R.id.belum_ada_catatan, View.GONE);
        } else {
            GuiHelper.setVisibility(rootView, R.id.belum_ada_catatan, View.VISIBLE);
        }
        return catatanArrayList.toArray(new Catatan[0]);
    }

    private void defineSubjectListOnClick(final View view) {
        ListView subjectList = view.findViewById(R.id.catatan_list);
        subjectList.setTextFilterEnabled(true);

        subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Intent intent = new Intent(More.this, CatatanAdd.class);
                intent.putExtra("CatatanID", allCatatanInList[position].getId());
                startActivity(intent);
            }
        });
    }

    public static void setListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}