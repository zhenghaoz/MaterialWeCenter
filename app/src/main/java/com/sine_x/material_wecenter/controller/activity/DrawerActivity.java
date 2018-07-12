package com.sine_x.material_wecenter.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sine_x.material_wecenter.Config;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.fragment.ExploreFragment;
import com.sine_x.material_wecenter.controller.fragment.HomeFragment;
import com.sine_x.material_wecenter.controller.fragment.InboxFragment;
import com.sine_x.material_wecenter.controller.fragment.SearchFragment;
import com.sine_x.material_wecenter.controller.fragment.SettingsFragment;
import com.sine_x.material_wecenter.controller.fragment.TopicFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int uid;
    private String user_name;
    private String avatar_file;

    // 子页面
    private ExploreFragment exploreFragment;
    private HomeFragment homeFragment;
    private TopicFragment topicFragment;
    private InboxFragment inboxFragment;
    private SearchFragment searchFragment;
    private SettingsFragment settingsFragment;

    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView textViewTitle = navigationView.getHeaderView(0).findViewById(R.id.textView);
        TextView textViewEmail = navigationView.getHeaderView(0).findViewById(R.id.textViewEmail);
        ImageView imageViewAvatar = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, UserActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });

        // 验证用户保存的登录信息
        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        uid = preferences.getInt(Config.PRE_UID, -1);
        user_name = preferences.getString(Config.PRE_USER_NAME, "");
        avatar_file = preferences.getString(Config.PRE_AVATAR_FILE, "");
        String email = preferences.getString(Config.PRE_EMAIL, "");
        // 加载用户头像/用户名
        if (!avatar_file.isEmpty())
            Picasso.with(this).load(avatar_file).into(imageViewAvatar);
        textViewTitle.setText(user_name);
        textViewEmail.setText(email);

        // 加载动态页面
        homeFragment = new HomeFragment();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.add(R.id.container, homeFragment);
        tx.commit();
        setTitle(R.string.dynamic);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 隐藏所有的Fragment
        if (exploreFragment != null)
            transaction.hide(exploreFragment);
        if (homeFragment != null)
            transaction.hide(homeFragment);
        if (topicFragment != null)
            transaction.hide(topicFragment);
        if (inboxFragment != null)
            transaction.hide(inboxFragment);
        if (searchFragment != null)
            transaction.hide(searchFragment);
        if (settingsFragment != null)
            transaction.hide(settingsFragment);
        if (getSupportActionBar() != null)
            getSupportActionBar().show();
        if (id == R.id.nav_home) {
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
                transaction.add(R.id.container, homeFragment);
            } else {
                transaction.show(homeFragment);
            }
            setTitle(R.string.dynamic);
        } else if (id == R.id.nav_explore) {
            if (exploreFragment == null) {
                exploreFragment = new ExploreFragment();
                transaction.add(R.id.container, exploreFragment);
            } else {
                transaction.show(exploreFragment);
            }
            setTitle(R.string.explore);
        } else if (id == R.id.nav_topic) {
            if (topicFragment == null) {
                topicFragment = new TopicFragment();
                transaction.add(R.id.container, topicFragment);
            } else {
                transaction.show(topicFragment);
            }
            setTitle(R.string.topic);
        } else if (id == R.id.nav_message) {
            if (inboxFragment == null) {
                inboxFragment = new InboxFragment();
                transaction.add(R.id.container, inboxFragment);
            } else {
                transaction.show(inboxFragment);
            }
            setTitle(R.string.inbox);
        } else if (id == R.id.nav_search) {
            if (searchFragment == null) {
                searchFragment = new SearchFragment();
                searchFragment.setDrawer(drawer);
                transaction.add(R.id.container, searchFragment);
            } else {
                transaction.show(searchFragment);
            }
            setTitle(R.string.search);
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();
        } else if (id == R.id.nav_settings) {
            if (settingsFragment == null) {
                settingsFragment = new SettingsFragment();
                transaction.add(R.id.container, settingsFragment);
            } else {
                transaction.show(settingsFragment);
            }
            setTitle(getString(R.string.settings));
        } else if (id == R.id.nav_sign_out) {
            // 删除用户信息
            SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(Config.PRE_UID, -1);
            editor.putString(Config.PRE_USER_NAME, "");
            editor.putString(Config.PRE_PASSWORD, "");
            editor.putString(Config.PRE_AVATAR_FILE, "");
            editor.apply();
            // 退出主页
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
