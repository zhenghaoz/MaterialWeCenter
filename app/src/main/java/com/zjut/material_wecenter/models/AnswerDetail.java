package com.zjut.material_wecenter.models;

/**
 * Created by Administrator on 2016/2/1.
 */
public class AnswerDetail {

    private AnswerEntity answer;

    public void setAnswer(AnswerEntity answer) {
        this.answer = answer;
    }

    public AnswerEntity getAnswer() {
        return answer;
    }

    public static class AnswerEntity {
        private int answer_id;
        private int question_id;
        private String answer_content;
        private long add_time;
        private int against_count;
        private int agree_count;
        private long uid;
        private int comment_count;
        private int uninterested_count;
        private int thanks_count;
        private int category_id;
        private int has_attach;
        private long ip;
        private int force_fold;
        private int anonymous;
        private String publish_source;
        private int user_vote_status;
        private int user_thanks_status;
        /**
         * uid : 3555555555555555556
         * user_name : 假装生活
         * signature : null
         * avatar_file : http://wecenter.dev.hihwei.com/static/common/avatar-mid-img.png
         */

        private UserInfoEntity user_info;
        private String question_content;

        public void setAnswer_id(int answer_id) {
            this.answer_id = answer_id;
        }

        public void setQuestion_id(int question_id) {
            this.question_id = question_id;
        }

        public void setAnswer_content(String answer_content) {
            this.answer_content = answer_content;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public void setAgainst_count(int against_count) {
            this.against_count = against_count;
        }

        public void setAgree_count(int agree_count) {
            this.agree_count = agree_count;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public void setUninterested_count(int uninterested_count) {
            this.uninterested_count = uninterested_count;
        }

        public void setThanks_count(int thanks_count) {
            this.thanks_count = thanks_count;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public void setHas_attach(int has_attach) {
            this.has_attach = has_attach;
        }

        public void setIp(long ip) {
            this.ip = ip;
        }

        public void setForce_fold(int force_fold) {
            this.force_fold = force_fold;
        }

        public void setAnonymous(int anonymous) {
            this.anonymous = anonymous;
        }

        public void setPublish_source(String publish_source) {
            this.publish_source = publish_source;
        }

        public void setUser_vote_status(int user_vote_status) {
            this.user_vote_status = user_vote_status;
        }

        public void setUser_thanks_status(int user_thanks_status) {
            this.user_thanks_status = user_thanks_status;
        }

        public void setUser_info(UserInfoEntity user_info) {
            this.user_info = user_info;
        }

        public void setQuestion_content(String question_content) {
            this.question_content = question_content;
        }

        public int getAnswer_id() {
            return answer_id;
        }

        public int getQuestion_id() {
            return question_id;
        }

        public String getAnswer_content() {
            return answer_content;
        }

        public long getAdd_time() {
            return add_time;
        }

        public int getAgainst_count() {
            return against_count;
        }

        public int getAgree_count() {
            return agree_count;
        }

        public long getUid() {
            return uid;
        }

        public int getComment_count() {
            return comment_count;
        }

        public int getUninterested_count() {
            return uninterested_count;
        }

        public int getThanks_count() {
            return thanks_count;
        }

        public int getCategory_id() {
            return category_id;
        }

        public int getHas_attach() {
            return has_attach;
        }

        public long getIp() {
            return ip;
        }

        public int getForce_fold() {
            return force_fold;
        }

        public int getAnonymous() {
            return anonymous;
        }

        public String getPublish_source() {
            return publish_source;
        }

        public int getUser_vote_status() {
            return user_vote_status;
        }

        public int getUser_thanks_status() {
            return user_thanks_status;
        }

        public UserInfoEntity getUser_info() {
            return user_info;
        }

        public String getQuestion_content() {
            return question_content;
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
    }
}
