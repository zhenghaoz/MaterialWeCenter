package com.zjut.material_wecenter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.zjut.material_wecenter.models.Result;
import com.zjut.material_wecenter.models.LoginProcess;
import com.zjut.material_wecenter.models.PublishQuestion;
import com.zjut.material_wecenter.models.Question;
import com.zjut.material_wecenter.models.UserInfo;

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
public class Client {

    private String cooike;
    private static Client client;

    /**
     * 获得一个实例
     * @return Client对象
     */
    public static Client getInstance() {
        if (client == null)
            client = new Client();
        return client;
    }

    /**
     * loginProcess 用户登录
     * @param user_name 用户名
     * @param password 用户密码
     * @return 包含LoginProcess的Result对象
     */
    public Result loginProcess(String user_name, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("user_name", user_name);
        params.put("password", password);
        String json = doPost(Config.LOGIN_PROCESS, params);
        return getResult(json, LoginProcess.class);
    }

    /**
     * getUserInfo 获取用户信息
     * @param uid 用户ID
     * @return 包含UserInfo的Result对象
     */
    public Result getUserInfo(String uid) {
        String json = doGet(Config.GET_USERINFO + "?uid=" + uid);
        return getResult(json, UserInfo.class);
    }

    /**
     * explore 发现页面
     * @param page 页数
     * @return Result对象
     */
    public Result explore(int page) {
        String url = Config.EXPLORE + "?page=" + String.valueOf(page) + "&per_page=" + String.valueOf(Config.PER_PAGE);
        String json = doGet(url);
        return getResults(json, Question.class);
    }

    /**
     * publishQuestion 发起问题
     * @param content 问题的标题
     * @param detail 问题的内容
     * @param topics 问题的话题
     * @return 包含PublishQuestion对象的Result对象
     */
    public Result publishQuestion(String content, String detail, ArrayList<String> topics) {
        Map<String, String> params = new HashMap<>();
        params.put("question_content", content);
        params.put("question_detail", detail);
        // 生成话题列表
        StringBuilder topic = new StringBuilder();
        if (!topics.isEmpty()) {
            topic.append(topics.get(0));
            for (int i = 1; i < topics.size(); i++)
                topic.append(',').append(topics.get(i));
        }
        params.put("topics", topics.toString());
        String json = doPost(Config.PUSHLISH_QUESTION, params);
        return getResult(json, PublishQuestion.class);
    }

    /**
     * getResult 将返回的JSON对象转换为Result类
     * @param json JSON字符串
     * @param classType 类类型
     * @return Result（如果有错误，返回NULL）
     */
    private Result getResult(String json, @NonNull Class<? extends Object> classType) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            Result resualt = new Result();
            resualt.setErr(jsonObject.getString("err"));
            resualt.setErrno(jsonObject.getInt("errno"));
            if (resualt.getErrno() == 1) {
                Gson gson = new Gson();
                resualt.setRsm(gson.fromJson(jsonObject.getJSONObject("rsm").toString(), classType));
            } else
                resualt.setRsm(null);
            return resualt;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * getResults 将返回的JSON数组转换为Result类
     * @param json JSON字符串
     * @param classType 类型
     * @return Result对象
     */
    private Result getResults(String json,  @NonNull Class<? extends Object> classType) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            Result result = new Result();
            result.setErr(jsonObject.getString("err"));
            result.setErrno(jsonObject.getInt("errno"));
            if (result.getErrno() == 1) {
                ArrayList<Object> list = new ArrayList<>();
                JSONObject rsm = jsonObject.getJSONObject("rsm");
                JSONArray array = rsm.getJSONArray("rows");
                int total_rows = rsm.getInt("total_rows");
                Gson gson = new Gson();
                for (int i = 0; i < total_rows; i++) {
                    JSONObject item = array.getJSONObject(i);
                    list.add(gson.fromJson(item.toString(), classType));
                }
                result.setRsm(list);
            } else
                result.setRsm(null);
            return result;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * doPost 发送POST请求
     * @param URL 请求的URL
     * @param params 请求中的参数
     * @return 字符串（如果发生错误，那么返回NULL）
     */
    private String doPost(String URL, Map<String, String> params) {
        // 建立请求内容
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.append(entry.getKey())
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue()))
                    .append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        byte[] data = builder.toString().getBytes();
        // 发出请求
        try {
            URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Config.TIME_OUT);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            // 附上Cookie
            connection.setRequestProperty("Cookie", cooike);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(data.length));
            // 发送请求内容
            OutputStream output = connection.getOutputStream();
            output.write(data);
            // 接收返回信息
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                // 保存Cookie
                Map<String, List<String>> header = connection.getHeaderFields();
                List<String> cookies = header.get("Set-Cookie");
                if (cookies.size() == 3)
                    cooike = cookies.get(2);
                // 处理返回的字符串流
                InputStream input = connection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[Config.MAX_LINE_BUFFER];
                int len = 0;
                while ((len = input.read(buffer)) != -1)
                    byteArrayOutputStream.write(buffer, 0, len);
                return new String(byteArrayOutputStream.toByteArray());
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    /**
     * doGet 发送GET请求
     * @param URL 请求的URL地址
     * @return 字符串（如果发生错误，那么返回NULL）
     */
    private String doGet(String URL) {
        try {
            URL url = new URL(URL);
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
