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

    public static AchivFragment getInstance() {
        if (mInstance == null) {
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

        List<Plan> planList = new ArrayList<>();
        Plan.getUserPlan(getActivity(), planList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    public void refreshList(List<Plan> planList) {
        int planNum = 0, longest = 0, finishNum = 0;
        for (Plan plan : planList) {
            planNum++;
            if (plan.getStatus() == Plan.Status.FINISHED) {
                finishNum++;
            }
            if (plan.getSteak() > longest) {
                longest = plan.getSteak();
            }
        }
        List<Achievement> tempList = new ArrayList<>();
        tempList.add(new Achievement(R.drawable.trophy, "Amateur", "Create 1st plan", planNum >= 1));
        tempList.add(new Achievement(R.drawable.trophy, "Brilliant", "Finish 1st plan", finishNum >= 1));
        tempList.add(new Achievement(R.drawable.trophy, "Combo", "Streak for a week", longest >= 7));
        tempList.add(new Achievement(R.drawable.trophy, "Decided", "Streak for over 10 days in one plan", longest >= 10));
        tempList.add(new Achievement(R.drawable.trophy, "Enrichment", "Create 5 plans", planNum >= 5));
        tempList.add(new Achievement(R.drawable.trophy, "Fabulous", "Finish 5 plans", finishNum >= 5));
        recyclerView.setAdapter(new AchivListAdapter(tempList, getActivity()));
    }

}
