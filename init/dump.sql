-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: test_db
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_group` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (3,'不具合報告'),(5,'機能について'),(8,'新規追加'),(9,'火影になる'),(10,'目指す場所');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'カテゴリー',
  `body` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '本文',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '0:未対応,1:対応中,2:対応済み',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'kaede',NULL,'2','sasasa',2,'2026-04-26 05:08:43','2026-04-27 08:02:39'),(2,'なんで',NULL,'2','あいうえお',1,'2026-04-27 04:49:01','2026-04-27 08:02:28'),(3,'かえで',NULL,'1','不具合あり',1,'2026-05-01 08:11:10','2026-05-01 08:11:24'),(4,'sasa',NULL,'1','sassas',0,'2026-05-01 09:06:09','2026-05-01 09:06:09'),(5,'sasasa',NULL,'1','sasasa',1,'2026-05-01 09:06:24','2026-05-01 09:06:33'),(6,'sagana','k.nakamura@oplan.co.jp','5','kinounituite',0,'2026-05-06 03:23:41','2026-05-06 03:23:41'),(7,'kasasa','k.nakamura@oplan.co.jp','3','sasasfasdfdsdsdfasd',0,'2026-05-06 03:31:00','2026-05-06 03:31:00'),(8,'kasasa','k.nakamura@oplan.co.jp','3','sasasfasdfdsdsdfasd',0,'2026-05-06 04:00:03','2026-05-06 04:00:03'),(9,'なかむら','k.nakamura@oplan.co.jp','2','sasasafd',0,'2026-05-06 04:14:05','2026-05-06 04:14:05'),(10,'sasasa','k@gmail.com','2','sasaaa',1,'2026-05-06 09:49:18','2026-05-07 11:26:42'),(11,'nakamura','k.nakamura@oplan.co.jp','5','gasgagagaga',0,'2026-05-06 23:51:50','2026-05-06 23:51:50'),(12,'さささ','dsdsd@gmail.com','2','sasaasasasa',1,'2026-05-07 00:04:35','2026-05-07 11:26:05'),(13,'sasasas','sasasa@gmail.com','3','saasasaas',1,'2026-05-07 00:07:58','2026-05-07 11:26:12'),(14,'さささ','sasasaasa@gmail.com','4','sasasas',0,'2026-05-07 00:18:29','2026-05-07 00:18:29'),(16,'ささささ','k.nakamura@oplan.co.jp','2','sasasaasasssa',0,'2026-05-07 08:56:37','2026-05-07 08:56:37'),(17,'sasasa','pvqv31501@gmail.com','3','sasasasaass',0,'2026-05-07 09:05:39','2026-05-07 09:05:39'),(18,'sasasa','pvqv31501@gmail.com','3','sasasasaass',0,'2026-05-07 09:05:47','2026-05-07 09:05:47'),(19,'sasasas','k.nakamura@oplan.co.jp','2','sasasasss',0,'2026-05-07 09:18:31','2026-05-07 09:18:31'),(20,'sasasas','k.nakamura@oplan.co.jp','3','sasasa',0,'2026-05-08 07:12:18','2026-05-08 07:12:18'),(21,'さささ','ka.nakamura@oplan.co.jp','3','zzzzzzz',1,'2026-05-08 07:22:32','2026-05-11 12:15:52'),(22,'sasas','k.nakamura@oplan.co.jp','3','sasasa',0,'2026-05-08 07:29:26','2026-05-08 07:29:26'),(23,'saa','ka.nakamura@oplan.co.jp','3','sasasa',0,'2026-05-08 07:30:30','2026-05-08 07:30:30'),(24,'sasasa','k.nakamura@oplan.co.jp','3','saasasa',0,'2026-05-08 07:37:19','2026-05-08 07:37:19'),(25,'sasaa','k.nakamura@oplan.co.jp','3','sasa',0,'2026-05-08 07:42:05','2026-05-08 07:42:05'),(26,'sasasa','k.nakamura@oplan.co.jp','3','sasa',0,'2026-05-08 07:55:14','2026-05-08 07:55:14'),(27,'sasaa','k.nakamura@oplan.co.jp','3','sasas',0,'2026-05-08 08:01:57','2026-05-08 08:01:57'),(28,'sasassas','k.nakamura@oplan.co.jp','3','saaqsasas',0,'2026-05-08 08:04:23','2026-05-08 08:04:23'),(29,'sasasa','k.nakamura@oplan.co.jp','3','sasass',0,'2026-05-08 08:15:23','2026-05-08 08:15:23'),(30,'saas','k.nakamura@oplan.co.jp','3','saassasa',1,'2026-05-08 08:20:04','2026-05-11 12:15:12'),(31,'saassass','k.nakamura@oplan.co.jp','3','sasssasaas',2,'2026-05-08 08:24:30','2026-05-11 12:15:17'),(32,'sasasaasasssa','k.nakamura@oplan.co.jp','9','hdisfhsofhfh',0,'2026-05-11 12:32:00','2026-05-11 12:32:00'),(33,'ささあ','pvqv31501@gmail.com','5','zsssddf',0,'2026-05-12 13:23:57','2026-05-12 13:23:57'),(34,'中村楓楓','pvqv31501@gmail.com','3','sasaa',0,'2026-05-12 13:31:28','2026-05-12 13:31:28'),(35,'testname','test@example','システム','ユニットテストの内容',0,'2026-05-14 14:58:36','2026-05-14 14:58:36'),(36,'test','test@example.com','質問','test内容',0,'2026-05-14 14:58:36','2026-05-14 14:58:36');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `from_user_id` int NOT NULL,
  `to_user_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `is_delete` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_like` (`from_user_id`,`to_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (1,5,6,'2026-05-01 05:08:56','2026-05-01 05:09:01',NULL,'1'),(2,5,5,'2026-05-01 05:08:58','2026-05-01 05:09:01',NULL,'1'),(3,5,1,'2026-05-01 05:08:58','2026-05-01 05:09:00',NULL,'1'),(14,7,7,'2026-05-01 05:53:33','2026-05-01 05:53:33',NULL,'0'),(15,7,6,'2026-05-01 05:53:34','2026-05-01 05:53:45',NULL,'1'),(16,7,5,'2026-05-01 05:53:35','2026-05-01 05:54:07',NULL,'1'),(17,7,1,'2026-05-01 05:53:37','2026-05-01 05:53:37',NULL,'0'),(18,5,7,'2026-05-01 06:11:48','2026-05-01 09:07:23',NULL,'1'),(19,5,9,'2026-05-01 09:07:20','2026-05-01 09:07:20',NULL,'0'),(20,5,8,'2026-05-01 09:07:22','2026-05-01 09:07:28',NULL,'1'),(21,9,10,'2026-05-01 09:11:36','2026-05-01 09:11:36',NULL,'0'),(22,1,12,'2026-05-06 08:43:18','2026-05-06 08:43:18',NULL,'0'),(23,1,11,'2026-05-06 08:43:19','2026-05-06 08:43:19',NULL,'0'),(24,1,10,'2026-05-06 08:43:20','2026-05-06 08:43:21',NULL,'1'),(25,1,7,'2026-05-06 08:43:23','2026-05-06 08:56:10',NULL,'1'),(26,1,9,'2026-05-06 08:43:25','2026-05-06 08:44:13',NULL,'1'),(27,1,5,'2026-05-06 08:43:27','2026-05-06 08:44:25',NULL,'1'),(28,1,6,'2026-05-06 08:43:49','2026-05-06 08:46:51',NULL,'1'),(29,1,1,'2026-05-06 08:43:59','2026-05-06 08:44:02',NULL,'1'),(30,1,2,'2026-05-07 05:31:13','2026-05-07 05:31:13',NULL,'0'),(32,16,16,'2026-05-07 11:54:42','2026-05-07 11:54:42',NULL,'0'),(33,16,15,'2026-05-07 11:54:43','2026-05-07 11:54:43',NULL,'0'),(34,16,14,'2026-05-07 11:54:44','2026-05-07 11:54:44',NULL,'0'),(35,16,12,'2026-05-07 11:54:53','2026-05-07 11:54:53',NULL,'0'),(36,16,13,'2026-05-07 11:54:57','2026-05-07 11:54:57',NULL,'0'),(37,16,11,'2026-05-07 11:54:58','2026-05-07 11:54:58',NULL,'0'),(38,16,7,'2026-05-07 11:54:59','2026-05-07 11:54:59',NULL,'0'),(39,16,9,'2026-05-07 11:55:00','2026-05-07 11:55:00',NULL,'0'),(40,16,10,'2026-05-07 11:55:01','2026-05-07 11:55:01',NULL,'0'),(41,16,6,'2026-05-07 11:55:03','2026-05-07 11:55:03',NULL,'0'),(42,16,5,'2026-05-07 11:55:04','2026-05-07 11:55:05',NULL,'1'),(43,17,17,'2026-05-07 12:03:08','2026-05-07 12:03:08',NULL,'0'),(44,17,16,'2026-05-07 12:03:09','2026-05-08 05:33:20',NULL,'0'),(45,17,15,'2026-05-07 12:03:18','2026-05-08 05:33:22',NULL,'0'),(46,17,10,'2026-05-07 12:03:37','2026-05-08 05:33:14',NULL,'0'),(47,17,6,'2026-05-08 03:05:32','2026-05-08 05:51:39',NULL,'0'),(48,17,5,'2026-05-08 03:05:43','2026-05-08 05:33:39',NULL,'0'),(49,17,9,'2026-05-08 03:11:44','2026-05-11 12:30:50',NULL,'0'),(50,17,11,'2026-05-08 03:11:46','2026-05-08 05:33:16',NULL,'0'),(51,17,12,'2026-05-08 03:11:48','2026-05-08 05:33:18',NULL,'0'),(52,17,7,'2026-05-08 03:13:23','2026-05-08 05:33:04',NULL,'0'),(53,17,1,'2026-05-08 04:43:09','2026-05-08 05:51:34',NULL,'0'),(54,17,13,'2026-05-08 05:33:24','2026-05-08 05:33:25',NULL,'1'),(55,17,14,'2026-05-08 05:33:26','2026-05-11 12:30:02',NULL,'0');
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ruby` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_at` timestamp NULL DEFAULT NULL,
  `role` int DEFAULT '0',
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `bio` mediumtext COLLATE utf8mb4_unicode_ci,
  `profile_image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_deleted` int DEFAULT '0',
  `status` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (5,'naruto','なると','n@gmail.com','naruto12345','2026-04-25 11:26:33','2026-05-12 09:32:56',NULL,0,'male',29,'sasa✨','スクリーンショット 2026-04-14 124050.png',0,1),(6,'ふうか','ふうか','F@gmail.com','password','2026-04-25 11:27:41','2026-05-12 13:37:08',NULL,0,'female',22,'fuuka','スクリーンショット 2026-04-14 124050.png',0,0),(7,'サスケ','さすけ','s@gmail.com','naruto12345','2026-05-01 05:53:12','2026-05-01 05:53:12',NULL,0,'male',17,'sasuke','スクリーンショット 2026-04-14 124050.png',0,1),(9,'かえで','','ka@gmail.com','naruto12345','2026-05-01 07:40:39','2026-05-01 09:33:58',NULL,1,NULL,0,'','スクリーンショット 2026-04-14 124050.png',0,1),(10,'sasaa','か','sa@gmail.com','naruto12345','2026-05-01 09:10:32','2026-05-12 10:27:20',NULL,0,'male',21,'2222','スクリーンショット 2026-04-14 124050.png',0,0),(11,'hadaga','','sasasaasa@gmail.com','naruto12345','2026-05-01 09:11:16','2026-05-01 09:11:16',NULL,1,NULL,0,'','default_icon.png',0,1),(12,'sakura','','sakrua@gmail.com','password','2026-05-06 06:28:42','2026-05-06 06:28:42',NULL,1,NULL,0,'','default_icon.png',0,1),(13,'結合テスト','けつごうてすと','integrationTest@example.com','Pass1234','2026-05-07 04:07:02','2026-05-11 12:12:10',NULL,1,'female',30,'hello','icon.png',1,1),(14,'テストさん','てすとたろう','test@example.com','Password123!','2026-05-07 04:09:59','2026-05-07 04:09:59',NULL,1,'male',25,'自己紹介','default.png',0,1),(15,'kakashi','','kakashi@gmail.com','password','2026-05-07 09:29:54','2026-05-07 09:29:54',NULL,1,NULL,0,'','default_icon.png',0,1),(16,'shiori','','shiori@gmail.com','shiori1234','2026-05-07 09:36:07','2026-05-07 11:56:18',NULL,1,NULL,0,'','スクリーンショット 2026-04-14 124050.png',0,1),(17,'hinata','ひなた','hinata@gmail.com','hinata1234','2026-05-07 12:01:02','2026-05-11 12:25:31',NULL,0,'female',15,'日向ひなたです✨','スクリーンショット 2026-04-14 124050.png',0,1),(18,'test',NULL,'test@gmail.com','password','2026-05-12 02:55:03','2026-05-12 10:53:14',NULL,1,NULL,0,NULL,'default_icon.png',0,0),(19,'sasa',NULL,'sasa@g.com','password','2026-05-12 02:55:33','2026-05-12 02:55:33',NULL,1,NULL,0,NULL,'default_icon.png',0,1),(20,'sj',NULL,'sj@gmail.com','password','2026-05-12 03:05:13','2026-05-12 03:05:13',NULL,1,NULL,0,NULL,'default_icon.png',0,1),(21,'m',NULL,'m@gmail.com','password','2026-05-12 09:50:06','2026-05-12 10:52:58',NULL,1,NULL,0,NULL,'default_icon.png',0,1),(22,'結合テスト','けつごうてすと','integrationTest@example.com','Pass1234','2026-05-14 14:56:28','2026-05-14 14:56:28',NULL,1,'female',30,'hello','icon.png',0,0),(23,'テストさん','てすとたろう','test@example.com','Password123!','2026-05-14 14:56:28','2026-05-14 14:56:28',NULL,1,'male',25,'自己紹介','default.png',0,0),(24,'結合テスト','けつごうてすと','integrationTest@example.com','Pass1234','2026-05-14 14:58:36','2026-05-14 14:58:36',NULL,1,'female',30,'hello','icon.png',0,0),(25,'テストさん','てすとたろう','test@example.com','Password123!','2026-05-14 14:58:36','2026-05-14 14:58:36',NULL,1,'male',25,'自己紹介','default.png',0,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-18 18:41:21
