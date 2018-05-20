drop database sgysysdesign;
create database sgysysdesign;
use sgysysdesign;
create table tbl_user(
	userid int(4) primary key auto_increment,
	username varchar(20) not null,
	isOnline int(1)
);

create table tbl_chat(
	chatid int(4) primary key not null auto_increment,
	dname varchar(20),
	sname varchar(20),
	message varchar(200),
	data varchar(20)
);

insert tbl_user values('sgy',0);