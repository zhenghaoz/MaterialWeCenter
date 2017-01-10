package com.sine_x.material_wecenter.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.models.LoginProcess;
import com.sine_x.material_wecenter.models.Response;
import com.sine_x.material_wecenter.models.Result2;

public class LaunchActivity extends AppCompatActivity {

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


    // 用户登录验证
    private class UserLoginTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Response<LoginProcess> response = Client.getInstance().loginProcess(uid, password);
            if (response.getErrno() != 1) { // 验证失败，要求用户重新输入登录信息
                Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {                        // 验证成功，进入应用程序
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return null;
        }
    }

}
