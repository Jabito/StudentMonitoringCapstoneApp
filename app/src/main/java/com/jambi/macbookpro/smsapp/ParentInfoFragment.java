package com.jambi.macbookpro.smsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jambi.macbookpro.smsapp.model.Parent;
import com.jambi.macbookpro.smsapp.model.Student;
import com.jambi.macbookpro.smsapp.utilities.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IPC on 11/23/2017.
 */

public class ParentInfoFragment extends Fragment {

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


    Parent parent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_info, container, false);
        ButterKnife.bind(this,view);
        parent = SharedPref.parentData;

//        tv_last_update.setText(parent.getUpdatedOn());
        tv_last_update.setText("Last Update : 11/23/2017");
        tv_name.setText(parent.getParentLName() +", "+parent.getParentFName());
        tv_desc.setText(parent.getOccupation());
        tv_parent_of.setText(parent.getParentOf());
        tv_contact.setText(parent.getContactNo());
        return view;
    }
}
