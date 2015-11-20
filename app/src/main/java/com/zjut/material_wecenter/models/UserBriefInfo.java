package com.zjut.material_wecenter.models;


public class UserBriefInfo {

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
