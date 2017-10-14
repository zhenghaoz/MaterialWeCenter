package com.sine_x.material_wecenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.activity.QuestionActivity;
import com.sine_x.material_wecenter.controller.fragment.UserActonFragment;
import com.sine_x.material_wecenter.models.Action;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户页面问题条目和回答条目适配器
 */

public class ActionViewAdapter extends RecyclerView.Adapter<ActionViewAdapter.ActionViewHolder> {

    private Context context;
    private List<Action> contents;
    private int actions;

    public ActionViewAdapter(Context context, List<Action> contents, int actions) {
        this.context = context;
        this.contents = contents;
        this.actions = actions;
    }

    @Override
    public int getItemCount() {
        return contents == null ? 0 : contents.size();
    }

    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActionViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_action, parent, false));
    }

    @Override
    public void onBindViewHolder(ActionViewHolder holder, final int position) {
        Action action = contents.get(position);
        holder.title.setText(action.getQuestion_info().getQuestion_content());
        StringBuilder builder = new StringBuilder();
        if (actions == UserActonFragment.PUBLISH) {     // 发布问题
            holder.detail.setVisibility(View.GONE);
            builder.append(String.valueOf(action.getQuestion_info().getAnswer_count()))
                    .append("个回答");
        } else {
            // 回答问题
            String message = contents.get(position).getAnswer_info().getAnswer_content();
            message = message.replace("\n", "");
            message = message.replaceAll("\\[.*?\\]", "");
            if (message.length() > Config.MAX_LENGTH) {
                message = message.substring(0, Config.MAX_LENGTH);
                message = message + "...";
            }
            holder.detail.setText(message);
            builder.append(String.valueOf(action.getAnswer_info().getAgree_count()))
                    .append("个赞同 • ")
                    .append(String.valueOf(action.getAnswer_info().getAgainst_count()))
                    .append("个反对");
        }
        holder.info.setText(builder.toString());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.putExtra(Config.INT_QUESTION_ID, contents.get(position).getQuestion_info().getQuestion_id());
                context.startActivity(intent);
            }
        });
    }

    public static class ActionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.info)
        TextView info;
        @BindView(R.id.detail)
        TextView detail;
        @BindView(R.id.card_view)
        CardView card;

        public ActionViewHolder(View item) {
            super(item);
            ButterKnife.bind(this, item);
        }
    }
}