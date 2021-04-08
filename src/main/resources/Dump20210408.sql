-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: strichka
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
INSERT INTO `actor` VALUES (4,'Bradli','Cooper','https://cdn.britannica.com/57/199057-050-CCE5410A/Bradley-Cooper-2008.jpg'),(5,'Margot','Robbie','https://i.pinimg.com/originals/82/29/4d/82294d86ba859923b434bb4a78e7a0f0.jpg'),(6,'Morgan','Freeman','https://upload.wikimedia.org/wikipedia/commons/e/e4/Morgan_Freeman_Deauville_2018.jpg'),(9,'Tom','Hanks','https://m.media-amazon.com/images/M/MV5BMTQ2MjMwNDA3Nl5BMl5BanBnXkFtZTcwMTA2NDY3NQ@@._V1_.jpg'),(10,'Robin','Wright','https://upload.wikimedia.org/wikipedia/commons/d/d9/Robin_Wright_Cannes_2017_%28cropped%29.jpg'),(11,'Gary','Sinise','https://upload.wikimedia.org/wikipedia/commons/9/90/Gary_Sinise_2011_%28cropped%29.jpg'),(12,'Henry','Fonda','https://upload.wikimedia.org/wikipedia/commons/0/0c/Henry_Fonda_in_Warlock.jpg'),(13,'Lee','Cobb','https://upload.wikimedia.org/wikipedia/commons/b/ba/Lee_J._Cobb_1960s.JPG'),(14,'Brad','Pitt','https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Brad_Pitt_2019_by_Glenn_Francis.jpg/1200px-Brad_Pitt_2019_by_Glenn_Francis.jpg'),(15,'Leonardo','DiCaprio','https://upload.wikimedia.org/wikipedia/commons/4/46/Leonardo_Dicaprio_Cannes_2019.jpg'),(16,'Samuel','Jackson','https://upload.wikimedia.org/wikipedia/commons/a/a9/Samuel_L._Jackson_2019_by_Glenn_Francis.jpg'),(17,'Michael','Madsen','https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Michael_Madsen_by_Gage_Skidmore.jpg/1200px-Michael_Madsen_by_Gage_Skidmore.jpg');
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `director`
--

DROP TABLE IF EXISTS `director`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `director` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `director`
--

LOCK TABLES `director` WRITE;
/*!40000 ALTER TABLE `director` DISABLE KEYS */;
INSERT INTO `director` VALUES (7,'Robert','Zemeckis','https://static.hollywoodreporter.com/sites/default/files/2012/11/zemeckis_a_0.jpg'),(8,'Sidney','Lumet','https://cdn.britannica.com/93/208793-050-3E4EC101/Sidney-Lumet-Academy-Award-lifetime-achievement-2005.jpg'),(9,'Quentin','Tarantino','https://i.pinimg.com/originals/e1/d1/cf/e1d1cfa576a21c9ebe39fcd26111c75d.jpg'),(10,'Sergio','Leone','https://upload.wikimedia.org/wikipedia/commons/c/c1/Sergio_Leone_1975.jpg');
/*!40000 ALTER TABLE `director` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `genre_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1,'ACTION'),(2,'ADVENTURE'),(3,'ANIMATION'),(4,'BIOGRAPHY'),(5,'COMEDY'),(6,'CRIME'),(7,'DOCUMENTARY'),(8,'DRAMA'),(9,'FAMILY'),(10,'FANTASY'),(11,'HISTORY'),(12,'HORROR'),(13,'MUSIC'),(14,'MUSICAL'),(15,'MYSTERY'),(16,'ROMANCE'),(17,'SPORT'),(18,'SUPERHERO'),(19,'THRILLER'),(20,'WAR'),(21,'WESTERN');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `tag_line` varchar(255) DEFAULT NULL,
  `story_line` text,
  `release_date` date NOT NULL,
  `country` varchar(255) NOT NULL,
  `director_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_movie_director` (`director_id`),
  CONSTRAINT `FK_movie_director` FOREIGN KEY (`director_id`) REFERENCES `director` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (29,'Forrest Gump','The world will never be the same once you\'ve seen it through the eyes of Forrest Gump.','The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.','1994-06-06','USA',7),(31,'12 Angry Men','...it explodes like twelve sticks of dynamite!','A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.','1957-11-02','USA',8),(32,'Once Upon A Time In Hollywood','The 9th Film from Quentin Tarantino.','Quentin Tarantino\'s Once Upon a Time... in Hollywood visits 1969 Los Angeles, where everything is changing, as TV star Rick Dalton (Leonardo DiCaprio) and his longtime stunt double Cliff Booth (Brad Pitt) make their way around an industry they hardly recognize anymore. The ninth film from the writer-director features a large ensemble cast and multiple storylines in a tribute to the final moments of Hollywood\'s golden age.','2019-10-08','USA',9),(33,'The Hateful Eight','The bounty hunter. The Hangman. The Confederate. The Sheriff. The Mexican. The little man. The cow puncher. The prisoner.','Some time after the Civil War, a stagecoach hurtles through the wintry Wyoming landscape. Bounty hunter John Ruth and his fugitive captive Daisy Domergue race towards the town of Red Rock, where Ruth will bring Daisy to justice. Along the road, they encounter Major Marquis Warren (an infamous bounty hunter) and Chris Mannix (a man who claims to be Red Rock\'s new sheriff). Lost in a blizzard, the bunch seeks refuge at Minnie\'s Haberdashery. When they arrive they are greeted by unfamiliar faces: Bob, who claims to be taking care of the place while Minnie is gone; Oswaldo Mobray, the hangman of Red Rock; Joe Gage, a cow puncher; and confederate general Sanford Smithers. As the storm overtakes the mountainside, the eight travelers come to learn that they might not make it to Red Rock after all...','2016-01-14','USA',9),(35,'Il buono, il brutto, il cattivo','For Three Men The Civil War Wasn\'t Hell. It Was Practice!','Blondie (The Good) (Clint Eastwood) is a professional gunslinger who is out trying to earn a few dollars. Angel Eyes (The Bad) (Lee Van Cleef) is a hitman who always commits to a task and sees it through, as long as he is paid to do so. And Tuco (The Ugly) (Eli Wallach) is a wanted outlaw trying to take care of his own hide. Tuco and Blondie share a partnership together making money off of Tuco\'s bounty, but when Blondie unties the partnership, Tuco tries to hunt down Blondie. When Blondie and Tuco come across a horse carriage loaded with dead bodies, they soon learn from the only survivor, Bill Carson (Antonio Casale), that he and a few other men have buried a stash of gold in a cemetery. Unfortunately, Carson dies and Tuco only finds out the name of the cemetery, while Blondie finds out the name on the grave. Now the two must keep each other alive in order to find the gold. Angel Eyes (who had been looking for Bill Carson) discovers that Tuco and Blondie met with Carson and knows ...','1966-12-23','Italy',10);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_actor`
--

DROP TABLE IF EXISTS `movie_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_actor` (
  `movie_id` bigint NOT NULL,
  `actor_id` bigint NOT NULL,
  PRIMARY KEY (`movie_id`,`actor_id`),
  KEY `FK_movie_actor_actor` (`actor_id`),
  CONSTRAINT `FK_movie_actor_actor` FOREIGN KEY (`actor_id`) REFERENCES `actor` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `FK_movie_actor_movie` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_actor`
--

LOCK TABLES `movie_actor` WRITE;
/*!40000 ALTER TABLE `movie_actor` DISABLE KEYS */;
INSERT INTO `movie_actor` VALUES (32,5),(29,9),(29,10),(29,11),(31,12),(31,13),(32,14),(32,15),(33,16),(33,17);
/*!40000 ALTER TABLE `movie_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_genre`
--

DROP TABLE IF EXISTS `movie_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_genre` (
  `movie_id` bigint NOT NULL,
  `genre_id` bigint NOT NULL,
  PRIMARY KEY (`movie_id`,`genre_id`),
  KEY `FK_movie_genre_genre` (`genre_id`),
  CONSTRAINT `FK_movie_genre_genre` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `FK_movie_genre_movie` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_genre`
--

LOCK TABLES `movie_genre` WRITE;
/*!40000 ALTER TABLE `movie_genre` DISABLE KEYS */;
INSERT INTO `movie_genre` VALUES (29,8),(31,8),(35,21);
/*!40000 ALTER TABLE `movie_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_type_UNIQUE` (`role_type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'USER');
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
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_user_username` (`username`),
  UNIQUE KEY `UQ_user_email` (`email`),
  KEY `FK_user_role` (`role_id`),
  CONSTRAINT `FK_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin@gmail.com','','','$2a$10$vofFZ/GbOWYPfJBvkoDQM.1agbd1XA1DOLb9Rufd3mflzcI.pZ0M2',1),(2,'user','user@test.com','','','$2a$10$BWIx9TEJ31S2VJNzynROy.fe1B2KtOL7wu7XXLBh4.2xBKv1mj8ta',2);
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

-- Dump completed on 2021-04-08 10:43:45
