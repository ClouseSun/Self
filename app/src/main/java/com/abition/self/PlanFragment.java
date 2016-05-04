package com.abition.self;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.os.Handler;


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
        //get plan data
        refresh();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    public void refresh(){
        final List <Plan> planList = new ArrayList<>();
        Plan.getUserPlan(getActivity(),planList);
    }

    public void refreshList(List <Plan> planList){
        Collections.sort(planList);
        final PlanListAdapter adapter = new PlanListAdapter(planList, getActivity());
        recyclerView.setAdapter(adapter);
    }
}