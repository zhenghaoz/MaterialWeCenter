package com.zjut.material_wecenter;

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
public class Config {

    // Host name
    public static String HOST_NAME = "http://bbs.zjut.edu.cn/";
    // Account API URI
    public static String LOGIN_PROCESS = HOST_NAME + "?/api/account/login_process/";
    public static String AVATAR_UPLOAD = HOST_NAME + "?/api/account/avatar_upload/";
    public static String GET_USERINFO = HOST_NAME + "?/api/account/get_userinfo/";
    public static String USER = HOST_NAME + "api/user.php";
    public static String PROFILE = HOST_NAME + "api/profile.php";
    public static String PROFILE_SETTING = HOST_NAME + "api/profile_setting.php";
    public static String FOLLOW_PEOPLE = HOST_NAME + "?/follow/ajax/follow_people/";
    public static String GET_AVATARS = HOST_NAME + "?/api/account/get_avatars/";
    public static String PUSHLISH_QUESTION = HOST_NAME + "?/api/publish/publish_question/";

    public static String EXPLORE = HOST_NAME + "?/api/explore/";

    public static String AVATAR_DIR = HOST_NAME + "uploads/avatar/";

    public static int PER_PAGE = 20;

    // connection
    public static int TIME_OUT = 600;

    // application
    public static int MAX_LINE_BUFFER = 1024;

    public static String FIX = "\"<br><br>————来自精弘论坛安卓客户端\"";
}
