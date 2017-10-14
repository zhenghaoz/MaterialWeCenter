package com.sine_x.material_wecenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.activity.TopicActivity;
import com.sine_x.material_wecenter.models.Topic;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicViewAdapter extends RecyclerView.Adapter<TopicViewAdapter.ViewHolder> {

    private List<Topic> mList;
    private Context mContext;

    public TopicViewAdapter(Context context, List<Topic> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Topic topic = mList.get(position);
        if (topic.getTopic_pic() != null)
            Picasso.with(mContext).load(topic.getTopic_pic()).into(holder.topicImg);
        holder.topicTitle.setText(topic.getTopic_title());
        holder.topicDescription.setText(topic.getTopic_description());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TopicActivity.class);
                intent.putExtra(Config.INT_TOPIC_NAME, topic.getTopic_title());
                intent.putExtra(Config.INT_TOPIC_ID, topic.getTopic_id());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_topic, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view) CardView cardView;
        @BindView(R.id.topic_img) ImageView topicImg;
        @BindView(R.id.topic_title) TextView topicTitle;
        @BindView(R.id.topic_description) TextView topicDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
