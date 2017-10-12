package com.sine_x.material_wecenter.models;

public class Chat {

    /**
     * id : 1
     * uid : 1
     * dialog_id : 1
     * message : Hello
     * add_time : 1507700672
     * sender_remove : 0
     * recipient_remove : 0
     * receipt : 1507796239
     * user_name : DUPREEH
     * url_token : DUPREEH
     * local : true
     */

    private int id;
    private int uid;
    private int dialog_id;
    private String message;
    private int add_time;
    private int sender_remove;
    private int recipient_remove;
    private int receipt;
    private String user_name;
    private String url_token;
    private String avatar_file;
    private boolean local;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getDialog_id() {
        return dialog_id;
    }

    public void setDialog_id(int dialog_id) {
        this.dialog_id = dialog_id;
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

    public int getSender_remove() {
        return sender_remove;
    }

    public void setSender_remove(int sender_remove) {
        this.sender_remove = sender_remove;
    }

    public int getRecipient_remove() {
        return recipient_remove;
    }

    public void setRecipient_remove(int recipient_remove) {
        this.recipient_remove = recipient_remove;
    }

    public int getReceipt() {
        return receipt;
    }

    public void setReceipt(int receipt) {
        this.receipt = receipt;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUrl_token() {
        return url_token;
    }

    public void setUrl_token(String url_token) {
        this.url_token = url_token;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getAvatar_file() {
        return avatar_file;
    }
}
