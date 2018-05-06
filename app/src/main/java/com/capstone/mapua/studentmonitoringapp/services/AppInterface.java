package com.capstone.mapua.studentmonitoringapp.services;



import com.capstone.mapua.studentmonitoringapp.model.AnnouncementDetails;
import com.capstone.mapua.studentmonitoringapp.model.EmergencyContactDetails;
import com.capstone.mapua.studentmonitoringapp.model.GuidanceDetails;
import com.capstone.mapua.studentmonitoringapp.model.LogInDetails;
import com.capstone.mapua.studentmonitoringapp.model.ParentDetails;
import com.capstone.mapua.studentmonitoringapp.model.StudentDetails;
import com.capstone.mapua.studentmonitoringapp.model.TapDetails;
import com.capstone.mapua.studentmonitoringapp.model.ToggleSMSDetails;
import com.capstone.mapua.studentmonitoringapp.model.UserImageDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jj on 11/22/2017.
 */

public interface AppInterface {


    //    String ENDPOINT = "http://10.10.26.215:8080/";

//    String ENDPOINT = "http://www.aquajmt.com:8080/";
    String ENDPOINT = "http://192.168.1.9:8085/";

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
    Call<ToggleSMSDetails> setToggleSms(@Query("parentId") String parentId,
                                        @Query("mode") Boolean mode);
    @GET("/app/downloadPicture")
    Call<UserImageDetails> getUserImage(@Query("userId") String userId);

}
