package com.jambi.macbookpro.smsapp.implement;

import android.content.Context;

import com.jambi.macbookpro.smsapp.apiCalls.APICall;
import com.jambi.macbookpro.smsapp.callback.AnnouncementCallback;

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
