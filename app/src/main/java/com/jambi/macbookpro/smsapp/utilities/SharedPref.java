package com.jambi.macbookpro.smsapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.jambi.macbookpro.smsapp.model.LogInDetails;
import com.jambi.macbookpro.smsapp.model.Parent;
import com.jambi.macbookpro.smsapp.model.Student;
import com.jambi.macbookpro.smsapp.model.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IPC on 11/22/2017.
 */

public class SharedPref {
    //MASTER KEY
    public static String USER = "USER";
    public static User userData;
    public static Parent parentData;
    public static Student studentData;

    //USED UPON RETRIEVAL OF PARENT`S INFORMATION
    public static String PARENT_ID = "PARENT_ID";
    public static String PARENT_PARENT_OF = "PARENT_PARENT_OF";
    public static String PARENT_RELATIONSHIP = "PARENT_RELATIONSHIP";
    public static String PARENT_LNAME = "PARENT_LNAME";
    public static String PARENT_FNAME = "PARENT_FNAME";
    public static String PARENT_CONTACT = "PARENT_CONTACT";
    public static String PARENT_OCUPATION = "PARENT_OCUPATION";


    public void setBoolValue(String Key, boolean value, Context context) {

        SharedPreferences prefs = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("LOG", value);
        editor.commit();


    }


    public static boolean getBoolValue(String Key, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        return preferences.getBoolean("LOG", false);
    }


    public static void setStringValue(String Key, String specID, String value, Context context) {

        SharedPreferences prefs = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(specID, value);
        editor.apply();


    }

    public static void setBooleanValue(String Key, String specID, boolean value, Context context) {

        SharedPreferences prefs = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(specID, value);
        editor.apply();
    }

    public static void setIntegerValue(String Key, String specID, int value, Context context) {

        SharedPreferences prefs = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(specID, value);
        editor.apply();
    }

    public static boolean getBooleanValue(String Key, String specID, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        return preferences.getBoolean(specID, false);
    }

    public static   String getStringValue(String Key, String specID, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        return preferences.getString(specID, "null");
    }

    public static int getIntegerValue(String Key, String specID, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        return preferences.getInt(specID, 0);
    }

}
