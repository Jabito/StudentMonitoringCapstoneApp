package com.medicard.ipc.queuecoorv16.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.medicard.ipc.queuecoorv16.Mem_Activity;

/**
 * Created by IPC on 7/27/2017.
 */

public class DBAdapter {
    Context c;
    SQLiteDatabase db ;
    DBHelper helper;
    public DBAdapter(Context ctx)
    {
        this.c=ctx;
        helper=new DBHelper(c);
    }
    //OPEN DB
    public DBAdapter openDB()
    {
        try
        {
            db = helper.getWritableDatabase();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return this;
    }
    //CLOSE
    public void close()
    {
        try
        {
            helper.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    //INSERT DATA TO DB
    public long add(String queue, String name, String mobile, String code,String card, String date, String status)
    {
        try
        {
            db = helper.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put(Constants.MEM_QUEUE,queue);
            cv.put(Constants.MEM_NAME, name);
            cv.put(Constants.MEM_MOBILE,mobile);
            cv.put(Constants.MEM_CODE, code);
            cv.put(Constants.MEM_HEALTH_CARD, card);
            cv.put(Constants.MEM_DATE,date);
            cv.put(Constants.MEM_STATUS, status);
            return db.insert(Constants.TB_NAME,Constants.ROW_ID,cv);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }


    //RETRIEVE ALL PLAYERS


    public int getLastId() {
        int _id = 0;
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {Constants.MEM_QUEUE};
        Cursor cursor = db.query(Constants.TB_NAME,columns, null, null, null, null, null);
        try {
            if (cursor.moveToLast()) {
            _id = cursor.getInt(0);
            }
        } finally {
        cursor.close();
            db.close();
        }

        return _id;
    }


    public Cursor getAllQueueMember()
    {
        String[] columns={Constants.ROW_ID,Constants.MEM_QUEUE,Constants.MEM_NAME,Constants.MEM_MOBILE,Constants.MEM_CODE,Constants.MEM_HEALTH_CARD,Constants.MEM_DATE,Constants.MEM_STATUS};
        String whereClause = "status = ?";
        String[] whereArgs = new String[] {
                "queue"};
        String orderClause = "date DESC";
        return db.query(Constants.TB_NAME,columns,whereClause,whereArgs,null,null,orderClause);
    }

    public Cursor getAllServedMember()
    {
        String[] columns={Constants.ROW_ID,Constants.MEM_QUEUE,Constants.MEM_NAME,Constants.MEM_MOBILE,Constants.MEM_CODE,Constants.MEM_HEALTH_CARD,Constants.MEM_DATE,Constants.MEM_STATUS};
        String whereClause = "status = ? OR status = ?";
        String[] whereArgs = new String[] {
                "active","served"};
        String orderClause = "date DESC, queue DESC ";
        return db.query(Constants.TB_NAME,columns,whereClause,whereArgs,null,null,orderClause);
    }

    public Cursor getSecondMember()
    {
        String[] columns={Constants.ROW_ID,Constants.MEM_QUEUE,Constants.MEM_NAME,Constants.MEM_MOBILE,Constants.MEM_CODE,Constants.MEM_DATE,Constants.MEM_STATUS};
        String whereClause = "status = ?";
        String[] whereArgs = new String[] {
                "queue"};
        return db.query(Constants.TB_NAME,columns,whereClause,whereArgs,null,null,null);
    }


    //UPDATE
    public long UPDATE(int id,String status)
    {
        try
        {
            updateAll();
            ContentValues cv=new ContentValues();
            cv.put(Constants.MEM_STATUS, status);
            return db.update(Constants.TB_NAME,cv,Constants.ROW_ID+" =?",new String[]{String.valueOf(id)});
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateAll(){
        //fetch the highest ID from the database
        DBHelper helper = new DBHelper(c);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c=db.rawQuery("UPDATE memberQueue_TB SET status = 'served' where status = 'active'", null);
        try {
            c.moveToFirst();
        }finally {
            c.close();
        }
    }
    //DELETE
    public long Delete(int id)
    {
        try
        {
            return db.delete(Constants.TB_NAME,Constants.ROW_ID+" =?",new String[]{String.valueOf(id)});
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }


//    public int getProfilesCount() {
//        DBHelper helper = new DBHelper(c);
//        SQLiteDatabase db = helper.getReadableDatabase()
//        String countQuery = "SELECT  * FROM " + TABLE_NAME;
//        Cursor cursor = db.rawQuery("SELECT  FROM", null);
//        int cnt = cursor.getCount();
//        cursor.close();
//        return cnt;
//    }
}
