package com.sine_x.material_wecenter.controller.fragment;

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
import com.sine_x.material_wecenter.Client;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.models.Response;
import com.sine_x.material_wecenter.models.UserInfo;

public class UserInfoFragment extends Fragment {

    private ObservableScrollView mScrollView;

    private TextView fans, agree, thanks, signature, city;

    private long uid;

    public static UserInfoFragment newInstance(long uid) {
        Bundle args = new Bundle();
        args.putLong("uid", uid);
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        uid = args.getLong("uid");
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
        city = (TextView) view.findViewById(R.id.city);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
        new LoadUserInfo().execute();
    }

    /**
     * 获取用户信息的异步任务
     */
    class LoadUserInfo extends AsyncTask<Void, Void, Void> {

        Response<UserInfo> response;

        @Override
        protected Void doInBackground(Void... params) {
            Client client = Client.getInstance();
            response = client.getUserInfo(uid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (response.getRsm() != null) {
                // 显示用户个人信息
                UserInfo info = response.getRsm();
                fans.setText(String.valueOf(info.getFans_count()));
                agree.setText(String.valueOf(info.getAgree_count()));
                thanks.setText(String.valueOf(info.getThanks_count()));
                signature.setText(info.getSignature());
                String pc = info.getProvince() + info.getCity();
                city.setText(pc);
            }
        }
    }
}
