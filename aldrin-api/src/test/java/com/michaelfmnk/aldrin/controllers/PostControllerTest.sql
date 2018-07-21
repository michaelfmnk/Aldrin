INSERT INTO users
(user_id, username, first_name, last_name, password, last_password_reset_date, email)
VALUES
(1, 'MichaelFMNK', 'Michael', 'Fomenko', 'pass', '2017-07-16 14:40:14.518000', 'michaelfmnk@gmail.com'),
(2, 'broo', 'Nick', 'Brown', 'pass', '2007-07-16 14:40:14.518000', 'someoneelse@gmail.com');


INSERT INTO users_authorities
(user_id, authority_id)
VALUES
(1, 2),
(2, 2);


INSERT INTO posts
(post_id, title, "date", user_id)
VALUES
(1, 'POST-TITLE-1', '2010-07-16 17:20:14.518000', 1),
(2, 'POST-TITLE-2', '2014-07-16 17:20:14.518000', 1),
(3, 'POST-TITLE-3', '2014-07-16 17:20:14.518000', 2);


INSERT INTO comments
(comment_id, post_id, user_id, replied_comment_id, content, "date")
VALUES
(1, 1, 1, null, 'my fancy comment-1', '2050-07-26 13:20:14.518000'),
(2, 1, 2, null, 'my fancy comment-2', '2060-07-26 13:20:14.518000'),
(3, 1, 2, null, 'my fancy comment-3', '2010-07-26 13:20:14.518000');


INSERT INTO likes
(user_id, post_id)
VALUES
(1, 1),
(2, 1);
