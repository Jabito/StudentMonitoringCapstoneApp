package com.capstone.mapua.studentmonitoringapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.capstone.mapua.studentmonitoringapp.model.Announcement;
import com.capstone.mapua.studentmonitoringapp.model.TapLog;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created from Android studio on 11/23/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    Context context;
    protected static final String databaseName = "StudentMonitoring";
    private static final int version = 1;

    private String tapLogTable = "tapLogTable";
    private String rfid = "rfid";
    private String logType = "logType";
    private String logDateTime = "logDateTime";

    private String announcementTable = "announcementTable";
    private String messageTypeId = "messageTypeId";
    private String message = "message";
    private String postedBy = "postedBy";
    private String datePosted = "datePosted";
    private String messageTarget = "messageTarget";
    private String parentIds = "parentIds";



    public DatabaseHandler(Context context) {
        super(context, databaseName, null, version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql1 = "";
        sql1 += "CREATE TABLE " + tapLogTable;
        sql1 += " ( ";
        sql1 += rfid + " TEXT, ";
        sql1 += logType + " TEXT, ";
        sql1 += logDateTime + " TEXT ";
        sql1 += " ) ";
        db.execSQL(sql1);

        sql1 = "";
        sql1 += "CREATE TABLE " + announcementTable;
        sql1 += " ( ";
        sql1 += messageTypeId + " TEXT, ";
        sql1 += message + " TEXT, ";
        sql1 += postedBy + " TEXT, ";
        sql1 += datePosted + " TEXT, ";
        sql1 += messageTarget + " TEXT, ";
        sql1 += parentIds + " TEXT ";
        sql1 += " ) ";
        db.execSQL(sql1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {
            Log.d("DATABASE", "new version");
            String sql1 = "DROP TABLE IF EXISTS " + tapLogTable;
            String sql2 = "DROP TABLE IF EXISTS " + announcementTable;
            db.execSQL(sql1);
            db.execSQL(sql2);
            onCreate(db);
        } else
            Log.d("DATABASE", "old version");

    }

    public void createTagLogList(TapLog tapLog) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put(rfid, tapLog.getRfid());
        cv.put(logType, tapLog.getLogType());
        cv.put(logDateTime, tapLog.getLogDateTime());
        db.insert(tapLogTable, null, cv);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public ArrayList<TapLog> getTapLogList() {
        ArrayList<TapLog> tapLogList = new ArrayList<>();

        String sql = "";
        sql += "SELECT * FROM " + tapLogTable;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                TapLog insertion = new TapLog();
                insertion.setRfid(cursor.getString(cursor.getColumnIndex(rfid)));
                insertion.setLogType(cursor.getString(cursor.getColumnIndex(logType)));
                insertion.setLogDateTime(cursor.getString(cursor.getColumnIndex(logDateTime)));
                tapLogList.add(insertion);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();


        return tapLogList;
    }

    public void deteleLogData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + tapLogTable;
        db.execSQL(sql);
    }

    public void deteleAnnouncementData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + announcementTable;
        db.execSQL(sql);
    }

    public void createAnnouncementList(Announcement announcement) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put(messageTypeId, announcement.getMessageTarget());
        cv.put(message, announcement.getMessage());
        cv.put(postedBy, announcement.getPostedBy());
        cv.put(datePosted, announcement.getDatePosted());
        cv.put(messageTarget, announcement.getMessageTarget());
        db.insert(announcementTable, null, cv);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public ArrayList<Announcement>  getAnnouncementList() {
        ArrayList<Announcement> announcementArrayList = new ArrayList<>();

        String sql = "";
        sql += "SELECT * FROM " + announcementTable;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Announcement insertion = new Announcement();
                insertion.setMessageTypeId(cursor.getString(cursor.getColumnIndex(messageTypeId)));
                insertion.setMessage(cursor.getString(cursor.getColumnIndex(message)));
                insertion.setPostedBy(cursor.getString(cursor.getColumnIndex(postedBy)));
                insertion.setDatePosted(cursor.getString(cursor.getColumnIndex(datePosted)));
                insertion.setMessageTarget(cursor.getString(cursor.getColumnIndex(messageTarget)));
                announcementArrayList.add(insertion);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return   announcementArrayList;
    }
}
