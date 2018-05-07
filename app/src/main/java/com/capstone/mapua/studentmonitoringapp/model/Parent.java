package com.capstone.mapua.studentmonitoringapp.model;

/**
 * Created by jj on 11/23/2017.
 */

public class Parent {

    private String id;
    private String parentOf;
    private String relationship;
    private String parentFName;
    private String parentLName;
    private String contactNo;
    private String occupation;
    private String createdOn;
    private String updatedOn;
    private String createdBy;
    private String updatedBy;

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

    public String getParentFName() {
        return parentFName;
    }

    public void setParentFName(String parentFName) {
        this.parentFName = parentFName;
    }

    public String getParentLName() {
        return parentLName;
    }

    public void setParentLName(String parentLName) {
        this.parentLName = parentLName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
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
}
