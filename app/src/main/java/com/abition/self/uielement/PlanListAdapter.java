package com.abition.self.uielement;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
        if (planList.get(position).getSteak() >= 100)
            item.steakText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 56);
        else
            item.steakText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 72);
        item.steakText.setText(planList.get(position).getSteak() + "");
        item.steakText.setTextColor(Plan.themeStyle.get(planList.get(position).getThemeImage()));
        item.themeText.setText(planList.get(position).getTitle());
        item.themeText.setTextColor(Plan.themeStyle.get(planList.get(position).getThemeImage()));
        item.imageView.setImageResource(planList.get(position).getThemeImage());
        item.checkBtn.setSupportBackgroundTintList(
                ColorStateList.valueOf(Plan.themeStyle.get(planList.get(position).getThemeImage())));
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder
    {
        View view;

        TextView steakText;
        TextView themeText;
        ImageView imageView;
        AppCompatButton checkBtn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            steakText = (TextView) view.findViewById(R.id.tv_plan_steak);
            themeText = (TextView) view.findViewById(R.id.tv_plan_name);
            imageView = (ImageView) view.findViewById(R.id.cv_plan_icon);
            checkBtn = (AppCompatButton) view.findViewById(R.id.btn_check);
        }
    }
}


