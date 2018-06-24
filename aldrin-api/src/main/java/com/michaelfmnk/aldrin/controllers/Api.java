package com.michaelfmnk.aldrin.controllers;


public final class Api {
    private Api() {}

    public static final String ROOT_PATH = "/aldrin-api";

    public static final class Users {
        private Users() {}
        public static final String USERS = "/users";
        public static final String USER_BY_ID = USERS + "/{user_id}";
        public static final String USER_BY_USERNAME = USERS + "/{username}";
    }

    public static final class Posts {
        private Posts() {}
        public static final String POSTS = "/posts";
        public static final String POST_BY_ID = "/posts/{post_id}";
        public static final String POST_COMMENTS = "/posts/{post_id}/comments";
        public static final String POST_LIKES = "/posts/{post_id}/likes";
    }


}
