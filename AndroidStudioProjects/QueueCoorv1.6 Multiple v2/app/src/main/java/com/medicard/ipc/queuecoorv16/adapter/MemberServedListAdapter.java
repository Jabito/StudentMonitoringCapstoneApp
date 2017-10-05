package com.medicard.ipc.queuecoorv16.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.medicard.ipc.queuecoorv16.R;
import com.medicard.ipc.queuecoorv16.database.MemberQueue;
import com.medicard.ipc.queuecoorv16.holder.MemberServedListHolder;
import com.medicard.ipc.queuecoorv16.utilities.CreatePdf;
import com.medicard.ipc.queuecoorv16.utilities.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by IPC on 7/28/2017.
 */

public class MemberServedListAdapter extends RecyclerView.Adapter<MemberServedListHolder> {
    Context c;
    ArrayList<MemberQueue> memberQueue;

    public MemberServedListAdapter(Context ctx, ArrayList<MemberQueue> memberQueue)
    {
        //ASSIGN THEM LOCALLY
        this.c=ctx;
        this.memberQueue=memberQueue;
    }
    @Override
    public MemberServedListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //VIEW OBJ FROM XML
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_member_list,null);
        //holder
        MemberServedListHolder holder=new MemberServedListHolder(v);
        return holder;
    }
    //BIND DATA TO VIEWS
    @Override
    public void onBindViewHolder(MemberServedListHolder holder, final int position) {
        holder.txt_queueNum_display.setText(memberQueue.get(position).getDate()+"-"+ String.format("%06d", memberQueue.get(position).getQueue()));
        holder.txt_name_display.setText(memberQueue.get(position).getName());
        holder.txt_mobile_display.setText(memberQueue.get(position).getMobile());
        holder.txt_code_display.setText(memberQueue.get(position).getCode());
        holder.txt_card_display.setText(memberQueue.get(position).getHealthname());
        //HANDLE ITEMCLICKS
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                showDialog(memberQueue.get(position).getName(),memberQueue.get(position).getQueue(),memberQueue.get(position).getMobile(),memberQueue.get(position).getId(),memberQueue.get(position).getDate(),memberQueue.get(position).getHealthname());

            }
        });
    }
    @Override
    public int getItemCount() {
        return memberQueue.size();
    }

    private void showDialog(final String name, final int queue, final String mobile, final int id, final String date,final String card) {

        // custom dialog
        final Dialog dialog = new Dialog(c);
        dialog.setContentView(R.layout.custom_dialog);
        // set the custom dialog components - text, image and button
        TextView tv_queue, tv_name, tv_mobile,tv_card;
        Button bt_nextPatient, bt_viewQueue;

        tv_queue = (TextView) dialog.findViewById(R.id.tv_queue);
        tv_queue.setText(date + "-" + String.format("%06d", queue));
        tv_name = (TextView) dialog.findViewById(R.id.tv_name);
        tv_name.setText(name);
        tv_mobile = (TextView) dialog.findViewById(R.id.tv_mobile);
        tv_mobile.setText(mobile);
        tv_card = (TextView) dialog.findViewById(R.id.tv_card);
        tv_card.setText(card);
        bt_nextPatient = (Button) dialog.findViewById(R.id.bt_nextPatient);
        bt_nextPatient.setVisibility(View.GONE);
        bt_viewQueue = (Button) dialog.findViewById(R.id.bt_viewQueue);
        bt_viewQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        dialog.show();

    }

    //create pdf file
    public void GeneratePDF(int queue, String name, String mobile, String date)
    {
        // TODO Auto-generated method stub
        String filename = date +"-"+ String.format("%06d", queue);
        String pdfqueue = Integer.toString(queue);
        String pdfname = name;
        String pdfmobile = mobile;
        CreatePdf fop = new CreatePdf();
        if (fop.write(filename, pdfqueue,pdfname,pdfmobile)) {
            Toast.makeText(c,
                    filename + ".pdf created", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(c, "I/O error",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
