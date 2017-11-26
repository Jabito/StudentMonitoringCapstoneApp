package com.jambi.macbookpro.smsapp.implement;

import android.content.Context;

import com.jambi.macbookpro.smsapp.apiCalls.APICall;
import com.jambi.macbookpro.smsapp.callback.LoginCallback;
import com.jambi.macbookpro.smsapp.callback.SettingsCallback;

/**
 * Created by IPC on 11/26/2017.
 */

public class SettingsImplement {
    private Context context;

    public SettingsImplement(Context context) {
        this.context = context;
    }

    public void setToggleSms(Boolean isChecked,String id, SettingsCallback callback) {
        APICall.setToggleSms(isChecked, id, callback);
    }
}
