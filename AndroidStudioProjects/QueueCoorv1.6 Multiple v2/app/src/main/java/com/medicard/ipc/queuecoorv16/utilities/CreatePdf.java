package com.medicard.ipc.queuecoorv16.utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by IPC on 7/28/2017.
 */

public class CreatePdf {
    Context context;
    private Image bgImage;
    public Boolean write(String filename, String pdfqueue, String pdfname, String pdfmobile) {


        try {
            //Environment.getDataDirectory().getAbsolutePath()

            String fpath = "/sdcard/" + filename + ".pdf";
            File file = new File(fpath);
            Log.e("PDFCretor","PDF Path " + file);
            if (!file.exists()) {
                file.createNewFile();
            }

            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);

            Document document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));

            Rectangle one = new Rectangle(144,144);
            document.setPageSize(one);
            document.setMargins(1, 1, 1, 1);
            document.open();

//            Drawable d = context.getResources().getDrawable(R.drawable.icon);
//            Drawable d = ContextCompat.getDrawable(context,R.drawable.icon);
//            Drawable d = ResourcesCompat.getDrawable(get, R.drawable.icon, null);
//            BitmapDrawable bitDw = ((BitmapDrawable) d);
//            Bitmap bmp = bitDw.getBitmap();
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image image = Image.getInstance(stream.toByteArray());
//            document.add(image);


            Paragraph para1 = new Paragraph("#"+pdfqueue);
            para1.setAlignment(Element.ALIGN_CENTER);
            document.add(para1);

            Paragraph para2 = new Paragraph("NAME");
            para2.setAlignment(Element.ALIGN_CENTER);
            document.add(para2);

            Paragraph para3 = new Paragraph(pdfname);
            para3.setAlignment(Element.ALIGN_CENTER);
            document.add(para3);

//            Paragraph para4 = new Paragraph("MOBILE:");
//            para4.setAlignment(Element.ALIGN_CENTER);
//            document.add(para4);
//
//            Paragraph para5 = new Paragraph(pdfmobile);
//            para5.setAlignment(Element.ALIGN_CENTER);
//            document.add(para5);

//            document.add(new Paragraph("QUEUE:"));
//            document.add(new Paragraph(pdfqueue));
//
//            document.add(new Paragraph("NAME:"));
//            document.add(new Paragraph(pdfname));
//
//            document.add(new Paragraph("MOBILE:"));
//            document.add(new Paragraph(pdfmobile));


            document.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }




}
