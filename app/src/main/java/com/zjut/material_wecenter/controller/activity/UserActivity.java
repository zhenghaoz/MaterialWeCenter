package com.zjut.material_wecenter.controller.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.squareup.picasso.Picasso;
import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.fragment.RecyclerViewFragment;
import com.zjut.material_wecenter.controller.fragment.UserInfoFragment;
import com.zjut.material_wecenter.models.Result;
import com.zjut.material_wecenter.models.UserInfo;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {

    private MaterialViewPager mViewPager;

    private Toolbar toolbar;
    private CircleImageView logo;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // 获取用户ID
        uid = getIntent().getStringExtra("uid");
        setTitle("");
        // 实例化控件
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        // 初始化工具栏
        toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }


        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return UserInfoFragment.newInstance(uid);
                    case 1:
                        return RecyclerViewFragment.newInstance(uid, RecyclerViewFragment.PUBLISH);
                    default:
                        return RecyclerViewFragment.newInstance(uid, RecyclerViewFragment.ANSWER);
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "个人资料";
                    case 1:
                        return "提问";
                    case 2:
                        return "回答";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                return HeaderDesign.fromColorResAndUrl(
                        R.color.nliveo_blue_colorPrimary,
                        "file:///android_asset/background.jpg");
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        logo = (CircleImageView) findViewById(R.id.logo_white);

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
                setTitle(info.getUser_name() + "的主页");
                Picasso.with(UserActivity.this).load(info.getAvatar_file()).into(logo);
            }
        }
    }

}
