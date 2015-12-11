package com.zjut.material_wecenter.controller.activity;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.zjut.material_wecenter.models.PublishQuestion;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    ArrayList<String> topics = new ArrayList<>();
    Button btnAddTopic;
    TextView textTopics;
    MaterialEditText editTitle, editContent, editTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        // Init view
        btnAddTopic = (Button) findViewById(R.id.btn_add_topic);
        textTopics = (TextView) findViewById(R.id.txt_topics);
        editTitle = (MaterialEditText) findViewById(R.id.edit_title);
        editContent = (MaterialEditText) findViewById(R.id.edit_content);
        editTopic = (MaterialEditText) findViewById(R.id.edit_topic);
        editTitle.validate("\\w{5,}", "标题长度不能少于5个字");

        // Add topics
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_post) {
            if (editTitle.validate())
                new PublishTask().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class PublishTask extends AsyncTask<Void, Void, Void> {

        String title, content;
        PublishQuestion result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            content = editContent.getText().toString() + "<br><br>————来自精弘论坛安卓客户端";
            title = editTitle.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Client client = Client.getInstance();
            result = client.publishQuestion(title, content, topics);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (result == null)
                Snackbar.with(PostActivity.this).text("未知错误").show(PostActivity.this);
            else if (result.getErrno() == 1)
                PostActivity.this.finish();
            else
                Snackbar.with(PostActivity.this).text((String) result.getErr()).show(PostActivity.this);
        }
    }
}
