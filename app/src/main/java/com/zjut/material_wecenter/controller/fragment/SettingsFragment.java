package com.zjut.material_wecenter.controller.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.zjut.material_wecenter.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        getActivity().setTheme(R.style.nLiveo_Theme_Light);
    }
}
