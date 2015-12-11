package com.zjut.material_wecenter.models;

public class PublishQuestion {

    /**
     * question_id : 3572
     */

    private RsmEntity rsm;
    /**
     * rsm : {"question_id":"3572"}
     * errno : 1
     * err : null
     */

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
        private String question_id;

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public String getQuestion_id() {
            return question_id;
        }
    }
}
