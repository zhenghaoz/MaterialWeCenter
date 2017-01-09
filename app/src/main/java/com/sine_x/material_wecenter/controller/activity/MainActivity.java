package com.sine_x.material_wecenter.controller.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.sine_x.material_wecenter.R;
import com.sine_x.material_wecenter.controller.fragment.ExploreFragment;
import com.sine_x.material_wecenter.controller.fragment.HomeFragment;
import com.sine_x.material_wecenter.controller.fragment.QueryFragment;
import com.sine_x.material_wecenter.controller.fragment.SettingsFragment;

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements OnItemClickListener {

    private HelpLiveo mHelpLiveo;

    private String uid;
    private String user_name;
    private String avatar_file;

    // Fragments
    private ExploreFragment exploreFragment;
    private HomeFragment homeFragment;
    private SettingsFragment settingsFragment;


    @Override
    public void onInt(Bundle bundle) {

        // 验证用户保存的登录信息
        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        uid = preferences.getString("uid", "");
        user_name = preferences.getString("user_name", "");
        avatar_file = preferences.getString("avatar_file", "");

        // 创建主菜单
        this.userName.setText(user_name);
        this.userEmail.setText(uid);
        this.userBackground.setImageResource(R.drawable.background);
        if (!avatar_file.isEmpty())
            Picasso.with(this).load(avatar_file).into(this.userPhoto);
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add(getString(R.string.dynamic), R.mipmap.ic_notifications_on_grey600_48dp);
        mHelpLiveo.add(getString(R.string.explore), R.mipmap.ic_explore_grey600_48dp);
        mHelpLiveo.addSeparator();
        mHelpLiveo.add(getString(R.string.settings), R.mipmap.ic_settings_grey600_48dp);
        with(this).startingPosition(1)
                .addAllHelpItem(mHelpLiveo.getHelp())
                .colorNameSubHeader(R.color.nliveo_blue_colorPrimary)
                .colorItemSelected(R.color.nliveo_blue_colorPrimary)
                .setOnClickUser(onClickPhoto)
                .build();

    }

    // 点击用户头像，查看用户信息
    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            intent.putExtra("uid", uid);
            startActivity(intent);
        }
    };

    @Override
    public void onItemClick(int i) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // 隐藏所有的Fragment
        if (exploreFragment != null)
            transaction.hide(exploreFragment);
        if (homeFragment != null)
            transaction.hide(homeFragment);
        if (settingsFragment != null)
            transaction.hide(settingsFragment);
        // 显示被选中的Fragment
        switch (i) {
            case 0:     // 动态
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.container, homeFragment);
                } else
                    transaction.show(homeFragment);
                setTitle(R.string.dynamic);
                break;
            case 1:     // 发现
                if (exploreFragment == null) {
                    exploreFragment = new ExploreFragment();
                    transaction.add(R.id.container, exploreFragment);
                } else
                    transaction.show(exploreFragment);
                setTitle(R.string.explore);
                break;
            case 3:
                if (settingsFragment == null) {
                    settingsFragment = new SettingsFragment();
                    transaction.add(R.id.container, settingsFragment);
                } else
                    transaction.show(settingsFragment);
                setTitle("设置");
        }
        transaction.commit();
    }
}
