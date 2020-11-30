package com.cooleyah.tugasmanager.db;

import com.cooleyah.tugasmanager.JadwalPelajaran;

public interface DatabaseHelper {

    int DATABASE_VERSION = 1;

    String DATABASE_NAME = "TugasManager.db";

    String TABLE_NAME = "pelajaran";

    String PELAJARAN_KOLOM_ID = "pelajaran_id";

    String PELAJARAN_KOLOM_NAMA  = "pelajaran_nama";

    String PELAJARAN_KOLOM_HARI  = "pelajaran_hari";

    String PELAJARAN_KOLOM_JAMAWAL  = "pelajaran_jam_awal";

    String PELAJARAN_KOLOM_JAMAKHIR  = "pelajaran_jam_akhir";


    JadwalPelajaran getSubjectAtId(int id);

    JadwalPelajaran getSubjectAtIdOrThrow(int id) throws NoSuchFieldException;

    int insertIntoDB(JadwalPelajaran subject);

    int insertIntoDBOrThrow(JadwalPelajaran subject) throws IllegalAccessException;

    void updateSubjectAtId(JadwalPelajaran newSubject);

    void updateSubjectAtIdOrThrow(JadwalPelajaran newSubject) throws NoSuchFieldException;

    void deleteSubjectAtId(int id);

    void deleteSubjectAtIdOrThrow(int id) throws NoSuchFieldException;
}
