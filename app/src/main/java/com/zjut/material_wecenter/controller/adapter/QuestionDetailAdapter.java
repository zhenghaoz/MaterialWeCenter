package com.zjut.material_wecenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.models.QuestionDetail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/1/27.
 */
public class QuestionDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private Context mContext;
    private QuestionDetail questionDetail;

    public QuestionDetailAdapter(Context context, QuestionDetail questionDetail){

        this.mContext = context;
        this.questionDetail=questionDetail;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return TYPE_HEADER;
        else if(position==getItemCount()-1) return TYPE_FOOTER;
        else return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==TYPE_HEADER){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_question_header, parent, false);
            return new HeaderViewHolder(view);
        }
        else if(viewType==TYPE_FOOTER){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_question_footer, parent, false);
            return new FooterViewHolder(view);
        }
        else {
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_answer, parent, false);
            return new ItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof HeaderViewHolder){
            final QuestionDetail.QuestionInfo questionInfo=questionDetail.getQuestion_info();
            HeaderViewHolder headerViewHolder=(HeaderViewHolder) holder;
            String avatarFile=questionInfo.getUser_info().getAvatar_file();
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(headerViewHolder.avatar);

            String addTime=getTime(questionInfo.getAdd_time());
            headerViewHolder.addTime.setText(addTime);
            headerViewHolder.userName.setText(questionInfo.getUser_info().getUser_name());
            headerViewHolder.title.setText(questionInfo.getQuestion_content());
            headerViewHolder.detail.setText(questionInfo.getQuestion_detail());
            headerViewHolder.viewCount.setText(questionInfo.getView_count() + "");
            headerViewHolder.answerCount.setText(questionInfo.getAnswer_count() + "");

            if(questionInfo.getUser_follow_check()==1){
                headerViewHolder.follow.setText("取消关注");
            }
            else{
                headerViewHolder.follow.setText("关注");
            }

            headerViewHolder.follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(questionInfo.getUser_follow_check()==0){
                        ((TextView) v).setText("取消关注");
                    }
                    else{
                        ((TextView) v).setText("关注");
                    }

                }
            });



        }
        else if(holder instanceof ItemViewHolder){

            ItemViewHolder itemViewHolder=(ItemViewHolder) holder;
            QuestionDetail.AnswerInfo answerInfo=questionDetail.getAnswers().get(position-1);
            String avatarFile =answerInfo.getUser_info().getAvatar_file();
            //Log.e("avatarFile",avatarFile);
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(itemViewHolder.avatar);

            String addTime=getTime(answerInfo.getAdd_time());
            itemViewHolder.addTime.setText(addTime);
            itemViewHolder.userName.setText(answerInfo.getUser_info().getUser_name());
            itemViewHolder.briefDetail.setText(answerInfo.getAnswer_content());

        }
        else return;
    }

    @Override
    public int getItemCount() {
        return questionDetail.getQuestion_info().getAnswer_count()+2;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView avatar;
        private TextView userName;
        private TextView addTime;
        private TextView title;
        private TextView detail;
        private TextView viewCount;
        private TextView answerCount;
        private TextView follow;

        public HeaderViewHolder(View view) {
            super(view);
            avatar=(CircleImageView) view.findViewById(R.id.avatar_img_question);
            userName=(TextView) view.findViewById(R.id.textView_userName_question);
            addTime=(TextView) view.findViewById(R.id.textView_addTime_question);
            title=(TextView) view.findViewById(R.id.textView_title_question);
            detail=(TextView) view.findViewById(R.id.textView_detail_question);
            viewCount=(TextView) view.findViewById(R.id.textView_viewCount_question);
            answerCount=(TextView) view.findViewById(R.id.textView_answerCount_question);
            follow=(Button) view.findViewById(R.id.button_follow_question);
        }

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView avatar;
        private TextView userName;
        private TextView addTime;
        private TextView briefDetail;


        public ItemViewHolder(View view) {
            super(view);
            avatar=(CircleImageView) view.findViewById(R.id.avatar_img_answer);
            userName=(TextView) view.findViewById(R.id.textView_userName_answer);
            addTime=(TextView) view.findViewById(R.id.textView_addTime_answer);
            briefDetail=(TextView) view.findViewById(R.id.textView_briefDetail_answer);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View view) {
            super(view);
        }
    }


    private String getTime(long dateLong){


        final long SECOND_TO_LONG=1000l;
        final long MINUTE_TO_LONG=SECOND_TO_LONG*60;
        final long HOUR_TO_LONG=MINUTE_TO_LONG*60;
        final long DAY_TO_LONG=HOUR_TO_LONG*24;

        SimpleDateFormat sdf= new SimpleDateFormat("MM-dd HH:mm");
        SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd");

        long ct=System.currentTimeMillis();
        long cost = ct-dateLong*1000l;

        Date ctDate=new Date(ct);
        Date longDate=new Date(dateLong*1000l);

        if(cost<MINUTE_TO_LONG ){
            return cost/SECOND_TO_LONG +"秒前";
        }

        else if(cost<HOUR_TO_LONG){
            return cost/MINUTE_TO_LONG+"分钟前";
        }
        else if(cost<DAY_TO_LONG){
            return cost/HOUR_TO_LONG+"小时前";
        }
        else if(ctDate.getYear()>longDate.getYear()){
            return sdf2.format(longDate);
        }
        else return sdf.format(longDate);

    }
}

