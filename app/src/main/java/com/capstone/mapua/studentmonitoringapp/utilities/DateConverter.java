package com.capstone.mapua.studentmonitoringapp.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by me on 2/16/2018.
 */

public class DateConverter {

    //to format the received date from service
    public static String setToMonth(String dateReturn) {
        String date_s = dateReturn;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = null;
        try {
            date = dt.parse(date_s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("MMMM");
        return dt1.format(date);
    }

    //to format the received date from service
    public static String setFormatToMonthDayYearAndTime(String dateReturn) {
        String date_s = dateReturn;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = null;
        try {
            date = dt.parse(date_s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("MMMM d, yyyy HH:mm a ");
        return dt1.format(date);
    }

    public static String setToDate(String dateReturn) {
        String date_s = dateReturn;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = null;
        try {
            date = dt.parse(date_s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
        return dt1.format(date);
    }

    public static String setToTime(String dateReturn) {
        String date_s = dateReturn;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = null;
        try {
            date = dt.parse(date_s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm:ss a");
        return dt1.format(date);
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(cal.getTime());
    }
}
