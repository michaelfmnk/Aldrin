DELETE FROM users;
DELETE FROM posts;
DELETE FROM comments;

ALTER SEQUENCE users_user_id_seq RESTART WITH 1000;
ALTER SEQUENCE posts_post_id_seq RESTART WITH 1000;
ALTER SEQUENCE comments_comment_id_seq RESTART WITH 1000;