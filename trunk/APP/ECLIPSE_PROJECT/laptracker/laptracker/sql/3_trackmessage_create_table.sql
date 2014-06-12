create table LAP (
	id_lap int not null auto_increment primary key,
	id_lap_track int not null,
	identifier varchar(10) not null,
	format varchar(10) not null,
	time varchar(10) not null,
	distance_unit tinyint null,
	distance_value varchar(10) not null,
	break_time varchar(20) null
)ENGINE=INNODB;
alter table LAP add constraint fkLAPLAPTRACK foreign key (id_lap_track) references LAP_TRACK(id_lap_track);