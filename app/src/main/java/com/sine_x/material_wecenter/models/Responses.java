package com.sine_x.material_wecenter.models;

import java.util.List;

public class Responses<T> {

    List<T> rsm;
    private int errno;
    private String err;

    public void setRsm(List<T> rsm) {
        this.rsm = rsm;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public List<T> getRsm() {
        return rsm;
    }

    public int getErrno() {
        return errno;
    }

    public String getErr() {
        return err;
    }
}
