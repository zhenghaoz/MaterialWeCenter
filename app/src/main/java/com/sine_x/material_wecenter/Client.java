package com.sine_x.material_wecenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.sine_x.material_wecenter.models.Action;
import com.sine_x.material_wecenter.models.AnswerComment;
import com.sine_x.material_wecenter.models.AnswerDetail;
import com.sine_x.material_wecenter.models.Dynamic;
import com.sine_x.material_wecenter.models.LoginProcess;
import com.sine_x.material_wecenter.models.PublishAnswer;
import com.sine_x.material_wecenter.models.PublishQuestion;
import com.sine_x.material_wecenter.models.Question;
import com.sine_x.material_wecenter.models.QuestionDetail;
import com.sine_x.material_wecenter.models.Response;
import com.sine_x.material_wecenter.models.Responses;
import com.sine_x.material_wecenter.models.Result2;
import com.sine_x.material_wecenter.models.UserInfo;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public static int PUBLISH = 101;
    public static int ANSWER = 201;
    private static Client client;
    private String cooike;

    /**
     * 获得一个实例
     * @return Client对象
     */
    public static Client getInstance() {
        if (client == null)
            client = new Client();
        return client;
    }

    public @NonNull <T> Response<T> parseResponse(String json, Class<T> type) {
        Response<T> response = new Response<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setErrno(jsonObject.getInt("errno"));
            response.setErr(jsonObject.getString("err"));
            if (response.getErrno() == 1) {
                Gson gson = new Gson();
                response.setRsm(gson.fromJson(jsonObject.getJSONObject("rsm").toString(), type));
            } else {
                response.setRsm(null);
            }
        } catch (Exception e) {
            response.setErr("未知错误");
            response.setErrno(-1);
        }
        return response;
    }

    private <T> Responses<T> parseResponses(String json, @NonNull Class<T> classType) {
        Responses<T> responses = new Responses<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            responses.setErrno(jsonObject.getInt("errno"));
            responses.setErr(jsonObject.getString("err"));
            if (responses.getErrno() == 1) {
                ArrayList<T> list = new ArrayList<>();
                JSONObject rsm = jsonObject.getJSONObject("rsm");
                JSONArray array = rsm.getJSONArray("rows");
                int total_rows = rsm.getInt("total_rows");
                Gson gson = new Gson();
                for (int i = 0; i < total_rows; i++) {
                    JSONObject item = array.getJSONObject(i);
                    list.add(gson.fromJson(item.toString(), classType));
                }
                responses.setRsm(list);
            } else {
                responses.setRsm(null);
            }
        } catch (JSONException e) {
            responses.setRsm(null);
            responses.setErrno(-1);
            responses.setErr("未知错误");
        }
        return responses;
    }

    public String getSign(String apis) {
        if (Config.KEEP_SECRET) {
            String text = apis + Config.APP_SCRET;
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                String cipher = byteArrayToHex(md.digest(text.getBytes()));
                Log.d("sign", cipher);
                return cipher;
            } catch (NoSuchAlgorithmException e) {
                return "";
            }
        } else {
            return "";
        }
    }


    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    /**
     * loginProcess 用户登录
     * @param user_name 用户名
     * @param password 用户密码
     * @return 包含LoginProcess的Result对象
     */
    public Response<LoginProcess> loginProcess(@NonNull String user_name, @NonNull String password) {
        Map<String, String> params = new HashMap<>();
        params.put("user_name", user_name);
        params.put("password", password);
        String json = doPost(Config.APICAT_ACCOUNT, Config.API_LOGIN_PROCESS, params);
        return parseResponse(json, LoginProcess.class);
    }

    /**
     * getUserInfo 获取用户信息
     * @param uid 用户ID
     * @return 包含UserInfo的Result对象
     */
    public Response<UserInfo> getUserInfo(String uid) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        String json = doGet(Config.APICAT_ACCOUNT, Config.API_GET_USERINFO, params);
        return parseResponse(json, UserInfo.class);
    }

    /**
     * getUserActions获取用户回答或提问记录
     * @param uid 用户ID
     * @param actions 101-获取用户提问列表 201-获取用户回答列表
     * @return 包含Action对象数组的Result对象
     */
    public Responses<Action> getUserActions(String uid, int actions, int page) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("actions", String.valueOf(actions));
        params.put("page", String.valueOf(page));
        String json = doGet(Config.APICAT_PEOPLE, Config.API_USER_ACTIONS, params);
        return parseResponses(json, Action.class);
    }

    /**
     * explore 发现页面
     * @param page 页数
     * @return Result对象
     */
    public Responses<Question> explore(int page) {
        String json = doGet(Config.API_EXPLORE, "", new HashMap<String, String>());
        return parseResponses(json, Question.class);
    }

    /**
     * getQuestion 获取问题详情
     * @param questionID 问题的编号
     * @return Result对象
     */

    public Result2 getQuestion(int questionID){
        String url = Config.QUESTION + questionID;
        String json = doGet(url);
        return getResult(json, QuestionDetail.class);
    }

    /**
     * getAnswer 获取答案详情
     * @param answerID 答案的编号
     * @return Result对象
     */
    public Result2 getAnswer(int answerID){
        String url = Config.ANSWER + answerID;
        String json = doGet(url);
        return getResult(json, AnswerDetail.class);
    }

    /**
     * getAnswer 获取答案评论列表
     * @param answerID 答案的编号
     * @return Result对象
     */

    public Result2 getAnswerComments(int answerID){
        String url = Config.ANSWER_COMMENT + answerID;
        String json = doGet(url);
        return getResultArray(json, AnswerComment.class);
    }

    /**
     * getDynamic 首页动态（home）页面
     * @return Result对象
     */
    public Responses<Dynamic> getDynamic(int page) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        String json = doGet(Config.API_HOME, "", params);
        return parseResponses(json, Dynamic.class);
    }



    /**
     * publishQuestion 发起问题
     * @param content 问题的标题
     * @param detail 问题的内容
     * @param topics 问题的话题
     * @return 包含PublishQuestion对象的Result对象
     */
    public Result2 publishQuestion(String content, String detail, ArrayList<String> topics) {
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
     * publishAnswer 添加问题答案
     * @param questionID 问题编号
     * @param content 答案的内容
     * @return 包含PublishAnswer对象的Result对象
     */

    public Result2 publishAnswer(int questionID, String content){
        Map<String, String> params = new HashMap<>();
        params.put("question_id", questionID+"");
        params.put("answer_content", content);
        String json = doPost(Config.PUSHLISH_ANSWER, params);
        return getResult(json, PublishAnswer.class);
    }


    /**
     * postAction 对问题或答案进行感谢、点赞等动作
     * @param type 动作的类型
     * @param classType 类类型
     * @param strs 动作所需参的数列表
     * @return Result2（如果有错误，返回NULL）
     */

    public Result2 postAction(Config.ActionType type, @NonNull Class<? extends Object> classType, ArrayList<String> strs){

        Map<String, String> params = new HashMap<>();
        String json;
        if(type==Config.ActionType.QUESTION_FOCUS){
            params.put("question_id",strs.get(0));
            json=doPost(Config.QUESTION_FOCUS,params);
            return getResult(json,classType);
        }
        else if(type==Config.ActionType.QUESTION_THANKS){
            params.put("question_id",strs.get(0));
            json=doPost(Config.QUESTION_THANKS,params);
            return getResult(json,classType);
        }
        else if(type==Config.ActionType.PUSHLISH_ANSWER_COMMENT){
            params.put("answer_id",strs.get(0));
            params.put("message",strs.get(1));
            json=doPost(Config.PUSHLISH_ANSWER_COMMENT,params);
            return getResult(json,classType);
        }
        else if(type== Config.ActionType.ANSWER_VOTE){
            params.put("answer_id",strs.get(0));
            params.put("value",strs.get(1));
            json=doPost(Config.ANSWER_VOTE,params);
            return getResult(json,classType);
        }
        else if(type== Config.ActionType.ANSWER_RATE){
            params.put("type",strs.get(0));
            params.put("answer_id",strs.get(1));
            json=doPost(Config.ANSWER_RATE,params);
            return getResult(json,classType);
        }
        return null;
    }

    /**
     * getResult 将返回的JSON对象转换为Result类
     * @param json JSON字符串
     * @param classType 类类型
     * @return Result2（如果有错误，返回NULL）
     */
    private Result2 getResult(String json, @NonNull Class<? extends Object> classType) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            Result2 resualt = new Result2();
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
     * getResultArray 将返回的JSON数组转换为Result类
     * @param json JSON字符串
     * @param classType 类型
     * @return Result对象
     */

    private Result2 getResultArray(String json, @NonNull Class<? extends Object> classType){
        try {

            JSONObject jsonObject = new JSONObject(json);
            Result2 result2 = new Result2();
            result2.setErr(jsonObject.getString("err"));
            result2.setErrno(jsonObject.getInt("errno"));
            if (result2.getErrno() == 1) {
                ArrayList<Object> list = new ArrayList<>();
                JSONArray array = jsonObject.getJSONArray("rsm");
                int size=array.length();
                Gson gson = new Gson();
                for (int i=0;i<size;i++) {
                    JSONObject item = array.getJSONObject(i);
                    list.add(gson.fromJson(item.toString(), classType));
                }
                result2.setRsm(list);
            } else
                result2.setRsm(null);
            return result2;
        } catch (JSONException e) {
            return null;
        }
    }
    /**
     * getResults 将返回的JSON数组转换为Result类
     * @param json JSON字符串
     * @param classType 类型
     * @return Result对象
     */
    private Result2 getResults(String json, @NonNull Class<? extends Object> classType) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            Result2 result2 = new Result2();
            result2.setErr(jsonObject.getString("err"));
            result2.setErrno(jsonObject.getInt("errno"));
            if (result2.getErrno() == 1) {
                ArrayList<Object> list = new ArrayList<>();
                JSONObject rsm = jsonObject.getJSONObject("rsm");
                JSONArray array = rsm.getJSONArray("rows");
                int total_rows = rsm.getInt("total_rows");
                Gson gson = new Gson();
                for (int i = 0; i < total_rows; i++) {
                    JSONObject item = array.getJSONObject(i);
                    list.add(gson.fromJson(item.toString(), classType));
                }
                result2.setRsm(list);
            } else
                result2.setRsm(null);
            return result2;
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

    private String doPost(String apicat, String api, Map<String, String> params) {
        // 组合链接
        String apiUrl = Config.API_ROOT + apicat + '/' + api + '/';
        if (Config.KEEP_SECRET) {
            apiUrl += "&mobile_sign=" + getSign(apicat);
        }
        Log.d("POST", apiUrl);
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
            URL url = new URL(apiUrl);
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
            // 附上Cookie
            connection.setRequestProperty("Cookie", cooike);
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

    private String doGet(String apiCat, String api, Map<String, String> params) {
        try {
            // 组合链接
            StringBuilder builder = new StringBuilder();
            builder.append(Config.API_ROOT);
            builder.append(apiCat);
            builder.append('/');
            builder.append(api);
            builder.append("/?");
            if (Config.KEEP_SECRET) {
                params.put("mobile_sign", getSign(apiCat));
            }
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue()))
                        .append("&");
            }
            builder.deleteCharAt(builder.length() - 1);
            Log.d("GET", builder.toString());
            URL url = new URL(builder.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 附上Cookie
            connection.setRequestProperty("Cookie", cooike);
            InputStreamReader input = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(input);
            builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line);
            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
