package com.sine_x.material_wecenter.controller.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nispok.snackbar.Snackbar;
import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.models.PublishAnswer;
import com.sine_x.material_wecenter.models.Response;

import org.apache.commons.lang3.StringEscapeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.mthli.knife.KnifeText;

public class PostAnswerActivity extends AppCompatActivity {

    public static final int POST_ANSWER_POS = 1;
    public static final int POST_ANSWER_NEG = 0;

    private int questionID;
    private String questionTitle;
    private ImageButton publish;
    private final int resultCode=1;
    @BindView(R.id.question_title) TextView title;
    @BindView(R.id.editText_answerContent) KnifeText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_answer);
        ButterKnife.bind(this);

        //获取intent
        Intent mIntent=getIntent();
        questionID = mIntent.getIntExtra(Config.INT_QUESTION_ID, -1);
        questionTitle = mIntent.getStringExtra(Config.INT_QUESTION_TITLE);

        //init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_publishAnswer);
        setSupportActionBar(toolbar);
        title.setText("评论 "+questionTitle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(POST_ANSWER_NEG);
                finish();
            }
        });
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        //init editContent publish按钮

        publish=(ImageButton) findViewById(R.id.imageButton_publishAnswer);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editContent.getText().toString();
                if(!text.isEmpty()){
                    new PublishTask().execute();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_answer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
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

    //异步发布答案
    private class PublishTask extends AsyncTask<Void, Void, Void> {

        String content;
        Response<PublishAnswer> result2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            content = editContent.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Client client = Client.getInstance();
            result2 = client.publishAnswer(questionID, content);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (result2 == null) // 未知错误
                Snackbar.with(PostAnswerActivity.this).text("未知错误").show(PostAnswerActivity.this);
            else if (result2.getErrno() == 1){ // 发布成功
                PostAnswerActivity.this.setResult(POST_ANSWER_POS);
                PostAnswerActivity.this.finish();
            } else                // 显示错误
                Snackbar.with(PostAnswerActivity.this).text(result2.getErr()).show(PostAnswerActivity.this);
        }
    }
}
