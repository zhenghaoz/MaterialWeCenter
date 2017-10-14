package com.sine_x.material_wecenter.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.models.LoginProcess;
import com.sine_x.material_wecenter.models.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.button_login)
    TextView buttonLogin;
    @BindView(R.id.button_sign_up)
    Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_login)
    void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.button_sign_up)
    void signUp() {
        new SignUpTask().execute();
    }

    public class SignUpTask extends AsyncTask<Void, Void, Void> {
        String userName, password, email;
        Response<LoginProcess> response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            userName = editUsername.getText().toString();
            password = editPassword.getText().toString();
            email = editEmail.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            response = Client.getInstance().registerProcess(userName, password, email, "");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (response.getErrno() == 1) {
                // 保存用户名和密码
                SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(Config.PRE_UID, response.getRsm().getUid());
                editor.putString(Config.PRE_PASSWORD, password);
                editor.putString(Config.PRE_USER_NAME, response.getRsm().getUser_name());
                editor.putString(Config.PRE_AVATAR_FILE, Config.DEFAULT_AVATAR);
                editor.apply();
                // 加载主页
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                com.nispok.snackbar.Snackbar.with(getApplicationContext())
                        .text(response.getErr())
                        .show(SignUpActivity.this);
            }
        }
    }
}
