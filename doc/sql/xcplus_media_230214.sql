/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : localhost:3306
 Source Schema         : xcplus_media

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 14/02/2023 21:47:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for media_files
-- ----------------------------
DROP TABLE IF EXISTS `media_files`;
CREATE TABLE `media_files`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件id,md5值',
  `company_id` bigint(20) NULL DEFAULT NULL COMMENT '机构ID',
  `company_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构名称',
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
  `file_type` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型（图片、文档，视频）',
  `tags` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
  `bucket` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储目录',
  `file_path` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储路径',
  `file_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件id',
  `url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '媒资文件访问地址',
  `username` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传人',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `change_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态,1:正常，0:不展示',
  `remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `audit_status` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态',
  `audit_mind` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `file_size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_fileid`(`file_id`) USING BTREE COMMENT '文件id唯一索引 '
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '媒资信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of media_files
-- ----------------------------
INSERT INTO `media_files` VALUES ('224a797b837aba5bcefcedd1de96802f', 1232141425, NULL, 'Day1-05.开发环境配置-IDEA-虚拟机-Git-前端.mp4', '001002', '课程视频', 'video', '2/2/224a797b837aba5bcefcedd1de96802f/224a797b837aba5bcefcedd1de96802f.mp4', '224a797b837aba5bcefcedd1de96802f', '/video/2/2/224a797b837aba5bcefcedd1de96802f/224a797b837aba5bcefcedd1de96802f.mp4', NULL, '2023-02-12 20:07:19', NULL, '1', NULL, '002003', NULL, 167941019);
INSERT INTO `media_files` VALUES ('34ca8d8a522ea64ce5200c423755a03e', 1232141425, NULL, 'Day1-03.项目演示.mp4', '001002', '课程视频', 'video', '3/4/34ca8d8a522ea64ce5200c423755a03e/34ca8d8a522ea64ce5200c423755a03e.mp4', '34ca8d8a522ea64ce5200c423755a03e', '/video/3/4/34ca8d8a522ea64ce5200c423755a03e/34ca8d8a522ea64ce5200c423755a03e.mp4', NULL, '2023-02-12 20:04:40', NULL, '1', NULL, '002003', NULL, 160992129);
INSERT INTO `media_files` VALUES ('3706e9fb287258674c264313eb7a171b', 1232141425, NULL, 'Day6-01.文件预览开发.mp4', '001002', '课程视频', 'video', '3/7/3706e9fb287258674c264313eb7a171b/3706e9fb287258674c264313eb7a171b.mp4', '3706e9fb287258674c264313eb7a171b', '/video/3/7/3706e9fb287258674c264313eb7a171b/3706e9fb287258674c264313eb7a171b.mp4', NULL, '2023-02-12 20:15:22', NULL, '1', NULL, '002003', NULL, 138378506);
INSERT INTO `media_files` VALUES ('4587c09aa23951690d5c94d55a8fd421', 1232141425, NULL, 'Day4-01.媒资管理模块需求分析.mp4', '001002', '课程视频', 'video', '4/5/4587c09aa23951690d5c94d55a8fd421/4587c09aa23951690d5c94d55a8fd421.mp4', '4587c09aa23951690d5c94d55a8fd421', '/video/4/5/4587c09aa23951690d5c94d55a8fd421/4587c09aa23951690d5c94d55a8fd421.mp4', NULL, '2023-02-12 20:13:03', NULL, '1', NULL, '002003', NULL, 67524298);
INSERT INTO `media_files` VALUES ('584cd42490bd199bc702610f36d5b4e3', 1232141425, NULL, 'Day2-01.课程查询-DAO接口.mp4', '001002', '课程视频', 'video', '5/8/584cd42490bd199bc702610f36d5b4e3/584cd42490bd199bc702610f36d5b4e3.mp4', '584cd42490bd199bc702610f36d5b4e3', '/video/5/8/584cd42490bd199bc702610f36d5b4e3/584cd42490bd199bc702610f36d5b4e3.mp4', NULL, '2023-02-12 20:12:37', NULL, '1', NULL, '002003', NULL, 109094288);
INSERT INTO `media_files` VALUES ('7d82500458e8e9c1f4d59b2f665f6970', 1232141425, NULL, 'Day5-01.上传图片优化-抽取代码.mp4', '001002', '课程视频', 'video', '7/d/7d82500458e8e9c1f4d59b2f665f6970/7d82500458e8e9c1f4d59b2f665f6970.mp4', '7d82500458e8e9c1f4d59b2f665f6970', '/video/7/d/7d82500458e8e9c1f4d59b2f665f6970/7d82500458e8e9c1f4d59b2f665f6970.mp4', NULL, '2023-02-12 20:15:23', NULL, '1', NULL, '002003', NULL, 190241347);
INSERT INTO `media_files` VALUES ('88e4aabbed40ec82a7532d108a30260b', 1232141425, NULL, 'Day1-04.项目技术架构介绍.mp4', '001002', '课程视频', 'video', '8/8/88e4aabbed40ec82a7532d108a30260b/88e4aabbed40ec82a7532d108a30260b.mp4', '88e4aabbed40ec82a7532d108a30260b', '/video/8/8/88e4aabbed40ec82a7532d108a30260b/88e4aabbed40ec82a7532d108a30260b.mp4', NULL, '2023-02-12 20:06:46', NULL, '1', NULL, '002003', NULL, 69137019);
INSERT INTO `media_files` VALUES ('987c36d05123abda1514e8f0e7cc0138', 1232141425, NULL, 'Day7-01.媒资绑定-接口开发.mp4', '001002', '课程视频', 'video', '9/8/987c36d05123abda1514e8f0e7cc0138/987c36d05123abda1514e8f0e7cc0138.mp4', '987c36d05123abda1514e8f0e7cc0138', '/video/9/8/987c36d05123abda1514e8f0e7cc0138/987c36d05123abda1514e8f0e7cc0138.mp4', NULL, '2023-02-12 20:17:40', NULL, '1', NULL, '002003', NULL, 124685600);
INSERT INTO `media_files` VALUES ('a4e1a25899ba30f417ae8561f42591a0', 1232141425, NULL, 'Day8-01.课程发布-页面静态化.mp4', '001002', '课程视频', 'video', 'a/4/a4e1a25899ba30f417ae8561f42591a0/a4e1a25899ba30f417ae8561f42591a0.mp4', 'a4e1a25899ba30f417ae8561f42591a0', '/video/a/4/a4e1a25899ba30f417ae8561f42591a0/a4e1a25899ba30f417ae8561f42591a0.mp4', NULL, '2023-02-12 20:18:15', NULL, '1', NULL, '002003', NULL, 147120342);
INSERT INTO `media_files` VALUES ('a70348a565a37a3442b6ce60a577c427', 1232141425, NULL, '684f73bb4629bf4aa328eb5d51b22c690c83233a.jpg', '001001', NULL, 'mediafiles', '2023/2/12/a70348a565a37a3442b6ce60a577c427.jpg', 'a70348a565a37a3442b6ce60a577c427', '/mediafiles/2023/2/12/a70348a565a37a3442b6ce60a577c427.jpg', NULL, '2023-02-12 19:12:30', NULL, '1', NULL, '002003', NULL, 17294);
INSERT INTO `media_files` VALUES ('be58996735e21c091e0e76b324cd4236', 1232141425, NULL, '20221206101410Java-北京-苗润土.jpg', '001001', NULL, 'mediafiles', '2023/2/12/be58996735e21c091e0e76b324cd4236.jpg', 'be58996735e21c091e0e76b324cd4236', '/mediafiles/2023/2/12/be58996735e21c091e0e76b324cd4236.jpg', NULL, '2023-02-12 19:55:19', NULL, '1', NULL, '002003', NULL, 23481);
INSERT INTO `media_files` VALUES ('cdb7cb4b5e27fc11718405435da56ae1', 1232141425, NULL, 'Day11-01.支付-支付流程.mp4', '001002', '课程视频', 'video', 'c/d/cdb7cb4b5e27fc11718405435da56ae1/cdb7cb4b5e27fc11718405435da56ae1.mp4', 'cdb7cb4b5e27fc11718405435da56ae1', '/video/c/d/cdb7cb4b5e27fc11718405435da56ae1/cdb7cb4b5e27fc11718405435da56ae1.mp4', NULL, '2023-02-12 20:17:25', NULL, '1', NULL, '002003', NULL, 7783949);
INSERT INTO `media_files` VALUES ('d36b8ebb9a99bbdfb9855fb492f3afbf', 1232141425, NULL, 'Day12-01.接收支付通知-接口定义.mp4', '001002', '课程视频', 'video', 'd/3/d36b8ebb9a99bbdfb9855fb492f3afbf/d36b8ebb9a99bbdfb9855fb492f3afbf.mp4', 'd36b8ebb9a99bbdfb9855fb492f3afbf', '/video/d/3/d36b8ebb9a99bbdfb9855fb492f3afbf/d36b8ebb9a99bbdfb9855fb492f3afbf.mp4', NULL, '2023-02-12 20:18:41', NULL, '1', NULL, '002003', NULL, 62294977);
INSERT INTO `media_files` VALUES ('d8ab5d942a04d4edc03a3b62110550b2', 1232141425, NULL, 'Day1-01.项目介绍.mp4', '001002', '课程视频', 'video', 'd/8/d8ab5d942a04d4edc03a3b62110550b2/d8ab5d942a04d4edc03a3b62110550b2.mp4', 'd8ab5d942a04d4edc03a3b62110550b2', '/video/d/8/d8ab5d942a04d4edc03a3b62110550b2/d8ab5d942a04d4edc03a3b62110550b2.mp4', NULL, '2023-02-12 20:03:13', NULL, '1', NULL, '002003', NULL, 44105018);
INSERT INTO `media_files` VALUES ('dbaa8c77f6ceed7ff14885a1ea3dea39', 1232141425, NULL, 'Day1-02.面试-详细说说你的项目吧.mp4', '001002', '课程视频', 'video', 'd/b/dbaa8c77f6ceed7ff14885a1ea3dea39/dbaa8c77f6ceed7ff14885a1ea3dea39.mp4', 'dbaa8c77f6ceed7ff14885a1ea3dea39', '/video/d/b/dbaa8c77f6ceed7ff14885a1ea3dea39/dbaa8c77f6ceed7ff14885a1ea3dea39.mp4', NULL, '2023-02-12 20:03:19', NULL, '1', NULL, '002003', NULL, 35464938);
INSERT INTO `media_files` VALUES ('dd82cc59308d4afaeebb1c3bbad8c91a', 1232141425, NULL, 'Day13-01.课程查询优化需求.mp4', '001002', '课程视频', 'video', 'd/d/dd82cc59308d4afaeebb1c3bbad8c91a/dd82cc59308d4afaeebb1c3bbad8c91a.mp4', 'dd82cc59308d4afaeebb1c3bbad8c91a', '/video/d/d/dd82cc59308d4afaeebb1c3bbad8c91a/dd82cc59308d4afaeebb1c3bbad8c91a.mp4', NULL, '2023-02-12 20:18:22', NULL, '1', NULL, '002003', NULL, 10080529);
INSERT INTO `media_files` VALUES ('e00493393d836b75b5fb84813ec15f08', 1232141425, NULL, 'Day2-02.数据字典表.mp4', '001002', '课程视频', 'video', 'e/0/e00493393d836b75b5fb84813ec15f08/e00493393d836b75b5fb84813ec15f08.mp4', 'e00493393d836b75b5fb84813ec15f08', '/video/e/0/e00493393d836b75b5fb84813ec15f08/e00493393d836b75b5fb84813ec15f08.mp4', NULL, '2023-02-12 20:12:03', NULL, '1', NULL, '002003', NULL, 56492355);
INSERT INTO `media_files` VALUES ('e193d71ca57d173a47b32cd9b4d08592', 1232141425, NULL, 'Day1-00.项目导学.mp4', '001002', '课程视频', 'video', 'e/1/e193d71ca57d173a47b32cd9b4d08592/e193d71ca57d173a47b32cd9b4d08592.mp4', 'e193d71ca57d173a47b32cd9b4d08592', '/video/e/1/e193d71ca57d173a47b32cd9b4d08592/e193d71ca57d173a47b32cd9b4d08592.mp4', NULL, '2023-02-12 20:02:09', NULL, '1', NULL, '002003', NULL, 26072126);
INSERT INTO `media_files` VALUES ('e8f6582a26a4e82a867edb3286d1822e', 1232141425, NULL, 'Day9-01.网关鉴权.mp4', '001002', '课程视频', 'video', 'e/8/e8f6582a26a4e82a867edb3286d1822e/e8f6582a26a4e82a867edb3286d1822e.mp4', 'e8f6582a26a4e82a867edb3286d1822e', '/video/e/8/e8f6582a26a4e82a867edb3286d1822e/e8f6582a26a4e82a867edb3286d1822e.mp4', NULL, '2023-02-12 20:18:20', NULL, '1', NULL, '002003', NULL, 129355402);
INSERT INTO `media_files` VALUES ('ed694e924132a72e68ac00f023d083d9', 1232141425, NULL, 'Day2-03.课程查询-service.mp4', '001002', '课程视频', 'video', 'e/d/ed694e924132a72e68ac00f023d083d9/ed694e924132a72e68ac00f023d083d9.mp4', 'ed694e924132a72e68ac00f023d083d9', '/video/e/d/ed694e924132a72e68ac00f023d083d9/ed694e924132a72e68ac00f023d083d9.mp4', NULL, '2023-02-12 20:12:38', NULL, '1', NULL, '002003', NULL, 80765744);
INSERT INTO `media_files` VALUES ('efe6f7779c9d0ce1ac4c5f80cf48d1eb', 1232141425, NULL, 'Day3-01.异常处理-自定义异常类.mp4', '001002', '课程视频', 'video', 'e/f/efe6f7779c9d0ce1ac4c5f80cf48d1eb/efe6f7779c9d0ce1ac4c5f80cf48d1eb.mp4', 'efe6f7779c9d0ce1ac4c5f80cf48d1eb', '/video/e/f/efe6f7779c9d0ce1ac4c5f80cf48d1eb/efe6f7779c9d0ce1ac4c5f80cf48d1eb.mp4', NULL, '2023-02-12 20:13:19', NULL, '1', NULL, '002003', NULL, 62078746);
INSERT INTO `media_files` VALUES ('f6bb1fe0a9f46d7e3eab2f1d77d5f705', 1232141425, NULL, 'Day10-01.用户授权-什么的RBAC.mp4', '001002', '课程视频', 'video', 'f/6/f6bb1fe0a9f46d7e3eab2f1d77d5f705/f6bb1fe0a9f46d7e3eab2f1d77d5f705.mp4', 'f6bb1fe0a9f46d7e3eab2f1d77d5f705', '/video/f/6/f6bb1fe0a9f46d7e3eab2f1d77d5f705/f6bb1fe0a9f46d7e3eab2f1d77d5f705.mp4', NULL, '2023-02-12 20:17:15', NULL, '1', NULL, '002003', NULL, 30467147);

-- ----------------------------
-- Table structure for media_process
-- ----------------------------
DROP TABLE IF EXISTS `media_process`;
CREATE TABLE `media_process`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_id` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件标识',
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
  `bucket` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '存储桶',
  `file_path` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储路径',
  `status` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态,1:未处理，2：处理成功  3处理失败',
  `create_date` datetime(0) NOT NULL COMMENT '上传时间',
  `finish_date` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '媒资文件访问地址',
  `errormsg` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失败原因',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_fileid`(`file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of media_process
-- ----------------------------

-- ----------------------------
-- Table structure for media_process_history
-- ----------------------------
DROP TABLE IF EXISTS `media_process_history`;
CREATE TABLE `media_process_history`  (
  `id` bigint(20) NOT NULL,
  `file_id` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件标识',
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
  `bucket` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '存储源',
  `status` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态,1:未处理，2：处理成功  3处理失败',
  `create_date` datetime(0) NOT NULL COMMENT '上传时间',
  `finish_date` datetime(0) NOT NULL COMMENT '完成时间',
  `url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件访问地址',
  `file_path` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `errormsg` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失败原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of media_process_history
-- ----------------------------
INSERT INTO `media_process_history` VALUES (1, '1f229319d6fed3431d2f9d06193a433b', '01-分布式事务专题课程介绍.avi', 'video', '2', '2022-09-14 18:30:02', '2022-09-14 20:32:12', 'video1/f/1f229319d6fed3431d2f9d06193a433b/1f229319d6fed3431d2f9d06193a433b.mp4', '1/f/1f229319d6fed3431d2f9d06193a433b/1f229319d6fed3431d2f9d06193a433b.avi', NULL);
INSERT INTO `media_process_history` VALUES (2, '23f83ae728bd1269eee7ea2236e79644', '16-Nacos配置管理-课程总结.avi', 'video', '2', '2022-09-14 18:21:44', '2022-09-14 20:32:20', 'video2/3/23f83ae728bd1269eee7ea2236e79644/23f83ae728bd1269eee7ea2236e79644.mp4', '2/3/23f83ae728bd1269eee7ea2236e79644/23f83ae728bd1269eee7ea2236e79644.avi', NULL);
INSERT INTO `media_process_history` VALUES (3, '3a5a861d1c745d05166132c47b44f9e4', '01-Nacos配置管理-内容介绍.avi', 'video', '2', '2022-09-14 18:19:24', '2022-09-14 20:33:27', 'video3/a/3a5a861d1c745d05166132c47b44f9e4/3a5a861d1c745d05166132c47b44f9e4.mp4', '3/a/3a5a861d1c745d05166132c47b44f9e4/3a5a861d1c745d05166132c47b44f9e4.avi', NULL);
INSERT INTO `media_process_history` VALUES (4, '6ad24a762f67c18f61966c1b8c55abe6', '07-分布式事务基础理论-BASE理论.avi', 'video', '2', '2022-09-14 18:30:16', '2022-09-14 20:33:46', 'video6/a/6ad24a762f67c18f61966c1b8c55abe6/6ad24a762f67c18f61966c1b8c55abe6.mp4', '6/a/6ad24a762f67c18f61966c1b8c55abe6/6ad24a762f67c18f61966c1b8c55abe6.avi', NULL);
INSERT INTO `media_process_history` VALUES (5, '70a98b4a2fffc89e50b101f959cc33ca', '22-Hmily实现TCC事务-开发bank2的confirm方法.avi', 'video', '2', '2022-09-14 18:30:52', '2022-09-14 20:34:33', 'video7/0/70a98b4a2fffc89e50b101f959cc33ca/70a98b4a2fffc89e50b101f959cc33ca.mp4', '7/0/70a98b4a2fffc89e50b101f959cc33ca/70a98b4a2fffc89e50b101f959cc33ca.avi', NULL);

-- ----------------------------
-- Table structure for mq_message
-- ----------------------------
DROP TABLE IF EXISTS `mq_message`;
CREATE TABLE `mq_message`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息id',
  `message_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息类型代码',
  `business_key1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `business_key2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `business_key3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `mq_host` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息队列主机',
  `mq_port` int(11) NOT NULL COMMENT '消息队列端口',
  `mq_virtualhost` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息队列虚拟主机',
  `mq_queue` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '队列名称',
  `inform_num` int(10) UNSIGNED NOT NULL COMMENT '通知次数',
  `state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '处理状态，0:初始，1:成功',
  `returnfailure_date` datetime(0) NULL DEFAULT NULL COMMENT '回复失败时间',
  `returnsuccess_date` datetime(0) NULL DEFAULT NULL COMMENT '回复成功时间',
  `returnfailure_msg` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复失败内容',
  `inform_date` datetime(0) NULL DEFAULT NULL COMMENT '最近通知时间',
  `stage_state1` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阶段1处理状态, 0:初始，1:成功',
  `stage_state2` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阶段2处理状态, 0:初始，1:成功',
  `stage_state3` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阶段3处理状态, 0:初始，1:成功',
  `stage_state4` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阶段4处理状态, 0:初始，1:成功',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mq_message
-- ----------------------------
INSERT INTO `mq_message` VALUES ('f29a3149-7429-40be-8a4e-9909f32003b0', 'xc.mq.msgsync.coursepub', '111', NULL, NULL, '127.0.0.1', 5607, '/', 'xc.course.publish.queue', 0, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for mq_message_history
-- ----------------------------
DROP TABLE IF EXISTS `mq_message_history`;
CREATE TABLE `mq_message_history`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息id',
  `message_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息类型代码',
  `business_key1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `business_key2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `business_key3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `mq_host` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息队列主机',
  `mq_port` int(11) NOT NULL COMMENT '消息队列端口',
  `mq_virtualhost` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息队列虚拟主机',
  `mq_queue` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '队列名称',
  `inform_num` int(10) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '通知次数',
  `state` int(10) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '处理状态，0:初始，1:成功，2:失败',
  `returnfailure_date` datetime(0) NULL DEFAULT NULL COMMENT '回复失败时间',
  `returnsuccess_date` datetime(0) NULL DEFAULT NULL COMMENT '回复成功时间',
  `returnfailure_msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复失败内容',
  `inform_date` datetime(0) NULL DEFAULT NULL COMMENT '最近通知时间',
  `stage_state1` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stage_state2` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stage_state3` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stage_state4` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mq_message_history
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
