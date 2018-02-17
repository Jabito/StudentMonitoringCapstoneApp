package com.capstone.mapua.studentmonitoringapp.callback;


import com.capstone.mapua.studentmonitoringapp.model.Announcement;
import com.capstone.mapua.studentmonitoringapp.model.AnnouncementDetails;

import java.util.ArrayList;

/**
 * Created by IPC on 11/26/2017.
 */

public interface AnnouncementCallback {

    void onSuccess(AnnouncementDetails body);

    void onError(String s);

    void onSaveComplete(ArrayList<Announcement> announcementArrayList);
}
