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
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nispok.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.models.PublishQuestion;
import com.sine_x.material_wecenter.models.Response;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.mthli.knife.KnifeText;
import me.gujun.android.taggroup.TagGroup;

public class PostActivity extends AppCompatActivity {

    private ArrayList<String> topics = new ArrayList<>();
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tag_group) TagGroup tagGroup;
    @Bind(R.id.edit_title) MaterialEditText editTitle;
    @Bind(R.id.edit_content) KnifeText editContent;
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
        tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                topics.remove(tag);
                tagGroup.setTags(topics);
            }
        });
    }

    @OnClick(R.id.btn_add_topic)
    void addTopic() {
            topics.add(editTopic.getText().toString());
            tagGroup.setTags(topics);
            editTopic.setText("");
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
        switch (id) {
            case R.id.action_post:
                if (editTitle.validate())
                    new PublishTask().execute();
                break;
            case R.id.action_undo:
                editContent.undo();
                break;
            case R.id.action_redo:
                editContent.redo();
                break;
            case R.id.action_bold:
                editContent.bold(!editContent.contains(KnifeText.FORMAT_BOLD));
                break;
            case R.id.action_italic:
                editContent.italic(!editContent.contains(KnifeText.FORMAT_ITALIC));
                break;
            case R.id.action_quote:
                editContent.quote(!editContent.contains(KnifeText.FORMAT_QUOTE));
                break;
            case R.id.action_list_bulleted:
                editContent.bullet(!editContent.contains(KnifeText.FORMAT_BULLET));
                break;
            case R.id.action_insert_link:
                new MaterialDialog.Builder(this)
                        .title("插入链接")
                        .content("编辑链接地址")
                        .input("链接地址", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                editContent.link(input.toString());
                            }
                        }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private String htmlToBBcode(String html) {
        html = html.replace("<br>", "\n");
        html = html.replace("<i>", "[i]");
        html = html.replace("</i>", "[/i]");
        html = html.replace("<b>", "[b]");
        html = html.replace("</b>", "[/b]");
        html = html.replace("<blockquote>", "[quote]");
        html = html.replace("</blockquote>", "[/quote]");
        html = html.replace("<ul>", "[list]");
        html = html.replace("</ul>", "[/list]");
        html = html.replace("<li>", "[*]");
        html = html.replace("</li>", "[/*]");
        html = html.replaceAll("<a\\shref=\"(.*)\">", "[url=$1]");
        html = html.replace("</a>", "[/url]");
        html = StringEscapeUtils.unescapeHtml4(html);
        return html;
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
            content = htmlToBBcode(editContent.toHtml());
//            Log.d("CONTENT", content);
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
