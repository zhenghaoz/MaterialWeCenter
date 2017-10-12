package com.sine_x.material_wecenter.controller.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.slyce.messaging.SlyceMessagingFragment;
import it.slyce.messaging.message.TextMessage;

public class ChatActivity extends AppCompatActivity {

    SlyceMessagingFragment fragment;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
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
        // 实例化碎片
        fragment = (SlyceMessagingFragment) getFragmentManager().findFragmentById(R.id.messaging_fragment);
        // 加载对话
        Intent intent = getIntent();
        String username = intent.getStringExtra(Config.INT_CHAT_USERNAME);
        setTitle(username);

        TextMessage message = new TextMessage();
        message.setText("Hello");
        fragment.addNewMessage(message);
    }

    class LoadMessageList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
