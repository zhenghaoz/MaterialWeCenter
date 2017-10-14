package com.sine_x.material_wecenter.controller.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.adapter.ExploreViewAdapter;
import com.sine_x.material_wecenter.models.ExploreItem;
import com.sine_x.material_wecenter.models.Responses;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicActivity extends AppCompatActivity {

    List<ExploreItem> mList = new ArrayList<>();
    ExploreViewAdapter mAdapter;
    @BindView(R.id.post_list)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);
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

        Intent intent = getIntent();
        setTitle(intent.getStringExtra(Config.INT_TOPIC_NAME));
        int id = intent.getIntExtra(Config.INT_TOPIC_ID, -1);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ExploreViewAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        new LoadPostsTask(id).execute();
    }

    class LoadPostsTask extends AsyncTask<Void, Void, Void> {

        int topicID;
        Responses<ExploreItem> responses;

        LoadPostsTask(int id) {
            topicID = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            responses = Client.getInstance().posts(topicID);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (responses.getErrno() == 1) {
                mList.clear();
                mList.addAll(responses.getRsm());
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
