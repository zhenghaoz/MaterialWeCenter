package com.sine_x.material_wecenter.controller.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.sine_x.material_wecenter.controller.adapter.DynamicViewAdapter;
import com.sine_x.material_wecenter.models.Dynamic;
import com.sine_x.material_wecenter.models.Responses;
import com.sine_x.material_wecenter.models.Result2;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private List<Dynamic> mList;
    private DynamicViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Client client = Client.getInstance();

    //初始化一定处于刷新状态
    private boolean loading = true;
    //记录当前已经加载的页数
    private int page = 0;

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
                page = 0;
                new LoadDynamicList().execute();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        // 实例化RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.dynamic_list);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if (!loading) {
                    //当前不在加载，才进行新的加载
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        // 达到底部加载更多

                        //设置flag，标记当前正在刷新(加载）
                        loading = true;
                        mSwipeRefreshLayout.setRefreshing(true);
                        new LoadDynamicList().execute();
                    }
                }
            }
        });
        // 开始载入动态操作
        new LoadDynamicList().execute();
    }

    private class LoadDynamicList extends AsyncTask<Void, Void, Void> {

        private Responses<Dynamic> responses;

        @Override
        protected Void doInBackground(Void... voids) {
            responses = client.getDynamic(page);
            if (responses.getErrno() == 1) {
                List<Dynamic> rsm = responses.getRsm();
                if (rsm.size() == 0) {
                    // TODO: 2016/1/29 add toasts or something else to tell users that "there is no more dynamics".
                    Log.d("homefragment", "no more dynamics");
                } else if (page == 0) {
                    mList = rsm;
                } else {
                    mList.addAll(rsm);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mSwipeRefreshLayout.setRefreshing(false);
            if (page == 0) {
                mAdapter = new DynamicViewAdapter(getActivity(), mList);
                mRecyclerView.setAdapter(mAdapter);
            } else
                mAdapter.notifyDataSetChanged();

            //加载完成，更新flag
            page ++;
            loading = false;
        }
    }
}
