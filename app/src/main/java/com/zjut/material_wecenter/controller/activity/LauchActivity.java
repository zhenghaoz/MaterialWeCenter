package com.zjut.material_wecenter.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.models.Result;

public class LauchActivity extends AppCompatActivity {

    private String uid;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lauch);

        // 验证用户保存的登录信息
        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        uid = preferences.getString("uid", "");
        password = preferences.getString("password", "");
        new UserLoginTask().execute();
    }

    /**
     * 用户登录验证
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Result result = Client.getInstance().loginProcess(uid, password);
            // 验证失败，要求用户重新输入登录信息
            if (result == null || result.getErrno() != 1) {
                Intent intent = new Intent(LauchActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {    // 验证成功，进入应用程序
                Intent intent = new Intent(LauchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return null;
        }
    }

}
