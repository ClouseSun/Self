package com.abition.self;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
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

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetServerTimeListener;

/**
 * Created by KlousesSun on 16/4/23.
 */
public class NewPlanDialog extends DialogFragment implements View.OnClickListener {

    TextView dateFrom;
    TextView dateTo;
    ImageView okImage;
    LinearLayout typeImage;
    EditText planTitle;
    Integer themeImage = R.drawable.autumn;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_plan, null, false);
        dateFrom = (TextView) view.findViewById(R.id.tv_date_from);
        dateTo = (TextView) view.findViewById(R.id.tv_date_to);
        okImage = (ImageView) view.findViewById(R.id.iv_ok);
        typeImage = (LinearLayout) view.findViewById(R.id.ll_type_img);
        planTitle = (EditText) view.findViewById(R.id.title_plan);
        Bmob.getServerTime(getActivity(), new GetServerTimeListener() {
            @Override
            public void onSuccess(long l) {
                Date now = new Date(l * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String nowTime = sdf.format(now);
                dateFrom.setText(nowTime);
                dateTo.setText(nowTime);
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("@@@@@@", "can't get time");
            }
        });

        dateFrom.setOnClickListener(this);
        dateTo.setOnClickListener(this);
        okImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (planTitle.getText().length() < 1) {
                    planTitle.setError(Html.fromHtml("<font color=#FFFFFF>Title shouldn't be empty</font>"));
                    return;
                }
                Date date_from = null, date_to = null, date_persist = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateFromString = dateFrom.getText().toString();
                String dateToString = dateTo.getText().toString();
                try {
                    date_from = sdf.parse(dateFromString);
                    date_to = sdf.parse(dateToString);
                    if (date_from.getTime() > date_to.getTime()) {
                        planTitle.setError(Html.fromHtml("<font color=#FFFFFF>End time should bigger than start time</font>"));
                        return;
                    }
                    Calendar calendar = Calendar.getInstance();  //得到日历
                    calendar.setTime(date_from);//把当前时间赋给日历
                    calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
                    date_persist = calendar.getTime();   //得到前一天的时间
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Plan newPlan = new Plan(planTitle.getText().toString(), themeImage, date_from, date_to, date_persist, getActivity());
                dismiss();
                PlanFragment.getInstance().refresh();
            }
        });

        List<Integer> imageList = new ArrayList<>();
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
                    for (int i = 0; i < typeImage.getChildCount(); i++) {
                        CircleImageView tempCircleImageView = (CircleImageView) typeImage.getChildAt(i);
                        tempCircleImageView.setBorderWidth(0);
                    }
                    circleImageView.setBorderWidth(8);
                    circleImageView.setBorderColor(0xFF3CB371);
                    themeImage = id;
                }
            });
        }

        if (imageList.size() > 0) {
            CircleImageView tempCircleImageView = (CircleImageView) typeImage.getChildAt(0);
            tempCircleImageView.setBorderWidth(8);
            tempCircleImageView.setBorderColor(0xFF3CB371);
        }

        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }

    public void setDateText(String dateText, int from) {
        switch (from) {
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
