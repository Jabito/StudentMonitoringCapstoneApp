package com.capstone.mapua.studentmonitoringapp.model;

/**
 * Created by jj on 11/23/2017.
 */

public class ParentDetails {

    private String responseDesc;
    private String responseCode;
    private Parent parent;

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

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
