package com.capstone.mapua.studentmonitoringapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.capstone.mapua.studentmonitoringapp.callback.UserImageCallback;
import com.capstone.mapua.studentmonitoringapp.fragments.AttendanceFragment;
import com.capstone.mapua.studentmonitoringapp.fragments.HomeFragment;
import com.capstone.mapua.studentmonitoringapp.fragments.ParentInfoFragment;
import com.capstone.mapua.studentmonitoringapp.fragments.SettingsFragment;
import com.capstone.mapua.studentmonitoringapp.fragments.StudentInfoFragment;
import com.capstone.mapua.studentmonitoringapp.implement.StudentInfoImplement;
import com.capstone.mapua.studentmonitoringapp.model.Parent;
import com.capstone.mapua.studentmonitoringapp.model.User;
import com.capstone.mapua.studentmonitoringapp.model.UserImageDetails;
import com.capstone.mapua.studentmonitoringapp.services.AppInterface;
import com.capstone.mapua.studentmonitoringapp.utilities.CustomDialog;
import com.capstone.mapua.studentmonitoringapp.utilities.ImageConvert;
import com.capstone.mapua.studentmonitoringapp.utilities.NetworkTest;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener, UserImageCallback {


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.btn_nav)
    ImageButton btn_nav;
    @BindView(R.id.tv_parent_info)
    LinearLayout tv_parent_info;
    @BindView(R.id.nav_home)
    LinearLayout nav_home;
    @BindView(R.id.nav_attendance_logs)
    LinearLayout nav_attendance_logs;
    @BindView(R.id.nav_student)
    LinearLayout nav_student;
    @BindView(R.id.nav_settings)
    LinearLayout nav_settings;
    @BindView(R.id.nav_logout)
    LinearLayout nav_logout;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_desc)
    TextView tv_desc;

    @BindView(R.id.tv_header)
    TextView tv_header;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container_body)
    FrameLayout container_body;
    @BindView(R.id.iv_userImage)
    CircleImageView iv_userImage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    Context context;

    User user;
    Parent parent;

    StudentInfoImplement implement;
    UserImageCallback callback;
    CustomDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);
        implement = new StudentInfoImplement(context);
        callback = this;
        dialog = new CustomDialog();

        user = SharedPref.userData;
        parent = SharedPref.parentData;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);


        String parentName = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_LNAME, context) + ", " + SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_FNAME, context);
        String parentOccupation = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_OCUPATION, context);
        String parentId = SharedPref.getStringValue(SharedPref.USER, SharedPref.PARENT_ID, context);

        tv_name.setText(parentName);
        tv_desc.setText(parentOccupation);

        tv_parent_info.setOnClickListener(this);
        btn_nav.setOnClickListener(this);
        nav_home.setOnClickListener(this);
        nav_attendance_logs.setOnClickListener(this);
        nav_settings.setOnClickListener(this);
        nav_logout.setOnClickListener(this);
        nav_student.setOnClickListener(this);

        tv_header.setText("Home");
        fragment = null;
        fragment = new HomeFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();


        //to subscribe to a topic
        FirebaseMessaging.getInstance().subscribeToTopic("StudentMonitoring");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("Refreshed token: ", refreshedToken);

        if (NetworkTest.isOnline(context)) {
            progressBar.setVisibility(View.VISIBLE);
            implement.getUserImage(parentId, callback);
        } else {
            progressBar.setVisibility(View.GONE);
            dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
        }

    }

    @Override
    public void onBackPressed() {
        tv_header.setText("My Account");
        if (fragment.equals(HomeFragment.class)) {
            if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
                drawer_layout.closeDrawer(Gravity.LEFT);
            }
        } else {
            if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
                drawer_layout.closeDrawer(Gravity.LEFT);
            }
            fragment = null;
            fragment = new HomeFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_parent_info:
                tv_header.setText(context.getString(R.string.parent_info));
                fragment = null;
                fragment = new ParentInfoFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                closeDrawer();
                break;

            case R.id.nav_home:
                tv_header.setText(context.getString(R.string.home));
                fragment = null;
                fragment = new HomeFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                closeDrawer();
                break;
            case R.id.nav_attendance_logs:
                tv_header.setText(context.getString(R.string.attendanceLog));
                fragment = null;
                fragment = new AttendanceFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                closeDrawer();
                break;
            case R.id.nav_student:
                tv_header.setText(context.getString(R.string.student));
                fragment = null;
                fragment = new StudentInfoFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                closeDrawer();
                break;

            case R.id.nav_settings:
                tv_header.setText(context.getString(R.string.action_settings));
                fragment = null;
                fragment = new SettingsFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                closeDrawer();
                break;
            case R.id.nav_logout:
                new AlertDialog.Builder(context)
                        .setTitle("Hold On")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPref.setBooleanValue(SharedPref.USER, SharedPref.SESSION_ON, false, context);
                                SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_ID, "", context);
                                SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_PARENT_OF, "", context);
                                SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_RELATIONSHIP, "", context);
                                SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_LNAME, "", context);
                                SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_FNAME, "", context);
                                SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_CONTACT, "", context);
                                SharedPref.setStringValue(SharedPref.USER, SharedPref.PARENT_OCUPATION, "", context);
                                startActivity(new Intent(NavigationActivity.this, LoginActivity.class));
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

                closeDrawer();


                break;
            case R.id.btn_nav:
                closeDrawer();
                break;
        }
    }

    private void closeDrawer() {

        if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout.closeDrawer(Gravity.LEFT);
        } else {
            drawer_layout.openDrawer(Gravity.LEFT);
        }
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
