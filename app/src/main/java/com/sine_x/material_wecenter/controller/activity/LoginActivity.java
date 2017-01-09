package com.sine_x.material_wecenter.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nispok.snackbar.Snackbar;
import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.models.Result;
import com.sine_x.material_wecenter.models.LoginProcess;
import com.sine_x.material_wecenter.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editUsername;
    private EditText editPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 实例化控件
        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        btnLogin = (Button) findViewById(R.id.button_login);
        btnLogin.setOnClickListener(this);
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
            // 从输入框获取用户名和密码
            user_name = editUsername.getText().toString();
            password = editPassword.getText().toString();
        }
        // 验证登录信息
        @Override
        protected Void doInBackground(Void... params) {
            response = Client.getInstance().loginProcess(user_name, password);
            if (response != null && response.getErrno() == 1) {
                // 保存用户名和密码
                SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("uid", user_name);
                editor.putString("password", password);
                editor.putString("user_name", ((LoginProcess)response.getRsm()).getUser_name());
                editor.putString("avatar_file", ((LoginProcess) response.getRsm()).getAvatar_file());
                editor.apply();
                // 加载主页
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
            // 显示登陆错误原因
            String errmsg;
            if (response == null)
                errmsg = "登录时发生错误";
            else
                errmsg = response.getErr();
            Snackbar.with(getApplicationContext())
                    .text(errmsg)
                    .show(LoginActivity.this);
        }
    }
}
