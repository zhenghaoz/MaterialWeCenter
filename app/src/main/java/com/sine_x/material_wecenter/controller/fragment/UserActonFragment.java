package com.sine_x.material_wecenter.controller.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.adapter.ActionViewAdapter;
import com.sine_x.material_wecenter.models.Action;
import com.sine_x.material_wecenter.models.Responses;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActonFragment extends Fragment {

    public static int PUBLISH = 101;
    public static int ANSWER = 201;
    private boolean loading = true;

    private long uid;
    private int actions;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Action> mContentItems = new ArrayList<>();
    private int pageNum = 1;   // 当前页面的页码

    public static UserActonFragment newInstance(long uid, int actions) {
        Bundle args = new Bundle();
        args.putLong("uid", uid);
        args.putInt("actions", actions);
        UserActonFragment fragment = new UserActonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        uid = args.getLong("uid");
        actions = args.getInt("actions");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewMaterialAdapter(new ActionViewAdapter(getActivity(), mContentItems, actions));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        // 达到底部加载更多
                        loading = false;
                        new LoadActions().execute();
                    }
                }
            }
        });
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
        new LoadActions().execute();
    }

    class LoadActions extends AsyncTask<Void, Void, Void> {

        private Responses<Action> response;

        @Override
        protected Void doInBackground(Void... params) {
            response = Client.getInstance().getUserActions(uid, actions, pageNum);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (response.getErrno() == 1) {
                mContentItems.addAll(response.getRsm());
                mAdapter.notifyDataSetChanged();
                pageNum++;
                loading = true;
            }
        }
    }
}
