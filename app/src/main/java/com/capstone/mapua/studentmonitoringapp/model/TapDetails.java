package com.capstone.mapua.studentmonitoringapp.model;

import java.util.ArrayList;

/**
 * Created by IPC on 11/23/2017.
 */

public class TapDetails {

    private String responseDesc;
    private String responseCode;
    private ArrayList<TapLog> tapListDetails;

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

    public ArrayList<TapLog> getTapListDetails() {
        return tapListDetails;
    }

    public void setTapListDetails(ArrayList<TapLog> tapListDetails) {
        this.tapListDetails = tapListDetails;
    }
}
