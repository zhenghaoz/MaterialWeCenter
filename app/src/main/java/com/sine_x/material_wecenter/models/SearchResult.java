package com.sine_x.material_wecenter.models;

public class SearchResult {

    /**
     * type : users
     * search_id : 2
     * name : DUPREEH
     * detail : {"avatar_file":"https://vps.sine-x.com/wecenter/uploads/avatar/000/00/00/02_avatar_mid.jpg","signature":"CSGO","reputation":0,"agree_count":0,"thanks_count":0,"fans_count":0}
     */

    private String type;
    private int search_id;
    private String name;
    private DetailBean detail;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSearch_id() {
        return search_id;
    }

    public void setSearch_id(int search_id) {
        this.search_id = search_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public static class DetailBean {
        /**
         * avatar_file : https://vps.sine-x.com/wecenter/uploads/avatar/000/00/00/02_avatar_mid.jpg
         * signature : CSGO
         * reputation : 0
         * agree_count : 0
         * thanks_count : 0
         * fans_count : 0
         */

        private String avatar_file;
        private String signature;
        private int reputation;
        private int agree_count;
        private int thanks_count;
        private int fans_count;
        private int comments;
        private int views;
        private int best_answer;
        private int answer_count;
        private int comment_count;
        private int focus_count;
        private String topic_pic;
        private String topic_description;

        public String getAvatar_file() {
            return avatar_file;
        }

        public void setAvatar_file(String avatar_file) {
            this.avatar_file = avatar_file;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getReputation() {
            return reputation;
        }

        public void setReputation(int reputation) {
            this.reputation = reputation;
        }

        public int getAgree_count() {
            return agree_count;
        }

        public void setAgree_count(int agree_count) {
            this.agree_count = agree_count;
        }

        public int getThanks_count() {
            return thanks_count;
        }

        public void setThanks_count(int thanks_count) {
            this.thanks_count = thanks_count;
        }

        public int getFans_count() {
            return fans_count;
        }

        public void setFans_count(int fans_count) {
            this.fans_count = fans_count;
        }

        public int getComments() {
            return comments;
        }

        public int getViews() {
            return views;
        }

        public int getAnswer_count() {
            return answer_count;
        }

        public int getBest_answer() {
            return best_answer;
        }

        public int getComment_count() {
            return comment_count;
        }

        public int getFocus_count() {
            return focus_count;
        }

        public String getTopic_pic() {
            return topic_pic;
        }

        public String getTopic_description() {
            return topic_description;
        }
    }
}
