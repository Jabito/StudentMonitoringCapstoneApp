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
 * Created by jj on 9/5/17.
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
        dbHandler = new DatabaseHandler(context);
        implement = new AttendanceLogImplement(context, dbHandler, callback);
        SharedPref.LOG_TITLE = "";
        tapLogAdapter = new TapLogAdapter(context, logArrayList);
        rv_logs.setLayoutManager(new LinearLayoutManager(context));
        rv_logs.setAdapter(tapLogAdapter);
        sr_swipe.setColorSchemeResources(R.color.colorPrimary);
        sr_swipe.setOnRefreshListener(this);

        getCurrentLog();
        return view;
    }

    private void getCurrentLog() {
        SharedPref.LOG_TITLE = "LOG_TITLE";
        logArrayList.clear();
        logArrayList.addAll(dbHandler.getTapLogList());
        tapLogAdapter.notifyDataSetChanged();


        String label;

        if(logArrayList.size() == 0){
            label = "" + getText(R.string.pullDown);
        }else {
            label = "" + getText(R.string.pullDown)
                    + "\nUpdated as of: "
                    + SharedPref.getStringValue(SharedPref.USER, SharedPref.LAST_LOG_UPDATE, context);
        }
        tv_lastUpdate.setText(label);

    }

    @Override
    public void onSuccessTapDetails(TapDetails body) {
        implement.getTapLogOfStudent(body.getTapListDetails(), logArrayList);

    }

    @Override
    public void onErrorTapDetails(String s) {
        if (sr_swipe != null)
            sr_swipe.setRefreshing(false);
        dialog.showMessage(context, dialog.NO_Internet_title, s, 1);
    }

    @Override
    public void onSaveComplete(ArrayList<TapLog> logArrayList) {
        try{
            SharedPref.LOG_TITLE = "LOG_TITLE";
            this.logArrayList = logArrayList;
            tapLogAdapter.notifyDataSetChanged();
            String currDate = DateConverter.getCurrentDate();

            SharedPref.setStringValue(SharedPref.USER, SharedPref.LAST_LOG_UPDATE, currDate, context);
            tv_lastUpdate.setText(getText(R.string.pullDown)
                    + "\nUpdated as of: "
                    + currDate);
            if (sr_swipe != null)
                sr_swipe.setRefreshing(false);
        }catch (Exception e){
            if (sr_swipe != null)
                sr_swipe.setRefreshing(false);
        }

    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetworkTest.isOnline(context)) {
                    String studentId = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_PARENT_OF, context);
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
