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
-- Table structure for table `about`
--

DROP TABLE IF EXISTS `about`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `about` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_number` varchar(10) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `create_at` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `administrative_regions`
--

DROP TABLE IF EXISTS `administrative_regions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrative_regions` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `name_en` varchar(255) NOT NULL,
  `code_name` varchar(255) DEFAULT NULL,
  `code_name_en` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `administrative_units`
--

DROP TABLE IF EXISTS `administrative_units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrative_units` (
  `id` int NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `full_name_en` varchar(255) DEFAULT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `short_name_en` varchar(255) DEFAULT NULL,
  `code_name` varchar(255) DEFAULT NULL,
  `code_name_en` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `agency_district`
--

DROP TABLE IF EXISTS `agency_district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agency_district` (
  `user_id` varchar(255) NOT NULL,
  `district_code` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`district_code`),
  KEY `district_code` (`district_code`),
  CONSTRAINT `agency_district_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `special_account` (`user_id`),
  CONSTRAINT `agency_district_ibfk_2` FOREIGN KEY (`district_code`) REFERENCES `districts` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `apartment`
--

DROP TABLE IF EXISTS `apartment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apartment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `real_estate_post_id` varchar(255) NOT NULL,
  `floor_no` int DEFAULT NULL,
  `no_bedroom` int DEFAULT NULL,
  `no_bathroom` int DEFAULT NULL,
  `furniture` varchar(255) DEFAULT NULL,
  `balcony_direction` varchar(50) DEFAULT NULL,
  `construction` text,
  PRIMARY KEY (`id`),
  KEY `apartment_ibfk_1` (`real_estate_post_id`),
  CONSTRAINT `apartment_ibfk_1` FOREIGN KEY (`real_estate_post_id`) REFERENCES `real_estate_post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `charge`
--

DROP TABLE IF EXISTS `charge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `charge` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `so_tien` bigint NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `account_balance` bigint NOT NULL,
  `charge_type` varchar(50) NOT NULL,
  `create_at` datetime NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `charge_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_user_id` varchar(255) NOT NULL,
  `second_user_id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `districts`
--

DROP TABLE IF EXISTS `districts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `districts` (
  `code` varchar(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `name_en` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `full_name_en` varchar(255) DEFAULT NULL,
  `code_name` varchar(255) DEFAULT NULL,
  `province_code` varchar(20) DEFAULT NULL,
  `administrative_unit_id` int DEFAULT NULL,
  PRIMARY KEY (`code`),
  KEY `idx_districts_province` (`province_code`),
  KEY `idx_districts_unit` (`administrative_unit_id`),
  CONSTRAINT `districts_administrative_unit_id_fkey` FOREIGN KEY (`administrative_unit_id`) REFERENCES `administrative_units` (`id`),
  CONSTRAINT `districts_province_code_fkey` FOREIGN KEY (`province_code`) REFERENCES `provinces` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `forum_post`
--

DROP TABLE IF EXISTS `forum_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum_post` (
  `id` varchar(255) NOT NULL,
  `content` text,
  `create_by` varchar(255) NOT NULL,
  `create_at` datetime NOT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_by` (`create_by`),
  CONSTRAINT `forum_post_ibfk_1` FOREIGN KEY (`create_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `forum_post_like`
--

DROP TABLE IF EXISTS `forum_post_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum_post_like` (
  `forum_post_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  PRIMARY KEY (`forum_post_id`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `forum_post_like_ibfk_1` FOREIGN KEY (`forum_post_id`) REFERENCES `forum_post` (`id`),
  CONSTRAINT `forum_post_like_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `house`
--

DROP TABLE IF EXISTS `house`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `house` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `real_estate_post_id` varchar(255) NOT NULL,
  `no_floor` int DEFAULT NULL,
  `no_bedroom` int DEFAULT NULL,
  `no_bathroom` int DEFAULT NULL,
  `furniture` varchar(255) DEFAULT NULL,
  `balcony_direction` varchar(50) DEFAULT NULL,
  `front_width` double DEFAULT NULL,
  `behind_width` double DEFAULT NULL,
  `street_width` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `house_ibfk_1` (`real_estate_post_id`),
  CONSTRAINT `house_ibfk_1` FOREIGN KEY (`real_estate_post_id`) REFERENCES `real_estate_post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_post`
--

DROP TABLE IF EXISTS `info_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `info_post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `info_type_id` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `create_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `info_type_id` (`info_type_id`),
  CONSTRAINT `info_post_ibfk_1` FOREIGN KEY (`info_type_id`) REFERENCES `info_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_type`
--

DROP TABLE IF EXISTS `info_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `info_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `parent` int DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interested`
--

DROP TABLE IF EXISTS `interested`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interested` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `device_info` varchar(255) DEFAULT NULL,
  `real_estate_post_id` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sender` varchar(255) DEFAULT NULL,
  `message` text,
  `chat_room_id` int DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sender` (`sender`),
  KEY `chat_room_id` (`chat_room_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`sender`) REFERENCES `user` (`id`),
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `plot`
--

DROP TABLE IF EXISTS `plot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plot` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `real_estate_post_id` varchar(255) NOT NULL,
  `front_width` double NOT NULL,
  `behind_width` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `real_estate_post_id` (`real_estate_post_id`),
  CONSTRAINT `plot_ibfk_1` FOREIGN KEY (`real_estate_post_id`) REFERENCES `real_estate_post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_comment`
--

DROP TABLE IF EXISTS `post_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `is_forum_post` tinyint NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_media`
--

DROP TABLE IF EXISTS `post_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_media` (
  `id` varchar(100) NOT NULL,
  `media_type` varchar(50) DEFAULT NULL,
  `post_id` varchar(255) DEFAULT NULL,
  `post_type` varchar(100) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_pay`
--

DROP TABLE IF EXISTS `post_pay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_pay` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) NOT NULL,
  `price` int NOT NULL,
  `real_estate_post_id` varchar(255) NOT NULL,
  `create_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `real_estate_post_id` (`real_estate_post_id`),
  CONSTRAINT `post_pay_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `post_pay_ibfk_2` FOREIGN KEY (`real_estate_post_id`) REFERENCES `real_estate_post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_report`
--

DROP TABLE IF EXISTS `post_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_report` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` text,
  `post_id` varchar(255) DEFAULT NULL,
  `is_forum_post` tinyint DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_report_type`
--

DROP TABLE IF EXISTS `post_report_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_report_type` (
  `post_report_id` bigint NOT NULL,
  `report_type_id` int NOT NULL,
  PRIMARY KEY (`post_report_id`,`report_type_id`),
  KEY `report_type_id` (`report_type_id`),
  CONSTRAINT `post_report_type_ibfk_1` FOREIGN KEY (`post_report_id`) REFERENCES `post_report` (`id`),
  CONSTRAINT `post_report_type_ibfk_2` FOREIGN KEY (`report_type_id`) REFERENCES `report_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `price_fluctuation`
--

DROP TABLE IF EXISTS `price_fluctuation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `price_fluctuation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) NOT NULL,
  `district_code` varchar(50) DEFAULT NULL,
  `district_price` bigint DEFAULT NULL,
  `enable` tinyint DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `district_code` (`district_code`),
  CONSTRAINT `price_fluctuation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `price_fluctuation_ibfk_2` FOREIGN KEY (`district_code`) REFERENCES `districts` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `provinces`
--

DROP TABLE IF EXISTS `provinces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provinces` (
  `code` varchar(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `name_en` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `full_name_en` varchar(255) DEFAULT NULL,
  `code_name` varchar(255) DEFAULT NULL,
  `administrative_unit_id` int DEFAULT NULL,
  `administrative_region_id` int DEFAULT NULL,
  PRIMARY KEY (`code`),
  KEY `idx_provinces_region` (`administrative_region_id`),
  KEY `idx_provinces_unit` (`administrative_unit_id`),
  CONSTRAINT `provinces_administrative_region_id_fkey` FOREIGN KEY (`administrative_region_id`) REFERENCES `administrative_regions` (`id`),
  CONSTRAINT `provinces_administrative_unit_id_fkey` FOREIGN KEY (`administrative_unit_id`) REFERENCES `administrative_units` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `real_estate_post`
--

DROP TABLE IF EXISTS `real_estate_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `real_estate_post` (
  `id` varchar(255) NOT NULL,
  `type` varchar(20) NOT NULL,
  `owner_id` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `address_show` varchar(255) NOT NULL,
  `area` double NOT NULL,
  `price` double NOT NULL,
  `province_code` varchar(50) NOT NULL,
  `district_code` varchar(50) NOT NULL,
  `ward_code` varchar(50) NOT NULL,
  `status` varchar(20) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `enable` tinyint NOT NULL,
  `priority` int NOT NULL,
  `period` int NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  `direction` varchar(20) NOT NULL,
  `is_sell` tinyint DEFAULT NULL,
  `street` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `owner_id` (`owner_id`),
  KEY `province_code` (`province_code`),
  KEY `district_code` (`district_code`),
  KEY `ward_code` (`ward_code`),
  CONSTRAINT `real_estate_post_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`),
  CONSTRAINT `real_estate_post_ibfk_2` FOREIGN KEY (`province_code`) REFERENCES `provinces` (`code`),
  CONSTRAINT `real_estate_post_ibfk_3` FOREIGN KEY (`district_code`) REFERENCES `districts` (`code`),
  CONSTRAINT `real_estate_post_ibfk_4` FOREIGN KEY (`ward_code`) REFERENCES `wards` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_type`
--

DROP TABLE IF EXISTS `report_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `is_forum` tinyint DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
-- Table structure for table `special_account`
--

DROP TABLE IF EXISTS `special_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `special_account` (
  `user_id` varchar(255) NOT NULL,
  `monthly_charge` bigint DEFAULT NULL,
  `is_agency` tinyint DEFAULT NULL,
  `last_paid` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `special_account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_chat`
--

DROP TABLE IF EXISTS `system_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_chat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `device_info` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `message` text,
  `is_send_by_admin` tinyint DEFAULT NULL,
  `send_at` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transfer_charge`
--

DROP TABLE IF EXISTS `transfer_charge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transfer_charge` (
  `charge_id` bigint NOT NULL,
  `media_id` varchar(100) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`charge_id`),
  CONSTRAINT `transfer_charge_ibfk_1` FOREIGN KEY (`charge_id`) REFERENCES `charge` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `avatar_url` text,
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
-- Table structure for table `user_device_token`
--

DROP TABLE IF EXISTS `user_device_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_device_token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) NOT NULL,
  `device_info` varchar(255) DEFAULT NULL,
  `notify_token` varchar(255) DEFAULT NULL,
  `enable` tinyint DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  `is_logout` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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

--
-- Table structure for table `wards`
--

DROP TABLE IF EXISTS `wards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wards` (
  `code` varchar(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `name_en` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `full_name_en` varchar(255) DEFAULT NULL,
  `code_name` varchar(255) DEFAULT NULL,
  `district_code` varchar(20) DEFAULT NULL,
  `administrative_unit_id` int DEFAULT NULL,
  PRIMARY KEY (`code`),
  KEY `idx_wards_district` (`district_code`),
  KEY `idx_wards_unit` (`administrative_unit_id`),
  CONSTRAINT `wards_administrative_unit_id_fkey` FOREIGN KEY (`administrative_unit_id`) REFERENCES `administrative_units` (`id`),
  CONSTRAINT `wards_district_code_fkey` FOREIGN KEY (`district_code`) REFERENCES `districts` (`code`)
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

-- Dump completed on 2023-05-11 17:06:05
