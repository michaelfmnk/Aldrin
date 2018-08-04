package com.michaelfmnk.aldrin.controllers;


public final class Api {
    private Api() {}

    public static final String ROOT_PATH = "/aldrin-api";
    public static final String FEED = "/feed";

    public static final class Users {
        private Users() {}

        public static final String USER_BY_USERNAME = "/users/{username}";
        public static final String USER_POSTS = "/users/{user_id}/posts";
        public static final String VERIFY = "/users/{user_id}/verify";
    }

    public static final class Posts {
        private Posts() {}

        public static final String POST_BY_ID = "/posts/{post_id}";
        public static final String COMMENTS = "/posts/{post_id}/comments";
        public static final String POST_LIKES = "/posts/{post_id}/likes";
    }

    public static final class Comments {
        private Comments() {
        }

        public static final String COMMENT_BY_ID = "/comments/{comment_id}";
    }

    public static final class Auth {
        private Auth() {
        }
        public static final String SIGN_UP = "/auth/sign-up";
        public static final String LOGIN = "/auth/login";
    }


}
