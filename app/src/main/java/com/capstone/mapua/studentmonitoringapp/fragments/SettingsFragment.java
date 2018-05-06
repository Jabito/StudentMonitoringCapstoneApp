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
 * Created by IPC on 11/26/2017.
 */

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, SettingsCallback {

    @BindView(R.id.switch_sms)
    Switch switch_sms;
    @BindView(R.id.switch_notif)
    Switch switch_notif;

    SettingsImplement implement;
    Context context;
    CustomDialog dialog;
    SettingsCallback callback;
    String parentId;

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

        switch_sms.setOnCheckedChangeListener(this);
        switch_notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_notif.setChecked(false);
                    setShared(false, SharedPref.NOTIF_TOGGLE);
                } else {
                    switch_notif.setChecked(true);
                    setShared(true, SharedPref.NOTIF_TOGGLE);

                }
            }
        });

        try {
            if (SharedPref.getBooleanValue(SharedPref.USER, SharedPref.SMS_TOGGLE, context)) {
                switch_sms.setChecked(true);
            } else{
                switch_sms.setChecked(false);
            }
        } catch (Exception e) {
            setShared(true, SharedPref.SMS_TOGGLE);
            switch_sms.setChecked(true);
        }

        try {
            if (SharedPref.getBooleanValue(SharedPref.USER, SharedPref.NOTIF_TOGGLE, context)) {
                switch_notif.setChecked(true);
            } else {
                switch_notif.setChecked(false);
            }
        } catch (Exception e) {
            setShared(true, SharedPref.NOTIF_TOGGLE);
            switch_notif.setChecked(true);
        }

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            if (NetworkTest.isOnline(context)) {
                implement.setToggleSms(false, parentId, callback);
                setShared(false, SharedPref.SMS_TOGGLE);
            } else {
                switch_sms.setChecked(true);
                dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
            }
        } else {
            if (NetworkTest.isOnline(context)) {
                implement.setToggleSms(true, parentId, callback);
                setShared(true, SharedPref.SMS_TOGGLE);
            } else {
                switch_sms.setChecked(false);
                dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
            }
        }
    }

    @Override
    public void onSuccess(ToggleSMSDetails body) {

    }

    @Override
    public void onError(String s) {
        dialog.showMessage(context, dialog.NO_Internet_title, s, 1);
    }

    private void setShared(Boolean isChecked, String key) {
            SharedPref.setBooleanValue(SharedPref.USER, key, isChecked, context);
    }
}
