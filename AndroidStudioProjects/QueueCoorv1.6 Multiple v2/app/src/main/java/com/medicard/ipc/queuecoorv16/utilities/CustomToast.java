package com.medicard.ipc.queuecoorv16.utilities;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by IPC on 9/11/2017.
 */

public class CustomToast {

    public void customToastMessage(String message, Context context){

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);

        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(20);
        toast.show();

    }


}
