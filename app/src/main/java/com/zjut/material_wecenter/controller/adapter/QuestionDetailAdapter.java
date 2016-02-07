package com.zjut.material_wecenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.activity.AnswerActivity;
import com.zjut.material_wecenter.controller.activity.UserActivity;
import com.zjut.material_wecenter.models.QuestionDetail;
import com.zjut.material_wecenter.models.WebData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/1/27.
 */
public class QuestionDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int TYPE_TITLE=0;
    private final int TYPE_DETAIL_TEXT=1;
    private final int TYPE_DETAIL_IMAGE=2;
    private final int TYPE_ITEM = 3;
    private final int TYPE_FOOTER = 4;
    private final int TYPE_INFO=5;

    private int detailIndex;
    private int itemIndex;
    private int infoIndex;
    private Context mContext;
    private QuestionDetail questionDetail;
    private ArrayList<WebData> webDatas;

    public QuestionDetailAdapter(Context context, QuestionDetail questionDetail){

        this.mContext = context;
        this.questionDetail=questionDetail;
        webDatas=getData(questionDetail.getQuestion_info().getQuestion_detail());
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return TYPE_TITLE;
        else if(position==getItemCount()-1) return TYPE_FOOTER;
        else if(position<infoIndex&&position>=detailIndex) {

            if(webDatas.get(position-detailIndex).getType()== WebData.Type.TEXT){
                return TYPE_DETAIL_TEXT;
            }

            else {
                return TYPE_DETAIL_IMAGE;
            }

        }
        else if(position==infoIndex){
            return TYPE_INFO;
        }
        else return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==TYPE_TITLE){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_question_title, parent, false);
            return new TitleViewHolder(view);
        }
        else if(viewType==TYPE_DETAIL_TEXT){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_text, parent, false);
            return new TextViewHolder(view);
        }
        else if(viewType==TYPE_DETAIL_IMAGE){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(view);
        }
        else if(viewType==TYPE_FOOTER){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_question_footer, parent, false);
            return new FooterViewHolder(view);
        }
        else if(viewType==TYPE_INFO){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_question_info, parent, false);
            return new InfoViewHolder(view);
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

            titleViewHolder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, UserActivity.class);
                    intent.putExtra("uid",questionInfo.getUser_info().getUid()+"");
                    mContext.startActivity(intent);
                }
            });
            titleViewHolder.signature.setText(questionInfo.getUser_info().getSignature());
            titleViewHolder.userName.setText(questionInfo.getUser_info().getUser_name());
            titleViewHolder.title.setText(questionInfo.getQuestion_content());
        }
        else if (holder instanceof TextViewHolder){
            TextViewHolder textViewHolder=(TextViewHolder) holder;
            WebData webData=webDatas.get(position-detailIndex);
            if(webData.getGravity()== WebData.Gravity.CENTER)
                textViewHolder.text.setGravity(Gravity.CENTER);
            else if(webData.getGravity()== WebData.Gravity.RIGHT)
                textViewHolder.text.setGravity(Gravity.RIGHT);
            else textViewHolder.text.setGravity(Gravity.LEFT);
            Log.e("webData",webDatas.get(position-detailIndex).getData());
            textViewHolder.text.setText(Html.fromHtml(webDatas.get(position - detailIndex).getData()));
            textViewHolder.text.setVisibility(View.VISIBLE);
        }
        else if(holder instanceof ImageViewHolder){
            final ImageViewHolder imageViewHolder=(ImageViewHolder) holder;
            final String file=webDatas.get(position-detailIndex).getData();
            imageViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!file.isEmpty())
                        Picasso.with(mContext).load(file).into(imageViewHolder.imageView);
                }
            });

            imageViewHolder.imageView.setVisibility(View.VISIBLE);

        }
        else if(holder instanceof InfoViewHolder){

            final QuestionDetail.QuestionInfo questionInfo=questionDetail.getQuestion_info();
            InfoViewHolder infoViewHolder=(InfoViewHolder) holder;
            infoViewHolder.viewCount.setText(questionInfo.getView_count()+"");
            infoViewHolder.answerCount.setText(questionInfo.getAnswer_count()+"");
            infoViewHolder.thankCount.setText(questionInfo.getThanks_count()+"");
            String time=getTime(questionInfo.getAdd_time());
            infoViewHolder.addTime.setGravity(Gravity.RIGHT);
            infoViewHolder.addTime.setText("发布于  "+time);

        }
        else if(holder instanceof ItemViewHolder){

            ItemViewHolder itemViewHolder=(ItemViewHolder) holder;
            final QuestionDetail.AnswerInfo answerInfo=questionDetail.getAnswers().get(position - itemIndex);
            String avatarFile =answerInfo.getUser_info().getAvatar_file();
            //Log.e("avatarFile",avatarFile);
            if (!avatarFile.isEmpty())
                Picasso.with(mContext)
                        .load(avatarFile)
                        .into(itemViewHolder.avatar);

            itemViewHolder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, UserActivity.class);
                    intent.putExtra("uid",answerInfo.getUser_info().getUid()+"");
                    mContext.startActivity(intent);
                }
            });
            String addTime=getTime(answerInfo.getAdd_time());
            itemViewHolder.addTime.setText(addTime);
            itemViewHolder.userName.setText(answerInfo.getUser_info().getUser_name());
            itemViewHolder.agreeCount.setText(answerInfo.getAgree_count()+"");

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
        if(webDatas==null&&questionDetail.getAnswers()==null){
            detailIndex=1;
            infoIndex=detailIndex;
            itemIndex=infoIndex;
            return 3;
        }
        else if(webDatas==null){
            detailIndex=1;
            infoIndex=detailIndex;
            itemIndex=infoIndex+1;
            return questionDetail.getAnswers().size()+3;
        }
        else if(questionDetail.getAnswers()==null){
            detailIndex=1;
            infoIndex=webDatas.size()+detailIndex;
            itemIndex=infoIndex+1;
            return webDatas.size()+3;
        }
        else {
            detailIndex=1;
            infoIndex=webDatas.size()+detailIndex;
            itemIndex=1+infoIndex;
            return webDatas.size()+questionDetail.getAnswers().size()+3;
        }
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

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView avatar;
        private TextView userName;
        private TextView addTime;
        private TextView briefDetail;
        private TextView agreeCount;

        public ItemViewHolder(View view) {
            super(view);
            avatar=(CircleImageView) view.findViewById(R.id.avatar_img_answer);
            userName=(TextView) view.findViewById(R.id.textView_userName_answer);
            addTime=(TextView) view.findViewById(R.id.textView_addTime_answer);
            briefDetail=(TextView) view.findViewById(R.id.textView_briefDetail_answer);
            agreeCount=(TextView) view.findViewById(R.id.textView_agree_answer);
        }
    }
    public class InfoViewHolder extends RecyclerView.ViewHolder{

        TextView addTime;
        TextView viewCount;
        TextView answerCount;
        TextView thankCount;
        public InfoViewHolder(View view) {
            super(view);
            addTime=(TextView) view.findViewById(R.id.textView_addTime_question);
            viewCount=(TextView) view.findViewById(R.id.textView_viewCount_question);
            answerCount=(TextView) view.findViewById(R.id.textView_answerCount_question);
            thankCount=(TextView) view.findViewById(R.id.textView_thankCount_question);
        }
    }


    public class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder{

        TextView text;

        public TextViewHolder(View view) {
            super(view);
            text=(TextView) view.findViewById(R.id.textView_detailText_question);
        }

    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            imageView=(ImageView) view.findViewById(R.id.imageView_detailImage_question);
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

    //获取内容详情，过滤文本和图片
    public static ArrayList<WebData> getData(String html){

        final String emotion="http://bbs.zjut.edu.cn/static/umeditor/dialogs/emotion/";

        ArrayList<WebData> list=new ArrayList<>();
        Document doc= Jsoup.parse(html);
        String docHtml=doc.html();
        if(!docHtml.contains("<p")&&!docHtml.contains("<div")){
            WebData webData=new WebData(WebData.Type.TEXT,docHtml, WebData.Gravity.LEFT);
            list.add(webData);
            return list;
        }
        Elements tags=doc.select("p,div");
        for(Element tag : tags){
            String tagHtml=tag.html();
            if(tag.tagName().equals("p")){
                WebData webData;
                String pHtml=tag.html();
                if(pHtml.contains("<img")){
                    Element img=tag.select("img").first();
                    String src=img.attr("src");
                    if(src.startsWith(emotion)){
                        String align=tag.attr("style");
                        if(!align.isEmpty()){
                            if(align.contains("center")){
                                webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.CENTER);
                            }
                            else if(align.contains("right")){
                                webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.RIGHT);
                            }
                            else
                                webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.LEFT);
                        }
                        else
                            webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.LEFT);
                    }
                    else {
                        webData=new WebData(WebData.Type.IMAGE,src,WebData.Gravity.CENTER);
                    }
                }
                else{
                    String align=tag.attr("style");
                    if(!align.isEmpty()){
                        if(align.contains("center")){
                            webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.CENTER);
                        }
                        else if(align.contains("right")){
                            webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.RIGHT);
                        }
                        else
                            webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.LEFT);
                    }
                    else
                        webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.LEFT);
                }
                list.add(webData);
            }
            else if(tag.tagName().equals("div")){
                if(!tagHtml.contains("upload-img")){
                    Elements pInDiv=tag.select("p,div");
                    for(Element p:pInDiv){
                        WebData webData;
                        String pHtml=p.html();
                        if(pHtml.contains("<img")){
                            Element img=p.select("img").first();
                            String src=img.attr("src");
                            if(src.startsWith(emotion)){
                                String align=p.attr("style");
                                if(!align.isEmpty()){
                                    if(align.contains("center")){
                                        webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.CENTER);
                                    }
                                    else if(align.contains("right")){
                                        webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.RIGHT);
                                    }
                                    else
                                        webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.LEFT);
                                }
                                else
                                    webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.LEFT);
                            }
                            else {
                                webData=new WebData(WebData.Type.IMAGE,src,WebData.Gravity.CENTER);
                            }
                        }
                        else{
                            String align=p.attr("style");
                            if(!align.isEmpty()){
                                if(align.contains("center")){
                                    webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.CENTER);
                                }
                                else if(align.contains("right")){
                                    webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.RIGHT);
                                }
                                else
                                    webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.LEFT);
                            }
                            else
                                webData=new WebData(WebData.Type.TEXT,pHtml, WebData.Gravity.LEFT);
                        }
                        list.add(webData);
                    }
                }
                else {
                    Element a=tag.getElementsByTag("a").first();
                    String src=a.attr("href");
                    WebData webData=new WebData(WebData.Type.IMAGE,src, WebData.Gravity.CENTER);
                    list.add(webData);
                }

            }

        }

        return list;
    }

}

