package com.sine_x.material_wecenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.activity.QuestionActivity;
import com.sine_x.material_wecenter.controller.activity.UserActivity;
import com.sine_x.material_wecenter.models.Dynamic;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by windness on 2016/1/27.
 */
public class DynamicViewAdapter extends RecyclerView.Adapter<DynamicViewAdapter.ViewHolder> {

    /**
     * associate_action 列表
     * 参考链接：
     * http://wecenter.api.hihwei.com/doku.php?id=other:associate_action_%E5%88%97%E8%A1%A8
     */
    private static final int ADD_QUESTION=101;//添加问题
    private static final int MOD_QUESTON_TITLE=102;//修改问题标题
    private static final int MOD_QUESTION_DESCRI=103;//修改问题描述
    private static final int ADD_REQUESTION_FOCUS=105;//添加问题关注
    private static final int REDIRECT_QUESTION=107;//问题重定向
    private static final int MOD_QUESTION_CATEGORY=108;//修改问题分类
    private static final int MOD_QUESTION_ATTACH=109;//修改问题附件
    private static final int DEL_REDIRECT_QUESTION=110;//删除问题重定向

    private static final int ANSWER_QUESTION=201;//回复问题
    private static final int ADD_AGREE=204;//赞同答案
    private static final int ADD_USEFUL=206;//感谢作者
    private static final int ADD_UNUSEFUL=207;//问题没有帮助

    private static final int ADD_TOPIC=401;//创建话题
    private static final int MOD_TOPIC=402;//修改话题
    private static final int MOD_TOPIC_DESCRI=403;//修改话题描述
    private static final int MOD_TOPIC_PIC=404;//修改话题图片
    private static final int DELETE_TOPIC=405;//删除话题
    private static final int ADD_TOPIC_FOCUS=406;//添加话题关注
    private static final int ADD_RELATED_TOPIC=410;//添加相关话题
    private static final int DELETE_RELATED_TOPIC=411;//删除相关话题

    private static final int ADD_ARTICLE=501;//添加文章
    private static final int ADD_AGREE_ARTICLE=502;//赞同文章
    private static final int ADD_COMMENT_ARTICLE=503;//评论文章

    private boolean questionOnly(int n) {return n >= ADD_QUESTION && n <= DEL_REDIRECT_QUESTION;}
    private boolean questionAndAnswer(int n) {return n >= ANSWER_QUESTION && n <= ADD_UNUSEFUL;}
    private boolean topic(int n) {return n >= ADD_TOPIC && n <= DELETE_RELATED_TOPIC;}
    private boolean articleOnly(int n) {return n >= ADD_ARTICLE && n <= ADD_AGREE;}
    private boolean articleAndComment(int n) {return n == ADD_COMMENT_ARTICLE;}

    private static SparseArray<String> action;
    static {
        action = new SparseArray<String>() {
            {
                put(ADD_QUESTION, "发起了问题");
                put(MOD_QUESTON_TITLE, "修改了问题标题");
                put(MOD_QUESTION_DESCRI, "修改了问题描述");
                put(ADD_REQUESTION_FOCUS, "关注了该问题");
                put(REDIRECT_QUESTION, "设置了问题重定向");
                put(MOD_QUESTION_CATEGORY, "修改了问题分类");
                put(MOD_QUESTION_ATTACH, "修改了问题附件");
                put(DEL_REDIRECT_QUESTION, "删除了问题重定向");

                put(ANSWER_QUESTION, "回答了问题");
                put(ADD_AGREE, "赞同了回答");
                put(ADD_USEFUL, "感谢了作者");
                put(ADD_UNUSEFUL, "认为问题没有帮助");

                put(ADD_TOPIC, "创建了话题");
                put(MOD_TOPIC, "修改了话题");
                put(MOD_TOPIC_DESCRI, "修改了话题描述");
                put(MOD_TOPIC_PIC, "修改了话题图片");
                put(DELETE_TOPIC, "删除了话题");
                put(ADD_TOPIC_FOCUS, "添加了话题关注");
                put(ADD_RELATED_TOPIC, "添加了相关话题");
                put(DELETE_RELATED_TOPIC, "删除了相关话题");

                put(ADD_ARTICLE, "添加了文章");
                put(ADD_AGREE_ARTICLE, "赞同了文章");
                put(ADD_COMMENT_ARTICLE, "评论了文章");
            }
        };
    }


    private List<Dynamic> mList;
    private Context mContext;

    public DynamicViewAdapter(Context context, List<Dynamic> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Dynamic dynamic = mList.get(position);

        String avatarFile = dynamic.getUser_info().getAvatar_file();
        if (avatarFile != null && !avatarFile.isEmpty())
            Picasso.with(mContext)
                    .load(avatarFile)
                    .into(holder.avatarImg);
        final int associateActionType = dynamic.getAssociate_action();
        holder.dynamicUserInfo.setText(dynamic.getUser_info().getUser_name() + " " + action.get(associateActionType));

        if (questionOnly(associateActionType)) setQuestionView(holder, dynamic);
        else if (questionAndAnswer(associateActionType)) setQuestionAndAnswerView(holder, dynamic);
        else if (topic(associateActionType)) setTopicView(holder, dynamic);
        else if (articleOnly(associateActionType)) setArticleView(holder, dynamic);
        else if (articleAndComment(associateActionType)) setArticleAndCommentView(holder, dynamic);
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    //101 - 110
    private void setQuestionView(ViewHolder holder, final Dynamic dynamic) {
        holder.dynamicContent.setVisibility(View.GONE);
        holder.dynamicTitle.setText(dynamic.getQuestion_info().getQuestion_content());
        holder.dynamicInfo.setText(dynamic.getQuestion_info().getAgree_count() + "次赞同 • "
                + dynamic.getQuestion_info().getAnswer_count() + "次回答");
        holder.dynamicTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QuestionActivity.class);
                intent.putExtra("questionID", dynamic.getQuestion_info().getQuestion_id());
                mContext.startActivity(intent);
            }
        });
        holder.avatarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserActivity.class);
                intent.putExtra("uid", String.valueOf(dynamic.getUser_info().getUid()));
                mContext.startActivity(intent);
            }
        });
    }

    //201 - 207
    private void setQuestionAndAnswerView(ViewHolder holder, final Dynamic dynamic) {
        holder.dynamicTitle.setText(dynamic.getQuestion_info().getQuestion_content());
        holder.dynamicContent.setText(dynamic.getAnswer_info().getAnswer_content());
        holder.dynamicInfo.setText(dynamic.getAnswer_info().getAgree_count() + "次赞同 • "
                + dynamic.getAnswer_info().getAgainst_count() + "次反对");
        holder.dynamicTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QuestionActivity.class);
                intent.putExtra("questionID", dynamic.getQuestion_info().getQuestion_id());
                mContext.startActivity(intent);
            }
        });
        holder.avatarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserActivity.class);
                intent.putExtra("uid", String.valueOf(dynamic.getUser_info().getUid()));
                mContext.startActivity(intent);
            }
        });
    }

    private void setTopicView(ViewHolder holder, Dynamic dynamic) {
        // TODO: 2016/1/30 话题view的填充。。。
        setQuestionView(holder, dynamic);
    }

    //501 - 502
    private void setArticleView(ViewHolder holder, Dynamic dynamic) {
        holder.dynamicTitle.setText(dynamic.getArticle_info().getTitle());
        holder.dynamicContent.setText(dynamic.getArticle_info().getMessage());
        holder.dynamicInfo.setText(dynamic.getArticle_info().getViews() + "次浏览 • "
                + dynamic.getArticle_info().getComments() + "次回复");
    }

    //503
    private void setArticleAndCommentView(ViewHolder holder, Dynamic dynamic) {
        holder.dynamicTitle.setText("文章： " + dynamic.getArticle_info().getTitle());
        holder.dynamicContent.setText(dynamic.getComment_info().getMessage());
        holder.dynamicInfo.setText(dynamic.getComment_info().getVotes() + "个赞");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView avatarImg;
        private TextView dynamicUserInfo;
        private TextView dynamicTitle;
        private TextView dynamicContent;
        private TextView dynamicInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarImg = (CircleImageView) itemView.findViewById(R.id.avatar_img);
            dynamicUserInfo = (TextView) itemView.findViewById(R.id.dynamic_user_info);
            dynamicTitle = (TextView) itemView.findViewById(R.id.dynamic_title);
            dynamicContent = (TextView) itemView.findViewById(R.id.dynamic_content);
            dynamicInfo = (TextView) itemView.findViewById(R.id.dynamic_info);
        }
    }
}
