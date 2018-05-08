package com.capstone.mapua.studentmonitoringapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.capstone.mapua.studentmonitoringapp.R;
import com.capstone.mapua.studentmonitoringapp.adapter.AnnouncementAdapter;
import com.capstone.mapua.studentmonitoringapp.apiCalls.APICall;
import com.capstone.mapua.studentmonitoringapp.callback.AnnouncementCallback;
import com.capstone.mapua.studentmonitoringapp.database.DatabaseHandler;
import com.capstone.mapua.studentmonitoringapp.implement.AnnouncementImplement;
import com.capstone.mapua.studentmonitoringapp.model.Announcement;
import com.capstone.mapua.studentmonitoringapp.model.AnnouncementDetails;
import com.capstone.mapua.studentmonitoringapp.model.User;
import com.capstone.mapua.studentmonitoringapp.utilities.CustomDialog;
import com.capstone.mapua.studentmonitoringapp.utilities.DateConverter;
import com.capstone.mapua.studentmonitoringapp.utilities.Loader;
import com.capstone.mapua.studentmonitoringapp.utilities.NetworkTest;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jj on 9/5/17.
 */

public class HomeFragment extends Fragment implements AnnouncementCallback, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.tv_display_user)
    TextView tv_display_user;
    @BindView(R.id.tv_announceLabel)
    TextView tv_announceLabel;
    @BindView(R.id.sr_swipe)
    SwipeRefreshLayout sr_swipe;

    @BindView(R.id.rv_announcement)
    RecyclerView rv_announcement;


    Context context;
    AnnouncementImplement implement;
    AnnouncementCallback callback;
    AnnouncementAdapter adapter;
    CustomDialog dialog;
    Loader loader;
    User user;
    ArrayList<Announcement> announcementArrayList = new ArrayList<>();
    DatabaseHandler databaseHandler;

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
        databaseHandler = new DatabaseHandler(context);
        implement = new AnnouncementImplement(context, databaseHandler, callback);
        adapter = new AnnouncementAdapter(context, announcementArrayList);
        rv_announcement.setLayoutManager(new LinearLayoutManager(context));
        rv_announcement.setAdapter(adapter);
        dialog = new CustomDialog();
        sr_swipe.setColorSchemeResources(R.color.colorPrimary);
        sr_swipe.setOnRefreshListener(this);

        user = SharedPref.userData;

        setParentName();
        getCurrentAnnouncement();
        return view;
    }

    private void getCurrentAnnouncement() {
        announcementArrayList.clear();
        announcementArrayList.addAll(databaseHandler.getAnnouncementList());
        adapter.notifyDataSetChanged();

        String label = "";
        if (null == SharedPref.getStringValue(SharedPref.USER, SharedPref.LAST_ANNOUNCEMENT_UPDATE, context))
            SharedPref.setStringValue(SharedPref.USER, SharedPref.LAST_ANNOUNCEMENT_UPDATE, "", context);
        if (SharedPref.getStringValue(SharedPref.USER, SharedPref.LAST_ANNOUNCEMENT_UPDATE, context).isEmpty())
            label = getText(R.string.announcement_) + "\n" + getText(R.string.pullDown);
        else
            label = getText(R.string.announcement_) + "\n" + getText(R.string.pullDown)
                    + "\nUpdated as of: "
                    + SharedPref.getStringValue(SharedPref.USER, SharedPref.LAST_ANNOUNCEMENT_UPDATE, context);

        tv_announceLabel.setText(label);

    }

    private void setParentName() {
        try {
            String name = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_NAME, context);
            tv_display_user.setText("Welcome " + name);
        } catch (Exception e) {
            tv_display_user.setText("Welcome");
        }
    }

    @Override
    public void onSuccess(AnnouncementDetails body) {
        implement.getAnnouncements(body.getAnnouncements(), announcementArrayList);

    }

    @Override
    public void onError(String s) {
        if (sr_swipe != null)
            sr_swipe.setRefreshing(false);
        dialog.showMessage(context, dialog.NO_Internet_title, s, 1);
    }

    @Override
    public void onSaveComplete(ArrayList<Announcement> announcementArrayList) {
        this.announcementArrayList = announcementArrayList;
        adapter.notifyDataSetChanged();
        SharedPref.setStringValue(SharedPref.USER, SharedPref.LAST_ANNOUNCEMENT_UPDATE, DateConverter.getCurrentDate(), context);
        tv_announceLabel.setText(getText(R.string.announcement_) + "\nUpdated as of: "
                + SharedPref.getStringValue(SharedPref.USER, SharedPref.LAST_ANNOUNCEMENT_UPDATE, context));
        if (sr_swipe != null)
            sr_swipe.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (NetworkTest.isOnline(context)) {
                    String userId = SharedPref.getStringValue(SharedPref.USER, SharedPref.USER_ID, context);
                    APICall.getAnnouncements(userId, callback);
                    sr_swipe.setRefreshing(true);
                } else {
                    dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
                    sr_swipe.setRefreshing(false);
                }
            }
        }, 3000);
    }
}
