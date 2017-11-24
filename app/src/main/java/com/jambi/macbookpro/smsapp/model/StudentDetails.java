package com.jambi.macbookpro.smsapp.model;

/**
 * Created by IPC on 11/23/2017.
 */

public class StudentDetails {
    private String responseDesc;
    private String responseCode;
    private Student student;

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
