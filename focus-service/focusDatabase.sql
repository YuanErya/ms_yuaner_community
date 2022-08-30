DROP DATABASE IF EXISTS ms_yuaner_community_focus;
CREATE DATABASE IF NOT EXISTS ms_yuaner_community_focus;
USE ms_yuaner_community_focus;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `ye_focus`;
CREATE TABLE `ye_focus`  (
                            `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
                            `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关注者ID',
                            `focused_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '被关注者ID',
                            `create_time` datetime NOT NULL COMMENT '加入时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `user_id`(`user_id`) USING BTREE,
                            INDEX `focused_id`(`focused_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '关注表' ROW_FORMAT = DYNAMIC;

INSERT INTO `ye_focus` VALUES ('1874290158897311745', '1349290158897319087', '1349290158897311745', '2021-01-13 17:40:17');
INSERT INTO `ye_focus` VALUES ('1903648748226658398', '1349618748226656892', '1349290158897311745', '2021-01-14 15:25:59');
INSERT INTO `ye_focus` VALUES ('1679360158897319020', '1349618748226658305', '1349290158897311745','2021-01-13 19:40:17');
INSERT INTO `ye_focus` VALUES ('1039588748226656814', '1349290158897311745', '1349290158897319087',  '2021-01-24 15:25:59');
