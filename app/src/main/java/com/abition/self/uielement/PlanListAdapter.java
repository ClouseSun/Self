package com.abition.self.uielement;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abition.self.Plan;
import com.abition.self.R;

import java.util.List;

/**
 * Created by KlousesSun on 16/4/9.
 */
public class PlanListAdapter extends RecyclerView.Adapter {
    public PlanListAdapter(List<Plan> planList, Context context) {
        this.planList = planList;
        this.context = context;
    }

    List<Plan> planList;

    Context context;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plan, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder item = (ItemViewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        public ItemViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}


