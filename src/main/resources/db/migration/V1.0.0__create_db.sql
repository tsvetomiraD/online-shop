CREATE DATABASE IF NOT EXISTS `online_shop`;
USE `online_shop`;

CREATE TABLE IF NOT EXISTS `user` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      `deleted` tinyint(1) NOT NULL DEFAULT '0',
    `name` varchar(255) NOT NULL,
    `email` varchar(255) NOT NULL,
    `password_hash` varchar(255) NOT NULL,
    `status` varchar(255) NOT NULL DEFAULT 'ACTIVE',
    `registration_status` varchar(255) NOT NULL DEFAULT 'UNFILLED',
    `profile_picture` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`)
    ) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb3;
