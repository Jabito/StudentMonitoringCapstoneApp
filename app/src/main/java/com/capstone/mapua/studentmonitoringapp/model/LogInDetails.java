package com.capstone.mapua.studentmonitoringapp.model;

/**
 * Created by jj on 11/22/2017.
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

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public Guidance getGuidance() {
        return Guidance;
    }

    public void setGuidance(Guidance guidance) {
        Guidance = guidance;
    }

    public Parent getParent() {
        return Parent;
    }

    public void setParent(Parent parent) {
        Parent = parent;
    }

    @Override
    public String toString() {
        return "LogInDetails{" +
                "responseDesc='" + responseDesc + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", User=" + User +
                ", Guidance=" + Guidance +
                ", Parent=" + Parent +
                '}';
    }
}
