insert into user (id, confirmation_id, confirmation_status, email, first_name, last_name, newsletter_status, password_encrypted) values (null, null, true, 'test1@test.pl', 'Krzysztof', 'Chrusciel', false, '$2a$10$wG.lPg3cbXg/yfL1ZEU.yODOKnl//gaoi9HKFe91Ov9UghipxEaK6');
insert into user (id, confirmation_id, confirmation_status, email, first_name, last_name, newsletter_status, password_encrypted) values (null, null, true, 'test2@test.pl', 'Jan', 'Nowak', false, '$2a$10$wG.lPg3cbXg/yfL1ZEU.yODOKnl//gaoi9HKFe91Ov9UghipxEaK6');


-- References
insert into reference (id, about_id, author_id, reference_content) values (null, 2, 1, 'One ipsum dolor sit amet, consectetur adipiscing elit');
insert into reference (id, about_id, author_id, reference_content) values (null, 2, 1, 'Two ipsum dolor sit amet, consectetur adipiscing elit');

-- Offers
insert into offer (id, city, created_date, description, event_date, file_name, owner_id, phone_number, price, quantity, title) values (null, 'Sosnowiec',  PARSEDATETIME ('2016-12-20 21:49:09','yy-MM-dd hh:mm:ss'), 'Opis', PARSEDATETIME ('2016-12-20 21:49:09','yy-MM-dd hh:mm:ss'), 'test.png', 1, '500-600-700', 50, 4, 'Title');

-- Messages
insert into message (id, content, creation_date, is_read, offerid, owner, title) values (null, 'One ipsum dolor sit amet, consectetur adipiscing elit', PARSEDATETIME ('2016-12-20 21:49:09','yy-MM-dd hh:mm:ss'), FALSE, 1, 1, 'title');
insert into message (id, content, creation_date, is_read, offerid, owner, title) values (null, 'One ipsum dolor sit amet, consectetur adipiscing elit', PARSEDATETIME ('2016-12-20 21:49:09','yy-MM-dd hh:mm:ss'), FALSE, 1, 1, 'title');