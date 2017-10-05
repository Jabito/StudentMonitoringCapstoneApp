package com.medicard.ipc.queuecoorv16.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.medicard.ipc.queuecoorv16.Coor_Activity;
import com.medicard.ipc.queuecoorv16.Mem_Activity;
import com.medicard.ipc.queuecoorv16.database.DBHelper;
import com.medicard.ipc.queuecoorv16.utilities.Globals;
import com.medicard.ipc.queuecoorv16.utilities.PrinterCommands;
import com.medicard.ipc.queuecoorv16.R;
import com.medicard.ipc.queuecoorv16.database.DBAdapter;
import com.medicard.ipc.queuecoorv16.database.MemberQueue;
import com.medicard.ipc.queuecoorv16.holder.MemberQueueListHolder;
import com.medicard.ipc.queuecoorv16.utilities.CreatePdf;
import com.medicard.ipc.queuecoorv16.utilities.ItemClickListener;
import com.medicard.ipc.queuecoorv16.utilities.QrCodeCreator;
import com.medicard.ipc.queuecoorv16.utilities.SharedPref;
import com.medicard.ipc.queuecoorv16.utilities.Utilss;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by me on 7/27/2017.
 */

public class MemberQueueListAdapter extends RecyclerView.Adapter<MemberQueueListHolder> {

    Context context;
    ArrayList<MemberQueue> memberQueue;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    static OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    int WIDTH = 200;

    Activity activity;

    MemberQueueListAdapter(Activity activity){
        this.activity=activity;
    }


    public MemberQueueListAdapter(Context ctx, ArrayList<MemberQueue> memberQueue)
    {
        //ASSIGN THEM LOCALLY
        this.context=ctx;
        this.memberQueue=memberQueue;
    }
    @Override
    public MemberQueueListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //VIEW OBJ FROM XML
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_member_list,null);
        //holder
        MemberQueueListHolder holder=new MemberQueueListHolder(v);
        return holder;
    }
    //BIND DATA TO VIEWS
    @Override
    public void onBindViewHolder(MemberQueueListHolder holder, final int position) {
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
                findBT();
                try {
                    openBT();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return memberQueue.size();
    }

    private void showDialog(final String name, final int queue, final String mobile, final int id, final String date,final String card) {

        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);
        // set the custom dialog components - text, image and button
        TextView tv_queue, tv_name, tv_mobile,tv_card;
        Button bt_nextPatient, bt_viewQueue,bt_close;

        tv_queue = (TextView) dialog.findViewById(R.id.tv_queue);
        tv_queue.setText(date + "-" + String.format("%06d", queue));
        tv_name = (TextView) dialog.findViewById(R.id.tv_name);
        tv_name.setText(name);
        tv_mobile = (TextView) dialog.findViewById(R.id.tv_mobile);
        tv_mobile.setText(mobile);
        tv_card = (TextView) dialog.findViewById(R.id.tv_card);
        tv_card.setText(card);
        bt_nextPatient = (Button) dialog.findViewById(R.id.bt_nextPatient);
        bt_viewQueue = (Button) dialog.findViewById(R.id.bt_viewQueue);
        bt_close = (Button) dialog.findViewById(R.id.bt_close);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                try {
                    closeBT();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        bt_nextPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update(id, "active");
                SharedPref.setStringValue(SharedPref.MEMBER, SharedPref.CURRENT_QUEUE,Integer.toString(id),context);
                dialog.dismiss();
            }
        });
        bt_viewQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeneratePDF(queue,name,mobile,date);
                try {
                    sendData(String.format("%06d", queue),name,card);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        dialog.show();

    }

    //SAVE
    private void Update(int id, String status)
    {
        DBAdapter db=new DBAdapter(context);
        //OPEN
        db.openDB();
        //INSERT
        long result = db.UPDATE(id, status);
        if(result>0){
//                            Toast.makeText(c, "UPDATE COMPLETE",
//                        Toast.LENGTH_LONG).show();
        }else{
                            Toast.makeText(context, "UPDATE INCOMPLETE",
                        Toast.LENGTH_LONG).show();
        }
        //CLOSE
        db.close();
        //refresh

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
            Toast.makeText(context,
                    filename + ".pdf created", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(context, "I/O error",
                    Toast.LENGTH_SHORT).show();
        }
    }



    //custom toast experimental
    public void customToast(String text){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_toast);
        ImageView myImage=(ImageView)dialog.findViewById(R.id.iv_checked);
        myImage.setImageResource(R.drawable.ic_checked);
        TextView myMessage=(TextView)dialog.findViewById(R.id.tv_toast);
        myMessage.setText(text);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },5000L);

        dialog.show();
    }


    //reminder for the second in line
    public  void remindQueue(){
        String pmobile = "";
        String pname = "";
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT mobile,name FROM memberQueue_TB where status = 'queue'", null);
        try {
            if (c.moveToFirst()) {
                do {
                    pmobile = c.getString(0);
                    pname = c.getString(1);

                } while (c.moveToNext());
            }
        }finally {
            c.close();
            helper.close();
            db.close();
        }
        Globals sharedData = Globals.getInstance();
        sendSMS(pmobile, "Hi "+pname+" Your are now second in line." +
                " Please be ready while the current member is being served." +
                " Thank you for using your MediCard." +
                " Have a good day!",sharedData.getTxtReminder());

    }
    //send of sms experimental
    public void sendSMS(String phoneNumber, String message, String enabler){
        Toast.makeText(context, enabler,
                Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(enabler == "allow"){
                Toast.makeText(context, "got it reminder",
                        Toast.LENGTH_LONG).show();
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Toast.makeText(context, "MESSAGE SENT",
                            Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(context,ex.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }else if (enabler == "disallow"){
                Toast.makeText(context, "got it then reminder",
                        Toast.LENGTH_LONG).show();
            }
        }

    }


    //printer experimental
    void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                Toast.makeText(context, "No bluetooth adapter available",
                        Toast.LENGTH_LONG).show();
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals("HP-M200E_9044")) {
                        mmDevice = device;
                        break;
                    }
                }
            }
            Toast.makeText(context, "Bluetooth device found.",
                    Toast.LENGTH_LONG).show();


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //open a connection to the bluetooth printer device
    void openBT() throws IOException {
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            Toast.makeText(context, "Bluetooth Opened",
                    Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                Toast.makeText(context, data,
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this will send text data to be printed by the bluetooth printer
    void sendData(String queue,String name, String card) throws IOException {
        try {



            String Medmsg = "\n"+card+" Philippines\n";
            String msg    = "Your Queue Number is:\n" +
                    "#"+queue+ "\n\n\n\n";
//            msg += "\n";


            mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printPhoto(R.drawable.icon);
            mmOutputStream.write(Medmsg.getBytes());
            mmOutputStream.write(msg.getBytes());

//            byte[] command = Utilss.decodeBitmap(QrCodeCreator.getBitmapFromString(name));
//            mmOutputStream.write(command);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //print photo
    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
                    img);
            if(bmp!=null){
                byte[] command = Utilss.decodeBitmap(bmp);
                mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                mmOutputStream.write(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    // close the connection to bluetooth printer.
    void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
