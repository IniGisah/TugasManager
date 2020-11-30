package com.cooleyah.tugasmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cooleyah.tugasmanager.db.DatabaseHelper;
import com.cooleyah.tugasmanager.db.DatabaseHelperImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class JadwalAdd extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    TimePickerDialog picker;
    EditText eText, eText2, eText3;
    Button btnGet;
    TextView tvw;
    private DatabaseHelper dbHelper;
    private View rootView;
    private JadwalPelajaran showingSubject;
    private int jamawalinput;
    private int jamakhirinput;
    private String hariselect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_add);
        rootView = findViewById(R.id.tambahjadwal_main);
        eText=(EditText) findViewById(R.id.pelajaran_jamawal);
        eText2=(EditText) findViewById(R.id.pelajaran_jamakhir);
        eText3=(EditText) findViewById(R.id.pelajaran_hari);
        eText.setInputType(InputType.TYPE_NULL);
        eText2.setInputType(InputType.TYPE_NULL);
        eText3.setInputType(InputType.TYPE_NULL);

        dbHelper = new DatabaseHelperImpl(this);

        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);

                picker = new TimePickerDialog(JadwalAdd.this, R.style.DialogTheme ,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText.setText(sHour + ":" + sMinute);
                                jamawalinput = (sHour + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        eText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);

                picker = new TimePickerDialog(JadwalAdd.this, R.style.DialogTheme ,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText2.setText(sHour + ":" + sMinute);
                                jamakhirinput = (sHour + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        eText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(JadwalAdd.this, v);
                popup.setOnMenuItemClickListener(JadwalAdd.this);
                popup.inflate(R.menu.hari_selector);
                popup.show();
            }
        });


        FloatingActionButton floatingActionButton=findViewById(R.id.fabexit);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton floatingActionButton1=findViewById(R.id.fabyes);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dbHelper.insertIntoDB(readSubjectFromGUI());
                    finish();
                } catch (IllegalArgumentException ignored) {
                }
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()){
            case R.id.h_minggu:
                hariselect = "Minggu";
                eText3.setText("Minggu");
                return true;
            case R.id.h_senin:
                hariselect = "Senin";
                eText3.setText("Senin");
                return true;
            case R.id.h_selasa:
                hariselect = "Selasa";
                eText3.setText("Selasa");
                return true;
            case R.id.h_rabu:
                hariselect = "Rabu";
                eText3.setText("Rabu");
                return true;
            case R.id.h_kamis:
                hariselect = "Kamis";
                eText3.setText("Kamis");
                return true;
            case R.id.h_jumat:
                hariselect = "Jumat";
                eText3.setText("Jumat");
                return true;
            case R.id.h_sabtu:
                hariselect = "Sabtu";
                eText3.setText("Sabtu");
                return true;
            default:
                return false;
        }
    }

    private JadwalPelajaran readSubjectFromGUI() throws IllegalArgumentException {
        return new JadwalPelajaran(
                -1,
                GuiHelper.getInputFromMandatoryEditText(rootView, R.id.nama_pelajaran),
                hariselect,
                jamawalinput,
                jamakhirinput
        );
    }
}