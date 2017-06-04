DROP TABLE IF EXISTS people;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS followers;
DROP table if exists users;
DROP table if exists authorities;

CREATE TABLE users(
  id bigint auto_increment,
  username varchar(255),
  password varchar(255),
  enabled boolean
  );

CREATE TABLE authorities(
  username  varchar(255),
  authority  varchar(255),
  UNIQUE(username,authority)
  );

CREATE TABLE people (
    id IDENTITY,
    handle VARCHAR,
    name VARCHAR
);

CREATE TABLE messages (
    id IDENTITY,
    person_id NUMBER REFERENCES people (id),
    content VARCHAR
);

CREATE TABLE followers (
    id IDENTITY,
    person_id NUMBER REFERENCES people (id),
    follower_person_id NUMBER REFERENCES people (id)
);
