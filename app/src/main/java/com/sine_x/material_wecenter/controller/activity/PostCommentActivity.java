package com.sine_x.material_wecenter.controller.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.models.Response;

import org.apache.commons.lang3.StringEscapeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.mthli.knife.KnifeText;

public class PostCommentActivity extends AppCompatActivity {

    public static final int POST_COMMENT_POS = 1;
    public static final int POST_COMMENT_NEG = 0;

    int articleID;
    String artiicleTitle;

    @BindView(R.id.question_title)
    TextView title;
    @BindView(R.id.editText_answerContent)
    KnifeText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_answer);
        ButterKnife.bind(this);

        //获取intent
        Intent mIntent = getIntent();
        articleID = mIntent.getIntExtra(Config.INT_ARTICLE_ID, -1);
        artiicleTitle = mIntent.getStringExtra(Config.INT_ARTICLE_TITLE);

        //init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_publishAnswer);
        setSupportActionBar(toolbar);
        title.setText("评论 " + artiicleTitle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(POST_COMMENT_NEG);
                finish();
            }
        });
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.imageButton_publishAnswer)
    void comment() {
        if (!editContent.toHtml().isEmpty())
            new CommentTask().execute();
    }

    class CommentTask extends AsyncTask<Void, Void, Void> {
        Response response;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            message = editContent.toHtml();
            message = StringEscapeUtils.unescapeHtml4(message);
        }

        @Override
        protected Void doInBackground(Void... params) {
            response = Client.getInstance().articleSaveComment(articleID, message);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (response.getErrno() == 1) { // 发布成功
                PostCommentActivity.this.setResult(POST_COMMENT_POS);
                PostCommentActivity.this.finish();
            } else                // 显示错误
                Snackbar.with(PostCommentActivity.this).text(response.getErr()).show(PostCommentActivity.this);
        }
    }
}
