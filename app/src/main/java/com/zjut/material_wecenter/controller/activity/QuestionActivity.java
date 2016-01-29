package com.zjut.material_wecenter.controller.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isFirstRefresh=true;
    private final int ScrollOffset = 4;
    private final int resultCode=1;
    private int questionID;
    private QuestionDetail questionDetail;
    private QuestionDetailAdapter questionDetailAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton fabAnswer;
    private FloatingActionButton fabThank;
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

        floatingActionMenu=(FloatingActionMenu) findViewById(R.id.fab_question_menu);
        fabAnswer=(FloatingActionButton) findViewById(R.id.fab_menu_item_comment);
        fabAnswer.setOnClickListener(this);
        fabThank=(FloatingActionButton) findViewById(R.id.fab_menu_item_thanks);
        fabThank.setOnClickListener(this);

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
        recyclerView.addItemDecoration(new ItemDivider(this,LinearLayoutManager.VERTICAL));
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > ScrollOffset) {
                    if (dy > 0) {
                        floatingActionMenu.hideMenu(true);
                    } else {
                        floatingActionMenu.showMenu(true);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new LoadAnswers().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_menu_item_comment:
                Intent intent=new Intent(this,PostAnswerActivity.class);
                intent.putExtra("questionID",questionID);
                intent.putExtra("questionTitle",questionDetail.getQuestion_info().getQuestion_content());
                startActivityForResult(intent,resultCode);
                break;
            case R.id.fab_menu_item_thanks:
                break;
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
            swipeRefreshLayout.setRefreshing(false);
            questionDetailAdapter=new QuestionDetailAdapter
                    (QuestionActivity.this,questionDetail);
            recyclerView.setAdapter(questionDetailAdapter);
            questionDetailAdapter.notifyDataSetChanged();
            if(isFirstRefresh){
                isFirstRefresh=false;
            }
            else Toast.makeText(QuestionActivity.this,"更新完成",Toast.LENGTH_SHORT).show();
        }
    }
}
