package com.jambi.macbookpro.smsapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jambi.macbookpro.smsapp.R;

/**
 * Created by macbookpro on 9/5/17.
 */

public class AttendanceFragment extends Fragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_logs, container, false);
        return view;
    }
}
