package com.cooleyah.tugasmanager;

public class JadwalPelajaran {

    private final int id;
    private final String namaPelajaran;
    private final String hari;
    private final int jamAwalint;
    private final int menitAwalint;
    private final int jamAkhirint;
    private final int menitAkhirint;
    private final String jamAwal;
    private final String jamAkhir;

    public JadwalPelajaran(int id, String namaPelajaran, String hari, int jamAwalint, int menitAwalint , int jamAkhirint, int menitAkhirint, String jamAwal, String jamAkhir) {
        this.id = id;
        this.namaPelajaran = namaPelajaran;
        this.hari = hari;
        this.jamAwalint = jamAwalint;
        this.menitAwalint = menitAwalint;
        this.jamAkhirint = jamAkhirint;
        this.menitAkhirint = menitAkhirint;
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

    public int getJamAwalint() {
        return jamAwalint;
    }

    public int getMenitAwalint() {
        return menitAwalint;
    }

    public int getJamAkhirint() {
        return jamAkhirint;
    }

    public int getMenitAkhirint() {
        return menitAkhirint;
    }

    public String getJamAwal() {
        return jamAwal;
    }

    public String getJamAkhir() {
        return jamAkhir;
    }

    public boolean match(JadwalPelajaran otherSubject) {
        return this.id == otherSubject.id &&
                this.namaPelajaran.equals(otherSubject.namaPelajaran)
                && this.hari.equals(otherSubject.hari);
    }
}
