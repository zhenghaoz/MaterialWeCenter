package com.zjut.material_wecenter.controller.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zjut.material_wecenter.R;

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
