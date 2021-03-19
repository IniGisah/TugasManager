package com.cooleyah.tugasmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cooleyah.tugasmanager.activity.More;
import com.cooleyah.tugasmanager.activity.Stats;
import com.cooleyah.tugasmanager.activity.Timeline;
import com.cooleyah.tugasmanager.db.DatabaseHelper;
import com.cooleyah.tugasmanager.db.DatabaseHelperImpl;
import com.cooleyah.tugasmanager.db.Settings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class TugasAdd extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    TimePickerDialog picker;
    EditText eText, eText2, eText3;
    private DatabaseHelper dbHelper;
    private View rootView;
    private Tugas showingHomework;
    private JadwalPelajaran[] subjectsInSpinner;
    private boolean addMode;
    private Button dateButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private int day;
    private int month;
    private int year;
    private View view;
    private String date;
    private int jamakhirintinput;
    private int menitakhirintinput;
    private String jamakhirinput;

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
        setContentView(R.layout.activity_tugas_add);

        view = findViewById(R.id.tambahtugas_main);
        eText = (EditText) findViewById(R.id.tugas_jamakhir);

        dbHelper = new DatabaseHelperImpl(this);
        int homeworkID = getIntent().getIntExtra("HomeworkID", -1);
        if (homeworkID <= 0) {
            addMode = true;
        } else {
            addMode = false;
            showingHomework = dbHelper.getHomeworkAtId(homeworkID);
        }

        getDateForDatePicker();

        rootView = findViewById(R.id.tambahtugas_main);
        initGUI();

        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);

                picker = new TimePickerDialog(TugasAdd.this, R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                                eText.setText(String.format("%02d : %02d", sHour , sMinute));

                                //variable declaration
                                jamakhirintinput = sHour;
                                menitakhirintinput = sMinute;

                                //converter
                                //String s1 = Integer.toString(sHour);
                                //String s2 = Integer.toString(sMinute);

                                //String s = s1 + " : " + s2;

                                jamakhirinput = pad(sHour) + " : " + pad(sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
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
                    dbHelper.deleteHomeworkAtId(showingHomework.getId());
                    Toast.makeText(getApplicationContext(), "Jadwal Pelajaran Berhasil dihapus !", Toast.LENGTH_SHORT).show();
                } catch (IllegalArgumentException ignored) {
                }
                Intent refresh = new Intent(TugasAdd.this, Stats.class);
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
                        dbHelper.insertIntoDB(readHomeworkFromGUI());
                    } else {
                        dbHelper.updateHomeworkAtId(readHomeworkFromGUI());
                    }
                    Toast.makeText(view.getContext(), "Tugas Berhasil ditambahkan !", Toast.LENGTH_SHORT).show();
                    Intent refresh = new Intent(TugasAdd.this, Stats.class);
                    startActivity(refresh);
                    finish();
                } catch (IllegalArgumentException ignored) {
                }
            }
        });
    }

    private void initGUI() {
        if (!addMode) {
            GuiHelper.setTextToTextView(rootView, R.id.nama_tugas, showingHomework.getDeskripsi());
            GuiHelper.setTextToTextView(rootView, R.id.pelajaran_tanggal,
                    GuiHelper.extractGuiString(showingHomework.getDeadline(), false, rootView.getContext()));
            ((SwitchCompat) findViewById(R.id.homeworkDetails_switchDone)).setChecked(showingHomework.isDone());
            GuiHelper.setTextToTextView(rootView, R.id.tugas_jamakhir, showingHomework.getJamakhir());
            GuiHelper.setTextToTextView(rootView, R.id.tugas_catatan, showingHomework.getCatatan());
            jamakhirinput = showingHomework.getJamakhir();
            jamakhirintinput = showingHomework.getJamakhirint();
            menitakhirintinput = showingHomework.getMenitakhirint();

            TextView test2 = rootView.findViewById(R.id.texthome);
            test2.setText("DETAIL TUGAS");

            GuiHelper.setVisibility(rootView, R.id.fabdelete, View.VISIBLE);
            GuiHelper.setVisibility(rootView, R.id.homeworkDetails_switchDone, View.VISIBLE);
        } else {
            GuiHelper.setVisibility(rootView, R.id.fabdelete, View.GONE);
            GuiHelper.setVisibility(rootView, R.id.homeworkDetails_switchDone, View.INVISIBLE);
        }

        subjectsInSpinner = fillSpinner();

        //preselect spinner
        if (!addMode) {
            for (int i = 0; i < subjectsInSpinner.length; i++) {
                if (subjectsInSpinner[i].match(showingHomework.getSubject())) {
                    Spinner spinner = findViewById(R.id.pelajaran_tugas);
                    spinner.setSelection(i);
                }
            }
        }
        implementDatePicker();
    }

    private JadwalPelajaran[] fillSpinner() {
        ArrayList<String> subjectStrings = new ArrayList<>();
        ArrayList<JadwalPelajaran> subjectArrayList = new ArrayList<>();

        int[] subjectIndices = dbHelper.getIndices(DatabaseHelper.TABLE_PELAJARAN);

        for (int subjectIndex : subjectIndices) {
            JadwalPelajaran subject = dbHelper.getSubjectAtId(subjectIndex);

            subjectStrings.add(GuiHelper.extractGuiString(subject));
            subjectArrayList.add(subject);
        }

        if (subjectStrings.size() != 0) {
            GuiHelper.fillSpinnerFromArray(rootView, R.id.pelajaran_tugas, subjectStrings.toArray(new String[0]));
        } else {
            Toast.makeText(view.getContext(), "Jadwal Pelajaran Berhasil ditambahkan !", Toast.LENGTH_SHORT).show();
            findViewById(R.id.fabyes).setEnabled(false);
        }
        return subjectArrayList.toArray(new JadwalPelajaran[0]);
    }

    private void implementDatePicker() {
        dateButton = findViewById(R.id.pelajaran_tanggal);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        TugasAdd.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                updateDateForDatePicker(day, month, year);

                month = month + 1;
                dateButton.setText(formatDate(day, month, year));
            }
        };
    }

    private void updateDateForDatePicker(int day, int month, int year) {

        this.day = day;
        this.month = month;
        this.year = year;

    }

    private String formatDate(int day, int month, int year) {
        switch (Settings.getInstance(view.getContext()).getActiveDateFormat()) {
            case "DD.MM.YYYY":
                date = day + "." + month + "." + year;
                break;
            case "MM.DD.YYYY":
                date = month + "." + day + "." + year;
                break;
            case "YYYY.MM.DD":
                date = year + "." + month + "." + day;
        }
        return date;
    }

    private void getDateForDatePicker() {

        if (addMode) {
            Calendar cal = Calendar.getInstance();
            day = cal.get(Calendar.DAY_OF_MONTH);
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);
            System.out.println();
        } else {
            day = showingHomework.getDeadline().get(Calendar.DAY_OF_MONTH);
            month = showingHomework.getDeadline().get(Calendar.MONTH);
            year = showingHomework.getDeadline().get(Calendar.YEAR);
        }
    }

    private Tugas readHomeworkFromGUI() throws IllegalArgumentException {
        Spinner spinner = findViewById(R.id.pelajaran_tugas);
        SwitchCompat switchCompat = findViewById(R.id.homeworkDetails_switchDone);

        if (addMode) {
            return new Tugas(
                    -1,
                    subjectsInSpinner[spinner.getSelectedItemPosition()],
                    GuiHelper.getInputFromMandatoryEditText(rootView, R.id.nama_tugas),
                    GuiHelper.getDateFromMandatoryButton(rootView, R.id.pelajaran_tanggal),
                    false,
                    jamakhirinput,
                    jamakhirintinput,
                    menitakhirintinput,
                    GuiHelper.getInputFromMandatoryEditText(rootView, R.id.tugas_catatan)
            );
        } else {
            return new Tugas(
                    showingHomework.getId(),
                    subjectsInSpinner[spinner.getSelectedItemPosition()],
                    GuiHelper.getInputFromMandatoryEditText(rootView, R.id.nama_tugas),
                    GuiHelper.getDateFromMandatoryButton(rootView, R.id.pelajaran_tanggal),
                    switchCompat.isChecked(),
                    jamakhirinput,
                    jamakhirintinput,
                    menitakhirintinput,
                    GuiHelper.getInputFromMandatoryEditText(rootView, R.id.tugas_catatan)
            );
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}