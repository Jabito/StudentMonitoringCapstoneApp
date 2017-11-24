package com.jambi.macbookpro.smsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.iid.FirebaseInstanceId;
import com.jambi.macbookpro.smsapp.callback.LoginCallback;
import com.jambi.macbookpro.smsapp.implement.LoginImplement;
import com.jambi.macbookpro.smsapp.model.EmergencyContactDetails;
import com.jambi.macbookpro.smsapp.model.GuidanceDetails;
import com.jambi.macbookpro.smsapp.model.LogInDetails;
import com.jambi.macbookpro.smsapp.model.ParentDetails;
import com.jambi.macbookpro.smsapp.model.StudentDetails;
import com.jambi.macbookpro.smsapp.utilities.CustomDialog;
import com.jambi.macbookpro.smsapp.utilities.FireBaseNotification;
import com.jambi.macbookpro.smsapp.utilities.FirebaseIDService;
import com.jambi.macbookpro.smsapp.utilities.Loader;
import com.jambi.macbookpro.smsapp.utilities.NetworkTest;
import com.jambi.macbookpro.smsapp.utilities.SharedPref;

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



        edit_username.setText(FirebaseInstanceId.getInstance().getToken());

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
                    loader.setMessage("Signing In.. \n Please Wait");
                    loader.startLoad();
                    if (NetworkTest.isOnline(context)) {
                        implement.getSignInData(username, password, callback);
                    }else {
                        dialog.showMessage(context,dialog.NO_Internet_title,dialog.NO_Internet,1);
                    }

                }

                break;

        }
    }


    @Override
    public void onSuccessSignIn(LogInDetails body) {
        SharedPref.userData = body.getUser();
//        implement.getParent(body.getUser().getId(),callback);
        implement.getParent("parent1230",callback);


    }

    @Override
    public void onErrorSignIn(String message) {
        loader.stopLoad();
        dialog.showMessage(context,dialog.NO_Internet_title, message,1);
    }

    @Override
    public void onSuccessGetParentDetails(ParentDetails body) {
        loader.stopLoad();
//        SharedPref.setStringValue(SharedPref.USER,SharedPref.PARENT_ID,body.getParent().getId(),context);
//        SharedPref.setStringValue(SharedPref.USER,SharedPref.PARENT_PARENT_OF,body.getParent().getParentOf(),context);
//        SharedPref.setStringValue(SharedPref.USER,SharedPref.PARENT_RELATIONSHIP,body.getParent().getRelationship(),context);
//        SharedPref.setStringValue(SharedPref.USER,SharedPref.PARENT_LNAME,body.getParent().getParentLName(),context);
//        SharedPref.setStringValue(SharedPref.USER,SharedPref.PARENT_FNAME,body.getParent().getParentFName(),context);
//        SharedPref.setStringValue(SharedPref.USER,SharedPref.PARENT_CONTACT,body.getParent().getContactNo(),context);
//        SharedPref.setStringValue(SharedPref.USER,SharedPref.PARENT_OCUPATION,body.getParent().getOccupation(),context);

        SharedPref.parentData = body.getParent();

        Log.e("getParentOf",body.getParent().getParentOf());
        implement.getStudent(body.getParent().getParentOf(),callback);



    }

    @Override
    public void onErrorGetParentDetails(String message) {
        dialog.showMessage(context,dialog.NO_Internet_title, message,1);
    }

    @Override
    public void onSuccessGetStudentDetails(StudentDetails body) {
        SharedPref.studentData = body.getStudent();

        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onErrorGetStudentDetails(String s) {

    }

    @Override
    public void onSuccessGetGuidanceDetails(GuidanceDetails body) {

    }

    @Override
    public void onErrorGetGuidanceDetails(String s) {

    }

    @Override
    public void onSuccessGetEmergencyContactDetails(EmergencyContactDetails body) {

    }

    @Override
    public void onErrorGetEmergencyContactDetails(String s) {

    }


}
