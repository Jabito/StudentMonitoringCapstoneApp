package com.capstone.mapua.studentmonitoringapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.capstone.mapua.studentmonitoringapp.R;
import com.capstone.mapua.studentmonitoringapp.model.Student;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IPC on 11/23/2017.
 */

public class StudentInfoFragment extends Fragment {

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

    Student student;
    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_info, container, false);
        ButterKnife.bind(this,view);
        context = getContext();


        String studentName = SharedPref.getStringValue(SharedPref.USER,SharedPref.STUDENT_lastName,context) +", "+SharedPref.getStringValue(SharedPref.USER,SharedPref.STUDENT_firstName,context) + " " + SharedPref.getStringValue(SharedPref.USER,SharedPref.STUDENT_middleName,context);
        String studentId = SharedPref.getStringValue(SharedPref.USER,SharedPref.STUDENT_id,context);
        String studentSection = SharedPref.getStringValue(SharedPref.USER,SharedPref.STUDENT_section,context);
        String studentContact = SharedPref.getStringValue(SharedPref.USER,SharedPref.STUDENT_contactNo,context);
        String studentEmerContact = SharedPref.getStringValue(SharedPref.USER,SharedPref.STUDENT_emergencyContact,context);

//        tv_last_update.setText(student.getUpdatedOn());
        tv_last_update.setText("Last Update : 11/23/2017");
        tv_name.setText(studentName);
        tv_desc.setText(studentId);
        tv_section.setText(studentSection);
        tv_contact.setText(studentContact);
        tv_emergeny_contact.setText(studentEmerContact);
        return view;
    }
}