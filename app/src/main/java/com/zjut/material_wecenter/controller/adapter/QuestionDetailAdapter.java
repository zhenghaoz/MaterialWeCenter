package com.zjut.material_wecenter.controller.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.models.QuestionDetail;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/1/27.
 */
public class QuestionDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Html.ImageGetter{

    private static final int TYPE_TITLE=0;
    private static final int TYPE_DETAIL=1;
   // private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_FOOTER = 3;
    private Context mContext;
    private QuestionDetail questionDetail;

    public QuestionDetailAdapter(Context context, QuestionDetail questionDetail){

        this.mContext = context;
        this.questionDetail=questionDetail;

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return TYPE_TITLE;
        else if(position==1) return TYPE_DETAIL;
        else if(position==getItemCount()-1) return TYPE_FOOTER;
        else return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==TYPE_TITLE){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_question_title, parent, false);
            return new TitleViewHolder(view);
        }
        else if(viewType==TYPE_DETAIL){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_question_detail, parent, false);
            return new DetailViewHolder(view);
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

        if(holder instanceof TitleViewHolder){
            final QuestionDetail.QuestionInfo questionInfo=questionDetail.getQuestion_info();
            String avatarFile=questionInfo.getUser_info().getAvatar_file();
            TitleViewHolder titleViewHolder=(TitleViewHolder) holder;
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(titleViewHolder.avatar);

            titleViewHolder.signature.setText(questionInfo.getUser_info().getSignature());
            titleViewHolder.userName.setText(questionInfo.getUser_info().getUser_name());
            titleViewHolder.title.setText(questionInfo.getQuestion_content());
        }
        else if(holder instanceof DetailViewHolder){
            final QuestionDetail.QuestionInfo questionInfo=questionDetail.getQuestion_info();
            DetailViewHolder detailViewHolder=(DetailViewHolder) holder;

            //headerViewHolder.detail.setText(Html.fromHtml(questionInfo.getQuestion_detail(),this,null1));
            detailViewHolder.detail.setBackgroundColor(0);
            detailViewHolder.detail.loadDataWithBaseURL(null, questionInfo.getQuestion_detail(),
                    "text/html", "utf-8", null);
            detailViewHolder.detail.setVisibility(View.VISIBLE);

            detailViewHolder.viewCount.setText(questionInfo.getView_count() + "");
            detailViewHolder.answerCount.setText(questionInfo.getAnswer_count() + "");
            detailViewHolder.thankCount.setText(questionInfo.getThanks_count() + "");

        }
        else if(holder instanceof ItemViewHolder){

            ItemViewHolder itemViewHolder=(ItemViewHolder) holder;
            final QuestionDetail.AnswerInfo answerInfo=questionDetail.getAnswers().get(position-2);
            String avatarFile =answerInfo.getUser_info().getAvatar_file();
            //Log.e("avatarFile",avatarFile);
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(itemViewHolder.avatar);

            String addTime=getTime(answerInfo.getAdd_time());
            itemViewHolder.addTime.setText(addTime);
            itemViewHolder.userName.setText(answerInfo.getUser_info().getUser_name());
            itemViewHolder.briefDetail.setText(Html.fromHtml(answerInfo.getAnswer_content(),this,null));
            itemViewHolder.briefDetail.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return questionDetail.getQuestion_info().getAnswer_count()+3;
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

    public class TitleViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView avatar;
        private TextView userName;
        private TextView signature;
        private TextView title;

        public TitleViewHolder(View view) {
            super(view);
            avatar=(CircleImageView) view.findViewById(R.id.avatar_img_question);
            userName=(TextView) view.findViewById(R.id.textView_userName_question);
            signature=(TextView) view.findViewById(R.id.textView_signature_question);
            title=(TextView) view.findViewById(R.id.textView_title_question);
        }
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder{

        private WebView detail;
        private TextView viewCount;
        private TextView answerCount;
        private TextView thankCount;

        public DetailViewHolder(View view) {
            super(view);
            detail=(WebView) view.findViewById(R.id.webView_detail_question);
            viewCount=(TextView) view.findViewById(R.id.textView_viewCount_question);
            answerCount=(TextView) view.findViewById(R.id.textView_answerCount_question);
            thankCount=(TextView) view.findViewById(R.id.textView_thankCount_question);
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

