package com.capstone.mapua.studentmonitoringapp.implement;

import android.content.Context;
import android.os.AsyncTask;

import com.capstone.mapua.studentmonitoringapp.apiCalls.APICall;
import com.capstone.mapua.studentmonitoringapp.callback.AnnouncementCallback;
import com.capstone.mapua.studentmonitoringapp.callback.TapCallback;
import com.capstone.mapua.studentmonitoringapp.database.DatabaseHandler;
import com.capstone.mapua.studentmonitoringapp.model.Announcement;
import com.capstone.mapua.studentmonitoringapp.utilities.SetReturnDataToLocal;

import java.util.ArrayList;


/**
 * Created by IPC on 11/26/2017.
 */

public class AnnouncementImplement {

    Context context;
    private DatabaseHandler dbHandler;
    private AnnouncementCallback callback;

    public AnnouncementImplement(Context context, DatabaseHandler dbHandler, AnnouncementCallback callback) {
        this.context = context;
        this.dbHandler = dbHandler;
        this.callback = callback;
    }


    public void getAnnouncements(final ArrayList<Announcement> announcements, final ArrayList<Announcement> announcementArrayList) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected void onPostExecute(Object o) {
                callback.onSaveComplete(announcementArrayList);

            }

            @Override
            protected Object doInBackground(Object[] objects) {
                dbHandler.deteleAnnouncementData();
                SetReturnDataToLocal.setAnnouncementToDB(announcements, dbHandler);
                announcementArrayList.clear();
                announcementArrayList.addAll(dbHandler.getAnnouncementList());
                return null;
            }
        };
        task.execute();
    }

}
