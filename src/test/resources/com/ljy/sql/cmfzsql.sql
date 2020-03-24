/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : cmfz

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-12-27 16:06:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
/**后台管理员*/
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', 'admin');

-- ----------------------------
-- Table structure for album
-- ----------------------------
/**专辑表*/
DROP TABLE IF EXISTS `album`;
CREATE TABLE `album` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `score` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `broadcast` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of album
-- ----------------------------

-- ----------------------------
-- Table structure for article
-- ----------------------------
//文章
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `publish_date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `guru_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------

-- ----------------------------
-- Table structure for banner
-- ----------------------------
/**轮播图*/
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of banner
-- ----------------------------
INSERT INTO `banner` VALUES ('1', '最终幻想', '/img/377e.jpg', '111', '2000-01-01', 'FF14', '1');
INSERT INTO `banner` VALUES ('2ec919cf-13f0-4b92-a129-65255d0f156f', '诗经qq', '/img/a05.jpeg', 'qqqq', '2019-11-25', '蒹葭苍苍,白露为霜', '1');
INSERT INTO `banner` VALUES ('62b68ef4-7c34-493d-93d5-2cde73403eed', 'wqeqwe', '/upload/img/157743271576225.jpg', 'qweqwe', '2019-09-09', 'wwwww', '1');
INSERT INTO `banner` VALUES ('82201889-7e96-406a-9716-f5d985bb1b62', 'mmm', '/img/timg.jpg', '', '2019-11-25', '举头望明月', '1');
INSERT INTO `banner` VALUES ('da59cd85-384c-48f7-b187-3d827e2ea05e', '楚辞', '/img/552ba1.jpg', null, '2019-11-25', '诗风', '1');

-- ----------------------------
-- Table structure for chapter
-- ----------------------------
/**章节表*/
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `size` double DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `album_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 select c.id, c.title, c.url, c.size, c.time, c.create_time, c.album_id
  from chapter c left join album a on c.album_id = a.id limit 0, 2;





-- ----------------------------
-- Records of chapter
-- ----------------------------

-- ----------------------------
-- Table structure for counter
-- ----------------------------
//计数器
DROP TABLE IF EXISTS `counter`;
CREATE TABLE `counter` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `course_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of counter
-- ----------------------------

-- ----------------------------
-- Table structure for course
-- ----------------------------
//功课
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------

-- ----------------------------
-- Table structure for guru
-- ----------------------------
//上师
DROP TABLE IF EXISTS `guru`;
CREATE TABLE `guru` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of guru
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
//用户
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `sign` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `rigest_date` date DEFAULT NULL,
  `last_login` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

select * from course c left join user u  on c.user_id= u.id where u.id='1'





select count(*) from user where sex='1' and location='四川'

select count(*) from user where sex='0'and location='湖南'

select location  ,count(location)  from user where sex = '1' group by location

select * from user order by rand() limit 5;

select * from user where

SELECT * from admin a LEFT JOIN admin_role b ON a.id = b.user_id
LEFT JOIN role c ON b.role_id = c.id
LEFT JOIN role_resource d ON c.id = d.role_id
LEFT JOIN resource e ON d.resource_id =e.id
WHERE a.username = 'admin'
