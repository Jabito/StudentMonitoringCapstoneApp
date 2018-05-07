package com.capstone.mapua.studentmonitoringapp.model;

/**
 * Created by jj on 11/23/2017.
 */

public class EmergencyContactDetails {
    private String responseDesc;
    private String responseCode;
    private EmergencyContact emergencyContact;

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

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
}
