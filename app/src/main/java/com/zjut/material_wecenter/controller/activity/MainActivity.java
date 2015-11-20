package com.zjut.material_wecenter.controller.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.fragment.ExploreFragment;

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements OnItemClickListener {

    private HelpLiveo mHelpLiveo;

    // Fragments
    private ExploreFragment exploreFragment;

    @Override
    public void onInt(Bundle bundle) {

        // Creating items navigation
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
}
