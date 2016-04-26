package com.abition.self;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.util.zip.Inflater;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

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
        final EditText currentPswText = (EditText) view.findViewById(R.id.text_current_password);
        final EditText newPswText = (EditText) view.findViewById(R.id.text_new_password);
        final EditText verifyPswText = (EditText) view.findViewById(R.id.text_verify_password);
        okImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentPsw = currentPswText.getText().toString();
                String newPsw = newPswText.getText().toString();
                String verifyPsw = verifyPswText.getText().toString();
                if(!isPasswordValid(newPsw)){
                    newPswText.setError("This password is invalid");
                    newPswText.requestFocus();
                }
                else if (!newPsw.equals(verifyPsw)) {
                    verifyPswText.setError("Verify password does't equal with new password");
                    verifyPswText.requestFocus();
                } else {
                    BmobUser user = BmobUser.getCurrentUser(getActivity());
                    String currentPswMD = null;
                    String newPswMD = null;
                    try {
                        MessageDigest digest = java.security.MessageDigest
                                .getInstance("SHA-1");
                        digest.update(currentPsw.getBytes());
                        byte messageDigest[] = digest.digest();
                        StringBuffer hexString = new StringBuffer();
                        for (int i = 0; i < messageDigest.length; i++) {
                            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                            if (shaHex.length() < 2) {
                                hexString.append(0);
                            }
                            hexString.append(shaHex);
                            currentPswMD=hexString.toString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        MessageDigest digest = java.security.MessageDigest
                                .getInstance("SHA-1");
                        digest.update(newPsw.getBytes());
                        byte messageDigest[] = digest.digest();
                        StringBuffer hexString = new StringBuffer();
                        for (int i = 0; i < messageDigest.length; i++) {
                            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                            if (shaHex.length() < 2) {
                                hexString.append(0);
                            }
                            hexString.append(shaHex);
                            newPswMD=hexString.toString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    user.updateCurrentUserPassword(getActivity(), currentPswMD, newPswMD, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            dismiss();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            currentPswText.setError(s);
                            currentPswText.requestFocus();
                        }
                    });
                }
            }
        });
        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
