Hibernate: drop table if exists user CASCADE 
Hibernate: drop sequence if exists hibernate_sequence
Hibernate: create sequence hibernate_sequence start with 1 increment by 1
Hibernate: 


http://localhost:8080/h2-console

create table user 
(id integer not null, 
birth_date timestamp, 
name varchar(10), 
primary key (id))

create table post 
(id integer not null, 
description varchar(255), 
user_id integer, 
primary key (id))

