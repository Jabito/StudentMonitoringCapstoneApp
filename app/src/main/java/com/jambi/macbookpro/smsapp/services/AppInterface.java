package com.jambi.macbookpro.smsapp.services;

import com.jambi.macbookpro.smsapp.model.AnnouncementDetails;
import com.jambi.macbookpro.smsapp.model.LogIn;
import com.jambi.macbookpro.smsapp.model.LogInDetails;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;
import com.jambi.macbookpro.smsapp.model.EmergencyContactDetails;
import com.jambi.macbookpro.smsapp.model.GuidanceDetails;
import com.jambi.macbookpro.smsapp.model.LogInDetails;
import com.jambi.macbookpro.smsapp.model.ParentDetails;
import com.jambi.macbookpro.smsapp.model.StudentDetails;
import com.jambi.macbookpro.smsapp.model.TapDetails;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by IPC on 11/22/2017.
 */

public interface AppInterface {


    String ENDPOINT = "http://10.10.26.215:8080/";


    @POST("/app/loginUser")
    Call<LogInDetails> getUserLogin(@Query("username") String username,
                                    @Query("password") String password);

    @GET("/app/getStudent")
    Call<StudentDetails> getStudent(@Query("studentId") String studentId);


    @GET("/app/getParent")
    Call<ParentDetails> getParent(@Query("id") String id);


    @GET("/app/getGuidance")
    Call<GuidanceDetails> getGuidance(@Query("id") String id);


    @GET("/app/getEmergencyContact")
    Call<EmergencyContactDetails> getEmergencyContact(@Query("id") String id);


    @GET("/app/getTapLogOfStudent")
    Call<TapDetails> getTapLogOfStudent(@Query("studentId") String studentId);

    @GET("/app/getAnnouncements")
    Call<AnnouncementDetails> getAnnouncements(@Query("parentId") String parentId);



    @POST("/app/toggleSMS")
    Call<AnnouncementDetails> setToggleSms(@Query("parentId") String parentId,
                                           @Query("mode") Boolean mode);
}
