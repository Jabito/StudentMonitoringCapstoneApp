package com.jambi.macbookpro.smsapp.callback;

import com.jambi.macbookpro.smsapp.model.LogInDetails;
import com.jambi.macbookpro.smsapp.model.EmergencyContactDetails;
import com.jambi.macbookpro.smsapp.model.GuidanceDetails;
import com.jambi.macbookpro.smsapp.model.LogInDetails;
import com.jambi.macbookpro.smsapp.model.ParentDetails;
import com.jambi.macbookpro.smsapp.model.StudentDetails;

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
