package com.abition.self;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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

}
