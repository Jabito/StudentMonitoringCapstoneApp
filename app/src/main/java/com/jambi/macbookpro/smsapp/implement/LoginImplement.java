package com.jambi.macbookpro.smsapp.implement;

import android.content.Context;

import com.jambi.macbookpro.smsapp.apiCalls.LogInAPiCall;
import com.jambi.macbookpro.smsapp.callback.LoginCallback;
import com.jambi.macbookpro.smsapp.model.LogIn;

/**
 * Created by IPC on 11/22/2017.
 */

public class LoginImplement {

    private Context context;

    public LoginImplement(Context context) {
        this.context = context;
    }

    public void getSignInData(String username, String password, LoginCallback callback) {
        LogInAPiCall.logIn(username,password, callback);
    }
}
