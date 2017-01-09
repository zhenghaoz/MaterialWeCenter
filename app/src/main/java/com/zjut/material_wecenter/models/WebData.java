package com.zjut.material_wecenter.models;

/**
 * Created by Administrator on 2016/2/5.
 */
public class WebData {
    private Type type;
    private String data;
    private Gravity gravity;

    public WebData(Type type, String data, Gravity gravity) {
        this.type=type;
        this.gravity = gravity;
        this.data = data;
    }

    public Gravity getGravity() {
        return gravity;
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        TEXT,
        IMAGE
    }

    public enum Gravity{
        LEFT,
        CENTER,
        RIGHT
    }
}
