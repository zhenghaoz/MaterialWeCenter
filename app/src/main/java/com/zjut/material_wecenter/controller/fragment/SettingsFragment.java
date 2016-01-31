package com.zjut.material_wecenter.controller.fragment;

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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;

import com.zjut.material_wecenter.BuildConfig;
import com.zjut.material_wecenter.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定配置
        addPreferencesFromResource(R.xml.settings);
        // 修改样式
        getActivity().setTheme(R.style.nLiveo_Theme_Light);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 显示版本号
        Preference version = findPreference("version");
        version.setSummary(BuildConfig.VERSION_NAME);
        // 打开GitHub项目地址
        Preference github = findPreference("github");
        github.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.github_url)));
                startActivity(intent);
                return false;
            }
        });
    }
}
