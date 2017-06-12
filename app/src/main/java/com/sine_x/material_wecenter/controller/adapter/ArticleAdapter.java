package com.sine_x.material_wecenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.activity.UserActivity;
import com.sine_x.material_wecenter.models.Ajax;
import com.sine_x.material_wecenter.models.Article;
import com.sine_x.material_wecenter.models.QuestionDetail;
import com.sine_x.material_wecenter.models.Response;
import com.squareup.picasso.Picasso;
import com.zzhoujay.richtext.RichText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_TITLE = 0;
    private final int TYPE_DETAIL = 1;
    private final int TYPE_TOPICS = 2;
    private final int TYPE_ITEM = 3;
    private final int TYPE_FOOTER = 4;
    private final int TYPE_INFO = 5;

    private boolean isThank;
    private Context mContext;
    private Article article;

    public ArticleAdapter(Context context, Article article) {
        this.mContext = context;
        this.article = article;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_TITLE;
            case 1:
                return TYPE_TOPICS;
            case 2:
                return TYPE_DETAIL;
            case 3:
                return TYPE_INFO;
            default:
                if (position == getItemCount()-1)
                    return TYPE_FOOTER;
                return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_TITLE:
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_question_title, parent, false);
                return new TitleViewHolder(view);
            case TYPE_TOPICS:
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_tags, parent, false);
                return new TopicViewHolder(view);
            case TYPE_DETAIL:
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_rich_text, parent, false);
                return new TextViewHolder(view);
            case TYPE_FOOTER:
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_question_footer, parent, false);
                return new FooterViewHolder(view);
            case TYPE_INFO:
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_question_info, parent, false);
                return new InfoViewHolder(view);
            default:
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_answer, parent, false);
                return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof TitleViewHolder) {
            final Article.ArticleInfo articleInfo = article.getArticle_info();
            String avatarFile = articleInfo.getUser_info().getAvatar_file();
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(titleViewHolder.avatar);
            titleViewHolder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserActivity.class);
                    intent.putExtra("uid", articleInfo.getUser_info().getUid());
                    mContext.startActivity(intent);
                }
            });
            Log.d("UID", String.valueOf(articleInfo.getUser_info().getUid()));
            titleViewHolder.title.setText(articleInfo.getTitle());
        } else if (holder instanceof TopicViewHolder) {
            TopicViewHolder topicViewHolder = (TopicViewHolder) holder;
            List<String> topics = new ArrayList<>();
            for (Article.ArticleTopicsBean info : article.getArticle_topics())
                topics.add(info.getTopic_title());
            topicViewHolder.tagGroup.setTags(topics);
        } else if (holder instanceof TextViewHolder) {
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            RichText.from(article.getArticle_info().getMessage())
                    .into(textViewHolder.text);
        } else if (holder instanceof InfoViewHolder) {
            final Article.ArticleInfo questionInfo = article.getArticle_info();
            InfoViewHolder infoViewHolder = (InfoViewHolder) holder;
            infoViewHolder.answerCount.setText(String.valueOf(questionInfo.getComments()));
            infoViewHolder.thankCount.setText(String.valueOf(questionInfo.getVotes()));
            String time = getTime(questionInfo.getAdd_time());
            infoViewHolder.addTime.setGravity(Gravity.RIGHT);
            infoViewHolder.addTime.setText("发布于  " + time);
            infoViewHolder.thank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else if (holder instanceof ItemViewHolder) {

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final Article.CommentsBean commentsBean = article.getComments().get(position - 4);
            String avatarFile = commentsBean.getUser_info().getAvatar_file();
            //Log.e("avatarFile",avatarFile);
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(itemViewHolder.avatar);

            itemViewHolder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserActivity.class);
                    intent.putExtra("uid", commentsBean.getUser_info().getUid());
                    mContext.startActivity(intent);
                }
            });
            String addTime = getTime(commentsBean.getAdd_time());
            itemViewHolder.addTime.setText(addTime);
            itemViewHolder.userName.setText(commentsBean.getUser_info().getUser_name());
            itemViewHolder.agreeCount.setText(String.valueOf(commentsBean.getVotes()));

//            if (commentsBean.getAgree_status() == 1) {
//                itemViewHolder.agree.setImageResource(R.drawable.ic_agree_red);
//            }

//            itemViewHolder.agree.setOnClickListener(new AgreeListener(itemViewHolder.agree
//                    , commentsBean.getAgree_count(), commentsBean.getAgree_status(), itemViewHolder.agreeCount,
//                    commentsBean.getAnswer_id()));

            itemViewHolder.briefDetail.setText(Html.fromHtml(commentsBean.getMessage()));
            itemViewHolder.briefDetail.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        if (article == null)
            return 1;
        return 5 + (article.getComments() == null ? 0 : article.getComments().size());
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.avatar_img_question) CircleImageView avatar;
        @Bind(R.id.textView_title_question) TextView title;

        TitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView avatar;
        private TextView userName;
        private TextView addTime;
        private TextView briefDetail;
        private ImageView agree;
        private TextView agreeCount;

        public ItemViewHolder(View view) {
            super(view);
            avatar = (CircleImageView) view.findViewById(R.id.avatar_img_answer);
            userName = (TextView) view.findViewById(R.id.textView_userName_answer);
            addTime = (TextView) view.findViewById(R.id.textView_addTime_answer);
            briefDetail = (TextView) view.findViewById(R.id.textView_briefDetail_answer);
            agreeCount = (TextView) view.findViewById(R.id.textView_agree_answer);
            agree = (ImageView) view.findViewById(R.id.image_agree_answer);
        }
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.textView_addTime_question) TextView addTime;
        @Bind(R.id.btn_focus_question) Button focus;
        @Bind(R.id.textView_answerCount_question) TextView answerCount;
        @Bind(R.id.textView_thankCount_question) TextView thankCount;
        @Bind(R.id.imageView_thank_question) ImageView thank;

        public InfoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            focus.setVisibility(View.GONE);
        }
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tag_group)
        TagGroup tagGroup;
        public TopicViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.textView_detailText_question) TextView text;

        public TextViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    //获取发布时间

    private String getTime(long dateLong) {


        final long SECOND_TO_LONG = 1000l;
        final long MINUTE_TO_LONG = SECOND_TO_LONG * 60;
        final long HOUR_TO_LONG = MINUTE_TO_LONG * 60;
        final long DAY_TO_LONG = HOUR_TO_LONG * 24;

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        long ct = System.currentTimeMillis();
        long cost = ct - dateLong * 1000l;

        Date ctDate = new Date(ct);
        Date longDate = new Date(dateLong * 1000l);

        if (cost < MINUTE_TO_LONG) {
            return cost / SECOND_TO_LONG + "秒前";
        } else if (cost < HOUR_TO_LONG) {
            return cost / MINUTE_TO_LONG + "分钟前";
        } else if (cost < DAY_TO_LONG) {
            return cost / HOUR_TO_LONG + "小时前";
        } else if (ctDate.getYear() > longDate.getYear()) {
            return sdf2.format(longDate);
        } else return sdf.format(longDate);

    }

    private class AgreeListener implements View.OnClickListener {

        private ImageView agree;
        private TextView count;
        private int status;
        private int num;
        private int ID;

        public AgreeListener(ImageView agree, int num, int status, TextView count, int ID) {
            this.agree = agree;
            this.count = count;
            this.status = status;
            this.ID = ID;
            this.num = num;
        }

        @Override
        public void onClick(View v) {

            new AgreeTask(ID).execute();

            if (status == 0) {
                agree.setImageResource(R.drawable.ic_agree_red);
                count.setText((++num) + "");
                status = 1;
            } else {
                agree.setImageResource(R.drawable.ic_agree_gray);
                count.setText((--num) + "");
                status = 0;
            }
        }
    }

    private class AgreeTask extends AsyncTask<Integer, Integer, Integer> {

        private int answerID;
        Response<Object> result2;

        public AgreeTask(int answerID) {
            this.answerID = answerID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                Client client = Client.getInstance();
                ArrayList<String> strs = new ArrayList<>();
                strs.add(answerID + "");
                strs.add("1");
                result2 = client.postAction(Config.ActionType.ANSWER_VOTE, null, strs);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    private class AcitonListener implements View.OnClickListener {

        private int action;
        private int questionID;
        private int num;
        private View count;
        private View view;

        public AcitonListener(int action, int questionID, int num, View count, View view) {
            this.action = action;
            this.questionID = questionID;
            this.num = num;
            this.count = count;
            this.view = view;
        }

        @Override
        public void onClick(View v) {
            new DoAction(action, questionID, num, count, view).execute();
        }
    }

    private class DoAction extends AsyncTask<Integer, Integer, Integer> {

        private static final int FOCUS = 0;
        private static final int THANKS = 1;
        private int action;
        private int articleID;
        private int num;
        Response<Ajax> result2;
        private View count;
        private View view;

        public DoAction(int action, int articleID, int num, View count, View view) {
            this.action = action;
            this.articleID = articleID;
            this.num = num;
            this.count = count;
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                Client client = Client.getInstance();
                ArrayList<String> strs = new ArrayList<>();
                switch (action) {
                    case FOCUS:
                        result2 = client.focus(articleID);
                        break;
                    case THANKS:
                        result2 = client.thanks(articleID);
                        break;
                    default:
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            switch (action) {
                case FOCUS:
                    Ajax mAjax = (Ajax) result2.getRsm();
                    if (mAjax.getType().equals("add")) {
                        ((Button) view).setText("取消关注");
                        ((Button) view).setBackgroundResource(R.drawable.stroker);
                    } else if (mAjax.getType().equals("remove")) {
                        ((Button) view).setText("关注");
                        ((Button) view).setBackgroundResource(R.drawable.un_follow_shape);
                    }
                    break;
                case THANKS:
                    if (!isThank) {
                        ((ImageView) view).setImageResource(R.drawable.ic_red_heart);
                        ((TextView) count).setText((++num) + "");
                        isThank = true;
                    }

                    break;
                default:
                    break;
            }
            if (result2 == null)
                Toast.makeText(mContext, "未知错误", Toast.LENGTH_SHORT).show();
            if (result2.getErrno() == 1) {
                Toast.makeText(mContext, "操作成功", Toast.LENGTH_SHORT).show();
            } else                // 显示错误
                Toast.makeText(mContext, result2.getErr(), Toast.LENGTH_SHORT).show();
        }
    }
}

