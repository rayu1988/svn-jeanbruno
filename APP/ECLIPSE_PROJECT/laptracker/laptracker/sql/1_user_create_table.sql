create table USER (
	id_user int not null auto_increment primary key,
	nickname varchar(50) not null,
	password varchar(50) not null
)ENGINE=INNODB;
insert into USER (nickname, password) values ('admin', 'admin');