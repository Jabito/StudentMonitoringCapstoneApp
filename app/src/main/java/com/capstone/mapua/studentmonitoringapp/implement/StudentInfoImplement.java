package com.capstone.mapua.studentmonitoringapp.implement;

import android.content.Context;

import com.capstone.mapua.studentmonitoringapp.apiCalls.APICall;
import com.capstone.mapua.studentmonitoringapp.callback.UserImageCallback;

/**
 * Created by me on 5/5/2018.
 */

public class StudentInfoImplement {

    private Context context;

    public StudentInfoImplement(Context context) {
        this.context = context;
    }

    public void getUserImage(String studentId, UserImageCallback callback) {
        APICall.getUserImage(studentId, callback);
    }
}
