package com.capstone.mapua.studentmonitoringapp.utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.capstone.mapua.studentmonitoringapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jj on 11/22/2017.
 */


public class CustomDialog {

    public String HOLD_ON_title = "Hold On";
    public String NO_Internet = "Please check your internet connection.";
    public String NO_Internet_title = "Hold On";
    public String cannot_login = "Cannot Login";
    TextView tv_message, tv_title;
    CircleImageView ci_error_image;
    Button btn_accept;

    public void showMessage(Context context, String title, String message, int errorImage) {

        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertshow);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        ci_error_image = (CircleImageView) dialog.findViewById(R.id.ci_error_image);
        tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        btn_accept = (Button) dialog.findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        setDetails(context, message, title, errorImage, btn_accept);

        dialog.show();

        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = width;
        dialog.getWindow().setAttributes(lp);
    }

    private void setDetails(Context context, String message, String title, int errorImage, Button button) {
        tv_message.setText(message);
        tv_title.setText(title);
        switch (errorImage) {
            case 1:
                //error
                ci_error_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.warning));
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.BLACK));
                break;
            case 2:
                //congrats
                ci_error_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.success));
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            case 3:
                //Log out
                ci_error_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logout));
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.BLACK));
                break;
        }
    }
}
