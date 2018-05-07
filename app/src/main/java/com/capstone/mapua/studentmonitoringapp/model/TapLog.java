package com.capstone.mapua.studentmonitoringapp.model;

/**
 * Created by jj on 11/23/2017.
 */

public class TapLog {

    private String rfid;
    private String logType;
    private String logDateTime;

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(String logDateTime) {
        this.logDateTime = logDateTime;
    }

    public TapLog() {
    }

    public TapLog(String rfid, String logType, String logDateTime) {
        this.rfid = rfid;
        this.logType = logType;
        this.logDateTime = logDateTime;
    }

    @Override
    public String toString() {
        return "rfid+ " + rfid + "logType+ " + logType + "logDateTime+ " + logDateTime;
    }
}
