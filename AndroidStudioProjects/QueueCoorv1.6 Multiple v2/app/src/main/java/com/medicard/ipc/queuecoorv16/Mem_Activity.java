package com.medicard.ipc.queuecoorv16;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.medicard.ipc.queuecoorv16.database.DBAdapter;
import com.medicard.ipc.queuecoorv16.database.DBHelper;
import com.medicard.ipc.queuecoorv16.utilities.CreatePdf;
import com.medicard.ipc.queuecoorv16.utilities.CustomToast;
import com.medicard.ipc.queuecoorv16.utilities.Globals;
import com.medicard.ipc.queuecoorv16.utilities.PrinterCommands;
import com.medicard.ipc.queuecoorv16.utilities.SharedPref;
import com.medicard.ipc.queuecoorv16.utilities.Utilss;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Mem_Activity extends AppCompatActivity {

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

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    String txtregister;
    DBAdapter db;
    static String stringUsername,stringPassword;

    private static final String TELEPHON_NUMBER_FIELD_NAME = "address";
    private static final String MESSAGE_BODY_FIELD_NAME = "body";
    private static final Uri SENT_MSGS_CONTET_PROVIDER = Uri.parse("content://sms/ m'/'");

    @BindView(R.id.ll_card_1)
    LinearLayout ll_card_1;
    @BindView(R.id.ll_card_2)
    LinearLayout ll_card_2;
    @BindView(R.id.ll_card_3)
    LinearLayout ll_card_3;
    @BindView(R.id.ll_card_4)
    LinearLayout ll_card_4;
    @BindView(R.id.ll_card_5)
    LinearLayout ll_card_5;
    @BindView(R.id.ll_card_6)
    LinearLayout ll_card_6;
    @BindView(R.id.textClock)
    TextClock textClock;
    @BindView(R.id.txt_Number)
    TextView txt_Number;
    @BindView(R.id.txt_Title)
    TextView txt_Title;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    EditText ed_name,ed_mobNum,ed_memCode,ed_selectedCard;
    Button bt_register,bt_cancel;
    TextInputLayout TextInput_edit,TextInput_healthName;
    TextView tv_selected;
    CustomToast customToast = new CustomToast();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_member);

        context = this;
        ButterKnife.bind(this);

        textClock = (TextClock) findViewById(R.id.textClock);
        txtClock();
        txt_Number = (TextView)findViewById(R.id.txt_Number);
        txt_Number.setText("000000");
        txt_Title = (TextView)findViewById(R.id.txt_Title);
        txt_Title.setText("NOW SERVING");
        SharedPref.setStringValue(SharedPref.MEMBER, SharedPref.CURRENT_QUEUE,getString(R.string.card_2_string),context);
        //experimental auto update of now serving
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                updateQueue();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

    }

    @OnClick({R.id.ll_card_1,R.id.ll_card_2,R.id.ll_card_3,R.id.ll_card_4,R.id.ll_card_5,R.id.ll_card_6})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_card_1:
                SharedPref.setStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,getString(R.string.card_1_string),context);
                showDialog(SharedPref.getStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,context));
                break;
            case R.id.ll_card_2:
                SharedPref.setStringValue(SharedPref.MEMBER, SharedPref.ORIGIN,getString(R.string.card_2_string),context);
                showDialog(SharedPref.getStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,context));
                break;
            case R.id.ll_card_3:
                SharedPref.setStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,getString(R.string.card_3_string),context);
                showDialog(SharedPref.getStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,context));
                break;
            case R.id.ll_card_4:
                SharedPref.setStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,getString(R.string.card_4_string),context);
                showDialog(SharedPref.getStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,context));
                break;
            case R.id.ll_card_5:
                SharedPref.setStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,getString(R.string.card_5_string),context);
                showDialog(SharedPref.getStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,context));
                break;
            case R.id.ll_card_6:
                SharedPref.setStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,getString(R.string.card_6_string),context);
                showDialog(SharedPref.getStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,context));
                break;

        }
    }



    public void showDialog(final String ORIGIN){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_select_card);
        dialog.setCancelable(false);

        TextInput_edit = (TextInputLayout) dialog.findViewById(R.id.TextInput_edit);
        ed_selectedCard = (EditText) dialog.findViewById(R.id.ed_selectedCard);
        ed_name = (EditText) dialog.findViewById(R.id.ed_name);
        ed_mobNum = (EditText) dialog.findViewById(R.id.ed_mobNum);
        ed_memCode = (EditText) dialog.findViewById(R.id.ed_memCode);
        bt_register = (Button) dialog.findViewById(R.id.bt_register);
        bt_cancel = (Button) dialog.findViewById(R.id.bt_cancel);

        ed_selectedCard.setText(ORIGIN);

        if(ORIGIN.equals(context.getString(R.string.card_1_string))){

        }else if(ORIGIN.equals(context.getString(R.string.card_6_string))){
            ed_selectedCard.setText("");
            ed_selectedCard.setEnabled(true);
            TextInput_edit.setVisibility(View.GONE);

        }else {
            TextInput_edit.setVisibility(View.GONE);
        }

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ed_name.getText().toString().trim().equals("admin") && ed_mobNum.getText().toString().trim().equals("12345")){
                    Intent in = new Intent(Mem_Activity.this, Coor_Activity.class);
                    startActivity(in);
                    customToast.customToastMessage("admin login",context);
                }else {
                    //get member input data
                    String pcard = ed_selectedCard.getText().toString();
                    String pname = ed_name.getText().toString();
                    String pmobile = ed_mobNum.getText().toString();
                    String pcode = ed_memCode.getText().toString();

                    String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
                    getMaxId(pname,pmobile,pcode,pcard,date);

                }

                dialog.dismiss();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
    }

    public void getMaxId(String pname, String pmobile, String pcode, String pcard, String date){

        //fetch the highest ID from the database
        int pqueue = 0;
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(queue) FROM memberQueue_TB where date =" + date + " ", null);
        try {
            if (c.moveToFirst()) {
                do {
                    pqueue = c.getInt(0);

                } while (c.moveToNext());
            }
        } finally {
            c.close();
            helper.close();
            db.close();
        }
        //format 000001
        String.format("%06d", pqueue);
        int pidPlus = 1 + pqueue;

        save(String.format("%06d", pidPlus), pname, pmobile, pcode,pcard, date, "queue");
        customMemberRegistry(String.format("%06d", pidPlus), pname, pmobile, date);
        findBT();
        try {
            openBT();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    //custom member register message
    public void customMemberRegistry(final String queue, final String name, final String mobile, final String date){
        final Dialog dialog = new Dialog(Mem_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_member_dialog);
        dialog.setCancelable(false);
        Button bt_print = (Button)dialog.findViewById(R.id.bt_print);
        Button bt_ok = (Button)dialog.findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
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
        bt_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeneratePDF(queue,name,mobile,date);
                try {
                    sendData(queue,name);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

    }


    //create pdf file
    public void GeneratePDF(String queue,String name,String mobile,String date)
    {
        // TODO Auto-generated method stub
        String filename = date +"-"+ queue;
        String pdfqueue = queue;
        String pdfname = name;
        String pdfmobile = mobile;
        CreatePdf fop = new CreatePdf();
        if (fop.write(filename, pdfqueue,pdfname,pdfmobile)) {
            Toast.makeText(Mem_Activity.this,
                    filename + ".pdf created", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(Mem_Activity.this, "I/O error",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //SAVE
    private void save(String queue,String name,String mobile,String code,String card,String date,String status)
    {
        db=new DBAdapter(this);
        //OPEN
        db.openDB();
        //INSERT
        long result = db.add(queue, name, mobile, code,card, date, status);
        if(result>0)
        {

//            phoneNo = mobile;
//            message = "Hi "+name+", You have been waitlisted to" +
//                    " MediCard Coordinator as #"+queue+"." +
//                    " You will receive another text message when the" +
//                    " MediCard Coordinator placed you on next.";
            ed_selectedCard.setText("");
            ed_name.setText("");
            ed_mobNum.setText("");
            ed_memCode.setText("");

//            int hasWriteContactsPermission = 0;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                hasWriteContactsPermission = checkSelfPermission(Manifest.permission.SEND_SMS);
//            }
//            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (!shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
//                        showMessageOKCancel("You need to allow access to send sms",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
//                                                    REQUEST_CODE_ASK_PERMISSIONS);
//                                        }
//                                    }
//                                });
//                        return;
//                    }
//                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},
//                            REQUEST_CODE_ASK_PERMISSIONS);
//                }
//                return;
//            }

            sendSMS(mobile, "Hi "+name+", You have been waitlisted to" +
                    " MediCard Coordinator as #"+queue+"." +
                    " You will receive another text message when the" +
                    " MediCard Coordinator placed you on next.");



        }else
        {
            Snackbar.make(ed_name,"Unable To Insert",Snackbar.LENGTH_SHORT).show();
        }
        //CLOSE
        db.close();
        //refresh

    }



//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_ASK_PERMISSIONS:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission Granted
//                } else {
//                    // Permission Denied
//                    Toast.makeText(Mem_Activity.this, "Send SMS Denied", Toast.LENGTH_SHORT)
//                            .show();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(Mem_Activity.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }

    private void sendSMS(final String phoneNumber, final String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        ContentValues values = new ContentValues();
                            values.put("address", phoneNumber);// txtPhoneNo.getText().toString());
                            values.put("body", message);
                        getContentResolver().insert(
                                Uri.parse("content://sms/sent"), values);
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    public  void updateQueue(){

        int pid = 0;
        DBHelper helper = new DBHelper(Mem_Activity.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT queue FROM memberQueue_TB where status = 'active'", null);
        try {
            if (c.moveToFirst()) {
                do {
                    pid = c.getInt(0);

                } while (c.moveToNext());
            }
        }finally {
            c.close();
        }

        String.format("%06d", pid);
        txt_Number.setText(String.format("%06d", pid));
    }
    //textClock  set Format24Hour "dd/MM/yyyy hh:mm:ss a""
    public  void txtClock(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textClock.setFormat12Hour("MMMM d, yyyy hh:mm:ss a");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textClock.setFormat24Hour("MMMM d, yyyy hh:mm:ss a");
        }
    }


//    //send of sms experimental
//    public void sendSMS(String mobile, String message)
//    {
//        try {
////            Intent intent=new Intent(getApplicationContext(),Mem_Activity.class);
////            PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(mobile, null, message, null, null);
//            Toast.makeText(Mem_Activity.this, "MESSAGE SENT",
//                    Toast.LENGTH_LONG).show();
//        } catch (Exception ex) {
//            Toast.makeText(Mem_Activity.this,ex.getMessage().toString(),
//                    Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
//
//    }
//
//    private void addMessageToSent(String telNumber, String messageBody) {
//        ContentValues sentSms = new ContentValues();
//        sentSms.put(TELEPHON_NUMBER_FIELD_NAME, telNumber);
//        sentSms.put(MESSAGE_BODY_FIELD_NAME, messageBody);
//
//        ContentResolver contentResolver = getContentResolver();
//        contentResolver.insert(SENT_MSGS_CONTET_PROVIDER, sentSms);
//    }
    //printer experimental
    void findBT() {

    try {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "No bluetooth adapter available",
                    Toast.LENGTH_LONG).show();
        }

        if(!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
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
        Toast.makeText(getApplicationContext(), "Bluetooth device found.",
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

            Toast.makeText(getApplicationContext(), "Bluetooth Opened",
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
                                                Toast.makeText(getApplicationContext(), data,
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
    void sendData(String queue,String name) throws IOException {
        try {
            String ORIGIN = SharedPref.getStringValue(SharedPref.MEMBER,SharedPref.ORIGIN,context);

            String Medmsg = "\n"+ORIGIN+" Philippines\n";
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
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
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
