package com.medicard.ipc.queuecoorv16.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.medicard.ipc.queuecoorv16.R;
import com.medicard.ipc.queuecoorv16.utilities.ItemClickListener;


/**
 * Created by IPC on 7/28/2017.
 */



public class MemberServedListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_queueNum_display,txt_name_display,txt_mobile_display,txt_code_display,txt_card_display;
    ItemClickListener itemClickListener;
    public MemberServedListHolder(View itemView) {
        super(itemView);
        //ASSIGN
        txt_queueNum_display = (TextView) itemView.findViewById(R.id.tv_queue);
        txt_name_display = (TextView) itemView.findViewById(R.id.tv_name);
        txt_mobile_display = (TextView) itemView.findViewById(R.id.tv_mobile);
        txt_code_display = (TextView) itemView.findViewById(R.id.tv_code);
        txt_card_display = (TextView) itemView.findViewById(R.id.tv_card);
        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }
    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }
}
