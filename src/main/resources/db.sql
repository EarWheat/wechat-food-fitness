CREATE TABLE `Counters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL DEFAULT '1',
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `food_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `openid` varchar(64) NOT NULL DEFAULT '',
  `image` mediumtext,
  `name` varchar(255) DEFAULT '',
  `calories` double DEFAULT 0,
  `protein` double DEFAULT 0,
  `carbs` double DEFAULT 0,
  `fat` double DEFAULT 0,
  `serving` varchar(64) DEFAULT '',
  `meal_type` varchar(16) DEFAULT '',
  `record_time` bigint(20) NOT NULL DEFAULT 0,
  `source` varchar(16) DEFAULT '',
  `created_at` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `openid` varchar(64) NOT NULL DEFAULT '',
  `nickname` varchar(64) DEFAULT '',
  `gender` varchar(8) DEFAULT 'male',
  `height` double DEFAULT 0,
  `weight` double DEFAULT 0,
  `age` int(11) DEFAULT 0,
  `goal` varchar(16) DEFAULT 'maintain',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `weight_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `openid` varchar(64) NOT NULL DEFAULT '',
  `weight` double NOT NULL DEFAULT 0,
  `record_time` bigint(20) NOT NULL DEFAULT 0,
  `created_at` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
