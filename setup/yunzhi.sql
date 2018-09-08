-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: yunzhi
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chapter` (
  `course_school_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `course_start_date` date NOT NULL,
  `course_end_date` date NOT NULL,
  `sequence_number` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`course_school_id`,`course_id`,`course_start_date`,`course_end_date`,`sequence_number`),
  KEY `fk_chapter_course1_idx` (`course_school_id`,`course_id`,`course_start_date`,`course_end_date`),
  CONSTRAINT `fk_chapter_course` FOREIGN KEY (`course_school_id`, `course_id`, `course_start_date`, `course_end_date`) REFERENCES `course` (`school_id`, `id`, `start_date`, `end_date`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
INSERT INTO `chapter` VALUES (10013,1,'2018-01-10','2018-07-01',1,'第一章'),(10013,1,'2018-01-10','2018-07-01',2,'第二章'),(10013,1,'2018-01-10','2018-07-01',3,'第三章'),(10013,1,'2018-01-10','2018-07-01',4,'第四章'),(10013,1,'2018-01-10','2018-07-01',5,'第五章'),(10013,1,'2018-01-10','2018-07-01',6,'第六章'),(10013,1,'2018-01-10','2018-07-01',7,'第七章'),(10013,1,'2018-01-10','2018-07-01',9,'第九章'),(10013,1,'2018-01-10','2018-07-01',10,'第十章'),(10013,1,'2018-01-10','2018-07-01',11,'第十一章'),(10013,2,'2018-01-10','2018-07-01',1,'章节一'),(10013,3,'2018-01-10','2018-07-01',1,'第一章'),(10013,5,'2018-01-10','2018-07-01',10,'第一章');
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `school_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `name` varchar(45) NOT NULL,
  `cost` varchar(45) NOT NULL DEFAULT '0',
  `logo` varchar(450) DEFAULT '/media/images/logo_course.png',
  `liveroom_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`school_id`,`id`,`start_date`,`end_date`),
  KEY `fk_course_school1_idx` (`school_id`),
  KEY `fk_liveroom_idx` (`liveroom_id`),
  CONSTRAINT `fk_course_school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_liveroom` FOREIGN KEY (`liveroom_id`) REFERENCES `liveroom` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (10013,1,'2018-01-10','2018-07-01','流媒体开发与服务器搭建','0','/media/images/logo_course.png','FTEA0EYD'),(10013,2,'2018-01-10','2018-07-01','服务外包大赛指导','0','/media/images/logo_course.png',NULL),(10013,3,'2018-01-10','2018-07-01','JavaEE','0','/media/images/logo_course.png','k10eaY62'),(10013,4,'2018-01-10','2018-07-01','Git团队开发和代码管理','0','/media/images/logo_course.png',NULL),(10013,5,'2018-01-10','2018-07-01','云智教育系统开发','0','/media/images/logo_course.png',NULL);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend` (
  `userId1` varchar(45) NOT NULL,
  `userId2` varchar(45) NOT NULL,
  `friendShip` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userId1`,`userId2`),
  KEY `fk_friends_user2_idx` (`userId2`),
  CONSTRAINT `fk_friends_user1` FOREIGN KEY (`userId1`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_friends_user2` FOREIGN KEY (`userId2`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
INSERT INTO `friend` VALUES ('123456','13167320611',NULL),('123456','2015212100',NULL),('13167320611','123456',NULL),('13167320611','15611375811',NULL),('13167320611','2015212100',NULL),('15577582651','15577582651',NULL),('15611375811','13167320611',NULL),('15611375811','2015212100',NULL),('18801261120','2015212100',NULL),('2015212100','123456',NULL),('2015212100','13167320611',NULL),('2015212100','15611375811',NULL),('2015212100','18801261120',NULL),('2015212100','2015212100',NULL);
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `identity`
--

DROP TABLE IF EXISTS `identity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `identity` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `identity`
--

LOCK TABLES `identity` WRITE;
/*!40000 ALTER TABLE `identity` DISABLE KEYS */;
INSERT INTO `identity` VALUES ('educational administractor'),('student'),('teacher');
/*!40000 ALTER TABLE `identity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inform`
--

DROP TABLE IF EXISTS `inform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inform` (
  `userid` varchar(45) NOT NULL,
  `talkTo` varchar(45) NOT NULL,
  `newMessage` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userid`,`talkTo`),
  CONSTRAINT `fk_inform_user1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inform`
--

LOCK TABLES `inform` WRITE;
/*!40000 ALTER TABLE `inform` DISABLE KEYS */;
INSERT INTO `inform` VALUES ('123456','123456',0),('123456','13167320611',1),('123456','18801261120',0),('123456','2015212100',0),('13167320611','123456',0),('13167320611','13167320611',0),('13167320611','15600931170',0),('13167320611','15611375811',0),('13167320611','15911187836',0),('13167320611','18801261120',0),('13167320611','18977563026',0),('13167320611','2015212100',0),('13167320611','654321',0),('13167320611','null',0),('15611375811','2015212100',1),('15911187836','13167320611',1),('18801261120','13167320611',0),('18801261120','18977563026',0),('18801261120','2015212100',0),('2015212100','123456',0),('2015212100','13167320611',0),('2015212100','15600931170',0),('2015212100','15611375811',0),('2015212100','18801261120',0),('2015212100','18977563026',0),('2015212100','2015212100',0),('2015212100','654321',0),('654321','13167320611',0);
/*!40000 ALTER TABLE `inform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liveroom`
--

DROP TABLE IF EXISTS `liveroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `liveroom` (
  `id` varchar(45) NOT NULL,
  `rtmp` varchar(450) NOT NULL,
  `stream` varchar(450) NOT NULL,
  `src` varchar(450) NOT NULL,
  `title` varchar(450) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liveroom`
--

LOCK TABLES `liveroom` WRITE;
/*!40000 ALTER TABLE `liveroom` DISABLE KEYS */;
INSERT INTO `liveroom` VALUES ('FTEA0EYD','rtmp://demo.srs.com/live','64dLFSuUroVjUgnM','http://10.206.11.101:8888/live/64dLFSuUroVjUgnM.m3u8','测试的直播间'),('index','rtmp://demo.srs.com/live','index','http://10.206.11.101:8888/live/index.m3u8','首页直播间'),('k10eaY62','rtmp://demo.srs.com/live','rvW072M3XF7AR8C7','http://10.206.11.101:8888/live/rvW072M3XF7AR8C7.m3u8','JavaEE的直播间');
/*!40000 ALTER TABLE `liveroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `userid` varchar(45) NOT NULL,
  `talkTo` varchar(45) NOT NULL,
  `datetime` datetime NOT NULL,
  `whospeak` varchar(45) NOT NULL,
  `msg` text NOT NULL,
  PRIMARY KEY (`userid`,`talkTo`,`datetime`),
  CONSTRAINT `fk_userid_user` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES ('123456','13167320611','2018-04-18 09:20:35','dawsonlee1790','你好'),('123456','2015212100','2018-04-12 19:03:22','mary','你好，我是mary'),('123456','2015212100','2018-04-12 19:03:58','Test1','你好，test1,你有什么问题吗'),('13167320611','123456','2018-04-18 09:20:35','dawsonlee1790','你好'),('13167320611','13167320611','2018-04-12 15:24:05','dawsonlee1790','我是你自己'),('13167320611','13167320611','2018-04-12 15:55:16','dawsonlee1790','你好我自己'),('13167320611','15911187836','2018-04-12 18:08:49','庞敬','李少'),('13167320611','15911187836','2018-04-12 18:10:46','庞敬','李少'),('13167320611','15911187836','2018-04-12 19:09:39','dawsonlee1790','Ok'),('13167320611','18801261120','2018-04-12 23:53:54','不知名人','jjjjjjjj'),('13167320611','18801261120','2018-04-12 23:53:59','不知名人','jijiji'),('13167320611','18977563026','2018-04-12 23:49:38','juanzi63026','呵呵'),('13167320611','18977563026','2018-04-12 23:50:39','dawsonlee1790','看到了'),('13167320611','18977563026','2018-04-12 23:50:43','juanzi63026','东东'),('13167320611','18977563026','2018-04-12 23:50:44','dawsonlee1790','妈妈'),('13167320611','18977563026','2018-04-12 23:50:51','dawsonlee1790','还可以把'),('13167320611','18977563026','2018-04-12 23:51:03','juanzi63026','嗯嗯，不错不错'),('13167320611','18977563026','2018-04-12 23:51:13','dawsonlee1790','你可以加我为好友'),('13167320611','18977563026','2018-04-12 23:51:19','dawsonlee1790','长按'),('13167320611','18977563026','2018-04-12 23:51:34','juanzi63026','我看看怎么加'),('13167320611','18977563026','2018-04-12 23:51:39','dawsonlee1790','不行'),('13167320611','18977563026','2018-04-12 23:51:49','dawsonlee1790','在浏览器里不能长按'),('13167320611','18977563026','2018-04-12 23:52:08','dawsonlee1790','你试试你那边可以长按吗'),('13167320611','18977563026','2018-04-12 23:52:44','juanzi63026','不知道加成了没有'),('13167320611','18977563026','2018-04-12 23:54:16','dawsonlee1790','我看看'),('13167320611','18977563026','2018-04-12 23:54:52','dawsonlee1790','没有加成功'),('13167320611','18977563026','2018-04-12 23:55:15','juanzi63026','那怎么加?'),('13167320611','18977563026','2018-04-12 23:55:27','dawsonlee1790','应该加成功了'),('13167320611','18977563026','2018-04-12 23:55:37','dawsonlee1790','我这是有个小bug'),('13167320611','18977563026','2018-04-12 23:55:51','dawsonlee1790','这里是有一个小bug '),('13167320611','18977563026','2018-04-12 23:56:15','dawsonlee1790','应该是你能看到加了我，我看不到你加了我'),('13167320611','18977563026','2018-04-12 23:58:17','juanzi63026','加上了'),('13167320611','18977563026','2018-04-12 23:58:31','juanzi63026','妈妈这边看到你了'),('13167320611','2015212100','2018-04-12 15:23:35','dawsonlee1790','mary你好，我是dawson'),('13167320611','2015212100','2018-04-12 15:56:03','mary','你好Dawson，我是mary'),('13167320611','2015212100','2018-04-12 17:50:27','dawsonlee1790','你好'),('13167320611','2015212100','2018-04-12 17:50:45','dawsonlee1790','我是dawson,晚安'),('13167320611','2015212100','2018-04-12 17:57:18','dawsonlee1790','我是dawson,newMessage'),('13167320611','2015212100','2018-04-12 18:11:31','mary','李少'),('13167320611','2015212100','2018-04-12 18:11:42','dawsonlee1790','庞敬'),('13167320611','2015212100','2018-04-12 19:08:53','dawsonlee1790','测试消息'),('13167320611','2015212100','2018-04-12 19:29:18','mary','12313213'),('13167320611','2015212100','2018-04-12 19:30:34','mary','166221'),('13167320611','2015212100','2018-04-16 13:32:35','dawsonlee1790','老师您好'),('13167320611','2015212100','2018-04-16 13:46:52','dawsonlee1790','这是测试消息'),('13167320611','2015212100','2018-04-16 14:01:14','dawsonlee1790','这是测试消息'),('13167320611','2015212100','2018-04-16 14:50:13','dawsonlee1790','这是测试消息'),('13167320611','2015212100','2018-04-16 15:06:54','dawsonlee1790','你好mary'),('13167320611','2015212100','2018-04-16 15:42:24','Mary','zzzz'),('13167320611','2015212100','2018-04-16 15:42:32','Mary','5P8XH'),('13167320611','2015212100','2018-04-16 15:42:59','Mary','你好'),('13167320611','2015212100','2018-04-16 15:44:43','Mary','hao'),('13167320611','2015212100','2018-04-16 15:46:48','Mary','你好'),('13167320611','2015212100','2018-04-16 15:48:03','Mary','5P8XH'),('13167320611','2015212100','2018-04-16 15:48:08','Mary','XNEW'),('13167320611','2015212100','2018-04-16 16:01:26','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:01:50','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:05:26','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:05:29','dawsonlee1790','fa'),('13167320611','2015212100','2018-04-16 16:05:32','dawsonlee1790','fsdfsa'),('13167320611','2015212100','2018-04-16 16:05:38','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:06:21','dawsonlee1790','2'),('13167320611','2015212100','2018-04-16 16:06:30','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:08:28','dawsonlee1790','2'),('13167320611','2015212100','2018-04-16 16:08:30','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:08:33','dawsonlee1790','XNEW'),('13167320611','2015212100','2018-04-16 16:12:16','dawsonlee1790','2'),('13167320611','2015212100','2018-04-16 16:12:50','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:15:32','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:15:35','dawsonlee1790','2'),('13167320611','2015212100','2018-04-16 16:16:14','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:26:02','dawsonlee1790','null'),('13167320611','2015212100','2018-04-16 16:31:02','dawsonlee1790','fafa'),('13167320611','2015212100','2018-04-16 16:31:32','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:43:00','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:44:18','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:44:56','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:45:10','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:46:23','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 16:59:23','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 17:02:58','dawsonlee1790','2'),('13167320611','2015212100','2018-04-16 17:03:02','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 17:05:17','dawsonlee1790','5P8XH'),('13167320611','2015212100','2018-04-16 17:57:48','dawsonlee1790','这是测试消息'),('13167320611','2015212100','2018-04-16 17:58:16','dawsonlee1790','这是测试消息'),('13167320611','2015212100','2018-04-16 17:59:32','dawsonlee1790','你好mary'),('13167320611','2015212100','2018-04-16 18:00:23','dawsonlee1790','Hello'),('13167320611','2015212100','2018-04-16 18:04:17','dawsonlee1790','Nihao'),('13167320611','2015212100','2018-04-16 19:10:55','Mary','5P8XH'),('13167320611','2015212100','2018-04-16 19:11:12','dawsonlee1790','恩'),('13167320611','2015212100','2018-04-16 19:11:21','dawsonlee1790','好不好呀'),('13167320611','2015212100','2018-05-26 03:14:18','dawsonlee1790','你好'),('13167320611','2015212100','2018-05-26 03:14:42','Mary','en '),('13167320611','654321','2018-04-12 19:09:50','test2','nih'),('13167320611','654321','2018-04-12 19:10:29','test2','haohaoah'),('13167320611','654321','2018-04-12 19:10:40','test2','haohaohao'),('15611375811','2015212100','2018-04-14 19:05:59','mary','你好，扎西南杰'),('15911187836','13167320611','2018-04-12 18:08:49','庞敬','李少'),('15911187836','13167320611','2018-04-12 18:10:46','庞敬','李少'),('15911187836','13167320611','2018-04-12 19:09:39','dawsonlee1790','Ok'),('18801261120','13167320611','2018-04-12 23:53:54','不知名人','jjjjjjjj'),('18801261120','13167320611','2018-04-12 23:53:59','不知名人','jijiji'),('18801261120','2015212100','2018-04-14 19:07:18','mary','你好'),('18801261120','2015212100','2018-04-14 19:08:09','不知名人','。。。'),('2015212100','123456','2018-04-12 19:03:22','mary','你好，我是mary'),('2015212100','123456','2018-04-12 19:03:58','Test1','你好，test1,你有什么问题吗'),('2015212100','13167320611','2018-04-12 15:23:35','dawsonlee1790','mary你好，我是dawson'),('2015212100','13167320611','2018-04-12 15:56:03','mary','你好Dawson，我是mary'),('2015212100','13167320611','2018-04-12 17:50:27','dawsonlee1790','你好'),('2015212100','13167320611','2018-04-12 17:50:45','dawsonlee1790','我是dawson,晚安'),('2015212100','13167320611','2018-04-12 17:57:18','dawsonlee1790','我是dawson,newMessage'),('2015212100','13167320611','2018-04-12 18:11:31','mary','李少'),('2015212100','13167320611','2018-04-12 18:11:42','dawsonlee1790','庞敬'),('2015212100','13167320611','2018-04-12 19:08:53','dawsonlee1790','测试消息'),('2015212100','13167320611','2018-04-12 19:29:18','mary','12313213'),('2015212100','13167320611','2018-04-12 19:30:34','mary','166221'),('2015212100','13167320611','2018-04-16 13:32:35','dawsonlee1790','老师您好'),('2015212100','13167320611','2018-04-16 13:46:52','dawsonlee1790','这是测试消息'),('2015212100','13167320611','2018-04-16 14:01:14','dawsonlee1790','这是测试消息'),('2015212100','13167320611','2018-04-16 14:50:13','dawsonlee1790','这是测试消息'),('2015212100','13167320611','2018-04-16 15:06:54','dawsonlee1790','你好mary'),('2015212100','13167320611','2018-04-16 15:42:24','Mary','zzzz'),('2015212100','13167320611','2018-04-16 15:42:32','Mary','5P8XH'),('2015212100','13167320611','2018-04-16 15:42:59','Mary','你好'),('2015212100','13167320611','2018-04-16 15:44:43','Mary','hao'),('2015212100','13167320611','2018-04-16 15:46:48','Mary','你好'),('2015212100','13167320611','2018-04-16 15:48:03','Mary','5P8XH'),('2015212100','13167320611','2018-04-16 15:48:08','Mary','XNEW'),('2015212100','13167320611','2018-04-16 16:01:26','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:01:50','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:05:26','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:05:29','dawsonlee1790','fa'),('2015212100','13167320611','2018-04-16 16:05:32','dawsonlee1790','fsdfsa'),('2015212100','13167320611','2018-04-16 16:05:38','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:06:21','dawsonlee1790','2'),('2015212100','13167320611','2018-04-16 16:06:30','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:08:28','dawsonlee1790','2'),('2015212100','13167320611','2018-04-16 16:08:30','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:08:33','dawsonlee1790','XNEW'),('2015212100','13167320611','2018-04-16 16:12:16','dawsonlee1790','2'),('2015212100','13167320611','2018-04-16 16:12:50','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:15:32','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:15:35','dawsonlee1790','2'),('2015212100','13167320611','2018-04-16 16:16:14','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:26:02','dawsonlee1790','null'),('2015212100','13167320611','2018-04-16 16:31:02','dawsonlee1790','fafa'),('2015212100','13167320611','2018-04-16 16:31:32','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:43:00','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:44:18','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:44:56','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:45:10','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:46:23','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 16:59:23','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 17:02:58','dawsonlee1790','2'),('2015212100','13167320611','2018-04-16 17:03:02','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 17:05:17','dawsonlee1790','5P8XH'),('2015212100','13167320611','2018-04-16 17:57:48','dawsonlee1790','这是测试消息'),('2015212100','13167320611','2018-04-16 17:58:16','dawsonlee1790','这是测试消息'),('2015212100','13167320611','2018-04-16 17:59:32','dawsonlee1790','你好mary'),('2015212100','13167320611','2018-04-16 18:00:23','dawsonlee1790','Hello'),('2015212100','13167320611','2018-04-16 18:04:17','dawsonlee1790','Nihao'),('2015212100','13167320611','2018-04-16 19:10:55','Mary','5P8XH'),('2015212100','13167320611','2018-04-16 19:11:12','dawsonlee1790','恩'),('2015212100','13167320611','2018-04-16 19:11:21','dawsonlee1790','好不好呀'),('2015212100','13167320611','2018-05-26 03:14:18','dawsonlee1790','你好'),('2015212100','13167320611','2018-05-26 03:14:42','Mary','en '),('2015212100','15611375811','2018-04-14 19:05:59','mary','你好，扎西南杰'),('2015212100','18801261120','2018-04-14 19:07:18','mary','你好'),('2015212100','18801261120','2018-04-14 19:08:09','不知名人','。。。'),('654321','13167320611','2018-04-12 19:09:50','test2','nih'),('654321','13167320611','2018-04-12 19:10:29','test2','haohaoah'),('654321','13167320611','2018-04-12 19:10:40','test2','haohaohao');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notice` (
  `course_school_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `course_start_date` date NOT NULL,
  `course_end_date` date NOT NULL,
  `publish_datetime` datetime NOT NULL,
  `title` varchar(450) NOT NULL,
  `body` text NOT NULL,
  `upload_file_url` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`course_school_id`,`course_id`,`course_start_date`,`course_end_date`,`publish_datetime`),
  CONSTRAINT `fk_notice_course` FOREIGN KEY (`course_school_id`, `course_id`, `course_start_date`, `course_end_date`) REFERENCES `course` (`school_id`, `id`, `start_date`, `end_date`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (10013,1,'2018-01-10','2018-07-01','2018-04-16 00:00:00','作业','请上传演示视频','/cloudDisk/2015212100'),(10013,1,'2018-01-10','2018-07-01','2018-04-16 21:41:24','作业二','请上传项目文档','/cloudDisk/2015212100'),(10013,1,'2018-01-10','2018-07-01','2018-04-16 21:42:52','作业三','请上传Demo','/cloudDisk/2015212100'),(10013,1,'2018-01-10','2018-07-01','2018-04-16 21:49:27','作业四','测试文档','/cloudDisk/2015212100'),(10013,1,'2018-01-10','2018-07-01','2018-04-16 21:49:38','作业五','测试文档','/cloudDisk/2015212100'),(10013,1,'2018-01-10','2018-07-01','2018-04-16 21:50:37','作业六','测试文档',NULL);
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `sequence_number` int(11) NOT NULL,
  `chapter_course_school_id` int(11) NOT NULL,
  `chapter_course_id` int(11) NOT NULL,
  `chapter_course_start_date` date NOT NULL,
  `chapter_course_end_date` date NOT NULL,
  `chapter_sequence_number` int(11) NOT NULL,
  `title` varchar(450) DEFAULT NULL,
  `a` varchar(450) DEFAULT NULL,
  `b` varchar(450) DEFAULT NULL,
  `c` varchar(450) DEFAULT NULL,
  `d` varchar(450) DEFAULT NULL,
  `answer` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`sequence_number`,`chapter_course_school_id`,`chapter_course_id`,`chapter_course_start_date`,`chapter_course_end_date`,`chapter_sequence_number`),
  KEY `fk_question_chapter1_idx` (`chapter_course_school_id`,`chapter_course_id`,`chapter_course_start_date`,`chapter_course_end_date`,`chapter_sequence_number`),
  CONSTRAINT `fk_question_chapter` FOREIGN KEY (`chapter_course_school_id`, `chapter_course_id`, `chapter_course_start_date`, `chapter_course_end_date`, `chapter_sequence_number`) REFERENCES `chapter` (`course_school_id`, `course_id`, `course_start_date`, `course_end_date`, `sequence_number`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resume`
--

DROP TABLE IF EXISTS `resume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resume` (
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `user_id` varchar(45) NOT NULL,
  `school_id` int(11) NOT NULL,
  `identity_name` varchar(45) NOT NULL,
  PRIMARY KEY (`start_date`,`end_date`,`user_id`,`school_id`,`identity_name`),
  KEY `fk_user_has_school1_school1_idx` (`school_id`),
  KEY `fk_user_has_school1_user1_idx` (`user_id`),
  KEY `fk_resume_identity1_idx` (`identity_name`),
  CONSTRAINT `fk_identity` FOREIGN KEY (`identity_name`) REFERENCES `identity` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resume`
--

LOCK TABLES `resume` WRITE;
/*!40000 ALTER TABLE `resume` DISABLE KEYS */;
INSERT INTO `resume` VALUES ('2015-09-01','2019-06-01','13167320611',10013,'student'),('2015-09-01','2019-06-01','2015212100',10013,'teacher'),('2015-09-01','2019-06-01','2015212101',10013,'teacher'),('2015-09-01','2019-06-01','2015212102',10013,'teacher');
/*!40000 ALTER TABLE `resume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school`
--

DROP TABLE IF EXISTS `school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `logo` varchar(450) DEFAULT '/media/images/logo_school.png',
  `introduction` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school`
--

LOCK TABLES `school` WRITE;
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
INSERT INTO `school` VALUES (10001,'北京大学','/media/images/logo_school.png','北京大学，简称“北大”，诞生于1898年，初名京师大学堂，是中国近代第一所国立大学，也是最早以“大学”之名创办的学校，其成立标志着中国近代高等教育的开端。北大是中国近代以来唯一以国家最高学府身份创立的学校，最初也是国家最高教育行政机关，行使教育部职能，统管全国教育。北大催生了中国最早的现代学制，开创了中国最早的文科、理科、社科、农科、医科等大学学科，是近代以来中国高等教育的奠基者。1912年5月3日，京师大学堂改称北京大学校，严复为首任校长。1916年，蔡元培出任校长，“循思想自由原则、取兼容并包之义”，把北大办成全国学术和思想中心，使北大成为新文化运动中心、五四运动策源地。1937年抗日战争爆发，北大与清华大学、南开大学南迁长沙，组成国立长沙临时大学。不久迁往昆明，改称国立西南联合大学。1946年10月在北平复学。1952年院系调整，校园从内城沙滩红楼迁至西北郊燕园。1912年5月3日，京师大学堂改称北京大学校，严复为首任校长。1916年，蔡元培出任校长，“循思想自由原则、取兼容并包之义”，把北大办成全国学术和思想中心，使北大成为新文化运动中心、五四运动策源地。1937年抗日战争爆发，北大与清华大学、南开大学南迁长沙，组成国立长沙临时大学。不久迁往昆明，改称国立西南联合大学。1946年10月在北平复学。1952年院系调整，校园从内城沙滩红楼迁至西北郊燕园。1912年5月3日，京师大学堂改称北京大学校，严复为首任校长。1916年，蔡元培出任校长，“循思想自由原则、取兼容并包之义”，把北大办成全国学术和思想中心，使北大成为新文化运动中心、五四运动策源地。1937年抗日战争爆发，北大与清华大学、南开大学南迁长沙，组成国立长沙临时大学。不久迁往昆明，改称国立西南联合大学。1946年10月在北平复学。1952年院系调整，校园从内城沙滩红楼迁至西北郊燕园。000'),(10002,'中国人民大学','/media/images/logo_school.png','中国人民大学（Renmin University of China），简称“人大”，由教育部直属，教育部与北京市共建，中央直管副部级建制，位列“双一流“ 、“211工程”、“985工程”，入选“111计划”、“2011计划”、“卓越法律人才教育培养计划”、“卓越农林人才教育培养计划”、“海外高层次人才引进计划”、“中国政府奖学金来华留学生接收院校”，为世界大学联盟成员、亚太国际教育协会创始成员，是一所以人文社会科学为主的综合性研究型全国重点大学。'),(10003,'清华大学','/media/images/logo_school.png','清华大学（Tsinghua University），简称“清华”，由中华人民共和国教育部直属，中央直管副部级建制，位列“211工程”、“985工程”、“世界一流大学和一流学科”，入选“基础学科拔尖学生培养试验计划”、“高等学校创新能力提升计划”、“高等学校学科创新引智计划”，为九校联盟、中国大学校长联谊会、东亚研究型大学协会、亚洲大学联盟、环太平洋大学联盟、清华—剑桥—MIT低碳大学联盟成员，被誉为“红色工程师的摇篮”。'),(10013,'北京邮电大学','/media/images/logo_school.png','北京邮电大学（Beijing University of Posts and Telecommunications），简称北邮，是中华人民共和国教育部直属，工业和信息化部共建的一所以信息科技为特色，工学门类为主体，管理学、文学、理学等多个学科门类协调发展的全国重点大学，是北京高科大学联盟成员高校、国家首批“双一流”世界一流学科建设高校。系国家“211工程”、“985工程优势学科创新平台”项目重点建设，列入首批“卓越工程师教育培养计划”、“111计划”。被誉为“中国信息科技人才的摇篮”。'),(10027,'北京师范大学','/media/images/logo_school.png','北京师范大学（Beijing Normal University）简称“北师大”，由中华人民共和国教育部直属，中央直管副部级建制，位列“211工程”、“985工程”，入选国家“双一流”、“珠峰计划”、“2011计划”、“111计划”、“卓越法律人才教育培养计划”，设有研究生院，是一所以教师教育、教育科学和文理基础学科为主要特色的综合性全国重点大学。');
/*!40000 ALTER TABLE `school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `user_id` varchar(45) NOT NULL,
  `course_school_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `course_start_date` date NOT NULL,
  `course_end_date` date NOT NULL,
  `mark` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`course_school_id`,`course_id`,`course_start_date`,`course_end_date`),
  KEY `fk_student_course1_idx` (`course_school_id`,`course_id`,`course_start_date`,`course_end_date`),
  CONSTRAINT `fk_student_course` FOREIGN KEY (`course_school_id`, `course_id`, `course_start_date`, `course_end_date`) REFERENCES `course` (`school_id`, `id`, `start_date`, `end_date`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_student_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('123456',10013,1,'2018-01-10','2018-07-01',NULL),('123456',10013,2,'2018-01-10','2018-07-01',NULL),('123456',10013,3,'2018-01-10','2018-07-01',NULL),('13167320611',10013,1,'2018-01-10','2018-07-01',100),('13167320611',10013,2,'2018-01-10','2018-07-01',NULL),('13167320611',10013,3,'2018-01-10','2018-07-01',NULL),('13167320611',10013,4,'2018-01-10','2018-07-01',NULL),('13167320611',10013,5,'2018-01-10','2018-07-01',NULL),('15600931170',10013,1,'2018-01-10','2018-07-01',NULL),('15600931170',10013,4,'2018-01-10','2018-07-01',NULL),('15611375811',10013,1,'2018-01-10','2018-07-01',NULL),('15611375811',10013,2,'2018-01-10','2018-07-01',NULL),('15911187836',10013,1,'2018-01-10','2018-07-01',NULL),('18801261120',10013,1,'2018-01-10','2018-07-01',NULL),('2015212100',10013,2,'2018-01-10','2018-07-01',NULL),('654321',10013,1,'2018-01-10','2018-07-01',NULL);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `user_id` varchar(45) NOT NULL,
  `course_school_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `course_start_date` date NOT NULL,
  `course_end_date` date NOT NULL,
  `mark` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`course_school_id`,`course_id`,`course_start_date`,`course_end_date`),
  KEY `fk_teacher_course1_idx` (`course_school_id`,`course_id`,`course_start_date`,`course_end_date`),
  CONSTRAINT `fk_teacher_course` FOREIGN KEY (`course_school_id`, `course_id`, `course_start_date`, `course_end_date`) REFERENCES `course` (`school_id`, `id`, `start_date`, `end_date`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_teacher_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES ('2015212100',10013,1,'2018-01-10','2018-07-01',NULL),('2015212100',10013,3,'2018-01-10','2018-07-01',NULL),('2015212101',10013,2,'2018-01-10','2018-07-01',NULL),('2015212101',10013,4,'2018-01-10','2018-07-01',NULL),('2015212102',10013,5,'2018-01-10','2018-07-01',NULL);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `logo` varchar(450) NOT NULL DEFAULT '/media/images/logo_user.jpg',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1111','123456','dawsonlee1790','/media/images/logo_user.jpg'),('123456','12345','Test1','/media/images/logo_user.jpg'),('13167320611','123456','dawsonlee1790','/media/images/logo_user.jpg'),('15577582651','12345','李东山','/media/images/logo_user.jpg'),('15600931170','12345','肖圣康','/media/images/logo_user.jpg'),('15611375811','123456789','扎西南杰','/media/images/logo_user.jpg'),('15911187836','123456','庞敬','/media/images/logo_user.jpg'),('18801261120','123','不知名人','/media/images/logo_user.jpg'),('2015212100','123456','Mary','/media/images/logo_user.jpg'),('2015212101','123456','jack','/media/images/logo_user.jpg'),('2015212102','123456','chen','/media/images/logo_user.jpg'),('2222','123456','dawsonlee1790','/media/images/logo_user.jpg'),('4444','123456','dawsonlee1790','/media/images/logo_user.jpg'),('654321','12345','test2','/media/images/logo_user.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vedio`
--

DROP TABLE IF EXISTS `vedio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vedio` (
  `sequence_number` int(11) NOT NULL,
  `chapter_course_school_id` int(11) NOT NULL,
  `chapter_course_id` int(11) NOT NULL,
  `chapter_course_start_date` date NOT NULL,
  `chapter_course_end_date` date NOT NULL,
  `chapter_sequence_number` int(11) NOT NULL,
  `name` varchar(450) DEFAULT NULL,
  `url` mediumtext,
  PRIMARY KEY (`sequence_number`,`chapter_course_school_id`,`chapter_course_id`,`chapter_course_start_date`,`chapter_course_end_date`,`chapter_sequence_number`),
  KEY `fk_vedio_chapter1_idx` (`chapter_course_school_id`,`chapter_course_id`,`chapter_course_start_date`,`chapter_course_end_date`,`chapter_sequence_number`),
  CONSTRAINT `fk_vedio_chapter` FOREIGN KEY (`chapter_course_school_id`, `chapter_course_id`, `chapter_course_start_date`, `chapter_course_end_date`, `chapter_sequence_number`) REFERENCES `chapter` (`course_school_id`, `course_id`, `course_start_date`, `course_end_date`, `sequence_number`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vedio`
--

LOCK TABLES `vedio` WRITE;
/*!40000 ALTER TABLE `vedio` DISABLE KEYS */;
INSERT INTO `vedio` VALUES (1,10013,1,'2018-01-10','2018-07-01',1,'什么是流媒体','/media/vedios/movie1.mp4'),(1,10013,1,'2018-01-10','2018-07-01',2,'现在常用的里媒体技术','/media/vedios/movie2.mp4'),(1,10013,1,'2018-01-10','2018-07-01',3,'rtmp技术介绍','/media/vedios/movie3.mp4'),(1,10013,1,'2018-01-10','2018-07-01',4,'rtmp技术深入','/media/vedios/movie4.mp4'),(1,10013,1,'2018-01-10','2018-07-01',5,'hls技术介绍','/media/vedios/movie5.mp4'),(1,10013,1,'2018-01-10','2018-07-01',6,'流媒体服务器搭建','/media/vedios/movie6.mp4'),(1,10013,5,'2018-01-10','2018-07-01',10,'演示视频360p.mp4','/media/vedios/L5LeKdqAvccn0TLW.mp4'),(2,10013,5,'2018-01-10','2018-07-01',10,'演示视频360p.mp4','/media/vedios/K349jx2V5Qe8K4lB.mp4'),(3,10013,5,'2018-01-10','2018-07-01',10,'演示视频360p.mp4','/media/vedios/83pYILKaFHCd1o6i.mp4'),(4,10013,5,'2018-01-10','2018-07-01',10,'演示视频360p.mp4','/media/vedios/U25aHULESaB2n3y5.mp4'),(5,10013,5,'2018-01-10','2018-07-01',10,'演示视频360p.mp4','/media/vedios/8RWmF286V2vyP9tZ.mp4');
/*!40000 ALTER TABLE `vedio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'yunzhi'
--

--
-- Dumping routines for database 'yunzhi'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-08 15:26:11
