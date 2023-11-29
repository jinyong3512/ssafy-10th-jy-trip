create database jytrip;

use jytrip;

CREATE TABLE member (
    id VARCHAR(255),
    pw VARCHAR(255),
    nickname VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE security(
	id VARCHAR(255),
    salt BLOB
);

CREATE TABLE token (
	ip VARCHAR(255),
    id VARCHAR(255) primary key,
    refreshToken VARCHAR(500),
    nickname VARCHAR(255),
    accessToken VARCHAR(500)
);

CREATE TABLE board (
    articleNumber INT PRIMARY KEY AUTO_INCREMENT,
    subject VARCHAR(255),
    content TEXT,
    registerTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nickname VARCHAR(255)
);

CREATE TABLE trip(
	title VARCHAR(255) primary key,
    firstimage VARCHAR(255),
    addr1 VARCHAR(255),
    addr2 VARCHAR(255),
    mapy VARCHAR(255),
    mapx VARCHAR(255),
    nickname VARCHAR(255)
);

select * from member;
select * from security;

select * from token;
select * from board;
select * from trip;