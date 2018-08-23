package com.capstone.mapua.studentmonitoringapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


import com.capstone.mapua.studentmonitoringapp.callback.LoginCallback;
import com.capstone.mapua.studentmonitoringapp.implement.LoginImplement;
import com.capstone.mapua.studentmonitoringapp.model.EmergencyContactDetails;
import com.capstone.mapua.studentmonitoringapp.model.LogInDetails;
import com.capstone.mapua.studentmonitoringapp.model.ParentDetails;
import com.capstone.mapua.studentmonitoringapp.model.StudentDetails;
import com.capstone.mapua.studentmonitoringapp.utilities.CustomDialog;
import com.capstone.mapua.studentmonitoringapp.utilities.DateConverter;
import com.capstone.mapua.studentmonitoringapp.utilities.Loader;
import com.capstone.mapua.studentmonitoringapp.utilities.NetworkTest;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements LoginCallback, View.OnClickListener {

    @BindView(R.id.edit_username)
    EditText edit_username;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.button_signin)
    Button button_signin;


    LoginImplement implement;
    Context context;
    LoginCallback callback;
    Loader loader;
    CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        context = this;
        implement = new LoginImplement(context);
        callback = this;
        loader = new Loader(context);
        dialog = new CustomDialog();
        ButterKnife.bind(this);
        loader.setPropertes();


        button_signin.setOnClickListener(this);

    }

    @OnClick({R.id.button_signin})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_signin:


                String username = edit_username.getText().toString();
                String password = edit_password.getText().toString();
                if (username.isEmpty()) {
                    edit_username.setError("Enter username");
                } else if (password.isEmpty()) {
                    edit_password.setError("Enter password");
                } else {
                    loader.setMessage("Signing In...\nPlease Wait");
                    loader.startLoad();
                    if (NetworkTest.isOnline(context)) {
                        implement.getSignInData(username, password, callback);
                    } else {
                        loader.stopLoad();
                        dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
                    }

                }

                break;

        }
    }


    @Override
    public void onSuccessSignIn(LogInDetails body) {
        SharedPref.userData = body.getUser();
//        implement.getParent(body.getUser().getId(),callback);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.USER_ID, body.getUser().getId(), context);


        if (null == body.getParent()) {
            loader.stopLoad();
            dialog.showMessage(context, dialog.NO_Internet_title, dialog.cannot_login, 1);
        } else if (null != body.getParent().getId() && NetworkTest.isOnline(context)) {
            implement.getParent(body.getParent().getId(), callback);
        } else {
            loader.stopLoad();
            dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
        }


    }

    @Override
    public void onErrorSignIn(String message) {
        loader.stopLoad();
        dialog.showMessage(context, dialog.NO_Internet_title, message, 1);
    }

    @Override
    public void onSuccessGetParentDetails(ParentDetails body) {
        SharedPref.parentData = body.getParent();
        SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_ID, body.getParent().getId(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_PARENT_OF, body.getParent().getParentOf(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_NAME, body.getParent().getParentName(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_OCUPATION, body.getParent().getOccupation(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_RELATIONSHIP, body.getParent().getRelationship(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_LAST_UPDATE, DateConverter.getCurrentDate(), context);


        if (null == body.getParent()) {
            loader.stopLoad();
            dialog.showMessage(context, dialog.NO_Internet_title, dialog.cannot_login, 1);
        } else if (null != body.getParent().getParentOf() && NetworkTest.isOnline(context)) {
             implement.getStudent(body.getParent().getParentOf(), callback);
         } else {
            loader.stopLoad();
            dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
        }



    }


    @Override
    public void onErrorGetParentDetails(String message) {
        loader.checkLoad();
        dialog.showMessage(context, dialog.NO_Internet_title, message, 1);
    }

    @Override
    public void onSuccessGetStudentDetails(StudentDetails body) {
        SharedPref.studentData = body.getStudent();
        loader.checkLoad();

        SharedPref.setStringValue(SharedPref.USER, SharedPref.STUDENT_id, body.getStudent().getId(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.STUDENT_lastName, body.getStudent().getLastName(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.STUDENT_firstName, body.getStudent().getFirstName(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.STUDENT_middleName, body.getStudent().getMiddleName(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.STUDENT_section, body.getStudent().getSection(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.STUDENT_contactNo, body.getStudent().getContactNo(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.STUDENT_emergencyContact, body.getStudent().getEmergencyContact(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.STUDENT_updatedOn, DateConverter.getCurrentDate(), context);
        SharedPref.setIntegerValue(SharedPref.USER, SharedPref.STUDENT_gradeLvlId, body.getStudent().getGradeLvlId(), context);

        if (null == body.getStudent()) {
            loader.stopLoad();
            dialog.showMessage(context, dialog.NO_Internet_title, dialog.cannot_login, 1);
        } else {
            SharedPref.setBooleanValue(SharedPref.USER, SharedPref.SESSION_ON, true, context);
            Intent intent = new Intent(this, NavigationActivity.class);
            startActivity(intent);
            finish();
        }


        //TODO WILL USE IF EMERGENCY CAN BE RETRIEVE
//        if (NetworkTest.isOnline(context)) {
//            implement.getEmergencyContact(body.getParent().getParentOf(), callback);
//        } else {
//            loader.stopLoad();
//            dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
//        }

    }

    @Override
    public void onErrorGetStudentDetails(String message) {
        loader.checkLoad();
        dialog.showMessage(context, dialog.NO_Internet_title, message, 1);

    }


    @Override
    public void onSuccessGetEmergencyContactDetails(EmergencyContactDetails body) {


    }

    @Override
    public void onErrorGetEmergencyContactDetails(String s) {
        loader.checkLoad();
        dialog.showMessage(context, dialog.NO_Internet_title, s, 1);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
