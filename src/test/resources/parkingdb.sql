CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_vehicle` varchar(50) NOT NULL,
  `licence_number` varchar(10) NOT NULL,
  `in_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `out_date` timestamp NULL DEFAULT NULL,
  `cylinder_capacity` int(11) NOT NULL DEFAULT '0',
  `rate` float DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1