package com.jambi.macbookpro.smsapp.implement;

import android.content.Context;

import com.jambi.macbookpro.smsapp.apiCalls.APICall;
import com.jambi.macbookpro.smsapp.callback.LoginCallback;

/**
 * Created by IPC on 11/22/2017.
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
}
