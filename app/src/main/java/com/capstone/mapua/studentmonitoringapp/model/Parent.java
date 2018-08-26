package com.capstone.mapua.studentmonitoringapp.model;

import java.util.Date;

/**
 * Created by jj on 11/23/2017.
 */

public class Parent {

    private String id;
    private String parentOf;
    private String relationship;
    private String parentName;
    private String officeNo;
    private String occupation;
    private String createdOn;
    private Boolean smsNotif;
    private String updatedOn;
    private String createdBy;
    private String updatedBy;
    private String email;


    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getOfficeNo() {
        return officeNo;
    }

    public void setOfficeNo(String officeNo) {
        this.officeNo = officeNo;
    }

    public Boolean getSmsNotif() {
        return smsNotif;
    }

    public void setSmsNotif(Boolean smsNotif) {
        this.smsNotif = smsNotif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentOf() {
        return parentOf;
    }

    public void setParentOf(String parentOf) {
        this.parentOf = parentOf;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id='" + id + '\'' +
                ", parentOf='" + parentOf + '\'' +
                ", relationship='" + relationship + '\'' +
                ", parentName='" + parentName + '\'' +
                ", officeNo='" + officeNo + '\'' +
                ", occupation='" + occupation + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", smsNotif=" + smsNotif +
                ", updatedOn='" + updatedOn + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
