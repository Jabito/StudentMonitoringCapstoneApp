package com.jambi.macbookpro.smsapp.model;

import com.jambi.macbookpro.smsapp.model.User;

/**
 * Created by IPC on 11/22/2017.
 */

public class LogInDetails {

    private String responseDesc;
    private String responseCode;
    private User User;
    private Guidance Guidance;
    private Parent Parent;


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

    public com.jambi.macbookpro.smsapp.model.User getUser() {
        return User;
    }

    public void setUser(com.jambi.macbookpro.smsapp.model.User user) {
        User = user;
    }

    public com.jambi.macbookpro.smsapp.model.Guidance getGuidance() {
        return Guidance;
    }

    public void setGuidance(com.jambi.macbookpro.smsapp.model.Guidance guidance) {
        Guidance = guidance;
    }

    public com.jambi.macbookpro.smsapp.model.Parent getParent() {
        return Parent;
    }

    public void setParent(com.jambi.macbookpro.smsapp.model.Parent parent) {
        Parent = parent;
    }
}
