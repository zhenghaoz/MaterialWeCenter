package com.sine_x.material_wecenter.controller.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.adapter.SearchViewAdapter;
import com.sine_x.material_wecenter.models.Responses;
import com.sine_x.material_wecenter.models.SearchResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private List<SearchResult> mList = new ArrayList<>();
    private SearchViewAdapter mAdapter;
    private String q;
    private DrawerLayout drawerLayout;

    @BindView(R.id.floating_search_view)
    FloatingSearchView searchView;
    @BindView(R.id.search_list)
    RecyclerView mRecyclerView;

    public SearchFragment() {
        // Required empty public constructor
    }


    public void setDrawer(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mAdapter = new SearchViewAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                q = newQuery;
                new SearchTask().execute();
            }
        });
        searchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
            @Override
            public void onMenuOpened() {

            }

            @Override
            public void onMenuClosed() {

            }
        });
        if (drawerLayout != null)
            searchView.attachNavigationDrawerToMenuButton(drawerLayout);
    }

    class SearchTask extends AsyncTask<Void, Void, Void> {

        Responses<SearchResult> responses;

        @Override
        protected Void doInBackground(Void... voids) {
            responses = Client.getInstance().search(q);
            if (responses.getErrno() == 1) {
                mList.clear();
                mList.addAll(responses.getRsm());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter.notifyDataSetChanged();
        }
    }
}
