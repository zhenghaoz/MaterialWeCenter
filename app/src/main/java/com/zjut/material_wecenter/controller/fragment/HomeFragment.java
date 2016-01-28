package com.zjut.material_wecenter.controller.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.adapter.DynamicViewAdapter;
import com.zjut.material_wecenter.models.Dynamic;
import com.zjut.material_wecenter.models.Result;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private ArrayList<Dynamic> mList;
    private DynamicViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Client client = Client.getInstance();

//    private boolean loading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                new LoadDynamicList().execute();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        // 实例化RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.dynamic_list);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 开始载入动态操作
        new LoadDynamicList().execute();
    }

    private class LoadDynamicList extends AsyncTask<Void, Void, Void> {

        private Result result;

        @Override
        protected Void doInBackground(Void... voids) {
            result = client.getDynamic();
            if (result != null && result.getErrno() == 1) {
                mList = (ArrayList<Dynamic>) result.getRsm();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mSwipeRefreshLayout.setRefreshing(false);
            if (mAdapter == null) {
                mAdapter = new DynamicViewAdapter(getActivity(), mList);
                mRecyclerView.setAdapter(mAdapter);
            } else
                mAdapter.notifyDataSetChanged();
        }
    }
}
