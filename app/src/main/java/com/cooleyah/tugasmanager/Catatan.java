package com.cooleyah.tugasmanager;

public class Catatan {

    private final int id;
    private final String judul;
    private final String isicatatan;


    public Catatan(int id, String judul, String isicatatan) {
        this.id = id;
        this.judul = judul;
        this.isicatatan = isicatatan;
    }

    public int getId() {
        return id;
    }

    public String getJudul(){
        return judul;
    }

    public String getCatatan(){
        return isicatatan;
    }
}
