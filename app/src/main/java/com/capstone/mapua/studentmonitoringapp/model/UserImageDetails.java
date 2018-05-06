package com.capstone.mapua.studentmonitoringapp.model;

import java.io.Serializable;

/**
 * Created by jj on 4/26/2018.
 */

public class UserImageDetails implements Serializable{

    private String responseDesc;
    private String responseCode;
    private String imageBase64;

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
