package com.medicard.ipc.queuecoorv16;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextClock;
import android.widget.TextView;

import com.medicard.ipc.queuecoorv16.adapter.MemberQueueListAdapter;
import com.medicard.ipc.queuecoorv16.adapter.MemberServedListAdapter;
import com.medicard.ipc.queuecoorv16.database.Constants;
import com.medicard.ipc.queuecoorv16.database.DBAdapter;
import com.medicard.ipc.queuecoorv16.database.DBHelper;
import com.medicard.ipc.queuecoorv16.database.MemberQueue;
import com.medicard.ipc.queuecoorv16.drawer.DrawerActivity;
import com.medicard.ipc.queuecoorv16.utilities.SharedPref;

import java.util.ArrayList;

public class Coor_Activity extends DrawerActivity {

     Context c;
     TextClock textClock;
     TextView txt_Number;
     RecyclerView rv;
     RecyclerView rv_archive;
     MemberQueueListAdapter memberQueueListAdapter;
     MemberServedListAdapter memberServedListAdapter;
     ArrayList<MemberQueue> memberQueueArrayList=new ArrayList<>();
     ArrayList<MemberQueue> memberServedArrayList=new ArrayList<>();
     String txtregister = "";
     String a;
     String current_queue = "";
    String queue_oocc = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_coor);

        textClock = (TextClock) findViewById(R.id.textClock);
        txtClock();
        txt_Number = (TextView)findViewById(R.id.txt_Number);

        //experimental auto update of now serving
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                current_queue = SharedPref.getStringValue(SharedPref.MEMBER,SharedPref.CURRENT_QUEUE,Coor_Activity.this);
//                                displayCurrenteQueue();
                                if(!current_queue.equals(queue_oocc)){
                                    displayCurrenteQueue();
                                    queue_oocc = current_queue;
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();


        //recycler
        rv= (RecyclerView) findViewById(R.id.rv_member_list);
        //SET ITS PROPS
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        //ADAPTER
        memberQueueListAdapter =new MemberQueueListAdapter(this,memberQueueArrayList);
        retrieveQueueList();

        //recycler
        rv_archive= (RecyclerView) findViewById(R.id.rv_served_list);
        //SET ITS PROPS
        rv_archive.setLayoutManager(new LinearLayoutManager(this));
        rv_archive.setItemAnimator(new DefaultItemAnimator());
        //ADAPTER
        memberServedListAdapter=new MemberServedListAdapter(this,memberServedArrayList);
        retrieveServedList();
    }

    //update the current serving
    public  void displayCurrenteQueue(){

        int pid = 0;
        DBHelper helper = new DBHelper(Coor_Activity.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT queue FROM memberQueue_TB where status = 'active'", null);
        try {
            if (c.moveToFirst()) {
                do {
                    pid = c.getInt(0);

                } while (c.moveToNext());
            }
        } finally {
            c.close();
            db.close();
        }

        txt_Number.setText(String.format("%06d", pid));
        retrieveQueueList();
        retrieveServedList();
    }



    //RETRIEVE queue list
    public void retrieveQueueList() {
        DBAdapter db=new DBAdapter(Coor_Activity.this);
        db.openDB();
        memberQueueArrayList.clear();
        Cursor c=db.getAllQueueMember();
        try {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                int queue = c.getInt(1);
                String name = c.getString(2);
                String mobile = c.getString(3);
                String code = c.getString(4);
                String card = c.getString(5);
                String date = c.getString(6);
                String status = c.getString(7);
                MemberQueue p = new MemberQueue(queue, name, mobile, code,card, date, status, id);
                memberQueueArrayList.add(p);
            }
            if(!(memberQueueArrayList.size()<1))
            {
                rv.setAdapter(memberQueueListAdapter);
            }
        }finally {
            c.close();
            db.close();
        }
    }

    ///RETRIEVE served list
    private  void retrieveServedList()
    {
        DBAdapter db=new DBAdapter(Coor_Activity.this);
        //OPEN
        db.openDB();
        memberServedArrayList.clear();
        //SELECT
        Cursor c=db.getAllServedMember();
        //LOOP THRU THE DATA ADDING TO ARRAYLIST
        try {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                int queue = c.getInt(1);
                String name = c.getString(2);
                String mobile = c.getString(3);
                String code = c.getString(4);
                String card = c.getString(5);
                String date = c.getString(6);
                String status = c.getString(7);

                MemberQueue p = new MemberQueue(queue, name, mobile, code,card, date, status, id);
                memberServedArrayList.add(p);
            }
            if(!(memberServedArrayList.size()<1))
            {
                rv_archive.setAdapter(memberServedListAdapter);
            }
        }finally {
            c.close();
            db.close();
        }
    }

    //textClock  set Format24Hour "dd/MM/yyyy hh:mm:ss a""
    public void txtClock(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textClock.setFormat12Hour("MMMM d, yyyy hh:mm:ss a");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textClock.setFormat24Hour("MMMM d, yyyy hh:mm:ss a");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        retrieveQueueList();
    }

}

