package com.zjut.material_wecenter.models;

/**
 * Copyright (C) 2015 Jinghong Union of ZJUT
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Question {

    private int question_id;
    private String question_content;
    private int add_time;
    private int update_time;
    private long published_uid;
    private int answer_count;
    private int view_count;
    private int focus_count;
    private String post_type;

    private UserInfoEntity user_info;

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public void setPublished_uid(long published_uid) {
        this.published_uid = published_uid;
    }

    public void setAnswer_count(int answer_count) {
        this.answer_count = answer_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public void setFocus_count(int focus_count) {
        this.focus_count = focus_count;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public void setUser_info(UserInfoEntity user_info) {
        this.user_info = user_info;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public int getAdd_time() {
        return add_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public long getPublished_uid() {
        return published_uid;
    }

    public int getAnswer_count() {
        return answer_count;
    }

    public int getView_count() {
        return view_count;
    }

    public int getFocus_count() {
        return focus_count;
    }

    public String getPost_type() {
        return post_type;
    }

    public UserInfoEntity getUser_info() {
        return user_info;
    }

    public static class UserInfoEntity {
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
