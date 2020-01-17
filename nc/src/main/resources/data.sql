INSERT INTO db_camunda.act_id_group (ID_, REV_, NAME_, TYPE_) VALUES ('camunda-admin', 1, 'camunda BPM Administrators', 'SYSTEM');
INSERT INTO db_camunda.act_id_group (ID_, REV_, NAME_, TYPE_) VALUES ('users', 1, 'Users', 'WORKFLOW');
INSERT INTO db_camunda.act_id_group (ID_, REV_, NAME_, TYPE_) VALUES ('reviewers', 1, 'Reviewers', 'WORKFLOW');
INSERT INTO db_camunda.act_id_group (ID_, REV_, NAME_, TYPE_) VALUES ('editors', 1, 'Editors', 'WORKFLOW');

INSERT INTO db_camunda.AUTHORITY (id, name) VALUES (1, 'ROLE_ADMINISTRATOR');
INSERT INTO db_camunda.AUTHORITY (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO db_camunda.AUTHORITY (id, name) VALUES (3, 'ROLE_REVIEWER');
INSERT INTO db_camunda.AUTHORITY (id, name) VALUES (4, 'ROLE_EDITOR');

INSERT INTO db_camunda.act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, SALT_, LOCK_EXP_TIME_, ATTEMPTS_, PICTURE_ID_)
VALUES ('systemAdmin', 1, 'System', 'Administrator', 'xmlws.megatravel@gmail.com',
'{SHA-512}Xy3jYn9hwSzWOv2vGzVZwHlx6uR525qn9ZdXHsWKcWNaIjZ8mEqpd1cVe6m3nejetpENeYOW7Im3CtizYKIHMw==',
'+vjpNnOsPi4VU7a1p/9heQ==', null, null, null);

INSERT INTO db_camunda.act_id_membership (USER_ID_, GROUP_ID_) VALUES ('systemAdmin', 'camunda-admin');

INSERT INTO db_camunda.user (id, email, enabled, first_name, last_name, password, title, username)
VALUES (1, 'xmlws.megatravel@gmail.com', b'1', 'System', 'Administrator', '$2a$10$T.0qfMAWd5K.l5zOXTh7Y.79ModAo1cZkSb0kt4i0.AVcdWifFFwK', null, 'systemAdmin');

INSERT INTO db_camunda.user_authorities(user_id, authority_id) VALUES (1, 1);

INSERT INTO db_camunda.act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, SALT_, LOCK_EXP_TIME_, ATTEMPTS_, PICTURE_ID_)
VALUES ('urednik1', 1, 'Urednik', 'Tadic', 'urednik1@gmail.com',
'{SHA-512}Xy3jYn9hwSzWOv2vGzVZwHlx6uR525qn9ZdXHsWKcWNaIjZ8mEqpd1cVe6m3nejetpENeYOW7Im3CtizYKIHMw==',
'+vjpNnOsPi4VU7a1p/9heQ==', null, null, null);
INSERT INTO db_camunda.act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, SALT_, LOCK_EXP_TIME_, ATTEMPTS_, PICTURE_ID_)
VALUES ('urednik2', 1, 'Urednik', 'Ljajic', 'urednik2@gmail.com',
'{SHA-512}Xy3jYn9hwSzWOv2vGzVZwHlx6uR525qn9ZdXHsWKcWNaIjZ8mEqpd1cVe6m3nejetpENeYOW7Im3CtizYKIHMw==',
'+vjpNnOsPi4VU7a1p/9heQ==', null, null, null);
INSERT INTO db_camunda.act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, SALT_, LOCK_EXP_TIME_, ATTEMPTS_, PICTURE_ID_)
VALUES ('urednik3', 1, 'Urednik', 'Rukavina', 'urednik3@gmail.com',
'{SHA-512}Xy3jYn9hwSzWOv2vGzVZwHlx6uR525qn9ZdXHsWKcWNaIjZ8mEqpd1cVe6m3nejetpENeYOW7Im3CtizYKIHMw==',
'+vjpNnOsPi4VU7a1p/9heQ==', null, null, null);

INSERT INTO db_camunda.act_id_membership (USER_ID_, GROUP_ID_) VALUES ('urednik1', 'editors');
INSERT INTO db_camunda.act_id_membership (USER_ID_, GROUP_ID_) VALUES ('urednik2', 'editors');
INSERT INTO db_camunda.act_id_membership (USER_ID_, GROUP_ID_) VALUES ('urednik3', 'editors');

INSERT INTO db_camunda.act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, SALT_, LOCK_EXP_TIME_, ATTEMPTS_, PICTURE_ID_)
VALUES ('recenzent1', 1, 'Recenzent', 'Tadic', 'recenzent1@gmail.com',
'{SHA-512}Xy3jYn9hwSzWOv2vGzVZwHlx6uR525qn9ZdXHsWKcWNaIjZ8mEqpd1cVe6m3nejetpENeYOW7Im3CtizYKIHMw==',
'+vjpNnOsPi4VU7a1p/9heQ==', null, null, null);
INSERT INTO db_camunda.act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, SALT_, LOCK_EXP_TIME_, ATTEMPTS_, PICTURE_ID_)
VALUES ('recenzent2', 1, 'Recenzent', 'Ljajic', 'recenzent2@gmail.com',
'{SHA-512}Xy3jYn9hwSzWOv2vGzVZwHlx6uR525qn9ZdXHsWKcWNaIjZ8mEqpd1cVe6m3nejetpENeYOW7Im3CtizYKIHMw==',
'+vjpNnOsPi4VU7a1p/9heQ==', null, null, null);
INSERT INTO db_camunda.act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, SALT_, LOCK_EXP_TIME_, ATTEMPTS_, PICTURE_ID_)
VALUES ('recenzent3', 1, 'Recenzent', 'Rukavina', 'recenzent3@gmail.com',
'{SHA-512}Xy3jYn9hwSzWOv2vGzVZwHlx6uR525qn9ZdXHsWKcWNaIjZ8mEqpd1cVe6m3nejetpENeYOW7Im3CtizYKIHMw==',
'+vjpNnOsPi4VU7a1p/9heQ==', null, null, null);

INSERT INTO db_camunda.act_id_membership (USER_ID_, GROUP_ID_) VALUES ('recenzent1', 'reviewers');
INSERT INTO db_camunda.act_id_membership (USER_ID_, GROUP_ID_) VALUES ('recenzent2', 'reviewers');
INSERT INTO db_camunda.act_id_membership (USER_ID_, GROUP_ID_) VALUES ('recenzent3', 'reviewers');


INSERT INTO db_camunda.user (id, email, enabled, first_name, last_name, password, title, username)
VALUES (2, 'urednik1@gmail.com', b'1', 'Urednik', 'Tadic', '$2a$10$T.0qfMAWd5K.l5zOXTh7Y.79ModAo1cZkSb0kt4i0.AVcdWifFFwK', null, 'urednik1');
INSERT INTO db_camunda.user_authorities(user_id, authority_id) VALUES (2, 4);
INSERT INTO db_camunda.user (id, email, enabled, first_name, last_name, password, title, username)
VALUES (3, 'urednik2@gmail.com', b'1', 'Urednik', 'Ljajic', '$2a$10$T.0qfMAWd5K.l5zOXTh7Y.79ModAo1cZkSb0kt4i0.AVcdWifFFwK', null, 'urednik2');
INSERT INTO db_camunda.user_authorities(user_id, authority_id) VALUES (3, 4);
INSERT INTO db_camunda.user (id, email, enabled, first_name, last_name, password, title, username)
VALUES (4, 'urednik3@gmail.com', b'1', 'Urednik', 'Rukavina', '$2a$10$T.0qfMAWd5K.l5zOXTh7Y.79ModAo1cZkSb0kt4i0.AVcdWifFFwK', null, 'urednik3');
INSERT INTO db_camunda.user_authorities(user_id, authority_id) VALUES (4, 4);

INSERT INTO db_camunda.user (id, email, enabled, first_name, last_name, password, title, username)
VALUES (5, 'recenzent1@gmail.com', b'1', 'Recenzent', 'Tadic', '$2a$10$T.0qfMAWd5K.l5zOXTh7Y.79ModAo1cZkSb0kt4i0.AVcdWifFFwK', null, 'recenzent1');
INSERT INTO db_camunda.user_authorities(user_id, authority_id) VALUES (5, 3);
INSERT INTO db_camunda.user (id, email, enabled, first_name, last_name, password, title, username)
VALUES (6, 'recenzent2@gmail.com', b'1', 'Recenzent', 'Ljajic', '$2a$10$T.0qfMAWd5K.l5zOXTh7Y.79ModAo1cZkSb0kt4i0.AVcdWifFFwK', null, 'recenzent2');
INSERT INTO db_camunda.user_authorities(user_id, authority_id) VALUES (6, 3);
INSERT INTO db_camunda.user (id, email, enabled, first_name, last_name, password, title, username)
VALUES (7, 'recenzent3@gmail.com', b'1', 'Recenzent', 'Rukavina', '$2a$10$T.0qfMAWd5K.l5zOXTh7Y.79ModAo1cZkSb0kt4i0.AVcdWifFFwK', null, 'recenzent3');
INSERT INTO db_camunda.user_authorities(user_id, authority_id) VALUES (7, 3);

INSERT INTO db_camunda.SCIENTIFIC_AREA(id,name) VALUES (1,'Istorija');
INSERT INTO db_camunda.SCIENTIFIC_AREA(id,name) VALUES (2,'Geografija');
INSERT INTO db_camunda.SCIENTIFIC_AREA(id,name) VALUES (3,'Biologija');

INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (2,1);
INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (2,2);
INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (3,1);
INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (3,3);
INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (4,2);
INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (4,3);
INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (5,1);
INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (5,2);
INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (6,1);
INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (6,3);
INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (7,2);
INSERT INTO db_camunda.user_scientific_areas(user_id, scientific_area_id) VALUES (7,3);