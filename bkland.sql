use bkland;

drop table if exists `role`;
create table `role`(
	id int primary key auto_increment,
    name varchar(20)
);

insert into `role`(`name`) values ('ROLE_USER'), ('ROLE_AGENCY'), ('ROLE_ENTERPRISE'), ('ROLE_ADMIN');

drop table if exists `user`;
create table `user`(
	id varchar(255) primary key,
    first_name varchar(255),
    middle_name varchar(20),
    last_name varchar(20),
    username varchar(50),
    email varchar(100),
    `password` varchar(255),
    identification varchar(12),
    gender varchar(10),
    province_code varchar(50),
    district_code varchar(50),
    ward_code varchar(50),
    address varchar(255),
    phone_number varchar(10),
    date_of_birth date,
    `enable` tinyint,
    account_balance bigint,
    create_at timestamp,
    create_id varchar(255),
    update_at timestamp,
    update_id varchar(255),
    foreign key (province_code) references provinces(code),
    foreign key (district_code) references districts(code),
    foreign key (ward_code) references wards(code)
);

drop table if exists `user_role`;
create table `user_role`(
	user_id varchar(255),
    role_id int,
    primary key(user_id, role_id),
    foreign key (user_id) references user(id),
    foreign key (role_id) references role(id)
);
