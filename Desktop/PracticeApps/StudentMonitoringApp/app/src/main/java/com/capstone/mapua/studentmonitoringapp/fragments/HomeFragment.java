package com.capstone.mapua.studentmonitoringapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.capstone.mapua.studentmonitoringapp.R;
import com.capstone.mapua.studentmonitoringapp.adapter.AnnouncementAdapter;
import com.capstone.mapua.studentmonitoringapp.callback.AnnouncementCallback;
import com.capstone.mapua.studentmonitoringapp.implement.AnnouncementImplement;
import com.capstone.mapua.studentmonitoringapp.model.Announcement;
import com.capstone.mapua.studentmonitoringapp.model.AnnouncementDetails;
import com.capstone.mapua.studentmonitoringapp.model.User;
import com.capstone.mapua.studentmonitoringapp.utilities.CustomDialog;
import com.capstone.mapua.studentmonitoringapp.utilities.Loader;
import com.capstone.mapua.studentmonitoringapp.utilities.NetworkTest;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by macbookpro on 9/5/17.
 */

public class HomeFragment extends Fragment implements AnnouncementCallback, View.OnClickListener {


    @BindView(R.id.tv_display_user)
    TextView tv_display_user;
    @BindView(R.id.tv_display_announcement)
    TextView tv_display_announcement;

    @BindView(R.id.rv_announcement)
    RecyclerView rv_announcement;

    @BindView(R.id.tv_refresh)
    TextView tv_refresh;
    @BindView(R.id.btn_refresh)
    Button btn_refresh;



    Context context;
    AnnouncementImplement implement;
    AnnouncementCallback callback;
    AnnouncementAdapter adapter;
    CustomDialog dialog;
    Loader loader;
    User user;
    ArrayList<Announcement> announcementArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        callback = this;
        implement = new AnnouncementImplement(context);
        dialog = new CustomDialog();
        loader = new Loader(context);
        loader.setPropertes();
        btn_refresh.setOnClickListener(this);
        user = SharedPref.userData;


        setParentName();
        getLatestAnnouncement();


        return view;
    }

    private void setParentName() {
        try{
            String name = SharedPref.getStringValue(SharedPref.USER,SharedPref.PARENT_LNAME,context) + SharedPref.getStringValue(SharedPref.USER,SharedPref.PARENT_FNAME,context);
            tv_display_user.setText("Welcome " + name);
        }catch (Exception e){
            tv_display_user.setText("Welcome");
        }


    }

    private void getLatestAnnouncement() {
        if (NetworkTest.isOnline(context)) {
            loader.setMessage("Loading...\nPlease Wait");
            loader.startLoad();
            implement.getAnnouncements(SharedPref.getStringValue(SharedPref.USER,SharedPref.USER_ID,context), callback);
        } else {
            loader.stopLoad();
            btn_refresh.setVisibility(View.VISIBLE);
            tv_display_announcement.setVisibility(View.GONE);
            tv_refresh.setText(context.getString(R.string.no_net));
            rv_announcement.setVisibility(View.GONE);
            dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
        }
    }

    @Override
    public void onSuccess(AnnouncementDetails body) {
        btn_refresh.setVisibility(View.GONE);
        tv_display_announcement.setVisibility(View.VISIBLE);
        tv_refresh.setText(context.getString(R.string.announcement));
        rv_announcement.setVisibility(View.VISIBLE);


        announcementArrayList.clear();
        for (int i = 0; i < body.getAnnouncements().size(); i++) {
            if (i == 0) {
                tv_display_announcement.setText(body.getAnnouncements().get(i).getMessage());
            }else{
                announcementArrayList.add(body.getAnnouncements().get(i));
            }
        }
//        announcementArrayList.addAll(body.getAnnouncements());
        adapter = new AnnouncementAdapter(rv_announcement, context, announcementArrayList);
        rv_announcement.setLayoutManager(new LinearLayoutManager(context));
        rv_announcement.setAdapter(adapter);
        loader.stopLoad();
    }

    @Override
    public void onError(String s) {
        loader.stopLoad();
        dialog.showMessage(context, dialog.NO_Internet_title, s, 1);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_refresh:
                getLatestAnnouncement();
                break;
        }
    }
}
