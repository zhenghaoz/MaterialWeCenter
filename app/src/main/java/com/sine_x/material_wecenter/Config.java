package com.sine_x.material_wecenter;

public class Config {

    // 应用配置

    // 网站目录
    public static String HOST_NAME = "https://wecenter.sine-x.com/";
    // 每页问题数量
    public static final int ITEM_PER_PAGE = 20;
    // 设置网络超时时间
    static final int TIME_OUT = 1000;
    // 缓冲大小
    static final int MAX_LINE_BUFFER = 1024;
    // API密钥
    static final String APP_SECRET = "12884789df747d7affbcd6a7cadd9359";
    // API密钥开关
    static final boolean KEEP_SECRET = false;
    // 默认头像
    public static final String DEFAULT_AVATAR = HOST_NAME + "static/common/avatar-max-img.png";
    // 消息刷新周期
    public static final long INBOX_REFRESH_PERIOD = 10000L;

    public static final int MAX_LENGTH = 100;

    // 警告！ 修改下面内容可能会导致应用无法使用

    static final String API_ROOT = HOST_NAME + "?/api/";

    // API请求地址

    static final String API_CAT_ACCOUNT = "account";
    static final String API_LOGIN_PROCESS = "login_process";
    static final String API_GET_USERINFO = "get_userinfo";
    static final String API_REGISTER_PROCESS = "register_process";

    static final String API_CAT_PEOPLE = "people";
    static final String API_USER_ACTIONS = "user_actions";

    static final String API_CAT_HOME = "home";

    static final String API_CAT_PUBLISH = "publish";
    static final String API_PUBLISH_QUESTION = "publish_question";
    static final String API_PUBLISH_ANSWER = "save_answer";

    static final String API_CAT_QUESTION = "question";
    static final String API_ANSWER = "answer";
    static final String API_ANSWER_COMMENTS = "answer_comments";
    static final String API_PUBLISH_ANSWER_COMMENT = "save_answer_comment";

    static final String API_CAT_ARTICLE = "article";
    static final String API_SAVE_COMMENT = "save_comment";

    static final String API_CAT_TOPIC = "topic";
    static final String API_HOT_TOPICS = "hot_topics";
    static final String API_POSTS = "posts";

    static final String API_CAT_INBOX = "inbox";
    static final String API_READ = "read";
    static final String API_SEND = "send";

    static final String API_CAT_SEARCH = "search";

    // AJAX请求地址

    static final String AJAX_ANSWER_RATE = "?/question/ajax/question_answer_rate/";
    static final String AJAX_ANSWER_VOTE = "?/question/ajax/answer_vote/";
    static final String AJAX_QUESTION_FOCUS = "?/question/ajax/focus/";
    static final String AJAX_QUESTION_THANKS = "?/question/ajax/question_thanks/";
    static final String AJAX_ARTICLE_VOTE = "?/article/ajax/article_vote/";
    static final String AJAX_SAVE_COMMENT = "?/article/ajax/save_comment/";
    static final String AJAX_FOLLOW_PEOPLE = "?/follow/ajax/follow_people/";

    static final String API_CAT_EXPLORE = "explore";

    //操作枚举
    public enum ActionType {
        QUESTION_FOCUS,
        QUESTION_THANKS,
        PUBLISH_ANSWER_COMMENT,
        ANSWER_RATE,
        ANSWER_VOTE,
        ARTICLE_VOTE
    }

    public static final String PRE_ACCOUNT = "account";
    public static final String PRE_UID = "uid";
    public static final String PRE_PASSWORD = "password";
    public static final String PRE_USER_NAME = "user_name";
    public static final String PRE_AVATAR_FILE = "avatar_file";

    public static final String INT_QUESTION_ID = "questionID";
    public static final String INT_QUESTION_TITLE = "questionTitle";
    public static final String INT_ARTICLE_ID = "articleID";
    public static final String INT_ARTICLE_TITLE = "articleTitle";
    public static final String INT_CHAT_USERNAME = "chatUsername";
    public static final String INT_CHAT_ID = "chatID";
    public static final String INT_TOPIC_NAME = "topicName";
    public static final String INT_TOPIC_ID = "topicID";
}
