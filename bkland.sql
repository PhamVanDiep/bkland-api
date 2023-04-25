-- MySQL dump 10.13  Distrib 8.0.32, for Linux (x86_64)
--
-- Host: localhost    Database: bkland
-- ------------------------------------------------------
-- Server version	8.0.32-0ubuntu0.22.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
  `name` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `phone_number` varchar(30) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `create_id` bigint DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_id` bigint DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_id` (`create_id`),
  KEY `update_id` (`update_id`),
  CONSTRAINT `about_ibfk_1` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`),
  CONSTRAINT `about_ibfk_2` FOREIGN KEY (`update_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `about`
--

LOCK TABLES `about` WRITE;
/*!40000 ALTER TABLE `about` DISABLE KEYS */;
/*!40000 ALTER TABLE `about` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `administrative_regions`
--

LOCK TABLES `administrative_regions` WRITE;
/*!40000 ALTER TABLE `administrative_regions` DISABLE KEYS */;
INSERT INTO `administrative_regions` VALUES (1,'Đông Bắc Bộ','Northeast','dong_bac_bo','northest'),(2,'Tây Bắc Bộ','Northwest','tay_bac_bo','northwest'),(3,'Đồng bằng sông Hồng','Red River Delta','dong_bang_song_hong','red_river_delta'),(4,'Bắc Trung Bộ','North Central Coast','bac_trung_bo','north_central_coast'),(5,'Duyên hải Nam Trung Bộ','South Central Coast','duyen_hai_nam_trung_bo','south_central_coast'),(6,'Tây Nguyên','Central Highlands','tay_nguyen','central_highlands'),(7,'Đông Nam Bộ','Southeast','dong_nam_bo','southeast'),(8,'Đồng bằng sông Cửu Long','Mekong River Delta','dong_bang_song_cuu_long','southwest');
/*!40000 ALTER TABLE `administrative_regions` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `administrative_units`
--

LOCK TABLES `administrative_units` WRITE;
/*!40000 ALTER TABLE `administrative_units` DISABLE KEYS */;
INSERT INTO `administrative_units` VALUES (1,'Thành phố trực thuộc trung ương','Municipality','Thành phố','City','thanh_pho_truc_thuoc_trung_uong','municipality'),(2,'Tỉnh','Province','Tỉnh','Province','tinh','province'),(3,'Thành phố thuộc thành phố trực thuộc trung ương','Municipal city','Thành phố','City','thanh_pho_thuoc_thanh_pho_truc_thuoc_trung_uong','municipal_city'),(4,'Thành phố thuộc tỉnh','Provincial city','Thành phố','City','thanh_pho_thuoc_tinh','provincial_city'),(5,'Quận','Urban district','Quận','District','quan','urban_district'),(6,'Thị xã','District-level town','Thị xã','Town','thi_xa','district_level_town'),(7,'Huyện','District','Huyện','District','huyen','district'),(8,'Phường','Ward','Phường','Ward','phuong','ward'),(9,'Thị trấn','Commune-level town','Thị trấn','Township','thi_tran','commune_level_town'),(10,'Xã','Commune','Xã','Commune','xa','commune');
/*!40000 ALTER TABLE `administrative_units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apartment`
--

DROP TABLE IF EXISTS `apartment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apartment` (
  `real_estate_post_id` varchar(50) NOT NULL,
  `floor_no` int DEFAULT NULL,
  `no_bedroom` int DEFAULT NULL,
  `no_bathroom` int DEFAULT NULL,
  `furniture` text,
  `balcony_direction` varchar(20) DEFAULT NULL,
  `construction` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`real_estate_post_id`),
  CONSTRAINT `apartment_ibfk_1` FOREIGN KEY (`real_estate_post_id`) REFERENCES `real_estate_post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apartment`
--

LOCK TABLES `apartment` WRITE;
/*!40000 ALTER TABLE `apartment` DISABLE KEYS */;
/*!40000 ALTER TABLE `apartment` ENABLE KEYS */;
UNLOCK TABLES;

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
  KEY `districts_administrative_unit_id_fkey` (`administrative_unit_id`),
  KEY `districts_province_code_fkey` (`province_code`),
  CONSTRAINT `districts_administrative_unit_id_fkey` FOREIGN KEY (`administrative_unit_id`) REFERENCES `administrative_units` (`id`),
  CONSTRAINT `districts_province_code_fkey` FOREIGN KEY (`province_code`) REFERENCES `provinces` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `districts`
--

LOCK TABLES `districts` WRITE;
/*!40000 ALTER TABLE `districts` DISABLE KEYS */;
INSERT INTO `districts` VALUES ('001','Ba Đình','Ba Dinh','Quận Ba Đình','Ba Dinh District','ba_dinh','01',5),('002','Hoàn Kiếm','Hoan Kiem','Quận Hoàn Kiếm','Hoan Kiem District','hoan_kiem','01',5),('003','Tây Hồ','Tay Ho','Quận Tây Hồ','Tay Ho District','tay_ho','01',5),('004','Long Biên','Long Bien','Quận Long Biên','Long Bien District','long_bien','01',5),('005','Cầu Giấy','Cau Giay','Quận Cầu Giấy','Cau Giay District','cau_giay','01',5),('006','Đống Đa','Dong Da','Quận Đống Đa','Dong Da District','dong_da','01',5),('007','Hai Bà Trưng','Hai Ba Trung','Quận Hai Bà Trưng','Hai Ba Trung District','hai_ba_trung','01',5),('008','Hoàng Mai','Hoang Mai','Quận Hoàng Mai','Hoang Mai District','hoang_mai','01',5),('009','Thanh Xuân','Thanh Xuan','Quận Thanh Xuân','Thanh Xuan District','thanh_xuan','01',5),('016','Sóc Sơn','Soc Son','Huyện Sóc Sơn','Soc Son District','soc_son','01',7),('017','Đông Anh','Dong Anh','Huyện Đông Anh','Dong Anh District','dong_anh','01',7),('018','Gia Lâm','Gia Lam','Huyện Gia Lâm','Gia Lam District','gia_lam','01',7),('019','Nam Từ Liêm','Nam Tu Liem','Quận Nam Từ Liêm','Nam Tu Liem District','nam_tu_liem','01',5),('020','Thanh Trì','Thanh Tri','Huyện Thanh Trì','Thanh Tri District','thanh_tri','01',7),('021','Bắc Từ Liêm','Bac Tu Liem','Quận Bắc Từ Liêm','Bac Tu Liem District','bac_tu_liem','01',5),('250','Mê Linh','Me Linh','Huyện Mê Linh','Me Linh District','me_linh','01',7),('268','Hà Đông','Ha Dong','Quận Hà Đông','Ha Dong District','ha_dong','01',5),('269','Sơn Tây','Son Tay','Thị xã Sơn Tây','Son Tay Town','son_tay','01',6),('271','Ba Vì','Ba Vi','Huyện Ba Vì','Ba Vi District','ba_vi','01',7),('272','Phúc Thọ','Phuc Tho','Huyện Phúc Thọ','Phuc Tho District','phuc_tho','01',7),('273','Đan Phượng','Dan Phuong','Huyện Đan Phượng','Dan Phuong District','dan_phuong','01',7),('274','Hoài Đức','Hoai Duc','Huyện Hoài Đức','Hoai Duc District','hoai_duc','01',7),('275','Quốc Oai','Quoc Oai','Huyện Quốc Oai','Quoc Oai District','quoc_oai','01',7),('276','Thạch Thất','Thach That','Huyện Thạch Thất','Thach That District','thach_that','01',7),('277','Chương Mỹ','Chuong My','Huyện Chương Mỹ','Chuong My District','chuong_my','01',7),('278','Thanh Oai','Thanh Oai','Huyện Thanh Oai','Thanh Oai District','thanh_oai','01',7),('279','Thường Tín','Thuong Tin','Huyện Thường Tín','Thuong Tin District','thuong_tin','01',7),('280','Phú Xuyên','Phu Xuyen','Huyện Phú Xuyên','Phu Xuyen District','phu_xuyen','01',7),('281','Ứng Hòa','Ung Hoa','Huyện Ứng Hòa','Ung Hoa District','ung_hoa','01',7),('282','Mỹ Đức','My Duc','Huyện Mỹ Đức','My Duc District','my_duc','01',7);
/*!40000 ALTER TABLE `districts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `house`
--

DROP TABLE IF EXISTS `house`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `house` (
  `real_estate_post_id` varchar(50) NOT NULL,
  `no_floor` int DEFAULT NULL,
  `no_bedroom` int DEFAULT NULL,
  `no_bathroom` int DEFAULT NULL,
  `furniture` text,
  `balcony_direction` varchar(20) DEFAULT NULL,
  `front_width` double DEFAULT NULL,
  `behind_width` double DEFAULT NULL,
  `street_width` double DEFAULT NULL,
  `direction` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`real_estate_post_id`),
  CONSTRAINT `house_ibfk_1` FOREIGN KEY (`real_estate_post_id`) REFERENCES `real_estate_post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `house`
--

LOCK TABLES `house` WRITE;
/*!40000 ALTER TABLE `house` DISABLE KEYS */;
/*!40000 ALTER TABLE `house` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `info_post`
--

DROP TABLE IF EXISTS `info_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `info_post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `info_type_id` int DEFAULT NULL,
  `title` varchar(300) DEFAULT NULL,
  `content` text,
  `create_id` bigint DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_id` bigint DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_id` (`create_id`),
  KEY `update_id` (`update_id`),
  CONSTRAINT `info_post_ibfk_1` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`),
  CONSTRAINT `info_post_ibfk_2` FOREIGN KEY (`update_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info_post`
--

LOCK TABLES `info_post` WRITE;
/*!40000 ALTER TABLE `info_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `info_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `info_type`
--

DROP TABLE IF EXISTS `info_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `info_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent` varchar(10) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `create_id` bigint DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_id` bigint DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_id` (`create_id`),
  KEY `update_id` (`update_id`),
  CONSTRAINT `info_type_ibfk_1` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`),
  CONSTRAINT `info_type_ibfk_2` FOREIGN KEY (`update_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info_type`
--

LOCK TABLES `info_type` WRITE;
/*!40000 ALTER TABLE `info_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `info_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interested`
--

DROP TABLE IF EXISTS `interested`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interested` (
  `user_id` bigint NOT NULL,
  `real_estate_post_id` varchar(50) NOT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`,`real_estate_post_id`),
  KEY `real_estate_post_id` (`real_estate_post_id`),
  CONSTRAINT `interested_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `interested_ibfk_2` FOREIGN KEY (`real_estate_post_id`) REFERENCES `real_estate_post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interested`
--

LOCK TABLES `interested` WRITE;
/*!40000 ALTER TABLE `interested` DISABLE KEYS */;
/*!40000 ALTER TABLE `interested` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plot`
--

DROP TABLE IF EXISTS `plot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plot` (
  `real_estate_post_id` varchar(50) NOT NULL,
  `direction` varchar(20) DEFAULT NULL,
  `front_width` double DEFAULT NULL,
  `behind_width` double DEFAULT NULL,
  PRIMARY KEY (`real_estate_post_id`),
  CONSTRAINT `plot_ibfk_1` FOREIGN KEY (`real_estate_post_id`) REFERENCES `real_estate_post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plot`
--

LOCK TABLES `plot` WRITE;
/*!40000 ALTER TABLE `plot` DISABLE KEYS */;
/*!40000 ALTER TABLE `plot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_report`
--

DROP TABLE IF EXISTS `post_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_report` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` text,
  `create_id` bigint DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_id` (`create_id`),
  CONSTRAINT `post_report_ibfk_1` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_report`
--

LOCK TABLES `post_report` WRITE;
/*!40000 ALTER TABLE `post_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_report_type`
--

DROP TABLE IF EXISTS `post_report_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_report_type` (
  `report_type_id` int NOT NULL,
  `post_report_id` bigint NOT NULL,
  PRIMARY KEY (`report_type_id`,`post_report_id`),
  KEY `post_report_id` (`post_report_id`),
  CONSTRAINT `post_report_type_ibfk_1` FOREIGN KEY (`report_type_id`) REFERENCES `report_type` (`id`),
  CONSTRAINT `post_report_type_ibfk_2` FOREIGN KEY (`post_report_id`) REFERENCES `post_report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_report_type`
--

LOCK TABLES `post_report_type` WRITE;
/*!40000 ALTER TABLE `post_report_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_report_type` ENABLE KEYS */;
UNLOCK TABLES;

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
  KEY `provinces_administrative_region_id_fkey` (`administrative_region_id`),
  KEY `provinces_administrative_unit_id_fkey` (`administrative_unit_id`),
  CONSTRAINT `provinces_administrative_region_id_fkey` FOREIGN KEY (`administrative_region_id`) REFERENCES `administrative_regions` (`id`),
  CONSTRAINT `provinces_administrative_unit_id_fkey` FOREIGN KEY (`administrative_unit_id`) REFERENCES `administrative_units` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provinces`
--

LOCK TABLES `provinces` WRITE;
/*!40000 ALTER TABLE `provinces` DISABLE KEYS */;
INSERT INTO `provinces` VALUES ('01','Hà Nội','Ha Noi','Thành phố Hà Nội','Ha Noi City','ha_noi',1,3);
/*!40000 ALTER TABLE `provinces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `real_estate_bought`
--

DROP TABLE IF EXISTS `real_estate_bought`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `real_estate_bought` (
  `real_estate_post_id` varchar(50) NOT NULL,
  `rate` double NOT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `create_id` bigint DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`real_estate_post_id`),
  KEY `create_id` (`create_id`),
  CONSTRAINT `real_estate_bought_ibfk_1` FOREIGN KEY (`real_estate_post_id`) REFERENCES `real_estate_post` (`id`),
  CONSTRAINT `real_estate_bought_ibfk_2` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `real_estate_bought`
--

LOCK TABLES `real_estate_bought` WRITE;
/*!40000 ALTER TABLE `real_estate_bought` DISABLE KEYS */;
/*!40000 ALTER TABLE `real_estate_bought` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `real_estate_post`
--

DROP TABLE IF EXISTS `real_estate_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `real_estate_post` (
  `id` varchar(50) NOT NULL,
  `type_id` int DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  `title` text,
  `description` text,
  `address_show` text,
  `area` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `province_code` varchar(20) DEFAULT NULL,
  `district_code` varchar(20) DEFAULT NULL,
  `ward_code` varchar(20) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL,
  `create_id` bigint DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_id` bigint DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `owner_id` (`owner_id`),
  KEY `type_id` (`type_id`),
  KEY `create_id` (`create_id`),
  KEY `update_id` (`update_id`),
  KEY `province_code` (`province_code`),
  KEY `district_code` (`district_code`),
  KEY `ward_code` (`ward_code`),
  CONSTRAINT `real_estate_post_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`),
  CONSTRAINT `real_estate_post_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `real_estate_type` (`id`),
  CONSTRAINT `real_estate_post_ibfk_3` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`),
  CONSTRAINT `real_estate_post_ibfk_4` FOREIGN KEY (`update_id`) REFERENCES `user` (`id`),
  CONSTRAINT `real_estate_post_ibfk_5` FOREIGN KEY (`province_code`) REFERENCES `provinces` (`code`),
  CONSTRAINT `real_estate_post_ibfk_6` FOREIGN KEY (`district_code`) REFERENCES `districts` (`code`),
  CONSTRAINT `real_estate_post_ibfk_7` FOREIGN KEY (`ward_code`) REFERENCES `wards` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `real_estate_post`
--

LOCK TABLES `real_estate_post` WRITE;
/*!40000 ALTER TABLE `real_estate_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `real_estate_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `real_estate_post_media`
--

DROP TABLE IF EXISTS `real_estate_post_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `real_estate_post_media` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `real_estate_post_id` varchar(50) DEFAULT NULL,
  `link` text,
  PRIMARY KEY (`id`),
  KEY `real_estate_post_id` (`real_estate_post_id`),
  CONSTRAINT `real_estate_post_media_ibfk_1` FOREIGN KEY (`real_estate_post_id`) REFERENCES `real_estate_post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `real_estate_post_media`
--

LOCK TABLES `real_estate_post_media` WRITE;
/*!40000 ALTER TABLE `real_estate_post_media` DISABLE KEYS */;
/*!40000 ALTER TABLE `real_estate_post_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `real_estate_sold`
--

DROP TABLE IF EXISTS `real_estate_sold`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `real_estate_sold` (
  `real_estate_post_id` varchar(50) NOT NULL,
  `sold_for_user_id` bigint NOT NULL,
  `sold_at` datetime NOT NULL,
  `create_id` bigint DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  PRIMARY KEY (`real_estate_post_id`),
  KEY `sold_for_user_id` (`sold_for_user_id`),
  KEY `create_id` (`create_id`),
  CONSTRAINT `real_estate_sold_ibfk_1` FOREIGN KEY (`real_estate_post_id`) REFERENCES `real_estate_post` (`id`),
  CONSTRAINT `real_estate_sold_ibfk_2` FOREIGN KEY (`sold_for_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `real_estate_sold_ibfk_3` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `real_estate_sold`
--

LOCK TABLES `real_estate_sold` WRITE;
/*!40000 ALTER TABLE `real_estate_sold` DISABLE KEYS */;
/*!40000 ALTER TABLE `real_estate_sold` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `real_estate_type`
--

DROP TABLE IF EXISTS `real_estate_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `real_estate_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `real_estate_type`
--

LOCK TABLES `real_estate_type` WRITE;
/*!40000 ALTER TABLE `real_estate_type` DISABLE KEYS */;
INSERT INTO `real_estate_type` VALUES (1,'HOUSE'),(2,'APARTMENT'),(3,'PLOT');
/*!40000 ALTER TABLE `real_estate_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report_type`
--

DROP TABLE IF EXISTS `report_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_type`
--

LOCK TABLES `report_type` WRITE;
/*!40000 ALTER TABLE `report_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `report_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_USER'),(2,'ROLE_EMPLOYEE'),(3,'ROLE_ADMIN'),(4,'ROLE_ACCOUNT_MANAGEMENT');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) DEFAULT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `identification` varchar(20) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `ward` varchar(50) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `date_of_birth` datetime DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `create_id` bigint DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

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
  KEY `wards_administrative_unit_id_fkey` (`administrative_unit_id`),
  KEY `wards_district_code_fkey` (`district_code`),
  CONSTRAINT `wards_administrative_unit_id_fkey` FOREIGN KEY (`administrative_unit_id`) REFERENCES `administrative_units` (`id`),
  CONSTRAINT `wards_district_code_fkey` FOREIGN KEY (`district_code`) REFERENCES `districts` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wards`
--

LOCK TABLES `wards` WRITE;
/*!40000 ALTER TABLE `wards` DISABLE KEYS */;
INSERT INTO `wards` VALUES ('00001','Phúc Xá','Phuc Xa','Phường Phúc Xá','Phuc Xa Ward','phuc_xa','001',8),('00004','Trúc Bạch','Truc Bach','Phường Trúc Bạch','Truc Bach Ward','truc_bach','001',8),('00006','Vĩnh Phúc','Vinh Phuc','Phường Vĩnh Phúc','Vinh Phuc Ward','vinh_phuc','001',8),('00007','Cống Vị','Cong Vi','Phường Cống Vị','Cong Vi Ward','cong_vi','001',8),('00008','Liễu Giai','Lieu Giai','Phường Liễu Giai','Lieu Giai Ward','lieu_giai','001',8),('00010','Nguyễn Trung Trực','Nguyen Trung Truc','Phường Nguyễn Trung Trực','Nguyen Trung Truc Ward','nguyen_trung_truc','001',8),('00013','Quán Thánh','Quan Thanh','Phường Quán Thánh','Quan Thanh Ward','quan_thanh','001',8),('00016','Ngọc Hà','Ngoc Ha','Phường Ngọc Hà','Ngoc Ha Ward','ngoc_ha','001',8),('00019','Điện Biên','Dien Bien','Phường Điện Biên','Dien Bien Ward','dien_bien','001',8),('00022','Đội Cấn','Doi Can','Phường Đội Cấn','Doi Can Ward','doi_can','001',8),('00025','Ngọc Khánh','Ngoc Khanh','Phường Ngọc Khánh','Ngoc Khanh Ward','ngoc_khanh','001',8),('00028','Kim Mã','Kim Ma','Phường Kim Mã','Kim Ma Ward','kim_ma','001',8),('00031','Giảng Võ','Giang Vo','Phường Giảng Võ','Giang Vo Ward','giang_vo','001',8),('00034','Thành Công','Thanh Cong','Phường Thành Công','Thanh Cong Ward','thanh_cong','001',8),('00037','Phúc Tân','Phuc Tan','Phường Phúc Tân','Phuc Tan Ward','phuc_tan','002',8),('00040','Đồng Xuân','Dong Xuan','Phường Đồng Xuân','Dong Xuan Ward','dong_xuan','002',8),('00043','Hàng Mã','Hang Ma','Phường Hàng Mã','Hang Ma Ward','hang_ma','002',8),('00046','Hàng Buồm','Hang Buom','Phường Hàng Buồm','Hang Buom Ward','hang_buom','002',8),('00049','Hàng Đào','Hang Dao','Phường Hàng Đào','Hang Dao Ward','hang_dao','002',8),('00052','Hàng Bồ','Hang Bo','Phường Hàng Bồ','Hang Bo Ward','hang_bo','002',8),('00055','Cửa Đông','Cua Dong','Phường Cửa Đông','Cua Dong Ward','cua_dong','002',8),('00058','Lý Thái Tổ','Ly Thai To','Phường Lý Thái Tổ','Ly Thai To Ward','ly_thai_to','002',8),('00061','Hàng Bạc','Hang Bac','Phường Hàng Bạc','Hang Bac Ward','hang_bac','002',8),('00064','Hàng Gai','Hang Gai','Phường Hàng Gai','Hang Gai Ward','hang_gai','002',8),('00067','Chương Dương','Chuong Duong','Phường Chương Dương','Chuong Duong Ward','chuong_duong','002',8),('00070','Hàng Trống','Hang Trong','Phường Hàng Trống','Hang Trong Ward','hang_trong','002',8),('00073','Cửa Nam','Cua Nam','Phường Cửa Nam','Cua Nam Ward','cua_nam','002',8),('00076','Hàng Bông','Hang Bong','Phường Hàng Bông','Hang Bong Ward','hang_bong','002',8),('00079','Tràng Tiền','Trang Tien','Phường Tràng Tiền','Trang Tien Ward','trang_tien','002',8),('00082','Trần Hưng Đạo','Tran Hung Dao','Phường Trần Hưng Đạo','Tran Hung Dao Ward','tran_hung_dao','002',8),('00085','Phan Chu Trinh','Phan Chu Trinh','Phường Phan Chu Trinh','Phan Chu Trinh Ward','phan_chu_trinh','002',8),('00088','Hàng Bài','Hang Bai','Phường Hàng Bài','Hang Bai Ward','hang_bai','002',8),('00091','Phú Thượng','Phu Thuong','Phường Phú Thượng','Phu Thuong Ward','phu_thuong','003',8),('00094','Nhật Tân','Nhat Tan','Phường Nhật Tân','Nhat Tan Ward','nhat_tan','003',8),('00097','Tứ Liên','Tu Lien','Phường Tứ Liên','Tu Lien Ward','tu_lien','003',8),('00100','Quảng An','Quang An','Phường Quảng An','Quang An Ward','quang_an','003',8),('00103','Xuân La','Xuan La','Phường Xuân La','Xuan La Ward','xuan_la','003',8),('00106','Yên Phụ','Yen Phu','Phường Yên Phụ','Yen Phu Ward','yen_phu','003',8),('00109','Bưởi','Buoi','Phường Bưởi','Buoi Ward','buoi','003',8),('00112','Thụy Khuê','Thuy Khue','Phường Thụy Khuê','Thuy Khue Ward','thuy_khue','003',8),('00115','Thượng Thanh','Thuong Thanh','Phường Thượng Thanh','Thuong Thanh Ward','thuong_thanh','004',8),('00118','Ngọc Thụy','Ngoc Thuy','Phường Ngọc Thụy','Ngoc Thuy Ward','ngoc_thuy','004',8),('00121','Giang Biên','Giang Bien','Phường Giang Biên','Giang Bien Ward','giang_bien','004',8),('00124','Đức Giang','Duc Giang','Phường Đức Giang','Duc Giang Ward','duc_giang','004',8),('00127','Việt Hưng','Viet Hung','Phường Việt Hưng','Viet Hung Ward','viet_hung','004',8),('00130','Gia Thụy','Gia Thuy','Phường Gia Thụy','Gia Thuy Ward','gia_thuy','004',8),('00133','Ngọc Lâm','Ngoc Lam','Phường Ngọc Lâm','Ngoc Lam Ward','ngoc_lam','004',8),('00136','Phúc Lợi','Phuc Loi','Phường Phúc Lợi','Phuc Loi Ward','phuc_loi','004',8),('00139','Bồ Đề','Bo De','Phường Bồ Đề','Bo De Ward','bo_de','004',8),('00142','Sài Đồng','Sai Dong','Phường Sài Đồng','Sai Dong Ward','sai_dong','004',8),('00145','Long Biên','Long Bien','Phường Long Biên','Long Bien Ward','long_bien','004',8),('00148','Thạch Bàn','Thach Ban','Phường Thạch Bàn','Thach Ban Ward','thach_ban','004',8),('00151','Phúc Đồng','Phuc Dong','Phường Phúc Đồng','Phuc Dong Ward','phuc_dong','004',8),('00154','Cự Khối','Cu Khoi','Phường Cự Khối','Cu Khoi Ward','cu_khoi','004',8),('00157','Nghĩa Đô','Nghia Do','Phường Nghĩa Đô','Nghia Do Ward','nghia_do','005',8),('00160','Nghĩa Tân','Nghia Tan','Phường Nghĩa Tân','Nghia Tan Ward','nghia_tan','005',8),('00163','Mai Dịch','Mai Dich','Phường Mai Dịch','Mai Dich Ward','mai_dich','005',8),('00166','Dịch Vọng','Dich Vong','Phường Dịch Vọng','Dich Vong Ward','dich_vong','005',8),('00167','Dịch Vọng Hậu','Dich Vong Hau','Phường Dịch Vọng Hậu','Dich Vong Hau Ward','dich_vong_hau','005',8),('00169','Quan Hoa','Quan Hoa','Phường Quan Hoa','Quan Hoa Ward','quan_hoa','005',8),('00172','Yên Hoà','Yen Hoa','Phường Yên Hoà','Yen Hoa Ward','yen_hoa','005',8),('00175','Trung Hoà','Trung Hoa','Phường Trung Hoà','Trung Hoa Ward','trung_hoa','005',8),('00178','Cát Linh','Cat Linh','Phường Cát Linh','Cat Linh Ward','cat_linh','006',8),('00181','Văn Miếu','Van Mieu','Phường Văn Miếu','Van Mieu Ward','van_mieu','006',8),('00184','Quốc Tử Giám','Quoc Tu Giam','Phường Quốc Tử Giám','Quoc Tu Giam Ward','quoc_tu_giam','006',8),('00187','Láng Thượng','Lang Thuong','Phường Láng Thượng','Lang Thuong Ward','lang_thuong','006',8),('00190','Ô Chợ Dừa','O Cho Dua','Phường Ô Chợ Dừa','O Cho Dua Ward','o_cho_dua','006',8),('00193','Văn Chương','Van Chuong','Phường Văn Chương','Van Chuong Ward','van_chuong','006',8),('00196','Hàng Bột','Hang Bot','Phường Hàng Bột','Hang Bot Ward','hang_bot','006',8),('00199','Láng Hạ','Lang Ha','Phường Láng Hạ','Lang Ha Ward','lang_ha','006',8),('00202','Khâm Thiên','Kham Thien','Phường Khâm Thiên','Kham Thien Ward','kham_thien','006',8),('00205','Thổ Quan','Tho Quan','Phường Thổ Quan','Tho Quan Ward','tho_quan','006',8),('00208','Nam Đồng','Nam Dong','Phường Nam Đồng','Nam Dong Ward','nam_dong','006',8),('00211','Trung Phụng','Trung Phung','Phường Trung Phụng','Trung Phung Ward','trung_phung','006',8),('00214','Quang Trung','Quang Trung','Phường Quang Trung','Quang Trung Ward','quang_trung','006',8),('00217','Trung Liệt','Trung Liet','Phường Trung Liệt','Trung Liet Ward','trung_liet','006',8),('00220','Phương Liên','Phuong Lien','Phường Phương Liên','Phuong Lien Ward','phuong_lien','006',8),('00223','Thịnh Quang','Thinh Quang','Phường Thịnh Quang','Thinh Quang Ward','thinh_quang','006',8),('00226','Trung Tự','Trung Tu','Phường Trung Tự','Trung Tu Ward','trung_tu','006',8),('00229','Kim Liên','Kim Lien','Phường Kim Liên','Kim Lien Ward','kim_lien','006',8),('00232','Phương Mai','Phuong Mai','Phường Phương Mai','Phuong Mai Ward','phuong_mai','006',8),('00235','Ngã Tư Sở','Nga Tu So','Phường Ngã Tư Sở','Nga Tu So Ward','nga_tu_so','006',8),('00238','Khương Thượng','Khuong Thuong','Phường Khương Thượng','Khuong Thuong Ward','khuong_thuong','006',8),('00241','Nguyễn Du','Nguyen Du','Phường Nguyễn Du','Nguyen Du Ward','nguyen_du','007',8),('00244','Bạch Đằng','Bach Dang','Phường Bạch Đằng','Bach Dang Ward','bach_dang','007',8),('00247','Phạm Đình Hổ','Pham Dinh Ho','Phường Phạm Đình Hổ','Pham Dinh Ho Ward','pham_dinh_ho','007',8),('00256','Lê Đại Hành','Le Dai Hanh','Phường Lê Đại Hành','Le Dai Hanh Ward','le_dai_hanh','007',8),('00259','Đồng Nhân','Dong Nhan','Phường Đồng Nhân','Dong Nhan Ward','dong_nhan','007',8),('00262','Phố Huế','Pho Hue','Phường Phố Huế','Pho Hue Ward','pho_hue','007',8),('00265','Đống Mác','Dong Mac','Phường Đống Mác','Dong Mac Ward','dong_mac','007',8),('00268','Thanh Lương','Thanh Luong','Phường Thanh Lương','Thanh Luong Ward','thanh_luong','007',8),('00271','Thanh Nhàn','Thanh Nhan','Phường Thanh Nhàn','Thanh Nhan Ward','thanh_nhan','007',8),('00274','Cầu Dền','Cau Den','Phường Cầu Dền','Cau Den Ward','cau_den','007',8),('00277','Bách Khoa','Bach Khoa','Phường Bách Khoa','Bach Khoa Ward','bach_khoa','007',8),('00280','Đồng Tâm','Dong Tam','Phường Đồng Tâm','Dong Tam Ward','dong_tam','007',8),('00283','Vĩnh Tuy','Vinh Tuy','Phường Vĩnh Tuy','Vinh Tuy Ward','vinh_tuy','007',8),('00286','Bạch Mai','Bach Mai','Phường Bạch Mai','Bach Mai Ward','bach_mai','007',8),('00289','Quỳnh Mai','Quynh Mai','Phường Quỳnh Mai','Quynh Mai Ward','quynh_mai','007',8),('00292','Quỳnh Lôi','Quynh Loi','Phường Quỳnh Lôi','Quynh Loi Ward','quynh_loi','007',8),('00295','Minh Khai','Minh Khai','Phường Minh Khai','Minh Khai Ward','minh_khai','007',8),('00298','Trương Định','Truong Dinh','Phường Trương Định','Truong Dinh Ward','truong_dinh','007',8),('00301','Thanh Trì','Thanh Tri','Phường Thanh Trì','Thanh Tri Ward','thanh_tri','008',8),('00304','Vĩnh Hưng','Vinh Hung','Phường Vĩnh Hưng','Vinh Hung Ward','vinh_hung','008',8),('00307','Định Công','Dinh Cong','Phường Định Công','Dinh Cong Ward','dinh_cong','008',8),('00310','Mai Động','Mai Dong','Phường Mai Động','Mai Dong Ward','mai_dong','008',8),('00313','Tương Mai','Tuong Mai','Phường Tương Mai','Tuong Mai Ward','tuong_mai','008',8),('00316','Đại Kim','Dai Kim','Phường Đại Kim','Dai Kim Ward','dai_kim','008',8),('00319','Tân Mai','Tan Mai','Phường Tân Mai','Tan Mai Ward','tan_mai','008',8),('00322','Hoàng Văn Thụ','Hoang Van Thu','Phường Hoàng Văn Thụ','Hoang Van Thu Ward','hoang_van_thu','008',8),('00325','Giáp Bát','Giap Bat','Phường Giáp Bát','Giap Bat Ward','giap_bat','008',8),('00328','Lĩnh Nam','Linh Nam','Phường Lĩnh Nam','Linh Nam Ward','linh_nam','008',8),('00331','Thịnh Liệt','Thinh Liet','Phường Thịnh Liệt','Thinh Liet Ward','thinh_liet','008',8),('00334','Trần Phú','Tran Phu','Phường Trần Phú','Tran Phu Ward','tran_phu','008',8),('00337','Hoàng Liệt','Hoang Liet','Phường Hoàng Liệt','Hoang Liet Ward','hoang_liet','008',8),('00340','Yên Sở','Yen So','Phường Yên Sở','Yen So Ward','yen_so','008',8),('00343','Nhân Chính','Nhan Chinh','Phường Nhân Chính','Nhan Chinh Ward','nhan_chinh','009',8),('00346','Thượng Đình','Thuong Dinh','Phường Thượng Đình','Thuong Dinh Ward','thuong_dinh','009',8),('00349','Khương Trung','Khuong Trung','Phường Khương Trung','Khuong Trung Ward','khuong_trung','009',8),('00352','Khương Mai','Khuong Mai','Phường Khương Mai','Khuong Mai Ward','khuong_mai','009',8),('00355','Thanh Xuân Trung','Thanh Xuan Trung','Phường Thanh Xuân Trung','Thanh Xuan Trung Ward','thanh_xuan_trung','009',8),('00358','Phương Liệt','Phuong Liet','Phường Phương Liệt','Phuong Liet Ward','phuong_liet','009',8),('00361','Hạ Đình','Ha Dinh','Phường Hạ Đình','Ha Dinh Ward','ha_dinh','009',8),('00364','Khương Đình','Khuong Dinh','Phường Khương Đình','Khuong Dinh Ward','khuong_dinh','009',8),('00367','Thanh Xuân Bắc','Thanh Xuan Bac','Phường Thanh Xuân Bắc','Thanh Xuan Bac Ward','thanh_xuan_bac','009',8),('00370','Thanh Xuân Nam','Thanh Xuan Nam','Phường Thanh Xuân Nam','Thanh Xuan Nam Ward','thanh_xuan_nam','009',8),('00373','Kim Giang','Kim Giang','Phường Kim Giang','Kim Giang Ward','kim_giang','009',8),('00376','Sóc Sơn','Soc Son','Thị trấn Sóc Sơn','Soc Son Township','soc_son','016',9),('00379','Bắc Sơn','Bac Son','Xã Bắc Sơn','Bac Son Commune','bac_son','016',10),('00382','Minh Trí','Minh Tri','Xã Minh Trí','Minh Tri Commune','minh_tri','016',10),('00385','Hồng Kỳ','Hong Ky','Xã Hồng Kỳ','Hong Ky Commune','hong_ky','016',10),('00388','Nam Sơn','Nam Son','Xã Nam Sơn','Nam Son Commune','nam_son','016',10),('00391','Trung Giã','Trung Gia','Xã Trung Giã','Trung Gia Commune','trung_gia','016',10),('00394','Tân Hưng','Tan Hung','Xã Tân Hưng','Tan Hung Commune','tan_hung','016',10),('00397','Minh Phú','Minh Phu','Xã Minh Phú','Minh Phu Commune','minh_phu','016',10),('00400','Phù Linh','Phu Linh','Xã Phù Linh','Phu Linh Commune','phu_linh','016',10),('00403','Bắc Phú','Bac Phu','Xã Bắc Phú','Bac Phu Commune','bac_phu','016',10),('00406','Tân Minh','Tan Minh','Xã Tân Minh','Tan Minh Commune','tan_minh','016',10),('00409','Quang Tiến','Quang Tien','Xã Quang Tiến','Quang Tien Commune','quang_tien','016',10),('00412','Hiền Ninh','Hien Ninh','Xã Hiền Ninh','Hien Ninh Commune','hien_ninh','016',10),('00415','Tân Dân','Tan Dan','Xã Tân Dân','Tan Dan Commune','tan_dan','016',10),('00418','Tiên Dược','Tien Duoc','Xã Tiên Dược','Tien Duoc Commune','tien_duoc','016',10),('00421','Việt Long','Viet Long','Xã Việt Long','Viet Long Commune','viet_long','016',10),('00424','Xuân Giang','Xuan Giang','Xã Xuân Giang','Xuan Giang Commune','xuan_giang','016',10),('00427','Mai Đình','Mai Dinh','Xã Mai Đình','Mai Dinh Commune','mai_dinh','016',10),('00430','Đức Hoà','Duc Hoa','Xã Đức Hoà','Duc Hoa Commune','duc_hoa','016',10),('00433','Thanh Xuân','Thanh Xuan','Xã Thanh Xuân','Thanh Xuan Commune','thanh_xuan','016',10),('00436','Đông Xuân','Dong Xuan','Xã Đông Xuân','Dong Xuan Commune','dong_xuan','016',10),('00439','Kim Lũ','Kim Lu','Xã Kim Lũ','Kim Lu Commune','kim_lu','016',10),('00442','Phú Cường','Phu Cuong','Xã Phú Cường','Phu Cuong Commune','phu_cuong','016',10),('00445','Phú Minh','Phu Minh','Xã Phú Minh','Phu Minh Commune','phu_minh','016',10),('00448','Phù Lỗ','Phu Lo','Xã Phù Lỗ','Phu Lo Commune','phu_lo','016',10),('00451','Xuân Thu','Xuan Thu','Xã Xuân Thu','Xuan Thu Commune','xuan_thu','016',10),('00454','Đông Anh','Dong Anh','Thị trấn Đông Anh','Dong Anh Township','dong_anh','017',9),('00457','Xuân Nộn','Xuan Non','Xã Xuân Nộn','Xuan Non Commune','xuan_non','017',10),('00460','Thuỵ Lâm','Thuy Lam','Xã Thuỵ Lâm','Thuy Lam Commune','thuy_lam','017',10),('00463','Bắc Hồng','Bac Hong','Xã Bắc Hồng','Bac Hong Commune','bac_hong','017',10),('00466','Nguyên Khê','Nguyen Khe','Xã Nguyên Khê','Nguyen Khe Commune','nguyen_khe','017',10),('00469','Nam Hồng','Nam Hong','Xã Nam Hồng','Nam Hong Commune','nam_hong','017',10),('00472','Tiên Dương','Tien Duong','Xã Tiên Dương','Tien Duong Commune','tien_duong','017',10),('00475','Vân Hà','Van Ha','Xã Vân Hà','Van Ha Commune','van_ha','017',10),('00478','Uy Nỗ','Uy No','Xã Uy Nỗ','Uy No Commune','uy_no','017',10),('00481','Vân Nội','Van Noi','Xã Vân Nội','Van Noi Commune','van_noi','017',10),('00484','Liên Hà','Lien Ha','Xã Liên Hà','Lien Ha Commune','lien_ha','017',10),('00487','Việt Hùng','Viet Hung','Xã Việt Hùng','Viet Hung Commune','viet_hung','017',10),('00490','Kim Nỗ','Kim No','Xã Kim Nỗ','Kim No Commune','kim_no','017',10),('00493','Kim Chung','Kim Chung','Xã Kim Chung','Kim Chung Commune','kim_chung','017',10),('00496','Dục Tú','Duc Tu','Xã Dục Tú','Duc Tu Commune','duc_tu','017',10),('00499','Đại Mạch','Dai Mach','Xã Đại Mạch','Dai Mach Commune','dai_mach','017',10),('00502','Vĩnh Ngọc','Vinh Ngoc','Xã Vĩnh Ngọc','Vinh Ngoc Commune','vinh_ngoc','017',10),('00505','Cổ Loa','Co Loa','Xã Cổ Loa','Co Loa Commune','co_loa','017',10),('00508','Hải Bối','Hai Boi','Xã Hải Bối','Hai Boi Commune','hai_boi','017',10),('00511','Xuân Canh','Xuan Canh','Xã Xuân Canh','Xuan Canh Commune','xuan_canh','017',10),('00514','Võng La','Vong La','Xã Võng La','Vong La Commune','vong_la','017',10),('00517','Tàm Xá','Tam Xa','Xã Tàm Xá','Tam Xa Commune','tam_xa','017',10),('00520','Mai Lâm','Mai Lam','Xã Mai Lâm','Mai Lam Commune','mai_lam','017',10),('00523','Đông Hội','Dong Hoi','Xã Đông Hội','Dong Hoi Commune','dong_hoi','017',10),('00526','Yên Viên','Yen Vien','Thị trấn Yên Viên','Yen Vien Township','yen_vien','018',9),('00529','Yên Thường','Yen Thuong','Xã Yên Thường','Yen Thuong Commune','yen_thuong','018',10),('00532','Yên Viên','Yen Vien','Xã Yên Viên','Yen Vien Commune','yen_vien','018',10),('00535','Ninh Hiệp','Ninh Hiep','Xã Ninh Hiệp','Ninh Hiep Commune','ninh_hiep','018',10),('00538','Đình Xuyên','Dinh Xuyen','Xã Đình Xuyên','Dinh Xuyen Commune','dinh_xuyen','018',10),('00541','Dương Hà','Duong Ha','Xã Dương Hà','Duong Ha Commune','duong_ha','018',10),('00544','Phù Đổng','Phu Dong','Xã Phù Đổng','Phu Dong Commune','phu_dong','018',10),('00547','Trung Mầu','Trung Mau','Xã Trung Mầu','Trung Mau Commune','trung_mau','018',10),('00550','Lệ Chi','Le Chi','Xã Lệ Chi','Le Chi Commune','le_chi','018',10),('00553','Cổ Bi','Co Bi','Xã Cổ Bi','Co Bi Commune','co_bi','018',10),('00556','Đặng Xá','Dang Xa','Xã Đặng Xá','Dang Xa Commune','dang_xa','018',10),('00559','Phú Thị','Phu Thi','Xã Phú Thị','Phu Thi Commune','phu_thi','018',10),('00562','Kim Sơn','Kim Son','Xã Kim Sơn','Kim Son Commune','kim_son','018',10),('00565','Trâu Quỳ','Trau Quy','Thị trấn Trâu Quỳ','Trau Quy Township','trau_quy','018',9),('00568','Dương Quang','Duong Quang','Xã Dương Quang','Duong Quang Commune','duong_quang','018',10),('00571','Dương Xá','Duong Xa','Xã Dương Xá','Duong Xa Commune','duong_xa','018',10),('00574','Đông Dư','Dong Du','Xã Đông Dư','Dong Du Commune','dong_du','018',10),('00577','Đa Tốn','Da Ton','Xã Đa Tốn','Da Ton Commune','da_ton','018',10),('00580','Kiêu Kỵ','Kieu Ky','Xã Kiêu Kỵ','Kieu Ky Commune','kieu_ky','018',10),('00583','Bát Tràng','Bat Trang','Xã Bát Tràng','Bat Trang Commune','bat_trang','018',10),('00586','Kim Lan','Kim Lan','Xã Kim Lan','Kim Lan Commune','kim_lan','018',10),('00589','Văn Đức','Van Duc','Xã Văn Đức','Van Duc Commune','van_duc','018',10),('00592','Cầu Diễn','Cau Dien','Phường Cầu Diễn','Cau Dien Ward','cau_dien','019',8),('00595','Thượng Cát','Thuong Cat','Phường Thượng Cát','Thuong Cat Ward','thuong_cat','021',8),('00598','Liên Mạc','Lien Mac','Phường Liên Mạc','Lien Mac Ward','lien_mac','021',8),('00601','Đông Ngạc','Dong Ngac','Phường Đông Ngạc','Dong Ngac Ward','dong_ngac','021',8),('00602','Đức Thắng','Duc Thang','Phường Đức Thắng','Duc Thang Ward','duc_thang','021',8),('00604','Thụy Phương','Thuy Phuong','Phường Thụy Phương','Thuy Phuong Ward','thuy_phuong','021',8),('00607','Tây Tựu','Tay Tuu','Phường Tây Tựu','Tay Tuu Ward','tay_tuu','021',8),('00610','Xuân Đỉnh','Xuan Dinh','Phường Xuân Đỉnh','Xuan Dinh Ward','xuan_dinh','021',8),('00611','Xuân Tảo','Xuan Tao','Phường Xuân Tảo','Xuan Tao Ward','xuan_tao','021',8),('00613','Minh Khai','Minh Khai','Phường Minh Khai','Minh Khai Ward','minh_khai','021',8),('00616','Cổ Nhuế 1','Co Nhue 1','Phường Cổ Nhuế 1','Co Nhue 1 Ward','co_nhue_1','021',8),('00617','Cổ Nhuế 2','Co Nhue 2','Phường Cổ Nhuế 2','Co Nhue 2 Ward','co_nhue_2','021',8),('00619','Phú Diễn','Phu Dien','Phường Phú Diễn','Phu Dien Ward','phu_dien','021',8),('00620','Phúc Diễn','Phuc Dien','Phường Phúc Diễn','Phuc Dien Ward','phuc_dien','021',8),('00622','Xuân Phương','Xuan Phuong','Phường Xuân Phương','Xuan Phuong Ward','xuan_phuong','019',8),('00623','Phương Canh','Phuong Canh','Phường Phương Canh','Phuong Canh Ward','phuong_canh','019',8),('00625','Mỹ Đình 1','My Dinh 1','Phường Mỹ Đình 1','My Dinh 1 Ward','my_dinh_1','019',8),('00626','Mỹ Đình 2','My Dinh 2','Phường Mỹ Đình 2','My Dinh 2 Ward','my_dinh_2','019',8),('00628','Tây Mỗ','Tay Mo','Phường Tây Mỗ','Tay Mo Ward','tay_mo','019',8),('00631','Mễ Trì','Me Tri','Phường Mễ Trì','Me Tri Ward','me_tri','019',8),('00632','Phú Đô','Phu Do','Phường Phú Đô','Phu Do Ward','phu_do','019',8),('00634','Đại Mỗ','Dai Mo','Phường Đại Mỗ','Dai Mo Ward','dai_mo','019',8),('00637','Trung Văn','Trung Van','Phường Trung Văn','Trung Van Ward','trung_van','019',8),('00640','Văn Điển','Van Dien','Thị trấn Văn Điển','Van Dien Township','van_dien','020',9),('00643','Tân Triều','Tan Trieu','Xã Tân Triều','Tan Trieu Commune','tan_trieu','020',10),('00646','Thanh Liệt','Thanh Liet','Xã Thanh Liệt','Thanh Liet Commune','thanh_liet','020',10),('00649','Tả Thanh Oai','Ta Thanh Oai','Xã Tả Thanh Oai','Ta Thanh Oai Commune','ta_thanh_oai','020',10),('00652','Hữu Hoà','Huu Hoa','Xã Hữu Hoà','Huu Hoa Commune','huu_hoa','020',10),('00655','Tam Hiệp','Tam Hiep','Xã Tam Hiệp','Tam Hiep Commune','tam_hiep','020',10),('00658','Tứ Hiệp','Tu Hiep','Xã Tứ Hiệp','Tu Hiep Commune','tu_hiep','020',10),('00661','Yên Mỹ','Yen My','Xã Yên Mỹ','Yen My Commune','yen_my','020',10),('00664','Vĩnh Quỳnh','Vinh Quynh','Xã Vĩnh Quỳnh','Vinh Quynh Commune','vinh_quynh','020',10),('00667','Ngũ Hiệp','Ngu Hiep','Xã Ngũ Hiệp','Ngu Hiep Commune','ngu_hiep','020',10),('00670','Duyên Hà','Duyen Ha','Xã Duyên Hà','Duyen Ha Commune','duyen_ha','020',10),('00673','Ngọc Hồi','Ngoc Hoi','Xã Ngọc Hồi','Ngoc Hoi Commune','ngoc_hoi','020',10),('00676','Vạn Phúc','Van Phuc','Xã Vạn Phúc','Van Phuc Commune','van_phuc','020',10),('00679','Đại áng','Dai ang','Xã Đại áng','Dai ang Commune','dai_ang','020',10),('00682','Liên Ninh','Lien Ninh','Xã Liên Ninh','Lien Ninh Commune','lien_ninh','020',10),('00685','Đông Mỹ','Dong My','Xã Đông Mỹ','Dong My Commune','dong_my','020',10),('04927','Yên Trung','Yen Trung','Xã Yên Trung','Yen Trung Commune','yen_trung','276',10),('04930','Yên Bình','Yen Binh','Xã Yên Bình','Yen Binh Commune','yen_binh','276',10),('04936','Tiến Xuân','Tien Xuan','Xã Tiến Xuân','Tien Xuan Commune','tien_xuan','276',10),('04939','Đông Xuân','Dong Xuan','Xã Đông Xuân','Dong Xuan Commune','dong_xuan','275',10),('08973','Chi Đông','Chi Dong','Thị trấn Chi Đông','Chi Dong Township','chi_dong','250',9),('08974','Đại Thịnh','Dai Thinh','Xã Đại Thịnh','Dai Thinh Commune','dai_thinh','250',10),('08977','Kim Hoa','Kim Hoa','Xã Kim Hoa','Kim Hoa Commune','kim_hoa','250',10),('08980','Thạch Đà','Thach Da','Xã Thạch Đà','Thach Da Commune','thach_da','250',10),('08983','Tiến Thắng','Tien Thang','Xã Tiến Thắng','Tien Thang Commune','tien_thang','250',10),('08986','Tự Lập','Tu Lap','Xã Tự Lập','Tu Lap Commune','tu_lap','250',10),('08989','Quang Minh','Quang Minh','Thị trấn Quang Minh','Quang Minh Township','quang_minh','250',9),('08992','Thanh Lâm','Thanh Lam','Xã Thanh Lâm','Thanh Lam Commune','thanh_lam','250',10),('08995','Tam Đồng','Tam Dong','Xã Tam Đồng','Tam Dong Commune','tam_dong','250',10),('08998','Liên Mạc','Lien Mac','Xã Liên Mạc','Lien Mac Commune','lien_mac','250',10),('09001','Vạn Yên','Van Yen','Xã Vạn Yên','Van Yen Commune','van_yen','250',10),('09004','Chu Phan','Chu Phan','Xã Chu Phan','Chu Phan Commune','chu_phan','250',10),('09007','Tiến Thịnh','Tien Thinh','Xã Tiến Thịnh','Tien Thinh Commune','tien_thinh','250',10),('09010','Mê Linh','Me Linh','Xã Mê Linh','Me Linh Commune','me_linh','250',10),('09013','Văn Khê','Van Khe','Xã Văn Khê','Van Khe Commune','van_khe','250',10),('09016','Hoàng Kim','Hoang Kim','Xã Hoàng Kim','Hoang Kim Commune','hoang_kim','250',10),('09019','Tiền Phong','Tien Phong','Xã Tiền Phong','Tien Phong Commune','tien_phong','250',10),('09022','Tráng Việt','Trang Viet','Xã Tráng Việt','Trang Viet Commune','trang_viet','250',10),('09538','Nguyễn Trãi','Nguyen Trai','Phường Nguyễn Trãi','Nguyen Trai Ward','nguyen_trai','268',8),('09541','Mộ Lao','Mo Lao','Phường Mộ Lao','Mo Lao Ward','mo_lao','268',8),('09542','Văn Quán','Van Quan','Phường Văn Quán','Van Quan Ward','van_quan','268',8),('09544','Vạn Phúc','Van Phuc','Phường Vạn Phúc','Van Phuc Ward','van_phuc','268',8),('09547','Yết Kiêu','Yet Kieu','Phường Yết Kiêu','Yet Kieu Ward','yet_kieu','268',8),('09550','Quang Trung','Quang Trung','Phường Quang Trung','Quang Trung Ward','quang_trung','268',8),('09551','La Khê','La Khe','Phường La Khê','La Khe Ward','la_khe','268',8),('09552','Phú La','Phu La','Phường Phú La','Phu La Ward','phu_la','268',8),('09553','Phúc La','Phuc La','Phường Phúc La','Phuc La Ward','phuc_la','268',8),('09556','Hà Cầu','Ha Cau','Phường Hà Cầu','Ha Cau Ward','ha_cau','268',8),('09562','Yên Nghĩa','Yen Nghia','Phường Yên Nghĩa','Yen Nghia Ward','yen_nghia','268',8),('09565','Kiến Hưng','Kien Hung','Phường Kiến Hưng','Kien Hung Ward','kien_hung','268',8),('09568','Phú Lãm','Phu Lam','Phường Phú Lãm','Phu Lam Ward','phu_lam','268',8),('09571','Phú Lương','Phu Luong','Phường Phú Lương','Phu Luong Ward','phu_luong','268',8),('09574','Lê Lợi','Le Loi','Phường Lê Lợi','Le Loi Ward','le_loi','269',8),('09577','Phú Thịnh','Phu Thinh','Phường Phú Thịnh','Phu Thinh Ward','phu_thinh','269',8),('09580','Ngô Quyền','Ngo Quyen','Phường Ngô Quyền','Ngo Quyen Ward','ngo_quyen','269',8),('09583','Quang Trung','Quang Trung','Phường Quang Trung','Quang Trung Ward','quang_trung','269',8),('09586','Sơn Lộc','Son Loc','Phường Sơn Lộc','Son Loc Ward','son_loc','269',8),('09589','Xuân Khanh','Xuan Khanh','Phường Xuân Khanh','Xuan Khanh Ward','xuan_khanh','269',8),('09592','Đường Lâm','Duong Lam','Xã Đường Lâm','Duong Lam Commune','duong_lam','269',10),('09595','Viên Sơn','Vien Son','Phường Viên Sơn','Vien Son Ward','vien_son','269',8),('09598','Xuân Sơn','Xuan Son','Xã Xuân Sơn','Xuan Son Commune','xuan_son','269',10),('09601','Trung Hưng','Trung Hung','Phường Trung Hưng','Trung Hung Ward','trung_hung','269',8),('09604','Thanh Mỹ','Thanh My','Xã Thanh Mỹ','Thanh My Commune','thanh_my','269',10),('09607','Trung Sơn Trầm','Trung Son Tram','Phường Trung Sơn Trầm','Trung Son Tram Ward','trung_son_tram','269',8),('09610','Kim Sơn','Kim Son','Xã Kim Sơn','Kim Son Commune','kim_son','269',10),('09613','Sơn Đông','Son Dong','Xã Sơn Đông','Son Dong Commune','son_dong','269',10),('09616','Cổ Đông','Co Dong','Xã Cổ Đông','Co Dong Commune','co_dong','269',10),('09619','Tây Đằng','Tay Dang','Thị trấn Tây Đằng','Tay Dang Township','tay_dang','271',9),('09625','Phú Cường','Phu Cuong','Xã Phú Cường','Phu Cuong Commune','phu_cuong','271',10),('09628','Cổ Đô','Co Do','Xã Cổ Đô','Co Do Commune','co_do','271',10),('09631','Tản Hồng','Tan Hong','Xã Tản Hồng','Tan Hong Commune','tan_hong','271',10),('09634','Vạn Thắng','Van Thang','Xã Vạn Thắng','Van Thang Commune','van_thang','271',10),('09637','Châu Sơn','Chau Son','Xã Châu Sơn','Chau Son Commune','chau_son','271',10),('09640','Phong Vân','Phong Van','Xã Phong Vân','Phong Van Commune','phong_van','271',10),('09643','Phú Đông','Phu Dong','Xã Phú Đông','Phu Dong Commune','phu_dong','271',10),('09646','Phú Phương','Phu Phuong','Xã Phú Phương','Phu Phuong Commune','phu_phuong','271',10),('09649','Phú Châu','Phu Chau','Xã Phú Châu','Phu Chau Commune','phu_chau','271',10),('09652','Thái Hòa','Thai Hoa','Xã Thái Hòa','Thai Hoa Commune','thai_hoa','271',10),('09655','Đồng Thái','Dong Thai','Xã Đồng Thái','Dong Thai Commune','dong_thai','271',10),('09658','Phú Sơn','Phu Son','Xã Phú Sơn','Phu Son Commune','phu_son','271',10),('09661','Minh Châu','Minh Chau','Xã Minh Châu','Minh Chau Commune','minh_chau','271',10),('09664','Vật Lại','Vat Lai','Xã Vật Lại','Vat Lai Commune','vat_lai','271',10),('09667','Chu Minh','Chu Minh','Xã Chu Minh','Chu Minh Commune','chu_minh','271',10),('09670','Tòng Bạt','Tong Bat','Xã Tòng Bạt','Tong Bat Commune','tong_bat','271',10),('09673','Cẩm Lĩnh','Cam Linh','Xã Cẩm Lĩnh','Cam Linh Commune','cam_linh','271',10),('09676','Sơn Đà','Son Da','Xã Sơn Đà','Son Da Commune','son_da','271',10),('09679','Đông Quang','Dong Quang','Xã Đông Quang','Dong Quang Commune','dong_quang','271',10),('09682','Tiên Phong','Tien Phong','Xã Tiên Phong','Tien Phong Commune','tien_phong','271',10),('09685','Thụy An','Thuy An','Xã Thụy An','Thuy An Commune','thuy_an','271',10),('09688','Cam Thượng','Cam Thuong','Xã Cam Thượng','Cam Thuong Commune','cam_thuong','271',10),('09691','Thuần Mỹ','Thuan My','Xã Thuần Mỹ','Thuan My Commune','thuan_my','271',10),('09694','Tản Lĩnh','Tan Linh','Xã Tản Lĩnh','Tan Linh Commune','tan_linh','271',10),('09697','Ba Trại','Ba Trai','Xã Ba Trại','Ba Trai Commune','ba_trai','271',10),('09700','Minh Quang','Minh Quang','Xã Minh Quang','Minh Quang Commune','minh_quang','271',10),('09703','Ba Vì','Ba Vi','Xã Ba Vì','Ba Vi Commune','ba_vi','271',10),('09706','Vân Hòa','Van Hoa','Xã Vân Hòa','Van Hoa Commune','van_hoa','271',10),('09709','Yên Bài','Yen Bai','Xã Yên Bài','Yen Bai Commune','yen_bai','271',10),('09712','Khánh Thượng','Khanh Thuong','Xã Khánh Thượng','Khanh Thuong Commune','khanh_thuong','271',10),('09715','Phúc Thọ','Phuc Tho','Thị trấn Phúc Thọ','Phuc Tho Township','phuc_tho','272',9),('09718','Vân Hà','Van Ha','Xã Vân Hà','Van Ha Commune','van_ha','272',10),('09721','Vân Phúc','Van Phuc','Xã Vân Phúc','Van Phuc Commune','van_phuc','272',10),('09724','Vân Nam','Van Nam','Xã Vân Nam','Van Nam Commune','van_nam','272',10),('09727','Xuân Đình','Xuan Dinh','Xã Xuân Đình','Xuan Dinh Commune','xuan_dinh','272',10),('09733','Sen Phương','Sen Phuong','Xã Sen Phương','Sen Phuong Commune','sen_phuong','272',10),('09739','Võng Xuyên','Vong Xuyen','Xã Võng Xuyên','Vong Xuyen Commune','vong_xuyen','272',10),('09742','Thọ Lộc','Tho Loc','Xã Thọ Lộc','Tho Loc Commune','tho_loc','272',10),('09745','Long Xuyên','Long Xuyen','Xã Long Xuyên','Long Xuyen Commune','long_xuyen','272',10),('09748','Thượng Cốc','Thuong Coc','Xã Thượng Cốc','Thuong Coc Commune','thuong_coc','272',10),('09751','Hát Môn','Hat Mon','Xã Hát Môn','Hat Mon Commune','hat_mon','272',10),('09754','Tích Giang','Tich Giang','Xã Tích Giang','Tich Giang Commune','tich_giang','272',10),('09757','Thanh Đa','Thanh Da','Xã Thanh Đa','Thanh Da Commune','thanh_da','272',10),('09760','Trạch Mỹ Lộc','Trach My Loc','Xã Trạch Mỹ Lộc','Trach My Loc Commune','trach_my_loc','272',10),('09763','Phúc Hòa','Phuc Hoa','Xã Phúc Hòa','Phuc Hoa Commune','phuc_hoa','272',10),('09766','Ngọc Tảo','Ngoc Tao','Xã Ngọc Tảo','Ngoc Tao Commune','ngoc_tao','272',10),('09769','Phụng Thượng','Phung Thuong','Xã Phụng Thượng','Phung Thuong Commune','phung_thuong','272',10),('09772','Tam Thuấn','Tam Thuan','Xã Tam Thuấn','Tam Thuan Commune','tam_thuan','272',10),('09775','Tam Hiệp','Tam Hiep','Xã Tam Hiệp','Tam Hiep Commune','tam_hiep','272',10),('09778','Hiệp Thuận','Hiep Thuan','Xã Hiệp Thuận','Hiep Thuan Commune','hiep_thuan','272',10),('09781','Liên Hiệp','Lien Hiep','Xã Liên Hiệp','Lien Hiep Commune','lien_hiep','272',10),('09784','Phùng','Phung','Thị trấn Phùng','Phung Township','phung','273',9),('09787','Trung Châu','Trung Chau','Xã Trung Châu','Trung Chau Commune','trung_chau','273',10),('09790','Thọ An','Tho An','Xã Thọ An','Tho An Commune','tho_an','273',10),('09793','Thọ Xuân','Tho Xuan','Xã Thọ Xuân','Tho Xuan Commune','tho_xuan','273',10),('09796','Hồng Hà','Hong Ha','Xã Hồng Hà','Hong Ha Commune','hong_ha','273',10),('09799','Liên Hồng','Lien Hong','Xã Liên Hồng','Lien Hong Commune','lien_hong','273',10),('09802','Liên Hà','Lien Ha','Xã Liên Hà','Lien Ha Commune','lien_ha','273',10),('09805','Hạ Mỗ','Ha Mo','Xã Hạ Mỗ','Ha Mo Commune','ha_mo','273',10),('09808','Liên Trung','Lien Trung','Xã Liên Trung','Lien Trung Commune','lien_trung','273',10),('09811','Phương Đình','Phuong Dinh','Xã Phương Đình','Phuong Dinh Commune','phuong_dinh','273',10),('09814','Thượng Mỗ','Thuong Mo','Xã Thượng Mỗ','Thuong Mo Commune','thuong_mo','273',10),('09817','Tân Hội','Tan Hoi','Xã Tân Hội','Tan Hoi Commune','tan_hoi','273',10),('09820','Tân Lập','Tan Lap','Xã Tân Lập','Tan Lap Commune','tan_lap','273',10),('09823','Đan Phượng','Dan Phuong','Xã Đan Phượng','Dan Phuong Commune','dan_phuong','273',10),('09826','Đồng Tháp','Dong Thap','Xã Đồng Tháp','Dong Thap Commune','dong_thap','273',10),('09829','Song Phượng','Song Phuong','Xã Song Phượng','Song Phuong Commune','song_phuong','273',10),('09832','Trạm Trôi','Tram Troi','Thị trấn Trạm Trôi','Tram Troi Township','tram_troi','274',9),('09835','Đức Thượng','Duc Thuong','Xã Đức Thượng','Duc Thuong Commune','duc_thuong','274',10),('09838','Minh Khai','Minh Khai','Xã Minh Khai','Minh Khai Commune','minh_khai','274',10),('09841','Dương Liễu','Duong Lieu','Xã Dương Liễu','Duong Lieu Commune','duong_lieu','274',10),('09844','Di Trạch','Di Trach','Xã Di Trạch','Di Trach Commune','di_trach','274',10),('09847','Đức Giang','Duc Giang','Xã Đức Giang','Duc Giang Commune','duc_giang','274',10),('09850','Cát Quế','Cat Que','Xã Cát Quế','Cat Que Commune','cat_que','274',10),('09853','Kim Chung','Kim Chung','Xã Kim Chung','Kim Chung Commune','kim_chung','274',10),('09856','Yên Sở','Yen So','Xã Yên Sở','Yen So Commune','yen_so','274',10),('09859','Sơn Đồng','Son Dong','Xã Sơn Đồng','Son Dong Commune','son_dong','274',10),('09862','Vân Canh','Van Canh','Xã Vân Canh','Van Canh Commune','van_canh','274',10),('09865','Đắc Sở','Dac So','Xã Đắc Sở','Dac So Commune','dac_so','274',10),('09868','Lại Yên','Lai Yen','Xã Lại Yên','Lai Yen Commune','lai_yen','274',10),('09871','Tiền Yên','Tien Yen','Xã Tiền Yên','Tien Yen Commune','tien_yen','274',10),('09874','Song Phương','Song Phuong','Xã Song Phương','Song Phuong Commune','song_phuong','274',10),('09877','An Khánh','An Khanh','Xã An Khánh','An Khanh Commune','an_khanh','274',10),('09880','An Thượng','An Thuong','Xã An Thượng','An Thuong Commune','an_thuong','274',10),('09883','Vân Côn','Van Con','Xã Vân Côn','Van Con Commune','van_con','274',10),('09886','Dương Nội','Duong Noi','Phường Dương Nội','Duong Noi Ward','duong_noi','268',8),('09889','La Phù','La Phu','Xã La Phù','La Phu Commune','la_phu','274',10),('09892','Đông La','Dong La','Xã Đông La','Dong La Commune','dong_la','274',10),('09895','Quốc Oai','Quoc Oai','Thị trấn Quốc Oai','Quoc Oai Township','quoc_oai','275',9),('09898','Sài Sơn','Sai Son','Xã Sài Sơn','Sai Son Commune','sai_son','275',10),('09901','Phượng Cách','Phuong Cach','Xã Phượng Cách','Phuong Cach Commune','phuong_cach','275',10),('09904','Yên Sơn','Yen Son','Xã Yên Sơn','Yen Son Commune','yen_son','275',10),('09907','Ngọc Liệp','Ngoc Liep','Xã Ngọc Liệp','Ngoc Liep Commune','ngoc_liep','275',10),('09910','Ngọc Mỹ','Ngoc My','Xã Ngọc Mỹ','Ngoc My Commune','ngoc_my','275',10),('09913','Liệp Tuyết','Liep Tuyet','Xã Liệp Tuyết','Liep Tuyet Commune','liep_tuyet','275',10),('09916','Thạch Thán','Thach Than','Xã Thạch Thán','Thach Than Commune','thach_than','275',10),('09919','Đồng Quang','Dong Quang','Xã Đồng Quang','Dong Quang Commune','dong_quang','275',10),('09922','Phú Cát','Phu Cat','Xã Phú Cát','Phu Cat Commune','phu_cat','275',10),('09925','Tuyết Nghĩa','Tuyet Nghia','Xã Tuyết Nghĩa','Tuyet Nghia Commune','tuyet_nghia','275',10),('09928','Nghĩa Hương','Nghia Huong','Xã Nghĩa Hương','Nghia Huong Commune','nghia_huong','275',10),('09931','Cộng Hòa','Cong Hoa','Xã Cộng Hòa','Cong Hoa Commune','cong_hoa','275',10),('09934','Tân Phú','Tan Phu','Xã Tân Phú','Tan Phu Commune','tan_phu','275',10),('09937','Đại Thành','Dai Thanh','Xã Đại Thành','Dai Thanh Commune','dai_thanh','275',10),('09940','Phú Mãn','Phu Man','Xã Phú Mãn','Phu Man Commune','phu_man','275',10),('09943','Cấn Hữu','Can Huu','Xã Cấn Hữu','Can Huu Commune','can_huu','275',10),('09946','Tân Hòa','Tan Hoa','Xã Tân Hòa','Tan Hoa Commune','tan_hoa','275',10),('09949','Hòa Thạch','Hoa Thach','Xã Hòa Thạch','Hoa Thach Commune','hoa_thach','275',10),('09952','Đông Yên','Dong Yen','Xã Đông Yên','Dong Yen Commune','dong_yen','275',10),('09955','Liên Quan','Lien Quan','Thị trấn Liên Quan','Lien Quan Township','lien_quan','276',9),('09958','Đại Đồng','Dai Dong','Xã Đại Đồng','Dai Dong Commune','dai_dong','276',10),('09961','Cẩm Yên','Cam Yen','Xã Cẩm Yên','Cam Yen Commune','cam_yen','276',10),('09964','Lại Thượng','Lai Thuong','Xã Lại Thượng','Lai Thuong Commune','lai_thuong','276',10),('09967','Phú Kim','Phu Kim','Xã Phú Kim','Phu Kim Commune','phu_kim','276',10),('09970','Hương Ngải','Huong Ngai','Xã Hương Ngải','Huong Ngai Commune','huong_ngai','276',10),('09973','Canh Nậu','Canh Nau','Xã Canh Nậu','Canh Nau Commune','canh_nau','276',10),('09976','Kim Quan','Kim Quan','Xã Kim Quan','Kim Quan Commune','kim_quan','276',10),('09979','Dị Nậu','Di Nau','Xã Dị Nậu','Di Nau Commune','di_nau','276',10),('09982','Bình Yên','Binh Yen','Xã Bình Yên','Binh Yen Commune','binh_yen','276',10),('09985','Chàng Sơn','Chang Son','Xã Chàng Sơn','Chang Son Commune','chang_son','276',10),('09988','Thạch Hoà','Thach Hoa','Xã Thạch Hoà','Thach Hoa Commune','thach_hoa','276',10),('09991','Cần Kiệm','Can Kiem','Xã Cần Kiệm','Can Kiem Commune','can_kiem','276',10),('09994','Hữu Bằng','Huu Bang','Xã Hữu Bằng','Huu Bang Commune','huu_bang','276',10),('09997','Phùng Xá','Phung Xa','Xã Phùng Xá','Phung Xa Commune','phung_xa','276',10),('10000','Tân Xã','Tan Xa','Xã Tân Xã','Tan Xa Commune','tan_xa','276',10),('10003','Thạch Xá','Thach Xa','Xã Thạch Xá','Thach Xa Commune','thach_xa','276',10),('10006','Bình Phú','Binh Phu','Xã Bình Phú','Binh Phu Commune','binh_phu','276',10),('10009','Hạ Bằng','Ha Bang','Xã Hạ Bằng','Ha Bang Commune','ha_bang','276',10),('10012','Đồng Trúc','Dong Truc','Xã Đồng Trúc','Dong Truc Commune','dong_truc','276',10),('10015','Chúc Sơn','Chuc Son','Thị trấn Chúc Sơn','Chuc Son Township','chuc_son','277',9),('10018','Xuân Mai','Xuan Mai','Thị trấn Xuân Mai','Xuan Mai Township','xuan_mai','277',9),('10021','Phụng Châu','Phung Chau','Xã Phụng Châu','Phung Chau Commune','phung_chau','277',10),('10024','Tiên Phương','Tien Phuong','Xã Tiên Phương','Tien Phuong Commune','tien_phuong','277',10),('10027','Đông Sơn','Dong Son','Xã Đông Sơn','Dong Son Commune','dong_son','277',10),('10030','Đông Phương Yên','Dong Phuong Yen','Xã Đông Phương Yên','Dong Phuong Yen Commune','dong_phuong_yen','277',10),('10033','Phú Nghĩa','Phu Nghia','Xã Phú Nghĩa','Phu Nghia Commune','phu_nghia','277',10),('10039','Trường Yên','Truong Yen','Xã Trường Yên','Truong Yen Commune','truong_yen','277',10),('10042','Ngọc Hòa','Ngoc Hoa','Xã Ngọc Hòa','Ngoc Hoa Commune','ngoc_hoa','277',10),('10045','Thủy Xuân Tiên','Thuy Xuan Tien','Xã Thủy Xuân Tiên','Thuy Xuan Tien Commune','thuy_xuan_tien','277',10),('10048','Thanh Bình','Thanh Binh','Xã Thanh Bình','Thanh Binh Commune','thanh_binh','277',10),('10051','Trung Hòa','Trung Hoa','Xã Trung Hòa','Trung Hoa Commune','trung_hoa','277',10),('10054','Đại Yên','Dai Yen','Xã Đại Yên','Dai Yen Commune','dai_yen','277',10),('10057','Thụy Hương','Thuy Huong','Xã Thụy Hương','Thuy Huong Commune','thuy_huong','277',10),('10060','Tốt Động','Tot Dong','Xã Tốt Động','Tot Dong Commune','tot_dong','277',10),('10063','Lam Điền','Lam Dien','Xã Lam Điền','Lam Dien Commune','lam_dien','277',10),('10066','Tân Tiến','Tan Tien','Xã Tân Tiến','Tan Tien Commune','tan_tien','277',10),('10069','Nam Phương Tiến','Nam Phuong Tien','Xã Nam Phương Tiến','Nam Phuong Tien Commune','nam_phuong_tien','277',10),('10072','Hợp Đồng','Hop Dong','Xã Hợp Đồng','Hop Dong Commune','hop_dong','277',10),('10075','Hoàng Văn Thụ','Hoang Van Thu','Xã Hoàng Văn Thụ','Hoang Van Thu Commune','hoang_van_thu','277',10),('10078','Hoàng Diệu','Hoang Dieu','Xã Hoàng Diệu','Hoang Dieu Commune','hoang_dieu','277',10),('10081','Hữu Văn','Huu Van','Xã Hữu Văn','Huu Van Commune','huu_van','277',10),('10084','Quảng Bị','Quang Bi','Xã Quảng Bị','Quang Bi Commune','quang_bi','277',10),('10087','Mỹ Lương','My Luong','Xã Mỹ Lương','My Luong Commune','my_luong','277',10),('10090','Thượng Vực','Thuong Vuc','Xã Thượng Vực','Thuong Vuc Commune','thuong_vuc','277',10),('10093','Hồng Phong','Hong Phong','Xã Hồng Phong','Hong Phong Commune','hong_phong','277',10),('10096','Đồng Phú','Dong Phu','Xã Đồng Phú','Dong Phu Commune','dong_phu','277',10),('10099','Trần Phú','Tran Phu','Xã Trần Phú','Tran Phu Commune','tran_phu','277',10),('10102','Văn Võ','Van Vo','Xã Văn Võ','Van Vo Commune','van_vo','277',10),('10105','Đồng Lạc','Dong Lac','Xã Đồng Lạc','Dong Lac Commune','dong_lac','277',10),('10108','Hòa Chính','Hoa Chinh','Xã Hòa Chính','Hoa Chinh Commune','hoa_chinh','277',10),('10111','Phú Nam An','Phu Nam An','Xã Phú Nam An','Phu Nam An Commune','phu_nam_an','277',10),('10114','Kim Bài','Kim Bai','Thị trấn Kim Bài','Kim Bai Township','kim_bai','278',9),('10117','Đồng Mai','Dong Mai','Phường Đồng Mai','Dong Mai Ward','dong_mai','268',8),('10120','Cự Khê','Cu Khe','Xã Cự Khê','Cu Khe Commune','cu_khe','278',10),('10123','Biên Giang','Bien Giang','Phường Biên Giang','Bien Giang Ward','bien_giang','268',8),('10126','Bích Hòa','Bich Hoa','Xã Bích Hòa','Bich Hoa Commune','bich_hoa','278',10),('10129','Mỹ Hưng','My Hung','Xã Mỹ Hưng','My Hung Commune','my_hung','278',10),('10132','Cao Viên','Cao Vien','Xã Cao Viên','Cao Vien Commune','cao_vien','278',10),('10135','Bình Minh','Binh Minh','Xã Bình Minh','Binh Minh Commune','binh_minh','278',10),('10138','Tam Hưng','Tam Hung','Xã Tam Hưng','Tam Hung Commune','tam_hung','278',10),('10141','Thanh Cao','Thanh Cao','Xã Thanh Cao','Thanh Cao Commune','thanh_cao','278',10),('10144','Thanh Thùy','Thanh Thuy','Xã Thanh Thùy','Thanh Thuy Commune','thanh_thuy','278',10),('10147','Thanh Mai','Thanh Mai','Xã Thanh Mai','Thanh Mai Commune','thanh_mai','278',10),('10150','Thanh Văn','Thanh Van','Xã Thanh Văn','Thanh Van Commune','thanh_van','278',10),('10153','Đỗ Động','Do Dong','Xã Đỗ Động','Do Dong Commune','do_dong','278',10),('10156','Kim An','Kim An','Xã Kim An','Kim An Commune','kim_an','278',10),('10159','Kim Thư','Kim Thu','Xã Kim Thư','Kim Thu Commune','kim_thu','278',10),('10162','Phương Trung','Phuong Trung','Xã Phương Trung','Phuong Trung Commune','phuong_trung','278',10),('10165','Tân Ước','Tan Uoc','Xã Tân Ước','Tan Uoc Commune','tan_uoc','278',10),('10168','Dân Hòa','Dan Hoa','Xã Dân Hòa','Dan Hoa Commune','dan_hoa','278',10),('10171','Liên Châu','Lien Chau','Xã Liên Châu','Lien Chau Commune','lien_chau','278',10),('10174','Cao Dương','Cao Duong','Xã Cao Dương','Cao Duong Commune','cao_duong','278',10),('10177','Xuân Dương','Xuan Duong','Xã Xuân Dương','Xuan Duong Commune','xuan_duong','278',10),('10180','Hồng Dương','Hong Duong','Xã Hồng Dương','Hong Duong Commune','hong_duong','278',10),('10183','Thường Tín','Thuong Tin','Thị trấn Thường Tín','Thuong Tin Township','thuong_tin','279',9),('10186','Ninh Sở','Ninh So','Xã Ninh Sở','Ninh So Commune','ninh_so','279',10),('10189','Nhị Khê','Nhi Khe','Xã Nhị Khê','Nhi Khe Commune','nhi_khe','279',10),('10192','Duyên Thái','Duyen Thai','Xã Duyên Thái','Duyen Thai Commune','duyen_thai','279',10),('10195','Khánh Hà','Khanh Ha','Xã Khánh Hà','Khanh Ha Commune','khanh_ha','279',10),('10198','Hòa Bình','Hoa Binh','Xã Hòa Bình','Hoa Binh Commune','hoa_binh','279',10),('10201','Văn Bình','Van Binh','Xã Văn Bình','Van Binh Commune','van_binh','279',10),('10204','Hiền Giang','Hien Giang','Xã Hiền Giang','Hien Giang Commune','hien_giang','279',10),('10207','Hồng Vân','Hong Van','Xã Hồng Vân','Hong Van Commune','hong_van','279',10),('10210','Vân Tảo','Van Tao','Xã Vân Tảo','Van Tao Commune','van_tao','279',10),('10213','Liên Phương','Lien Phuong','Xã Liên Phương','Lien Phuong Commune','lien_phuong','279',10),('10216','Văn Phú','Van Phu','Xã Văn Phú','Van Phu Commune','van_phu','279',10),('10219','Tự Nhiên','Tu Nhien','Xã Tự Nhiên','Tu Nhien Commune','tu_nhien','279',10),('10222','Tiền Phong','Tien Phong','Xã Tiền Phong','Tien Phong Commune','tien_phong','279',10),('10225','Hà Hồi','Ha Hoi','Xã Hà Hồi','Ha Hoi Commune','ha_hoi','279',10),('10228','Thư Phú','Thu Phu','Xã Thư Phú','Thu Phu Commune','thu_phu','279',10),('10231','Nguyễn Trãi','Nguyen Trai','Xã Nguyễn Trãi','Nguyen Trai Commune','nguyen_trai','279',10),('10234','Quất Động','Quat Dong','Xã Quất Động','Quat Dong Commune','quat_dong','279',10),('10237','Chương Dương','Chuong Duong','Xã Chương Dương','Chuong Duong Commune','chuong_duong','279',10),('10240','Tân Minh','Tan Minh','Xã Tân Minh','Tan Minh Commune','tan_minh','279',10),('10243','Lê Lợi','Le Loi','Xã Lê Lợi','Le Loi Commune','le_loi','279',10),('10246','Thắng Lợi','Thang Loi','Xã Thắng Lợi','Thang Loi Commune','thang_loi','279',10),('10249','Dũng Tiến','Dung Tien','Xã Dũng Tiến','Dung Tien Commune','dung_tien','279',10),('10252','Thống Nhất','Thong Nhat','Xã Thống Nhất','Thong Nhat Commune','thong_nhat','279',10),('10255','Nghiêm Xuyên','Nghiem Xuyen','Xã Nghiêm Xuyên','Nghiem Xuyen Commune','nghiem_xuyen','279',10),('10258','Tô Hiệu','To Hieu','Xã Tô Hiệu','To Hieu Commune','to_hieu','279',10),('10261','Văn Tự','Van Tu','Xã Văn Tự','Van Tu Commune','van_tu','279',10),('10264','Vạn Điểm','Van Diem','Xã Vạn Điểm','Van Diem Commune','van_diem','279',10),('10267','Minh Cường','Minh Cuong','Xã Minh Cường','Minh Cuong Commune','minh_cuong','279',10),('10270','Phú Minh','Phu Minh','Thị trấn Phú Minh','Phu Minh Township','phu_minh','280',9),('10273','Phú Xuyên','Phu Xuyen','Thị trấn Phú Xuyên','Phu Xuyen Township','phu_xuyen','280',9),('10276','Hồng Minh','Hong Minh','Xã Hồng Minh','Hong Minh Commune','hong_minh','280',10),('10279','Phượng Dực','Phuong Duc','Xã Phượng Dực','Phuong Duc Commune','phuong_duc','280',10),('10282','Nam Tiến','Nam Tien','Xã Nam Tiến','Nam Tien Commune','nam_tien','280',10),('10288','Tri Trung','Tri Trung','Xã Tri Trung','Tri Trung Commune','tri_trung','280',10),('10291','Đại Thắng','Dai Thang','Xã Đại Thắng','Dai Thang Commune','dai_thang','280',10),('10294','Phú Túc','Phu Tuc','Xã Phú Túc','Phu Tuc Commune','phu_tuc','280',10),('10297','Văn Hoàng','Van Hoang','Xã Văn Hoàng','Van Hoang Commune','van_hoang','280',10),('10300','Hồng Thái','Hong Thai','Xã Hồng Thái','Hong Thai Commune','hong_thai','280',10),('10303','Hoàng Long','Hoang Long','Xã Hoàng Long','Hoang Long Commune','hoang_long','280',10),('10306','Quang Trung','Quang Trung','Xã Quang Trung','Quang Trung Commune','quang_trung','280',10),('10309','Nam Phong','Nam Phong','Xã Nam Phong','Nam Phong Commune','nam_phong','280',10),('10312','Nam Triều','Nam Trieu','Xã Nam Triều','Nam Trieu Commune','nam_trieu','280',10),('10315','Tân Dân','Tan Dan','Xã Tân Dân','Tan Dan Commune','tan_dan','280',10),('10318','Sơn Hà','Son Ha','Xã Sơn Hà','Son Ha Commune','son_ha','280',10),('10321','Chuyên Mỹ','Chuyen My','Xã Chuyên Mỹ','Chuyen My Commune','chuyen_my','280',10),('10324','Khai Thái','Khai Thai','Xã Khai Thái','Khai Thai Commune','khai_thai','280',10),('10327','Phúc Tiến','Phuc Tien','Xã Phúc Tiến','Phuc Tien Commune','phuc_tien','280',10),('10330','Vân Từ','Van Tu','Xã Vân Từ','Van Tu Commune','van_tu','280',10),('10333','Tri Thủy','Tri Thuy','Xã Tri Thủy','Tri Thuy Commune','tri_thuy','280',10),('10336','Đại Xuyên','Dai Xuyen','Xã Đại Xuyên','Dai Xuyen Commune','dai_xuyen','280',10),('10339','Phú Yên','Phu Yen','Xã Phú Yên','Phu Yen Commune','phu_yen','280',10),('10342','Bạch Hạ','Bach Ha','Xã Bạch Hạ','Bach Ha Commune','bach_ha','280',10),('10345','Quang Lãng','Quang Lang','Xã Quang Lãng','Quang Lang Commune','quang_lang','280',10),('10348','Châu Can','Chau Can','Xã Châu Can','Chau Can Commune','chau_can','280',10),('10351','Minh Tân','Minh Tan','Xã Minh Tân','Minh Tan Commune','minh_tan','280',10),('10354','Vân Đình','Van Dinh','Thị trấn Vân Đình','Van Dinh Township','van_dinh','281',9),('10357','Viên An','Vien An','Xã Viên An','Vien An Commune','vien_an','281',10),('10360','Viên Nội','Vien Noi','Xã Viên Nội','Vien Noi Commune','vien_noi','281',10),('10363','Hoa Sơn','Hoa Son','Xã Hoa Sơn','Hoa Son Commune','hoa_son','281',10),('10366','Quảng Phú Cầu','Quang Phu Cau','Xã Quảng Phú Cầu','Quang Phu Cau Commune','quang_phu_cau','281',10),('10369','Trường Thịnh','Truong Thinh','Xã Trường Thịnh','Truong Thinh Commune','truong_thinh','281',10),('10372','Cao Thành','Cao Thanh','Xã Cao Thành','Cao Thanh Commune','cao_thanh','281',10),('10375','Liên Bạt','Lien Bat','Xã Liên Bạt','Lien Bat Commune','lien_bat','281',10),('10378','Sơn Công','Son Cong','Xã Sơn Công','Son Cong Commune','son_cong','281',10),('10381','Đồng Tiến','Dong Tien','Xã Đồng Tiến','Dong Tien Commune','dong_tien','281',10),('10384','Phương Tú','Phuong Tu','Xã Phương Tú','Phuong Tu Commune','phuong_tu','281',10),('10387','Trung Tú','Trung Tu','Xã Trung Tú','Trung Tu Commune','trung_tu','281',10),('10390','Đồng Tân','Dong Tan','Xã Đồng Tân','Dong Tan Commune','dong_tan','281',10),('10393','Tảo Dương Văn','Tao Duong Van','Xã Tảo Dương Văn','Tao Duong Van Commune','tao_duong_van','281',10),('10396','Vạn Thái','Van Thai','Xã Vạn Thái','Van Thai Commune','van_thai','281',10),('10399','Minh Đức','Minh Duc','Xã Minh Đức','Minh Duc Commune','minh_duc','281',10),('10402','Hòa Lâm','Hoa Lam','Xã Hòa Lâm','Hoa Lam Commune','hoa_lam','281',10),('10405','Hòa Xá','Hoa Xa','Xã Hòa Xá','Hoa Xa Commune','hoa_xa','281',10),('10408','Trầm Lộng','Tram Long','Xã Trầm Lộng','Tram Long Commune','tram_long','281',10),('10411','Kim Đường','Kim Duong','Xã Kim Đường','Kim Duong Commune','kim_duong','281',10),('10414','Hòa Nam','Hoa Nam','Xã Hòa Nam','Hoa Nam Commune','hoa_nam','281',10),('10417','Hòa Phú','Hoa Phu','Xã Hòa Phú','Hoa Phu Commune','hoa_phu','281',10),('10420','Đội Bình','Doi Binh','Xã Đội Bình','Doi Binh Commune','doi_binh','281',10),('10423','Đại Hùng','Dai Hung','Xã Đại Hùng','Dai Hung Commune','dai_hung','281',10),('10426','Đông Lỗ','Dong Lo','Xã Đông Lỗ','Dong Lo Commune','dong_lo','281',10),('10429','Phù Lưu','Phu Luu','Xã Phù Lưu','Phu Luu Commune','phu_luu','281',10),('10432','Đại Cường','Dai Cuong','Xã Đại Cường','Dai Cuong Commune','dai_cuong','281',10),('10435','Lưu Hoàng','Luu Hoang','Xã Lưu Hoàng','Luu Hoang Commune','luu_hoang','281',10),('10438','Hồng Quang','Hong Quang','Xã Hồng Quang','Hong Quang Commune','hong_quang','281',10),('10441','Đại Nghĩa','Dai Nghia','Thị trấn Đại Nghĩa','Dai Nghia Township','dai_nghia','282',9),('10444','Đồng Tâm','Dong Tam','Xã Đồng Tâm','Dong Tam Commune','dong_tam','282',10),('10447','Thượng Lâm','Thuong Lam','Xã Thượng Lâm','Thuong Lam Commune','thuong_lam','282',10),('10450','Tuy Lai','Tuy Lai','Xã Tuy Lai','Tuy Lai Commune','tuy_lai','282',10),('10453','Phúc Lâm','Phuc Lam','Xã Phúc Lâm','Phuc Lam Commune','phuc_lam','282',10),('10456','Mỹ Thành','My Thanh','Xã Mỹ Thành','My Thanh Commune','my_thanh','282',10),('10459','Bột Xuyên','Bot Xuyen','Xã Bột Xuyên','Bot Xuyen Commune','bot_xuyen','282',10),('10462','An Mỹ','An My','Xã An Mỹ','An My Commune','an_my','282',10),('10465','Hồng Sơn','Hong Son','Xã Hồng Sơn','Hong Son Commune','hong_son','282',10),('10468','Lê Thanh','Le Thanh','Xã Lê Thanh','Le Thanh Commune','le_thanh','282',10),('10471','Xuy Xá','Xuy Xa','Xã Xuy Xá','Xuy Xa Commune','xuy_xa','282',10),('10474','Phùng Xá','Phung Xa','Xã Phùng Xá','Phung Xa Commune','phung_xa','282',10),('10477','Phù Lưu Tế','Phu Luu Te','Xã Phù Lưu Tế','Phu Luu Te Commune','phu_luu_te','282',10),('10480','Đại Hưng','Dai Hung','Xã Đại Hưng','Dai Hung Commune','dai_hung','282',10),('10483','Vạn Kim','Van Kim','Xã Vạn Kim','Van Kim Commune','van_kim','282',10),('10486','Đốc Tín','Doc Tin','Xã Đốc Tín','Doc Tin Commune','doc_tin','282',10),('10489','Hương Sơn','Huong Son','Xã Hương Sơn','Huong Son Commune','huong_son','282',10),('10492','Hùng Tiến','Hung Tien','Xã Hùng Tiến','Hung Tien Commune','hung_tien','282',10),('10495','An Tiến','An Tien','Xã An Tiến','An Tien Commune','an_tien','282',10),('10498','Hợp Tiến','Hop Tien','Xã Hợp Tiến','Hop Tien Commune','hop_tien','282',10),('10501','Hợp Thanh','Hop Thanh','Xã Hợp Thanh','Hop Thanh Commune','hop_thanh','282',10),('10504','An Phú','An Phu','Xã An Phú','An Phu Commune','an_phu','282',10);
/*!40000 ALTER TABLE `wards` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-25 21:41:54
