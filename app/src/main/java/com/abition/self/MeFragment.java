package com.abition.self;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bmob.v3.BmobUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    private static MeFragment mInstance;

    CardView cardView;

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment getInstance(){
        if(mInstance == null){
            mInstance = new MeFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_me, container, false);
        cardView = (CardView) rootView.findViewById(R.id.cv_reset_psw);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).pswResetDialog.show((getActivity()).getSupportFragmentManager(), null);

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView resetPasswordView = (TextView) getView().findViewById(R.id.view_reset_password);
        TextView signOutView = (TextView) getView().findViewById(R.id.view_sign_out);
        signOutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser user = BmobUser.getCurrentUser(getActivity());
                user.logOut(getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
    }

}
