package com.sine_x.material_wecenter;

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
public class Config {

    /**
     * 应用配置
     */

    // 网址
    public static String HOST_NAME = "http://bbs.zjut.edu.cn/";
    // 文本后缀
    public static String FIX = "<br><br>\"————来自精弘论坛安卓客户端\"";
    // 每页问题数量
    public static int PER_PAGE = 20;
    // 设置网络超时时间
    public static int TIME_OUT = 600;
    // 缓冲大小
    public static int MAX_LINE_BUFFER = 1024;

    /**
     * 警告！ 修改下面内容可能会导致应用无法使用
     */

    // API请求URL
    public static String LOGIN_PROCESS = HOST_NAME + "?/api/account/login_process/";
    public static String GET_USERINFO = HOST_NAME + "?/api/account/get_userinfo/";
    public static String GET_USER_ACTIONS = HOST_NAME + "?/api/people/user_actions/";
    public static String FOLLOW_PEOPLE = HOST_NAME + "?/follow/ajax/follow_people/";
    public static String PUSHLISH_QUESTION = HOST_NAME + "?/api/publish/publish_question/";
    public static String PUSHLISH_ANSWER = HOST_NAME + "api/publish/save_answer/";
    public static String PUSHLISH_ANSWER_COMMENT=HOST_NAME+"api/question/save_answer_comment/";
    public static String EXPLORE = HOST_NAME + "?/api/explore/";
    public static String QUESTION = HOST_NAME + "api/question/";
    public static String QUESTION_FOCUS=HOST_NAME+"question/ajax/focus/";
    public static String QUESTION_THANKS=HOST_NAME+"question/ajax/question_thanks/";
    public static String ANSWER=HOST_NAME+"api/question/answer/?answer_id=";
    public static String ANSWER_COMMENT=HOST_NAME+"api/question/answer_comments/?answer_id=";
    public static String ANSWER_RATE=HOST_NAME+"question/ajax/question_answer_rate/";
    public static String ANSWER_VOTE=HOST_NAME+"question/ajax/answer_vote/";
    public static String AVATAR_DIR = HOST_NAME + "uploads/avatar/";
    public static String HOME_DYNAMIC = HOST_NAME + "?/api/home/";

    //操作枚举
    public enum ActionType{
        QUESTION_FOCUS,
        QUESTION_THANKS,
        PUSHLISH_ANSWER_COMMENT,
        ANSWER_RATE,
        ANSWER_VOTE
    }
}
