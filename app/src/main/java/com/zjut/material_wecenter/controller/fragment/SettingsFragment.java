package com.zjut.material_wecenter.controller.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;


import com.zjut.material_wecenter.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings);
    }
}
