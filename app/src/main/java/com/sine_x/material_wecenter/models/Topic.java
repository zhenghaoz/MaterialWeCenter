package com.sine_x.material_wecenter.models;


public class Topic {

    /**
     * topic_id : 2
     * topic_title : HLTV
     * add_time : 1507697438
     * discuss_count : 2
     * topic_description : HLTV是Half-Life TV的简写，由Valve在2002年创造，使得让使用半条命引擎的游戏比赛可以在互联网上进行广播和转播，目前最多观众数量纪录是在CPL2004年比赛上的一场比赛有39500人观看。反恐精英游戏是使用HLTV最广泛的游戏，这项技术可以大大减少带宽的使用，从而实现很多观众在很窄的带宽下观看同一场比赛。
     * topic_pic : 20171012/07c44d410de9b60cab0e7d9ece561c90_32_32.png
     * topic_lock : 0
     * focus_count : 1
     * user_related : 1
     * url_token : null
     * merged_id : 0
     * seo_title : null
     * parent_id : 0
     * is_parent : 0
     * discuss_count_last_week : 2
     * discuss_count_last_month : 2
     * discuss_count_update : 1507699519
     */

    private int topic_id;
    private String topic_title;
    private int add_time;
    private int discuss_count;
    private String topic_description;
    private String topic_pic;
    private int topic_lock;
    private int focus_count;
    private int user_related;
    private Object url_token;
    private int merged_id;
    private Object seo_title;
    private int parent_id;
    private int is_parent;
    private int discuss_count_last_week;
    private int discuss_count_last_month;
    private int discuss_count_update;

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

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public int getDiscuss_count() {
        return discuss_count;
    }

    public void setDiscuss_count(int discuss_count) {
        this.discuss_count = discuss_count;
    }

    public String getTopic_description() {
        return topic_description;
    }

    public void setTopic_description(String topic_description) {
        this.topic_description = topic_description;
    }

    public String getTopic_pic() {
        return topic_pic;
    }

    public void setTopic_pic(String topic_pic) {
        this.topic_pic = topic_pic;
    }

    public int getTopic_lock() {
        return topic_lock;
    }

    public void setTopic_lock(int topic_lock) {
        this.topic_lock = topic_lock;
    }

    public int getFocus_count() {
        return focus_count;
    }

    public void setFocus_count(int focus_count) {
        this.focus_count = focus_count;
    }

    public int getUser_related() {
        return user_related;
    }

    public void setUser_related(int user_related) {
        this.user_related = user_related;
    }

    public Object getUrl_token() {
        return url_token;
    }

    public void setUrl_token(Object url_token) {
        this.url_token = url_token;
    }

    public int getMerged_id() {
        return merged_id;
    }

    public void setMerged_id(int merged_id) {
        this.merged_id = merged_id;
    }

    public Object getSeo_title() {
        return seo_title;
    }

    public void setSeo_title(Object seo_title) {
        this.seo_title = seo_title;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(int is_parent) {
        this.is_parent = is_parent;
    }

    public int getDiscuss_count_last_week() {
        return discuss_count_last_week;
    }

    public void setDiscuss_count_last_week(int discuss_count_last_week) {
        this.discuss_count_last_week = discuss_count_last_week;
    }

    public int getDiscuss_count_last_month() {
        return discuss_count_last_month;
    }

    public void setDiscuss_count_last_month(int discuss_count_last_month) {
        this.discuss_count_last_month = discuss_count_last_month;
    }

    public int getDiscuss_count_update() {
        return discuss_count_update;
    }

    public void setDiscuss_count_update(int discuss_count_update) {
        this.discuss_count_update = discuss_count_update;
    }
}
