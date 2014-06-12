create table LAP_TRACK (
	id_lap_track int not null auto_increment primary key,
	id_user int not null,
	lap_type tinyint not null,
	date datetime not null,
	distance_unit tinyint null,
	distance_value varchar(20) not null,
	total_time_format varchar(10) not null,
	total_time_value varchar(10) not null,
	distance_divisor_unit tinyint not null,
	distance_divisor_value varchar(10) not null,
	break_time varchar(20) null
)ENGINE=INNODB;
alter table LAP_TRACK add constraint fkUSER foreign key (id_user) references USER(id_user);