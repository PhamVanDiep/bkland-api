-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: bkland
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `refresh_token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `middle_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `identification` varchar(12) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `province_code` varchar(50) DEFAULT NULL,
  `district_code` varchar(50) DEFAULT NULL,
  `ward_code` varchar(50) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_number` varchar(10) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `enable` tinyint DEFAULT NULL,
  `account_balance` bigint DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `province_code` (`province_code`),
  KEY `district_code` (`district_code`),
  KEY `ward_code` (`ward_code`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`province_code`) REFERENCES `provinces` (`code`),
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`district_code`) REFERENCES `districts` (`code`),
  CONSTRAINT `user_ibfk_3` FOREIGN KEY (`ward_code`) REFERENCES `wards` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` varchar(255) NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-27 14:24:51
drop table if exists `about`;
create table `about`(
	id int primary key auto_increment,
    name varchar(255),
    address varchar(255),
    phone_number varchar(10),
    email varchar(100),
    create_by varchar(255),
    update_by varchar(255),
    create_at varchar(255),
    update_at datetime
);

create table info_type(
	id int primary key auto_increment,
    name varchar(255),
    parent int,
    create_by varchar(255),
    create_at datetime,
    update_by varchar(255),
    update_at datetime
);

create table info_post(
	id bigint primary key auto_increment,
    info_type_id int,
    title varchar(255),
    content text,
    create_by varchar(255),
    create_at datetime,
    update_by varchar(255),
    update_at datetime,
    foreign key(info_type_id) references info_type(id)
);

create table system_chat(
	id bigint primary key auto_increment,
    device_info varchar(255),
    user_id	varchar(255),
    message text,
    is_send_by_admin tinyint,
    send_at datetime,
    update_by varchar(255),
    update_at datetime
);

create table real_estate_post(
	id varchar(255) primary key,
	`type` varchar(20) not null,
    `owner_id` varchar(255) not null,
    title varchar(255) not null,
    description text,
    address_show varchar(255) not null,
    area double not null,
    price double not null,
    province_code varchar(50) not null,
    district_code varchar(50) not null,
    ward_code varchar(50) not null,
    status varchar(20) not null,
    lat double not null,
    lng double not null,
    enable tinyint not null,
    post_type int not null,
    priority int not null,
    period int not null,
    create_by varchar(255),
    update_by varchar(255),
    create_at datetime,
    update_at datetime,
    foreign key(owner_id) references user(id),
    foreign key(province_code) references provinces(code),
    foreign key(district_code) references districts(code),
    foreign key(ward_code) references wards(code)
);
alter table real_estate_post add column direction varchar(20) not null;

create table post_media(
	id varchar(100) primary key not null,
	media_type varchar(50) not null,
    post_id varchar(255) not null,
    post_type varchar(100) not null,
    create_by varchar(255),
    create_at datetime,
    update_by varchar(255),
    update_at datetime
);

create table plot(
	real_estate_post_id varchar(255) primary key,
    front_width double not null,
    behind_width double not null,
    foreign key(real_estate_post_id) references real_estate_post(id)
);

create table apartment(
	real_estate_post_id varchar(255) primary key,
    floor_no int,
    no_bedroom int,
    no_bathroom int,
    furniture varchar(255),
    balcony_direction varchar(50),
    construction text,
    foreign key(real_estate_post_id) references real_estate_post(id)
);

create table house(
	real_estate_post_id varchar(255) primary key,
    no_floor int,
    no_bedroom int,
    no_bathroom int,
    furniture varchar(255),
    balcony_direction varchar(50),
    front_width double,
    behind_width double,
    street_width double,
    foreign key(real_estate_post_id) references real_estate_post(id)
);

create table interested(
	id bigint primary key auto_increment,
    user_id varchar(255),
    device_info varchar(255),
    real_estate_post_id varchar(255),
    create_by varchar(255),
    create_at datetime,
    update_by varchar(255),
    update_at datetime
);

create table special_account(
	user_id varchar(255) primary key,
    monthly_charge bigint,
    is_agency tinyint,
    last_paid datetime,
    foreign key(user_id) references user(id)
);

create table project(
	id bigint auto_increment primary key,
    title varchar(255) not null,
    content text not null,
    create_by varchar(255),
    create_at datetime,
    update_by varchar(255),
    update_at datetime
);

create table report_type(
	id int primary key auto_increment,
    name varchar(255),
    is_forum tinyint,
    create_by varchar(255),
    create_at datetime,
	update_by varchar(255),
    update_at datetime
);

create table post_report(
	id bigint primary key auto_increment,
    description text,
    post_id varchar(255),
    is_forum_post tinyint,
    user_id varchar(255),
    create_by varchar(255),
    create_at datetime
);

create table post_report_type(
	post_report_id bigint,
    report_type_id int,
    primary key(post_report_id, report_type_id),
    foreign key(post_report_id) references post_report(id),
    foreign key(report_type_id) references report_type(id)
);

create table chat_room(
	id int auto_increment primary key,
    first_user_id varchar(255) not null,
    second_user_id varchar(255) not null,
	create_by varchar(255),
    create_at datetime,
    update_by varchar(255),
    update_at datetime
);
alter table chat_room add foreign key(first_user_id) references user(id);
alter table chat_room add foreign key(second_user_id) references user(id);

create table message(
	id bigint primary key auto_increment,
    sender varchar(255),
    message text,
    chat_room_id int,
    create_by varchar(255),
    create_at datetime,
    update_by varchar(255),
    update_at datetime,
    foreign key(sender) references user(id),
    foreign key(chat_room_id) references chat_room(id)
);

create table price_fluctuation(
	id bigint primary key auto_increment,
    user_id varchar(255) not null,
    district_code varchar(50),
    district_price bigint,
    enable tinyint,
    create_by varchar(255),
    create_at datetime,
    update_by varchar(255),
    update_at datetime,
	foreign key(user_id) references user(id),
    foreign key(district_code) references districts(code)
);

create table agency_district(
	user_id varchar(255) not null,
    district_code varchar(50) not null,
    primary key(user_id, district_code),
    foreign key(user_id) references special_account(user_id),
    foreign key(district_code) references districts(code)
);

create table post_comment(
	id bigint auto_increment primary key,
    post_id varchar(255) not null,
    user_id varchar(255) not null,
    is_forum_post tinyint not null,
    create_by varchar(255),
    update_by varchar(255),
    create_at datetime,
    update_at datetime
);

create table charge(
	id bigint primary key auto_increment,
    so_tien bigint not null,
    user_id varchar(255) not null,
    account_balance bigint not null,
    charge_type varchar(50) not null,
    create_at datetime not null,
    create_by varchar(255),
    foreign key(user_id) references user(id)
);

create table transfer_charge(
	charge_id bigint primary key,
    media_id varchar(100),
    status varchar(50),
    foreign key(charge_id) references charge(id)
);

create table forum_post(
	id varchar(255) primary key not null,
    content text,
    create_by varchar(255) not null,
    create_at datetime not null,
    update_by varchar(255),
    update_at datetime,
    foreign key(create_by) references user(id)
);

create table forum_post_like(
	forum_post_id varchar(255) not null,
    user_id varchar(255) not null,
    create_by varchar(255),
    create_at datetime,
    primary key(forum_post_id, user_id),
    foreign key(forum_post_id) references forum_post(id),
    foreign key(user_id) references user(id)
);

create table user_device_token(
	id int primary key auto_increment,
    user_id varchar(255) not null,
    device_info varchar(255),
    notify_token varchar(255),
    enable tinyint,
    create_by varchar(255),
    create_at datetime,
    update_by varchar(255),
    update_at datetime
);