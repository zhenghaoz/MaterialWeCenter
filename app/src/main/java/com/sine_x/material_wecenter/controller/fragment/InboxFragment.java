package com.sine_x.material_wecenter.controller.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.adapter.ConversationViewAdapter;
import com.sine_x.material_wecenter.controller.adapter.TopicViewAdapter;
import com.sine_x.material_wecenter.models.Conversation;
import com.sine_x.material_wecenter.models.Responses;
import com.sine_x.material_wecenter.models.Topic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InboxFragment extends Fragment {

    private List<Conversation> mList = new ArrayList<>();
    private ConversationViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.dynamic_list) RecyclerView mRecyclerView;

    private static int POST_ACTIVITY = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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
                new LoadConversationList().execute();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        // 实例化RecyclerView
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ConversationViewAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 开始载入问题操作
        new LoadConversationList().execute();
    }

    /**
     * 加载话题页面的异步任务
     */
    private class LoadConversationList extends AsyncTask<Void, Integer, Integer> {

        private Responses<Conversation> responses;

        @Override
        protected Integer doInBackground(Void... params) {
            responses = Client.getInstance().inbox();
            mList.clear();
            mList.addAll(responses.getRsm());
            Log.d("Topics",String.valueOf(mList.size()));
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
