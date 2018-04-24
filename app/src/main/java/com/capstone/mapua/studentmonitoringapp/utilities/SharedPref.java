package com.capstone.mapua.studentmonitoringapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.capstone.mapua.studentmonitoringapp.model.Parent;
import com.capstone.mapua.studentmonitoringapp.model.Student;
import com.capstone.mapua.studentmonitoringapp.model.User;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * Created by IPC on 11/22/2017.
 */

public class SharedPref {
    //MASTER KEY
    public static String USER = "USER";
    public static User userData;
    public static Parent parentData;
    public static Student studentData;

    //USED UPON RETRIEVAL OF USER INFORMATION
    public static String USER_ID = "USER_ID";

    //USED UPON RETRIEVAL OF PARENT`S INFORMATION
    public static String PARENT_ID = "PARENT_ID";
    public static String PARENT_PARENT_OF = "PARENT_PARENT_OF";
    public static String PARENT_RELATIONSHIP = "PARENT_RELATIONSHIP";
    public static String PARENT_LNAME = "PARENT_LNAME";
    public static String PARENT_FNAME = "PARENT_FNAME";
    public static String PARENT_CONTACT = "PARENT_CONTACT";
    public static String PARENT_OCUPATION = "PARENT_OCUPATION";


    //USED UPON RETRIEVAL OF STUDENT`S INFORMATION
    public static String STUDENT_id = "STUDENT_id";
    public static String STUDENT_firstName = "STUDENT_lastName";
    public static String STUDENT_lastName = "STUDENT_lastName";
    public static String STUDENT_middleName = "STUDENT_middleName";
    public static String STUDENT_bday = "STUDENT_bday";
    public static String STUDENT_place = "STUDENT_place";
    public static String STUDENT_gender = "STUDENT_gender";
    public static String STUDENT_citizenship = "STUDENT_citizenship";
    public static String STUDENT_address = "STUDENT_address";
    public static String STUDENT_contactNo = "STUDENT_contactNo";
    public static String STUDENT_emergencyContact = "STUDENT_emergencyContact";
    public static String STUDENT_gradeLvlId = "STUDENT_gradeLvlId";
    public static String STUDENT_schoolYear = "STUDENT_schoolYear";
    public static String STUDENT_section = "STUDENT_section";
    public static String STUDENT_isOldStudent = "STUDENT_isOldStudent";
    public static String STUDENT_isTransferee = "STUDENT_isTransferee";
    public static String STUDENT_rfid = "STUDENT_rfid";
    public static String STUDENT_isEnrolled = "STUDENT_isEnrolled";
    public static String STUDENT_createdBy = "STUDENT_createdBy";
    public static String STUDENT_createdOn = "STUDENT_createdOn";
    public static String STUDENT_updatedBy = "STUDENT_updatedBy";
    public static String STUDENT_updatedOn = "STUDENT_updatedOn";


    //used for checking of session
    public static String SESSION_ON = "SESSION_ON";

    //used for checking of session
    public static String SMS_TOGGLE = "SMS_TOGGLE";


    //used for taplogadapter
    public static String LAST_LOG_UPDATE = "LAST_LOG_UPDATE";
    public static String LOG_TITLE = "LOG_TITLE";

 //used for announcementadapter
    public static String LAST_ANNOUNCEMENT_UPDATE = "LAST_ANNOUNCEMENT_UPDATE";





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

    public static String getStringValue(String Key, String specID, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        return preferences.getString(specID, "null");
    }

    public static int getIntegerValue(String Key, String specID, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        return preferences.getInt(specID, 0);
    }


    public static ArrayList<String>  getArrayStringValue(String Key, String specID, Context context) {
        ArrayList<String> bytes = new ArrayList<>();
        SharedPreferences prefs = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        int size = prefs.getInt(specID+"_size", 0);
        for(int i=0;i<size;i++)
        {
            bytes.add(prefs.getString(specID+"_" + i, null));
        }
        return bytes;
    }

    public static void setArrayStringValue(String Key, String specID, ArrayList<String> bytes, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(specID+"_size", bytes.size()); /* byteArr is an array */
        for(int i=0;i<bytes.size();i++)
        {
            editor.remove(specID+"_" + i);
            editor.putString(specID+"_" + i, bytes.get(i));
        }
        editor.commit();
    }

}
