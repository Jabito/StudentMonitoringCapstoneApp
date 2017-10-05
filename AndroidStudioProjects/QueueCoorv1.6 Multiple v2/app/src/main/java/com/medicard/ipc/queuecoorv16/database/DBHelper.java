package com.medicard.ipc.queuecoorv16.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IPC on 7/27/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }
    //WHEN TB IS CREATED
    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(Constants.CREATE_TB);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    //UPGRADE TB
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TB_NAME);
        onCreate(db);
    }
}