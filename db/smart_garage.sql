-- Table structure for table `brands`
DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brands` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `name` varchar(50) NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dumping data for table `brands`
LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES
                         (2,'Mercedes-Benz'),
                         (4,'Ineos Grenadier');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

-- Table structure for table `car_classes`
DROP TABLE IF EXISTS `car_classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_classes` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `name` varchar(20) NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dumping data for table `car_classes`
LOCK TABLES `car_classes` WRITE;
/*!40000 ALTER TABLE `car_classes` DISABLE KEYS */;
INSERT INTO `car_classes` VALUES
                              (1,'Low'),
                              (2,'Mid'),
                              (3,'High'),
                              (4,'Exotic');
/*!40000 ALTER TABLE `car_classes` ENABLE KEYS */;
UNLOCK TABLES;

-- Table structure for table `models`
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dumping data for table `models`
LOCK TABLES `models` WRITE;
/*!40000 ALTER TABLE `models` DISABLE KEYS */;
INSERT INTO `models` VALUES
                         (1,'A Class',2,1),
                         (2,'E Class',2,2),
                         (3,'S Class',2,3),
                         (4,'AMG GT',2,4),
                         (5,'Arcane Works',4,2),
                         (6,'G Class',2,3),
                         (7,'Quartermaster',4,3),
                         (8,'Utility Wagon',4,2);
/*!40000 ALTER TABLE `models` ENABLE KEYS */;
UNLOCK TABLES;

-- Table structure for table `users`
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dumping data for table `users`
LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES
                        (1,'DKomitski','Dimitar123!@#','dimitar_smart_garage@abv.bg','0881234567',1),
                        (2,'PetkoAsenov','Petko321!98','petko.asenov@gmail.com','0892345678',0),
                        (3,'GPetrov','GeoPetro@2024','georgi.petrov@gmail.com','0873456789',1),
                        (4,'VStoyanova','Vessy@1234','vesela.stoyanova@gmail.com','0884567890',0),
                        (5,'NDimitrov','NikoPass!567','nikolay.dimitrov@gmail.com','0895678901',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

-- Table structure for table `cars`
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dumping data for table `cars`
LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES
                       (1,'CB 4881 AM','ZFF79ALA0K0234567',2,1,2019,2),
                       (2,'CB 8202 BB','ZFF92LLA1L0234568',2,2,2020,5),
                       (3,'PB 8123 CC','ZFF87LAM2M0234569',2,3,2021,4),
                       (4,'CA 2450 KH','ZFF89LLA8J0234570',2,4,2018,2),
                       (5,'PB 9090 EX','ZFF94LAM0N0234571',4,5,2022,5);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

-- Table structure for table `parts`
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

-- Dumping data for table `parts`
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

-- Table structure for table `services`
DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `services` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `car_id` int(11) NOT NULL,
                            `user_id` int(11) NOT NULL,
                            `total` int(11) DEFAULT NULL,
                            `date` date NOT NULL,
                            `car_license_plate` varchar(10) NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `services_users_id_fk` (`user_id`),
                            KEY `services_cars_id_fk` (`car_id`),
                            CONSTRAINT `services_cars_id_fk` FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`) ON DELETE CASCADE,
                            CONSTRAINT `services_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dumping data for table `services`
LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES
                           (46,1,2,1300,'2024-09-15','CB 4881 AM'),
                           (47,1,2,4100,'2024-09-15','CB 4881 AM'),
                           (49,1,2,3300,'2024-09-16','CB 4881 AM'),
                           (50,1,2,1100,'2024-09-16','CB 4881 AM'),
                           (51,1,2,2170,'2024-09-16','CB 4881 AM');
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

-- Table structure for table `services_parts`
DROP TABLE IF EXISTS `services_parts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `services_parts` (
                                  `service_id` int(11) NOT NULL,
                                  `part_id` int(11) NOT NULL,
                                  KEY `services_parts_parts_id_fk` (`part_id`),
                                  KEY `services_parts_services_id_fk` (`service_id`),
                                  CONSTRAINT `services_parts_parts_id_fk` FOREIGN KEY (`part_id`) REFERENCES `parts` (`id`),
                                  CONSTRAINT `services_parts_services_id_fk` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dumping data for table `services_parts`
LOCK TABLES `services_parts` WRITE;
/*!40000 ALTER TABLE `services_parts` DISABLE KEYS */;
INSERT INTO `services_parts` VALUES
                                 (46,1),
                                 (46,4),
                                 (46,5),
                                 (46,6),
                                 (46,7),
                                 (46,8),
                                 (47,8),
                                 (47,9),
                                 (47,10),
                                 (47,12),
                                 (47,13),
                                 (49,1),
                                 (49,4),
                                 (49,5),
                                 (49,6),
                                 (49,13),
                                 (49,14),
                                 (49,15),
                                 (49,16),
                                 (50,5),
                                 (50,6),
                                 (50,7),
                                 (50,8),
                                 (51,1),
                                 (51,5),
                                 (51,6),
                                 (51,9),
                                 (51,10),
                                 (51,13),
                                 (51,15);
/*!40000 ALTER TABLE `services_parts` ENABLE KEYS */;
UNLOCK TABLES;