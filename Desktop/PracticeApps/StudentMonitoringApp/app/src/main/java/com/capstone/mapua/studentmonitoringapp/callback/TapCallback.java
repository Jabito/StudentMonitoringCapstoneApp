package com.capstone.mapua.studentmonitoringapp.callback;


import com.capstone.mapua.studentmonitoringapp.model.TapDetails;

/**
 * Created by IPC on 11/23/2017.
 */

public interface TapCallback {

    void onSuccessTapDetails(TapDetails body);

    void onErrorTapDetails(String s);
}
