package com.sine_x.material_wecenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pavlospt.roundedletterview.RoundedLetterView;
import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.activity.ChatActivity;
import com.sine_x.material_wecenter.models.Conversation;
import com.sine_x.material_wecenter.models.Topic;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversationViewAdapter extends RecyclerView.Adapter<ConversationViewAdapter.ViewHolder> {

    private List<Conversation> mList;
    private Context mContext;

    public ConversationViewAdapter(Context context, List<Conversation> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Conversation conversation = mList.get(position);
        holder.conversationImage.setTitleText(conversation.getUser_name().substring(0,1));
        holder.conversationUser.setText(conversation.getUser_name());
        holder.conversationLast.setText(conversation.getLast_message());
        if (conversation.getUnread() > 0) {
            holder.conversationUser.setTypeface(null, Typeface.BOLD);
            holder.conversationLast.setTypeface(null, Typeface.BOLD);
            holder.conversationUser.setTextColor(Color.BLACK);
            holder.conversationLast.setTextColor(Color.BLACK);
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra(Config.INT_CHAT_USERNAME, conversation.getUser_name());
                intent.putExtra(Config.INT_CHAT_ID, conversation.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_conversation, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card) CardView card;
        @BindView(R.id.conversation_img) RoundedLetterView conversationImage;
        @BindView(R.id.conversation_user_name) TextView conversationUser;
        @BindView(R.id.conversation_last_msg) TextView conversationLast;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
