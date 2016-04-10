package com.abition.self;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abition.self.uielement.PlanListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment {

    private static PlanFragment mInstance;
    private RecyclerView recyclerView;

    public PlanFragment() {

    }

    public static PlanFragment getInstance() {
        if(mInstance == null) {
            mInstance = new PlanFragment();
        }
        return mInstance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_plan, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_plan_list);

        // TODO data here
        List <Plan> tempList = new ArrayList<>();
        tempList.add(new Plan("exercise", R.drawable.autumn, 13));
        tempList.add(new Plan("reading", R.drawable.spring, 7));
        tempList.add(new Plan("code", R.drawable.winter, 999));
        // ~

        recyclerView.setAdapter(new PlanListAdapter(tempList, getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }
}
