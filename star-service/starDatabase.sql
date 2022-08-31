DROP DATABASE IF EXISTS ms_yuaner_community_star;
CREATE DATABASE IF NOT EXISTS ms_yuaner_community_star;
USE ms_yuaner_community_star;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `ye_star`;
CREATE TABLE `ye_star`  (
                            `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
                            `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点赞者ID',
                            `answer_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点赞的回答ID',
                            `create_time` datetime NOT NULL COMMENT '创建时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `user_id`(`user_id`) USING BTREE,
                            INDEX `answer_id`(`answer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '点赞表' ROW_FORMAT = DYNAMIC;

INSERT INTO `ye_star` VALUES ('1874290158897311364', '1349290158897319087', '1349618748556654587', '2021-01-13 17:40:17');
INSERT INTO `ye_star` VALUES ('1903648748226654679', '1349618748226656892', '8705618748556654908', '2021-01-14 15:25:59');
INSERT INTO `ye_star` VALUES ('1679360158897315665', '1349618748226658305', '4686458748556655820', '2021-01-13 19:40:17');
INSERT INTO `ye_star` VALUES ('1039588748224655628', '1349290158897311745', '1583518748556654998', '2021-01-24 15:25:59');
