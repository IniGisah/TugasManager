package com.cooleyah.tugasmanager;

import android.os.Parcel;
import android.os.Parcelable;

public class JadwalPelajaran {

    private final int id;
    private final String namaPelajaran;
    private final String hari;
    private final int jamAwal;
    private final int jamAkhir;

    public JadwalPelajaran(int id, String namaPelajaran, String hari, int jamAwal, int jamAkhir) {
        this.id = id;
        this.namaPelajaran = namaPelajaran;
        this.hari = hari;
        this.jamAwal = jamAwal;
        this.jamAkhir = jamAkhir;
    }




    public int getId() {
        return id;
    }

    public String getNamaPelajaran() {
        return namaPelajaran;
    }

    public String getHari() {
        return hari;
    }

    public int getJamAwal() {
        return jamAwal;
    }

    public int getJamAkhir() {
        return jamAkhir;
    }

}
