package com.zjut.material_wecenter.controller.activity;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.ClientYC;
import com.zjut.material_wecenter.R;

public class BindActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passWord;
    private Button bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        initView();
    }

    private void initView() {

        userName = (EditText) findViewById(R.id.editText_userName);
        passWord = (EditText) findViewById(R.id.editText_passWord);
        bind = (Button) findViewById(R.id.button_bind);
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_bind);
        setSupportActionBar(toolbar);
        setTitle("绑定");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

    }

    private void bind() {

        ClientYC.username = userName.getText().toString();
        ClientYC.password = passWord.getText().toString();

        Log.e("user", ClientYC.username);

        if(!ClientYC.username.isEmpty()){
            SharedPreferences sharedPreferences=getSharedPreferences("userInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", ClientYC.username);
            editor.putString("password",ClientYC.password );
            editor.apply();

            setResult(1);

            finish();
        }
        else {
            Toast.makeText(this,"请输入帐号密码！",Toast.LENGTH_SHORT).show();
        }

    }
}
