package com.medicard.ipc.queuecoorv16.database;

/**
 * Created by IPC on 8/4/2017.
 */

public class Constants {
    //COLUMNS
    static final String ROW_ID="id";
    static final String MEM_QUEUE = "queue";
    static final String MEM_NAME = "name";
    static final String MEM_MOBILE = "mobile";
    static final String MEM_CODE = "code";
    static final String MEM_HEALTH_CARD = "healthcard";
    static final String MEM_DATE = "date";
    static final String MEM_STATUS = "status";
    //DB PROPERTIES
    static final String DB_NAME ="memberQueue_DB";
    static final String TB_NAME ="memberQueue_TB";
    static final int DB_VERSION='3';
    //CREATE TABLE
    static final String CREATE_TB =" CREATE TABLE "+TB_NAME+" ("
            +ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +MEM_QUEUE+" INTEGER, "
            +MEM_NAME+" TEXT , "
            +MEM_MOBILE+" TEXT , "
            +MEM_CODE+" TEXT , "
            +MEM_HEALTH_CARD+" TEXT , "
            +MEM_DATE+" TEXT , "
            +MEM_STATUS+" TEXT);";
}
