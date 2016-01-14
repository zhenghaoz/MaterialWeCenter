package com.zjut.material_wecenter.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.Config;
import com.zjut.material_wecenter.models.Result;
import com.zjut.material_wecenter.models.LoginProcess;
import com.zjut.material_wecenter.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Client client = Client.getInstance();
    private EditText editUsername;
    private EditText editPassword;
    private Button btnLogin;
    private TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 实例化控件
        appName = (TextView) findViewById(R.id.app_name);
        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        btnLogin = (Button) findViewById(R.id.button_login);
        btnLogin.setOnClickListener(this);
        // 显示应用名称
        appName.setText(Config.APP_NAME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击用户登录按钮
            case R.id.button_login:
                new UserLoginTask().execute();
        }
    }

    /**
     * 用户登录验证异步任务
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Void> {
        private String user_name;
        private String password;
        private Result response;
        // 获取用户输入的用户名和密码
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            user_name = editUsername.getText().toString();
            password = editPassword.getText().toString();
        }
        // 验证登录信息
        @Override
        protected Void doInBackground(Void... params) {
            response = client.loginProcess(user_name, password);
            if (response != null && response.getErrno() == 1) {
                // Save username and password
                SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("uid", user_name);
                editor.putString("password", password);
                editor.putString("user_name", ((LoginProcess)response.getRsm()).getUser_name());
                editor.putString("avatar_file", ((LoginProcess) response.getRsm()).getAvatar_file());
                editor.apply();
                // Load main activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return null;
        }
        // 通知用户登录结果
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String snacktext;
            if (response == null)
                snacktext = "登录时发生错误";
            else
                snacktext = (String) response.getErr();
            Snackbar.with(getApplicationContext())
                    .text(snacktext)
                    .show(LoginActivity.this);
        }
    }
}
