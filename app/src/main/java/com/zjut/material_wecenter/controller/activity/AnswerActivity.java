package com.zjut.material_wecenter.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nispok.snackbar.Snackbar;
import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.Config;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.adapter.AnswerDetailAdapter;
import com.zjut.material_wecenter.models.AnswerComment;
import com.zjut.material_wecenter.models.AnswerDetail;
import com.zjut.material_wecenter.models.Result;
import com.zjut.material_wecenter.ui.ItemDivider;

import java.util.ArrayList;

public class AnswerActivity extends Activity implements View.OnClickListener{

    private int answerID;
    private Client client = Client.getInstance();
    private AnswerDetail answerDetail;
    private ArrayList<AnswerComment> answerComments;
    private RecyclerView recyclerView;
    private AnswerDetailAdapter answerDetailAdapter;
    private EditText content;
    private Button publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        getWindow().setLayout(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.MATCH_PARENT);

        Intent mIntent=getIntent();
        answerID=mIntent.getIntExtra("answerID", -1);
        Log.e("AnswerActivity","start"+answerID);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView_answer);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ItemDivider(this, LinearLayoutManager.VERTICAL, ItemDivider.TITLE_INDEX));

        content=(EditText) findViewById(R.id.edit_content_answerComment);
        publish=(Button) findViewById(R.id.btn_add_answerComment);
        publish.setOnClickListener(this);
        new LoadAnswerDetail().execute();
    }

    @Override
    public void onClick(View v) {
        String mContent=content.getText().toString();
        switch (v.getId()){
            case R.id.btn_add_answerComment:
                if(!mContent.isEmpty()){
                    new PublishTask().execute();
                }
                break;
            default:
                break;
        }
    }

    private class LoadAnswerDetail extends AsyncTask<Integer,Integer,Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                Log.e("LoadAnswer", "load has started");
                answerDetail=(AnswerDetail) client.getAnswer(answerID).getRsm();
                answerComments=(ArrayList<AnswerComment>) client.getAnswerComments(answerID).getRsm();

            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            answerDetailAdapter=new AnswerDetailAdapter(AnswerActivity.this,answerDetail,answerComments);
            recyclerView.setAdapter(answerDetailAdapter);
            answerDetailAdapter.notifyDataSetChanged();

        }
    }

    private class PublishTask extends AsyncTask<Void, Void, Void> {

        String mContent;
        Result result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mContent = content.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Client client = Client.getInstance();
            ArrayList<String> strs=new ArrayList<>();
            strs.add(answerID+"");
            strs.add(mContent);
            result = client.postAction(Config.ActionType.PUSHLISH_ANSWER_COMMENT,PublishAnswerComment.class,strs);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            answerDetailAdapter.notifyItemInserted(answerDetailAdapter.getItemCount());
            if (result == null) // 未知错误
                Snackbar.with(AnswerActivity.this).text("未知错误").show(AnswerActivity.this);
            else if (result.getErrno() == 1){ // 发布成功
                AnswerActivity.this.finish();
            }

            else                // 显示错误
                Snackbar.with(AnswerActivity.this).text(result.getErr()).show(AnswerActivity.this);
        }
    }

    public static class PublishAnswerComment{

        private int item_id;
        private String type_name;

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public int getItem_id() {
            return item_id;
        }

        public String getType_name() {
            return type_name;
        }
    }
}
