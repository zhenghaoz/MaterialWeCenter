package com.zjut.material_wecenter.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.zjut.material_wecenter.Client;
import com.zjut.material_wecenter.Config;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.fragment.ExploreFragment;
import com.zjut.material_wecenter.models.LoginProcess;

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements OnItemClickListener {

    private HelpLiveo mHelpLiveo;

    private String uid;
    private String user_name;
    private String password;
    private String avatar_file;

    // Fragments
    private ExploreFragment exploreFragment;

    @Override
    public void onInt(Bundle bundle) {

        // Read shared preference
        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        uid = preferences.getString("uid", "");
        user_name = preferences.getString("user_name", "");
        password = preferences.getString("password", "");
        avatar_file = preferences.getString("avatar_file", "");
        new UserLoginTask().execute();

        // Creating items navigation
        this.userName.setText(user_name);
        this.userEmail.setText(uid);
        Picasso.with(this).load(Config.AVATAR_DIR + avatar_file).into(this.userPhoto);
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add(getString(R.string.dynamic), R.mipmap.ic_notifications_on_grey600_48dp);
        mHelpLiveo.add(getString(R.string.explore), R.mipmap.ic_explore_grey600_48dp);

        with(this).startingPosition(0)
                .addAllHelpItem(mHelpLiveo.getHelp())
                .colorNameSubHeader(R.color.nliveo_blue_colorPrimary)
                .colorItemSelected(R.color.nliveo_blue_colorPrimary)
                .setOnClickUser(onClickPhoto)
                .setOnClickFooter(onClickFooter)
                .build();

    }

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
        }
    };

    @Override
    public void onItemClick(int i) {
        // Get manager
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Hide all fragments
        if (exploreFragment != null)
            transaction.hide(exploreFragment);
        switch (i) {
            case 1: // Explore
                if (exploreFragment == null) {
                    exploreFragment = new ExploreFragment();
                    transaction.add(R.id.container, exploreFragment);
                } else
                    transaction.show(exploreFragment);
                setTitle(R.string.explore);
        }
        transaction.commit();
    }

    private class UserLoginTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            LoginProcess response = Client.LoginProcess(uid, password);
            if (response == null || response.getErrno() != 1) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            return null;
        }
    }
}
