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

insert into user (id, confirmation_id, confirmation_status, email, first_name, last_name, newsletter_status, password_encrypted) values (null, null, true, 'test1@test.pl', 'Krzysztof', 'Chrusciel', FALSE, '$2a$10$wG.lPg3cbXg/yfL1ZEU.yODOKnl//gaoi9HKFe91Ov9UghipxEaK6');
insert into user (id, confirmation_id, confirmation_status, email, first_name, last_name, newsletter_status, password_encrypted) values (null, null, true, 'test2@test.pl', 'Jan', 'Nowak', FALSE, '$2a$10$wG.lPg3cbXg/yfL1ZEU.yODOKnl//gaoi9HKFe91Ov9UghipxEaK6');


-- References
insert into reference (id, about_id, author_id, reference_content) values (null, 2, 1, 'One ipsum dolor sit amet, consectetur adipiscing elit');
insert into reference (id, about_id, author_id, reference_content) values (null, 2, 1, 'Two ipsum dolor sit amet, consectetur adipiscing elit');

-- Offers
insert into offer (id, city, created_date, description, event_date, file_name, owner_id, phone_number, price, quantity, title) values (null, 'Sosnowiec',  STR_TO_DATE ('2016-12-20 21:49:09','%Y-%m-%d %H:%i:%s'), 'Opis', STR_TO_DATE('2016-12-20 21:49:09','%Y-%m-%d %H:%i:%s'), 'test.png', 1, '500-600-700', 50, 4, 'Title');

-- Messages
insert into message (id, content, creation_date, is_read, offerid, owner, title) values (null, 'One ipsum dolor sit amet, consectetur adipiscing elit', STR_TO_DATE ('2016-12-20 21:49:09','%Y-%m-%d %H:%i:%s'), FALSE, 1, 1, 'title');
insert into message (id, content, creation_date, is_read, offerid, owner, title) values (null, 'One ipsum dolor sit amet, consectetur adipiscing elit', STR_TO_DATE ('2016-12-20 21:49:09','%Y-%m-%d %H:%i:%s'), FALSE, 1, 1, 'title');