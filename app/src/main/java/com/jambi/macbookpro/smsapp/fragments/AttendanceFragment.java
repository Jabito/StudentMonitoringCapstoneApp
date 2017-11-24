package com.jambi.macbookpro.smsapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jambi.macbookpro.smsapp.R;
import com.jambi.macbookpro.smsapp.callback.TapCallback;
import com.jambi.macbookpro.smsapp.implement.AttendanceLogImplement;
import com.jambi.macbookpro.smsapp.model.Student;
import com.jambi.macbookpro.smsapp.model.TapDetails;
import com.jambi.macbookpro.smsapp.model.TapLog;
import com.jambi.macbookpro.smsapp.utilities.SharedPref;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by macbookpro on 9/5/17.
 */

public class AttendanceFragment extends Fragment implements TapCallback {

    @BindView(R.id.list_date)
    ListView list_date;
    @BindView(R.id.list_time)
    ListView list_time;
    @BindView(R.id.list_type)
    ListView list_type;


    Context context;
    AttendanceLogImplement implement;
    TapCallback callback;
    ArrayAdapter<String> adapterListDate;
    ArrayAdapter<String> adapterListTime;
    ArrayAdapter<String> adapterListType;
    ArrayList<String> arrayListDate;
    ArrayList<String> arrayListTime;
    ArrayList<String> arrayListType;

    Student student;
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
        implement = new AttendanceLogImplement(context);
        student = SharedPref.studentData;
        arrayListDate = new ArrayList<>();
        arrayListTime = new ArrayList<>();
        arrayListType = new ArrayList<>();



        implement.getTapLogOfStudent(student.getId(),callback);


//        adapterListDate = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayListDate);
//        list_date.setAdapter(adapterListDate);
//        list_date.setEnabled(false);
//        setListViewHeightBasedOnItems(list_date);
//
//        adapterListTime = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayListTime);
//        list_time.setAdapter(adapterListTime);
//        list_time.setEnabled(false);
//        setListViewHeightBasedOnItems(list_time);
//
//        adapterListType = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayListType);
//        list_type.setAdapter(adapterListType);
//        list_type.setEnabled(false);
//        setListViewHeightBasedOnItems(list_type);
        return view;
    }

    @Override
    public void onSuccessTapDetails(TapDetails body) {

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
}
