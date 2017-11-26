package com.jambi.macbookpro.smsapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jambi.macbookpro.smsapp.R;
import com.jambi.macbookpro.smsapp.callback.TapCallback;
import com.jambi.macbookpro.smsapp.implement.AttendanceLogImplement;
import com.jambi.macbookpro.smsapp.model.Student;
import com.jambi.macbookpro.smsapp.model.TapDetails;
import com.jambi.macbookpro.smsapp.model.TapLog;
import com.jambi.macbookpro.smsapp.utilities.CustomDialog;
import com.jambi.macbookpro.smsapp.utilities.NetworkTest;
import com.jambi.macbookpro.smsapp.utilities.SharedPref;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by macbookpro on 9/5/17.
 */

public class AttendanceFragment extends Fragment implements TapCallback, View.OnClickListener {

    @BindView(R.id.list_date)
    ListView list_date;
    @BindView(R.id.list_time)
    ListView list_time;
    @BindView(R.id.list_type)
    ListView list_type;
    @BindView(R.id.cv_log)
    CardView cv_log;

    @BindView(R.id.ll_noNet)
    LinearLayout ll_noNet;
    @BindView(R.id.btn_refresh)
    Button btn_refresh;





    Context context;
    AttendanceLogImplement implement;
    TapCallback callback;
    ArrayAdapter<String> adapterListDate;
    ArrayAdapter<String> adapterListTime;
    ArrayAdapter<String> adapterListType;
    ArrayList<String> arrayListDate;
    ArrayList<String> arrayListTime;
    ArrayList<String> arrayListType;
    CustomDialog dialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_logs, container, false);
        ButterKnife.bind(this,view);
        context = getContext();
        callback =this;
        dialog = new CustomDialog();
        implement = new AttendanceLogImplement(context);
        arrayListDate = new ArrayList<>();
        arrayListTime = new ArrayList<>();
        arrayListType = new ArrayList<>();
        btn_refresh.setOnClickListener(this);

        getTapLog();

        return view;
    }

    private void getTapLog() {
        if (NetworkTest.isOnline(context)) {
            String studentId = SharedPref.getStringValue(SharedPref.USER,SharedPref.STUDENT_id,context);
            implement.getTapLogOfStudent(studentId,callback);
        } else {
            ll_noNet.setVisibility(View.VISIBLE);
            cv_log.setVisibility(View.GONE);
            dialog.showMessage(context, dialog.HOLD_ON_title, dialog.NO_Internet, 1);
        }
    }

    @Override
    public void onSuccessTapDetails(TapDetails body) {
        ll_noNet.setVisibility(View.GONE);
        cv_log.setVisibility(View.VISIBLE);


        arrayListDate.clear();
        arrayListTime.clear();
        arrayListType.clear();
        ArrayList<TapLog> tapLogs = body.getTapListDetails();
        for (TapLog tapLog : tapLogs) {
            arrayListDate.add(tapLog.getLogDateTime());
            arrayListTime.add(tapLog.getLogDateTime());
            arrayListType.add(tapLog.getLogType());
        }
//        adapterListDate.notifyDataSetChanged();
//        adapterListTime.notifyDataSetChanged();
//        adapterListType.notifyDataSetChanged();
        adapterListDate = new ArrayAdapter<String>
                (context, R.layout.listview_text, arrayListDate){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                view.setBackground(getContext().getDrawable(R.drawable.list_divider));
                return view;
            }
        };
        list_date.setAdapter(adapterListDate);
        list_date.setEnabled(false);
        setListViewHeightBasedOnItems(list_date);

        adapterListTime = new ArrayAdapter<String>
                (context, R.layout.listview_text, arrayListTime){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                view.setBackground(getContext().getDrawable(R.drawable.list_divider));
                return view;
            }
        };
        list_time.setAdapter(adapterListTime);
        list_time.setEnabled(false);
        setListViewHeightBasedOnItems(list_time);

        adapterListType = new ArrayAdapter<String>
                (context, R.layout.listview_text, arrayListType){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                view.setBackground(getContext().getDrawable(R.drawable.list_divider));
                return view;
            }
        };
        list_type.setAdapter(adapterListType);
        list_type.setEnabled(false);
        setListViewHeightBasedOnItems(list_type);

    }

    @Override
    public void onErrorTapDetails(String s) {
        ll_noNet.setVisibility(View.VISIBLE);
        cv_log.setVisibility(View.GONE);
        dialog.showMessage(context, dialog.NO_Internet_title, s, 1);
    }


    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btn_refresh:
                getTapLog();
                break;
        }
    }
}
