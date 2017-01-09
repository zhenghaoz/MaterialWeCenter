package com.zjut.material_wecenter.models;

import java.util.List;

/**
 * Created by Administrator on 2016/3/20.
 */
public class Courses {
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
        private String name;
        private String collage;
        private String classscore;
        private String classhuor;
        private String classinfo;
        private String classtype;

        public void setName(String name) {
            this.name = name;
        }

        public void setCollage(String collage) {
            this.collage = collage;
        }

        public void setClassscore(String classscore) {
            this.classscore = classscore;
        }

        public void setClasshuor(String classhuor) {
            this.classhuor = classhuor;
        }

        public void setClassinfo(String classinfo) {
            this.classinfo = classinfo;
        }

        public void setClasstype(String classtype) {
            this.classtype = classtype;
        }

        public String getName() {
            return name;
        }

        public String getCollage() {
            return collage;
        }

        public String getClassscore() {
            return classscore;
        }

        public String getClasshuor() {
            return classhuor;
        }

        public String getClassinfo() {
            return classinfo;
        }

        public String getClasstype() {
            return classtype;
        }
    }
}
