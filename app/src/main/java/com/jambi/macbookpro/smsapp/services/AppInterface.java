package com.jambi.macbookpro.smsapp.services;

import com.jambi.macbookpro.smsapp.model.LogIn;
import com.jambi.macbookpro.smsapp.model.LogInDetails;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by IPC on 11/22/2017.
 */

public interface AppInterface {


    String ENDPOINT = "http://10.10.26.215:8080/";


    @POST("app/loginUser/")
    Call<LogInDetails> getUserLogin(@Query("username") String username,
                                    @Query("password") String password);


    @GET("app/loginUser/")
    Call<LogInDetails> getStudent(@Query("studentId") String studentId);


    Call<LogInDetails> getParent(String username);
}
