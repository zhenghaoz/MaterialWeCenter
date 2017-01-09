package com.zjut.material_wecenter.models;


import java.lang.Object;

public class Result {

    private Object rsm;
    private int errno;
    private String err;

    public void setErr(String err) {
        this.err = err;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public void setRsm(Object rsm) {
        this.rsm = rsm;
    }

    public int getErrno() {
        return errno;
    }

    public Object getRsm() {
        return rsm;
    }

    public String getErr() {
        return err;
    }
}
