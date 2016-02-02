package com.zjut.material_wecenter.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.Config;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.models.Result;

import java.util.ArrayList;

public class QuestionMenuActivity extends Activity implements View.OnClickListener {

    private int resultCode=0;
    private int questionID;
    private String questionTitle;
    private View focus;
    private TextView focusText;
    private View thanks;
    private TextView thanksText;
    private View answer;
    private View invite;
    private View close;

    private int isFocus;
    private int isThanks;
    private int isAllowToAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_menu);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        Intent intent=getIntent();
        questionID=intent.getIntExtra("questionID", -1);
        questionTitle=intent.getStringExtra("questionTitle");
        isFocus=intent.getIntExtra("isFocus", -1);
        isThanks=intent.getIntExtra("isThanks",-1);
        isAllowToAnswer=intent.getIntExtra("isAllowToAnswer", -1);

        focus=findViewById(R.id.fab_question_focus);
        focus.setOnClickListener(this);
        focusText=(TextView) findViewById(R.id.textView_focus);
        if(isFocus==1) focusText.setText("已关注");
        else focusText.setText("关注");

        thanks=findViewById(R.id.fab_question_thanks);
        thanks.setOnClickListener(this);
        thanksText= (TextView)findViewById(R.id.textView_thanks);
        if(isThanks==1) thanksText.setText("已感谢");
        else thanksText.setText("感谢");

        answer=findViewById(R.id.fab_question_answer);
        answer.setOnClickListener(this);
        invite=findViewById(R.id.fab_question_invite);
        invite.setOnClickListener(this);
        close=findViewById(R.id.fab_question_close);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.fab_question_focus:
                new DoAction(DoAction.FOCUS).execute();
                break;
            case R.id.fab_question_thanks:
                new DoAction(DoAction.THANKS).execute();
                break;
            case R.id.fab_question_answer:
                if(isAllowToAnswer==0){
                    intent=new Intent(this,PostAnswerActivity.class);
                    intent.putExtra("questionID",questionID);
                    intent.putExtra("questionTitle",questionTitle);
                    startActivityForResult(intent,resultCode);
                }
                else {
                    Toast.makeText(this,"对不起，您不可以再继续回答该问题了！",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.fab_question_invite:
                break;
            case R.id.fab_question_close:
                finish();
                break;
        }
    }

    private class DoAction extends AsyncTask<Integer,Integer,Integer> {

        private static final int FOCUS=0;
        private static final int THANKS=1;
        private int action;
        Result result;

        public DoAction(int action){
            this.action=action;
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
                    case FOCUS:
                        strs.add(questionID+"");
                        result=client.postAction(Config.ActionType.QUESTION_FOCUS,Focus.class,strs);
                        break;
                    case THANKS:
                        strs.add(questionID+"");
                        result=client.postAction(Config.ActionType.QUESTION_THANKS,Thanks.class,strs);
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
            switch (action){
                case FOCUS:
                    Focus mFocus=(Focus)result.getRsm();
                    if(mFocus.getType().equals("add")){
                        focusText.setText("已关注");
                    }
                    else if(mFocus.getType().equals("remove")){
                        focusText.setText("关注");
                    }
                    else Toast.makeText(QuestionMenuActivity.this,"操作失败",Toast.LENGTH_LONG);
                    break;
                case THANKS:
                    thanksText.setText("已感谢");
                    break;
                default:
                    break;
            }
        }
    }

    private static class Focus{

        private String type;

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    private static class Thanks{

        private String type;

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
