package com.cooleyah.tugasmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cooleyah.tugasmanager.activity.Stats;
import com.cooleyah.tugasmanager.activity.Timeline;
import com.cooleyah.tugasmanager.db.DatabaseHelper;
import com.cooleyah.tugasmanager.db.DatabaseHelperImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.Calendar;

public class JadwalAdd extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    TimePickerDialog picker;
    EditText eText, eText2, eText3;
    Button btnGet;
    TextView tvw;
    private DatabaseHelper dbHelper;
    private View rootView;
    private boolean addMode;
    private JadwalPelajaran showingSubject;
    private int jamawalintinput;
    private int menitawalintinput;
    private int jamakhirintinput;
    private int menitakhirintinput;
    private String jamawalinput;
    private String jamakhirinput;
    private String hariselect;

    public String pad(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + input;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_add);
        rootView = findViewById(R.id.tambahjadwal_main);
        eText = (EditText) findViewById(R.id.pelajaran_jamawal);
        eText2 = (EditText) findViewById(R.id.pelajaran_jamakhir);
        eText3 = (EditText) findViewById(R.id.pelajaran_tanggal);
        eText.setInputType(InputType.TYPE_NULL);
        eText2.setInputType(InputType.TYPE_NULL);
        eText3.setInputType(InputType.TYPE_NULL);

        dbHelper = new DatabaseHelperImpl(this);

        int subjectId = getIntent().getIntExtra("SubjectID", -1);
        if (subjectId <= 0) {
            addMode = true;
        } else {
            addMode = false;
            showingSubject = dbHelper.getSubjectAtId(subjectId);
        }

        initGUI();

        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);

                picker = new TimePickerDialog(JadwalAdd.this, R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                                eText.setText(String.format("%02d : %02d", sHour , sMinute));

                                //variable declaration
                                jamawalintinput = sHour;
                                menitawalintinput = sMinute;

                                //converter
                                //String s1 = Integer.toString(sHour);
                                //String s2 = Integer.toString(sMinute);

                                //String s = s1 + " : " + s2;

                                jamawalinput = pad(sHour) + " : " + pad(sMinute);
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

                picker = new TimePickerDialog(JadwalAdd.this, R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText2.setText(String.format("%02d : %02d", sHour , sMinute));

                                //variable declaration
                                jamakhirintinput = sHour;
                                menitakhirintinput = sMinute;

                                //converter
                                //String d1 = Integer.toString(sHour);
                                //String d2 = Integer.toString(sMinute);

                                //String d = d1 + " : " + d2;

                                jamakhirinput = pad(sHour) + " : " + pad(sMinute);
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


        FloatingActionButton floatingActionButton = findViewById(R.id.fabexit);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //Nanyain buat apus
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Konfirmasi");
        builder.setMessage("Apakah anda yakin ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                try {
                    dbHelper.deleteSubjectAtId(showingSubject.getId());
                    Toast.makeText(getApplicationContext(), "Jadwal Pelajaran Berhasil dihapus !", Toast.LENGTH_SHORT).show();
                } catch (IllegalArgumentException ignored) {
                }
                Intent refresh = new Intent(JadwalAdd.this, Timeline.class);
                startActivity(refresh);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        FloatingActionButton floatingActionButton2 = findViewById(R.id.fabdelete);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED);
            }
        });

        FloatingActionButton floatingActionButton1 = findViewById(R.id.fabyes);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (addMode) {
                        dbHelper.insertIntoDB(readSubjectFromGUI());
                    } else {
                        dbHelper.updateSubjectAtId(readSubjectFromGUI());
                    }
                    Toast.makeText(view.getContext(), "Jadwal Pelajaran Berhasil ditambahkan !", Toast.LENGTH_SHORT).show();
                    Intent refresh = new Intent(JadwalAdd.this, Timeline.class);
                    startActivity(refresh);
                    finish();
                } catch (IllegalArgumentException ignored) {
                }
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
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
        if (addMode) {
            return new JadwalPelajaran(
                    -1,
                    GuiHelper.getInputFromMandatoryEditText(rootView, R.id.nama_pelajaran),
                    hariselect,
                    jamawalintinput,
                    menitawalintinput,
                    jamakhirintinput,
                    menitakhirintinput,
                    jamawalinput,
                    jamakhirinput
            );
        } else {
            return new JadwalPelajaran(
                    showingSubject.getId(),
                    GuiHelper.getInputFromMandatoryEditText(rootView, R.id.nama_pelajaran),
                    hariselect,
                    jamawalintinput,
                    menitawalintinput,
                    jamakhirintinput,
                    menitakhirintinput,
                    jamawalinput,
                    jamakhirinput
            );
        }
    }

    private void initGUI() {
        if (!addMode) {
            GuiHelper.setTextToTextView(rootView, R.id.nama_pelajaran, showingSubject.getNamaPelajaran());
            GuiHelper.setTextToTextView(rootView, R.id.pelajaran_tanggal, showingSubject.getHari());
            hariselect = showingSubject.getHari();
            jamawalintinput = showingSubject.getJamAwalint();
            menitawalintinput = showingSubject.getMenitAwalint();
            jamakhirintinput = showingSubject.getJamAkhirint();
            menitakhirintinput = showingSubject.getMenitAkhirint();
            GuiHelper.setTextToTextView(rootView, R.id.pelajaran_jamawal, showingSubject.getJamAwal());
            jamawalinput = showingSubject.getJamAwal();
            GuiHelper.setTextToTextView(rootView, R.id.pelajaran_jamakhir, showingSubject.getJamAkhir());
            jamakhirinput = showingSubject.getJamAkhir();
            GuiHelper.setVisibility(rootView, R.id.fabdelete, View.VISIBLE);

            TextView test2 = rootView.findViewById(R.id.texthome);
            test2.setText("DETAIL JADWAL PELAJARAN");
        } else {
            GuiHelper.setVisibility(rootView, R.id.fabdelete, View.GONE);
        }
    }
}