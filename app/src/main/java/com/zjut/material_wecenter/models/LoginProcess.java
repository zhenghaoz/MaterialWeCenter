package com.zjut.material_wecenter.models;

public class LoginProcess {

    private RsmEntity rsm;

    private int errno;
    private Object err;

    public void setRsm(RsmEntity rsm) {
        this.rsm = rsm;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public void setErr(Object err) {
        this.err = err;
    }

    public RsmEntity getRsm() {
        return rsm;
    }

    public int getErrno() {
        return errno;
    }

    public Object getErr() {
        return err;
    }

    public static class RsmEntity {
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
