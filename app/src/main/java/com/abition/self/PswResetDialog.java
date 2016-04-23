package com.abition.self;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.zip.Inflater;

/**
 * Created by KlousesSun on 16/4/23.
 */
public class PswResetDialog extends DialogFragment {

    ImageView okImage;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_reset_psw, null, false);
        okImage = (ImageView) view.findViewById(R.id.iv_ok);
        okImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }
}
