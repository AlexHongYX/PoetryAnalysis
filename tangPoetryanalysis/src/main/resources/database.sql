
create database if not exists 'poetry';
use 'poetry';

create table if not exists 'poetry_info'(
  title varchar(64) not null,
  dynasty varchar(32) not null,
  author varchar(16) not null,
  content varchar(1024) not null
);

-- 数据插入
insert into poetry_info (title,dynasty,author,content) values (?,?,?,?);

-- 数据查询
select * from poetry_info;