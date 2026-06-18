CREATE DATABASE  IF NOT EXISTS `bugtrackingsystem` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bugtrackingsystem`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: bugtrackingsystem
-- ------------------------------------------------------
-- Server version	9.4.0

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
-- Table structure for table `bugs`
--

DROP TABLE IF EXISTS `bugs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bugs` (
  `bug_id` int NOT NULL AUTO_INCREMENT,
  `bug_name` varchar(255) NOT NULL,
  `bug_type` varchar(100) DEFAULT NULL,
  `bug_priority` varchar(50) DEFAULT NULL,
  `project_name` varchar(100) DEFAULT NULL,
  `bug_level` varchar(50) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `reported_by` varchar(100) DEFAULT NULL,
  `assigned_to` varchar(100) DEFAULT NULL,
  `severity` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT 'Open',
  `date_reported` date DEFAULT (curdate()),
  `date_fixed` date DEFAULT NULL,
  PRIMARY KEY (`bug_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bugs`
--

LOCK TABLES `bugs` WRITE;
/*!40000 ALTER TABLE `bugs` DISABLE KEYS */;
INSERT INTO `bugs` VALUES (1,'Slow page load on dashboard','Performance','Low','ECommerceApp','Minor','2025-10-30','Tester A','Developer A','Low','Fixed','2025-10-30',NULL),(2,'Data not saving in DB','Backend','High','InventorySystem','Critical','2025-10-30','Tester B','Developer B','High','Open','2025-10-30',NULL),(3,'Email validation fails','Validation','Medium','RegistrationApp','Major','2025-10-30','Tester C','Developer C','Medium','Open','2025-10-30',NULL),(4,'Search results incorrect','Logic','High','LibrarySystem','Major','2025-10-30','Tester D','Developer D','High','Fixed','2025-10-30',NULL),(5,'Broken image on homepage','UI','Low','PortfolioSite','Trivial','2025-10-30','Tester E','Developer D','Low','Fixed','2025-10-30','2025-11-10'),(6,'Payment gateway timeout','Network','High','ShoppingCart','Critical','2025-10-30','Tester F','Developer F','High','Fixed','2025-10-30',NULL),(7,'Password reset email not sent','Backend','Medium','UserPortal','Major','2025-10-30','Tester G','Developer G','Medium','Fixed','2025-10-30','2026-05-23'),(8,'Dark mode theme mismatch','UI','Low','SocialApp','Minor','2025-10-30','Tester A','Developer A','Low','Closed','2025-10-30',NULL),(9,'API returns 500 error','Backend','High','AnalyticsDashboard','Critical','2025-10-30','Tester B','Developer B','High','Fixed','2025-10-30',NULL),(10,'File upload exceeds limit','Validation','Medium','DocumentManager','Major','2025-10-30','Tester C','Developer C','Medium','Assigned','2025-10-30',NULL),(11,'Notification not displaying','Frontend','Low','ChatSystem','Minor','2025-10-30','Tester D','Developer D','Low','Fixed','2025-10-30',NULL),(12,'Report export broken','Backend','High','HRMS','Critical','2025-10-30','Tester E','Developer E','High','Open','2025-10-30',NULL),(13,'Session timeout too early','Logic','Medium','BankingApp','Major','2025-10-30','Tester F','Developer F','Medium','Closed','2025-10-30',NULL),(14,'Missing validation on signup','Validation','High','RegistrationApp','Major','2025-10-30','Tester G','Developer A','High','Open','2025-10-30',NULL),(15,'Image upload not working','Frontend','High','MediaPortal','Critical','2025-10-30','Tester A','Developer C','High','Fixed','2025-10-30',NULL),(16,'Logout Redirect Failure','Functional Bug','Medium','Bug Pro Manager','Major','2021-11-29','Tester D','Developer F','Medium','Open','2025-10-30',NULL),(17,'Refresh button functionality is not happening','Performance','Low','Online Sales','Minor','2025-11-10','DeveloperD','TesterC','Low','Open','2025-11-10',NULL),(18,'LoginIssue','Performance','Low','Ecommerce','Minor','2025-11-09','TesterH','DeveloperB','Low','Open','2025-11-11',NULL),(19,'Dark Theme Issue','UI/UX','Medium','Bug tracking system','Critical','2025-11-11','TesterB','DeveloperC','Medium','Open','2025-11-11',NULL);
/*!40000 ALTER TABLE `bugs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Admin 1','admin1@example.com','admin123','Admin'),(2,'Admin 2','admin2@example.com','admin123','Admin'),(3,'Admin 3','admin3@example.com','admin123','Admin'),(4,'Admin 4','admin4@example.com','admin123','Admin'),(5,'Admin 5','admin5@example.com','admin123','Admin'),(6,'Admin 6','admin6@example.com','admin123','Admin'),(8,'Developer A','devA@example.com','dev123','Developer'),(9,'Developer B','devB@example.com','dev123','Developer'),(10,'Developer C','devC@example.com','dev123','Developer'),(11,'Developer D','devD@example.com','dev123','Developer'),(12,'Developer E','devE@example.com','dev123','Developer'),(13,'Developer F','devF@example.com','dev123','Developer'),(14,'Developer G','devG@example.com','dev123','Developer'),(15,'Tester 1','tester1@example.com','test123','Tester'),(16,'Tester 2','tester2@example.com','test123','Tester'),(17,'Tester 3','tester3@example.com','test123','Tester'),(18,'Tester 4','tester4@example.com','test123','Tester'),(19,'Tester 5','tester5@example.com','test123','Tester'),(20,'Tester 6','tester6@example.com','test123','Tester'),(21,'Tester 7','tester7@example.com','test123','Tester'),(22,'Project Manager 1','pm1@example.com','pm123','Project Manager'),(23,'Project Manager 2','pm2@example.com','pm123','Project Manager'),(24,'Project Manager 3','pm3@example.com','pm123','Project Manager'),(25,'Project Manager 4','pm4@example.com','pm123','Project Manager'),(26,'Project Manager 5','pm5@example.com','pm123','Project Manager'),(27,'Project Manager 6','pm6@example.com','pm123','Project Manager'),(28,'Project Manager 7','pm7@example.com','pm123','Project Manager'),(29,'john','john@gmail.com','john','Project Manager'),(30,'qwerty','qwerty@gmail.com','1234','Admin'),(31,'Alex','alex@gmail.com','alex123','Developer'),(32,'qwerty','qwert@gmail.com','qwerty','Project Manager'),(33,'bruno','bruno@gmail.com','bruno','Tester'),(34,'deeeeee','dee@gmail.com','dee','Admin'),(35,'dee','dee@gmail.com','dee','Project Manager'),(36,'abc','abc@gmail.com','abc','Tester'),(37,'ASDF','asdf@gmail.com','1234','Tester'),(38,'Aaro','aaro@2gmail.com','1234','Developer'),(39,'asdf','as2df@gmail.com','1234','Tester');
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

-- Dump completed on 2026-06-18  2:34:35
