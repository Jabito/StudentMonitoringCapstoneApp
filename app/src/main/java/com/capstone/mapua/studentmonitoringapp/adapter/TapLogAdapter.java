package com.capstone.mapua.studentmonitoringapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.capstone.mapua.studentmonitoringapp.R;
import com.capstone.mapua.studentmonitoringapp.model.TapLog;
import com.capstone.mapua.studentmonitoringapp.utilities.DateConverter;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by me on 2/16/2018.
 */

public class TapLogAdapter extends RecyclerView.Adapter<TapLogAdapter.Holder> {
    ArrayList<TapLog> arrayList = new ArrayList<>();
    Context context;

    String label = "";
    public TapLogAdapter(Context context, ArrayList<TapLog> arrayList ) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_tap_log, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        if(label != DateConverter.setToMonth(arrayList.get(position).getLogDateTime())){
            holder.tv_label.setVisibility(View.VISIBLE);
            label = DateConverter.setToMonth(arrayList.get(position).getLogDateTime());
        }else
            holder.tv_label.setVisibility(View.GONE);

        holder.tv_label.setText(DateConverter.setToMonth(arrayList.get(position).getLogDateTime()));

        holder.tv_date.setText(DateConverter.setToDate(arrayList.get(position).getLogDateTime()));
        holder.tv_time.setText(DateConverter.setToTime(arrayList.get(position).getLogDateTime()));
        holder.tv_type.setText(arrayList.get(position).getLogType());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_label)
        TextView tv_label;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_type)
        TextView tv_type;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}