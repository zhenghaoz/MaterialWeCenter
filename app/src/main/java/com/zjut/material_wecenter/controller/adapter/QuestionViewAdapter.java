package com.zjut.material_wecenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.activity.QuestionActivity;
import com.zjut.material_wecenter.controller.activity.UserActivity;
import com.zjut.material_wecenter.models.Question;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionViewAdapter extends RecyclerView.Adapter<QuestionViewAdapter.ViewHolder> {

    private ArrayList<Question> mList;
    private Context mContext;

    public QuestionViewAdapter(Context context, ArrayList<Question> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Question question = mList.get(position);
        // Load avatar
        String avatarFile = question.getUser_info().getAvatar_file();
        if (!avatarFile.isEmpty())
            Picasso.with(mContext)
                    .load(avatarFile)
                    .into(holder.avatarImg);
        holder.questionTitle.setText(question.getQuestion_content());
        holder.questionInfo.setText(String.valueOf(question.getFocus_count()) + "人关注 • "
                + String.valueOf(question.getAnswer_count()) + "个回答 • "
                + String.valueOf(question.getView_count()) + "次浏览");
        holder.questionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, QuestionActivity.class);
                intent.putExtra("questionID", question.getQuestion_id());
                mContext.startActivity(intent);
            }
        });
        holder.avatarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserActivity.class);
                intent.putExtra("uid", String.valueOf(question.getUser_info().getUid()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_question, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView avatarImg;
        private TextView questionTitle;
        private TextView questionInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarImg = (CircleImageView) itemView.findViewById(R.id.avatar_img);
            questionTitle = (TextView) itemView.findViewById(R.id.question_title);
            questionInfo = (TextView) itemView.findViewById(R.id.question_info);
        }
    }
}
