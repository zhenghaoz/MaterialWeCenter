package com.zjut.material_wecenter;

import android.util.Log;

import com.google.gson.Gson;
import com.zjut.material_wecenter.models.LoginProcess;
import com.zjut.material_wecenter.models.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2015 Jinghong Union of ZJUT
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
public class Client {

    private String cooike;
    private static Client client;

    public static Client getInstance() {
        if (client == null)
            client = new Client();
        return client;
    }

    public LoginProcess LoginProcess(String user_name, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("user_name", user_name);
        params.put("password", password);
        String json = doPost(Config.LOGIN_PROCESS, params);
        if (json == null)
            return null;
        Gson gson = new Gson();
        return gson.fromJson(json, LoginProcess.class);
    }

    public ArrayList<Question> explore(int page) {
        String url = Config.EXPLORE + "?page=" + String.valueOf(page) + "&per_page=" + String.valueOf(Config.PER_PAGE);
        String json = doGet(url);
        ArrayList<Question> list = new ArrayList<>();
        Gson gson = new Gson();
        if (json == null)
            return null;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.getInt("errno") != 1)
                return null;
            JSONObject rsm = obj.getJSONObject("rsm");
            JSONArray array = rsm.getJSONArray("rows");
            int total_rows = rsm.getInt("total_rows");
            for (int i = 0; i < total_rows; i++) {
                JSONObject item = array.getJSONObject(i);
                list.add(gson.fromJson(item.toString(), Question.class));
            }
        } catch (JSONException e) {
            return null;
        }
        return list;
    }

    /*
     * doPost - given uri and params, return string. Return null if there is any exception
     */
    private String doPost(String uri, Map<String, String> params) {
        String result = null;

        // build request content
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.append(entry.getKey())
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue()))
                    .append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        byte[] data = builder.toString().getBytes();

        // connection
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Config.TIME_OUT);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(data.length));

            OutputStream output = connection.getOutputStream();
            output.write(data);

            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream input = connection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[Config.MAX_LINE_BUFFER];
                int len = 0;
                while ((len = input.read(buffer)) != -1)
                    byteArrayOutputStream.write(buffer, 0, len);
                result = new String(byteArrayOutputStream.toByteArray());
            }
        } catch (IOException e) {
            return null;
        }
        return result;
    }

    /*
     * doGet - given url, return string. Return null if there is any exception
     */
    private String doGet(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStreamReader input = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(input);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line);
            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
