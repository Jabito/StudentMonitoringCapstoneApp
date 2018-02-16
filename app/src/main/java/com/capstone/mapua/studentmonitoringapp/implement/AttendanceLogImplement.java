package com.capstone.mapua.studentmonitoringapp.implement;

import android.content.Context;
import android.os.AsyncTask;

import com.capstone.mapua.studentmonitoringapp.apiCalls.APICall;
import com.capstone.mapua.studentmonitoringapp.callback.TapCallback;
import com.capstone.mapua.studentmonitoringapp.database.DatabaseHandler;
import com.capstone.mapua.studentmonitoringapp.model.TapLog;
import com.capstone.mapua.studentmonitoringapp.utilities.SetReturnDataToLocal;

import java.util.ArrayList;


/**
 * Created by IPC on 11/24/2017.
 */



public class AttendanceLogImplement {

    private Context context;
    private DatabaseHandler dbHandler;
    private TapCallback callback;

    public AttendanceLogImplement(Context context, DatabaseHandler dbHandler, TapCallback callback) {
        this.context = context;
        this.dbHandler = dbHandler;
        this.callback = callback;
    }

    public void getTapLogOfStudent(final ArrayList<TapLog> tapLogs) {
        AsyncTask task = new AsyncTask() {


            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                callback.onSaveComplete(dbHandler.getTapLogList());
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                SetReturnDataToLocal.setTapLogToDB(tapLogs, dbHandler);
                return null;
            }
        };
        task.execute();

    }


}