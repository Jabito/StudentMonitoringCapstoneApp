package com.jambi.macbookpro.smsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.jambi.macbookpro.smsapp.callback.LoginCallback;
import com.jambi.macbookpro.smsapp.model.LogInDetails;
import com.jambi.macbookpro.smsapp.utilities.CustomDialog;
import com.jambi.macbookpro.smsapp.utilities.ErrorMessage;
import com.jambi.macbookpro.smsapp.utilities.Loader;
import com.jambi.macbookpro.smsapp.utilities.NetworkTest;

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
        loader.stopLoad();


    }

    @Override
    public void onErrorSignIn(String s) {
        dialog.showMessage(context,dialog.NO_Internet_title, ErrorMessage.setErrorMessage(s),1);
    }


}
