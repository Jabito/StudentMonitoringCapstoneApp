package com.capstone.mapua.studentmonitoringapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;


import com.capstone.mapua.studentmonitoringapp.R;
import com.capstone.mapua.studentmonitoringapp.callback.SettingsCallback;
import com.capstone.mapua.studentmonitoringapp.implement.SettingsImplement;
import com.capstone.mapua.studentmonitoringapp.model.ToggleSMSDetails;
import com.capstone.mapua.studentmonitoringapp.utilities.CustomDialog;
import com.capstone.mapua.studentmonitoringapp.utilities.NetworkTest;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jj on 11/26/2017.
 */

public class SettingsFragment extends Fragment implements SettingsCallback {

    @BindView(R.id.switch_sms)
    Switch sw_sms;

    SettingsImplement implement;
    Context context;
    CustomDialog dialog;
    SettingsCallback callback;

    String parentId;
    Boolean smsToggle, notifToggle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        callback = this;
        dialog = new CustomDialog();
        implement = new SettingsImplement(context);
        parentId = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_ID, context);
        smsToggle = SharedPref.getBooleanValue(SharedPref.USER, SharedPref.SMS_TOGGLE, context);

        sw_sms.setChecked(smsToggle);

        sw_sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (NetworkTest.isOnline(context)) {
                    implement.setToggleSms(isChecked, parentId, callback);
                    setShared(isChecked,SharedPref.SMS_TOGGLE);
                } else {
                    sw_sms.setChecked(!isChecked);
                    setShared(!isChecked,SharedPref.SMS_TOGGLE);
                    dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
                }

            }
        });



        return view;
    }
    
    @Override
    public void onToggleSmsSuccess(ToggleSMSDetails body) {

    }

    @Override
    public void onToggleSmsError(String s) {
        dialog.showMessage(context, dialog.NO_Internet_title, s, 1);
    }

    private void setShared(Boolean isChecked, String key) {
        SharedPref.setBooleanValue(SharedPref.USER, key, isChecked, context);
    }
}
