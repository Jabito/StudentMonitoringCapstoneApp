package com.jambi.macbookpro.smsapp.model;

import com.jambi.macbookpro.smsapp.model.User;

/**
 * Created by IPC on 11/22/2017.
 */

public class LogInDetails {

    private String responseDesc;
    private String responseCode;
    private User user;


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
