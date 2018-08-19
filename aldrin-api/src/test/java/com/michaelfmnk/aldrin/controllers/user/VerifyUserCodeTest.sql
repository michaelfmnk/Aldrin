INSERT INTO users
(user_id, login, first_name, last_name, password, last_password_reset_date, enabled)
VALUES
(1, 'michaelfmnk@gmail.com', 'Michael', 'Fomenko', '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK', '2017-07-16 14:40:14.518000', false), --test
(2, 'someoneelse@gmail.com', 'Nick', 'Brown', '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK', '2007-07-16 14:40:14.518000', true), --test
(3, 'admin@gmail.com', 'admin', 'admin', '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK', '2007-07-16 14:40:14.518000', true),
(4, 'steven@gmail.com', 'Steven', 'Else', '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK', '2007-07-16 14:40:14.518000', false);--test


INSERT INTO users_authorities
(user_id, authority_id)
VALUES
(1, 2),
(2, 2),
(3, 1),
(3, 2);


INSERT INTO posts
(post_id, title, "date", user_id)
VALUES
(1, 'POST-TITLE-1', '2010-07-16 17:20:14.518000', 1),
(2, 'POST-TITLE-2', '2014-07-10 17:20:14.518000', 1),
(3, 'POST-TITLE-3', '2014-07-16 17:20:14.518000', 2),
(4, 'POST-TITLE-4', '2000-07-19 17:20:14.518000', 1);


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

INSERT INTO verification_codes
(verification_code, user_id)
VALUES
('123321', 1),
('222333', 2);
