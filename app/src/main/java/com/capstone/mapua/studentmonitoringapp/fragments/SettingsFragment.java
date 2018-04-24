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
import com.capstone.mapua.studentmonitoringapp.model.AnnouncementDetails;
import com.capstone.mapua.studentmonitoringapp.utilities.CustomDialog;
import com.capstone.mapua.studentmonitoringapp.utilities.ErrorMessage;
import com.capstone.mapua.studentmonitoringapp.utilities.NetworkTest;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IPC on 11/26/2017.
 */

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, SettingsCallback {

    @BindView(R.id.switch_toggle)
    Switch switch_toggle;

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

        switch_toggle.setOnCheckedChangeListener(this);

        try {
            if (SharedPref.getStringValue(SharedPref.USER, SharedPref.SMS_TOGGLE, context).equalsIgnoreCase("true")) {
                switch_toggle.setChecked(true);
            } else if (SharedPref.getStringValue(SharedPref.USER, SharedPref.SMS_TOGGLE, context).equalsIgnoreCase("false")) {
                switch_toggle.setChecked(false);
            }
        } catch (Exception e) {

        }

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            if (NetworkTest.isOnline(context)) {
                implement.setToggleSms(isChecked, parentId, callback);
                setShared(isChecked, SharedPref.SMS_TOGGLE);
            } else {
                switch_toggle.setChecked(!isChecked);
                dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
            }
        } else {
            if (NetworkTest.isOnline(context)) {
                implement.setToggleSms(isChecked, parentId, callback);
                setShared(isChecked, SharedPref.SMS_TOGGLE);
            } else {
                switch_toggle.setChecked(isChecked);
                dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
            }
        }
    }

    @Override
    public void onSuccess(AnnouncementDetails body) {

    }

    @Override
    public void onError(String s) {
        dialog.showMessage(context, dialog.NO_Internet_title, s, 1);
    }

    private void setShared(Boolean isChecked, String key) {
        if (isChecked) {
            SharedPref.setStringValue(SharedPref.USER, key, "true", context);
        } else {
            SharedPref.setStringValue(SharedPref.USER, key, "false", context);

        }

    }
}
