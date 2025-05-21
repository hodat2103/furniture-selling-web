-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: furniture_selling_web_db
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `blogs`
--

DROP TABLE IF EXISTS `blogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blogs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `blogs_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blogs`
--

LOCK TABLES `blogs` WRITE;
/*!40000 ALTER TABLE `blogs` DISABLE KEYS */;
/*!40000 ALTER TABLE `blogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `slug` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `slug` (`slug`),
  KEY `FKsaok720gsu4u2wrgbk10b5n8d` (`parent_id`),
  CONSTRAINT `FKsaok720gsu4u2wrgbk10b5n8d` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` (`id`, `name`, `description`, `parent_id`, `created_at`, `updated_at`, `slug`) VALUES (1,'Bàn làm việc','Bàn làm việc với nhiều mẫu mã đa dạng',NULL,'2025-03-16 09:29:15','2025-05-20 17:41:13','ban-lam-viec'),(2,'Bàn giám đốc','Hịn',1,'2025-03-16 09:30:53','2025-05-20 17:41:13','ban-giam-doc'),(3,'Bàn họp','Rẻ',1,'2025-03-16 09:31:10','2025-05-20 17:41:13','ban-hop'),(4,'Bàn quầy lễ tân','Rẻ',1,'2025-03-16 09:31:28','2025-05-20 17:41:13','ban-quay-le-tan'),(5,'Bàn ăn công nghiệp','Rẻ',1,'2025-03-16 09:32:04','2025-05-20 17:41:13','ban-an-cong-nghiep'),(6,'Bàn trà','Rẻ',1,'2025-03-16 09:32:11','2025-05-20 17:41:13','ban-tra'),(7,'Ghế văn phòng','Ghế văn phòng nhiều loại',NULL,'2025-03-16 09:33:06','2025-05-20 18:24:23','ghe-van-phong'),(8,'Ghế tựa','Xịn',7,'2025-03-16 09:33:26','2025-05-20 17:41:13','ghe-tua'),(9,'Ghế xoay văn phòng','Xịn',7,'2025-03-16 09:33:46','2025-05-20 17:41:13','ghe-xoay-van-phong'),(10,'Ghế chân quỳ','Xịn',7,'2025-03-16 09:33:57','2025-05-20 17:41:13','ghe-chan-quy'),(11,'Tủ đựng','Tủ đựng nhiều loại',NULL,'2025-03-16 09:34:53','2025-05-20 17:41:13','tu-dung'),(12,'Tủ tài liệu','Chất lượng',11,'2025-03-16 09:35:25','2025-05-20 17:41:13','tu-tai-lieu'),(13,'Tủ giám đốc','Chất lượng',11,'2025-03-16 09:35:35','2025-05-20 17:41:13','tu-giam-doc'),(14,'Tủ nối và góc nối','Chất lượng',11,'2025-03-16 09:35:52','2025-05-20 17:41:13','tu-noi-va-goc-noi');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` (`id`, `user_id`, `position`) VALUES (1,8,'Shipper');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedbacks`
--

DROP TABLE IF EXISTS `feedbacks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedbacks` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `rating_value` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `feedbacks_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `feedbacks_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedbacks`
--

LOCK TABLES `feedbacks` WRITE;
/*!40000 ALTER TABLE `feedbacks` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedbacks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materials`
--

DROP TABLE IF EXISTS `materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materials` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `material_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materials`
--

LOCK TABLES `materials` WRITE;
/*!40000 ALTER TABLE `materials` DISABLE KEYS */;
INSERT INTO `materials` (`id`, `material_name`) VALUES (1,'Gỗ tự nhiên'),(2,'Kim loại'),(3,'Ván ép'),(4,'Gỗ công nghiệp'),(5,'Nhựa cao cấp'),(6,'Da cao cấp'),(7,'Vải'),(8,'Phụ kiện khác');
/*!40000 ALTER TABLE `materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `product_item_id` bigint DEFAULT NULL,
  `price` decimal(15,5) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_item_id` (`product_item_id`),
  CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`product_item_id`) REFERENCES `product_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` (`id`, `order_id`, `product_item_id`, `price`, `quantity`) VALUES (1,1,4,NULL,2),(2,3,4,15000000.00000,2),(3,4,4,15000000.00000,2),(4,5,5,13000000.00000,2),(5,6,5,13000000.00000,2),(6,7,5,13000000.00000,2),(7,8,5,13000000.00000,2);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `total_price` decimal(38,2) DEFAULT NULL,
  `final_price` decimal(38,2) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` (`id`, `code`, `user_id`, `order_date`, `total_price`, `final_price`, `status`, `payment_method`) VALUES (1,NULL,8,'2025-03-25 17:49:11',30000000.00,30000000.00,'0','cod'),(2,NULL,8,'2025-03-25 17:50:54',30000000.00,0.00,'0','cod'),(3,NULL,8,'2025-03-25 17:53:14',30000000.00,30000000.00,'0','cod'),(4,NULL,8,'2025-03-25 18:06:33',30000000.00,30000000.00,'0','cod'),(5,'1324473CA561PY',8,'2025-03-29 11:00:42',26000000.00,26000000.00,'0','cod'),(6,'6366544Q85E354',8,'2025-03-29 11:02:09',26000000.00,10400000.00,'0','cod'),(7,'86120342BVWG05',8,'2025-03-29 11:27:18',26000000.00,21400000.00,'PENDING','cod'),(8,'4029919T809S77',8,'2025-04-03 17:35:58',26000000.00,26200000.00,'PENDING','cod');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `payment_date` datetime(6) DEFAULT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `paymentMethod` varchar(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `end_point` varchar(255) DEFAULT NULL,
  `http_method` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` (`id`, `name`, `end_point`, `http_method`) VALUES (1,'ADMIN:CREATE:PERMISSION','permissions/**','POST'),(2,'GET_CATEGORIES','/categories/**','GET'),(3,'POST_CATEGORIES','/categories/**','POST'),(4,'PUT_CATEGORIES','/categories/**','PUT'),(5,'DELETE_CATEGORIES','/categories/**','DELETE'),(6,'GET_MATERIALS','/materials/**','GET'),(7,'POST_MATERIALS','/materials/**','POST'),(8,'PUT_MATERIALS','/materials/**','PUT'),(9,'DELETE_MATERIALS','/materials/**','DELETE'),(10,'GET_ROLES','/roles/**','GET'),(11,'POST_ROLES','/roles/**','POST'),(12,'PUT_ROLES','/roles/**','PUT'),(13,'DELETE_ROLES','/roles/**','DELETE'),(14,'GET_PERMISSIONS','/permissions/**','GET'),(15,'POST_PERMISSIONS','/permissions/**','POST'),(16,'PUT_PERMISSIONS','/permissions/**','PUT'),(17,'DELETE_PERMISSIONS','/permissions/**','DELETE'),(18,'GET_PRODUCTS','/products/**','GET'),(19,'POST_PRODUCTS','/products/**','POST'),(20,'PUT_PRODUCTS','/products/**','PUT'),(21,'DELETE_PRODUCTS','/products/**','DELETE'),(22,'GET_SUPPLIERS','/suppliers/**','GET'),(23,'POST_SUPPLIERS','/suppliers/**','POST'),(24,'PUT_SUPPLIERS','/suppliers/**','PUT'),(25,'DELETE_SUPPLIERS','/suppliers/**','DELETE'),(26,'GET_SIZE_OPTIONS','/size-options/**','GET'),(27,'POST_SIZE_OPTIONS','/size-options/**','POST'),(28,'PUT_SIZE_OPTIONS','/size-options/**','PUT'),(29,'DELETE_SIZE_OPTIONS','/size-options/**','DELETE'),(30,'GET_COLORS','/colors/**','GET'),(31,'POST_COLORS','/colors/**','POST'),(32,'PUT_COLORS','/colors/**','PUT'),(33,'DELETE_COLORS','/colors/**','DELETE'),(34,'ADMIN:UPDATE:USER','users/**','PUT');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) NOT NULL,
  `image_type` varchar(255) NOT NULL,
  `product_item_id` bigint DEFAULT NULL,
  `upload_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FKpgs3npmtenia3q21263ce7t7u` (`product_item_id`),
  CONSTRAINT `product_images_ibfk_1` FOREIGN KEY (`product_item_id`) REFERENCES `product_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` (`id`, `image_url`, `image_type`, `product_item_id`, `upload_at`) VALUES (4,'Passo-PA-1522A-V-ct1.jpg_661277e0-9aeb-463b-b5d8-9e1e329904a7','MAIN',4,'2025-03-21 23:49:42'),(5,'PA-1522B-ct1.jpg_b6ae2e48-3a67-4d01-a7e8-5ecb939443f0','SUB',4,'2025-03-21 23:49:45'),(6,'PA-1522C-ct1.jpg_37a02a48-235a-4b16-9ea3-8b8ef0d3656a','SUB',4,'2025-03-21 23:49:48'),(7,'030.jpg_0add9630-d983-418d-bc92-632e48afb9c3','SUB',4,'2025-03-21 23:49:49'),(8,'0303.jpg_a22c067a-dcf1-4d7f-9c27-dd6f4130027b','SUB',4,'2025-03-21 23:49:50'),(9,'1736192520_1.jpg_a7ac4d88-70e8-4f80-85ea-a4765c73add4','MAIN',5,'2025-03-29 03:59:02'),(10,'1736192523_2.jpg_745844e0-9b6b-4e8b-ac61-eb46125af725','SUB',5,'2025-03-29 03:59:05'),(11,'1736192612_tesla20tes-00123.png_c47d1a18-b0d4-4db7-bc92-e0bbfabbe249','SUB',5,'2025-03-29 03:59:07');
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_items`
--

DROP TABLE IF EXISTS `product_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` text,
  `stock` int DEFAULT NULL,
  `warranty` varchar(255) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_items_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_items`
--

LOCK TABLES `product_items` WRITE;
/*!40000 ALTER TABLE `product_items` DISABLE KEYS */;
INSERT INTO `product_items` (`id`, `description`, `stock`, `warranty`, `product_id`, `size`) VALUES (4,'hịn',5,'16 tháng',7,'W80-L75-H110'),(5,'cao cấp',8,'2 năm',8,'W70-L160-H70');
/*!40000 ALTER TABLE `product_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_material`
--

DROP TABLE IF EXISTS `product_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_material` (
  `product_item_id` bigint NOT NULL,
  `material_id` bigint NOT NULL,
  KEY `FK6uuu5e4lo8i9eprdau4hi2799` (`material_id`),
  KEY `FKnuvay6t2k14gq3was5jn76l55` (`product_item_id`),
  CONSTRAINT `FK6uuu5e4lo8i9eprdau4hi2799` FOREIGN KEY (`material_id`) REFERENCES `materials` (`id`),
  CONSTRAINT `FKnuvay6t2k14gq3was5jn76l55` FOREIGN KEY (`product_item_id`) REFERENCES `product_items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_material`
--

LOCK TABLES `product_material` WRITE;
/*!40000 ALTER TABLE `product_material` DISABLE KEYS */;
INSERT INTO `product_material` (`product_item_id`, `material_id`) VALUES (4,1),(4,6),(5,1),(5,3);
/*!40000 ALTER TABLE `product_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_materials`
--

DROP TABLE IF EXISTS `product_materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_materials` (
  `material_id` bigint NOT NULL,
  `product_item_id` bigint NOT NULL,
  PRIMARY KEY (`material_id`,`product_item_id`),
  KEY `product_item_id` (`product_item_id`),
  CONSTRAINT `product_materials_ibfk_1` FOREIGN KEY (`material_id`) REFERENCES `materials` (`id`),
  CONSTRAINT `product_materials_ibfk_2` FOREIGN KEY (`product_item_id`) REFERENCES `product_items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_materials`
--

LOCK TABLES `product_materials` WRITE;
/*!40000 ALTER TABLE `product_materials` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_promotion`
--

DROP TABLE IF EXISTS `product_promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_promotion` (
  `product_id` bigint NOT NULL,
  `promotion_id` bigint NOT NULL,
  KEY `FK8cwbpy6c6dnxcah4l8tyt0la3` (`promotion_id`),
  KEY `FKk8v2k4230avk5u4eatqyld4db` (`product_id`),
  CONSTRAINT `FK8cwbpy6c6dnxcah4l8tyt0la3` FOREIGN KEY (`promotion_id`) REFERENCES `promotions` (`id`),
  CONSTRAINT `FKk8v2k4230avk5u4eatqyld4db` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_promotion`
--

LOCK TABLES `product_promotion` WRITE;
/*!40000 ALTER TABLE `product_promotion` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_videos`
--

DROP TABLE IF EXISTS `product_videos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_videos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `video_url` varchar(255) DEFAULT NULL,
  `product_item_id` bigint DEFAULT NULL,
  `upload_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK29non5s8mbkpx8b464iq0ammm` (`product_item_id`),
  CONSTRAINT `product_videos_ibfk_1` FOREIGN KEY (`product_item_id`) REFERENCES `product_items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_videos`
--

LOCK TABLES `product_videos` WRITE;
/*!40000 ALTER TABLE `product_videos` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_videos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `tag` varchar(20) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `supplier_id` bigint DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  KEY `supplier_id` (`supplier_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` (`id`, `code`, `name`, `slug`, `tag`, `price`, `category_id`, `supplier_id`, `is_active`, `created_at`, `updated_at`) VALUES (7,'XXG2985','Ghế giám đốc cao cấp','ghe-giam-oc-cao-cap','NEW',15000000.00,8,1,1,'2025-03-19 11:48:48','2025-03-21 23:49:50'),(8,'OKT2972','Bàn giám đốc cao câp','ban-giam-oc-cao-cap','NEW',13000000.00,2,1,1,'2025-03-29 03:58:59','2025-03-29 03:58:59');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion_category`
--

DROP TABLE IF EXISTS `promotion_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion_category` (
  `promotion_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  `apply_to_subcategories` bit(1) NOT NULL,
  PRIMARY KEY (`promotion_id`,`category_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `promotion_category_ibfk_1` FOREIGN KEY (`promotion_id`) REFERENCES `promotions` (`id`),
  CONSTRAINT `promotion_category_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion_category`
--

LOCK TABLES `promotion_category` WRITE;
/*!40000 ALTER TABLE `promotion_category` DISABLE KEYS */;
INSERT INTO `promotion_category` (`promotion_id`, `category_id`, `apply_to_subcategories`) VALUES (1,1,_binary ''),(2,1,_binary ''),(3,2,_binary '');
/*!40000 ALTER TABLE `promotion_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion_condition`
--

DROP TABLE IF EXISTS `promotion_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion_condition` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `promotion_id` bigint DEFAULT NULL,
  `discount_value` decimal(38,2) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `promotion_id` (`promotion_id`),
  CONSTRAINT `promotion_condition_ibfk_1` FOREIGN KEY (`promotion_id`) REFERENCES `promotions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion_condition`
--

LOCK TABLES `promotion_condition` WRITE;
/*!40000 ALTER TABLE `promotion_condition` DISABLE KEYS */;
INSERT INTO `promotion_condition` (`id`, `promotion_id`, `discount_value`, `start_date`, `end_date`) VALUES (1,1,0.10,'2025-03-01 00:00:00','2025-03-31 00:00:00'),(2,2,0.20,'2025-03-01 02:15:00','2025-03-31 16:59:59'),(3,3,0.20,'2025-03-01 02:15:00','2025-03-31 16:59:59');
/*!40000 ALTER TABLE `promotion_condition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotions`
--

DROP TABLE IF EXISTS `promotions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotions`
--

LOCK TABLES `promotions` WRITE;
/*!40000 ALTER TABLE `promotions` DISABLE KEYS */;
INSERT INTO `promotions` (`id`, `code`, `title`, `description`, `is_active`, `created_at`, `updated_at`) VALUES (1,'Z6M0E5O3','Tháng ba sale thả ga','Xả hàng',1,NULL,NULL),(2,'C3C3X8A2','Tháng Ba Sale Thả Ga','Xả hàng cực mạnh',1,'2025-03-29 02:52:57','2025-03-29 02:52:57'),(3,'O8E0K2N5','Tháng Ba Sale Thả Ga','Xả hàng cực mạnh',1,'2025-03-29 04:02:05','2025-03-29 04:02:05');
/*!40000 ALTER TABLE `promotions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1,1),(1,2),(2,2),(1,3),(1,4),(1,5),(1,6),(2,6),(1,7),(1,8),(1,9),(1,10),(2,10),(1,11),(1,12),(1,13),(1,14),(2,14),(1,15),(1,16),(1,17),(1,18),(2,18),(1,19),(1,20),(1,21),(1,22),(2,22),(1,23),(1,24),(1,25),(1,26),(2,26),(1,27),(1,28),(1,29),(1,30),(2,30),(1,31),(1,32),(1,33),(1,34);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `name`, `description`, `created_at`, `updated_at`) VALUES (1,'admin','Administrator role with full permissions','2025-02-24 08:30:57','2025-02-24 08:30:57'),(2,'user','User role with some permissions','2025-02-24 09:09:42','2025-03-15 02:08:36'),(3,'admin','Admin role with some permissions','2025-04-24 10:28:37','2025-04-24 10:28:37');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `search_history`
--

DROP TABLE IF EXISTS `search_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `search_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `search_tags` text,
  `search_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `search_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `search_history`
--

LOCK TABLES `search_history` WRITE;
/*!40000 ALTER TABLE `search_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `search_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipments`
--

DROP TABLE IF EXISTS `shipments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `order_id` bigint DEFAULT NULL,
  `shipping_fee` decimal(10,2) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  `shipment_date` date DEFAULT NULL,
  `expected_delivery_date` date DEFAULT NULL,
  `shipment_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `code_2` (`code`),
  KEY `order_id` (`order_id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `shipments_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `shipments_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipments`
--

LOCK TABLES `shipments` WRITE;
/*!40000 ALTER TABLE `shipments` DISABLE KEYS */;
INSERT INTO `shipments` (`id`, `code`, `order_id`, `shipping_fee`, `status`, `employee_id`, `shipment_date`, `expected_delivery_date`, `shipment_address`) VALUES (1,'FMXVN09572080421B',7,200000.00,'pending',1,'2025-04-03','2025-04-05',NULL),(2,'FMXVN00246892715B',7,200000.00,'pending',1,'2025-04-03','2025-04-05',NULL),(3,'FMXVN03100494418B',7,200000.00,'pending',1,'2025-04-03','2025-04-05',NULL),(4,'FMXVN08932423125B',8,200000.00,'pending',1,'2025-04-03','2025-04-05',NULL);
/*!40000 ALTER TABLE `shipments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` (`id`, `name`, `description`, `is_active`) VALUES (1,'Nội thất ABC','Thương hiệu tốt, hàng chất lượng',1),(2,'Nội thất ABC','Thương hiệu tốt, hàng chất lượng',0);
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fullname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` varchar(255) DEFAULT NULL,
  `code_expiration` timestamp NULL DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `product_item_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK1lqnd29hylf4rdyqrayxhex5c` (`order_id`),
  KEY `role_id` (`role_id`),
  KEY `FKbkefr8b1demmhbd543baeq7ei` (`product_item_id`),
  CONSTRAINT `FK58jef2oi0dogn5n6chm0cvmnl` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKbkefr8b1demmhbd543baeq7ei` FOREIGN KEY (`product_item_id`) REFERENCES `product_items` (`id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `fullname`, `email`, `phone`, `address`, `image_url`, `password`, `role_id`, `is_active`, `created_at`, `updated_at`, `verification_code`, `code_expiration`, `order_id`, `product_item_id`) VALUES (8,'Nguyen Van B','hobadat2022@gmail.com','0987654321','54, Phố Triều Khúc, Phường Thanh Xuân Bắc, Quận Thanh Xuân, Hà Nội',NULL,'{bcrypt}$2a$10$6E0BBu4jw1NS7tAvfIcLge8HWjPJmMiGSAm0XMyeW8Fv9hInQ/vMy',2,1,'2025-03-10 11:42:46','2025-04-02 19:06:26',NULL,NULL,NULL,NULL),(9,'Ho Ba Dat','hobadat21032003@gmail.com','0334204369','Vinhomes Royal City, Hà Nội, Việt Nam','https://res.cloudinary.com/dln3udzvm/image/upload/v1745596860/AVATAR/AVATAR_tadaboh.jpg','{bcrypt}$2a$10$ATpw3874g7wUsmrQZSO2rOcU5aiBHUxiHglPRPB2WEHTBydUUBc1a',1,1,'2025-03-16 02:15:39','2025-04-25 09:10:26',NULL,NULL,NULL,NULL),(10,'Giang A Pao','giangaphao@gmail.com','0123456777',NULL,NULL,'{bcrypt}$2a$10$RbaNVSnupv/A6W7OcWHfresj9E8YhB4/NKDH47ZE96A2E7dp/4jsy',1,NULL,'2025-04-17 09:22:37','2025-04-17 09:22:37','852539','2025-04-17 09:26:14',NULL,NULL),(11,'Ho Dat','hobadat0303@gmail.com','0123456788',NULL,NULL,'{bcrypt}$2a$10$FwBYrXHPmyibxbVCKU8j5O8kM.ok4yQLY93iujL1x58wlykvdqaOK',1,NULL,'2025-04-17 10:37:23','2025-04-17 10:37:23','965042','2025-04-17 10:17:18',NULL,NULL),(12,'Nong A Long','code.technology0820212727@gmail.com','0123456666',NULL,NULL,'{bcrypt}$2a$10$j4OnS3u6FANgDFw4IBJynuPRXjDTdFVHd7pg3jT12hP22pJCiL/Oq',1,NULL,'2025-04-17 12:29:24','2025-04-17 12:29:24','393419','2025-04-17 10:17:18',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wish_list`
--

DROP TABLE IF EXISTS `wish_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wish_list` (
  `user_id` bigint NOT NULL,
  `product_item_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`product_item_id`),
  KEY `FKkl2njf4ehq0us3o0ayxqawyrs` (`product_item_id`),
  CONSTRAINT `wish_list_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `wish_list_ibfk_2` FOREIGN KEY (`product_item_id`) REFERENCES `product_items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish_list`
--

LOCK TABLES `wish_list` WRITE;
/*!40000 ALTER TABLE `wish_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `wish_list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-21 18:01:50
