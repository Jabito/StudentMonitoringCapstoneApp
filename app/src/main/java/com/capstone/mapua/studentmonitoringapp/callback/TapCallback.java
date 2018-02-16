package com.capstone.mapua.studentmonitoringapp.callback;


import com.capstone.mapua.studentmonitoringapp.model.TapDetails;
import com.capstone.mapua.studentmonitoringapp.model.TapLog;

import java.util.ArrayList;

/**
 * Created by IPC on 11/23/2017.
 */

public interface TapCallback {

    void onSuccessTapDetails(TapDetails body);

    void onErrorTapDetails(String s);

    void onSaveComplete(ArrayList<TapLog> tapLogList);
}
