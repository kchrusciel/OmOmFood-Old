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
insert into user (id, confirmation_id, confirmation_status, email, first_name, last_name, newsletter_status, password_encrypted, avatar_file_name) values (null, null, false, 'test1@test.pl', 'Krzysztof', 'Chrusciel', FALSE, '$2a$10$wG.lPg3cbXg/yfL1ZEU.yODOKnl//gaoi9HKFe91Ov9UghipxEaK6', 'defaults/users/user.svg');
insert into user (id, confirmation_id, confirmation_status, email, first_name, last_name, newsletter_status, password_encrypted, avatar_file_name) values (null, null, true, 'admin@omomfood.pl', 'Admin', 'Admin', FALSE, '$2a$10$wG.lPg3cbXg/yfL1ZEU.yODOKnl//gaoi9HKFe91Ov9UghipxEaK6', 'defaults/users/user.svg');
insert into user (id, confirmation_id, confirmation_status, email, first_name, last_name, newsletter_status, password_encrypted, avatar_file_name) values (null, null, true, 'test2@test.pl', 'Jan', 'Nowak', FALSE, '$2a$10$wG.lPg3cbXg/yfL1ZEU.yODOKnl//gaoi9HKFe91Ov9UghipxEaK6', 'defaults/users/user.svg');
insert into user (id, confirmation_id, confirmation_status, email, first_name, last_name, newsletter_status, password_encrypted, avatar_file_name) values (null, null, true, 'test3@test.pl', 'Jan', 'Kowalski', FALSE, '$2a$10$wG.lPg3cbXg/yfL1ZEU.yODOKnl//gaoi9HKFe91Ov9UghipxEaK6', 'defaults/users/user.svg');


-- References
insert into reference (id, about_id, author_id, reference_content) values (null, 2, 1, 'One ipsum dolor sit amet, consectetur adipiscing elit');
insert into reference (id, about_id, author_id, reference_content) values (null, 2, 1, 'Two ipsum dolor sit amet, consectetur adipiscing elit');

-- Offers
insert into offer (id, city, created_date, description, event_date, unlimited, icon_file_name, owner_id, free, phone_number, value, currency_code, quantity, title, offer_type) values (null, 'Sosnowiec',  STR_TO_DATE ('2016-12-20 21:49:09','%Y-%m-%d %H:%i:%s'), 'Oczywiście weganizm i wegetarianizm mają ze sobą wiele wspólnego. Weganizm jest bardziej konserwatywną odmianą wegetarianizmu, zatem są tutaj wyraźnie zarysowane części wspólne. Oczywiście weganizm i wegetarianizm mają ze sobą wiele wspólnego. Weganizm jest bardziej konserwatywną odmianą wegetarianizmu, zatem są tutaj wyraźnie zarysowane części wspólne.', STR_TO_DATE('2016-12-19 23:00:00','%Y-%m-%d %H:%i:%s'), FALSE, 'defaults/offers/offer.png', 1, FALSE,'500-600-700', 50, 'EUR', 4, 'Oczywiście weganizm!', 'DINNER');
insert into offer (id, city, created_date, description, event_date, unlimited, icon_file_name, owner_id, free, phone_number, value, currency_code, quantity, title, offer_type) values (null, 'Warszawa',  STR_TO_DATE ('2016-12-20 21:49:09','%Y-%m-%d %H:%i:%s'), 'Oczywiście weganizm i wegetarianizm mają ze sobą wiele wspólnego. Weganizm jest bardziej konserwatywną odmianą wegetarianizmu, zatem są tutaj wyraźnie zarysowane części wspólne. Oczywiście weganizm i wegetarianizm mają ze sobą wiele wspólnego. Weganizm jest bardziej konserwatywną odmianą wegetarianizmu, zatem są tutaj wyraźnie zarysowane części wspólne.', STR_TO_DATE('2016-12-20 22:00:00','%Y-%m-%d %H:%i:%s'), FALSE, 'defaults/offers/offer.png', 3, TRUE, '500-600-700', 50, 'EUR', 4, 'Oczywiście weganizm!', 'TAKEOUT');
insert into offer (id, city, created_date, description, event_date, unlimited, icon_file_name, owner_id, free, phone_number, value, currency_code, quantity, title, offer_type) values (null, 'Warszawa',  STR_TO_DATE ('2016-12-20 21:49:09','%Y-%m-%d %H:%i:%s'), 'Oczywiście weganizm i wegetarianizm mają ze sobą wiele wspólnego. Weganizm jest bardziej konserwatywną odmianą wegetarianizmu, zatem są tutaj wyraźnie zarysowane części wspólne. Oczywiście weganizm i wegetarianizm mają ze sobą wiele wspólnego. Weganizm jest bardziej konserwatywną odmianą wegetarianizmu, zatem są tutaj wyraźnie zarysowane części wspólne.', STR_TO_DATE('2016-12-20 22:00:00','%Y-%m-%d %H:%i:%s'), FALSE, 'defaults/offers/offer.png', 3, TRUE, '500-600-700', 50, 'EUR', 4, 'Oczywiście weganizm!', 'ONTHESPOT');


-- Offers types
insert into offer_offer_details_types (offer_id, offer_details_types) values (1, "ALCOHOL");
insert into offer_offer_details_types (offer_id, offer_details_types) values (1, "PARKING");
insert into offer_offer_details_types (offer_id, offer_details_types) values (1, "VEGETARIAN");
insert into offer_offer_details_types (offer_id, offer_details_types) values (1, "VEGE");
insert into offer_offer_details_types (offer_id, offer_details_types) values (1, "ANIMAL");
insert into offer_offer_details_types (offer_id, offer_details_types) values (1, "WIFI");
insert into offer_offer_details_types (offer_id, offer_details_types) values (1, "GLUTEN");

insert into offer_offer_details_types (offer_id, offer_details_types) values (2, "VEGE");
insert into offer_offer_details_types (offer_id, offer_details_types) values (2, "ANIMAL");
insert into offer_offer_details_types (offer_id, offer_details_types) values (2, "WIFI");
insert into offer_offer_details_types (offer_id, offer_details_types) values (2, "GLUTEN");

insert into offer_offer_details_types (offer_id, offer_details_types) values (3, "ALCOHOL");
insert into offer_offer_details_types (offer_id, offer_details_types) values (3, "PARKING");
insert into offer_offer_details_types (offer_id, offer_details_types) values (3, "VEGETARIAN");



-- Messages
insert into message (id, content, creation_date, is_read, recipient, author, title) values (null, 'One ipsum dolor sit amet, consectetur adipiscing elit', STR_TO_DATE ('2016-12-20 21:49:09','%Y-%m-%d %H:%i:%s'), FALSE, 2, 1, 'title');
insert into message (id, content, creation_date, is_read, recipient, author, title) values (null, 'One ipsum dolor sit amet, consectetur adipiscing elit', STR_TO_DATE ('2016-12-20 21:49:09','%Y-%m-%d %H:%i:%s'), FALSE, 2, 1, 'title');

-- Assigning user to offer
INSERT INTO `omomfood`.`offer_user` (`offer_is`, `user_id`) VALUES ('2', '1');
INSERT INTO `omomfood`.`offer_user` (`offer_is`, `user_id`) VALUES ('2', '4');