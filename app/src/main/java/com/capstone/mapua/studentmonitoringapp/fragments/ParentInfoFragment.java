package com.capstone.mapua.studentmonitoringapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.capstone.mapua.studentmonitoringapp.R;
import com.capstone.mapua.studentmonitoringapp.callback.UserImageCallback;
import com.capstone.mapua.studentmonitoringapp.implement.StudentInfoImplement;
import com.capstone.mapua.studentmonitoringapp.model.UserImageDetails;
import com.capstone.mapua.studentmonitoringapp.utilities.CustomDialog;
import com.capstone.mapua.studentmonitoringapp.utilities.ImageConvert;
import com.capstone.mapua.studentmonitoringapp.utilities.NetworkTest;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by IPC on 11/23/2017.
 */

public class ParentInfoFragment extends Fragment implements UserImageCallback {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_parent_of)
    TextView tv_parent_of;
    @BindView(R.id.tv_relationship)
    TextView tv_relationship;
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_last_update)
    TextView tv_last_update;
    @BindView(R.id.iv_userImage)
    CircleImageView iv_userImage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    Context context;
    StudentInfoImplement implement;
    UserImageCallback callback;
    CustomDialog dialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_info, container, false);
        ButterKnife.bind(this,view);
        context = getContext();
        implement = new StudentInfoImplement(context);
        callback = this;
        dialog = new CustomDialog();

//        tv_last_update.setText(parent.getUpdatedOn());
        String studentName = SharedPref.getStringValue(SharedPref.USER,SharedPref.STUDENT_lastName,context) +", "+SharedPref.getStringValue(SharedPref.USER,SharedPref.STUDENT_firstName,context) + " " + SharedPref.getStringValue(SharedPref.USER,SharedPref.STUDENT_middleName,context);
        String parentName = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_LNAME, context) + ", " + SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_FNAME, context);
        String parentOccupation = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_OCUPATION, context);
        String parentContact = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_CONTACT, context);
        String parentRelationship = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_RELATIONSHIP, context);
        String parentLastUpdate = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_LAST_UPDATE, context);
        String parentId = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_ID, context);



        tv_last_update.setText("Last Update : " + parentLastUpdate);
        tv_name.setText(parentName);
        tv_desc.setText(parentOccupation);
        tv_parent_of.setText(studentName);
        tv_contact.setText(parentContact);
        tv_relationship.setText(parentRelationship);

        if (NetworkTest.isOnline(context)) {
            progressBar.setVisibility(View.VISIBLE);
            implement.getUserImage(parentId, callback);
        } else {
            progressBar.setVisibility(View.GONE);
            dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
        }
        return view;
    }

    @Override
    public void onSuccess(UserImageDetails body) {
        progressBar.setVisibility(View.GONE);
        ImageConvert.setBase64ToImageView(body.getImageBase64(),iv_userImage);

    }

    @Override
    public void onError(String message) {
        progressBar.setVisibility(View.GONE);
        dialog.showMessage(context, dialog.NO_Internet_title, message, 1);
    }
}
