--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brands` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `name` varchar(50) NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES
    (1,'Ferrari');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car_classes`
--

DROP TABLE IF EXISTS `car_classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_classes` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `name` varchar(20) NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_classes`
--

LOCK TABLES `car_classes` WRITE;
/*!40000 ALTER TABLE `car_classes` DISABLE KEYS */;
INSERT INTO `car_classes` VALUES
                              (1,'Low'),
                              (2,'Mid'),
                              (3,'High'),
                              (4,'Exotic');
/*!40000 ALTER TABLE `car_classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cars` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `license_plate` varchar(10) NOT NULL,
                        `vin` varchar(17) NOT NULL,
                        `brand_id` int(11) DEFAULT NULL,
                        `model_id` int(11) DEFAULT NULL,
                        `year` int(4) NOT NULL,
                        `owner_id` int(11) NOT NULL,
                        PRIMARY KEY (`id`),
                        KEY `cars_users_id_fk` (`owner_id`),
                        KEY `cars_brands_id_fk` (`brand_id`),
                        KEY `cars_models_id_fk` (`model_id`),
                        CONSTRAINT `cars_brands_id_fk` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
                        CONSTRAINT `cars_models_id_fk` FOREIGN KEY (`model_id`) REFERENCES `models` (`id`),
                        CONSTRAINT `cars_users_id_fk` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES
                       (1,'CB 4881 AM','ZFF79ALA0K0234567',1,1,2019,2),
                       (2,'CB 8202 BB','ZFF92LLA1L0234568',1,1,2020,5),
                       (3,'PB 8123 CC','ZFF87LAM2M0234569',1,1,2021,4),
                       (4,'CA 2450 KH','ZFF89LLA8J0234570',1,1,2018,2),
                       (5,'PB 9090 EX','ZFF94LAM0N0234571',1,1,2022,5);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `models`
--

DROP TABLE IF EXISTS `models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `models` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `name` varchar(50) NOT NULL,
                          `brand_id` int(11) NOT NULL,
                          `class_id` int(11) NOT NULL,
                          PRIMARY KEY (`id`),
                          KEY `models_brands_id_fk` (`brand_id`),
                          KEY `models_car_classes_id_fk` (`class_id`),
                          CONSTRAINT `models_brands_id_fk` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
                          CONSTRAINT `models_car_classes_id_fk` FOREIGN KEY (`class_id`) REFERENCES `car_classes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `models`
--

LOCK TABLES `models` WRITE;
/*!40000 ALTER TABLE `models` DISABLE KEYS */;
INSERT INTO `models` VALUES
                         (1,'488 GTB',1,4),
                         (2,'F8 Tributo',1,4),
                         (3,'812 Superfast',1,4),
                         (4,'Ferrari Portofino',1,4),
                         (5,'SF90 Stradale',1,4);
/*!40000 ALTER TABLE `models` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parts`
--

DROP TABLE IF EXISTS `parts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parts` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `name` varchar(50) NOT NULL,
                         `price` double NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parts`
--

LOCK TABLES `parts` WRITE;
/*!40000 ALTER TABLE `parts` DISABLE KEYS */;
INSERT INTO `parts` VALUES
                        (1,'Brake Pads',120),
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
                        (20,'Oil Change',300);
/*!40000 ALTER TABLE `parts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `services` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `car_id` int(11) NOT NULL,
                            `user_id` int(11) NOT NULL,
                            `total` int(11) DEFAULT NULL,
                            `date` date NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `services_cars_id_fk` (`car_id`),
                            KEY `services_users_id_fk` (`user_id`),
                            CONSTRAINT `services_cars_id_fk` FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`) ON DELETE CASCADE,
                            CONSTRAINT `services_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services_parts`
--

DROP TABLE IF EXISTS `services_parts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `services_parts` (
                                  `service_id` int(11) NOT NULL,
                                  `part_id` int(11) NOT NULL,
                                  KEY `services_parts_parts_id_fk` (`part_id`),
                                  KEY `services_parts_services_id_fk` (`service_id`),
                                  CONSTRAINT `services_parts_parts_id_fk` FOREIGN KEY (`part_id`) REFERENCES `parts` (`id`),
                                  CONSTRAINT `services_parts_services_id_fk` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services_parts`
--

LOCK TABLES `services_parts` WRITE;
/*!40000 ALTER TABLE `services_parts` DISABLE KEYS */;
/*!40000 ALTER TABLE `services_parts` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
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
                        (5,'NDimitrov','NikoPass!567','nikolay.dimitrov@example.com','+359895678901',0),
                        (6,'PAsenov','m^zl400S','smart_garage_dp_user@abv.bg','+359881242513',0);
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