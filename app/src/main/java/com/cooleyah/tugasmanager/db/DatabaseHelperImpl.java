package com.cooleyah.tugasmanager.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cooleyah.tugasmanager.Catatan;
import com.cooleyah.tugasmanager.JadwalPelajaran;
import com.cooleyah.tugasmanager.Tugas;

import java.util.ArrayList;

public class DatabaseHelperImpl extends SQLiteOpenHelper implements DatabaseHelper {
    private final Context context;
    private Activity activity = null;

    public DatabaseHelperImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public DatabaseHelperImpl(Activity activity) {
        super(activity, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = activity;
        this.activity = activity;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        createSubjectTable(db);
        createHomeworkTable(db);
        createCatatanTable(db);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase database = super.getWritableDatabase();
        database.setForeignKeyConstraintsEnabled(true);

        return database;
    }

    private void createSubjectTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PELAJARAN + "(" +
                PELAJARAN_KOLOM_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                PELAJARAN_KOLOM_NAMA + " VARCHAR NOT NULL, " +
                PELAJARAN_KOLOM_HARI + " VARCHAR NOT NULL, " +
                PELAJARAN_KOLOM_JAMAWALint + " INTEGER NOT NULL, " +
                PELAJARAN_KOLOM_MENITAWALint + " INTEGER NOT NULL, " +
                PELAJARAN_KOLOM_JAMAKHIRint + " INTEGER NOT NULL, " +
                PELAJARAN_KOLOM_MENITAKHIRint + " INTEGER NOT NULL, " +
                PELAJARAN_KOLOM_JAMAWAL + " VARCHAR NOT NULL, " +
                PELAJARAN_KOLOM_JAMAKHIR + " VARCHAR NOT NULL )"
        );
    }

    private void createHomeworkTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TUGAS + "(" +
                TUGAS_KOLOM_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                TUGAS_KOLOM_PELAJARAN_ID + " INTEGER NOT NULL " +
                "REFERENCES " + TABLE_PELAJARAN + "(" + PELAJARAN_KOLOM_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                TUGAS_KOLOM_DESKRIPSI + " TEXT NOT NULL, " +
                TUGAS_KOLOM_DEADLINE + " DATE NOT NULL, " +
                TUGAS_KOLOM_DONE + " INTEGER, "+
                TUGAS_KOLOM_JAMAKHIR + " VARCHAR NOT NULL, "+
                TUGAS_KOLOM_JAMAKHIRint + " INTEGER NOT NULL, "+
                TUGAS_KOLOM_MENITAKHIRint + " INTEGER NOT NULL, "+
                TUGAS_KOLOM_CATATAN + " TEXT NOT NULL)"
        );
    }

    private void createCatatanTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CATATAN + "(" +
                CATATAN_KOLOM_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                CATATAN_KOLOM_JUDUL + " VARCHAR NOT NULL, " +
                CATATAN_KOLOM_ISICATATAN + " VARCHAR NOT NULL )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAllTables(db);
        onCreate(db);
    }

    @Override
    public JadwalPelajaran getSubjectAtId(int id) {
        try {
            return getSubjectAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Subject", context);
            return null;
        }
    }

    @Override
    public Tugas getHomeworkAtId(int id) {
        try {
            return getHomeworkAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Homework", context);
            return null;
        }
    }

    @Override
    public Catatan getCatatanAtId(int id){
        try{
            return getCatatanAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Catatan", context);
            return null;
        }
    }

    @Override
    public JadwalPelajaran getSubjectAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_PELAJARAN, PELAJARAN_KOLOM_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return new JadwalPelajaran(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getString(8)
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    @Override
    public Tugas getHomeworkAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_TUGAS, TUGAS_KOLOM_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            if (cursor.getInt(4) == 0) {
                return new Tugas(
                        cursor.getInt(0),
                        getSubjectAtIdOrThrow(cursor.getInt(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        false,
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getInt(7),
                        cursor.getString(8)
                );
            } else {
                return new Tugas(
                        cursor.getInt(0),
                        getSubjectAtIdOrThrow(cursor.getInt(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        true,
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getInt(7),
                        cursor.getString(8)
                );
            }

        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    @Override
    public Catatan getCatatanAtIdOrThrow(int id) throws NoSuchFieldException{

        String query = buildQueryToGetRowAtId(TABLE_CATATAN, CATATAN_KOLOM_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return new Catatan(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
        } catch (Exception e){
            throw new NoSuchFieldException();
        }
    }

    private String buildQueryToGetRowAtId(String table, String tableColumnID, int id) {
        return "SELECT * " +
                "FROM " + table +
                " WHERE " + tableColumnID +
                " = " + id;
    }

    private void dropAllTables(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(false);
        db.execSQL("DROP TABLE " + TABLE_PELAJARAN);
        db.execSQL("DROP TABLE " + TABLE_TUGAS);
    }

    private int getNewID(String tableName, String idColumnName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(" + idColumnName + ") FROM " + tableName;

        try (Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return cursor.getInt(0) + 1;
        }
    }

    @Override
    public int insertIntoDB(JadwalPelajaran subject) {
        try {
            return insertIntoDBOrThrow(subject);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(subject, context);
            return -1;
        }
    }

    @Override
    public int insertIntoDB(Tugas homework) {
        try {
            return insertIntoDBOrThrow(homework);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(homework, context);
            return -1;
        }
    }

    @Override
    public int insertIntoDB(Catatan catatan){
        try {
            return insertIntoDBOrThrow(catatan);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(catatan, context);
            return -1;
        }
    }

    @Override
    public int insertIntoDBOrThrow(JadwalPelajaran subject) throws IllegalAccessException {

        SQLiteDatabase db = this.getWritableDatabase();

        int subjectId;
        if (subject.getId() <= 0) {
            subjectId = getNewID(TABLE_PELAJARAN, PELAJARAN_KOLOM_ID);
        } else {
            subjectId = subject.getId();
        }

        String query = "INSERT INTO " + TABLE_PELAJARAN + " VALUES ( " + subjectId + ", \"" + subject.getNamaPelajaran() + "\" , \"" + subject.getHari() + "\" , \"" + subject.getJamAwalint() + "\" , \"" + subject.getMenitAwalint() + "\" , \"" + subject.getJamAkhirint() + "\" , \"" + subject.getMenitAkhirint() + "\" , \"" + subject.getJamAwal() + "\" , \"" + subject.getJamAkhir() + "\" )";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }
        return subjectId;
    }

    @Override
    public int insertIntoDBOrThrow(Tugas homework) throws IllegalAccessException {
        try {
            getSubjectAtIdOrThrow(homework.getSubject().getId());
        } catch (NoSuchFieldException e) {
            insertIntoDBOrThrow(homework.getSubject());
        }

        SQLiteDatabase db = this.getWritableDatabase();

        int homeworkId;
        if (homework.getId() <= 0) {
            homeworkId = getNewID(TABLE_TUGAS, TUGAS_KOLOM_ID);
        } else {
            homeworkId = homework.getId();
        }

        String query = "INSERT INTO " + TABLE_TUGAS + " VALUES ( " + homeworkId + ", " + homework.getSubject().getId() + ", \"" + homework.getDeskripsi() + "\", \"" + homework.getDeadlineAsDatabaseString() + "\", " + homework.getDone() + ", \"" + homework.getJamakhir() + "\" , \"" + homework.getJamakhirint() + "\" , \"" + homework.getMenitakhirint() + "\" , \"" + homework.getCatatan() + "\")";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }

        return homeworkId;
    }

    @Override
    public int insertIntoDBOrThrow(Catatan catatan) throws IllegalAccessException {

        SQLiteDatabase db = this.getWritableDatabase();

        int catatanId;
        if (catatan.getId() <=0 ) {
            catatanId = getNewID(TABLE_CATATAN, CATATAN_KOLOM_ID);
        } else {
            catatanId = catatan.getId();
        }

        String query = "INSERT INTO " + TABLE_CATATAN + " VALUES ( " + catatanId + ", \"" + catatan.getJudul() + "\" , \"" + catatan.getCatatan() + "\" )";

        try {
            db.execSQL(query);
        } catch (Exception e ) {
            throw new IllegalAccessException();
        }
        return catatanId;
    }

    @Override
    public void updateSubjectAtId(JadwalPelajaran newSubject) {
        try {
            updateSubjectAtIdOrThrow(newSubject);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Subject", context);
        }
    }

    @Override
    public void updateHomeworkAtId(Tugas newHomework) {
        try {
            updateHomeworkAtIdOrThrow(newHomework);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Homework", context);
        }
    }

    @Override
    public void updateCatatanAtId(Catatan newCatatan) {
        try {
            updateCatatanAtIdOrThrow(newCatatan);
        } catch (NoSuchFieldException e ) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Catatan", context);
        }
    }

    @Override
    public void updateSubjectAtIdOrThrow(JadwalPelajaran newSubject) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_PELAJARAN + " SET " +
                    PELAJARAN_KOLOM_NAMA + " = \"" + newSubject.getNamaPelajaran() + "\", " +
                    PELAJARAN_KOLOM_HARI + " = \"" + newSubject.getHari() + "\", " +
                    PELAJARAN_KOLOM_JAMAWALint + " = \"" + newSubject.getJamAwalint() + "\", " +
                    PELAJARAN_KOLOM_MENITAWALint + " = \"" + newSubject.getMenitAwalint() + "\", " +
                    PELAJARAN_KOLOM_JAMAKHIRint + " = \"" + newSubject.getJamAkhirint() + "\", " +
                    PELAJARAN_KOLOM_MENITAKHIRint + " = \"" + newSubject.getMenitAkhirint() + "\", " +
                    PELAJARAN_KOLOM_JAMAWAL + " = \"" + newSubject.getJamAwal() + "\", " +
                    PELAJARAN_KOLOM_JAMAKHIR + " = \"" + newSubject.getJamAkhir() + "\" " +
                    "WHERE " + PELAJARAN_KOLOM_ID + " = " + newSubject.getId()
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    @Override
    public void updateHomeworkAtIdOrThrow(Tugas newHomework) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_TUGAS + " SET " +
                    TUGAS_KOLOM_PELAJARAN_ID + " = " + newHomework.getSubject().getId() + ", " +
                    TUGAS_KOLOM_DESKRIPSI + " = \"" + newHomework.getDeskripsi() + "\", " +
                    TUGAS_KOLOM_DEADLINE + " = \"" + newHomework.getDeadlineAsDatabaseString() + "\", " +
                    TUGAS_KOLOM_DONE + " = " + newHomework.getDone() + " ," +
                    TUGAS_KOLOM_JAMAKHIR + " = \"" + newHomework.getJamakhir() + "\", " +
                    TUGAS_KOLOM_JAMAKHIRint + " = \"" + newHomework.getJamakhirint() + "\", " +
                    TUGAS_KOLOM_MENITAKHIRint + " = \"" + newHomework.getMenitakhirint() + "\", " +
                    TUGAS_KOLOM_CATATAN + " = \"" + newHomework.getCatatan() + "\" " +
                    "WHERE " + TUGAS_KOLOM_ID + " = " + newHomework.getId()
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    @Override
    public void updateCatatanAtIdOrThrow(Catatan newCatatan) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_CATATAN + " SET " +
                    CATATAN_KOLOM_JUDUL + " = \"" + newCatatan.getJudul() + "\"," +
                    CATATAN_KOLOM_ISICATATAN + " = \"" + newCatatan.getCatatan() + "\" " +
                    "WHERE " + CATATAN_KOLOM_ID + " = " +newCatatan.getId()
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    @Override
    public void deleteSubjectAtId(int id) {
        try {
            deleteSubjectAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }

    @Override
    public void deleteHomeworkAtId(int id) {
        try {
            deleteHomeworkAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }

    @Override
    public void deleteCatatanAtId(int id) {
        try {
            deleteCatatanAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }

    @Override
    public void deleteSubjectAtIdOrThrow(int id) throws NoSuchFieldException {
        final String query = "DELETE FROM " + TABLE_PELAJARAN + " WHERE " + PELAJARAN_KOLOM_ID + " = " + id;
            SQLiteDatabase db = new DatabaseHelperImpl(context).getWritableDatabase();
            db.execSQL(query);
                if (activity != null) {
                    activity.finish();
                }
    }

    @Override
    public void deleteHomeworkAtIdOrThrow(int id) throws NoSuchFieldException {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_TUGAS + " WHERE " + TUGAS_KOLOM_ID + " = " + id;

        try {
            db.execSQL(query);

            if (activity != null) {
                activity.finish();
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    @Override
    public void deleteCatatanAtIdOrThrow(int id) throws NoSuchFieldException {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_CATATAN + " WHERE " + CATATAN_KOLOM_ID + " = " + id;

        try{
            db.execSQL(query);

            if (activity !=null){
                activity.finish();
            }
        } catch (Exception e){
            throw new NoSuchFieldException();
        }
    }

    @Override
    public int[] getIndices(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * " + "FROM " + tableName;

        ArrayList<Integer> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            arrayList.add(cursor.getInt(0));
        }

        cursor.close();
        db.close();


        int[] returningArray = new int[arrayList.size()];
        for (int i = 0; i < returningArray.length; i++) {
            returningArray[i] = arrayList.get(i);
        }

        return returningArray;
    }
}
