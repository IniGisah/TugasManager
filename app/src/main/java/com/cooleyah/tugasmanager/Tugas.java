package com.cooleyah.tugasmanager;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Tugas {

    private final int id;
    private final JadwalPelajaran jadwalPelajaran;
    private final String deskripsi;
    private final GregorianCalendar deadline;
    private final boolean done;
    private final String jamakhir;
    private final int jamakhirint;
    private final int menitakhirint;
    private final String catatan;

    public Tugas(int id, JadwalPelajaran jadwalPelajaran, String deskripsi, GregorianCalendar deadline, boolean done, String jamakhir, int jamakhirint, int menitakhirint, String catatan) {
        this.id = id;
        this.jadwalPelajaran = jadwalPelajaran;
        this.deskripsi = deskripsi;
        this.deadline = deadline;
        this.done = done;
        this.jamakhir = jamakhir;
        this.jamakhirint = jamakhirint;
        this.menitakhirint = menitakhirint;
        this.catatan = catatan;
    }

    public Tugas(int id,JadwalPelajaran jadwalPelajaran, String deskripsi, String deadline, boolean done, String jamakhir, int jamakhirint, int menitakhirint, String catatan) {
        this.id = id;
        this.jadwalPelajaran = jadwalPelajaran;
        this.deskripsi = deskripsi;
        this.deadline = convertDateStringToGregorianCalendar(deadline);
        this.done = done;
        this.jamakhir = jamakhir;
        this.jamakhirint = jamakhirint;
        this.menitakhirint = menitakhirint;
        this.catatan = catatan;
    }

    public int getId() {
        return id;
    }

    public JadwalPelajaran getSubject() {
        return jadwalPelajaran;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public GregorianCalendar getDeadline() {
        return deadline;
    }

    public String getDeadlineAsDatabaseString() {
        return deadline.get(Calendar.YEAR) + "-" + (deadline.get(Calendar.MONTH) + 1) + "-" + deadline.get(Calendar.DAY_OF_MONTH);
    }

    public boolean isDone() {
        return done;
    }

    public int getDone() {
        if (done) {
            return 1;
        } else {
            return 0;
        }
    }

    public String getJamakhir(){
        return jamakhir;
    }

    public int getJamakhirint(){
        return jamakhirint;
    }

    public int getMenitakhirint(){
        return menitakhirint;
    }

    public String getCatatan(){
        return catatan;
    }

    @Override
    public String toString() {
        return "---Homework--- \n" +
                "Id: \t" + id + "\n" +
                jadwalPelajaran.toString() + "\n" +
                "Description: \t" + deskripsi + "\n" +
                "Deadline: \t" + getDeadlineAsDatabaseString() + "\n" +
                "Done: \t" + done + "\n" +
                "---####---";
    }

    private GregorianCalendar convertDateStringToGregorianCalendar(String source) {
        String[] date = source.split("-");

        return new GregorianCalendar(
                Integer.parseInt(date[0]),
                Integer.parseInt(date[1]) - 1,
                Integer.parseInt(date[2])
        );
    }

}
