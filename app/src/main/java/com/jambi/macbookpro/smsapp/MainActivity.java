package com.jambi.macbookpro.smsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jambi.macbookpro.smsapp.LoginActivity;
import com.jambi.macbookpro.smsapp.R;
import com.jambi.macbookpro.smsapp.fragments.AttendanceFragment;
import com.jambi.macbookpro.smsapp.fragments.HomeFragment;
import com.jambi.macbookpro.smsapp.fragments.MessagesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.btn_nav)
    ImageButton btn_nav;


    @BindView(R.id.nav_home)
    TextView nav_home;
    @BindView(R.id.nav_attendance_logs)
    TextView nav_attendance_logs;
    @BindView(R.id.nav_messages)
    TextView nav_messages;
    @BindView(R.id.nav_settings)
    TextView nav_settings;
    @BindView(R.id.nav_logout)
    TextView nav_logout;

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

    Fragment fragment;
    FragmentTransaction fragmentTransaction;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        tv_name.setText("DELACRUZ, JUAN P.");
        tv_desc.setText("BSIT(2012) 4th Year");


        btn_nav.setOnClickListener(this);
        nav_home.setOnClickListener(this);
        nav_attendance_logs.setOnClickListener(this);
        nav_messages.setOnClickListener(this);
        nav_settings.setOnClickListener(this);
        nav_logout.setOnClickListener(this);


        tv_header.setText("Home");
        fragment = null;
        fragment = new HomeFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
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
            case R.id.nav_messages:
                tv_header.setText(context.getString(R.string.message_));
                fragment = null;
                fragment = new MessagesFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                closeDrawer();
                break;
            case R.id.nav_settings:
                tv_header.setText(context.getString(R.string.action_settings));
                closeDrawer();
                break;
            case R.id.nav_logout:
                Intent goToLogin = new Intent(this, LoginActivity.class);
                startActivity(goToLogin);
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
}
