package com.capstone.mapua.studentmonitoringapp.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.capstone.mapua.studentmonitoringapp.R;
import com.capstone.mapua.studentmonitoringapp.callback.UserImageCallback;
import com.capstone.mapua.studentmonitoringapp.implement.StudentInfoImplement;
import com.capstone.mapua.studentmonitoringapp.model.Student;
import com.capstone.mapua.studentmonitoringapp.model.UserImageDetails;
import com.capstone.mapua.studentmonitoringapp.utilities.CustomDialog;
import com.capstone.mapua.studentmonitoringapp.utilities.ImageConvert;
import com.capstone.mapua.studentmonitoringapp.utilities.NetworkTest;
import com.capstone.mapua.studentmonitoringapp.utilities.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jj on 11/23/2017.
 */

public class StudentInfoFragment extends Fragment implements UserImageCallback {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_section)
    TextView tv_section;

    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_emergeny_contact)
    TextView tv_emergeny_contact;
    @BindView(R.id.tv_last_update)
    TextView tv_last_update;
    @BindView(R.id.iv_userImage)
    CircleImageView iv_userImage;
    //    ImageView iv_userImage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Student student;
    Context context;

    String studentName = "";
    String studentId = "";
    String studentSection = "";

    String studentSectionText = "";

    int studentGrade;
    String studentGradeText = "";


    String studentContact = "";
    String studentEmerContact = "";
    String studentLastUpdateOn = "";

    StudentInfoImplement implement;
    UserImageCallback callback;
    CustomDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_info, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        implement = new StudentInfoImplement(context);
        callback = this;
        dialog = new CustomDialog();


        studentName = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_lastName, context) + ", " + SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_firstName, context) + " " + SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_middleName, context);
        studentId = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_id, context);
        studentSection = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_section, context);
        studentGrade = SharedPref.getIntegerValue(SharedPref.USER, SharedPref.STUDENT_gradeLvlId, context);
        studentContact = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_contactNo, context);
        studentEmerContact = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_emergencyContact, context);
        studentLastUpdateOn = SharedPref.getStringValue(SharedPref.USER, SharedPref.STUDENT_updatedOn, context);


        /*
            Hi sorry for this switch case
            btw wala na ako oras ayusin to
            <~ wag ka magalala mababasa mo tong code wag kang hunghang
            <~ para to sa section :)
         */
        switch (studentSection) {
            case "1":
                studentSectionText = "Gem";
                break;
            case "2":
                studentSectionText = "Gold";
                break;
            case "3":
                studentSectionText = "Pearl";
                break;
            case "4":
                studentSectionText = "Earth";
                break;
            case "5":
                studentSectionText = "Jupiter";
                break;
            case "6":
                studentSectionText = "Faith";
                break;
            case "7":
                studentSectionText = "Loyalty";
                break;
            case "8":
                studentSectionText = "Integrity";
                break;
            case "9":
                studentSectionText = "Humility";
                break;
            case "10":
                studentSectionText = "Patience";
                break;
            case "11":
                studentSectionText = "Wisdom";
                break;
            case "12":
                studentSectionText = "St.John";
                break;
            case "13":
                studentSectionText = "St.Mark";
                break;
            case "14":
                studentSectionText = "St.Peter";
                break;
            case "15":
                studentSectionText = "St.Joseph";
                break;
            case "16":
                studentSectionText = "St.Francis";
                break;
            case "17":
                studentSectionText = "St.Gabriel";
                break;
            case "18":
                studentSectionText = "St.Matthew";
                break;
            case "19":
                studentSectionText = "St.James";
                break;
            case "20":
                studentSectionText = "St.Lorenzo";
                break;
            case "21":
                studentSectionText = "St.Michael";
                break;
            case "22":
                studentSectionText = "St.Paul";
                break;
            case "23":
                studentSectionText = "St.Anne";
                break;
            case "24":
                studentSectionText = "St.Ignatius";
                break;
        }

        switch (studentGrade) {
            case 0:
                studentGradeText = "Nursery";
                break;
            case 1:
                studentGradeText = "Kinder 1";
                break;
            case 2:
                studentGradeText = "Kinder 2";
                break;
            case 3:
                studentGradeText = "Grade 1";
                break;
            case 4:
                studentGradeText = "Grade 2";
                break;
            case 5:
                studentGradeText = "Grade 3";
                break;
            case 6:
                studentGradeText = "Grade 4";
                break;
            case 7:
                studentGradeText = "Grade 5";
                break;
            case 8:
                studentGradeText = "Grade 6";
                break;
            case 9:
                studentGradeText = "Grade 7";
                break;
            case 10:
                studentGradeText = "Grade 8";
                break;
            case 11:
                studentGradeText = "Grade 9";
                break;
            case 12:
                studentGradeText = "Grade 10";
                break;
            case 13:
                studentGradeText = "Grade 11";
                break;
            case 14:
                studentGradeText = "Grade 12";
                break;
        }

//        tv_last_update.setText(student.getUpdatedOn());
        tv_last_update.setText("Last Update : " +studentLastUpdateOn);
        tv_name.setText(studentName);
        tv_desc.setText(studentId);
        tv_section.setText(studentSectionText);
        tv_grade.setText(studentGradeText);
        tv_contact.setText(studentContact);
        tv_emergeny_contact.setText(studentEmerContact);


        if (NetworkTest.isOnline(context)) {
            progressBar.setVisibility(View.VISIBLE);
            implement.getUserImage(studentId, callback);
        } else {
            progressBar.setVisibility(View.GONE);
            dialog.showMessage(context, dialog.NO_Internet_title, dialog.NO_Internet, 1);
        }

        return view;
    }


    @Override
    public void onSuccess(UserImageDetails body) {

        progressBar.setVisibility(View.GONE);
        ImageConvert.setBase64ToImageView(body.getImageBase64(),iv_userImage);

    }

    @Override
    public void onError(String message) {
        progressBar.setVisibility(View.GONE);
        dialog.showMessage(context, dialog.NO_Internet_title, message, 1);
    }
}