package com.zjut.material_wecenter.controller.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.models.Result;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostActivity extends AppCompatActivity {

    private ArrayList<String> topics = new ArrayList<>();
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.btn_add_topic) Button btnAddTopic;
    @Bind(R.id.txt_topics) TextView textTopics;
    @Bind(R.id.edit_title) MaterialEditText editTitle;
    @Bind(R.id.edit_content) MaterialEditText editContent;
    @Bind(R.id.edit_topic) MaterialEditText editTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        // 初始化工具栏
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
        // 实例化控件
        editTitle.validate("\\w{5,}", "标题长度不能少于5个字");
        // 添加话题
        btnAddTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTopic.getText().toString();
                if (!text.isEmpty()) {
                    topics.add(text);
                    String old = textTopics.getText().toString();
                    old += text + "   ";
                    textTopics.setText(old);
                    editTopic.getText().clear();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_post) {   // 点击发布问题
            if (editTitle.validate())
                new PublishTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 发布问题的异步任务
     */
    class PublishTask extends AsyncTask<Void, Void, Void> {

        private String title, content;
        private Result result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            content = editContent.getText().toString();
            title = editTitle.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            result = Client.getInstance().publishQuestion(title, content, topics);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (result == null) // 未知错误
                Snackbar.with(PostActivity.this).text("未知错误").show(PostActivity.this);
            else if (result.getErrno() == 1)    // 发布成功
                PostActivity.this.finish();
            else                // 显示错误
                Snackbar.with(PostActivity.this).text(result.getErr()).show(PostActivity.this);
        }
    }
}
