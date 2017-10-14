package com.sine_x.material_wecenter.controller.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.activity.PostActivity;
import com.sine_x.material_wecenter.controller.adapter.ExploreViewAdapter;
import com.sine_x.material_wecenter.models.ExploreItem;
import com.sine_x.material_wecenter.models.Responses;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExploreFragment extends Fragment {

    private final int ScrollOffset = 4;
    private boolean loading = true;
    private List<ExploreItem> mList = new ArrayList<>();
    private ExploreViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    @BindView(R.id.button_publish)
    FloatingActionButton btnPublish;

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
        ButterKnife.bind(this, view);
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
        mAdapter = new ExploreViewAdapter(getActivity(), mList);
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

    @OnClick(R.id.button_publish)
    public void publish() {
        // 点击发起问题按钮
        Intent intent = new Intent(getActivity(), PostActivity.class);
        startActivityForResult(intent, POST_ACTIVITY);
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

        private Responses<ExploreItem> result2;

        @Override
        protected Integer doInBackground(Void... params) {
            result2 = Client.getInstance().explore(page);
            if (result2.getErrno() == 1) {
                if (page == 1)  // 首页
                    mList.clear();
                mList.addAll(result2.getRsm());
            }
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
