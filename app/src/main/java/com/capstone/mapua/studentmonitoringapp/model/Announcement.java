package com.capstone.mapua.studentmonitoringapp.model;

/**
 * Created by IPC on 11/26/2017.
 */

public class Announcement {

    private String messageTypeId;
    private String message;
    private String postedBy;
    private String datePosted;
    private String messageTarget;
    private String parentIds;

    public Announcement() {
    }

    public Announcement(String messageTypeId, String message, String postedBy, String datePosted, String messageTarget, String parentIds) {

        this.messageTypeId = messageTypeId;
        this.message = message;
        this.postedBy = postedBy;
        this.datePosted = datePosted;
        this.messageTarget = messageTarget;
        this.parentIds = parentIds;
    }

    public String getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(String messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getMessageTarget() {
        return messageTarget;
    }

    public void setMessageTarget(String messageTarget) {
        this.messageTarget = messageTarget;
    }

    public String announcement() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
}
