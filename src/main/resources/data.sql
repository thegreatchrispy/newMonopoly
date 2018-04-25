-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: new_monopoly
-- ------------------------------------------------------
-- Server version	5.7.11-log

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `account_role`;
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `active` int(11) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role` (
  `account_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `role` VALUES (1,'ADMIN');

--
-- Table structure for table `boards`
--

DROP TABLE IF EXISTS `boards`;
CREATE TABLE `boards` (
	`id` NOT NULL AUTO_INCREMENT,
	`players` JSON NOT NULL,
	`current_turn` INT NOT NULL,
	`total_player` INT NOT NULL,
	`turn_over` BOOLEAN NOT NULL,
	`spaces` JSON NOT NULL,
	-- `chance` JSON NOT NULL,
	-- `community` JSON NOT NULL,
	`player_index` INT NOT NULL,
	`houses_available` INT NOT NULL,
	`hotels_available` INT NOT NULL,
	`die_value` INT NOT NULL,
	`player_choice_char` CHAR(1),
	`player_choice_int` INT,
	`trade_choice_char` CHAR(1)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;