package com.zjut.material_wecenter.models;

/**
 * Created by windness on 2016/1/27.
 */
public class Dynamic {


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
        private long uid;
        private String user_name;
        private String signature;
        private String avatar_file;

        public void setUid(long uid) {
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

        public long getUid() {
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
