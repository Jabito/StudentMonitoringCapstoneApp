package com.capstone.mapua.studentmonitoringapp.implement;

import android.content.Context;

import com.capstone.mapua.studentmonitoringapp.apiCalls.APICall;
import com.capstone.mapua.studentmonitoringapp.callback.AnnouncementCallback;


/**
 * Created by IPC on 11/26/2017.
 */

public class AnnouncementImplement {

    Context context;

    public AnnouncementImplement(Context context) {
        this.context = context;
    }

    public void getAnnouncements(String parentId, AnnouncementCallback callback) {
        APICall.getAnnouncements(parentId, callback);
    }

}
