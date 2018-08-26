package com.capstone.mapua.studentmonitoringapp.implement;

import android.content.Context;

import com.capstone.mapua.studentmonitoringapp.apiCalls.APICall;
import com.capstone.mapua.studentmonitoringapp.callback.LoginCallback;
import com.capstone.mapua.studentmonitoringapp.callback.SettingsCallback;


/**
 * Created by jj on 11/22/2017.
 */

public class LoginImplement {

    private Context context;

    public LoginImplement(Context context) {
        this.context = context;
    }

    public void getSignInData(String username, String password, LoginCallback callback) {
        APICall.getLogIn(username, password, callback);
    }

    public void getParent(String id, LoginCallback callback) {
        APICall.getParent(id, callback);
    }

    public void getStudent(String parentOf, LoginCallback callback) {
        APICall.getStudent(parentOf, callback);
    }

    public void getEmergencyContact(String id, LoginCallback callback) {
        APICall.getEmergencyContact(id, callback);
    }

}
