--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cars` (
                        `id` int(11) NOT NULL,
                        `license_plate` varchar(10) NOT NULL,
                        `vin` varchar(17) NOT NULL,
                        `brand` varchar(50) NOT NULL,
                        `model` varchar(50) NOT NULL,
                        `year` int(4) NOT NULL,
                        `owner` int(11) NOT NULL,
                        PRIMARY KEY (`id`),
                        KEY `cars_users_id_fk` (`owner`),
                        CONSTRAINT `cars_users_id_fk` FOREIGN KEY (`owner`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES
                       (1,'CB 4881 AM','ZFF79ALA0K0234567','Ferrari','488 GTB',2019,2),
                       (2,'CB 8202 BB','ZFF92LLA1L0234568','Ferrari','F8 Tributo',2020,5),
                       (3,'PB 8123 CC','ZFF87LAM2M0234569','Ferrari','812 Superfast',2021,4),
                       (4,'CA 2450 KH','ZFF89LLA8J0234570','Ferrari','Portofino',2018,2),
                       (5,'PB 9090 EX','ZFF94LAM0N0234571','Ferrari','SF90 Stradale',2022,5);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parts`
--

DROP TABLE IF EXISTS `parts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parts` (
                         `id` int(11) NOT NULL,
                         `name` varchar(50) NOT NULL,
                         `price` double NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parts`
--

LOCK TABLES `parts` WRITE;
/*!40000 ALTER TABLE `parts` DISABLE KEYS */;
INSERT INTO `parts` VALUES
                        (1,'Brake Pads',120),
                        (2,'Oil Filter',20),
                        (3,'Air Filter',30),
                        (4,'Spark Plugs (Set of 4)',80),
                        (5,'Battery',250),
                        (6,'Alternator',400),
                        (7,'Radiator',300),
                        (8,'Headlight (Single Unit)',150),
                        (9,'Fuel Pump',350),
                        (10,'Timing Belt',200),
                        (11,'Clutch Kit',800),
                        (12,'Automatic Transmission',3000),
                        (13,'Shock Absorbers (Set of 2)',400),
                        (14,'Brake Disc (Set of two)',400),
                        (15,'Starter',450),
                        (16,'Engine Control Unit (ECU)',1200),
                        (17,'Turbocharger',1500),
                        (18,'Water Pump',300),
                        (19,'Transmission Fluid',300),
                        (20,'Oil',200),
                        (21,'Fuel Filter',40);
/*!40000 ALTER TABLE `parts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `username` varchar(20) NOT NULL,
                         `password` varchar(20) NOT NULL,
                         `email` varchar(50) NOT NULL,
                         `phone_number` varchar(13) NOT NULL,
                         `is_employee` tinyint(1) NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES
                        (1,'IPopov','Ivan123!@#','ivan.popov@example.com','+359881234567',1),
                        (2,'MIvanova','MariaPass!98','maria.ivanova@example.com','+359892345678',0),
                        (3,'GPetrov','GeoPetro@2024','georgi.petrov@example.com','+359873456789',1),
                        (4,'VStoyanova','Vessy@1234','vesela.stoyanova@example.com','+359884567890',0),
                        (5,'NDimitrov','NikoPass!567','nikolay.dimitrov@example.com','+359895678901',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;
