package com.zjut.material_wecenter.controller.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.adapter.QuestionDetailAdapter;
import com.zjut.material_wecenter.models.QuestionDetail;
import com.zjut.material_wecenter.ui.ItemDivider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean isFirstRefresh=true;
    private final int ScrollOffset = 4;
    private int resultCode=0;
    private int questionID;
    private QuestionDetail questionDetail;
    private QuestionDetailAdapter questionDetailAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Client client = Client.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_questionDetail);
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        Intent mIntent=getIntent();
        questionID=mIntent.getIntExtra("questionID", -1);

        floatingActionButton=(FloatingActionButton) findViewById(R.id.fab_question_menu);
        floatingActionButton.setOnClickListener(this);

        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_question);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        TypedValue typed_value = new TypedValue();
        getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize,
                typed_value, true);
        swipeRefreshLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize
                (typed_value.resourceId));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉刷新
                new LoadAnswers().execute();
            }
        });

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView_answerList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
      //  recyclerView.addItemDecoration(new ItemDivider(this,LinearLayoutManager.VERTICAL,ItemDivider.TITLE_INDEX));
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > ScrollOffset) {
                    if (dy > 0) {
                        floatingActionButton.hide(true);
                    } else {
                        floatingActionButton.show(true);
                    }
                }
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        new LoadAnswers().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_question, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch (id){
            case R.id.action_toTop:
                recyclerView.smoothScrollToPosition(0);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
           new LoadAnswers().execute();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.fab_question_menu:
                intent=new Intent(this,QuestionMenuActivity.class);
                intent.putExtra("questionID",questionID);
                intent.putExtra("questionTitle",questionDetail.getQuestion_info().getQuestion_content());
                intent.putExtra("isFocus",questionDetail.getQuestion_info().getUser_question_focus());
                intent.putExtra("isThanks",questionDetail.getQuestion_info().getUser_thanks());
                intent.putExtra("isAllowToAnswer",questionDetail.getQuestion_info().getUser_answered());
                startActivityForResult(intent,resultCode);
            default:
                break;
        }
    }

    private class LoadAnswers extends AsyncTask<Integer,Integer,Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                Log.e("Load", "load has started");
                questionDetail=(QuestionDetail) client.getQuestion(questionID).getRsm();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            questionDetailAdapter=new QuestionDetailAdapter
                    (QuestionActivity.this,questionDetail);
            recyclerView.setAdapter(questionDetailAdapter);
            questionDetailAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            if(isFirstRefresh){
                isFirstRefresh=false;
            }
            else Toast.makeText(QuestionActivity.this,"更新完成",Toast.LENGTH_SHORT).show();
        }
    }

}
