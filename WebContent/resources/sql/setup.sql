CREATE DATABASE  IF NOT EXISTS `csp584_project` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `csp584_project`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: csp584_project
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer_profile`
--

DROP TABLE IF EXISTS `customer_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_profile` (
  `cID` int(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `creditCard` int(255) DEFAULT NULL,
  `creditCardExpDate` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `zipcode` int(10) DEFAULT NULL,
  `uID` int(255) DEFAULT NULL,
  PRIMARY KEY (`cID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_profile`
--

LOCK TABLES `customer_profile` WRITE;
/*!40000 ALTER TABLE `customer_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hotel` (
  `seq_no` int(11) NOT NULL AUTO_INCREMENT,
  `location` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `description` text,
  `image_link` text,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
INSERT INTO `hotel` VALUES (1,1,'Hampton Inn Majestic Chicago Theatre District','22 W. Monroe','The Hampton Majestic experience is unlike any other, attached to CIBC Theatre and steps from State Street, museums, Millennium Park and Willis Tower.','1.jpg,2.jpg,3.jpg,4.jpg,5.jpg,6.jpg,7.jpg,8.jpg,9.jpg,10.jpg,',0),(2,2,'theWit Chicago - a DoubleTree by Hilton Hotel','201 N. State Street','Award-winning downtown hotel and rooftop bar offers a dramatic sensory experience, blending the amenities of a resort with the flair of a boutique hotel','1.jpg,2.jpg,3.jpg,4.jpg,5.jpg,6.jpg,7.jpg,8.jpg,9.jpg,10.jpg,11.jpg,12.jpg,',0),(3,1,'Palmer House - A Hilton Hotel','17 East Monroe Street','AAA Four Diamond, historic hotel located in the heart of the Loop. Steps from theatre, shopping, the Art Institute of Chicago and Millennium Park.','1.jpg,2.jpg,3.jpg,4.jpg,5.jpg,6.jpg,7.jpg,8.jpg,9.jpg,10.jpg,11.jpg,12.jpg,',0);
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `seq_no` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(10) DEFAULT NULL,
  `zip` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Chicago','IL','60603'),(2,'Chicago','IL','60601');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `seq_no` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `hotel` int(11) DEFAULT NULL,
  `room` text,
  `check_in` datetime DEFAULT NULL,
  `check_out` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_type`
--

DROP TABLE IF EXISTS `room_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_type` (
  `seq_no` int(11) NOT NULL AUTO_INCREMENT,
  `hotel` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `bed_type` varchar(255) DEFAULT NULL,
  `bed_amount` int(11) DEFAULT NULL,
  `people_no` int(11) DEFAULT NULL,
  `view` varchar(45) DEFAULT NULL,
  `is_wifi` tinyint(4) DEFAULT NULL,
  `is_tv` tinyint(4) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `room_list` text,
  `image` text,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_type`
--

LOCK TABLES `room_type` WRITE;
/*!40000 ALTER TABLE `room_type` DISABLE KEYS */;
INSERT INTO `room_type` VALUES (1,1,'1 King Bed','king_bed',1,2,'City',1,1,139,3,'523,312','1.jpg',0),(2,1,'2 Queen Bed','queen_bed',2,4,'City',1,1,199,4,'101,102','1.jpg',0);
/*!40000 ALTER TABLE `room_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff_profile`
--

DROP TABLE IF EXISTS `staff_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff_profile` (
  `sID` int(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `uID` int(255) DEFAULT NULL,
  PRIMARY KEY (`sID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff_profile`
--

LOCK TABLES `staff_profile` WRITE;
/*!40000 ALTER TABLE `staff_profile` DISABLE KEYS */;
INSERT INTO `staff_profile` VALUES (1,'role manager','Manager',1),(2,'role staff','staff',2);
/*!40000 ALTER TABLE `staff_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `tid` int(255) NOT NULL,
  `room_number` int(255) DEFAULT NULL,
  `customer_id` int(255) DEFAULT NULL,
  `check_in_date` varchar(255) DEFAULT NULL,
  `check_out_date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tid`),
  UNIQUE KEY `room_number` (`room_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `uid` int(255) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'manager','manager','manager@website','manager'),(2,'staff','staff','staff@website','staff');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-27 18:45:25
