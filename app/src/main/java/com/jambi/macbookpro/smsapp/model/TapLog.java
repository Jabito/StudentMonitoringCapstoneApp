package com.jambi.macbookpro.smsapp.model;

/**
 * Created by IPC on 11/23/2017.
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
}
