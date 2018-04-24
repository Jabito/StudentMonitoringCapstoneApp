package com.capstone.mapua.studentmonitoringapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.capstone.mapua.studentmonitoringapp.R;
import com.capstone.mapua.studentmonitoringapp.model.Announcement;
import com.capstone.mapua.studentmonitoringapp.model.TapLog;
import com.capstone.mapua.studentmonitoringapp.utilities.DateConverter;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jj on 11/26/2017.
 */


public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.Holder> {
    ArrayList<Announcement> arrayList = new ArrayList<>();
    Context context;


    public AnnouncementAdapter(Context context, ArrayList<Announcement> announcementArrayList) {
        this.arrayList = announcementArrayList;
        this.context = context;
    }

    @Override
    public AnnouncementAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.announcement_layout, parent, false);
        return new AnnouncementAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(AnnouncementAdapter.Holder holder, int position) {
        holder.tv_header.setText(addEllipsis(arrayList.get(position).getMessage()));
        holder.tv_post.setText(arrayList.get(position).getMessage());
        holder.tv_datePosted.setText((null != arrayList.get(position).getDatePosted()) ?
                DateConverter.setFormatToMonthDayYearAndTime(arrayList.get(position).getDatePosted()) : "null");
        holder.tv_postedBy.setText("post by " + arrayList.get(position).getPostedBy());

        if (position == 0) {
            holder.tb_details.setChecked(true);
            holder.ll_showHide.setVisibility(View.VISIBLE);
        } else {
            holder.tb_details.setChecked(false);
            holder.ll_showHide.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public String addEllipsis(String data) {
        String userName;
        if (data.length() >= 26) {
            userName = data.substring(0, 26) + "...";
        } else {
            userName = data;
        }
        return userName;
    }

    public class Holder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        @BindView(R.id.tv_header)
        TextView tv_header;
        @BindView(R.id.tv_datePosted)
        TextView tv_datePosted;
        @BindView(R.id.tv_post)
        TextView tv_post;
        @BindView(R.id.tv_postedBy)
        TextView tv_postedBy;
        @BindView(R.id.tb_details)
        ToggleButton tb_details;
        @BindView(R.id.ll_showHide)
        LinearLayout ll_showHide;
        @BindView(R.id.cv_roomDetails)
        CardView cv_roomDetails;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tb_details.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                ll_showHide.setVisibility(View.VISIBLE);
                tv_header.setText((null != arrayList.get(getAdapterPosition()).getDatePosted()) ?
                        DateConverter.setFormatToMonthDayYearAndTime(arrayList.get(getAdapterPosition()).getDatePosted()) : "null");
            } else {
                ll_showHide.setVisibility(View.GONE);
                tv_header.setText(addEllipsis(arrayList.get(getAdapterPosition()).getMessage()));
            }
        }


    }
}