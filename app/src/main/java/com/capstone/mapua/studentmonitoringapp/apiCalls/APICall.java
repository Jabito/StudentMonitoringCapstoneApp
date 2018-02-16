package com.capstone.mapua.studentmonitoringapp.apiCalls;

import android.util.Log;

import com.capstone.mapua.studentmonitoringapp.callback.AnnouncementCallback;
import com.capstone.mapua.studentmonitoringapp.callback.LoginCallback;
import com.capstone.mapua.studentmonitoringapp.callback.SettingsCallback;
import com.capstone.mapua.studentmonitoringapp.callback.TapCallback;
import com.capstone.mapua.studentmonitoringapp.model.AnnouncementDetails;
import com.capstone.mapua.studentmonitoringapp.model.EmergencyContactDetails;
import com.capstone.mapua.studentmonitoringapp.model.LogInDetails;
import com.capstone.mapua.studentmonitoringapp.model.ParentDetails;
import com.capstone.mapua.studentmonitoringapp.model.StudentDetails;
import com.capstone.mapua.studentmonitoringapp.model.TapDetails;
import com.capstone.mapua.studentmonitoringapp.services.AppInterface;
import com.capstone.mapua.studentmonitoringapp.services.AppService;
import com.capstone.mapua.studentmonitoringapp.utilities.ErrorMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by IPC on 11/22/2017.
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
                        if (response.body() != null) {
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
                    public void onFailure(Call<LogInDetails> call, Throwable e) {
                        try {
                            callback.onErrorSignIn(e.getMessage());
                            Log.e("error2", ErrorMessage.setErrorMessage(e.getMessage()));
                        } catch (Exception error) {
                            callback.onErrorSignIn("");
                            Log.e("error3", "error3");
                        }

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
                        if (response.body() != null) {
                            if (response.body().getResponseCode().equalsIgnoreCase("NOT_FOUND")) {
                                callback.onErrorGetParentDetails("USER NOT FOUND");
                            } else if (response.body().getResponseCode().equalsIgnoreCase("OK")) {
                                callback.onSuccessGetParentDetails(response.body());
                            } else if (response.body().getResponseCode().equalsIgnoreCase("UNAUTHORIZED")) {
                                callback.onErrorGetParentDetails("UNAUTHORIZED USER");
                            }
                        } else {
                            callback.onErrorGetParentDetails("no response to server");
                        }
                    }

                    @Override
                    public void onFailure(Call<ParentDetails> call, Throwable e) {
                        try {
                            callback.onErrorGetParentDetails(ErrorMessage.setErrorMessage(e.getMessage()));
                            Log.e("error2getParent", e.getMessage());
                        } catch (Exception error) {
                            callback.onErrorGetParentDetails("");
                            Log.e("error3getParent", "error3");
                        }

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
                        if (response.body() != null) {
                            callback.onSuccessGetStudentDetails(response.body());
                            Log.e("success", "success");
                        } else {
                            callback.onErrorGetStudentDetails("no response to server");
                            Log.e("error1", "error1");
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentDetails> call, Throwable e) {
                        try {
                            callback.onErrorGetStudentDetails(e.getMessage());
                            Log.e("error2", e.getMessage());
                        } catch (Exception error) {
                            callback.onErrorGetStudentDetails("");
                            Log.e("error3", "error3");
                        }

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
                        if (response.body() != null) {
                            callback.onSuccessGetEmergencyContactDetails(response.body());
                            Log.e("success", "success");
                        } else {
                            callback.onErrorGetEmergencyContactDetails("no response to server");
                            Log.e("error1", "error1");
                        }
                    }

                    @Override
                    public void onFailure(Call<EmergencyContactDetails> call, Throwable e) {
                        try {
                            callback.onErrorGetEmergencyContactDetails(e.getMessage());
                            Log.e("error2", e.getMessage());
                        } catch (Exception error) {
                            callback.onErrorGetEmergencyContactDetails("");
                            Log.e("error3", "error3");
                        }

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
                        if (response.body() != null) {
                            callback.onSuccessTapDetails(response.body());
                            Log.e("success getParent", "success");
                        } else {
                            callback.onErrorTapDetails("no response to server");
                            Log.e("error1 getParent", "error1");
                        }
                    }

                    @Override
                    public void onFailure(Call<TapDetails> call, Throwable e) {
                        try {
                            callback.onErrorTapDetails(ErrorMessage.setErrorMessage(e.getMessage()));
                            Log.e("error2getParent", e.getMessage());
                        } catch (Exception error) {
                            callback.onErrorTapDetails("");
                            Log.e("error3getParent", "error3");
                        }

                    }
                });

    }


    public static void getAnnouncements(String parentId, final AnnouncementCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        appInterface.getAnnouncements(parentId)
                .enqueue(new Callback<AnnouncementDetails>() {
                    @Override
                    public void onResponse(Call<AnnouncementDetails> call, Response<AnnouncementDetails> response) {
                        try {
                            if (response.body() != null) {
                                callback.onSuccess(response.body());
                            } else {
                                callback.onError("no response to server");
                            }
                        } catch (Exception e) {
                            callback.onError("");
                        }
                    }

                    @Override
                    public void onFailure(Call<AnnouncementDetails> call, Throwable t) {
                        try {
                            callback.onError(t.getMessage());
                        } catch (Exception e) {
                            callback.onError("");
                        }
                    }
                });
    }

    public static void setToggleSms(Boolean isChecked, String id, final SettingsCallback callback) {
        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        appInterface.setToggleSms(id, isChecked)
                .enqueue(new Callback<AnnouncementDetails>() {
                    @Override
                    public void onResponse(Call<AnnouncementDetails> call, Response<AnnouncementDetails> response) {
                        try {
                            if (response.body() != null) {
                                callback.onSuccess(response.body());
                            } else {
                                callback.onError("no response to server");
                            }
                        } catch (Exception e) {
                            callback.onError("");
                        }
                    }

                    @Override
                    public void onFailure(Call<AnnouncementDetails> call, Throwable t) {
                        try {
                            callback.onError(t.getMessage());
                        } catch (Exception e) {
                            callback.onError("");
                        }
                    }
                });
    }
}
