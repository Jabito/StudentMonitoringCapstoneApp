package com.capstone.mapua.studentmonitoringapp.utilities;

import android.support.annotation.NonNull;

/**
 * Created by jj on 11/22/2017.
 */

public class ErrorMessage {

    @NonNull
    public static String    setErrorMessage(String data) {

        try {
            String message = "";
            if (data.contains("unexpected end of stream") || data.contains("404")
                    || data.contains("500"))
                message = "MACE cannot connect to the server.";
            else if (data.contains("Connection timed out") || data.contains("connect timed out"))
                message = "Connection timed out. Please try again.";
            else if (data.contains("Failed to connect to") || data.contains("failed to connect to") || data.contains("no response to server"))
                message = "Cannot connect to server";
            else if (data.contains("Unable to resolve host"))
                message = "Cannot connect to server, please check your internet connection";
            else
                message = "An error else occurred. Please try again.";
            return message;
        }catch (Exception e){
            return "An error exception occurred. Please try again.";
        }
    }
}
