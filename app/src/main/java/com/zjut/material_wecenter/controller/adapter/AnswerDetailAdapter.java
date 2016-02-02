package com.zjut.material_wecenter.controller.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.models.AnswerComment;
import com.zjut.material_wecenter.models.AnswerDetail;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/2/1.
 */
public class AnswerDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Html.ImageGetter{

    private final int TYPE_HEADER=0;
    private final int TYPE_ITEM = 1;
    private Context mContext;
    private AnswerDetail answerDetail;
    private ArrayList<AnswerComment> answerComments;

    public AnswerDetailAdapter(Context context, AnswerDetail answerDetail,
                               ArrayList<AnswerComment> answerComments){
        this.mContext = context;
        this.answerDetail=answerDetail;
        this.answerComments=answerComments;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return TYPE_HEADER;
        else return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==TYPE_HEADER){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_answer_detail, parent, false);
            return new HeaderViewHolder(view);
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
            final AnswerDetail.AnswerEntity answer=(AnswerDetail.AnswerEntity) answerDetail.getAnswer();
            String avatarFile =answer.getUser_info().getAvatar_file();
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(headerViewHolder.avatar);
            headerViewHolder.userName.setText(answer.getUser_info().getUser_name());
            headerViewHolder.signature.setText(answer.getUser_info().getSignature());
            headerViewHolder.agreeCount.setText(answer.getAgree_count()+"");
            headerViewHolder.commentCount.setText(answer.getComment_count()+"");
            headerViewHolder.thankCount.setText(answer.getThanks_count()+"");

            headerViewHolder.detail.setBackgroundColor(0);
            headerViewHolder.detail.loadDataWithBaseURL(null, answer.getAnswer_content(),
                    "text/html", "utf-8", null);
            headerViewHolder.detail.setVisibility(View.VISIBLE);
        }
        else if(holder instanceof ItemViewHolder){

            final AnswerComment answerComment= answerComments.get(position-1);
            ItemViewHolder itemViewHolder=(ItemViewHolder) holder;

            String avatarFile =answerComment.getUser_info().getAvatar_file();
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(itemViewHolder.avatar);

            String addTime=getTime(answerComment.getTime());
            itemViewHolder.addTime.setText(addTime);
            itemViewHolder.userName.setText(answerComment.getUser_info().getUser_name());
            itemViewHolder.message.setText(Html.fromHtml(answerComment.getMessage(), this, null));
            itemViewHolder.message.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return answerComments==null?1:answerComments.size()+1;
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
        private TextView userName;
        private TextView signature;
        private WebView detail;
        private TextView agreeCount;
        private TextView commentCount;
        private TextView thankCount;

        public HeaderViewHolder(View view) {
            super(view);
            avatar=(CircleImageView) view.findViewById(R.id.avatar_img_answer);
            userName=(TextView) view.findViewById(R.id.textView_userName_answer);
            signature=(TextView) view.findViewById(R.id.textView_signature_answer);
            detail=(WebView) view.findViewById(R.id.webView_detail_answer);
            agreeCount=(TextView) view.findViewById(R.id.textView_agreeCount_answer);
            commentCount=(TextView) view.findViewById(R.id.textView_commentCount_answer);
            thankCount=(TextView) view.findViewById(R.id.textView_thankCount_answer);
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
