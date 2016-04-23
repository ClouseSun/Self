package com.abition.self;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_plan, parent, false);

            return new ItemViewHolder(view);
        } else if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_add_plan, parent, false);
            return new AddViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
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
            item.edgeView.setBackgroundColor(Plan.themeStyle.get(planList.get(position).getThemeImage()));

            item.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(context)
                            .setItems(new String[]{"Delete"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    planList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, planList.size());
                                }
                            }).create().show();
                    return false;
                }
            });
        }
        else if (holder instanceof AddViewHolder) {
            AddViewHolder item = (AddViewHolder) holder;
            item.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).newPlanDialog.show(((MainActivity)context).getSupportFragmentManager(), null);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return planList.size() + 1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View view;

        TextView steakText;
        TextView themeText;
        ImageView imageView;
        AppCompatButton checkBtn;
        CardView cardView;
        View edgeView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            steakText = (TextView) view.findViewById(R.id.tv_plan_steak);
            themeText = (TextView) view.findViewById(R.id.tv_plan_name);
            imageView = (ImageView) view.findViewById(R.id.cv_plan_icon);
            checkBtn = (AppCompatButton) view.findViewById(R.id.btn_check);
            cardView = (CardView) view.findViewById(R.id.cv_plan);
            edgeView = view.findViewById(R.id.ll_plan_edge);
        }
    }

    class AddViewHolder extends RecyclerView.ViewHolder
    {
        View view;

        ImageView addImage;
        CardView cardView;

        public AddViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cardView = (CardView) view.findViewById(R.id.cv_plan_add);
        }
    }
}


