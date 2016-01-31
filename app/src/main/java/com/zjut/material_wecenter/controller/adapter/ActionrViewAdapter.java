package com.zjut.material_wecenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.activity.QuestionActivity;
import com.zjut.material_wecenter.controller.fragment.RecyclerViewFragment;
import com.zjut.material_wecenter.models.Action;

import java.util.List;

public class ActionrViewAdapter extends RecyclerView.Adapter<ActionrViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Action> contents;
    private int actions;

    public ActionrViewAdapter(Context context, List<Action> contents, int actions) {
        this.mContext = context;
        this.contents = contents;
        this.actions = actions;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public ActionrViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (actions == RecyclerViewFragment.PUBLISH)
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_card_small, parent, false);
        else
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_card_big, parent, false);
        return new ActionrViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ActionrViewAdapter.ViewHolder holder, final int position) {
        holder.title.setText(contents.get(position).getQuestion_info().getQuestion_content());
        String info;
        if (actions == RecyclerViewFragment.PUBLISH) {
            info = String.valueOf(contents.get(position).getQuestion_info().getAnswer_count())
                    + "个回答";
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, QuestionActivity.class);
                    intent.putExtra("questionID", contents.get(position).getQuestion_info().getQuestion_id());
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.answer.setText(Html.fromHtml(Html.fromHtml(contents.get(position).getAnswer_info().getAnswer_content()).toString()));
            info = String.valueOf(contents.get(position).getAnswer_info().getAgree_count()) + "个赞同 • "
                    + String.valueOf(contents.get(position).getAnswer_info().getAgainst_count()) + "个反对";
            holder.answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, QuestionActivity.class);
                    intent.putExtra("questionID", contents.get(position).getQuestion_info().getQuestion_id());
                    mContext.startActivity(intent);
                }
            });
        }
        holder.info.setText(info);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, answer, info;
        public ViewHolder(View item) {
            super(item);
            title = (TextView) item.findViewById(R.id.title);
            info = (TextView) item.findViewById(R.id.info);
            if (actions == RecyclerViewFragment.ANSWER)
                answer = (TextView) item.findViewById(R.id.answer);
        }
    }
}