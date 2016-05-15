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
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;

import com.zjut.material_wecenter.BuildConfig;
import com.zjut.material_wecenter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SettingsFragment extends PreferenceFragment {

    private static String UPDATE_SOURCE = "https://api.github.com/repos/ZhangZhenghao/Material-WeCenter/releases/latest";

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
        // 检查更新
        new CheckForUpdate().execute();
    }

    // 检查更新
    class CheckForUpdate extends AsyncTask<Void, Void, Void> {
        String result;
        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .header("Accept", "application/vnd.github.v3+json")
                    .url(UPDATE_SOURCE)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch (IOException e) {
                result = "error";
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Preference checkForUpdate = findPreference("update");
            if (result.equals("error")) {
                checkForUpdate.setTitle("检查更新出错");
            } else {
                Log.v("UPDATE", result);
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray assets = object.getJSONArray("assets");
                    final String link = assets.getJSONObject(0).getString("browser_download_url");
                    String version = object.getString("tag_name");
                    String body = object.getString("body");
                    if (version.compareTo(BuildConfig.VERSION_NAME) > 0) {
                        checkForUpdate.setTitle("发现新版本:" + version);
                        checkForUpdate.setSummary(body);
                        checkForUpdate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                            @Override
                            public boolean onPreferenceClick(Preference preference) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(link));
                                startActivity(intent);
                                return false;
                            }
                        });
                    }
                } catch (JSONException e) {
                    checkForUpdate.setTitle("检查更新出错");
                }
            }
        }
    }
}
