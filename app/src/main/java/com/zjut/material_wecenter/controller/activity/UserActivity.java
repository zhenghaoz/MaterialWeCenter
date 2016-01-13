package com.zjut.material_wecenter.controller.activity;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.squareup.picasso.Picasso;
import com.zjut.material_wecenter.Config;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.fragment.RecyclerViewFragment;
import com.zjut.material_wecenter.controller.fragment.ScrollFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {

    private MaterialViewPager mViewPager;

    private Toolbar toolbar;
    private String uid;
    private String user_name;
    private String avatar_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // 获取用户名和头像
        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        uid = preferences.getString("uid", "");
        user_name = preferences.getString("user_name", "");
        avatar_file = preferences.getString("avatar_file", "");
        // 清除原来的变体
        setTitle(user_name + "的主页");
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
                        return ScrollFragment.newInstance(uid);
                    //case 1:
                    //    return RecyclerViewFragment.newInstance();
                    //case 2:
                    //    return WebViewFragment.newInstance();
                    default:
                        return RecyclerViewFragment.newInstance();
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
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.nliveo_blue_colorPrimary,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.nliveo_blue_colorPrimary,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.nliveo_blue_colorPrimary,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        CircleImageView logo = (CircleImageView) findViewById(R.id.logo_white);
        Picasso.with(this).load(Config.AVATAR_DIR + avatar_file).into(logo);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
    }

}
