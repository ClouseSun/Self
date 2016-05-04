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

import com.abition.self.uielement.CornerLabel;

import java.util.List;

/**
 * Created by KlousesSun on 16/5/1.
 */
public class AchivListAdapter extends RecyclerView.Adapter {
    
    List<Achievement> achivList;
    Context context;

    public AchivListAdapter(List<Achievement> achivList, Context context) {
        this.achivList = achivList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return achivList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_achievement, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder item = (ItemViewHolder) holder;
            
            item.title.setText(achivList.get(position).getTitle() + "");
            item.description.setText(achivList.get(position).getDescription() + "");
            
            if(achivList.get(position).isAccomplished() == true) {
                item.imageView.setImageResource(achivList.get(position).getImage());
                item.edgeView.setBackgroundColor(0xFF2AC833);
            }
            else {
                item.imageView.setImageResource(R.drawable.love);
                item.edgeView.setBackgroundColor(0xFF455A64);
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View view;

        ImageView imageView;
        TextView title;
        TextView description;
        View edgeView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = (ImageView) view.findViewById(R.id.cv_achiv_icon);
            title = (TextView) view.findViewById(R.id.tv_achiv_name);
            description = (TextView) view.findViewById(R.id.tv_achiv_desc);
            edgeView = view.findViewById(R.id.ll_achiv_edge);
        }
    }
}
