package com.jambi.macbookpro.smsapp.implement;

import android.content.Context;

import com.jambi.macbookpro.smsapp.apiCalls.APICall;
import com.jambi.macbookpro.smsapp.callback.LoginCallback;
import com.jambi.macbookpro.smsapp.callback.TapCallback;

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