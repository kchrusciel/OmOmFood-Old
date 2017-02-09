DROP TABLE IF EXISTS UserConnection;

create table UserConnection (userId varchar(255) not null,
                             providerId varchar(255) not null,
                             providerUserId varchar(255),
                             rank int not null,
                             displayName varchar(255),
                             profileUrl varchar(512),
                             imageUrl varchar(512),
                             accessToken varchar(512) not null,
                             secret varchar(512),
                             refreshToken varchar(512),
                             expireTime bigint,
  primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);


-- Users
insert into "user" (id, confirmation_id, confirmation_status, email, first_name, last_name, newsletter_status, password_encrypted) values (1, null, true, 'test1@test.pl', 'Krzysztof', 'Chrusciel', FALSE, '$2a$10$wG.lPg3cbXg/yfL1ZEU.yODOKnl//gaoi9HKFe91Ov9UghipxEaK6');
insert into "user" (id, confirmation_id, confirmation_status, email, first_name, last_name, newsletter_status, password_encrypted) values (2, null, true, 'test2@test.pl', 'Jan', 'Nowak', FALSE, '$2a$10$wG.lPg3cbXg/yfL1ZEU.yODOKnl//gaoi9HKFe91Ov9UghipxEaK6');
insert into "user" (id, confirmation_id, confirmation_status, email, first_name, last_name, newsletter_status, password_encrypted) values (3, null, true, 'admin@omomfood.pl', 'Admin', 'Admin', FALSE, '$2a$10$wG.lPg3cbXg/yfL1ZEU.yODOKnl//gaoi9HKFe91Ov9UghipxEaK6');


-- References
insert into reference (id, about_id, author_id, reference_content) values (1, 2, 1, 'One ipsum dolor sit amet, consectetur adipiscing elit');
insert into reference (id, about_id, author_id, reference_content) values (2, 2, 1, 'Two ipsum dolor sit amet, consectetur adipiscing elit');

-- Offers
insert into offer (id, city, created_date, description, event_date, file_name, owner_id, phone_number, price, quantity, title) values (1, 'Sosnowiec',  to_date ('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'Opis', to_date('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'test.png', 1, '500-600-700', 50, 4, 'Title');
insert into offer (id, city, created_date, description, event_date, file_name, owner_id, phone_number, price, quantity, title) values (2, 'Katowice',  to_date ('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'Opis', to_date('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'test.png', 1, '500-600-700', 50, 4, 'Title');
insert into offer (id, city, created_date, description, event_date, file_name, owner_id, phone_number, price, quantity, title) values (3, 'Warszawa',  to_date ('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'Opis', to_date('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'test.png', 1, '500-600-700', 50, 4, 'Title');
insert into offer (id, city, created_date, description, event_date, file_name, owner_id, phone_number, price, quantity, title) values (4, 'Sochaczew',  to_date ('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'Opis', to_date('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'test.png', 1, '500-600-700', 50, 4, 'Title');
insert into offer (id, city, created_date, description, event_date, file_name, owner_id, phone_number, price, quantity, title) values (5, 'Skoczów',  to_date ('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'Opis', to_date('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'test.png', 1, '500-600-700', 50, 4, 'Title');
insert into offer (id, city, created_date, description, event_date, file_name, owner_id, phone_number, price, quantity, title) values (6, 'Sędziszów',  to_date ('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'Opis', to_date('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'test.png', 1, '500-600-700', 50, 4, 'Title');
insert into offer (id, city, created_date, description, event_date, file_name, owner_id, phone_number, price, quantity, title) values (7, 'Sosnowiec',  to_date ('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'Opis', to_date('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'test.png', 1, '500-600-700', 50, 4, 'Title');
insert into offer (id, city, created_date, description, event_date, file_name, owner_id, phone_number, price, quantity, title) values (8, 'Warszawa',  to_date ('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'Opis', to_date('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), 'test.png', 1, '500-600-700', 50, 4, 'Title');


-- Offers types
insert into offer_offer_type (offer_id, offer_type) values (1, "DOG");

-- Messages
insert into message (id, content, creation_date, is_read, recipient, author,, title) values (1, 'One ipsum dolor sit amet, consectetur adipiscing elit', to_date ('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), FALSE, 2, 1, 'title');
insert into message (id, content, creation_date, is_read, recipient, author,, title) values (2, 'One ipsum dolor sit amet, consectetur adipiscing elit', to_date ('2016-12-20 21:49:09','YYYY-MM-DD HH24:MI:SS'), FALSE, 2, 1, 'title');