package com.abition.self;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abition.self.uielement.CircleImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by KlousesSun on 16/4/23.
 */
public class NewPlanDialog extends DialogFragment implements View.OnClickListener{

    TextView dateFrom;
    TextView dateTo;
    ImageView okImage;
    LinearLayout typeImage;
    EditText planTitle;
    Integer themeImage = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_plan, null, false);
        dateFrom = (TextView) view.findViewById(R.id.tv_date_from);
        dateTo = (TextView) view.findViewById(R.id.tv_date_to);
        okImage = (ImageView) view.findViewById(R.id.iv_ok);
        typeImage = (LinearLayout) view.findViewById(R.id.ll_type_img);
        planTitle = (EditText)view.findViewById(R.id.title_plan);
        final Calendar calendar = Calendar.getInstance();
        String month, day;
        if (calendar.MONTH > 8) {
            month = String.valueOf(calendar.MONTH + 1);
        } else {
            month = "0" + calendar.MONTH + 1;
        }
        if (calendar.DATE > 9) {
            day = String.valueOf(calendar.DATE);
        } else {
            day = "0" + calendar.DATE;
        }
        dateFrom.setText(calendar.get(Calendar.YEAR) + "-" + month + "-" + day);
        dateTo.setText(calendar.get(Calendar.YEAR) + "-" + month + "-" + day);
        dateFrom.setOnClickListener(this);
        dateTo.setOnClickListener(this);
        okImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date_from = null,date_to = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateFromString = dateFrom.getText().toString();
                String dateToString = dateTo.getText().toString();
                try {
                    date_from = sdf.parse(dateFromString);
                    date_to = sdf.parse(dateToString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Plan newPlan = new Plan(planTitle.getText().toString(),themeImage,date_from,date_to,getActivity());
                dismiss();
            }
        });

        List<Integer> imageList =  new ArrayList<>();
        imageList.add(R.drawable.autumn);
        imageList.add(R.drawable.spring);
        imageList.add(R.drawable.winter);
        imageList.add(R.drawable.love);
        imageList.add(R.drawable.night);


        for (final Integer id : imageList) {
            final CircleImageView circleImageView = new CircleImageView(getActivity());
            circleImageView.setImageResource(id);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 120);
            layoutParams.weight = 1;
            circleImageView.setLayoutParams(layoutParams);
            typeImage.addView(circleImageView);

            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0; i < typeImage.getChildCount(); i++) {
                        CircleImageView tempCircleImageView = (CircleImageView) typeImage.getChildAt(i);
                        tempCircleImageView.setBorderWidth(0);
                    }
                    circleImageView.setBorderWidth(8);
                    circleImageView.setBorderColor(0xFF3CB371);
                    themeImage = id;
                }
            });
        }

        if(imageList.size() > 0) {
            CircleImageView tempCircleImageView = (CircleImageView) typeImage.getChildAt(0);
            tempCircleImageView.setBorderWidth(8);
            tempCircleImageView.setBorderColor(0xFF3CB371);
        }

        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }

    public void setDateText(String dateText, int from) {
        switch (from)
        {
            case R.id.tv_date_from:
                dateFrom.setText(dateText);
                break;
            case R.id.tv_date_to:
                dateTo.setText(dateText);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        switch (v.getId()) {
            case R.id.tv_date_from:
                args.putInt("from", R.id.tv_date_from);
                break;
            case R.id.tv_date_to:
                args.putInt("from", R.id.tv_date_to);
                break;
            default:
                break;
        }
        fragment.setArguments(args);
        fragment.show(((MainActivity) getActivity()).getSupportFragmentManager(), null);
    }
}
