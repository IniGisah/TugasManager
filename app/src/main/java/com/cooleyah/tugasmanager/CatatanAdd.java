package com.cooleyah.tugasmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cooleyah.tugasmanager.activity.MainActivity;
import com.cooleyah.tugasmanager.activity.More;
import com.cooleyah.tugasmanager.activity.Timeline;
import com.cooleyah.tugasmanager.db.DatabaseHelper;
import com.cooleyah.tugasmanager.db.DatabaseHelperImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CatatanAdd extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private DatabaseHelper dbHelper;
    private Catatan showingCatatan;
    private String isicatatan;
    private boolean addMode;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan_add);
        rootView = findViewById(R.id.tambahcatatan_main);

        dbHelper = new DatabaseHelperImpl(this);

        int catatanId = getIntent().getIntExtra("CatatanID", -1);
        if (catatanId <= 0) {
            addMode = true;
        } else {
            addMode = false;
            showingCatatan = dbHelper.getCatatanAtId(catatanId);
        }

        initGUI();


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
                    dbHelper.deleteCatatanAtId(showingCatatan.getId());
                    Toast.makeText(getApplicationContext(), "Catatan Berhasil dihapus !", Toast.LENGTH_SHORT).show();
                } catch (IllegalArgumentException ignored) {
                }
                Intent refresh = new Intent(CatatanAdd.this, More.class);
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
                        dbHelper.insertIntoDB(readCatatanFromGUI());
                    } else {
                        dbHelper.updateCatatanAtId(readCatatanFromGUI());
                    }
                    Toast.makeText(view.getContext(), "Catatan Berhasil ditambahkan !", Toast.LENGTH_SHORT).show();
                    Intent refresh = new Intent(CatatanAdd.this, More.class);
                    startActivity(refresh);
                    finish();
                } catch (IllegalArgumentException ignored) {
                }
            }
        });
    }

    private void initGUI() {
        if (!addMode) {
            GuiHelper.setTextToTextView(rootView, R.id.judul_catatan, showingCatatan.getJudul());
            GuiHelper.setTextToTextView(rootView, R.id.catatan_catatan, showingCatatan.getCatatan());
            TextView test2 = rootView.findViewById(R.id.texthome);
            test2.setText("DETAIL CATATAN");
            GuiHelper.setVisibility(rootView, R.id.fabdelete, View.VISIBLE);
        } else {
            GuiHelper.setVisibility(rootView, R.id.fabdelete, View.GONE);
        }
    }

    private Catatan readCatatanFromGUI() throws IllegalArgumentException {
        if (addMode) {
            return new Catatan(
                    -1,
                    GuiHelper.getInputFromMandatoryEditText(rootView, R.id.judul_catatan),
                    GuiHelper.getInputFromMandatoryEditText(rootView, R.id.catatan_catatan)
            );
        } else {
            return new Catatan(
                    showingCatatan.getId(),
                    GuiHelper.getInputFromMandatoryEditText(rootView, R.id.judul_catatan),
                    GuiHelper.getInputFromMandatoryEditText(rootView, R.id.catatan_catatan)
            );
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
