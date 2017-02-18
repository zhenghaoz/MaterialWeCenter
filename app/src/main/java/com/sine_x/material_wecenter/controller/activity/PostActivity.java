package com.sine_x.material_wecenter.controller.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.models.PublishQuestion;
import com.sine_x.material_wecenter.models.Response;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.mthli.knife.KnifeText;

public class PostActivity extends AppCompatActivity {

    private ArrayList<String> topics = new ArrayList<>();
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.btn_add_topic) Button btnAddTopic;
    @Bind(R.id.txt_topics) TextView textTopics;
    @Bind(R.id.edit_title) MaterialEditText editTitle;
    @Bind(R.id.edit_content) KnifeText editContent;
    @Bind(R.id.edit_topic) MaterialEditText editTopic;

    // 编辑器
    @OnClick(R.id.action_undo)
    void undo() {
        editContent.undo();
    }

    @OnClick(R.id.action_redo)
    void redo() {
        editContent.redo();
    }

    @OnClick(R.id.action_bold)
    void setBold() {
        editContent.bold(!editContent.contains(KnifeText.FORMAT_BOLD));
    }

    @OnClick(R.id.action_italic)
    void setItalic() {
        editContent.italic(!editContent.contains(KnifeText.FORMAT_ITALIC));
    }

    @OnClick(R.id.action_underline)
    void setUnderline() {
        editContent.underline(!editContent.contains(KnifeText.FORMAT_UNDERLINED));
    }

    @OnClick(R.id.action_quote)
    void setQuote() {
        editContent.quote(!editContent.contains(KnifeText.FORMAT_QUOTE));
    }

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
        private Response<PublishQuestion> result2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            content = editContent.toHtml();
            content = content.replace("<", "[");
            content = content.replace(">", "]");
            Log.d("CONTENT", content);
            title = editTitle.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            result2 = Client.getInstance().publishQuestion(title, content, topics);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (result2 == null) // 未知错误
                Snackbar.with(PostActivity.this).text("未知错误").show(PostActivity.this);
            else if (result2.getErrno() == 1)    // 发布成功
                PostActivity.this.finish();
            else                // 显示错误
                Snackbar.with(PostActivity.this).text(result2.getErr()).show(PostActivity.this);
        }
    }
}
