/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : miaosha

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 19/02/2020 20:32:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `price` double(10, 2) NOT NULL DEFAULT 0.00,
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品描述图路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (4, 'iphone12xs', '黑死最新二手128G ihone12xs', 4323.78, 'https://mp.ofweek.com/Upload/News/Img/member1/201911/03232757794781.jpeg');
INSERT INTO `item` VALUES (5, 'iphone8xs', '白色全新164G iphone8', 3999.98, 'http://dynamic-image.yesky.com/500x-/resources/product/20170913/C60TB77KLEB76K383QF69VN9AXUR58G0.jpg');

-- ----------------------------
-- Table structure for item_sales
-- ----------------------------
DROP TABLE IF EXISTS `item_sales`;
CREATE TABLE `item_sales`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `sales` int(11) NOT NULL DEFAULT 0 COMMENT '销量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_item_sales`(`item_id`) USING BTREE,
  CONSTRAINT `fk_item_sales` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_sales
-- ----------------------------
INSERT INTO `item_sales` VALUES (2, 4, 161);
INSERT INTO `item_sales` VALUES (3, 5, 101);

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `stock` int(11) NOT NULL DEFAULT 0 COMMENT '库存',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_item`(`item_id`) USING BTREE,
  CONSTRAINT `fk_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES (2, 4, 138);
INSERT INTO `item_stock` VALUES (3, 5, 198);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `user_id` int(11) NOT NULL COMMENT '下单人id',
  `item_id` int(11) NOT NULL COMMENT '商品id',
  `item_price` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '下单时的商品价格',
  `amount` int(11) NOT NULL DEFAULT 0 COMMENT '下单数目',
  `order_price` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '订单交易总金额',
  `promo_id` int(11) NOT NULL DEFAULT 0 COMMENT '此订单创建于哪个促销活动期间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_order_user`(`user_id`) USING BTREE,
  INDEX `fk_order_item`(`item_id`) USING BTREE,
  CONSTRAINT `fk_order_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '2020021400000000', 10, 4, 4323.78, 10, 43237.80, 0);
INSERT INTO `orders` VALUES (2, '2020021400000100', 10, 5, 3999.98, 1, 3999.98, 0);
INSERT INTO `orders` VALUES (3, '2020021400000200', 10, 4, 4323.78, 1, 4323.78, 0);
INSERT INTO `orders` VALUES (4, '2020021400000300', 10, 4, 4323.78, 20, 86475.60, 0);
INSERT INTO `orders` VALUES (5, '2020021400000400', 10, 4, 4323.78, 20, 86475.60, 0);
INSERT INTO `orders` VALUES (6, '2020021400000500', 10, 4, 4323.78, 1, 4323.78, 0);
INSERT INTO `orders` VALUES (7, '2020021500000600', 10, 4, 4323.78, 1, 4323.78, 0);
INSERT INTO `orders` VALUES (8, '2020021500000700', 10, 4, 3999.99, 1, 3999.99, 1);
INSERT INTO `orders` VALUES (9, '2020021500000800', 10, 4, 3999.99, 1, 3999.99, 1);
INSERT INTO `orders` VALUES (10, '2020021500000900', 10, 5, 3999.98, 1, 3999.98, 0);
INSERT INTO `orders` VALUES (11, '2020021500001000', 10, 4, 3999.99, 1, 3999.99, 1);
INSERT INTO `orders` VALUES (12, '2020021500001100', 10, 4, 3999.99, 1, 3999.99, 1);
INSERT INTO `orders` VALUES (13, '2020021500001200', 10, 4, 3999.99, 1, 3999.99, 1);
INSERT INTO `orders` VALUES (14, '2020021500001300', 10, 4, 3999.99, 1, 3999.99, 1);
INSERT INTO `orders` VALUES (15, '2020021700001400', 10, 4, 4323.78, 1, 4323.78, 0);
INSERT INTO `orders` VALUES (16, '2020021700001500', 10, 4, 4323.78, 1, 4323.78, 0);
INSERT INTO `orders` VALUES (17, '2020021900001600', 10, 4, 4323.78, 1, 4323.78, 0);

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '秒杀活动名称',
  `item_id` int(11) NOT NULL,
  `promo_price` double(10, 2) NOT NULL DEFAULT 0.00,
  `start_time` datetime(0) NOT NULL COMMENT '活动开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '活动结束时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_promo_item`(`item_id`) USING BTREE,
  CONSTRAINT `fk_promo_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of promo
-- ----------------------------
INSERT INTO `promo` VALUES (1, 'iphone12限时秒杀', 4, 3999.99, '2020-02-15 17:16:30', '2020-02-15 23:59:59');

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info`  (
  `sequence_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `current_value` int(11) NOT NULL DEFAULT 0,
  `increase_step` int(11) NOT NULL,
  `max_value` int(11) NOT NULL DEFAULT 999999,
  `init_value` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`sequence_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_sequence', 17, 1, 999999, 0);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `gender` int(1) NOT NULL DEFAULT -1 COMMENT '0 男，1 女',
  `age` int(3) NOT NULL DEFAULT -1,
  `telephone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `register_mode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'byphone' COMMENT '注册方式，phonenumber,wechat,alipay',
  `third_part_account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '第三方账号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_phone`(`telephone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (10, 'lisi', 1, 25, '13572523592', 'byphone', '');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `encrpt_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_info`(`user_id`) USING BTREE,
  CONSTRAINT `fk_user_info` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES (2, 10, '$2a$10$P646dpFzAEbKeY5jKJEjTeLdCUw2pQAacbUJYZjh6RSwe2TcFxA4y');

SET FOREIGN_KEY_CHECKS = 1;
