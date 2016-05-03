package com.abition.self;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by KlousesSun on 16/4/22.
 */
public class DatePickerFragment extends DialogFragment {
    int from;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        from = bundle.getInt("from");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                NewPlanDialog newPlanDialog = ((MainActivity) getActivity()).newPlanDialog;
                String month, day;
                if (monthOfYear > 8) {
                    month = String.valueOf(monthOfYear + 1);
                } else {
                    month = "0" + (monthOfYear + 1);
                }
                if (dayOfMonth > 9) {
                    day = String.valueOf(dayOfMonth);
                } else {
                    day = "0" + dayOfMonth;
                }
                newPlanDialog.setDateText(year + "-" + month + "-" + day, from);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
    }
}
