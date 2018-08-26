package com.capstone.mapua.studentmonitoringapp.model;

/**
 * Created by jj on 11/23/2017.
 */

public class GuidanceDetails {
    private String responseDesc;
    private String responseCode;
    private Guidance guidance;


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

    public Guidance getGuidance() {
        return guidance;
    }

    public void setGuidance(Guidance guidance) {
        this.guidance = guidance;
    }
}
