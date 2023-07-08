/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : analysis_sys_db

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 08/07/2023 22:49:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员的账号',
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员的密码',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理员的姓名',
  `level` tinyint NOT NULL COMMENT '管理员的权限级别',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像的url',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_admin_u`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'root', '$2a$10$da9/I8iUA4XSuTr9AV2fvuXySxm4NwV8jDAFdglWbXjTSompl/Tsq', '超级管理员', 1, 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/blob-1677669319148310528.png?Expires=1720444910&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=7aNkr6O7DVN42SjwFAOb3MUFHas%3D', '16680802519', '1966752024@qq.com');
INSERT INTO `admin` VALUES (5, 'admin1', '$2a$10$hY.sUFb0Qxxkj7K9SSWfUeKSETbQ.TOWD5B6fX47yZnB8AAc9jhGK', '用户1675168212180127745', 2, NULL, '15073548312', '3412732@qq.com');
INSERT INTO `admin` VALUES (6, 'admin2', '$2a$10$YRBJetHP0qbkkcdMyGKCxOo.hC5nnApWuRPRPq/3rLocy3dZM5A5S', '用户1675168284850638850', 2, NULL, '15073548316', '3412732@163.com');
INSERT INTO `admin` VALUES (7, 'admin3', '$2a$10$LHJuTeyzX1j1xXuMAyqHq.3pHpNyLvxcnlhXJ5NfgLMeO68uFOvwa', '用户1675168300772216834', 2, NULL, NULL, NULL);
INSERT INTO `admin` VALUES (11, 'hzx', '$2a$10$0DdzGYL3gx5BT2g84esALuHUDTbweBhAOmjHdC2z8HnzH2ZpD3cCi', '黄志祥', 2, NULL, NULL, NULL);
INSERT INTO `admin` VALUES (12, 'admin4', '$2a$10$vhUKXNvuUjDGJohCH64txOOsw2An/WbJQbbAoECaISTRnOO9z7/O2', '管理员4', 2, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for hot_work
-- ----------------------------
DROP TABLE IF EXISTS `hot_work`;
CREATE TABLE `hot_work`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '热点文化作品编号，主键，自增',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '热点文化作品名',
  `category` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文化作品的类别',
  `title` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '热点文化作品介绍标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '热点文化作品介绍详情',
  `citeUrl` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '热点文化作品介绍的网址',
  `imgUrl` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '热点文化作品介绍相关的图片url',
  `postTime` date NULL DEFAULT NULL COMMENT '热点文化作品介绍发布日期',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_hoot_workname`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hot_work
-- ----------------------------
INSERT INTO `hot_work` VALUES (1, '西游记', '影视', '西游记（电视剧）', '电视剧《西游记》，总长达25个小时，几乎包括了百回小说《西游记》里所有精彩篇章，塑造了众多色彩绚丽的屏幕形象。该片采用实景为主，内外景结合的拍摄方法。既有金碧辉煌的灵宵宝殿，祥云飘渺的瑶池仙境，金镶玉砌的东海龙宫等棚内场景，又有风光倚丽的园林妙景，名山绝胜以及扬名远近的寺刹道观。', 'https://tv.cctv.com/2012/12/03/VIDA1354534828865268.shtml', 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img5_2-1676058242249736192.jpg?Expires=1720060799&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=bDhXYZW8U0DSYZfVuWM9rcukKsg%3D', '2020-07-03');
INSERT INTO `hot_work` VALUES (2, '李子柒短视频', '影视', '李子柒刷新吉尼斯世界纪录', '吉尼斯世界纪录官方微博2月2日宣布，1月25日，中国短视频博主@李子柒 以1410万的YouTube订阅量刷新了由她创下的“最多订阅量的YouTube中文频道”的吉尼斯世界纪录称号。', 'https://language.chinadaily.com.cn/a/202102/03/WS601a13f1a31024ad0baa6fcb.html', 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img1_2-1676058324466483200.jpg?Expires=1720060819&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=lUVjR6Lsd1ffnqL4H77W%2FKh3ZJ4%3D', '2021-02-18');
INSERT INTO `hot_work` VALUES (3, '舌尖上的中国', '影视', '舌尖上的中国，味到深处即是家', '一个让人“痛并快乐着”的时间段，捧红了一部“垂涎欲滴”的纪录片。《舌尖上的中国》触动了无数人的味蕾，“大晚上的播这种片子真是虐心”；它也让无数网友欢欣鼓舞，无论是立意还是画面都堪称精品；它更激发了海外游子们思乡之情，“我的胃刚刚在求我回国”。透过屏幕，观众们仿佛嗅到了那股味道，那是历史的味道、时间的味道、家的味道。', 'http://news.cntv.cn/special/thinkagain/tastingchina/index.shtml', 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img2-1676056811530698752.jpeg?Expires=1720060458&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=DWNxAfRm0OBbToE1HrdmpGieItU%3D', '2019-07-17');
INSERT INTO `hot_work` VALUES (4, '流浪地球', '影视', '《流浪地球》影评：科幻希望，合格的里程碑', '春节假期过半，想必很多人的朋友圈都被这部大场面大制作的《流浪地球》霸屏了。\r\n\r\n尊重原著的设定，外加导演组对影片的理解和心意，让一切都恰到好处。不管是很早就喜欢上了刘慈欣作品的科幻迷，还是因为吴京等人而来的影迷，都证明了这是一部口碑颇高的影片。', 'https://zhuanlan.zhihu.com/p/56400500', 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img3-1676056955420491776.jpeg?Expires=1720060493&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=0i1DH%2FCcLVVMBFf8jIRmg7xM02s%3D', '2021-05-14');
INSERT INTO `hot_work` VALUES (5, '如果国宝会说话', '影视', '【评析】《如果国宝会说话》 让国宝文物“活”起来、“火”起来', '近日，中央电视台纪录频道推出大型系列纪录片《如果国宝会说话》。该片以传记体的方式，在每集五分钟的篇幅里分别讲述了一批国宝级文物的故事。这些国宝文物来自全国各大博物馆，其中很多都是镇馆之宝，它们身上所承载的各类信息，从不同角度和侧面记录、见证了中华文化的发展历程。节目在讲故事的过程中，用全新的视角和手法展现文物之美、阐释物质文物的精神内涵，让观众从中感受文物的审美价值、人文价值、艺术价值和文化价值。', 'https://mp.weixin.qq.com/s?__biz=MzI3NTE2NTQyNw==&mid=2650741673&idx=1&sn=faa99ac8e1aaa25a556fca4f0240fab9&chksm=f3034235c474cb23bab12b1c430fc2c707542d3a60ab45eca2c27999f60b5c93c17ce9d721dd&scene=27', 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img4-1676057065667772416.jpeg?Expires=1720060519&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=ggjlVdc4OAsbui8w41xHtu%2FlTmI%3D', '2020-03-03');
INSERT INTO `hot_work` VALUES (6, '大唐玄奘', '影视', '真实版的西游记《大唐玄奘》深度影评', '这是一部真实版《西游记》影片，讲述的是唐代高僧玄奘西行古印度取经的故事。影片基本上是以一个传记的结构，介绍了玄奘从洛阳到古印度的历程、玄奘在古印度学习佛经的情形以及玄奘带著657部佛经返回大唐所做出的贡献。', 'https://www.sohu.com/a/239722561_100127699', 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hotwork7_2-1676058407631142912.jpg?Expires=1720060839&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=Deen43A4uIojDAp4FYa0kwTSrs8%3D', '2022-02-09');
INSERT INTO `hot_work` VALUES (7, '哪吒之魔童降世', '影视', '《哪吒之魔童降世》广受好评，评分达8.6，高于《大圣归来》', '作为首部IMAX国产动画电影，电影《哪吒之魔童降世》这一次可谓是“来势汹汹”，很多观众在观看了电影之后，给出的评分都非常高。而在豆瓣评分上，大家也可以看到，《哪吒之魔童降世》已经达到了8.6分的高分，超过了此前口碑佳作《大圣归来》的8.3分。', 'https://mbd.baidu.com/newspage/data/landingsuper?wfr=spider&for=pc&third=baijiahao&baijiahao_id=1639469725056856491&id=1639469725056856491&c_source=kunlun&c_score=0.999100&p_tk=4902drLFl9%2BC8Qzac%2FeOFdV%2Bs9WddPkVUoEmhi7EF0KmDm8KNSWOiQrCIslUH%2BVS8mGemWbGR25zKI%2Brsb4qSeBbo6MyM4o5jpRxAH3y39saljz%2BBVSCkam%2FLxIvEq3BTzBvzPILRKyGcRAhH8W8ROcmJO2LjVj7yq87jrOx%2BK1alCs%3D&p_timestamp=1688295290&p_sign=4b9e11f1619621d6925ed9e9b78ee8f1&p_signature=a89e62e3644ef40aba7958b38dc0d6b1&__pc2ps_ab=4902drLFl9%2BC8Qzac%2FeOFdV%2Bs9WddPkVUoEmhi7EF0KmDm8KNSWOiQrCIslUH%2BVS8mGemWbGR25zKI%2Brsb4qSeBbo6MyM4o5jpRxAH3y39saljz%2BBVSCkam%2FLxIvEq3BTzBvzPILRKyGcRAhH8W8ROcmJO2LjVj7yq87jrOx%2BK1alCs%3D|1688295290|a89e62e3644ef40aba7958b38dc0d6b1|4b9e11f1619621d6925ed9e9b78ee8f1', 'http://hzx-oss.oss-cn-guangzhou.aliyuncs.com/images/hot_img6-1676058710812213248.jpeg?Expires=1720060911&OSSAccessKeyId=LTAI5tQcjbvbjA5JjMMkUkc1&Signature=uzh5MtXbv%2BQpKkuFXd22XRFnM2o%3D', '2021-11-11');

-- ----------------------------
-- Table structure for monitor_request
-- ----------------------------
DROP TABLE IF EXISTS `monitor_request`;
CREATE TABLE `monitor_request`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '监测请求ID，主键，自增',
  `userId` int NOT NULL COMMENT '用户的ID，外键',
  `workId` int NOT NULL COMMENT '监测作品的编号，外键',
  `createTime` datetime NOT NULL COMMENT '创建请求的时间',
  `checked` tinyint NULL DEFAULT NULL COMMENT '是否通过审核，0或1',
  `complete` tinyint NULL DEFAULT NULL COMMENT '是否完成监测，0或1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_monitor_req`(`userId`, `workId`) USING BTREE,
  INDEX `foreignkey_req_work`(`workId`) USING BTREE,
  INDEX `foreignkey_req_user`(`userId`) USING BTREE,
  CONSTRAINT `foreignkey_req_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `foreignkey_req_work` FOREIGN KEY (`workId`) REFERENCES `monitor_work` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of monitor_request
-- ----------------------------
INSERT INTO `monitor_request` VALUES (2, 3, 2, '2023-07-03 00:00:00', 0, 0);
INSERT INTO `monitor_request` VALUES (3, 4, 2, '2023-07-03 00:00:00', 0, 0);
INSERT INTO `monitor_request` VALUES (4, 4, 6, '2023-07-03 00:00:00', 0, 0);
INSERT INTO `monitor_request` VALUES (5, 4, 1, '2023-07-03 00:00:00', 0, 0);
INSERT INTO `monitor_request` VALUES (6, 4, 4, '2023-07-03 00:00:00', 0, 0);
INSERT INTO `monitor_request` VALUES (7, 4, 5, '2023-07-03 00:00:00', 0, 0);
INSERT INTO `monitor_request` VALUES (8, 4, 7, '2023-07-03 00:00:00', 0, 0);
INSERT INTO `monitor_request` VALUES (9, 22, 1, '2023-07-03 19:42:35', 0, 0);
INSERT INTO `monitor_request` VALUES (10, 22, 4, '2023-07-02 19:42:53', 0, 0);
INSERT INTO `monitor_request` VALUES (11, 22, 5, '2023-07-03 19:43:09', 0, 0);
INSERT INTO `monitor_request` VALUES (12, 22, 7, '2023-07-01 19:43:25', 0, 0);
INSERT INTO `monitor_request` VALUES (13, 22, 2, '2023-07-06 15:17:47', 0, 0);
INSERT INTO `monitor_request` VALUES (14, 4, 8, '2023-07-06 15:43:56', 0, 0);
INSERT INTO `monitor_request` VALUES (17, 4, 9, '2023-07-06 15:47:02', 0, 0);
INSERT INTO `monitor_request` VALUES (19, 48, 9, '2023-07-08 15:16:49', 0, 0);

-- ----------------------------
-- Table structure for monitor_work
-- ----------------------------
DROP TABLE IF EXISTS `monitor_work`;
CREATE TABLE `monitor_work`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '监测作品ID，主键，自增',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '监测作品的名称',
  `category` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文化作品的类别',
  `createTime` datetime NULL DEFAULT NULL COMMENT '监测作品的创建时间',
  `endTime` datetime NULL DEFAULT NULL COMMENT '完成作品监测的时间',
  `crawlOk` tinyint NULL DEFAULT NULL COMMENT '是否完成数据爬取，0或1',
  `sentimentOk` tinyint NULL DEFAULT NULL COMMENT '是否完成情感分析，0或1',
  `polarityOk` tinyint NULL DEFAULT NULL COMMENT '是否完成情感极性分析，0或1',
  `wordCloudOk` tinyint NULL DEFAULT NULL COMMENT '是否完成词云图分析，0或1',
  `gramNetOk` tinyint NULL DEFAULT NULL COMMENT '是否完成语义网络分析，0或1',
  `allDone` tinyint NULL DEFAULT NULL COMMENT '是否完成所有监测工作，0或1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_workname`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of monitor_work
-- ----------------------------
INSERT INTO `monitor_work` VALUES (1, '西游记', '影视', '2023-07-01 00:00:00', NULL, 0, 0, 0, 0, 0, 0);
INSERT INTO `monitor_work` VALUES (2, '流浪地球', '影视', '2023-07-01 00:00:00', NULL, 0, 0, 0, 0, 0, 0);
INSERT INTO `monitor_work` VALUES (4, '流浪地球2', '影视', '2023-07-02 19:41:52', NULL, 0, 0, 0, 0, 0, 0);
INSERT INTO `monitor_work` VALUES (5, '李子柒短视频', '影视', '2023-07-02 19:45:32', NULL, 0, 0, 0, 0, 0, 0);
INSERT INTO `monitor_work` VALUES (6, '敢教日月换新天', '影视', '2023-07-03 02:57:02', NULL, 0, 0, 0, 0, 0, 0);
INSERT INTO `monitor_work` VALUES (7, '三体英文版(Death\'s End)', '书籍', '2023-07-03 15:43:43', NULL, 0, 0, 0, 0, 0, 0);
INSERT INTO `monitor_work` VALUES (8, '红楼梦', '书籍', '2023-07-06 15:43:56', NULL, 0, 0, 0, 0, 0, 0);
INSERT INTO `monitor_work` VALUES (9, '三国演义', '书籍', '2023-07-06 15:47:02', NULL, 0, 0, 0, 0, 0, 0);

-- ----------------------------
-- Table structure for polarity_analy
-- ----------------------------
DROP TABLE IF EXISTS `polarity_analy`;
CREATE TABLE `polarity_analy`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '分析编号，主键，自增',
  `workId` int NOT NULL COMMENT '所属监测作品的编号，外键',
  `country` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '情感极性分析的评论所属国家',
  `time` date NOT NULL COMMENT '情感极性分析的评论发布时间',
  `platform` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '情感极性分析评论来自的平台',
  `positive` int NULL DEFAULT NULL COMMENT '积极的评论数',
  `negative` int NULL DEFAULT NULL COMMENT '消极的评论数',
  `neutrality` int NULL DEFAULT NULL COMMENT '中立的评论数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_polarityrecord`(`workId`, `country`, `time`, `platform`) USING BTREE,
  INDEX `foreignkey_polarity_work`(`workId`) USING BTREE,
  CONSTRAINT `foreignkey_polarity_work` FOREIGN KEY (`workId`) REFERENCES `monitor_work` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of polarity_analy
-- ----------------------------
INSERT INTO `polarity_analy` VALUES (3, 2, '英国', '2023-02-03', 'Youtube', 40, 50, 30);
INSERT INTO `polarity_analy` VALUES (4, 1, '美国', '2023-07-01', 'Twitter', 46, 18, 15);
INSERT INTO `polarity_analy` VALUES (5, 1, '美国', '2023-07-01', 'Instagram', 65, 19, 24);
INSERT INTO `polarity_analy` VALUES (7, 1, '美国', '2023-07-02', 'Twitter', 45, 26, 18);
INSERT INTO `polarity_analy` VALUES (12, 4, '美国', '2023-06-30', 'Twitter', 46, 18, 15);
INSERT INTO `polarity_analy` VALUES (13, 4, '美国', '2023-06-30', 'Instagram', 40, 19, 24);
INSERT INTO `polarity_analy` VALUES (14, 4, '美国', '2023-06-30', 'Youtube', 48, 37, 14);
INSERT INTO `polarity_analy` VALUES (15, 4, '美国', '2023-07-01', 'Twitter', 45, 26, 18);
INSERT INTO `polarity_analy` VALUES (19, 4, '美国', '2023-07-01', 'Instagram', 50, 20, 15);
INSERT INTO `polarity_analy` VALUES (20, 4, '英国', '2023-07-01', 'Youtube', 30, 20, 14);
INSERT INTO `polarity_analy` VALUES (21, 4, '英国', '2023-06-30', 'Twitter', 57, 34, 16);
INSERT INTO `polarity_analy` VALUES (22, 4, '英国', '2023-06-30', 'Instagram', 45, 26, 24);
INSERT INTO `polarity_analy` VALUES (23, 4, '英国', '2023-06-30', 'Youtube', 47, 20, 14);
INSERT INTO `polarity_analy` VALUES (24, 4, '英国', '2023-07-01', 'Twitter', 57, 26, 17);
INSERT INTO `polarity_analy` VALUES (25, 4, '英国', '2023-07-02', 'Youtube', 46, 24, 15);
INSERT INTO `polarity_analy` VALUES (26, 4, '英国', '2023-07-03', 'Youtube', 50, 20, 15);
INSERT INTO `polarity_analy` VALUES (27, 4, '英国', '2023-07-04', 'Youtube', 60, 26, 18);
INSERT INTO `polarity_analy` VALUES (28, 4, '英国', '2023-07-01', 'Instagram', 50, 20, 15);
INSERT INTO `polarity_analy` VALUES (29, 4, '美国', '2023-06-26', 'YouTube', 50, 20, 15);
INSERT INTO `polarity_analy` VALUES (30, 4, '美国', '2023-06-28', 'YouTube', 30, 10, 15);
INSERT INTO `polarity_analy` VALUES (31, 4, '美国', '2023-06-29', 'YouTube', 75, 35, 10);
INSERT INTO `polarity_analy` VALUES (32, 1, '美国', '2023-06-28', 'YouTube', 45, 30, 15);
INSERT INTO `polarity_analy` VALUES (33, 1, '美国', '2023-06-29', 'YouTube', 40, 25, 15);
INSERT INTO `polarity_analy` VALUES (34, 4, '美国', '2023-07-02', 'YouTube', 80, 36, 15);
INSERT INTO `polarity_analy` VALUES (35, 4, '美国', '2023-07-03', 'YouTube', 70, 40, 15);
INSERT INTO `polarity_analy` VALUES (36, 4, '美国', '2023-07-04', 'YouTube', 75, 10, 10);
INSERT INTO `polarity_analy` VALUES (37, 2, '美国', '2023-06-30', 'YouTube', 85, 15, 15);
INSERT INTO `polarity_analy` VALUES (38, 2, '美国', '2023-07-01', 'YouTube', 55, 27, 15);
INSERT INTO `polarity_analy` VALUES (39, 4, '美国', '2023-07-01', 'YouTube', 45, 12, 15);
INSERT INTO `polarity_analy` VALUES (40, 1, '美国', '2023-07-01', 'YouTube', 60, 16, 15);
INSERT INTO `polarity_analy` VALUES (41, 1, '美国', '2023-07-02', 'YouTube', 70, 17, 15);
INSERT INTO `polarity_analy` VALUES (42, 1, '美国', '2023-07-03', 'YouTube', 75, 15, 15);
INSERT INTO `polarity_analy` VALUES (43, 1, '美国', '2023-07-04', 'YouTube', 30, 18, 15);
INSERT INTO `polarity_analy` VALUES (44, 2, '美国', '2023-06-28', 'YouTube', 35, 35, 15);
INSERT INTO `polarity_analy` VALUES (45, 2, '美国', '2023-06-29', 'YouTube', 87, 45, 15);
INSERT INTO `polarity_analy` VALUES (46, 2, '美国', '2023-07-02', 'YouTube', 48, 28, 15);
INSERT INTO `polarity_analy` VALUES (47, 2, '美国', '2023-07-03', 'YouTube', 78, 20, 15);
INSERT INTO `polarity_analy` VALUES (48, 2, '美国', '2023-07-04', 'YouTube', 65, 25, 10);
INSERT INTO `polarity_analy` VALUES (49, 5, '美国', '2023-06-28', 'YouTube', 60, 20, 15);
INSERT INTO `polarity_analy` VALUES (50, 5, '美国', '2023-06-29', 'YouTube', 70, 30, 15);
INSERT INTO `polarity_analy` VALUES (51, 5, '美国', '2023-06-30', 'YouTube', 75, 35, 15);
INSERT INTO `polarity_analy` VALUES (52, 5, '美国', '2023-07-01', 'YouTube', 55, 28, 15);
INSERT INTO `polarity_analy` VALUES (53, 5, '美国', '2023-07-02', 'YouTube', 60, 15, 15);
INSERT INTO `polarity_analy` VALUES (54, 5, '美国', '2023-07-03', 'YouTube', 50, 30, 15);
INSERT INTO `polarity_analy` VALUES (55, 5, '美国', '2023-07-04', 'YouTube', 72, 40, 15);
INSERT INTO `polarity_analy` VALUES (56, 1, '美国', '2023-06-30', 'Youtube', 60, 34, 28);

-- ----------------------------
-- Table structure for raw_comment
-- ----------------------------
DROP TABLE IF EXISTS `raw_comment`;
CREATE TABLE `raw_comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评论编号，主键，自增',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论的内容',
  `likes` int NULL DEFAULT NULL COMMENT '评论的点赞数',
  `workId` int NOT NULL COMMENT '所属监测作品的编号，外键',
  `sentiment` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的情感倾向',
  `country` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论所属的国家',
  `platform` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论所在的平台',
  `postTime` date NOT NULL COMMENT '评论的发布时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `foreignkey_work`(`workId`) USING BTREE,
  CONSTRAINT `foreignkey_work` FOREIGN KEY (`workId`) REFERENCES `monitor_work` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of raw_comment
-- ----------------------------
INSERT INTO `raw_comment` VALUES (1, '有人像我一样2023 还在刷《西游记》 的嘛', 106, 1, '积极', '中国', 'youtube', '2023-07-04');
INSERT INTO `raw_comment` VALUES (2, '兒時的回憶。心中得永恆經典。再怎麼翻拍都無法超越這部西遊記', 156, 1, '积极', '美国', 'youtube', '2023-07-01');
INSERT INTO `raw_comment` VALUES (3, '无法超越的经典之作！那个年代也没有什么特效，能拍到这个水准，非常了不起啊！陪伴了多少人的成长啊？永远的回忆！👍👍👍', 87, 1, '积极', '美国', 'youtube', '2023-07-01');
INSERT INTO `raw_comment` VALUES (4, '这是西游记最美好的一集，就像一个人的童年一样，美好得让人想哭。 \r\n 师傅让悟空不要再来找他，没有这个徒弟。猴哥后来闯了那么多的祸，从来没有提起过自己的师傅。\r\n直到后来把人参树推倒，猴哥确实扛不住了，来找师傅，那一幕让人心酸至极：\r\n就像一个孩子去找自己的父亲。', 67, 1, NULL, '英国', 'youtube', '2021-01-01');
INSERT INTO `raw_comment` VALUES (5, 'This movie is associated with my childhood. Every time I listen to the song in the movie, I am very touched, my childhood seems to come back.', 75, 1, NULL, '英国', 'youtube', '2023-06-01');
INSERT INTO `raw_comment` VALUES (6, 'With its completion with Death’s End, I can now say that the Remembrance of Earth’s Past is my all-time favorite science fiction series (says the noob of a sci fi fan). It opens just like you would expect the final volume of an insanely ambitious hard science fiction series to open, with a magician offering to help the emperor prevent the Fall of Constantinople in 1453. Wait, what? This has never been a series interested in hewing to convention. And so we get a story spanning a few million years (specifically, 1453 – 18906416).\r\n\r\n“Once, ancient Romans had whistled in their grand, magnificent baths, thinking that their empire, like the granite that made up the walls of the pools in which they floated, would last forever. No banquet was eternal. Everything had an end. Everything.”', 120, 7, NULL, '美国', 'amazon', '2017-02-15');
INSERT INTO `raw_comment` VALUES (7, 'DEATH\'S END brings popular Chinese science fiction author\'s Remembrance of Earth\'s Past trilogy (begun with Hugo winner THE THREE BODY PROBLEM) to a rousing, fulfilling, and moving conclusion. It is a story that spans millions of years and multiple universes. It is strange, wonderful, full of ideas, and thought provoking. It is grand in scope and despite that, personal in nature. It is quite possibly the best science fiction book of 2016, which was full of science fiction novels that could claim that title, as this year\'s Best Novel Hugo finalist list attests to. It deserves all those superlatives and more.', 24, 7, NULL, '美国', 'amazon', '2017-07-16');
INSERT INTO `raw_comment` VALUES (8, 'I enjoyed this sci-fi series immensely. This third book Death’s End is not simple reading, but the effort is extremely worthwhile. This book ties up many questions and loose ends I had from reading the first two books, and I want to go back and reread them in order to fill in the gaps from my understanding of the story. I’ve enjoyed so many of the classic sci fi books and would add this to the list of masterpieces. Though I couldn’t possibly follow the physics involved, I was able to imagine the multi dimensional space scenes and the dark forest theory made perfect sense. The translation is impeccable as it felt like I was reading books written in English. I highly recommend this book and the entire series to any sci-fi lover.I’d love to watch a trilogy of films or tv series created for these books.', 45, 7, NULL, '日本', 'amazon', '2019-07-03');
INSERT INTO `raw_comment` VALUES (9, 'Arrivato al termine dell\'opera, mi guardo indietro e apprezzo il grande progetto di Cixin Liu: offrire una visione non banale dell\'incontro un\'altra civiltà e della conoscenza dell\'universo che imporrebbe scaturire. La visione proposta non è ottimistica ed è originale il modo in cui si propone la tesi della responsabilità individuale a contribuire ad un diverso corso degli eventi. Anche qui Cixin Più indulge in lunghe descrizioni e racchiude momenti chiave in poche pagine. Gli ultimi capitoli sono ricchi di spunti di riflessione e di idee sull\'uso di diverse tecnologie in contesti differenti. Spero che presto qualcuno scriva un libro su \"la fisica di Three Body Problem\" .', 67, 7, NULL, '意大利', '烂番茄', '2020-07-03');
INSERT INTO `raw_comment` VALUES (10, 'Many years ago, I decided that I would no longer read new book series until all the books were published. I became tired of reading something I really liked and then being forced to wait years for the follow-up when the original story had started to fade. This may be the first time that I wished I wouldn’t have waited so long to read something. I can say without reservation that this trilogy is the best science fiction I have read in at least a decade. Granted, I don’t read as much science fiction as I used to. Still, I would put this trilogy on the shelf next to Asimov’s original Foundation trilogy and not feel a bit of embarrassment. (And I worshipped Asimov when I was a teenager.)', 103, 7, NULL, '英国', '烂番茄', '2020-06-03');
INSERT INTO `raw_comment` VALUES (11, 'This was a decent conclusion to the original trilogy. Probably my least favorite of the three but still really enjoy it such a great sci fy series. Looking forward to what book 4 has in store since three wrapped things up nicely.', 30, 7, NULL, '法国', '烂番茄', '2023-05-03');
INSERT INTO `raw_comment` VALUES (12, 'Blown away doesnt cover it. The journey from the first book to here is breathtaking, sobering, enlightening, and terrifying all at once. I could argue and pontificate about this book, this trilogy as a whole, but instead I\'ll mention what I think is the most important aspect of this series that exceeds many others: it makes you think in ways many of us have never considered, in scale and scope.\r\n\r\nThe most valuable gift of reading these books are the discussions sure to follow.', 46, 7, NULL, '英国', '好读', '2020-07-03');
INSERT INTO `raw_comment` VALUES (13, 'This is actually a review for the entire trilogy of Remembrance of Earth\'s Past. I found the series both interesting and frustrating, but ultimately a letdown.\r\n\r\nThe first two books appealed to me as smart physics puzzles and sociological studies with some political thriller mixed in: technologically advanced aliens have to leave their unstable solar system; when they intercept a SETI-style message from Earth, they dispatch a fleet to take over the planet. The fleet will arrive in 400 years (due to distance and lack of warp-drive ships). The author has a great time investigating various physics-related angles of technological development (unfolding higher dimensions into lower ones, artificial intelligence, propulsion systems for spacecraft, etc.) plus sociological and psychological effects (how humans react to a certain but distant threat, the existence of other intelligences, conflict within human society compared to an external threat, and so on).', 55, 7, NULL, '日本', '好读', '2022-07-03');
INSERT INTO `raw_comment` VALUES (14, 'I don’t even know where to begin. This novel just melts your brain in so many ways. I feel so so so insignificant but also feel incredibly attuned to my small piece of the universe.', 68, 7, NULL, '法国', '好读', '2021-07-03');
INSERT INTO `raw_comment` VALUES (15, 'Death’s End is the concluding volume of Cixin Liu’s Remembrance of Earth’s Past trilogy. If you haven’t read the first two books, don’t begin here. But definitely begin, because this is the most magnificent science fiction work I have ever read, and I have read almost all of them.\r\n\r\nThe first book in the trilogy, The Three Body Problem, was a very nice work, but ultimately the least engaging of the three. The Dark Forest was the best book I’ve ever read, of any genre. It contained elements of anthropology, sociology, philosophy and outstanding hard science fiction concepts and scenarios. It was simply magnificent, and ended in such a manner that a third book was not necessarily required.\r\n', 77, 7, NULL, '法国', '书评汇总', '2020-06-03');
INSERT INTO `raw_comment` VALUES (16, 'Science fiction by a Chinese writer is new to me and his perspective on humanity and society are interesting. The science in the novel is plausible and contains quite a few possibilities that I never thought of.\r\nA well written and interesting piece of sci_fi.', 25, 7, NULL, '英国', '书评汇总', '2023-02-03');
INSERT INTO `raw_comment` VALUES (17, 'The final chapter in one of the best sci fi trilogies I have read in recent memory. If you\'re read the other two of course you\'re going to read this one and it delivers in every way. Better than book two in my opinion, and a strong finale to a great series.', 49, 7, NULL, '美国', '书评汇总', '2023-02-03');
INSERT INTO `raw_comment` VALUES (18, 'I really enjoyed the first book and thought the second was an awesome expansion on all the ideas raised by the central premise: What if we found out that we\'d be wiped out by aliens in 400 years? How would society change? What might happen? Liu\'s scientific animation of these ideas is always fascinating, even if his more social/political prescriptions aren\'t as convincing (case in point - how amazingly effective the UN is in all these books). However, I found that by this third entry, things really do drag on.', 35, 7, NULL, '美国', 'amazon', '2023-02-03');
INSERT INTO `raw_comment` VALUES (19, 'I almost gave up reading the first book due to the slow build, unfamiliar writing style and difficult to remember character names for my Western tongue. But wow, the third book took this saga into epic mind blowing territory and now I see why this won the Hugo award. The author has built and visualized the operation of the universe, multiple universes and in multiple dimensions. Most sci-fi focuses on alien cultures and invents interesting science to make it interesting, this book focuses almost entirely on the fundamental mechanics of the universe which is far more difficult to do but Cixin Liu created a masterpiece.', 15, 7, NULL, '日本', 'amazon', '2020-07-03');
INSERT INTO `raw_comment` VALUES (20, 'This book is the paramount of Sci Fi of this era. Not only is the author skilled in science but is amazingly skilled also in literature. I literally lost sleep reading and thinking about this series. No other sci fi on the market compares. Chixin Liu is a modern Asimov.', 25, 7, NULL, '英国', '好读', '2021-07-03');

-- ----------------------------
-- Table structure for sentiment_analy
-- ----------------------------
DROP TABLE IF EXISTS `sentiment_analy`;
CREATE TABLE `sentiment_analy`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '情感分析编号，主键，自增',
  `workId` int NOT NULL COMMENT '所属监测作品的编号，外键',
  `country` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '情感分析的评论所属国家',
  `time` date NOT NULL COMMENT '情感分析的评论发布时间',
  `platform` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '情感分析评论来自的平台',
  `happy` int NULL DEFAULT NULL COMMENT '开心的评论数',
  `amazed` int NULL DEFAULT NULL COMMENT '惊讶的评论数',
  `neutrality` int NULL DEFAULT NULL COMMENT '中立的评论数',
  `hate` int NULL DEFAULT NULL COMMENT '厌恶的评论数',
  `angry` int NULL DEFAULT NULL COMMENT '愤怒的评论数',
  `fear` int NULL DEFAULT NULL COMMENT '恐惧的评论数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_key_senti`(`workId`, `country`, `time`, `platform`) USING BTREE,
  INDEX `foreignkey_senti_work`(`workId`) USING BTREE,
  CONSTRAINT `foreignkey_senti_work` FOREIGN KEY (`workId`) REFERENCES `monitor_work` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sentiment_analy
-- ----------------------------
INSERT INTO `sentiment_analy` VALUES (1, 1, '美国', '2023-07-01', 'Youtube', 25, 21, 8, 6, 3, 2);
INSERT INTO `sentiment_analy` VALUES (2, 1, '英国', '2023-07-01', 'Youtube', 40, 33, 17, 7, 5, 3);
INSERT INTO `sentiment_analy` VALUES (4, 1, '法国', '2023-07-01', 'Youtube', 30, 16, 20, 5, 3, 2);
INSERT INTO `sentiment_analy` VALUES (5, 1, '美国', '2023-07-01', 'Twitter', 40, 36, 18, 7, 4, 4);
INSERT INTO `sentiment_analy` VALUES (7, 1, '美国', '2023-07-01', 'Instagram', 45, 36, 16, 4, 3, 3);
INSERT INTO `sentiment_analy` VALUES (8, 1, '美国', '2023-07-02', 'Youtube', 56, 30, 20, 8, 4, 3);
INSERT INTO `sentiment_analy` VALUES (9, 1, '美国', '2023-07-02', 'Twitter', 30, 26, 10, 4, 3, 1);
INSERT INTO `sentiment_analy` VALUES (10, 1, '美国', '2023-07-02', 'Instagram', 60, 36, 16, 7, 5, 4);
INSERT INTO `sentiment_analy` VALUES (11, 1, '英国', '2023-06-30', 'Twitter', 44, 36, 18, 7, 4, 4);
INSERT INTO `sentiment_analy` VALUES (12, 1, '英国', '2023-06-30', 'Instagram', 36, 36, 28, 4, 3, 3);
INSERT INTO `sentiment_analy` VALUES (19, 1, '英国', '2023-07-01', 'Twitter', 44, 36, 18, 7, 4, 4);
INSERT INTO `sentiment_analy` VALUES (20, 1, '英国', '2023-07-01', 'Instagram', 36, 36, 28, 4, 3, 3);
INSERT INTO `sentiment_analy` VALUES (21, 1, '英国', '2023-07-02', 'Youtube', 48, 30, 20, 8, 4, 3);
INSERT INTO `sentiment_analy` VALUES (22, 1, '英国', '2023-07-02', 'Twitter', 58, 26, 16, 6, 6, 1);
INSERT INTO `sentiment_analy` VALUES (23, 1, '英国', '2023-07-02', 'Instagram', 49, 34, 17, 4, 5, 4);
INSERT INTO `sentiment_analy` VALUES (24, 1, '英国', '2023-07-03', 'Youtube', 67, 30, 20, 10, 6, 5);
INSERT INTO `sentiment_analy` VALUES (25, 1, '英国', '2023-07-03', 'Twitter', 45, 26, 15, 4, 3, 1);
INSERT INTO `sentiment_analy` VALUES (26, 1, '英国', '2023-07-03', 'Instagram', 54, 36, 16, 7, 5, 4);
INSERT INTO `sentiment_analy` VALUES (27, 1, '法国', '2023-07-01', 'Twitter', 44, 36, 18, 7, 6, 4);
INSERT INTO `sentiment_analy` VALUES (28, 1, '法国', '2023-07-01', 'Instagram', 36, 36, 28, 7, 5, 3);
INSERT INTO `sentiment_analy` VALUES (29, 1, '法国', '2023-07-02', 'Youtube', 46, 30, 20, 8, 9, 5);
INSERT INTO `sentiment_analy` VALUES (30, 1, '法国', '2023-07-02', 'Twitter', 58, 26, 16, 12, 3, 1);
INSERT INTO `sentiment_analy` VALUES (31, 1, '法国', '2023-07-02', 'Instagram', 38, 34, 17, 6, 5, 4);
INSERT INTO `sentiment_analy` VALUES (32, 1, '法国', '2023-07-03', 'Youtube', 58, 30, 20, 8, 2, 5);
INSERT INTO `sentiment_analy` VALUES (33, 1, '法国', '2023-07-03', 'Twitter', 53, 26, 24, 16, 4, 1);
INSERT INTO `sentiment_analy` VALUES (34, 1, '法国', '2023-07-03', 'Instagram', 54, 36, 16, 12, 5, 4);
INSERT INTO `sentiment_analy` VALUES (35, 4, '英国', '2023-07-01', 'Youtube', 48, 35, 16, 8, 6, 2);
INSERT INTO `sentiment_analy` VALUES (36, 4, '法国', '2023-07-01', 'Youtube', 46, 32, 15, 6, 3, 2);
INSERT INTO `sentiment_analy` VALUES (37, 4, '英国', '2023-07-01', 'Instagram', 44, 36, 18, 7, 4, 4);
INSERT INTO `sentiment_analy` VALUES (38, 4, '英国', '2023-07-01', 'Twitter', 36, 36, 28, 4, 3, 3);
INSERT INTO `sentiment_analy` VALUES (39, 4, '英国', '2023-07-02', 'Youtube', 48, 30, 20, 8, 4, 3);
INSERT INTO `sentiment_analy` VALUES (40, 4, '英国', '2023-07-02', 'Instagram', 58, 26, 16, 6, 6, 1);
INSERT INTO `sentiment_analy` VALUES (41, 4, '英国', '2023-07-02', 'Twitter', 49, 34, 17, 4, 5, 4);
INSERT INTO `sentiment_analy` VALUES (42, 4, '英国', '2023-07-03', 'Youtube', 67, 30, 20, 10, 6, 5);
INSERT INTO `sentiment_analy` VALUES (43, 4, '英国', '2023-07-03', 'Instagram', 45, 26, 15, 5, 3, 1);
INSERT INTO `sentiment_analy` VALUES (44, 4, '英国', '2023-07-03', 'Twitter', 54, 36, 16, 7, 5, 2);
INSERT INTO `sentiment_analy` VALUES (45, 4, '法国', '2023-07-01', 'Instagram', 44, 43, 18, 5, 5, 1);
INSERT INTO `sentiment_analy` VALUES (46, 4, '法国', '2023-07-01', 'Twitter', 38, 36, 23, 5, 8, 2);
INSERT INTO `sentiment_analy` VALUES (47, 4, '法国', '2023-07-02', 'Youtube', 44, 43, 26, 8, 9, 2);
INSERT INTO `sentiment_analy` VALUES (48, 4, '法国', '2023-07-02', 'Instagram', 54, 26, 18, 8, 9, 1);
INSERT INTO `sentiment_analy` VALUES (49, 4, '法国', '2023-07-02', 'Twitter', 43, 36, 17, 5, 5, 4);
INSERT INTO `sentiment_analy` VALUES (50, 4, '法国', '2023-07-03', 'Youtube', 45, 30, 20, 8, 4, 2);
INSERT INTO `sentiment_analy` VALUES (51, 4, '法国', '2023-07-03', 'Instagram', 53, 34, 26, 9, 6, 1);
INSERT INTO `sentiment_analy` VALUES (52, 4, '法国', '2023-07-03', 'Twitter', 46, 36, 16, 6, 5, 4);
INSERT INTO `sentiment_analy` VALUES (53, 5, '英国', '2023-07-01', 'Youtube', 48, 35, 16, 8, 6, 2);
INSERT INTO `sentiment_analy` VALUES (54, 5, '法国', '2023-07-01', 'Youtube', 46, 32, 15, 6, 3, 2);
INSERT INTO `sentiment_analy` VALUES (55, 5, '英国', '2023-07-01', 'Instagram', 44, 36, 18, 7, 4, 4);
INSERT INTO `sentiment_analy` VALUES (56, 5, '英国', '2023-07-01', 'Twitter', 36, 36, 28, 4, 3, 3);
INSERT INTO `sentiment_analy` VALUES (57, 5, '英国', '2023-07-02', 'Youtube', 48, 30, 20, 8, 4, 3);
INSERT INTO `sentiment_analy` VALUES (58, 5, '英国', '2023-07-02', 'Instagram', 58, 26, 16, 6, 6, 1);
INSERT INTO `sentiment_analy` VALUES (59, 5, '英国', '2023-07-02', 'Twitter', 49, 34, 17, 4, 5, 4);
INSERT INTO `sentiment_analy` VALUES (60, 5, '英国', '2023-07-03', 'Youtube', 67, 30, 20, 10, 6, 5);
INSERT INTO `sentiment_analy` VALUES (61, 5, '英国', '2023-07-03', 'Instagram', 45, 26, 15, 5, 3, 1);
INSERT INTO `sentiment_analy` VALUES (62, 5, '英国', '2023-07-03', 'Twitter', 54, 36, 16, 7, 5, 2);
INSERT INTO `sentiment_analy` VALUES (63, 5, '法国', '2023-07-01', 'Instagram', 44, 43, 18, 5, 5, 1);
INSERT INTO `sentiment_analy` VALUES (64, 5, '法国', '2023-07-01', 'Twitter', 38, 36, 23, 5, 8, 2);
INSERT INTO `sentiment_analy` VALUES (65, 5, '法国', '2023-07-02', 'Youtube', 44, 43, 26, 8, 9, 2);
INSERT INTO `sentiment_analy` VALUES (66, 5, '法国', '2023-07-02', 'Instagram', 54, 26, 18, 8, 9, 1);
INSERT INTO `sentiment_analy` VALUES (67, 5, '法国', '2023-07-02', 'Twitter', 43, 36, 17, 5, 5, 4);
INSERT INTO `sentiment_analy` VALUES (68, 5, '法国', '2023-07-03', 'Youtube', 45, 30, 20, 8, 5, 2);
INSERT INTO `sentiment_analy` VALUES (69, 5, '法国', '2023-07-03', 'Instagram', 53, 34, 26, 5, 6, 1);
INSERT INTO `sentiment_analy` VALUES (70, 5, '法国', '2023-07-03', 'Twitter', 46, 36, 16, 6, 5, 5);
INSERT INTO `sentiment_analy` VALUES (71, 4, '美国', '2023-07-01', 'Youtube', 48, 35, 16, 8, 6, 2);
INSERT INTO `sentiment_analy` VALUES (72, 4, '美国', '2023-07-01', 'Instagram', 44, 36, 18, 5, 4, 4);
INSERT INTO `sentiment_analy` VALUES (73, 4, '美国', '2023-07-01', 'Twitter', 36, 36, 28, 5, 7, 3);
INSERT INTO `sentiment_analy` VALUES (74, 4, '美国', '2023-07-02', 'Youtube', 48, 30, 20, 8, 4, 6);
INSERT INTO `sentiment_analy` VALUES (75, 4, '美国', '2023-07-02', 'Instagram', 58, 26, 25, 6, 6, 5);
INSERT INTO `sentiment_analy` VALUES (76, 4, '美国', '2023-07-02', 'Twitter', 49, 34, 23, 5, 5, 4);
INSERT INTO `sentiment_analy` VALUES (77, 4, '美国', '2023-07-03', 'Youtube', 67, 30, 20, 5, 3, 5);
INSERT INTO `sentiment_analy` VALUES (78, 4, '美国', '2023-07-03', 'Instagram', 45, 26, 15, 5, 5, 5);
INSERT INTO `sentiment_analy` VALUES (79, 4, '美国', '2023-07-03', 'Twitter', 38, 36, 16, 5, 5, 2);
INSERT INTO `sentiment_analy` VALUES (80, 5, '美国', '2023-07-01', 'Youtube', 36, 32, 16, 8, 2, 2);
INSERT INTO `sentiment_analy` VALUES (81, 5, '美国', '2023-07-01', 'Instagram', 34, 32, 18, 7, 4, 4);
INSERT INTO `sentiment_analy` VALUES (82, 5, '美国', '2023-07-01', 'Twitter', 45, 32, 28, 4, 5, 5);
INSERT INTO `sentiment_analy` VALUES (83, 5, '美国', '2023-07-02', 'Youtube', 54, 30, 20, 2, 4, 3);
INSERT INTO `sentiment_analy` VALUES (84, 5, '美国', '2023-07-02', 'Instagram', 58, 26, 16, 6, 6, 1);
INSERT INTO `sentiment_analy` VALUES (85, 5, '美国', '2023-07-02', 'Twitter', 49, 28, 17, 3, 2, 4);
INSERT INTO `sentiment_analy` VALUES (86, 5, '美国', '2023-07-03', 'Youtube', 56, 28, 20, 5, 6, 5);
INSERT INTO `sentiment_analy` VALUES (87, 5, '美国', '2023-07-03', 'Instagram', 45, 26, 15, 5, 5, 1);
INSERT INTO `sentiment_analy` VALUES (88, 5, '美国', '2023-07-03', 'Twitter', 56, 36, 16, 7, 5, 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户的登录账号，唯一',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户的姓名',
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户的登录密码',
  `workUnit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户的工作单位',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户的电子邮箱',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户的手机号码',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户的头像url',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_user_u`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'user1', '用户1675202280464613377', '$2a$10$L8yq/GXKFSFPG/9lb0cru.uCjiJW1ZsAXmBwjfxYZeOzo7J33exPW', NULL, '12318237@qq.com', '15073548315', NULL);
INSERT INTO `user` VALUES (3, 'user2', '用户1675202346432626690', '$2a$10$gM2Eog6CFJmkkM8VlStTS.PUvoYkJzmuap2djpqVQ7OV.1egIT24K', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (4, 'user3', '用户1675202368909901826', '$2a$10$giE.511KeDx3D0JuZjL29uFi2JAhg1Zp.Wl5GRQx8kXjO1Z6d9lBS', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (5, 'user4', '用户1675202388052705281', '$2a$10$zdHp1hin2pVHQwWqjtZ1W.D9EcVxMDhoyX4PwPBkKqr4fdYWDSaAG', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (6, 'user5', '用户1675202404599234562', '$2a$10$7vmXVAxJy7cqLiqQ78R4fuMERFBYvY.vkd4XaJMScgIWUmlckcB86', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (7, 'user6', '用户7', '$2a$10$tTppf/iaKLjszY3i5obBROKPuXa0Ojou3r.Vye2fnQDHSGMcNloHq', '湖南师范大学', '', '', NULL);
INSERT INTO `user` VALUES (8, 'user7', '用户1675202435817439234', '$2a$10$dKB2wVMWnHPj1JiqzerTuevZNqpzqPt5EN0cfUgCpydIXBGqPM/xa', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (11, 'user10', '用户1675202483745751041', '$2a$10$Fxc2/SO0/gik6NJx/6FLqeSsD6nsVVEbUQ41sYnVLhlVCbIyICxCC', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (14, 'user13', '用户1675204064843259906', '$2a$10$1CObARrjeaaerH5YC/Sgm.KBr3kd7/C87jP7D1uMXD8ea52ufTQ.u', '湖南师范大学', '123123@qq.com', '127743821', NULL);
INSERT INTO `user` VALUES (15, 'user14', '用户1675204079208751105', '$2a$10$8RTHSAqcgwru3IGfdHn3c.a7XAI9Zp4OzpMsbcV3U4chxvRh0hV9O', '湖南师范大学', '123123@qq.com', '127743821', NULL);
INSERT INTO `user` VALUES (16, 'user15', '用户1675204092051709954', '$2a$10$pWe.yWhlY8AIm2BevTMB8O5q1u.7xoUlrei5ZoBHpxJcyaI00W7vi', '湖南师范大学', '123123@qq.com', '127743821', NULL);
INSERT INTO `user` VALUES (17, 'user16', '用户1675204105658032130', '$2a$10$.BWjEBeS2VwwtMsqcx8wVeKimrtwMAqfFJmCRcHNQhJZ/MwxLkE9i', '湖南师范大学', '123123@qq.com', '127743821', NULL);
INSERT INTO `user` VALUES (18, 'user17', '用户1675204120489091073', '$2a$10$5VnUv9f2tHX/LrzEKQoEtuEeCpGqpo/e48Nujfo5UyLYG00YQY6ZC', '湖南师范大学', '123123@qq.com', '127743821', NULL);
INSERT INTO `user` VALUES (19, 'user18', '用户1675204139468316674', '$2a$10$RIGUSLLaFwLVPMaKiNLHWed/LPYi.OVtSeBgEEhU8mjbKrH3e90de', '湖南师范大学', '123123@qq.com', '127743821', NULL);
INSERT INTO `user` VALUES (21, 'user20', '用户1675204184259289089', '$2a$10$w.QRVEH785DfWqijbQbWZON/DUlA6ccCgX7/Mz0cOJLUIxPrvgDYq', '湖南师范大学', '123123@qq.com', '127743821', NULL);
INSERT INTO `user` VALUES (22, 'root', '用户1675394849360703490', '$2a$10$Vv790Gf2.jHXMb9CfYwoYOgf/dlGunYUnO455eoiY.y9VDW5SC71q', '腾讯公司', '123123@qq.com', '127743821', NULL);
INSERT INTO `user` VALUES (24, 'kathy', 'anna', '$2a$10$YCkS9fd.w5EcZPNyBIBDC.8koX7E2Rz1ylz2A2RtghqI32Re4JcJm', '湖南师大', '1234@qq.com', NULL, NULL);
INSERT INTO `user` VALUES (25, '20230703', 'Anna', '$2a$10$.GBBchpW.8TeSmaxKgtUFe/ax9zR.igMqmBf9j0ZSek3YhFE5eDdy', '湖南师范大学', '3128315615@qq.com', NULL, NULL);
INSERT INTO `user` VALUES (26, 'hzx', '黄志祥', '$2a$10$nrfjC0EbbVMM6fxapQEFiOdTREtTAWRlUFbSaG28bXSPuxAthLQVq', '湖南师范大学', '1966752024@qq.com', '15073548315', NULL);
INSERT INTO `user` VALUES (27, 'hzx11', '用户11', '$2a$10$1iG5UtcAbOLmmq1635Pte.FiTjUPv.Us8.WxKsbAYnoGmwRI5qhF6', '湖南师范大学', '273127367@qq.com', '15073548316', NULL);
INSERT INTO `user` VALUES (28, 'hzx12', '用户12', '$2a$10$XAVICVfxbk7WGqc.NOCyl.AWCcSH4VjddbGF9huUrFqgo00lqfDvK', '湖南师范大学', '273127368@qq.com', '15073548317', NULL);
INSERT INTO `user` VALUES (29, 'hzx13', '用户13', '$2a$10$KqqLIHofwCx4fYlyzBeTuuXl8Slf7/pT5xdOurjEiQLRtzmZQP0uO', '湖南师范大学', '273127369@qq.com', '15073548318', NULL);
INSERT INTO `user` VALUES (30, 'hzx14', '用户14', '$2a$10$6/hYnUXakMrMat.v1Ldz9OfGpXnubyiFcUsX4uj8AC4QdsoRgPSTa', '湖南师范大学', '273127370@qq.com', '15073548319', NULL);
INSERT INTO `user` VALUES (34, 'hzx18', '用户18', '$2a$10$mXoKRYhraP1L4J.B3BjxX.UD09gLsHxmuLdhTF9aCfmoB9bclC59.', '湖南师范大学', '273127374@qq.com', '15073548323', NULL);
INSERT INTO `user` VALUES (35, 'hzx19', '用户19', '$2a$10$psPjkjWWK0lzjSEKwl.Sue4WevtCKPyNrE2J6V02fn2AwTh1Qmioa', '湖南师范大学', '273127375@qq.com', '15073548324', NULL);
INSERT INTO `user` VALUES (36, 'hzx20', '用户20', '$2a$10$piRWUpRt/o3fJp3KMIHxEOgA0oc0Xuu4U.oAtVA8x95zvq4mR37ru', '湖南师范大学', '273127376@qq.com', '15073548325', NULL);
INSERT INTO `user` VALUES (37, 'hzx21', '用户21', '$2a$10$YUWAGZhZ7EurCAEVzq5Qi.G9wWu6oyuefn9bJGyTWM3zOkHaYHwE2', '湖南师范大学', '273127377@qq.com', '15073548326', NULL);
INSERT INTO `user` VALUES (38, 'hzx22', '用户22', '$2a$10$roUfKkbOhQ7N3WQLm6AZ.uiXKcrnarb5EViH9KPwPKdw2Gm/cdAMW', '湖南师范大学', '273127378@qq.com', '15073548327', NULL);
INSERT INTO `user` VALUES (39, 'hzx23', '用户23', '$2a$10$nXGz4MoidtjM8wHL0aoYNONbQzOclkf97BMiNle5vjzmjrFMYKhmi', '湖南师范大学', '273127379@qq.com', '15073548328', NULL);
INSERT INTO `user` VALUES (40, 'hzx24', '用户24', '$2a$10$JWB6Gtfym/vG8OcOS4Ce5.I2kuLlLTdWPI2Cen7stemBHZ/Obrf3i', '湖南师范大学', '273127380@qq.com', '15073548329', NULL);
INSERT INTO `user` VALUES (41, 'hzx25', '用户25', '$2a$10$JH7QYZqDtukYPwa5qBqHJOm3XdRqsl9ODxxbZk6HPWIeRgfxn74J2', '湖南师范大学', '273127381@qq.com', '15073548330', NULL);
INSERT INTO `user` VALUES (42, 'hzx26', '用户26', '$2a$10$DmDQtNeXNiYT4W7Ht.vwRe29u0geLq29oI0QjxFnyrma/nU4Vg7dS', '湖南师范大学', '273127382@qq.com', '15073548331', NULL);
INSERT INTO `user` VALUES (43, 'yangtao', '羊涛', '$2a$10$MwhihYW/zGTCRQWYmuW8J.E2u91R5Mj6k0ebFvQgoy92.15maYi7a', '湖南师大', '13231321@qq.com', '', NULL);
INSERT INTO `user` VALUES (44, '123', '张三', '$2a$10$NRm9sIh6KG66SyVqAixi1.Sh949cieSd3AfZt5fr3rdXLVGevvnZe', '华为', '253647273@qq.com', '13135316056', NULL);
INSERT INTO `user` VALUES (45, '1234', '李四', '$2a$10$OZz.410UhueiGK2cDNsKYeXped4L03CsrU0P9Q2i4Xqf3NEl6R/Yu', '华为', '287636271@qq.com', '13526375726', NULL);
INSERT INTO `user` VALUES (46, '12345', '王五', '$2a$10$IPdVBl/dMs/Q.SL4vUUWFuiF4o7M/1uBhlrU5O8AZtx60RUAkkKRm', '腾讯', '263746537@qq.com', '13627676144', NULL);
INSERT INTO `user` VALUES (47, '20230708', 'Anny', '$2a$10$qk/EB85ZLgjEj.0F9YjdwemuyJAyTKlpU3FFEzMyTNeNn8wZ6htny', 'hunnu', '3125124768@qq.com', NULL, NULL);
INSERT INTO `user` VALUES (48, '123456', '赵四', '$2a$10$B1SSdUNLldVlhl/QtodBc.YLtOfegL.mK5oyHvucDl3xdK8K.PPu2', '腾讯', '1728326546@qq.com', '13135316598', NULL);

-- ----------------------------
-- Table structure for word_freq_analy
-- ----------------------------
DROP TABLE IF EXISTS `word_freq_analy`;
CREATE TABLE `word_freq_analy`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '词频统计编号，主键，自增',
  `workId` int NOT NULL COMMENT '监测作品的编号，外键',
  `country` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '词频统计的评论所属国家',
  `time` date NOT NULL COMMENT '词频统计的评论发布时间',
  `platform` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '词频统计的评论来自的平台',
  `polarity` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论中的高频词的情感极性数组',
  `keywords` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论中出现的高频词数组',
  `frequency` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论中出现的高频词频数数组',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `foreignkey_freq_work`(`workId`) USING BTREE,
  CONSTRAINT `foreignkey_freq_work` FOREIGN KEY (`workId`) REFERENCES `monitor_work` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of word_freq_analy
-- ----------------------------
INSERT INTO `word_freq_analy` VALUES (1, 1, '美国', '2023-06-26', 'Youtube', '积极 积极 消极 消极 积极 积极 积极 积极', '经典 名著 老土 剧情奇怪 无法超越 了不起 美好 耐人寻味', '10 15 8 6 14 20 25 32');
INSERT INTO `word_freq_analy` VALUES (2, 1, '美国', '2023-06-27', 'Youtube', '积极 积极 消极 消极 积极 积极 积极 积极', '经典 名著 难看 剧情古怪 厉害 了不起 美好 耐人寻味', '5 14 8 6 8 15 22 28');
INSERT INTO `word_freq_analy` VALUES (3, 4, '美国', '2023-07-01', 'Youtube', '积极 积极 消极 消极 积极 积极 积极 积极', '经典 名著 难看 剧情古怪 厉害 了不起 美好 耐人寻味', '5 14 8 8 8 15 22 28');
INSERT INTO `word_freq_analy` VALUES (4, 4, '美国', '2023-07-02', 'Youtube', '积极 积极 消极 消极 积极 积极 积极 积极', '经典 名著 老土 剧情奇怪 无法超越 了不起 美好 耐人寻味', '10 15 8 10 14 20 25 32');

-- ----------------------------
-- View structure for view_user_all
-- ----------------------------
DROP VIEW IF EXISTS `view_user_all`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_user_all` AS select `user`.`username` AS `username`,`user`.`name` AS `name`,`user`.`password` AS `password`,`user`.`workUnit` AS `workUnit`,`user`.`email` AS `email`,`user`.`phone` AS `phone` from `user`;

-- ----------------------------
-- View structure for view_user_touser
-- ----------------------------
DROP VIEW IF EXISTS `view_user_touser`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_user_touser` AS select `user`.`username` AS `username`,`user`.`name` AS `name`,`user`.`phone` AS `phone`,`user`.`email` AS `email`,`user`.`workUnit` AS `workUnit` from `user`;

-- ----------------------------
-- Procedure structure for hot_comments_proc
-- ----------------------------
DROP PROCEDURE IF EXISTS `hot_comments_proc`;
delimiter ;;
CREATE PROCEDURE `hot_comments_proc`(IN wId INT, IN cnt INT)
BEGIN
SELECT *
FROM raw_comment
WHERE workId = wId
ORDER BY likes DESC
LIMIT 0, cnt;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
