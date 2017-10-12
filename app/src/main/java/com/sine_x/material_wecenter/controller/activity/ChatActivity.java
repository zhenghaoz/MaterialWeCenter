package com.sine_x.material_wecenter.controller.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.models.Chat;
import com.sine_x.material_wecenter.models.Responses;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.slyce.messaging.SlyceMessagingFragment;
import it.slyce.messaging.listeners.UserClicksAvatarPictureListener;
import it.slyce.messaging.message.MessageSource;
import it.slyce.messaging.message.TextMessage;

public class ChatActivity extends AppCompatActivity {

    SlyceMessagingFragment fragment;

    int id;

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
        fragment.setStyle(R.style.ChatTheme);
        fragment.setUserClicksAvatarPictureListener(new UserClicksAvatarPictureListener() {
            @Override
            public void userClicksAvatarPhoto(String userId) {
                Intent intent = new Intent(ChatActivity.this, UserActivity.class);
                intent.putExtra("uid", Integer.parseInt(userId));
                startActivity(intent);
            }
        });
        // 加载对话
        Intent intent = getIntent();
        String username = intent.getStringExtra(Config.INT_CHAT_USERNAME);
        setTitle(username);
        id = intent.getIntExtra(Config.INT_CHAT_ID, 0);
        new LoadMessageList().execute();
    }

    class LoadMessageList extends AsyncTask<Void, Void, Void> {

        Responses<Chat> responses;

        @Override
        protected Void doInBackground(Void... voids) {
            responses = Client.getInstance().read(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (responses.getErrno() == 1) {
                for (int i = responses.getRsm().size() - 1; i >= 0; i--) {
                    Chat chat = responses.getRsm().get(i);
                    TextMessage message = new TextMessage();
                    message.setText(chat.getMessage());
                    message.setDate(chat.getAdd_time() * 1000L);
                    message.setAvatarUrl(chat.getAvatar_file());
                    message.setUserId(String.valueOf(chat.getUid()));
                    if (chat.isLocal())
                        message.setSource(MessageSource.LOCAL_USER);
                    else
                        message.setSource(MessageSource.EXTERNAL_USER);
                    fragment.addNewMessage(message);
                }
            }
        }
    }
}
