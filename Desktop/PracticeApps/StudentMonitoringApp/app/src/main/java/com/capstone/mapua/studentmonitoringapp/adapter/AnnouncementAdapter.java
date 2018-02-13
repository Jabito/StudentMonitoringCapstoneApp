package com.capstone.mapua.studentmonitoringapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.capstone.mapua.studentmonitoringapp.R;
import com.capstone.mapua.studentmonitoringapp.model.Announcement;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IPC on 11/26/2017.
 */



public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
    private static final int UNSELECTED = -1;

    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;
    Context context;
    ArrayList<Announcement> announcements;

    public AnnouncementAdapter(RecyclerView recyclerView, Context context, ArrayList<Announcement> announcements) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.announcements = announcements;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.announcement_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {
        @BindView(R.id.expandable_layout)
        ExpandableLayout expandable_layout;

        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.ll_header)
        LinearLayout ll_header;

        @BindView(R.id.ll_title)
        LinearLayout ll_title;
        @BindView(R.id.tv_index)
        TextView tv_index;
        @BindView(R.id.tv_index_read)
        TextView tv_index_read;


        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            expandable_layout.setInterpolator(new OvershootInterpolator());
            expandable_layout.setOnExpansionUpdateListener(this);
            ll_title.setOnClickListener(this);
        }

        public void bind(int position) {
            this.position = position;
//            if(position==0)
//                ll_header.setVisibility(View.GONE);
            tv_index.setText(getSafeSubstring(announcements.get(position).getMessage(),10) + "...");
            ll_title.setSelected(false);
            expandable_layout.collapse(false);
            tv_content.setText(announcements.get(position).getMessage());
        }

        @Override
        public void onClick(View view) {
            ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
            if (holder != null) {
                holder.ll_title.setSelected(false);
                holder.tv_index.setText(getSafeSubstring(announcements.get(position).getMessage(),10) + "...");
                tv_index_read.setText(context.getString(R.string.read_more));
//                holder.tv_index.setTextColor(context.getColor(R.color.BLACK));
                holder.expandable_layout.collapse();
            }
            if (position == selectedItem) {
                selectedItem = UNSELECTED;
            } else {
                ll_title.setSelected(true);
//                tv_index.setTextColor(context.getColor(R.color.white));
                tv_index.setText((position + 2)+":"+ context.getString(R.string.announcement_));
                tv_index_read.setText(context.getString(R.string.read_less));
                expandable_layout.expand();
                selectedItem = position;
            }
        }
        @Override
        public void onExpansionUpdate(float expansionFraction, int state) {
            recyclerView.smoothScrollToPosition(getAdapterPosition());
        }
    }


    public String getSafeSubstring(String s, int maxLength){
        if(!TextUtils.isEmpty(s)){
            if(s.length() >= maxLength){
                return s.substring(0, maxLength);
            }
        }
        return s;
    }
}