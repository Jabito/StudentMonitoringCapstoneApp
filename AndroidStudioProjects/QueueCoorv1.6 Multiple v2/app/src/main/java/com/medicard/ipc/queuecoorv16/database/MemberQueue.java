package com.medicard.ipc.queuecoorv16.database;

/**
 * Created by IPC on 7/27/2017.
 */

public class MemberQueue {
    private String name,mobile,code,healthname,date, status;
    private int id,queue;
    public MemberQueue(int queue, String name, String mobile, String code, String healthname, String date, String status, int id) {
        this.queue = queue;
        this.name = name;
        this.mobile = mobile;
        this.code = code;
        this.healthname = healthname;
        this.date = date;
        this.status = status;
        this.id = id;
    }
    public int getQueue() {
        return queue;
    }
    public void setQueue(int queue) {
        this.queue = queue;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getHealthname() {
        return healthname;
    }
    public void setHealthname(String healthname) {
        this.date = healthname;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
