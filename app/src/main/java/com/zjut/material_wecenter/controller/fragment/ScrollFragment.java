package com.zjut.material_wecenter.controller.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.models.Result;
import com.zjut.material_wecenter.models.UserInfo;

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
public class ScrollFragment extends Fragment {

    private ObservableScrollView mScrollView;

    private TextView fans, agree, thanks, signature;

    private String uid;

    public static ScrollFragment newInstance(String uid) {
        Bundle args = new Bundle();
        args.putString("uid", uid);
        ScrollFragment fragment = new ScrollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        uid = args.getString("uid");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scroll, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        fans = (TextView) view.findViewById(R.id.fans);
        agree = (TextView) view.findViewById(R.id.agree);
        thanks = (TextView) view.findViewById(R.id.thanks);
        signature = (TextView) view.findViewById(R.id.signature);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
        new LoadUserInfo().execute();
    }

    /**
     * 获取用户信息的异步任务
     */
    class LoadUserInfo extends AsyncTask<Void, Void, Void> {
        Result result;
        @Override
        protected Void doInBackground(Void... params) {
            Client client = Client.getInstance();
            result = client.getUserInfo(uid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (result != null && result.getRsm() != null) {
                // 显示用户个人信息
                UserInfo info = (UserInfo) result.getRsm();
                fans.setText(String.valueOf(info.getFans_count()));
                agree.setText(String.valueOf(info.getAgree_count()));
                thanks.setText(String.valueOf(info.getThanks_count()));
                signature.setText(info.getSignature());
            }
        }
    }
}
