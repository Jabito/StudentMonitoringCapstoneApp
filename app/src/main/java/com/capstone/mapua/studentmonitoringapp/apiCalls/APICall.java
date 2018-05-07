package com.capstone.mapua.studentmonitoringapp.apiCalls;

import android.util.Log;

import com.capstone.mapua.studentmonitoringapp.callback.AnnouncementCallback;
import com.capstone.mapua.studentmonitoringapp.callback.LoginCallback;
import com.capstone.mapua.studentmonitoringapp.callback.SettingsCallback;
import com.capstone.mapua.studentmonitoringapp.callback.TapCallback;
import com.capstone.mapua.studentmonitoringapp.callback.UserImageCallback;
import com.capstone.mapua.studentmonitoringapp.model.AnnouncementDetails;
import com.capstone.mapua.studentmonitoringapp.model.EmergencyContactDetails;
import com.capstone.mapua.studentmonitoringapp.model.LogInDetails;
import com.capstone.mapua.studentmonitoringapp.model.ParentDetails;
import com.capstone.mapua.studentmonitoringapp.model.StudentDetails;
import com.capstone.mapua.studentmonitoringapp.model.TapDetails;
import com.capstone.mapua.studentmonitoringapp.model.ToggleSMSDetails;
import com.capstone.mapua.studentmonitoringapp.model.UserImageDetails;
import com.capstone.mapua.studentmonitoringapp.services.AppInterface;
import com.capstone.mapua.studentmonitoringapp.services.AppService;
import com.capstone.mapua.studentmonitoringapp.utilities.ErrorMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jj on 11/22/2017.
 */

public class APICall {
    public static void getLogIn(String username, String password, final LoginCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        System.out.println(username);
        System.out.println(password);
        appInterface.getUserLogin(username, password)
                .enqueue(new Callback<LogInDetails>() {
                    @Override
                    public void onResponse(Call<LogInDetails> call, Response<LogInDetails> response) {
                        if (null != response.body()) {
                            if (response.body().getResponseCode().equalsIgnoreCase("OK")) {
                                callback.onSuccessSignIn(response.body());
                            } else {
                                callback.onErrorSignIn("Invalid Credentials");
                            }
                        } else {
                            callback.onErrorSignIn(ErrorMessage.setErrorMessage("no response to server"));
                            Log.e("error1", "error1");
                        }
                    }

                    @Override
                    public void onFailure(Call<LogInDetails> call, Throwable t) {
                        if (null != t.getMessage())
                            callback.onErrorSignIn(ErrorMessage.setErrorMessage(t.getMessage()));
                        else
                            callback.onErrorSignIn(ErrorMessage.setErrorMessage("no response to server"));


                    }
                });

    }

    public static void getParent(String id, final LoginCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        appInterface.getParent(id)
                .enqueue(new Callback<ParentDetails>() {
                    @Override
                    public void onResponse(Call<ParentDetails> call, Response<ParentDetails> response) {
                        if (null != response.body()) {
                            if (response.body().getResponseCode().equalsIgnoreCase("NOT_FOUND")) {
                                callback.onErrorGetParentDetails("USER NOT FOUND");
                            } else if (response.body().getResponseCode().equalsIgnoreCase("OK")) {
                                callback.onSuccessGetParentDetails(response.body());
                            } else if (response.body().getResponseCode().equalsIgnoreCase("UNAUTHORIZED")) {
                                callback.onErrorGetParentDetails("UNAUTHORIZED USER");
                            }
                        } else {
                            callback.onErrorGetParentDetails(ErrorMessage.setErrorMessage("no response to server"));
                        }
                    }

                    @Override
                    public void onFailure(Call<ParentDetails> call, Throwable t) {
                        if (null != t.getMessage())
                            callback.onErrorGetParentDetails(ErrorMessage.setErrorMessage(t.getMessage()));
                        else
                            callback.onErrorGetParentDetails(ErrorMessage.setErrorMessage("no response to server"));

                    }
                });

    }

    public static void getStudent(String id, final LoginCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        appInterface.getStudent(id)
                .enqueue(new Callback<StudentDetails>() {
                    @Override
                    public void onResponse(Call<StudentDetails> call, Response<StudentDetails> response) {
                        if (null != response.body()) {
                            callback.onSuccessGetStudentDetails(response.body());
                            Log.e("success", "success");
                        } else {
                            callback.onErrorGetStudentDetails(ErrorMessage.setErrorMessage("no response to server"));
                            Log.e("error1", "error1");
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentDetails> call, Throwable t) {
                        if (null != t.getMessage())
                            callback.onErrorGetStudentDetails(ErrorMessage.setErrorMessage(t.getMessage()));
                        else
                            callback.onErrorGetStudentDetails(ErrorMessage.setErrorMessage("no response to server"));
                    }
                });

    }


    public static void getEmergencyContact(String id, final LoginCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        appInterface.getEmergencyContact(id)
                .enqueue(new Callback<EmergencyContactDetails>() {
                    @Override
                    public void onResponse(Call<EmergencyContactDetails> call, Response<EmergencyContactDetails> response) {
                        if (null != response.body()) {
                            callback.onSuccessGetEmergencyContactDetails(response.body());
                            Log.e("success", "success");
                        } else {
                            callback.onErrorGetEmergencyContactDetails(ErrorMessage.setErrorMessage("no response to server"));
                            Log.e("error1", "error1");
                        }
                    }

                    @Override
                    public void onFailure(Call<EmergencyContactDetails> call, Throwable t) {
                        if (null != t.getMessage())
                            callback.onErrorGetEmergencyContactDetails(ErrorMessage.setErrorMessage(t.getMessage()));
                        else
                            callback.onErrorGetEmergencyContactDetails(ErrorMessage.setErrorMessage("no response to server"));
                    }
                });

    }


    public static void getTapLogOfStudent(String id, final TapCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        appInterface.getTapLogOfStudent(id)
                .enqueue(new Callback<TapDetails>() {
                    @Override
                    public void onResponse(Call<TapDetails> call, Response<TapDetails> response) {
                        if (null != response.body()) {
                            callback.onSuccessTapDetails(response.body());
                            Log.e("success getParent", "success");
                        } else {
                            callback.onErrorTapDetails(ErrorMessage.setErrorMessage("no response to server"));
                            Log.e("error1 getParent", "error1");
                        }
                    }

                    @Override
                    public void onFailure(Call<TapDetails> call, Throwable t) {
                        if (null != t.getMessage())
                            callback.onErrorTapDetails(ErrorMessage.setErrorMessage(t.getMessage()));
                        else
                            callback.onErrorTapDetails(ErrorMessage.setErrorMessage("no response to server"));

                    }
                });

    }


    public static void getAnnouncements(String userId, final AnnouncementCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        appInterface.getAnnouncements(userId)
                .enqueue(new Callback<AnnouncementDetails>() {
                    @Override
                    public void onResponse(Call<AnnouncementDetails> call, Response<AnnouncementDetails> response) {
                            if (null != response.body()) {
                                callback.onSuccess(response.body());
                            } else {
                                callback.onError(ErrorMessage.setErrorMessage("no response to server"));
                            }

                    }

                    @Override
                    public void onFailure(Call<AnnouncementDetails> call, Throwable t) {
                        if (null != t.getMessage())
                            callback.onError(ErrorMessage.setErrorMessage(t.getMessage()));
                        else
                            callback.onError(ErrorMessage.setErrorMessage("no response to server"));
                    }
                });
    }

    public static void setToggleSms(Boolean isChecked, String id, final SettingsCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        appInterface.setToggleSms(id, isChecked)
                .enqueue(new Callback<ToggleSMSDetails>() {
                    @Override
                    public void onResponse(Call<ToggleSMSDetails> call, Response<ToggleSMSDetails> response) {
                        if (null != response.body()) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError(ErrorMessage.setErrorMessage("no response to server"));
                        }
                    }

                    @Override
                    public void onFailure(Call<ToggleSMSDetails> call, Throwable t) {
                        if (null != t.getMessage())
                            callback.onError(ErrorMessage.setErrorMessage(t.getMessage()));
                        else
                            callback.onError(ErrorMessage.setErrorMessage("no response to server"));
                    }
                });
    }

    public static void getUserImage(String userId, final UserImageCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        appInterface.getUserImage(userId)
                .enqueue(new Callback<UserImageDetails>() {
                    @Override
                    public void onResponse(Call<UserImageDetails> call, Response<UserImageDetails> response) {
                        if (null != response.body()) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError(ErrorMessage.setErrorMessage("no response to server"));
                        }
                    }

                    @Override
                    public void onFailure(Call<UserImageDetails> call, Throwable t) {
                        if (null != t.getMessage())
                            callback.onError(ErrorMessage.setErrorMessage(t.getMessage()));
                        else
                            callback.onError(ErrorMessage.setErrorMessage("no response to server"));

                    }
                });
    }
}

