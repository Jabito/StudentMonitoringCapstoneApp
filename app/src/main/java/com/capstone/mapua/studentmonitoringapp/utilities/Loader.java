package com.capstone.mapua.studentmonitoringapp.utilities;

import android.app.ProgressDialog;
import android.content.Context;

import com.capstone.mapua.studentmonitoringapp.R;


/**
 * Created by IPC on 11/22/2017.
 */

public class Loader {


    ProgressDialog pd;

    Context context;

    public Loader(Context context) {
        this.context = context;
    }

    public void setMessage(String message) {
        pd.setMessage(message);
    }

    public void setPropertes() {
        pd = new ProgressDialog(context, R.style.MyTheme);
        pd.setCancelable(false);
        pd.setMessage("Logging in...");
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

    }

    public void startLoad(){
        pd.show();
    }

    public void checkLoad(){
        if((pd != null))
            pd.dismiss();
    }

    public void stopLoad() {
        pd.dismiss();
    }


}
