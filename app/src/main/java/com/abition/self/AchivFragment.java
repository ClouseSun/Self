package com.abition.self;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AchivFragment extends Fragment {

    private static AchivFragment mInstance;
    private RecyclerView recyclerView;

    public AchivFragment() {
        // Required empty public constructor
    }

    public static AchivFragment getInstance(){
        if(mInstance == null){
            mInstance = new AchivFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_achiv, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_achiv_list);

        List<Achievement> tempList = new ArrayList<>();
        tempList.add(new Achievement(R.drawable.trophy, "Amateur", "Create 1st plan", false));
        tempList.add(new Achievement(R.drawable.trophy, "Brilliant", "Finsh 1st plan", true));
        tempList.add(new Achievement(R.drawable.trophy, "Combo", "Streak for a week", true));
        tempList.add(new Achievement(R.drawable.trophy, "Decided", "Streak for over 10 days in one plan", false));
        tempList.add(new Achievement(R.drawable.trophy, "Enrichment", "Create 5 plans", false));
        tempList.add(new Achievement(R.drawable.trophy, "Fabulous", "Finish 5 plans", false));

        recyclerView.setAdapter(new AchivListAdapter(tempList, getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

}
