package com.cooleyah.tugasmanager.db;

import android.content.Context;
import android.widget.Toast;


class ExceptionHandler {

    static void handleDatabaseExceptionForGettingANotExistingObject(String objectTypeName, Context context) {
        Toast.makeText(context, "Could not get " + objectTypeName + " from Database. Maybe you have not created this" + objectTypeName + " before.", Toast.LENGTH_LONG).show();
    }

    static void handleDatabaseExceptionForAddingAAlreadyExistingObject(Object newObject, Context context) {
        Toast.makeText(context, "Could not add \n" + newObject.toString() + "\nto Database. Maybe you are trying to add an already existing Object", Toast.LENGTH_LONG).show();
    }

    static void handleDatabaseExceptionForUpdatingAnNotExistingObject(String objectTypeName, Context context) {
        Toast.makeText(context, "Could not update " + objectTypeName + " in Database. Maybe you have not created this " + objectTypeName + " before.", Toast.LENGTH_LONG).show();
    }

    static void handleDatabaseExceptionForDeletingAnNotExistingObject(int objectId, Context context) {
        Toast.makeText(context, "Could not delete " + objectId + " in Database. Maybe you have not created this " + objectId + " before.", Toast.LENGTH_LONG).show();
    }

}