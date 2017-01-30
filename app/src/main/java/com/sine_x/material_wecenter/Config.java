package com.sine_x.material_wecenter;

public class Config {

    // 应用配置

    // 网站目录
    static String HOST_NAME = "http://wecenter.dev.hihwei.com/";
    // 每页问题数量
    public static int ITEM_PER_PAGE = 20;
    // 设置网络超时时间
    static int TIME_OUT = 1000;
    // 缓冲大小
    static int MAX_LINE_BUFFER = 1024;
    // API密钥
    static String APP_SECRET = "12884789df747d7affbcd6a7cadd9359";
    // API密钥开关
    static boolean KEEP_SECRET = true;

    // 警告！ 修改下面内容可能会导致应用无法使用

    static String API_ROOT = HOST_NAME + "?/api/";

    // API请求地址

    static String API_CAT_ACCOUNT = "account";
    static String API_LOGIN_PROCESS = "login_process";
    static String API_GET_USERINFO = "get_userinfo";

    static String API_CAT_PEOPLE = "people";
    static String API_USER_ACTIONS = "user_actions";

    static String API_CAT_HOME = "home";

    static String API_CAT_PUBLISH = "publish";
    static String API_PUBLISH_QUESTION = "publish_question";
    static String API_PUBLISH_ANSWER = "save_answer";

    static String API_CAT_QUESTION = "question";
    static String API_ANSWER = "answer";
    static String API_ANSWER_COMMENTS = "answer_comments";
    static String API_PUBLISH_ANSWER_COMMENT = "save_answer_comment";

    // AJAX请求地址

    static String AJAX_ANSWER_RATE = "question/ajax/question_answer_rate/";
    static String AJAX_ANSWER_VOTE = "question/ajax/answer_vote/";
    static String AJAX_QUESTION_FOCUS = "question/ajax/focus/";
    static String AJAX_QUESTION_THANKS = "question/ajax/question_thanks/";

    static String API_CAT_EXPLORE = "explore";

    //操作枚举
    public enum ActionType{
        QUESTION_FOCUS,
        QUESTION_THANKS,
        PUBLISH_ANSWER_COMMENT,
        ANSWER_RATE,
        ANSWER_VOTE
    }

    public static final String PRE_ACCOUNT = "account";
    public static final String PRE_UID = "uid";
    public static final String PRE_PASSWORD = "password";
    public static final String PRE_USER_NAME = "user_name";
    public static final String PRE_AVATAR_FILE = "avatar_file";

    public static final String INT_QUESTION_ID = "questionID";
}
