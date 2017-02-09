package com.sine_x.material_wecenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.sine_x.material_wecenter.controller.activity.AnswerActivity;
import com.sine_x.material_wecenter.controller.activity.UserActivity;
import com.sine_x.material_wecenter.models.Ajax;
import com.sine_x.material_wecenter.models.QuestionDetail;
import com.sine_x.material_wecenter.models.Response;
import com.squareup.picasso.Picasso;
import com.zzhoujay.richtext.RichText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_TITLE = 0;
    private final int TYPE_DETAIL = 1;
    private final int TYPE_ITEM = 3;
    private final int TYPE_FOOTER = 4;
    private final int TYPE_INFO = 5;

    private boolean isThank;
    private Context mContext;
    private QuestionDetail questionDetail;

    public QuestionDetailAdapter(Context context, QuestionDetail questionDetail) {
        this.mContext = context;
        this.questionDetail = questionDetail;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_TITLE;
            case 1:
                return TYPE_DETAIL;
            case 2:
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
            final QuestionDetail.QuestionInfo questionInfo = questionDetail.getQuestion_info();
            String avatarFile = questionInfo.getUser_info().getAvatar_file();
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(titleViewHolder.avatar);
            titleViewHolder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserActivity.class);
                    intent.putExtra("uid", questionInfo.getUser_info().getUid());
                    mContext.startActivity(intent);
                }
            });
            titleViewHolder.title.setText(questionInfo.getQuestion_content());
        } else if (holder instanceof TextViewHolder) {
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            RichText.from(questionDetail.getQuestion_info().getQuestion_detail())
                    .into(textViewHolder.text);
        } else if (holder instanceof InfoViewHolder) {
            final QuestionDetail.QuestionInfo questionInfo = questionDetail.getQuestion_info();
            InfoViewHolder infoViewHolder = (InfoViewHolder) holder;
            if (questionInfo.getUser_question_focus() == 1) {
                infoViewHolder.focus.setText("取消关注");
            } else {
                infoViewHolder.focus.setText("关 注");
            }
            infoViewHolder.focus.setOnClickListener(new AcitonListener(DoAction.FOCUS,
                    questionInfo.getQuestion_id(), 0, null, infoViewHolder.focus));
            if (questionInfo.getUser_thanks() == 1) isThank = true;
            if (questionInfo.getUser_thanks() == 1) {
                infoViewHolder.thank.setImageResource(R.drawable.ic_red_heart);
            }
            infoViewHolder.thank.setOnClickListener(new AcitonListener(DoAction.THANKS,
                    questionInfo.getQuestion_id(), questionInfo.getThanks_count(),
                    infoViewHolder.thankCount, infoViewHolder.thank));
            infoViewHolder.answerCount.setText(String.valueOf(questionInfo.getAnswer_count()));
            infoViewHolder.thankCount.setText(String.valueOf(questionInfo.getThanks_count()));
            String time = getTime(questionInfo.getAdd_time());
            infoViewHolder.addTime.setGravity(Gravity.RIGHT);
            infoViewHolder.addTime.setText("发布于  " + time);
        } else if (holder instanceof ItemViewHolder) {

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final QuestionDetail.AnswerInfo answerInfo = questionDetail.getAnswers().get(position - 3);
            String avatarFile = answerInfo.getUser_info().getAvatar_file();
            //Log.e("avatarFile",avatarFile);
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(itemViewHolder.avatar);

            itemViewHolder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserActivity.class);
                    intent.putExtra("uid", answerInfo.getUser_info().getUid());
                    mContext.startActivity(intent);
                }
            });
            String addTime = getTime(answerInfo.getAdd_time());
            itemViewHolder.addTime.setText(addTime);
            itemViewHolder.userName.setText(answerInfo.getUser_info().getUser_name());
            itemViewHolder.agreeCount.setText(answerInfo.getAgree_count() + "");

            if (answerInfo.getAgree_status() == 1) {
                itemViewHolder.agree.setImageResource(R.drawable.ic_agree_red);
            }

            itemViewHolder.agree.setOnClickListener(new AgreeListener(itemViewHolder.agree
                    , answerInfo.getAgree_count(), answerInfo.getAgree_status(), itemViewHolder.agreeCount,
                    answerInfo.getAnswer_id()));

            itemViewHolder.briefDetail.setText(Html.fromHtml(answerInfo.getAnswer_content()));
            itemViewHolder.briefDetail.setVisibility(View.VISIBLE);
            itemViewHolder.briefDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AnswerActivity.class);
                    intent.putExtra("answerID", answerInfo.getAnswer_id());
                    mContext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (questionDetail == null)
            return 1;
        return 4 + (questionDetail.getAnswers() == null ? 0 : questionDetail.getAnswers().size());
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
        private int questionID;
        private int num;
        Response<Ajax> result2;
        private View count;
        private View view;

        public DoAction(int action, int questionID, int num, View count, View view) {
            this.action = action;
            this.questionID = questionID;
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
                        result2 = client.focus(questionID);
                        break;
                    case THANKS:
                        result2 = client.thanks(questionID);
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

