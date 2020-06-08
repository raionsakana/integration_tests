--this script initiates db for h2 db (used in test profile)
insert into user (id, account_status, email, first_name, last_name) values (1, 'CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (id, account_status, email, first_name) values (2, 'NEW', 'brian@domain.com', 'Brian')
insert into user (id, account_status, email, first_name) values (3, 'REMOVED', 'adam@domain.com', 'Adam')
insert into user (id, account_status, email, first_name, last_name) values (4, 'CONFIRMED', 'jane@domain.com', 'Jame', 'Steward')

insert into blog_post (id, entry, user_id) values (1, 'FindPostTest confirmed', 1)
insert into blog_post (id, entry, user_id) values (2, 'FindPostTest confirmed 2', 1)
insert into blog_post (id, entry, user_id) values (3, 'FindPostTest confirmed 3', 1)

insert into blog_post (id, entry, user_id) values (5, 'FindPostTest new', 2)
insert into blog_post (id, entry, user_id) values (4, 'FindPostTest removed', 3)