package com.sine_x.material_wecenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.activity.UserActivity;
import com.sine_x.material_wecenter.models.AnswerComment;
import com.sine_x.material_wecenter.models.AnswerDetail;
import com.sine_x.material_wecenter.models.Response;
import com.squareup.picasso.Picasso;
import com.zzhoujay.richtext.RichText;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnswerDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Html.ImageGetter{

    private final int TYPE_HEADER=0;
    private final int TYPE_DETAIL =1;
    private final int TYPE_ITEM = 3;

    private Context mContext;
    private AnswerDetail answerDetail;
    private List<AnswerComment> answerComments;

    public AnswerDetailAdapter(Context context, AnswerDetail answerDetail,
                               List<AnswerComment> answerComments){
        this.mContext = context;
        this.answerDetail=answerDetail;
        this.answerComments=answerComments;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            case 1:
                return TYPE_DETAIL;
            default:
                return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==TYPE_HEADER){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_answer_detail, parent, false);
            return new HeaderViewHolder(view);
        }
        else if(viewType== TYPE_DETAIL){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_rich_text, parent, false);
            return new TextViewHolder(view);
        }

        else {
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_answer_comment, parent, false);
            return new ItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof HeaderViewHolder){

            HeaderViewHolder headerViewHolder=(HeaderViewHolder) holder;
            final AnswerDetail.AnswerEntity answer = answerDetail.getAnswer();
            String avatarFile =answer.getUser_info().getAvatar_file();
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(headerViewHolder.avatar);
            headerViewHolder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserActivity.class);
                    intent.putExtra("uid", answer.getUser_info().getUid());
                    mContext.startActivity(intent);
                }
            });
            headerViewHolder.agreeCount.setText(answer.getAgree_count() + "");
            headerViewHolder.commentCount.setText(answer.getComment_count() + "");
            headerViewHolder.thankCount.setText(answer.getThanks_count() + "");

            if(answer.getUser_vote_status()==1){
                headerViewHolder.agree.setImageResource(R.drawable.ic_agree_red);
            }

            headerViewHolder.agree.setOnClickListener(new ItemOnClickListener(headerViewHolder.agreeCount,
                    headerViewHolder.agree,answer.getUser_vote_status(),ItemOnClickListener.TYPE_AGREE,
                    answer.getAnswer_id(),answer.getAgree_count()));

            if(answer.getUser_thanks_status()==1){
                headerViewHolder.thank.setImageResource(R.drawable.ic_red_heart);
            }

            headerViewHolder.thank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(answer.getUser_thanks_status()!=1){
                        new DoAction(DoAction.THANKS, answer.getAnswer_id()).execute();
                        ((ImageView)v).setImageResource(R.drawable.ic_red_heart);
                    }
                }
            });

        }
        else if (holder instanceof TextViewHolder){
            TextViewHolder textViewHolder=(TextViewHolder) holder;
            RichText.from(answerDetail.getAnswer().getAnswer_content())
                    .into(textViewHolder.text);
            textViewHolder.text.setVisibility(View.VISIBLE);
        }
        else if(holder instanceof ItemViewHolder){

            final AnswerComment answerComment= answerComments.get(position-2);
            ItemViewHolder itemViewHolder=(ItemViewHolder) holder;

            String avatarFile =answerComment.getUser_info().getAvatar_file();
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(itemViewHolder.avatar);

            itemViewHolder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, UserActivity.class);
                    intent.putExtra("uid",answerComment.getUser_info().getUid());
                    mContext.startActivity(intent);
                }
            });
            String addTime=getTime(answerComment.getTime());
            itemViewHolder.addTime.setText(addTime);
            itemViewHolder.userName.setText(answerComment.getUser_info().getUser_name());
            itemViewHolder.message.setText(Html.fromHtml(answerComment.getMessage(), this, null));
            itemViewHolder.message.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return 2 + (answerComments == null ? 0 : answerComments.size());
    }

    @Override
    public Drawable getDrawable(String source) {
        Drawable drawable=null;
        URL url;
        try {
            url = new URL(source);
            drawable = Drawable.createFromStream(url.openStream(), "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth()*10, drawable.getIntrinsicHeight()*10);
        return drawable;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView avatar;
        private TextView agreeCount;
        private ImageView agree;
        private TextView commentCount;
        private TextView thankCount;
        private ImageView thank;

        public HeaderViewHolder(View view) {
            super(view);
            avatar=(CircleImageView) view.findViewById(R.id.avatar_img_answer);
            agreeCount=(TextView) view.findViewById(R.id.textView_agreeCount_answer);
            agree=(ImageView) view.findViewById(R.id.imageView_agreeCount_answer);
            commentCount=(TextView) view.findViewById(R.id.textView_commentCount_answer);
            thankCount=(TextView) view.findViewById(R.id.textView_thankCount_answer);
            thank=(ImageView) view.findViewById(R.id.imageView_thankCount_answer);
        }

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView avatar;
        private TextView userName;
        private TextView addTime;
        private TextView message;

        public ItemViewHolder(View view) {
            super(view);
            avatar=(CircleImageView) view.findViewById(R.id.avatar_img_answer_comment);
            userName=(TextView) view.findViewById(R.id.textView_userName_answer_comment);
            addTime=(TextView) view.findViewById(R.id.textView_addTime_answer_comment);
            message=(TextView) view.findViewById(R.id.textView_message_answer_comment);
        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder{

        TextView text;

        public TextViewHolder(View view) {
            super(view);
            text=(TextView) view.findViewById(R.id.textView_detailText_question);
        }

    }


    //获取发布时间
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

    //异步进行点赞等操作

    private class DoAction extends AsyncTask<Integer,Integer,Integer> {

        private static final int AGREE=0;
        private static final int THANKS=1;
        private int answerID;
        private int action;
        Response<Object> result2;

        public DoAction(int action,int answerID){
            this.action=action;
            this.answerID=answerID;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                Client client=Client.getInstance();
                ArrayList<String> strs=new ArrayList<>();
                switch (action){
                    case AGREE:
                        strs.add(answerID+"");
                        strs.add(1+"");
                        result2 =client.postAction(Config.ActionType.ANSWER_VOTE,null,strs);
                        break;
                    case THANKS:
                        strs.add("thanks");
                        strs.add(answerID+"");
                        result2 =client.postAction(Config.ActionType.ANSWER_RATE,null,strs);
                        break;
                    default:
                        break;
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    private class ItemOnClickListener implements View.OnClickListener {

        private TextView text;
        private ImageView image;
        private int status;
        private int num;
        private int type;
        private int answerID;

        public static final int TYPE_AGREE=0;
        public static final int TYPE_THANK=1;

        public ItemOnClickListener(TextView text, ImageView image, int status,
                                   int type, int answerID,int num) {
            this.text = text;
            this.image = image;
            this.status = status;
            this.type = type;
            this.answerID = answerID;
            this.num=num;
        }

        @Override
        public void onClick(View v) {
            new DoAction(DoAction.AGREE, answerID).execute();
            if (status == 0) {
                image.setImageResource(R.drawable.ic_agree_red);
                text.setText((++num)+"");
                status=1;
            }
            else if (status==1) {
                image.setImageResource(R.drawable.ic_agree);
                text.setText((--num)+"");
                status=0;
            }
        }
    }

}
