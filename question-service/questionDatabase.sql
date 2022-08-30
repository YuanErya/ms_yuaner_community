DROP DATABASE IF EXISTS ms_yuaner_community_question;
CREATE DATABASE IF NOT EXISTS ms_yuaner_community_question;
USE ms_yuaner_community_question;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `ye_question`;
CREATE TABLE `ye_question`  (
                                `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问题ID',
                                `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标题',
                                `content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题内容',
                                `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提问者ID',
                                `answer_num` int NOT NULL DEFAULT 0 COMMENT '回答数目',
                                `create_time` datetime NOT NULL COMMENT '创建时间',
                                `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `title`(`title`) USING BTREE,
                                INDEX `user_id`(`user_id`) USING BTREE,
                                INDEX `create_time`(`create_time`) USING BTREE

) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '提问表' ROW_FORMAT = DYNAMIC;

INSERT INTO `ye_question` VALUES ('1349618748226654587', '第一啊', '这是第一个问题的内容哦！', '1349290158897311745', 0,'2021-01-14 15:25:59',NULL);
INSERT INTO `ye_question` VALUES ('1665718748226650589', '第2啊', '这是第2个问题的内容哦！', '1349618748226656892', 0,'2021-01-14 16:25:59',NULL);
INSERT INTO `ye_question` VALUES ('1704518748226659681', '第3啊', '这是第3个问题的内容哦！', '1349290158897319087', 0,'2021-01-14 20:25:59',NULL);
INSERT INTO `ye_question` VALUES ('1647938748226650850', '第4啊', '这是第4个问题的内容哦！', '1563504457765085186', 0,'2021-01-15 15:25:59',NULL);


DROP TABLE IF EXISTS `ye_answer`;
CREATE TABLE `ye_answer`  (
                              `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回答ID',
                              `content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回答内容',
                              `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回答者ID',
                              `question_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问题ID',
                              `create_time` datetime NOT NULL COMMENT '创建时间',
                              `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              INDEX `user_id`(`user_id`) USING BTREE,
                              INDEX `question_id`(`question_id`) USING BTREE,
                              INDEX `create_time`(`create_time`) USING BTREE

) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '回答表' ROW_FORMAT = DYNAMIC;

INSERT INTO `ye_answer` VALUES ('1349618748556654587',  '回答了第一个问题哦！', '1349618748226658305', '1349618748226654587','2021-01-15 15:25:59',NULL);
INSERT INTO `ye_answer` VALUES ('8705618748556654908',  '回答了问题哦！', '1349290158897319087', '1349618748226654587','2021-01-15 15:25:59',NULL);
INSERT INTO `ye_answer` VALUES ('1583518748556654998',  '回答了第NULL个问题哦！', '1349618748226656892', '1647938748226650850','2021-01-15 15:25:59',NULL);
INSERT INTO `ye_answer` VALUES ('4686458748556655820',  '一da个问题哦！', '1349618748226658305', '1665718748226650589','2021-01-15 15:25:59',NULL);


DROP TABLE IF EXISTS `ye_comment`;
CREATE TABLE `ye_comment`  (
                               `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论ID',
                               `content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
                               `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论者ID',
                               `question_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问题ID',
                               `answer_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回答ID',
                               `create_time` datetime NOT NULL COMMENT '创建时间',
                               `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `user_id`(`user_id`) USING BTREE,
                               INDEX `question_id`(`question_id`) USING BTREE,
                               INDEX `answer_id`(`answer_id`) USING BTREE,
                               INDEX `create_time`(`create_time`) USING BTREE

) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

INSERT INTO `ye_comment` VALUES ('1343418748556654587',  '评论了第一个问题的回答哦！', '1349290158897311745','1349618748226654587', '1349618748556654587','2021-01-16 15:25:59',NULL);
