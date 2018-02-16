package com.capstone.mapua.studentmonitoringapp.model;

import java.util.ArrayList;

/**
 * Created by IPC on 11/26/2017.
 */

public class AnnouncementDetails {

    private String responseDesc;
    private String responseCode;
    private ArrayList<Announcement> announcements;

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }
}
