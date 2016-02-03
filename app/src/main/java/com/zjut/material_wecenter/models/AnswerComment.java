package com.zjut.material_wecenter.models;

/**
 * Created by Administrator on 2016/2/1.
 */
public class AnswerComment {

    private int id;
    private int answer_id;
    private long uid;
    private String message;
    private long time;

    private UserInfoEntity user_info;

    public void setId(int id) {
        this.id = id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setUser_info(UserInfoEntity user_info) {
        this.user_info = user_info;
    }

    public int getId() {
        return id;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public long getUid() {
        return uid;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    public UserInfoEntity getUser_info() {
        return user_info;
    }

    public static class UserInfoEntity {
        private long uid;
        private String user_name;
        private String avatar_file;

        public void setUid(long uid) {
            this.uid = uid;
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
