package com.capstone.mapua.studentmonitoringapp.callback;


import com.capstone.mapua.studentmonitoringapp.model.ToggleSMSDetails;

/**
 * Created by jj on 11/26/2017.
 */

public interface SettingsCallback {

    void onSuccess(ToggleSMSDetails body);

    void onError(String s);
}
