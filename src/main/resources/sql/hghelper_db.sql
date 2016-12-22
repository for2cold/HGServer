/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : hghelper_db

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2016-12-14 17:09:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hg_account
-- ----------------------------
DROP TABLE IF EXISTS `hg_account`;
CREATE TABLE `hg_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sheet_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `mobile` varchar(32) NOT NULL,
  `alipay` varchar(32) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `amount` decimal(46,2) NOT NULL DEFAULT '0.00',
  `balance` decimal(46,2) NOT NULL DEFAULT '0.00',
  `withdraw_balance` decimal(46,2) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '0',
  `deadline` date DEFAULT NULL,
  `withdraw_date` date DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hg_account
-- ----------------------------
INSERT INTO `hg_account` VALUES ('1', '1', '3', '15603056644', '黄伟钢', 'K2', '100.00', '30.00', '0.00', '0', null, null, null, '2016-08-23 22:50:40');

-- ----------------------------
-- Table structure for hg_account_sheet
-- ----------------------------
DROP TABLE IF EXISTS `hg_account_sheet`;
CREATE TABLE `hg_account_sheet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `create_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account_sheet_title` (`title`),
  KEY `idx_account_sheet_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hg_account_sheet
-- ----------------------------
INSERT INTO `hg_account_sheet` VALUES ('1', '2016-12', '3', '2016-12-14 14:20:01');

-- ----------------------------
-- Table structure for hg_apk
-- ----------------------------
DROP TABLE IF EXISTS `hg_apk`;
CREATE TABLE `hg_apk` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `path` varchar(300) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hg_apk
-- ----------------------------
INSERT INTO `hg_apk` VALUES ('3', '小强载体.apk', 'apk/51520bb2649d7655e74b.apk', '1', '2016-09-08 22:03:07');
INSERT INTO `hg_apk` VALUES ('6', '学生赚老版本.apk', 'apk/1dbbd984004bfcb71e55.apk', '1', '2016-09-10 21:43:08');
INSERT INTO `hg_apk` VALUES ('7', '天啦撸.apk', 'apk/652e00b8e572f998b07c.apk', '1', '2016-09-10 21:43:54');
INSERT INTO `hg_apk` VALUES ('8', 'HG果冻_V1.4.apk', 'apk/a29de443172e3daf79d5.apk', '1', '2016-09-10 21:46:17');
INSERT INTO `hg_apk` VALUES ('9', 'ES文件浏览器.apk', 'apk/531a37ad05c6eb10eadf.apk', '1', '2016-09-10 21:50:05');
INSERT INTO `hg_apk` VALUES ('10', 'FFVpn_3.5.1.2.apk', 'apk/d3ba731129779bd04fcd.apk', '1', '2016-09-10 21:51:52');
INSERT INTO `hg_apk` VALUES ('11', '触动精灵_V2.0.5.apk', 'apk/ba8304ce8ef42a918caf.apk', '1', '2016-09-10 21:57:50');
INSERT INTO `hg_apk` VALUES ('12', 'KingRoot.apk', 'apk/35d46367b8b56b5f8661.apk', '1', '2016-09-10 22:17:58');
INSERT INTO `hg_apk` VALUES ('13', '讯飞输入法.apk', 'apk/007244ea9686831a970c.apk', '1', '2016-09-14 16:31:07');
INSERT INTO `hg_apk` VALUES ('14', '应用宝.apk', 'apk/5b001f9221a3eef22544.apk', '1', '2016-09-14 16:40:19');
INSERT INTO `hg_apk` VALUES ('22', 'xsz12941863.apk', 'apk/xsz12941863.apk', '1', '2016-09-22 22:33:54');
INSERT INTO `hg_apk` VALUES ('26', '小强载体.apk', 'apk/51520bb2649d7655e74b.apk', '3', '2016-09-08 22:03:07');
INSERT INTO `hg_apk` VALUES ('28', '天啦撸.apk', 'apk/652e00b8e572f998b07c.apk', '3', '2016-09-10 21:43:54');
INSERT INTO `hg_apk` VALUES ('29', 'ES文件浏览器.apk', 'apk/531a37ad05c6eb10eadf.apk', '3', '2016-09-10 21:50:05');
INSERT INTO `hg_apk` VALUES ('30', '触动精灵_V2.0.5.apk', 'apk/ba8304ce8ef42a918caf.apk', '3', '2016-09-10 21:57:50');
INSERT INTO `hg_apk` VALUES ('31', 'KingRoot.apk', 'apk/35d46367b8b56b5f8661.apk', '3', '2016-09-10 22:17:58');
INSERT INTO `hg_apk` VALUES ('32', '讯飞输入法.apk', 'apk/007244ea9686831a970c.apk', '3', '2016-09-14 16:31:07');
INSERT INTO `hg_apk` VALUES ('35', '触动精灵安卓版_V1.3.0.apk', 'apk/a8b36ae9da6013169960.apk', '2', '2016-10-04 16:35:08');
INSERT INTO `hg_apk` VALUES ('36', '触动精灵内测版_V2.0.4.1.apk', 'apk/1a456fa5b09d0a7d0d61.apk', '2', '2016-10-04 16:37:47');
INSERT INTO `hg_apk` VALUES ('41', 'xsz13068103', 'apk/da14e2f3470f5bede383.apk', '3', '2016-10-24 16:14:57');
INSERT INTO `hg_apk` VALUES ('46', '支付宝', 'apk/7d699b65adf88c1e32aa.apk', '3', '2016-11-19 14:35:54');
INSERT INTO `hg_apk` VALUES ('50', '学生赚7.24防更新版', 'apk/9d3992749dcd0beef90b.apk', '3', '2016-11-25 16:04:04');
INSERT INTO `hg_apk` VALUES ('52', '001', 'apk/d1991c82fe7a70bcfc86.apk', '2', '2016-11-26 15:16:22');
INSERT INTO `hg_apk` VALUES ('53', '学生赚7.24', 'apk/754e68392db6119f7543.apk', '3', '2016-12-01 09:40:01');

-- ----------------------------
-- Table structure for hg_script
-- ----------------------------
DROP TABLE IF EXISTS `hg_script`;
CREATE TABLE `hg_script` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `key` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `path` varchar(300) NOT NULL,
  `version` float NOT NULL DEFAULT '1',
  `type` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `remark` varchar(300) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hg_script
-- ----------------------------
INSERT INTO `hg_script` VALUES ('16', 'TSLib', 'tslib', '1', 'scripts/4c4ac3a5fa53429f85d6.lua', '1.1', '1', '1', '触动精灵官方拓展库', '2016-08-29 23:20:01');
INSERT INTO `hg_script` VALUES ('17', '填充手机数据', 'ts_fill_data', '1', 'scripts/0bcb5307ba3cbf9bb961.lua', '1', '1', '1', '填充手机数据', '2016-08-29 23:33:38');
INSERT INTO `hg_script` VALUES ('31', '普通任务', 'ts_normal_task', '1', 'scripts/1a0affbcc4bab4e1e8e2.lua', '1.5', '1', '1', '任务执行流程优化', '2016-09-05 02:01:12');
INSERT INTO `hg_script` VALUES ('33', '任务签到', 'ts_sign_task', '1', 'scripts/d9fe93fdf443d2907895.lua', '2', '1', '1', '任务签到功能', '2016-09-06 20:42:20');
INSERT INTO `hg_script` VALUES ('35', '一键卸载', 'ts_unstall', '1', 'scripts/eaee46bae3ca24a1fe1f.lua', '1', '1', '1', '一键卸载普通任务', '2016-09-09 22:28:05');
INSERT INTO `hg_script` VALUES ('38', '清理手机_QQ', 'ts_clean_phone_qq', '1', 'scripts/dc2fca26f7684dc36a61.lua', '1.1', '1', '1', 'QQ、钛备份不删除', '2016-09-16 00:13:10');
INSERT INTO `hg_script` VALUES ('40', '清理手机', 'ts_clean_phone', '1', 'scripts/88d70d530c30c52feed2.lua', '4', '1', '1', '使用HG果冻清理方式', '2016-09-18 10:39:09');
INSERT INTO `hg_script` VALUES ('43', '百度任务', 'ts_baidu_task', '1', 'scripts/059537534363141d3ad7.lua', '3.7', '1', '0', '百度任务列表开关', '2016-09-18 15:56:57');
INSERT INTO `hg_script` VALUES ('44', '触动百度任务v1.8.2', 'ts_baidu_task_1.8.2', '1', 'scripts/12d709f2ded5c9629205.lua', '1.82', '1', '0', '屏蔽咪咕直播', '2016-09-19 21:40:37');
INSERT INTO `hg_script` VALUES ('49', '百度任务', 'ts_baidu_task', '1', 'scripts/570ef76bf72efab8d0a9.lua', '3.8', '1', '0', '流程优化，增加延时', '2016-09-21 22:56:15');
INSERT INTO `hg_script` VALUES ('50', '百度任务_1.8.3', 'ts_baidu_task_1.8.2', '1', 'scripts/b3279c60c490bf1b1d7f.lua', '1.84', '1', '0', '流程优化，增加延时', '2016-09-21 22:57:20');
INSERT INTO `hg_script` VALUES ('65', '普通任务', 'th_normal_task', '1', 'scripts/75b11a03bf7b153ab189.lua', '1.6', '1', '1', '进入纯任务列表', '2016-09-25 10:58:26');
INSERT INTO `hg_script` VALUES ('66', '百度任务', 'ts_baidu_task', '1', 'scripts/e736b27f1d5dbb0fefe2.lua', '3.9', '1', '0', '运行时间改为30秒', '2016-09-25 11:00:16');
INSERT INTO `hg_script` VALUES ('75', '触动百度任务v1.8.2', 'ts_baidu_task_1.8.2', '1', 'scripts/0d9c6fab06b66d2af0c2.lua', '1.9', '1', '0', '运行时间30秒，增加苏宁易购', '2016-09-27 21:04:03');
INSERT INTO `hg_script` VALUES ('76', '百度任务', 'ts_baidu_task', '1', 'scripts/c97054970e6ef9c6c75b.lua', '4', '1', '0', '增加苏宁易购', '2016-09-27 21:05:19');
INSERT INTO `hg_script` VALUES ('77', '百度任务', 'ts_baidu_task', '1', 'scripts/b2eb801c1f4b120afe63.lua', '4.1', '1', '0', '天啦撸点击保存', '2016-09-27 22:05:25');
INSERT INTO `hg_script` VALUES ('78', '百度任务', 'ts_baidu_task', '1', 'scripts/af7cbbd043ace25a277e.lua', '4.2', '1', '0', '百度输入法', '2016-09-27 22:40:58');
INSERT INTO `hg_script` VALUES ('79', '触动百度任务v1.8.2', 'ts_baidu_task_1.8.2', '1', 'scripts/06f674042242c8c28979.lua', '2', '1', '0', '百度输入法', '2016-09-27 22:43:37');
INSERT INTO `hg_script` VALUES ('82', '触动百度任务v1.8.2', 'ts_baidu_task_1.8.2', '1', 'scripts/915e5dda66facb2e4d22.lua', '2.1', '1', '0', '安装时间 + 运行时间 = 70秒，增加驾校一点通，金额限制为49', '2016-09-28 23:49:16');
INSERT INTO `hg_script` VALUES ('83', '百度任务', 'ts_baidu_task', '1', 'scripts/2c046a24cc9d91a27461.lua', '4.3', '1', '0', '安装时间 + 运行时间 = 70秒，增加驾校一点通，金额限制为49', '2016-09-28 23:49:50');
INSERT INTO `hg_script` VALUES ('85', '008填充数据_v1.2', '008填充数据_v1.2', '2', 'scripts/7cc370f45287267f3d90.lua', '1.2', '1', '1', '008填充数据_v1.2', '2016-09-29 20:44:12');
INSERT INTO `hg_script` VALUES ('87', '触动百度任务v1.8.2', 'ts_baidu_task_1.8.2', '1', 'scripts/24ae854e55ae85bdb22f.lua', '2.2', '1', '0', '增加咪咕直播', '2016-09-29 20:54:19');
INSERT INTO `hg_script` VALUES ('88', 'TSLib', 'tslib', '3', 'scripts/4c4ac3a5fa53429f85d6.lua', '1', '1', '1', '触动精灵官方拓展库', '2016-08-29 23:20:01');
INSERT INTO `hg_script` VALUES ('89', '填充手机数据', 'ts_fill_data', '3', 'scripts/0bcb5307ba3cbf9bb961.lua', '1', '1', '1', '填充手机数据', '2016-08-29 23:33:38');
INSERT INTO `hg_script` VALUES ('153', 'TSLib', 'TSLib', '2', 'scripts/1f4cf8e3f74b4b43ebd1.lua', '6', '1', '1', 'TSLib', '2016-10-23 09:34:48');
INSERT INTO `hg_script` VALUES ('159', '百度任务', 'ts_baidu_task', '1', 'scripts/7adb643e6916b030db51.lua', '4.4', '1', '1', 'HG果冻点击不到bug', '2016-10-23 21:31:30');
INSERT INTO `hg_script` VALUES ('163', 'HM2A任务后清理', 'HM2A任务后清理', '2', 'scripts/26791ccdf4295bb67cd4.lua', '10', '1', '1', 'HM2A任务后清理', '2016-10-23 22:25:35');
INSERT INTO `hg_script` VALUES ('167', 'HM1S移动版清理脚本3.1', 'HM1S移动版清理脚本3.1', '2', 'scripts/10fd56c35b131b2f7e28.lua', '3.2', '1', '1', 'HM1S移动版清理脚本3.1', '2016-10-24 08:44:06');
INSERT INTO `hg_script` VALUES ('169', 'HM1S移动版任务后清理3.2', 'HM1S移动版任务后清理3.2', '2', 'scripts/6a1beb2e4f4e6a3f8c32.lua', '3.2', '1', '1', 'HM1S移动版任务后清理3.2', '2016-10-25 13:07:56');
INSERT INTO `hg_script` VALUES ('193', '百度任务', 'ts_baidu_task', '3', 'scripts/2fa59055b3515d1df467.lua', '6.3', '1', '1', '播呀FM任务', '2016-12-06 00:13:37');
INSERT INTO `hg_script` VALUES ('196', '清理手机', 'ts_clean_phone', '3', 'scripts/3aaa98db0b9ec32b73a7.lua', '4.3', '1', '1', '不卸载万能钥匙', '2016-12-06 21:54:59');
INSERT INTO `hg_script` VALUES ('197', 'HM1SPTv0.2', 'HM1SPTv0.2', '2', 'scripts/e5a93c69b3bff28e2cd8.lua', '0.2', '1', '1', 'HM1SPTv0.2', '2016-12-07 05:38:12');
INSERT INTO `hg_script` VALUES ('199', '普通任务', 'ts_normal_task', '3', 'scripts/2da26ae5101ddb55546f.lua', '1.6', '1', '0', '去除VPN检测', '2016-12-08 09:51:34');
INSERT INTO `hg_script` VALUES ('201', '清理手机_test', 'ts_clean_phone_test', '3', 'scripts/9d7cf898fad96c51b1bf.lua', '1.2', '1', '1', 'testatetaeteta', '2016-12-09 17:13:34');
INSERT INTO `hg_script` VALUES ('202', 'XSZ普通V0.31', 'XSZ普通V0.31', '2', 'scripts/c5caf7ed82d2f7f7e852.lua', '0.1', '1', '1', 'XSZ普通V0.31', '2016-12-10 06:39:42');
INSERT INTO `hg_script` VALUES ('203', '百度任务_新_测试', 'ts_baidu_task_test', '3', 'scripts/86c72a8419d03bf26d95.lua', '6.4', '1', '0', '升级触动精灵版本 2.16以上版本 测试阶段', '2016-12-11 01:52:23');
INSERT INTO `hg_script` VALUES ('206', '普通任务', 'ts_normal_task', '3', 'scripts/b6670f7f0fc9ea8aa401.lua', '1.7', '1', '1', '三行下载优化', '2016-12-11 12:36:25');
INSERT INTO `hg_script` VALUES ('207', 'XSZBD20元V0.10', 'XSZBD20元V0.10', '2', 'scripts/1f68f51efded4e7a9344.lua', '0.1', '1', '1', 'XSZBD20元V0.10', '2016-12-12 09:09:14');
INSERT INTO `hg_script` VALUES ('208', '百度任务_新_测试', 'ts_baidu_task_test', '3', 'scripts/8683c33d8e9fa1d839d5.lua', '6.5', '1', '1', 'ts_baidu_task_test', '2016-12-12 19:50:17');

-- ----------------------------
-- Table structure for hg_user
-- ----------------------------
DROP TABLE IF EXISTS `hg_user`;
CREATE TABLE `hg_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `realname` varchar(32) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hg_user
-- ----------------------------
INSERT INTO `hg_user` VALUES ('1', 'kazyle', '0934ad606d7ed57bab4d3fa941789ab9', '余清增', '2016-08-24 13:29:28');
INSERT INTO `hg_user` VALUES ('2', 'tianlalu', '9a440f1f071dad3043b6c8ba93bb761c', '天啦撸', '2016-08-24 14:13:13');
INSERT INTO `hg_user` VALUES ('3', 'hugo', 'ba4a5695993bc33ba3d01f13baa08a98', '黄伟钢', '2016-09-30 00:48:55');

-- ----------------------------
-- Table structure for hg_vpn
-- ----------------------------
DROP TABLE IF EXISTS `hg_vpn`;
CREATE TABLE `hg_vpn` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `name` varchar(32) NOT NULL,
  `host` varchar(128) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '0',
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hg_vpn
-- ----------------------------
INSERT INTO `hg_vpn` VALUES ('1', '1', '三门峡', 'mx.28289.org', 'zu1', '826', '0', '2016-09-18 22:00:03');
