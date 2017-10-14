package com.sine_x.material_wecenter.controller.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.adapter.ConversationViewAdapter;
import com.sine_x.material_wecenter.models.Conversation;
import com.sine_x.material_wecenter.models.Responses;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InboxFragment extends Fragment {

    private List<Conversation> mList = new ArrayList<>();
    private ConversationViewAdapter mAdapter;
    @BindView(R.id.dynamic_list)
    RecyclerView mRecyclerView;

    private static int POST_ACTIVITY = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inbox, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        // 实例化刷新布局
        TypedValue typed_value = new TypedValue();
        getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
        // 实例化RecyclerView
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ConversationViewAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 开始载入问题操作
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            new LoadConversationList().execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, Config.INBOX_REFRESH_PERIOD); //execute in every 50000 ms
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
            Log.d("Topics", String.valueOf(mList.size()));
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mAdapter.notifyDataSetChanged();
        }
    }
}
