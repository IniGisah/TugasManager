package com.cooleyah.tugasmanager.db;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cooleyah.tugasmanager.JadwalPelajaran;

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
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase database = super.getWritableDatabase();
        database.setForeignKeyConstraintsEnabled(true);

        return database;
    }

    private void createSubjectTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                PELAJARAN_KOLOM_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                PELAJARAN_KOLOM_NAMA + " VARCHAR NOT NULL, " +
                PELAJARAN_KOLOM_HARI + " VARCHAR NOT NULL, " +
                PELAJARAN_KOLOM_JAMAWAL + " INTEGER NOT NULL, " +
                PELAJARAN_KOLOM_JAMAKHIR + " INTEGER NOT NULL )"
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
    public JadwalPelajaran getSubjectAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_NAME, PELAJARAN_KOLOM_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return new JadwalPelajaran(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            );
        } catch (Exception e) {
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
        db.execSQL("DROP TABLE " + TABLE_NAME);
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
    public int insertIntoDBOrThrow(JadwalPelajaran subject) throws IllegalAccessException {

        SQLiteDatabase db = this.getWritableDatabase();

        int subjectId;
        if (subject.getId() <= 0) {
            subjectId = getNewID(TABLE_NAME, PELAJARAN_KOLOM_ID);
        } else {
            subjectId = subject.getId();
        }

        String query = "INSERT INTO " + TABLE_NAME + " VALUES ( " + subjectId + ", \"" + subject.getNamaPelajaran() + "\" , \"" + subject.getHari() + "\" , \"" + subject.getJamAwal() + "\" , \"" + subject.getJamAkhir() + "\" )";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }
        return subjectId;
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
    public void updateSubjectAtIdOrThrow(JadwalPelajaran newSubject) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " +
                    PELAJARAN_KOLOM_NAMA + " = \"" + newSubject.getNamaPelajaran() + "\", " +
                    PELAJARAN_KOLOM_HARI + " = \"" + newSubject.getHari() + "\", " +
                    PELAJARAN_KOLOM_JAMAWAL + " = \"" + newSubject.getJamAwal() + "\", " +
                    PELAJARAN_KOLOM_JAMAKHIR + " = \"" + newSubject.getJamAkhir() + "\" " +
                    "WHERE " + PELAJARAN_KOLOM_ID + " = " + newSubject.getId()
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
    public void deleteSubjectAtIdOrThrow(int id) throws NoSuchFieldException {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE " + PELAJARAN_KOLOM_ID + " = " + id;
            SQLiteDatabase db = new DatabaseHelperImpl(context).getWritableDatabase();
            db.execSQL(query);
                if (activity != null) {
                    activity.finish();
                }
    }
}
