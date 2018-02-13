package com.capstone.mapua.studentmonitoringapp.callback;


import com.capstone.mapua.studentmonitoringapp.model.EmergencyContactDetails;
import com.capstone.mapua.studentmonitoringapp.model.LogInDetails;
import com.capstone.mapua.studentmonitoringapp.model.ParentDetails;
import com.capstone.mapua.studentmonitoringapp.model.StudentDetails;

/**
 * Created by IPC on 11/22/2017.
 */

public interface LoginCallback {

    void onSuccessSignIn(LogInDetails body);

    void onErrorSignIn(String message);

    void onSuccessGetParentDetails(ParentDetails body);

    void onErrorGetParentDetails(String s);

    void onSuccessGetStudentDetails(StudentDetails body);

    void onErrorGetStudentDetails(String s);

    void onSuccessGetEmergencyContactDetails(EmergencyContactDetails body);

    void onErrorGetEmergencyContactDetails(String s);
}
