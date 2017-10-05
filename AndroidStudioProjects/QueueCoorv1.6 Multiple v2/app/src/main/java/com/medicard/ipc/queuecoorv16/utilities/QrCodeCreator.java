package com.medicard.ipc.queuecoorv16.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.image.ImageType;

import java.io.ByteArrayOutputStream;

/**
 * Created by mpx-pawpaw on 4/26/17.
 */

public class QrCodeCreator {

    public static Bitmap getBitmapFromString(String qrText) {
        ByteArrayOutputStream out = QRCode.from(qrText).to(ImageType.PNG).withSize(300, 300).stream();

        byte[] data = out.toByteArray();
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length, null);
        return bmp;
    }

}
