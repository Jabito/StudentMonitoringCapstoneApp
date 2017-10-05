package com.medicard.ipc.queuecoorv16.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by IPC on 9/11/2017.
 */

public class SharedPref {

    public static String MEMBER = "MEMBER";
    public static String ORIGIN = "ORIGIN";

    public static String MAX_QUEUE = "MAX_QUEUE";
    public static String BLUETOOTH = "BLUETOOTH";
    public static String TXT_REGISTER = "TXT_REGISTER";
    public static String TXT_COORD = "TXT_COORD";
    public static String TXT_REMINDER = "TXT_REMINDER";

    public static String CURRENT_QUEUE = "CURRENT_QUEUE";



    public static void setStringValue(String Key, String specID, String value, Context context) {

        SharedPreferences prefs = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(specID, value);
        editor.apply();


    }
    public static   String getStringValue(String Key, String specID, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        return preferences.getString(specID, "null");
    }

}
