/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : localhost:3306
 Source Schema         : xcplus_content

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 14/02/2023 21:46:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course_audit
-- ----------------------------
DROP TABLE IF EXISTS `course_audit`;
CREATE TABLE `course_audit`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `course_id` bigint(20) NOT NULL COMMENT '课程id',
  `audit_mind` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `audit_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审核状态',
  `audit_people` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人',
  `audit_date` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_audit
-- ----------------------------

-- ----------------------------
-- Table structure for course_base
-- ----------------------------
DROP TABLE IF EXISTS `course_base`;
CREATE TABLE `course_base`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` bigint(20) NOT NULL COMMENT '机构ID',
  `company_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构名称',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `users` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '适用人群',
  `tags` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程标签',
  `mt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大分类',
  `st` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小分类',
  `grade` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程等级',
  `teachmode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教育模式(common普通，record 录播，live直播等）',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程介绍',
  `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程图片',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `change_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_people` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `change_people` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `audit_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '审核状态',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '课程发布状态 未发布  已发布 下线',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程基本信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_base
-- ----------------------------
INSERT INTO `course_base` VALUES (6, 1232141425, NULL, '学成在线', 'Java 初级/中级 开发工程是', 'Java企业级实战开发', '1-3', '1-3-2', '204001', '200002', '本课程以在线教育业务为基础，基于当前热门的 Spring Cloud 微服务技术栈进行设计，采用Nginx、SpringBoot、Spring Cloud、MyBatis-Plus、MQ、Redis、Elasticsearch等框架和中间件为基础进行开发，带领学员体验Java大型项目从需求分析、架构设计、编码、调试、测试的整个过程。', '/mediafiles/2023/2/12/a70348a565a37a3442b6ce60a577c427.jpg', '2023-02-12 19:41:15', '2023-02-12 20:19:02', NULL, NULL, '202003', '203001');

-- ----------------------------
-- Table structure for course_category
-- ----------------------------
DROP TABLE IF EXISTS `course_category`;
CREATE TABLE `course_category`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `label` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类标签默认和名称一样',
  `parentid` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '父结点id（第一级的父节点是0，自关联字段id）',
  `is_show` tinyint(4) NULL DEFAULT NULL COMMENT '是否显示',
  `orderby` int(11) NULL DEFAULT NULL COMMENT '排序字段',
  `is_leaf` tinyint(4) NULL DEFAULT NULL COMMENT '是否叶子',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_category
-- ----------------------------
INSERT INTO `course_category` VALUES ('1', '根结点', '根结点', '0', 1, 1, 0);
INSERT INTO `course_category` VALUES ('1-1', '前端开发', '前端开发', '1', 1, 1, 0);
INSERT INTO `course_category` VALUES ('1-1-1', 'HTML/CSS', 'HTML/CSS', '1-1', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-1-10', '其它', '其它', '1-1', 1, 10, 1);
INSERT INTO `course_category` VALUES ('1-1-2', 'JavaScript', 'JavaScript', '1-1', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-1-3', 'jQuery', 'jQuery', '1-1', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-1-4', 'ExtJS', 'ExtJS', '1-1', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-1-5', 'AngularJS', 'AngularJS', '1-1', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-1-6', 'ReactJS', 'ReactJS', '1-1', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-1-7', 'Bootstrap', 'Bootstrap', '1-1', 1, 7, 1);
INSERT INTO `course_category` VALUES ('1-1-8', 'Node.js', 'Node.js', '1-1', 1, 8, 1);
INSERT INTO `course_category` VALUES ('1-1-9', 'Vue', 'Vue', '1-1', 1, 9, 1);
INSERT INTO `course_category` VALUES ('1-10', '研发管理', '研发管理', '1', 1, 10, 0);
INSERT INTO `course_category` VALUES ('1-10-1', '敏捷开发', '敏捷开发', '1-10', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-10-2', '软件设计', '软件设计', '1-10', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-10-3', '软件测试', '软件测试', '1-10', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-10-4', '研发管理', '研发管理', '1-10', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-10-5', '其它', '其它', '1-10', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-11', '系统运维', '系统运维', '1', 1, 11, 0);
INSERT INTO `course_category` VALUES ('1-11-1', 'Linux', 'Linux', '1-11', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-11-10', '其它', '其它', '1-11', 1, 10, 1);
INSERT INTO `course_category` VALUES ('1-11-2', 'Windows', 'Windows', '1-11', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-11-3', 'UNIX', 'UNIX', '1-11', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-11-4', 'Mac OS', 'Mac OS', '1-11', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-11-5', '网络技术', '网络技术', '1-11', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-11-6', '路由协议', '路由协议', '1-11', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-11-7', '无线网络', '无线网络', '1-11', 1, 7, 1);
INSERT INTO `course_category` VALUES ('1-11-8', 'Ngnix', 'Ngnix', '1-11', 1, 8, 1);
INSERT INTO `course_category` VALUES ('1-11-9', '邮件服务器', '邮件服务器', '1-11', 1, 9, 1);
INSERT INTO `course_category` VALUES ('1-12', '产品经理', '产品经理', '1', 1, 12, 0);
INSERT INTO `course_category` VALUES ('1-12-1', '交互设计', '交互设计', '1-12', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-12-2', '产品设计', '产品设计', '1-12', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-12-3', '原型设计', '原型设计', '1-12', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-12-4', '用户体验', '用户体验', '1-12', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-12-5', '需求分析', '需求分析', '1-12', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-12-6', '其它', '其它', '1-12', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-13', '企业/办公/职场', '企业/办公/职场', '1', 1, 13, 0);
INSERT INTO `course_category` VALUES ('1-13-1', '运营管理', '运营管理', '1-13', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-13-2', '企业信息化', '企业信息化', '1-13', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-13-3', '网络营销', '网络营销', '1-13', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-13-4', 'Office/WPS', 'Office/WPS', '1-13', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-13-5', '招聘/面试', '招聘/面试', '1-13', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-13-6', '电子商务', '电子商务', '1-13', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-13-7', 'CRM', 'CRM', '1-13', 1, 7, 1);
INSERT INTO `course_category` VALUES ('1-13-8', 'ERP', 'ERP', '1-13', 1, 8, 1);
INSERT INTO `course_category` VALUES ('1-13-9', '其它', '其它', '1-13', 1, 9, 1);
INSERT INTO `course_category` VALUES ('1-14', '信息安全', '信息安全', '1', 1, 14, 0);
INSERT INTO `course_category` VALUES ('1-14-1', '密码学/加密/破解', '密码学/加密/破解', '1-14', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-14-10', '其它', '其它', '1-14', 1, 10, 1);
INSERT INTO `course_category` VALUES ('1-14-2', '渗透测试', '渗透测试', '1-14', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-14-3', '社会工程', '社会工程', '1-14', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-14-4', '漏洞挖掘与利用', '漏洞挖掘与利用', '1-14', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-14-5', '云安全', '云安全', '1-14', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-14-6', '防护加固', '防护加固', '1-14', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-14-7', '代码审计', '代码审计', '1-14', 1, 7, 1);
INSERT INTO `course_category` VALUES ('1-14-8', '移动安全', '移动安全', '1-14', 1, 8, 1);
INSERT INTO `course_category` VALUES ('1-14-9', '病毒木马', '病毒木马', '1-14', 1, 9, 1);
INSERT INTO `course_category` VALUES ('1-15', '测试目录', '测试目录', '1', 1, 15, 0);
INSERT INTO `course_category` VALUES ('1-15-1', '测试目录01', '测试目录01', '1-15', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-2', '移动开发', '移动开发', '1', 1, 2, 0);
INSERT INTO `course_category` VALUES ('1-2-1', '微信开发', '微信开发', '1-2', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-2-2', 'iOS', 'iOS', '1-2', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-2-3', '手游开发', '手游开发', '1-2', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-2-4', 'Swift', 'Swift', '1-2', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-2-5', 'Android', 'Android', '1-2', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-2-6', 'ReactNative', 'ReactNative', '1-2', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-2-7', 'Cordova', 'Cordova', '1-2', 1, 7, 1);
INSERT INTO `course_category` VALUES ('1-2-8', '其它', '其它', '1-2', 1, 8, 1);
INSERT INTO `course_category` VALUES ('1-3', '编程开发', '编程开发', '1', 1, 3, 0);
INSERT INTO `course_category` VALUES ('1-3-1', 'C/C++', 'C/C++', '1-3', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-3-2', 'Java', 'Java', '1-3', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-3-3', '.NET', '.NET', '1-3', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-3-4', 'Objective-C', 'Objective-C', '1-3', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-3-5', 'Go语言', 'Go语言', '1-3', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-3-6', 'Python', 'Python', '1-3', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-3-7', 'Ruby/Rails', 'Ruby/Rails', '1-3', 1, 7, 1);
INSERT INTO `course_category` VALUES ('1-3-8', '其它', '其它', '1-3', 1, 8, 1);
INSERT INTO `course_category` VALUES ('1-4', '数据库', '数据库', '1', 1, 4, 0);
INSERT INTO `course_category` VALUES ('1-4-1', 'Oracle', 'Oracle', '1-4', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-4-2', 'MySQL', 'MySQL', '1-4', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-4-3', 'SQL Server', 'SQL Server', '1-4', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-4-4', 'DB2', 'DB2', '1-4', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-4-5', 'NoSQL', 'NoSQL', '1-4', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-4-6', 'Mongo DB', 'Mongo DB', '1-4', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-4-7', 'Hbase', 'Hbase', '1-4', 1, 7, 1);
INSERT INTO `course_category` VALUES ('1-4-8', '数据仓库', '数据仓库', '1-4', 1, 8, 1);
INSERT INTO `course_category` VALUES ('1-4-9', '其它', '其它', '1-4', 1, 9, 1);
INSERT INTO `course_category` VALUES ('1-5', '人工智能', '人工智能', '1', 1, 5, 0);
INSERT INTO `course_category` VALUES ('1-5-1', '机器学习', '机器学习', '1-5', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-5-2', '深度学习', '深度学习', '1-5', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-5-3', '语音识别', '语音识别', '1-5', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-5-4', '计算机视觉', '计算机视觉', '1-5', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-5-5', 'NLP', 'NLP', '1-5', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-5-6', '强化学习', '强化学习', '1-5', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-5-7', '其它', '其它', '1-5', 1, 7, 1);
INSERT INTO `course_category` VALUES ('1-6', '云计算/大数据', '云计算/大数据', '1', 1, 6, 0);
INSERT INTO `course_category` VALUES ('1-6-1', 'Spark', 'Spark', '1-6', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-6-2', 'Hadoop', 'Hadoop', '1-6', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-6-3', 'OpenStack', 'OpenStack', '1-6', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-6-4', 'Docker/K8S', 'Docker/K8S', '1-6', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-6-5', '云计算基础架构', '云计算基础架构', '1-6', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-6-6', '虚拟化技术', '虚拟化技术', '1-6', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-6-7', '云平台', '云平台', '1-6', 1, 7, 1);
INSERT INTO `course_category` VALUES ('1-6-8', 'ELK', 'ELK', '1-6', 1, 8, 1);
INSERT INTO `course_category` VALUES ('1-6-9', '其它', '其它', '1-6', 1, 9, 1);
INSERT INTO `course_category` VALUES ('1-7', 'UI设计', 'UI设计', '1', 1, 7, 0);
INSERT INTO `course_category` VALUES ('1-7-1', 'Photoshop', 'Photoshop', '1-7', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-7-10', 'InDesign', 'InDesign', '1-7', 1, 10, 1);
INSERT INTO `course_category` VALUES ('1-7-11', 'Pro/Engineer', 'Pro/Engineer', '1-7', 1, 11, 1);
INSERT INTO `course_category` VALUES ('1-7-12', 'Cinema 4D', 'Cinema 4D', '1-7', 1, 12, 1);
INSERT INTO `course_category` VALUES ('1-7-13', '3D Studio', '3D Studio', '1-7', 1, 13, 1);
INSERT INTO `course_category` VALUES ('1-7-14', 'After Effects（AE）', 'After Effects（AE）', '1-7', 1, 14, 1);
INSERT INTO `course_category` VALUES ('1-7-15', '原画设计', '原画设计', '1-7', 1, 15, 1);
INSERT INTO `course_category` VALUES ('1-7-16', '动画制作', '动画制作', '1-7', 1, 16, 1);
INSERT INTO `course_category` VALUES ('1-7-17', 'Dreamweaver', 'Dreamweaver', '1-7', 1, 17, 1);
INSERT INTO `course_category` VALUES ('1-7-18', 'Axure', 'Axure', '1-7', 1, 18, 1);
INSERT INTO `course_category` VALUES ('1-7-19', '其它', '其它', '1-7', 1, 19, 1);
INSERT INTO `course_category` VALUES ('1-7-2', '3Dmax', '3Dmax', '1-7', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-7-3', 'Illustrator', 'Illustrator', '1-7', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-7-4', 'Flash', 'Flash', '1-7', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-7-5', 'Maya', 'Maya', '1-7', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-7-6', 'AUTOCAD', 'AUTOCAD', '1-7', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-7-7', 'UG', 'UG', '1-7', 1, 7, 1);
INSERT INTO `course_category` VALUES ('1-7-8', 'SolidWorks', 'SolidWorks', '1-7', 1, 8, 1);
INSERT INTO `course_category` VALUES ('1-7-9', 'CorelDraw', 'CorelDraw', '1-7', 1, 9, 1);
INSERT INTO `course_category` VALUES ('1-8', '游戏开发', '游戏开发', '1', 1, 8, 0);
INSERT INTO `course_category` VALUES ('1-8-1', 'Cocos', 'Cocos', '1-8', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-8-2', 'Unity3D', 'Unity3D', '1-8', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-8-3', 'Flash', 'Flash', '1-8', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-8-4', 'SpriteKit 2D', 'SpriteKit 2D', '1-8', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-8-5', 'Unreal', 'Unreal', '1-8', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-8-6', '其它', '其它', '1-8', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-9', '智能硬件/物联网', '智能硬件/物联网', '1', 1, 9, 0);
INSERT INTO `course_category` VALUES ('1-9-1', '无线通信', '无线通信', '1-9', 1, 1, 1);
INSERT INTO `course_category` VALUES ('1-9-10', '物联网技术', '物联网技术', '1-9', 1, 10, 1);
INSERT INTO `course_category` VALUES ('1-9-11', '其它', '其它', '1-9', 1, 11, 1);
INSERT INTO `course_category` VALUES ('1-9-2', '电子工程', '电子工程', '1-9', 1, 2, 1);
INSERT INTO `course_category` VALUES ('1-9-3', 'Arduino', 'Arduino', '1-9', 1, 3, 1);
INSERT INTO `course_category` VALUES ('1-9-4', '体感技术', '体感技术', '1-9', 1, 4, 1);
INSERT INTO `course_category` VALUES ('1-9-5', '智能硬件', '智能硬件', '1-9', 1, 5, 1);
INSERT INTO `course_category` VALUES ('1-9-6', '驱动/内核开发', '驱动/内核开发', '1-9', 1, 6, 1);
INSERT INTO `course_category` VALUES ('1-9-7', '单片机/工控', '单片机/工控', '1-9', 1, 7, 1);
INSERT INTO `course_category` VALUES ('1-9-8', 'WinCE', 'WinCE', '1-9', 1, 8, 1);
INSERT INTO `course_category` VALUES ('1-9-9', '嵌入式', '嵌入式', '1-9', 1, 9, 1);

-- ----------------------------
-- Table structure for course_market
-- ----------------------------
DROP TABLE IF EXISTS `course_market`;
CREATE TABLE `course_market`  (
  `id` bigint(20) NOT NULL COMMENT '主键，课程id',
  `charge` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收费规则，对应数据字典',
  `price` float(10, 2) NULL DEFAULT NULL COMMENT '现价',
  `original_price` float(10, 2) NULL DEFAULT NULL COMMENT '原价',
  `qq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '咨询qq',
  `wechat` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `valid_days` int(11) NULL DEFAULT NULL COMMENT '有效期天数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程营销信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_market
-- ----------------------------
INSERT INTO `course_market` VALUES (6, '201001', 299.00, 2999.00, '299XXXXX99', 'XXXX_heima', '13311110000', 365);

-- ----------------------------
-- Table structure for course_publish
-- ----------------------------
DROP TABLE IF EXISTS `course_publish`;
CREATE TABLE `course_publish`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `company_id` bigint(20) NOT NULL COMMENT '机构ID',
  `company_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `users` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '适用人群',
  `tags` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `mt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大分类',
  `mt_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大分类名称',
  `st` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小分类',
  `st_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小分类名称',
  `grade` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程等级',
  `teachmode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教育模式',
  `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程图片',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程介绍',
  `market` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程营销信息，json格式',
  `teachplan` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '所有课程计划，json格式',
  `teachers` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '教师信息，json格式',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `online_date` datetime(0) NULL DEFAULT NULL COMMENT '上架时间',
  `offline_date` datetime(0) NULL DEFAULT NULL COMMENT '下架时间',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '发布状态',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `charge` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费规则，对应数据字典--203',
  `price` float(10, 2) NULL DEFAULT NULL COMMENT '现价',
  `original_price` float(10, 2) NULL DEFAULT NULL COMMENT '原价',
  `valid_days` int(11) NULL DEFAULT NULL COMMENT '课程有效期天数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程发布' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_publish
-- ----------------------------

-- ----------------------------
-- Table structure for course_publish_pre
-- ----------------------------
DROP TABLE IF EXISTS `course_publish_pre`;
CREATE TABLE `course_publish_pre`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `company_id` bigint(20) NOT NULL COMMENT '机构ID',
  `company_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `users` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '适用人群',
  `tags` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `mt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大分类',
  `mt_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大分类名称',
  `st` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小分类',
  `st_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小分类名称',
  `grade` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程等级',
  `teachmode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教育模式',
  `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程图片',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程介绍',
  `market` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程营销信息，json格式',
  `teachplan` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '所有课程计划，json格式',
  `teachers` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '教师信息，json格式',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
  `audit_date` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `charge` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费规则，对应数据字典--203',
  `price` float(10, 2) NULL DEFAULT NULL COMMENT '现价',
  `original_price` float(10, 2) NULL DEFAULT NULL COMMENT '原价',
  `valid_days` int(11) NULL DEFAULT NULL COMMENT '课程有效期天数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程发布' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_publish_pre
-- ----------------------------
INSERT INTO `course_publish_pre` VALUES (6, 1232141425, NULL, '学成在线', 'Java 初级/中级 开发工程是', 'Java企业级实战开发', NULL, '1-3', '编程开发', '1-3-2', 'Java', '204001', '200002', '/mediafiles/2023/2/12/a70348a565a37a3442b6ce60a577c427.jpg', '本课程以在线教育业务为基础，基于当前热门的 Spring Cloud 微服务技术栈进行设计，采用Nginx、SpringBoot、Spring Cloud、MyBatis-Plus、MQ、Redis、Elasticsearch等框架和中间件为基础进行开发，带领学员体验Java大型项目从需求分析、架构设计、编码、调试、测试的整个过程。', '{\"charge\":\"201001\",\"id\":6,\"originalPrice\":2999.0,\"phone\":\"13311110000\",\"price\":299.0,\"qq\":\"299XXXXX99\",\"validDays\":365,\"wechat\":\"XXXX_heima\"}', '[{\"courseId\":6,\"grade\":1,\"id\":1,\"orderby\":1,\"parentid\":0,\"pname\":\"day01 项目介绍 项目搭建\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":14,\"orderby\":1,\"parentid\":1,\"pname\":\"Day1-00.项目导学\",\"teachplanMedia\":{\"id\":53,\"mediaFilename\":\"Day1-00.项目导学.mp4\",\"mediaId\":\"e193d71ca57d173a47b32cd9b4d08592\"}},{\"courseId\":6,\"grade\":2,\"id\":15,\"orderby\":2,\"parentid\":1,\"pname\":\"Day1-01.项目介绍\",\"teachplanMedia\":{\"id\":54,\"mediaFilename\":\"Day1-01.项目介绍.mp4\",\"mediaId\":\"d8ab5d942a04d4edc03a3b62110550b2\"}},{\"courseId\":6,\"grade\":2,\"id\":16,\"orderby\":3,\"parentid\":1,\"pname\":\"Day1-02.面试-详细说说你的项目吧\",\"teachplanMedia\":{\"id\":55,\"mediaFilename\":\"Day1-02.面试-详细说说你的项目吧.mp4\",\"mediaId\":\"dbaa8c77f6ceed7ff14885a1ea3dea39\"}},{\"courseId\":6,\"grade\":2,\"id\":17,\"orderby\":4,\"parentid\":1,\"pname\":\"Day1-03.项目演示\",\"teachplanMedia\":{\"id\":56,\"mediaFilename\":\"Day1-03.项目演示.mp4\",\"mediaId\":\"34ca8d8a522ea64ce5200c423755a03e\"}},{\"courseId\":6,\"grade\":2,\"id\":18,\"orderby\":5,\"parentid\":1,\"pname\":\"Day1-04.项目技术架构介绍\",\"teachplanMedia\":{\"id\":57,\"mediaFilename\":\"Day1-04.项目技术架构介绍.mp4\",\"mediaId\":\"88e4aabbed40ec82a7532d108a30260b\"}},{\"courseId\":6,\"grade\":2,\"id\":19,\"orderby\":6,\"parentid\":1,\"pname\":\"Day1-05.开发环境配置-IDEA-虚拟机-Git-前端\",\"teachplanMedia\":{\"id\":58,\"mediaFilename\":\"Day1-05.开发环境配置-IDEA-虚拟机-Git-前端.mp4\",\"mediaId\":\"224a797b837aba5bcefcedd1de96802f\"}}]},{\"courseId\":6,\"grade\":1,\"id\":2,\"orderby\":2,\"parentid\":0,\"pname\":\"day02 课程查询 新增课程 前后端联调\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":20,\"orderby\":1,\"parentid\":2,\"pname\":\"Day2-01.课程查询-DAO接口\",\"teachplanMedia\":{\"id\":59,\"mediaFilename\":\"Day2-01.课程查询-DAO接口.mp4\",\"mediaId\":\"584cd42490bd199bc702610f36d5b4e3\"}},{\"courseId\":6,\"grade\":2,\"id\":21,\"orderby\":2,\"parentid\":2,\"pname\":\"Day2-02.数据字典表\",\"teachplanMedia\":{\"id\":60,\"mediaFilename\":\"Day2-02.数据字典表.mp4\",\"mediaId\":\"e00493393d836b75b5fb84813ec15f08\"}},{\"courseId\":6,\"grade\":2,\"id\":22,\"orderby\":3,\"parentid\":2,\"pname\":\"Day2-03.课程查询-service\",\"teachplanMedia\":{\"id\":61,\"mediaFilename\":\"Day2-03.课程查询-service.mp4\",\"mediaId\":\"ed694e924132a72e68ac00f023d083d9\"}}]},{\"courseId\":6,\"grade\":1,\"id\":3,\"orderby\":3,\"parentid\":0,\"pname\":\"day03 课程计划 异常处理 JSR303\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":23,\"orderby\":1,\"parentid\":3,\"pname\":\"Day3-01.异常处理-自定义异常类\",\"teachplanMedia\":{\"id\":62,\"mediaFilename\":\"Day3-01.异常处理-自定义异常类.mp4\",\"mediaId\":\"efe6f7779c9d0ce1ac4c5f80cf48d1eb\"}}]},{\"courseId\":6,\"grade\":1,\"id\":4,\"orderby\":4,\"parentid\":0,\"pname\":\"day04 媒资管理 Nacos Gateway MinIO\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":25,\"orderby\":1,\"parentid\":4,\"pname\":\"Day4-01.媒资管理模块需求分析\",\"teachplanMedia\":{\"id\":63,\"mediaFilename\":\"Day4-01.媒资管理模块需求分析.mp4\",\"mediaId\":\"4587c09aa23951690d5c94d55a8fd421\"}}]},{\"courseId\":6,\"grade\":1,\"id\":5,\"orderby\":5,\"parentid\":0,\"pname\":\"day05 上传文件 断点续传\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":24,\"orderby\":1,\"parentid\":5,\"pname\":\"Day5-01.上传图片优化-抽取代码\",\"teachplanMedia\":{\"id\":64,\"mediaFilename\":\"Day5-01.上传图片优化-抽取代码.mp4\",\"mediaId\":\"7d82500458e8e9c1f4d59b2f665f6970\"}}]},{\"courseId\":6,\"grade\":1,\"id\":6,\"orderby\":6,\"parentid\":0,\"pname\":\"day06 文件预览 视频处理 XXL-JOB\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":26,\"orderby\":1,\"parentid\":6,\"pname\":\"Day6-01.文件预览开发\",\"teachplanMedia\":{\"id\":65,\"mediaFilename\":\"Day6-01.文件预览开发.mp4\",\"mediaId\":\"3706e9fb287258674c264313eb7a171b\"}}]},{\"courseId\":6,\"grade\":1,\"id\":7,\"orderby\":7,\"parentid\":0,\"pname\":\"day07 课程预览 分布式事务 静态化\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":27,\"orderby\":1,\"parentid\":7,\"pname\":\"Day7-01.媒资绑定-接口开发\",\"teachplanMedia\":{\"id\":66,\"mediaFilename\":\"Day7-01.媒资绑定-接口开发.mp4\",\"mediaId\":\"987c36d05123abda1514e8f0e7cc0138\"}}]},{\"courseId\":6,\"grade\":1,\"id\":8,\"orderby\":8,\"parentid\":0,\"pname\":\"day08 课程发布 认证授权 SpringSecurity\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":28,\"orderby\":1,\"parentid\":8,\"pname\":\"Day8-01.课程发布-页面静态化\",\"teachplanMedia\":{\"id\":67,\"mediaFilename\":\"Day8-01.课程发布-页面静态化.mp4\",\"mediaId\":\"a4e1a25899ba30f417ae8561f42591a0\"}}]},{\"courseId\":6,\"grade\":1,\"id\":9,\"orderby\":9,\"parentid\":0,\"pname\":\"day09 用户认证 微信扫码登录 OAuth2 JWT\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":29,\"orderby\":1,\"parentid\":9,\"pname\":\"Day9-01.网关鉴权\",\"teachplanMedia\":{\"id\":68,\"mediaFilename\":\"Day9-01.网关鉴权.mp4\",\"mediaId\":\"e8f6582a26a4e82a867edb3286d1822e\"}}]},{\"courseId\":6,\"grade\":1,\"id\":10,\"orderby\":10,\"parentid\":0,\"pname\":\"day10 用户授权 选课学习\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":30,\"orderby\":1,\"parentid\":10,\"pname\":\"Day10-01.用户授权-什么的RBAC\",\"teachplanMedia\":{\"id\":69,\"mediaFilename\":\"Day10-01.用户授权-什么的RBAC.mp4\",\"mediaId\":\"f6bb1fe0a9f46d7e3eab2f1d77d5f705\"}}]},{\"courseId\":6,\"grade\":1,\"id\":11,\"orderby\":11,\"parentid\":0,\"pname\":\"day11 支付接口测试 生成支付二维码\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":31,\"orderby\":1,\"parentid\":11,\"pname\":\"Day11-01.支付-支付流程\",\"teachplanMedia\":{\"id\":70,\"mediaFilename\":\"Day11-01.支付-支付流程.mp4\",\"mediaId\":\"cdb7cb4b5e27fc11718405435da56ae1\"}}]},{\"courseId\":6,\"grade\":1,\"id\":12,\"orderby\":12,\"parentid\":0,\"pname\":\"day12 支付结果通知  在线学习 RabbitMQ\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":32,\"orderby\":1,\"parentid\":12,\"pname\":\"Day12-01.接收支付通知-接口定义\",\"teachplanMedia\":{\"id\":71,\"mediaFilename\":\"Day12-01.接收支付通知-接口定义.mp4\",\"mediaId\":\"d36b8ebb9a99bbdfb9855fb492f3afbf\"}}]},{\"courseId\":6,\"grade\":1,\"id\":13,\"orderby\":13,\"parentid\":0,\"pname\":\"day13 缓存优化 分布式锁 Redis\",\"teachPlanTreeNodes\":[{\"courseId\":6,\"grade\":2,\"id\":33,\"orderby\":1,\"parentid\":13,\"pname\":\"Day13-01.课程查询优化需求\",\"teachplanMedia\":{\"id\":72,\"mediaFilename\":\"Day13-01.课程查询优化需求.mp4\",\"mediaId\":\"dd82cc59308d4afaeebb1c3bbad8c91a\"}}]}]', NULL, '2023-02-12 19:41:15', NULL, '202003', NULL, '201001', 299.00, 2999.00, 365);

-- ----------------------------
-- Table structure for course_teacher
-- ----------------------------
DROP TABLE IF EXISTS `course_teacher`;
CREATE TABLE `course_teacher`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_id` bigint(20) NULL DEFAULT NULL COMMENT '课程标识',
  `teacher_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教师标识',
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教师职位',
  `introduction` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教师简介',
  `photograph` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '照片',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `courseid_teacherId_unique`(`course_id`, `teacher_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程-教师关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_teacher
-- ----------------------------
INSERT INTO `course_teacher` VALUES (24, 6, '苗老师', '项目经理', '15年研发经验，曾担任架构师、项目经理等职位，精通JavaEE技术栈，曾负责全国党员远程教育系统、云南学分银行系统、郑州地铁CCTV网管系统等项目的研发与管理工作。', '/mediafiles/2023/2/12/be58996735e21c091e0e76b324cd4236.jpg', NULL);

-- ----------------------------
-- Table structure for mq_message
-- ----------------------------
DROP TABLE IF EXISTS `mq_message`;
CREATE TABLE `mq_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `message_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息类型代码: course_publish ,  media_test',
  `business_key1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `business_key2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `business_key3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `execute_num` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '通知次数',
  `state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '处理状态，0:初始，1:成功',
  `returnfailure_date` datetime(0) NULL DEFAULT NULL COMMENT '回复失败时间',
  `returnsuccess_date` datetime(0) NULL DEFAULT NULL COMMENT '回复成功时间',
  `returnfailure_msg` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复失败内容',
  `execute_date` datetime(0) NULL DEFAULT NULL COMMENT '最近通知时间',
  `stage_state1` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '阶段1处理状态, 0:初始，1:成功',
  `stage_state2` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '阶段2处理状态, 0:初始，1:成功',
  `stage_state3` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '阶段3处理状态, 0:初始，1:成功',
  `stage_state4` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '阶段4处理状态, 0:初始，1:成功',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mq_message
-- ----------------------------

-- ----------------------------
-- Table structure for mq_message_history
-- ----------------------------
DROP TABLE IF EXISTS `mq_message_history`;
CREATE TABLE `mq_message_history`  (
  `id` bigint(20) NOT NULL COMMENT '消息id',
  `message_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息类型代码',
  `business_key1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `business_key2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `business_key3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
  `execute_num` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '通知次数',
  `state` int(10) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '处理状态，0:初始，1:成功，2:失败',
  `returnfailure_date` datetime(0) NULL DEFAULT NULL COMMENT '回复失败时间',
  `returnsuccess_date` datetime(0) NULL DEFAULT NULL COMMENT '回复成功时间',
  `returnfailure_msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复失败内容',
  `execute_date` datetime(0) NULL DEFAULT NULL COMMENT '最近通知时间',
  `stage_state1` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stage_state2` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stage_state3` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stage_state4` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mq_message_history
-- ----------------------------
INSERT INTO `mq_message_history` VALUES (12, 'course_publish', '2', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0');
INSERT INTO `mq_message_history` VALUES (13, 'course_publish', '2', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0');
INSERT INTO `mq_message_history` VALUES (14, 'course_publish', '2', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '1', '0', '0', '0');
INSERT INTO `mq_message_history` VALUES (15, 'course_publish', '117', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '1', '1', '0', '0');

-- ----------------------------
-- Table structure for teachplan
-- ----------------------------
DROP TABLE IF EXISTS `teachplan`;
CREATE TABLE `teachplan`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程计划名称',
  `parentid` bigint(20) NOT NULL COMMENT '课程计划父级Id',
  `grade` smallint(6) NOT NULL COMMENT '层级，分为1、2、3级',
  `media_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程类型:1视频、2文档',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始直播时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '直播结束时间',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '章节及课程时介绍',
  `timelength` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时长，单位时:分:秒',
  `orderby` int(11) NULL DEFAULT 0 COMMENT '排序字段',
  `course_id` bigint(20) NOT NULL COMMENT '课程标识',
  `course_pub_id` bigint(20) NULL DEFAULT NULL COMMENT '课程发布标识',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态（1正常  0删除）',
  `is_preview` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否支持试学或预览（试看）',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `change_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程计划' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachplan
-- ----------------------------
INSERT INTO `teachplan` VALUES (1, 'day01 项目介绍 项目搭建', 0, 1, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (2, 'day02 课程查询 新增课程 前后端联调', 0, 1, NULL, NULL, NULL, NULL, NULL, 2, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (3, 'day03 课程计划 异常处理 JSR303', 0, 1, NULL, NULL, NULL, NULL, NULL, 3, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (4, 'day04 媒资管理 Nacos Gateway MinIO', 0, 1, NULL, NULL, NULL, NULL, NULL, 4, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (5, 'day05 上传文件 断点续传', 0, 1, NULL, NULL, NULL, NULL, NULL, 5, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (6, 'day06 文件预览 视频处理 XXL-JOB', 0, 1, NULL, NULL, NULL, NULL, NULL, 6, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (7, 'day07 课程预览 分布式事务 静态化', 0, 1, NULL, NULL, NULL, NULL, NULL, 7, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (8, 'day08 课程发布 认证授权 SpringSecurity', 0, 1, NULL, NULL, NULL, NULL, NULL, 8, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (9, 'day09 用户认证 微信扫码登录 OAuth2 JWT', 0, 1, NULL, NULL, NULL, NULL, NULL, 9, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (10, 'day10 用户授权 选课学习', 0, 1, NULL, NULL, NULL, NULL, NULL, 10, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (11, 'day11 支付接口测试 生成支付二维码', 0, 1, NULL, NULL, NULL, NULL, NULL, 11, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (12, 'day12 支付结果通知  在线学习 RabbitMQ', 0, 1, NULL, NULL, NULL, NULL, NULL, 12, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (13, 'day13 缓存优化 分布式锁 Redis', 0, 1, NULL, NULL, NULL, NULL, NULL, 13, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (14, 'Day1-00.项目导学', 1, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '1', NULL, NULL);
INSERT INTO `teachplan` VALUES (15, 'Day1-01.项目介绍', 1, 2, NULL, NULL, NULL, NULL, NULL, 2, 6, NULL, 1, '1', NULL, NULL);
INSERT INTO `teachplan` VALUES (16, 'Day1-02.面试-详细说说你的项目吧', 1, 2, NULL, NULL, NULL, NULL, NULL, 3, 6, NULL, 1, '1', NULL, NULL);
INSERT INTO `teachplan` VALUES (17, 'Day1-03.项目演示', 1, 2, NULL, NULL, NULL, NULL, NULL, 4, 6, NULL, 1, '1', NULL, NULL);
INSERT INTO `teachplan` VALUES (18, 'Day1-04.项目技术架构介绍', 1, 2, NULL, NULL, NULL, NULL, NULL, 5, 6, NULL, 1, '1', NULL, NULL);
INSERT INTO `teachplan` VALUES (19, 'Day1-05.开发环境配置-IDEA-虚拟机-Git-前端', 1, 2, NULL, NULL, NULL, NULL, NULL, 6, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (20, 'Day2-01.课程查询-DAO接口', 2, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (21, 'Day2-02.数据字典表', 2, 2, NULL, NULL, NULL, NULL, NULL, 2, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (22, 'Day2-03.课程查询-service', 2, 2, NULL, NULL, NULL, NULL, NULL, 3, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (23, 'Day3-01.异常处理-自定义异常类', 3, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (24, 'Day5-01.上传图片优化-抽取代码', 5, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (25, 'Day4-01.媒资管理模块需求分析', 4, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (26, 'Day6-01.文件预览开发', 6, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (27, 'Day7-01.媒资绑定-接口开发', 7, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (28, 'Day8-01.课程发布-页面静态化', 8, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (29, 'Day9-01.网关鉴权', 9, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (30, 'Day10-01.用户授权-什么的RBAC', 10, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (31, 'Day11-01.支付-支付流程', 11, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (32, 'Day12-01.接收支付通知-接口定义', 12, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);
INSERT INTO `teachplan` VALUES (33, 'Day13-01.课程查询优化需求', 13, 2, NULL, NULL, NULL, NULL, NULL, 1, 6, NULL, 1, '0', NULL, NULL);

-- ----------------------------
-- Table structure for teachplan_media
-- ----------------------------
DROP TABLE IF EXISTS `teachplan_media`;
CREATE TABLE `teachplan_media`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `media_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '媒资文件id',
  `teachplan_id` bigint(20) NOT NULL COMMENT '课程计划标识',
  `course_id` bigint(20) NOT NULL COMMENT '课程标识',
  `media_fileName` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '媒资文件原始名称',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_people` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `change_people` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachplan_media
-- ----------------------------
INSERT INTO `teachplan_media` VALUES (53, 'e193d71ca57d173a47b32cd9b4d08592', 14, 6, 'Day1-00.项目导学.mp4', '2023-02-12 20:19:24', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (54, 'd8ab5d942a04d4edc03a3b62110550b2', 15, 6, 'Day1-01.项目介绍.mp4', '2023-02-12 20:19:34', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (55, 'dbaa8c77f6ceed7ff14885a1ea3dea39', 16, 6, 'Day1-02.面试-详细说说你的项目吧.mp4', '2023-02-12 20:19:42', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (56, '34ca8d8a522ea64ce5200c423755a03e', 17, 6, 'Day1-03.项目演示.mp4', '2023-02-12 20:19:51', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (57, '88e4aabbed40ec82a7532d108a30260b', 18, 6, 'Day1-04.项目技术架构介绍.mp4', '2023-02-12 20:20:00', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (58, '224a797b837aba5bcefcedd1de96802f', 19, 6, 'Day1-05.开发环境配置-IDEA-虚拟机-Git-前端.mp4', '2023-02-12 20:20:07', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (59, '584cd42490bd199bc702610f36d5b4e3', 20, 6, 'Day2-01.课程查询-DAO接口.mp4', '2023-02-12 20:20:16', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (60, 'e00493393d836b75b5fb84813ec15f08', 21, 6, 'Day2-02.数据字典表.mp4', '2023-02-12 20:20:24', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (61, 'ed694e924132a72e68ac00f023d083d9', 22, 6, 'Day2-03.课程查询-service.mp4', '2023-02-12 20:20:33', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (62, 'efe6f7779c9d0ce1ac4c5f80cf48d1eb', 23, 6, 'Day3-01.异常处理-自定义异常类.mp4', '2023-02-12 20:20:42', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (63, '4587c09aa23951690d5c94d55a8fd421', 25, 6, 'Day4-01.媒资管理模块需求分析.mp4', '2023-02-12 20:21:02', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (64, '7d82500458e8e9c1f4d59b2f665f6970', 24, 6, 'Day5-01.上传图片优化-抽取代码.mp4', '2023-02-12 20:21:21', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (65, '3706e9fb287258674c264313eb7a171b', 26, 6, 'Day6-01.文件预览开发.mp4', '2023-02-12 20:21:28', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (66, '987c36d05123abda1514e8f0e7cc0138', 27, 6, 'Day7-01.媒资绑定-接口开发.mp4', '2023-02-12 20:21:36', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (67, 'a4e1a25899ba30f417ae8561f42591a0', 28, 6, 'Day8-01.课程发布-页面静态化.mp4', '2023-02-12 20:21:45', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (68, 'e8f6582a26a4e82a867edb3286d1822e', 29, 6, 'Day9-01.网关鉴权.mp4', '2023-02-12 20:21:55', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (69, 'f6bb1fe0a9f46d7e3eab2f1d77d5f705', 30, 6, 'Day10-01.用户授权-什么的RBAC.mp4', '2023-02-12 20:22:03', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (70, 'cdb7cb4b5e27fc11718405435da56ae1', 31, 6, 'Day11-01.支付-支付流程.mp4', '2023-02-12 20:22:11', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (71, 'd36b8ebb9a99bbdfb9855fb492f3afbf', 32, 6, 'Day12-01.接收支付通知-接口定义.mp4', '2023-02-12 20:22:24', NULL, NULL);
INSERT INTO `teachplan_media` VALUES (72, 'dd82cc59308d4afaeebb1c3bbad8c91a', 33, 6, 'Day13-01.课程查询优化需求.mp4', '2023-02-12 20:22:37', NULL, NULL);

-- ----------------------------
-- Table structure for teachplan_work
-- ----------------------------
DROP TABLE IF EXISTS `teachplan_work`;
CREATE TABLE `teachplan_work`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `work_id` bigint(20) NOT NULL COMMENT '作业信息标识',
  `work_title` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作业标题',
  `teachplan_id` bigint(20) NOT NULL COMMENT '课程计划标识',
  `course_id` bigint(20) NULL DEFAULT NULL COMMENT '课程标识',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `course_pub_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachplan_work
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
