package com.jambi.macbookpro.smsapp.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IPC on 11/23/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    Context context;
    protected static final String databaseName = "StudentMonitoring";
    private static final int version = 1;

    public DatabaseHandler(Context context) {
        super(context, databaseName, null, version);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
