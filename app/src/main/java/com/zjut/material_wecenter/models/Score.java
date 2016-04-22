package com.zjut.material_wecenter.models;

import java.util.List;

/**
 * Created by Administrator on 2016/3/20.
 */
public class Score {

    private String status;

    private List<MsgEntity> msg;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMsg(List<MsgEntity> msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public List<MsgEntity> getMsg() {
        return msg;
    }

    public static class MsgEntity {
        private String term;
        private String name;
        private String classprop;
        private String classscore;
        private String classhuor;
        private String classcredit;

        public void setTerm(String term) {
            this.term = term;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setClassprop(String classprop) {
            this.classprop = classprop;
        }

        public void setClassscore(String classscore) {
            this.classscore = classscore;
        }

        public void setClasshuor(String classhuor) {
            this.classhuor = classhuor;
        }

        public void setClasscredit(String classcredit) {
            this.classcredit = classcredit;
        }

        public String getTerm() {
            return term;
        }

        public String getName() {
            return name;
        }

        public String getClassprop() {
            return classprop;
        }

        public String getClassscore() {
            return classscore;
        }

        public String getClasshuor() {
            return classhuor;
        }

        public String getClasscredit() {
            return classcredit;
        }
    }

}
