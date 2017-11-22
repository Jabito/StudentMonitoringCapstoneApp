package com.jambi.macbookpro.smsapp.apiCalls;

import android.util.Log;

import com.jambi.macbookpro.smsapp.model.LogIn;
import com.jambi.macbookpro.smsapp.callback.LoginCallback;
import com.jambi.macbookpro.smsapp.services.AppInterface;
import com.jambi.macbookpro.smsapp.services.AppService;
import com.jambi.macbookpro.smsapp.model.LogInDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by IPC on 11/22/2017.
 */

public class LogInAPiCall {
    public static void logIn(String username, String password, final LoginCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        System.out.println(username);
        System.out.println(password);
        appInterface.getUserLogin(username, password)
                .enqueue(new Callback<LogInDetails>() {
                    @Override
                    public void onResponse(Call<LogInDetails> call, Response<LogInDetails> response) {
                        if(response.body() != null){
                            callback.onSuccessSignIn(response.body());
                            Log.e("success","success");
                        }else {
                            callback.onErrorSignIn("no response to server");
                            Log.e("error1","error1");
                        }
                    }

                    @Override
                    public void onFailure(Call<LogInDetails> call, Throwable e) {
                        try {
                            callback.onErrorSignIn(e.getMessage());
                            Log.e("error2",e.getMessage());
                        } catch (Exception error) {
                            callback.onErrorSignIn("");
                            Log.e("error3","error3");
                        }

                    }
                });

    }

    public static void getParent(String username, String password, final LoginCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        appInterface.getParent(username)
                .enqueue(new Callback<LogInDetails>() {
                    @Override
                    public void onResponse(Call<LogInDetails> call, Response<LogInDetails> response) {
                        if(response.body() != null){
                            callback.onSuccessSignIn(response.body());
                            Log.e("success","success");
                        }else {
                            callback.onErrorSignIn("no response to server");
                            Log.e("error1","error1");
                        }
                    }

                    @Override
                    public void onFailure(Call<LogInDetails> call, Throwable e) {
                        try {
                            callback.onErrorSignIn(e.getMessage());
                            Log.e("error2",e.getMessage());
                        } catch (Exception error) {
                            callback.onErrorSignIn("");
                            Log.e("error3","error3");
                        }

                    }
                });

    }

}

