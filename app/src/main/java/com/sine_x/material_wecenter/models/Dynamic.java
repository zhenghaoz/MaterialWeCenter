package com.sine_x.material_wecenter.models;

public class Dynamic {

    public static final int ADD_QUESTION = 101;         // 添加问题
    public static final int MOD_QUESTON_TITLE = 102;    // 修改问题标题
    public static final int MOD_QUESTION_DESCRI = 103;  // 修改问题描述
    public static final int ADD_REQUESTION_FOCUS = 105; // 添加问题关注
    public static final int REDIRECT_QUESTION = 107;    // 问题重定向
    public static final int MOD_QUESTION_CATEGORY = 108;// 修改问题分类
    public static final int MOD_QUESTION_ATTACH = 109;  // 修改问题附件
    public static final int DEL_REDIRECT_QUESTION = 110;// 删除问题重定向

    public static final int ANSWER_QUESTION = 201;      // 回复问题
    public static final int ADD_AGREE = 204;            // 赞同答案
    public static final int ADD_USEFUL = 206;           // 感谢作者
    public static final int ADD_UNUSEFUL = 207;         // 问题没有帮助

    public static final int ADD_TOPIC = 401;            // 创建话题
    public static final int MOD_TOPIC = 402;            // 修改话题
    public static final int MOD_TOPIC_DESCRI = 403;     // 修改话题描述
    public static final int MOD_TOPIC_PIC = 404;        // 修改话题图片
    public static final int DELETE_TOPIC = 405;         // 删除话题
    public static final int ADD_TOPIC_FOCUS = 406;      // 添加话题关注
    public static final int ADD_RELATED_TOPIC = 410;    // 添加相关话题
    public static final int DELETE_RELATED_TOPIC = 411; // 删除相关话题

    public static final int ADD_ARTICLE = 501;          // 添加文章
    public static final int ADD_AGREE_ARTICLE = 502;    // 赞同文章
    public static final int ADD_COMMENT_ARTICLE = 503;  // 评论文章


    /**
     * history_id : 50034
     * associate_action : 201
     * add_time : 1453863313
     * user_info : {"uid":201319630201,"user_name":"丶然后先森","signature":"做论坛的苦逼背锅员","avatar_file":"http://bbs.zjut.edu.cn/uploads/avatar/2013/1963/02/01_avatar_mid.jpg"}
     * answer_info : {"answer_id":13612,"answer_content":"王老菊","add_time":1453863313,"against_count":0,"agree_count":0}
     * question_info : {"question_id":4489,"question_content":"我被催眠了，你要不要试试","add_time":1453820724,"update_time":1453863313,"answer_count":2,"agree_count":0}
     */

    private int history_id;
    private int associate_action;
    private int add_time;
    /**
     * uid : 201319630201
     * user_name : 丶然后先森
     * signature : 做论坛的苦逼背锅员
     * avatar_file : http://bbs.zjut.edu.cn/uploads/avatar/2013/1963/02/01_avatar_mid.jpg
     */

    private UserInfoEntity user_info;

    /**
     * answer_id : 13612
     * answer_content : 王老菊
     * <p>
     * add_time : 1453863313
     * against_count : 0
     * agree_count : 0
     */

    private AnswerInfoEntity answer_info;

    /**
     * question_id : 4489
     * question_content : 我被催眠了，你要不要试试
     * add_time : 1453820724
     * update_time : 1453863313
     * answer_count : 2
     * agree_count : 0
     */

    private ArticleInfoEntity article_info;

    private CommentInfoEntity comment_info;
    private QuestionInfoEntity question_info;

    public ArticleInfoEntity getArticle_info() {
        return article_info;
    }

    public void setArticle_info(ArticleInfoEntity article_info) {
        this.article_info = article_info;
    }

    public CommentInfoEntity getComment_info() {
        return comment_info;
    }

    public void setComment_info(CommentInfoEntity comment_info) {
        this.comment_info = comment_info;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public void setAssociate_action(int associate_action) {
        this.associate_action = associate_action;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public void setUser_info(UserInfoEntity user_info) {
        this.user_info = user_info;
    }

    public void setAnswer_info(AnswerInfoEntity answer_info) {
        this.answer_info = answer_info;
    }

    public void setQuestion_info(QuestionInfoEntity question_info) {
        this.question_info = question_info;
    }

    public int getHistory_id() {
        return history_id;
    }

    public int getAssociate_action() {
        return associate_action;
    }

    public int getAdd_time() {
        return add_time;
    }

    public UserInfoEntity getUser_info() {
        return user_info;
    }

    public AnswerInfoEntity getAnswer_info() {
        return answer_info;
    }

    public QuestionInfoEntity getQuestion_info() {
        return question_info;
    }

    public static class UserInfoEntity {
        private int uid;
        private String user_name;
        private String signature;
        private String avatar_file;

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public void setAvatar_file(String avatar_file) {
            this.avatar_file = avatar_file;
        }

        public int getUid() {
            return uid;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getSignature() {
            return signature;
        }

        public String getAvatar_file() {
            return avatar_file;
        }
    }

    public static class AnswerInfoEntity {
        private int answer_id;
        private String answer_content;
        private int add_time;
        private int against_count;
        private int agree_count;

        public void setAnswer_id(int answer_id) {
            this.answer_id = answer_id;
        }

        public void setAnswer_content(String answer_content) {
            this.answer_content = answer_content;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public void setAgainst_count(int against_count) {
            this.against_count = against_count;
        }

        public void setAgree_count(int agree_count) {
            this.agree_count = agree_count;
        }

        public int getAnswer_id() {
            return answer_id;
        }

        public String getAnswer_content() {
            return answer_content;
        }

        public int getAdd_time() {
            return add_time;
        }

        public int getAgainst_count() {
            return against_count;
        }

        public int getAgree_count() {
            return agree_count;
        }
    }

    public static class QuestionInfoEntity {
        private int question_id;
        private String question_content;
        private int add_time;
        private int update_time;
        private int answer_count;
        private int agree_count;

        public void setQuestion_id(int question_id) {
            this.question_id = question_id;
        }

        public void setQuestion_content(String question_content) {
            this.question_content = question_content;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public void setAnswer_count(int answer_count) {
            this.answer_count = answer_count;
        }

        public void setAgree_count(int agree_count) {
            this.agree_count = agree_count;
        }

        public int getQuestion_id() {
            return question_id;
        }

        public String getQuestion_content() {
            return question_content;
        }

        public int getAdd_time() {
            return add_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public int getAnswer_count() {
            return answer_count;
        }

        public int getAgree_count() {
            return agree_count;
        }
    }

    public static class ArticleInfoEntity {
        private int id;
        private String title;
        private String message;
        private int comments;
        private int views;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        private int add_time;
    }

    public static class CommentInfoEntity {
        private int id;
        private String message;
        private int add_time;
        private int votes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getVotes() {
            return votes;
        }

        public void setVotes(int votes) {
            this.votes = votes;
        }
    }
}
