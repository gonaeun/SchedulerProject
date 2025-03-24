CREATE DATABASE Schedules;
USE Schedules;

create table schedule (
	id INT AUTO_INCREMENT PRIMARY KEY,
    title TEXT NOT NULL COMMENT '일정 제목',
    writer TEXT NOT NULL COMMENT '작성자명',
    content TEXT NOT NULL COMMENT '일정 내용',
    plan_date datetime NOT NULL comment '일정 날짜', 
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
    updated_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
);