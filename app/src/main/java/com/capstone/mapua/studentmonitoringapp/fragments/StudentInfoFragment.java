package com.capstone.mapua.studentmonitoringapp.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.capstone.mapua.studentmonitoringapp.R;
import com.capstone.mapua.studentmonitoringapp.callback.UserImageCallback;
import com.capstone.mapua.studentmonitoringapp.implement.StudentInfoImplement;
import com.capstone.mapua.studentmonitoringapp.model.Student;
import com.capstone.mapua.studentmonitoringapp.model.UserImageDetails;
import com.capstone.mapua.studentmonitoringapp.utilities.CustomDialog;
import com.capstone.mapua.studentmonitoringapp.utilities.NetworkTest;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by IPC on 11/23/2017.
 */

public class StudentInfoFragment extends Fragment implements UserImageCallback {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_section)
    TextView tv_section;
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_emergeny_contact)
    TextView tv_emergeny_contact;
    @BindView(R.id.tv_last_update)
    TextView tv_last_update;
    @BindView(R.id.iv_userImage)
    CircleImageView iv_userImage;
    //    ImageView iv_userImage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Student student;
    Context context;

    String studentName = "";
    String studentId = "";
    String studentSection = "";
    String studentContact = "";
    String studentEmerContact = "";
    String studentLastUpdateOn = "";

    StudentInfoImplement implement;
    UserImageCallback callback;
    CustomDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_info, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        implement = new StudentInfoImplement(context);
        callback = this;
        dialog = new CustomDialog();


        studentName = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_lastName, context) + ", " + SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_firstName, context) + " " + SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_middleName, context);
        studentId = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_id, context);
        studentSection = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_section, context);
        studentContact = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_contactNo, context);
        studentEmerContact = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_emergencyContact, context);
        studentLastUpdateOn = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_updatedOn, context);

//        tv_last_update.setText(student.getUpdatedOn());
        tv_last_update.setText("Last Update : " +studentLastUpdateOn);
        tv_name.setText(studentName);
        tv_desc.setText(studentId);
        tv_section.setText(studentSection);
        tv_contact.setText(studentContact);
        tv_emergeny_contact.setText(studentEmerContact);


        if (NetworkTest.isOnline(context)) {
            progressBar.setVisibility(View.VISIBLE);
            implement.getUserImage(studentId, callback);
        } else {
            progressBar.setVisibility(View.GONE);
            dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
        }

        return view;
    }


    @Override
    public void onSuccess(UserImageDetails body) {

        progressBar.setVisibility(View.GONE);
        try{
            byte[] decodedString = Base64.decode(body.getImageBase64().getBytes(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            iv_userImage.setImageBitmap(decodedByte);
        }catch (Exception e){

        }

    }

    @Override
    public void onError(String message) {
        progressBar.setVisibility(View.GONE);
        dialog.showMessage(context, dialog.NO_Internet_title, message, 1);
    }
}