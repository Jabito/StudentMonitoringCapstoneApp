package com.capstone.mapua.studentmonitoringapp.implement;

import android.content.Context;

import com.capstone.mapua.studentmonitoringapp.apiCalls.APICall;
import com.capstone.mapua.studentmonitoringapp.callback.TapCallback;


/**
 * Created by IPC on 11/24/2017.
 */



public class AttendanceLogImplement {

    private Context context;


    public AttendanceLogImplement(Context context) {
        this.context = context;
    }

    public void getTapLogOfStudent(String studentId, TapCallback callback) {
        APICall.getTapLogOfStudent(studentId,callback);
    }


}