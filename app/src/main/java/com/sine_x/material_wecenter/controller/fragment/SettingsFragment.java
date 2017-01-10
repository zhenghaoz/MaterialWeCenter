package com.sine_x.material_wecenter.controller.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.View;

import com.sine_x.material_wecenter.BuildConfig;
import com.sine_x.material_wecenter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        Preference github = findPreference("github_repo");
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
