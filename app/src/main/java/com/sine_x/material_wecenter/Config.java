package com.sine_x.material_wecenter;

public class Config {

    /**
     * 应用配置
     */

    // 网址
    public static String HOST_NAME = "http://wecenter.dev.hihwei.com/";
    // 文本后缀
    public static String FIX = "";
    // 每页问题数量
    public static int PER_PAGE = 20;
    // 设置网络超时时间
    public static int TIME_OUT = 600;
    // 缓冲大小
    public static int MAX_LINE_BUFFER = 1024;
    public static String APP_SCRET = "12884789df747d7affbcd6a7cadd9359";
    public static boolean KEEP_SECRET = true;

    /**
     * 警告！ 修改下面内容可能会导致应用无法使用
     */

    public static String API_ROOT = HOST_NAME + "?/api/";

    // API请求URL
    static String APICAT_ACCOUNT = "account";
    static String API_LOGIN_PROCESS = "login_process";
    static String API_GET_USERINFO = "get_userinfo";

    static String APICAT_PEOPLE = "people";
    static String API_USER_ACTIONS = "user_actions";

    public static String GET_USER_ACTIONS = HOST_NAME + "?/api/people/user_actions/";
    public static String FOLLOW_PEOPLE = HOST_NAME + "?/follow/ajax/follow_people/";
    public static String PUSHLISH_QUESTION = HOST_NAME + "?/api/publish/publish_question/";
    public static String PUSHLISH_ANSWER = HOST_NAME + "api/publish/save_answer/";
    public static String PUSHLISH_ANSWER_COMMENT=HOST_NAME+"api/question/save_answer_comment/";
    public static String API_EXPLORE = "explore";
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
