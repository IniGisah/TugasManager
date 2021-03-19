package com.cooleyah.tugasmanager.db;

import com.cooleyah.tugasmanager.Catatan;
import com.cooleyah.tugasmanager.JadwalPelajaran;
import com.cooleyah.tugasmanager.Tugas;

import java.sql.Struct;

public interface DatabaseHelper {

    int DATABASE_VERSION = 1;

    String DATABASE_NAME = "TugasManager.db";


    String TABLE_PELAJARAN = "pelajaran";
    String PELAJARAN_KOLOM_ID = "pelajaran_id";
    String PELAJARAN_KOLOM_NAMA  = "pelajaran_nama";
    String PELAJARAN_KOLOM_HARI  = "pelajaran_hari";
    String PELAJARAN_KOLOM_JAMAWALint = "pelajaran_jam_awal_int";
    String PELAJARAN_KOLOM_MENITAWALint = "pelajaran_menit_awal_int";
    String PELAJARAN_KOLOM_JAMAKHIRint = "pelajaran_jam_akhir_int";
    String PELAJARAN_KOLOM_MENITAKHIRint = "pelajaran_menit_akhir_int";
    String PELAJARAN_KOLOM_JAMAWAL = "pelajaran_jam_awal";
    String PELAJARAN_KOLOM_JAMAKHIR = "pelajaran_jam_akhir";


    String TABLE_TUGAS = "tugas";
    String TUGAS_KOLOM_ID = "tugas_id";
    String TUGAS_KOLOM_PELAJARAN_ID = "tugas_pelajaran_id";
    String TUGAS_KOLOM_DESKRIPSI = "tugas_deskripsi";
    String TUGAS_KOLOM_DEADLINE = "tugas_deadline";
    String TUGAS_KOLOM_DONE = "tugas_done";
    String TUGAS_KOLOM_JAMAKHIR = "tugas_jam_akhir";
    String TUGAS_KOLOM_JAMAKHIRint = "tugas_jam_akhir_int";
    String TUGAS_KOLOM_MENITAKHIRint = "tugas_menit_akhir_int";
    String TUGAS_KOLOM_CATATAN = "tugas_catatan";


    String TABLE_CATATAN = "catatan";
    String CATATAN_KOLOM_ID = "catatan_id";
    String CATATAN_KOLOM_JUDUL = "catatan_judul";
    String CATATAN_KOLOM_ISICATATAN = "catatan_isi_catatan";

    //Jadwal Pelajaran
    JadwalPelajaran getSubjectAtId(int id);

    JadwalPelajaran getSubjectAtIdOrThrow(int id) throws NoSuchFieldException;

    int insertIntoDB(JadwalPelajaran subject);

    int insertIntoDBOrThrow(JadwalPelajaran subject) throws IllegalAccessException;

    void updateSubjectAtId(JadwalPelajaran newSubject);

    void updateSubjectAtIdOrThrow(JadwalPelajaran newSubject) throws NoSuchFieldException;

    void deleteSubjectAtId(int id);

    void deleteSubjectAtIdOrThrow(int id) throws NoSuchFieldException;

    //Tugas
    Tugas getHomeworkAtId(int id);

    Tugas getHomeworkAtIdOrThrow(int id) throws NoSuchFieldException;

    int insertIntoDB(Tugas homework);

    void updateHomeworkAtId(Tugas newHomework);

    void deleteHomeworkAtId(int id);

    void updateHomeworkAtIdOrThrow(Tugas newHomework) throws NoSuchFieldException;

    int insertIntoDBOrThrow(Tugas homework) throws IllegalAccessException;

    void deleteHomeworkAtIdOrThrow(int id) throws NoSuchFieldException;

    int[] getIndices(String tableName);

    //Catatan
    Catatan getCatatanAtId(int id);

    Catatan getCatatanAtIdOrThrow(int id) throws NoSuchFieldException;

    int insertIntoDB(Catatan catatan);

    int insertIntoDBOrThrow(Catatan catatan) throws IllegalAccessException;

    void updateCatatanAtId(Catatan newCatatan);

    void updateCatatanAtIdOrThrow(Catatan newCatatan) throws NoSuchFieldException;

    void deleteCatatanAtId(int id);

    void deleteCatatanAtIdOrThrow(int id) throws NoSuchFieldException;
}
