package com.sine_x.material_wecenter.models;

public class Response<T> {

    private T rsm;
    private String err;
    private int errno;

    public void setRsm(T rsm) {
        this.rsm = rsm;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public T getRsm() {
        return rsm;
    }

    public int getErrno() {
        return errno;
    }

    public String getErr() {
        return err;
    }
}
