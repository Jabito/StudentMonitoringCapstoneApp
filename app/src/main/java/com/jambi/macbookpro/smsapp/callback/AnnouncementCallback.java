package com.jambi.macbookpro.smsapp.callback;

import com.jambi.macbookpro.smsapp.model.Announcement;
import com.jambi.macbookpro.smsapp.model.AnnouncementDetails;

/**
 * Created by IPC on 11/26/2017.
 */

public interface AnnouncementCallback {

    void onSuccess(AnnouncementDetails body);

    void onError(String s);
}
