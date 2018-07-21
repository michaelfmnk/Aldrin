INSERT INTO users
(user_id, username, first_name, last_name, password, last_password_reset_date, email)
VALUES
(1, 'MichaelFMNK', 'Michael', 'Fomenko', '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK', '2017-07-16 14:40:14.518000', 'michaelfmnk@gmail.com'), --test
(2, 'broo', 'Nick', 'Brown', '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK', '2007-07-16 14:40:14.518000', 'someoneelse@gmail.com'); --test


INSERT INTO users_authorities
(user_id, authority_id)
VALUES
(1, 2),
(2, 2);


INSERT INTO posts
(post_id, title, "date", user_id)
VALUES
(1, 'POST-TITLE-1', '2010-07-16 17:20:14.518000', 1),
(2, 'POST-TITLE-2', '2014-07-16 17:20:14.518000', 1);
