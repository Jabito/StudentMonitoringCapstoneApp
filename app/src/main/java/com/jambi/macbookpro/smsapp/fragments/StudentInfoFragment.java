package com.jambi.macbookpro.smsapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jambi.macbookpro.smsapp.R;
import com.jambi.macbookpro.smsapp.model.Student;
import com.jambi.macbookpro.smsapp.utilities.SharedPref;

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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_info, container, false);
        ButterKnife.bind(this,view);
        student = SharedPref.studentData;

//        tv_last_update.setText(student.getUpdatedOn());
        tv_last_update.setText("Last Update : 11/23/2017");
        tv_name.setText(student.getLastName() +", "+student.getFirstName() +" "+ student.getMiddleName() );
        tv_desc.setText(student.getId());
        tv_section.setText(student.getSection());
        tv_contact.setText(student.getContactNo());
        tv_emergeny_contact.setText(student.getEmergencyContact());
        return view;
    }
}