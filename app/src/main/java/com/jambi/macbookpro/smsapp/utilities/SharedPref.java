package com.jambi.macbookpro.smsapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IPC on 11/22/2017.
 */

public class SharedPref {

    public static String USER = "USER";

    //CREDENTIALS


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
