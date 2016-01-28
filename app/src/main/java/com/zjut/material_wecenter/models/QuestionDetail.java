package com.zjut.material_wecenter.models;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/27.
 */
public class QuestionDetail {

    private QuestionInfo question_info;
    private ArrayList<TopicInfo> question_topics;
    private ArrayList<AnswerInfo> answers;

    public QuestionInfo getQuestion_info() {
        return question_info;
    }

    public void setQuestion_info(QuestionInfo question_info) {
        this.question_info = question_info;
    }

    public ArrayList<TopicInfo> getQuestion_topics() {
        return question_topics;
    }

    public void setQuestion_topics(ArrayList<TopicInfo> question_topics) {
        this.question_topics = question_topics;
    }

    public ArrayList<AnswerInfo> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerInfo> answers) {
        this.answers = answers;
    }

    public static class QuestionInfo {

        private int question_id;
        private String question_content;
        private String question_detail;
        private int answer_count;
        private int view_count;
        private int focus_count;
        private int comment_count;
        private int thanks_count;
        private long add_time;
        private long update_time;
        private int agree_count;
        private int against_count;
        private int user_answered;
        private int user_thanks;
        private int user_follow_check;
        private int user_question_focus;
        private UserInfoEntity user_info;


        public int getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(int question_id) {
            this.question_id = question_id;
        }

        public String getQuestion_content() {
            return question_content;
        }

        public void setQuestion_content(String question_content) {
            this.question_content = question_content;
        }

        public int getUser_question_focus() {
            return user_question_focus;
        }

        public void setUser_question_focus(int user_question_focus) {
            this.user_question_focus = user_question_focus;
        }

        public int getUser_follow_check() {
            return user_follow_check;
        }

        public void setUser_follow_check(int user_follow_check) {
            this.user_follow_check = user_follow_check;
        }

        public String getQuestion_detail() {
            return question_detail;
        }

        public void setQuestion_detail(String question_detail) {
            this.question_detail = question_detail;
        }

        public int getAnswer_count() {
            return answer_count;
        }

        public void setAnswer_count(int answer_count) {
            this.answer_count = answer_count;
        }

        public int getView_count() {
            return view_count;
        }

        public void setView_count(int view_count) {
            this.view_count = view_count;
        }

        public int getFocus_count() {
            return focus_count;
        }

        public void setFocus_count(int focus_count) {
            this.focus_count = focus_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getThanks_count() {
            return thanks_count;
        }

        public void setThanks_count(int thanks_count) {
            this.thanks_count = thanks_count;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public int getAgree_count() {
            return agree_count;
        }

        public void setAgree_count(int agree_count) {
            this.agree_count = agree_count;
        }

        public int getAgainst_count() {
            return against_count;
        }

        public void setAgainst_count(int against_count) {
            this.against_count = against_count;
        }

        public int getUser_answered() {
            return user_answered;
        }

        public void setUser_answered(int user_answered) {
            this.user_answered = user_answered;
        }

        public int getUser_thanks() {
            return user_thanks;
        }

        public void setUser_thanks(int user_thanks) {
            this.user_thanks = user_thanks;
        }

        public UserInfoEntity getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoEntity user_info) {
            this.user_info = user_info;
        }

        public static class UserInfoEntity {
            private long uid;
            private String signature;
            private String user_name;
            private String avatar_file;

            public void setUid(long uid) {
                this.uid = uid;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
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

            public String getAvatar_file() {
                return avatar_file;
            }
        }
    }

    public static class TopicInfo{
        private int topic_id;
        private String topic_title;

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public String getTopic_title() {
            return topic_title;
        }

        public void setTopic_title(String topic_title) {
            this.topic_title = topic_title;
        }
    }

    public static class AnswerInfo {
        private int answer_id;
        private String answer_content;
        private int question_id;
        private String question_content;
        private long add_time;
        private int against_count;
        private int agree_count;
        private int thanks_count;
        private int comment_count;
        private String publish_source;
        private int user_thanks_status;
        private int user_vote_status;
        private UserInfoEntity user_info;

        public int getAnswer_id() {
            return answer_id;
        }

        public void setAnswer_id(int answer_id) {
            this.answer_id = answer_id;
        }

        public String getAnswer_content() {
            return answer_content;
        }

        public void setAnswer_content(String answer_content) {
            this.answer_content = answer_content;
        }

        public int getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(int question_id) {
            this.question_id = question_id;
        }

        public String getQuestion_content() {
            return question_content;
        }

        public void setQuestion_content(String question_content) {
            this.question_content = question_content;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public int getAgainst_count() {
            return against_count;
        }

        public void setAgainst_count(int against_count) {
            this.against_count = against_count;
        }

        public int getAgree_count() {
            return agree_count;
        }

        public void setAgree_count(int agree_count) {
            this.agree_count = agree_count;
        }

        public int getThanks_count() {
            return thanks_count;
        }

        public void setThanks_count(int thanks_count) {
            this.thanks_count = thanks_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getUser_thanks_status() {
            return user_thanks_status;
        }

        public void setUser_thanks_status(int user_thanks_status) {
            this.user_thanks_status = user_thanks_status;
        }

        public int getUser_vote_status() {
            return user_vote_status;
        }

        public void setUser_vote_status(int user_vote_status) {
            this.user_vote_status = user_vote_status;
        }

        public String getPublish_source() {
            return publish_source;
        }

        public void setPublish_source(String publish_source) {
            this.publish_source = publish_source;
        }

        public UserInfoEntity getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoEntity user_info) {
            this.user_info = user_info;
        }

        public static class UserInfoEntity {
            private long uid;
            private String signature;
            private String user_name;
            private String avatar_file;

            public void setUid(long uid) {
                this.uid = uid;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
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

            public String getAvatar_file() {
                return avatar_file;
            }
        }
    }


}
