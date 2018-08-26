package com.capstone.mapua.studentmonitoringapp.implement;

import android.content.Context;

import com.capstone.mapua.studentmonitoringapp.apiCalls.APICall;
import com.capstone.mapua.studentmonitoringapp.callback.SettingsCallback;


/**
 * Created by jj on 11/26/2017.
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
