package com.zjut.material_wecenter.controller.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.activity.PostActivity;
import com.zjut.material_wecenter.controller.adapter.QuestionViewAdapter;
import com.zjut.material_wecenter.models.Question;
import com.zjut.material_wecenter.models.Result;

import java.util.ArrayList;

/**
 * Copyright (C) 2016 Jinghong Union of ZJUT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ExploreFragment extends Fragment implements View.OnClickListener {

    private final int ScrollOffset = 4;
    private boolean loading = true;
    private ArrayList<Question> mList = new ArrayList<>();
    private QuestionViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton btnPublish;

    private static int POST_ACTIVITY = 1;

    private int page = 1;   // 当前页面的页码

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 实例化发布问题按钮
        btnPublish = (FloatingActionButton)view.findViewById(R.id.button_publish);
        btnPublish.setOnClickListener(this);
        // 实例化刷新布局
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        TypedValue typed_value = new TypedValue();
        getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉刷新
                page = 1;
                new LoadQuestionList().execute();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        // 实例化RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.question_list);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new QuestionViewAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        // 达到底部加载更多
                        loading = false;
                        mSwipeRefreshLayout.setRefreshing(true);
                        new LoadQuestionList().execute();
                    }
                }
                // 自动隐藏发布按钮
                if (Math.abs(dy) > ScrollOffset)
                    if (dy > 0)
                        btnPublish.hide(true);
                    else
                        btnPublish.show(true);
            }
        });
        // 开始载入问题操作
        new LoadQuestionList().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击发起问题按钮
            case R.id.button_publish:
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivityForResult(intent, POST_ACTIVITY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 发布问题后，需要刷新发现页面
        if (requestCode == POST_ACTIVITY) {
            page = 1;
            new LoadQuestionList().execute();
        }
    }

    /**
     * 加载发现页面的异步任务
     */
    private class LoadQuestionList extends AsyncTask<Void, Integer, Integer> {

        private Result result;

        @Override
        protected Integer doInBackground(Void... params) {
            result = Client.getInstance().explore(page);
            if (result != null && result.getErrno() == 1)
                if (page == 1)  // 首页
                    mList.clear();
                mList.addAll((ArrayList<Question>) result.getRsm());
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mSwipeRefreshLayout.setRefreshing(false);
            mAdapter.notifyDataSetChanged();
            page++;
            loading = true;
        }
    }
}
