package com.zjut.material_wecenter.controller.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zjut.material_wecenter.ClientYC;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.adapter.ScoresAdapter;
import com.zjut.material_wecenter.models.Score;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Score score;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent=getIntent();
        value=intent.getStringExtra("value");

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView_scores);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_score);
        setSupportActionBar(toolbar);
        setTitle("成绩查询");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        new LoadScores().execute();
    }

    private class LoadScores extends AsyncTask<Integer,Integer,Integer> {

        String json;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {

            Log.e("Load", "Starting");
            json= ClientYC.doGet("http://api.zjut.com/student/scores.php?username="+ClientYC.username+
                    "&password="+ClientYC.password+"&term="+value);
            return null;

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Gson gson=new Gson();
            boolean isGet=false;
            try {
                String status;
                JSONObject object=new JSONObject(json);
                status = object.getString("status");
                JSONArray msg=object.getJSONArray("msg");
                if(status.equals("success"))
                    isGet=true;

            } catch (JSONException e) {
                e.printStackTrace();

            }

            if(isGet){
                score=gson.fromJson(json,Score.class);
                recyclerView.setAdapter(new ScoresAdapter(ScoreActivity.this, score));
            }
            else {
                Toast.makeText(ScoreActivity.this,"帐号、密码或学期错误",Toast.LENGTH_LONG).show();
            }
        }
    }
}
