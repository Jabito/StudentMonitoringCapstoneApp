package com.capstone.mapua.studentmonitoringapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.capstone.mapua.studentmonitoringapp.R;
import com.capstone.mapua.studentmonitoringapp.adapter.TapLogAdapter;
import com.capstone.mapua.studentmonitoringapp.apiCalls.APICall;
import com.capstone.mapua.studentmonitoringapp.callback.TapCallback;
import com.capstone.mapua.studentmonitoringapp.database.DatabaseHandler;
import com.capstone.mapua.studentmonitoringapp.implement.AttendanceLogImplement;
import com.capstone.mapua.studentmonitoringapp.model.TapDetails;
import com.capstone.mapua.studentmonitoringapp.model.TapLog;
import com.capstone.mapua.studentmonitoringapp.utilities.CustomDialog;
import com.capstone.mapua.studentmonitoringapp.utilities.DateConverter;
import com.capstone.mapua.studentmonitoringapp.utilities.NetworkTest;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by macbookpro on 9/5/17.
 */

public class AttendanceFragment extends Fragment implements TapCallback, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.sr_swipe)
    SwipeRefreshLayout sr_swipe;
    @BindView(R.id.rv_logs)
    RecyclerView rv_logs;
    @BindView(R.id.tv_lastUpdate)
    TextView tv_lastUpdate;


    Context context;
    AttendanceLogImplement implement;
    TapCallback callback;
    CustomDialog dialog;
    DatabaseHandler dbHandler;
    TapLogAdapter tapLogAdapter;
    ArrayList<TapLog> logArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_logs_top, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        callback = this;
        dialog = new CustomDialog();
        implement = new AttendanceLogImplement(context,dbHandler,callback);
        dbHandler = new DatabaseHandler(context);
        tapLogAdapter = new TapLogAdapter(context,logArrayList);

        sr_swipe.setColorSchemeResources(R.color.colorPrimary);
        sr_swipe.setOnRefreshListener(this);


        rv_logs.setLayoutManager(new LinearLayoutManager(context));
        rv_logs.setAdapter(tapLogAdapter);

        return view;
    }

    @Override
    public void onSuccessTapDetails(TapDetails body) {
        implement.getTapLogOfStudent(body.getTapListDetails());

    }

    @Override
    public void onErrorTapDetails(String s) {
        if (sr_swipe != null)
            sr_swipe.setRefreshing(false);
        dialog.showMessage(context, dialog.NO_Internet_title, s, 1);
    }

    @Override
    public void onSaveComplete(ArrayList<TapLog> logArrayList) {
        this.logArrayList = logArrayList;
        tapLogAdapter.notifyDataSetChanged();
        tv_lastUpdate.setText(getText(R.string.pullDown) +"\nUpdated as of: "+ DateConverter.getCurrentDate());
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (NetworkTest.isOnline(context)) {
                    String studentId = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_id, context);
                    APICall.getTapLogOfStudent(studentId, callback);
                    sr_swipe.setRefreshing(true);
                } else {
                    dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
                    sr_swipe.setRefreshing(false);
                }
            }
        }, 3000);
    }
}
