/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.10-log : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `test`;

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户编号',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号（用于登录）',
  `mail` varchar(30) DEFAULT NULL COMMENT '邮箱（用于登录）',
  `username` varchar(30) NOT NULL COMMENT '帐号',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `state` int(11) DEFAULT NULL COMMENT '状态（0：正常，1：冻结，2：未激活......）',
  `role` int(11) DEFAULT NULL COMMENT '角色',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `ip_add` varchar(50) DEFAULT NULL COMMENT '最后一次登录的ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='登录表';

/*Table structure for table `login_record` */

DROP TABLE IF EXISTS `login_record`;

CREATE TABLE `login_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录编号',
  `username` varchar(20) NOT NULL COMMENT '客户编号',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `ip_add` varchar(50) DEFAULT NULL COMMENT '最后一次登录的ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='登录记录表';

/*Table structure for table `persistent_logins` */

DROP TABLE IF EXISTS `persistent_logins`;

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `weixin` */

DROP TABLE IF EXISTS `weixin`;

CREATE TABLE `weixin` (
  `openid` varchar(50) NOT NULL COMMENT '微信openid',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `nickname` varchar(50) DEFAULT NULL COMMENT '微信昵称',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `country` varchar(30) DEFAULT NULL COMMENT '国家',
  `province` varchar(30) DEFAULT NULL COMMENT '省份',
  `city` varchar(30) DEFAULT NULL COMMENT '城市',
  `head_img_url` varchar(200) DEFAULT NULL COMMENT '用户头像',
  `bind_time` datetime DEFAULT NULL COMMENT '绑定时间',
  PRIMARY KEY (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
