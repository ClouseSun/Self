package com.abition.self;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by KlousesSun on 16/4/23.
 */
public class NewPlanDialog extends DialogFragment implements View.OnClickListener{

    TextView dateFrom;
    TextView dateTo;
    ImageView okImage;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_plan, null, false);
        dateFrom = (TextView) view.findViewById(R.id.tv_date_from);
        dateTo = (TextView) view.findViewById(R.id.tv_date_to);
        okImage = (ImageView) view.findViewById(R.id.iv_ok);
        final Calendar calendar = Calendar.getInstance();
        dateFrom.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(calendar.DATE));
        dateTo.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(calendar.DATE));
        dateFrom.setOnClickListener(this);
        dateTo.setOnClickListener(this);
        okImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
