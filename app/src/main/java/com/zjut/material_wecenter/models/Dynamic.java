package com.zjut.material_wecenter.models;

/**
 * Created by windness on 2016/1/27.
 */
public class Dynamic {

    /**
     * history_id : 48684
     * associate_action : 101
     * add_time : 1452927267
     * user_info : {"uid":1,"user_name":"精弘网络","signature":"","avatar_file":"http://bbs.zjut.edu.cn/uploads/avatar/1///1_avatar_mid.jpg"}
     * question_info : {"question_id":4371,"question_content":"1月14曰上午，我校一大三男生不幸去世，公安部门己于第一时间介入，死亡原因正在调查中。学校老师和同学们感到很悲痛，希望大家珍爱生命，每一位同学都身心健康，学习进步。","add_time":1452927267,"update_time":1453359295,"answer_count":13,"agree_count":7}
     */

    private int history_id;
    private int associate_action;
    private int add_time;
    /**
     * uid : 1
     * user_name : 精弘网络
     * signature :
     * avatar_file : http://bbs.zjut.edu.cn/uploads/avatar/1///1_avatar_mid.jpg
     */

    private UserInfoEntity user_info;
    /**
     * question_id : 4371
     * question_content : 1月14曰上午，我校一大三男生不幸去世，公安部门己于第一时间介入，死亡原因正在调查中。学校老师和同学们感到很悲痛，希望大家珍爱生命，每一位同学都身心健康，学习进步。
     * add_time : 1452927267
     * update_time : 1453359295
     * answer_count : 13
     * agree_count : 7
     */

    private QuestionInfoEntity question_info;

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
}
