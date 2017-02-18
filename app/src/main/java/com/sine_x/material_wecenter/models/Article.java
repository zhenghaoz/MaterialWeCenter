package com.sine_x.material_wecenter.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {


    /**
     * article_info : {"id":30,"uid":19,"title":"【分享】吴晓波8月8日演讲：大变局中如何拯救你的资产（一文讲清未来发生什么，赚钱机会在哪里）","message":"8月8日，著名财经作家吴晓波在南京作了一场1小时40分钟的演讲，题目为：《如何拯救我的资产》。谢谢大家！","comments":2,"views":70,"add_time":1439349922,"has_attach":0,"lock":0,"votes":2,"title_fulltext":"2099820139 215562619527874 2619527874 826376 826085 2843635762 2146423616 2532725937 3616420135 1996825991 3576228165 2641026469 2145729983 3618638065 2642620250 223122173837324","category_id":1,"is_recommend":1,"chapter_id":null,"sort":0,"user_info":{"uid":19,"user_name":"放肆","signature":"好困。。。","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/19_avatar_mid.jpg"},"vote_info":{"id":30,"uid":3,"type":"article","item_id":30,"rating":1,"time":1439350026,"reputation_factor":1,"item_uid":19},"vote_users":{"2":{"uid":2,"user_name":"小韭菜","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/02_avatar_mid.jpg"},"3":{"uid":3,"user_name":"BugFree","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/03_avatar_mid.jpg"}},"user_follow_check":1}
     * article_topics : [{"topic_id":164,"topic_title":"朝野对赌"},{"topic_id":165,"topic_title":"屌丝经济"},{"topic_id":166,"topic_title":"小众经济"}]
     * comments : [{"id":1,"uid":3,"article_id":30,"message":"GG ","add_time":1447911331,"at_uid":0,"votes":0,"user_info":{"uid":3,"user_name":"BugFree","signature":"绝望中新生，迟疑中上涨，欢乐中死亡，企盼中下跌。","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/03_avatar_mid.jpg"},"at_user_info":null,"vote_info":null},{"id":2,"uid":3,"article_id":30,"message":"GG个","add_time":1447911338,"at_uid":3,"votes":0,"user_info":{"uid":3,"user_name":"BugFree","signature":"绝望中新生，迟疑中上涨，欢乐中死亡，企盼中下跌。","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/03_avatar_mid.jpg"},"at_user_info":{"uid":3,"user_name":"BugFree","signature":"绝望中新生，迟疑中上涨，欢乐中死亡，企盼中下跌。","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/03_avatar_mid.jpg"},"vote_info":null}]
     */

    private ArticleInfo article_info;
    private List<ArticleTopicsBean> article_topics;
    private List<CommentsBean> comments;

    public ArticleInfo getArticle_info() {
        return article_info;
    }

    public void setArticle_info(ArticleInfo article_info) {
        this.article_info = article_info;
    }

    public List<ArticleTopicsBean> getArticle_topics() {
        return article_topics;
    }

    public void setArticle_topics(List<ArticleTopicsBean> article_topics) {
        this.article_topics = article_topics;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class ArticleInfo {
        /**
         * id : 30
         * uid : 19
         * title : 【分享】吴晓波8月8日演讲：大变局中如何拯救你的资产（一文讲清未来发生什么，赚钱机会在哪里）
         * message : 8月8日，著名财经作家吴晓波在南京作了一场1小时40分钟的演讲，题目为：《如何拯救我的资产》。谢谢大家！
         * comments : 2
         * views : 70
         * add_time : 1439349922
         * has_attach : 0
         * lock : 0
         * votes : 2
         * title_fulltext : 2099820139 215562619527874 2619527874 826376 826085 2843635762 2146423616 2532725937 3616420135 1996825991 3576228165 2641026469 2145729983 3618638065 2642620250 223122173837324
         * category_id : 1
         * is_recommend : 1
         * chapter_id : null
         * sort : 0
         * user_info : {"uid":19,"user_name":"放肆","signature":"好困。。。","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/19_avatar_mid.jpg"}
         * vote_info : {"id":30,"uid":3,"type":"article","item_id":30,"rating":1,"time":1439350026,"reputation_factor":1,"item_uid":19}
         * vote_users : {"2":{"uid":2,"user_name":"小韭菜","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/02_avatar_mid.jpg"},"3":{"uid":3,"user_name":"BugFree","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/03_avatar_mid.jpg"}}
         * user_follow_check : 1
         */

        private int id;
        private int uid;
        private String title;
        private String message;
        private int comments;
        private int views;
        private int add_time;
        private int has_attach;
        private int lock;
        private int votes;
        private String title_fulltext;
        private int category_id;
        private int is_recommend;
        private Object chapter_id;
        private int sort;
        private UserInfoBean user_info;
        private VoteInfoBean vote_info;
        private VoteUsersBean vote_users;
        private int user_follow_check;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getHas_attach() {
            return has_attach;
        }

        public void setHas_attach(int has_attach) {
            this.has_attach = has_attach;
        }

        public int getLock() {
            return lock;
        }

        public void setLock(int lock) {
            this.lock = lock;
        }

        public int getVotes() {
            return votes;
        }

        public void setVotes(int votes) {
            this.votes = votes;
        }

        public String getTitle_fulltext() {
            return title_fulltext;
        }

        public void setTitle_fulltext(String title_fulltext) {
            this.title_fulltext = title_fulltext;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(int is_recommend) {
            this.is_recommend = is_recommend;
        }

        public Object getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(Object chapter_id) {
            this.chapter_id = chapter_id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public VoteInfoBean getVote_info() {
            return vote_info;
        }

        public void setVote_info(VoteInfoBean vote_info) {
            this.vote_info = vote_info;
        }

        public VoteUsersBean getVote_users() {
            return vote_users;
        }

        public void setVote_users(VoteUsersBean vote_users) {
            this.vote_users = vote_users;
        }

        public int getUser_follow_check() {
            return user_follow_check;
        }

        public void setUser_follow_check(int user_follow_check) {
            this.user_follow_check = user_follow_check;
        }

        public static class UserInfoBean {
            /**
             * uid : 19
             * user_name : 放肆
             * signature : 好困。。。
             * avatar_file : http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/19_avatar_mid.jpg
             */

            private int uid;
            private String user_name;
            private String signature;
            private String avatar_file;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getAvatar_file() {
                return avatar_file;
            }

            public void setAvatar_file(String avatar_file) {
                this.avatar_file = avatar_file;
            }
        }

        public static class VoteInfoBean {
            /**
             * id : 30
             * uid : 3
             * type : article
             * item_id : 30
             * rating : 1
             * time : 1439350026
             * reputation_factor : 1
             * item_uid : 19
             */

            private int id;
            private int uid;
            private String type;
            private int item_id;
            private int rating;
            private int time;
            private int reputation_factor;
            private int item_uid;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getItem_id() {
                return item_id;
            }

            public void setItem_id(int item_id) {
                this.item_id = item_id;
            }

            public int getRating() {
                return rating;
            }

            public void setRating(int rating) {
                this.rating = rating;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getReputation_factor() {
                return reputation_factor;
            }

            public void setReputation_factor(int reputation_factor) {
                this.reputation_factor = reputation_factor;
            }

            public int getItem_uid() {
                return item_uid;
            }

            public void setItem_uid(int item_uid) {
                this.item_uid = item_uid;
            }
        }

        public static class VoteUsersBean {
            /**
             * 2 : {"uid":2,"user_name":"小韭菜","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/02_avatar_mid.jpg"}
             * 3 : {"uid":3,"user_name":"BugFree","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/03_avatar_mid.jpg"}
             */

            @SerializedName("2")
            private _$2Bean _$2;
            @SerializedName("3")
            private _$3Bean _$3;

            public _$2Bean get_$2() {
                return _$2;
            }

            public void set_$2(_$2Bean _$2) {
                this._$2 = _$2;
            }

            public _$3Bean get_$3() {
                return _$3;
            }

            public void set_$3(_$3Bean _$3) {
                this._$3 = _$3;
            }

            public static class _$2Bean {
                /**
                 * uid : 2
                 * user_name : 小韭菜
                 * avatar_file : http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/02_avatar_mid.jpg
                 */

                private int uid;
                private String user_name;
                private String avatar_file;

                public int getUid() {
                    return uid;
                }

                public void setUid(int uid) {
                    this.uid = uid;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getAvatar_file() {
                    return avatar_file;
                }

                public void setAvatar_file(String avatar_file) {
                    this.avatar_file = avatar_file;
                }
            }

            public static class _$3Bean {
                /**
                 * uid : 3
                 * user_name : BugFree
                 * avatar_file : http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/03_avatar_mid.jpg
                 */

                private int uid;
                private String user_name;
                private String avatar_file;

                public int getUid() {
                    return uid;
                }

                public void setUid(int uid) {
                    this.uid = uid;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getAvatar_file() {
                    return avatar_file;
                }

                public void setAvatar_file(String avatar_file) {
                    this.avatar_file = avatar_file;
                }
            }
        }
    }

    public static class ArticleTopicsBean {
        /**
         * topic_id : 164
         * topic_title : 朝野对赌
         */

        private int topic_id;
        private String topic_title;

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public String getTopic_title() {
            return topic_title;
        }

        public void setTopic_title(String topic_title) {
            this.topic_title = topic_title;
        }
    }

    public static class CommentsBean {
        /**
         * id : 1
         * uid : 3
         * article_id : 30
         * message : GG
         * add_time : 1447911331
         * at_uid : 0
         * votes : 0
         * user_info : {"uid":3,"user_name":"BugFree","signature":"绝望中新生，迟疑中上涨，欢乐中死亡，企盼中下跌。","avatar_file":"http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/03_avatar_mid.jpg"}
         * at_user_info : null
         * vote_info : null
         */

        private int id;
        private int uid;
        private int article_id;
        private String message;
        private int add_time;
        private int at_uid;
        private int votes;
        private UserInfoBeanX user_info;
        private Object at_user_info;
        private Object vote_info;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getArticle_id() {
            return article_id;
        }

        public void setArticle_id(int article_id) {
            this.article_id = article_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getAt_uid() {
            return at_uid;
        }

        public void setAt_uid(int at_uid) {
            this.at_uid = at_uid;
        }

        public int getVotes() {
            return votes;
        }

        public void setVotes(int votes) {
            this.votes = votes;
        }

        public UserInfoBeanX getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBeanX user_info) {
            this.user_info = user_info;
        }

        public Object getAt_user_info() {
            return at_user_info;
        }

        public void setAt_user_info(Object at_user_info) {
            this.at_user_info = at_user_info;
        }

        public Object getVote_info() {
            return vote_info;
        }

        public void setVote_info(Object vote_info) {
            this.vote_info = vote_info;
        }

        public static class UserInfoBeanX {
            /**
             * uid : 3
             * user_name : BugFree
             * signature : 绝望中新生，迟疑中上涨，欢乐中死亡，企盼中下跌。
             * avatar_file : http://wecenter.dev.hihwei.com/uploads/avatar/000/00/00/03_avatar_mid.jpg
             */

            private int uid;
            private String user_name;
            private String signature;
            private String avatar_file;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getAvatar_file() {
                return avatar_file;
            }

            public void setAvatar_file(String avatar_file) {
                this.avatar_file = avatar_file;
            }
        }
    }
}
