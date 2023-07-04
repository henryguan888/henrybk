/*
 Navicat Premium Data Transfer

 Source Server         : henrybk
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : henrybk

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 04/07/2023 10:26:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置编号',
  `config_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数键名',
  `config_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数键值',
  `config_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '逻辑删除（0未删除 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'SUPER_ADMIN', 'SUPERADMIN', '超级管理员账户', '2023-05-23 09:07:21', '2023-05-23 09:07:25', 0);
INSERT INTO `sys_config` VALUES (2, 'INIT_PASSWORD', '123456', '初始密码（未加密）', '2023-06-01 17:35:58', '2023-06-01 17:36:01', 0);
INSERT INTO `sys_config` VALUES (3, 'CAROUSEL_URL', '/static/carousel/1.jpg;/static/carousel/2.jpg;/static/carousel/3.jpg;/static/carousel/4.jpg', '轮播图地址配置', '2023-06-26 14:46:18', '2023-07-03 17:58:39', 0);
INSERT INTO `sys_config` VALUES (4, 'TEST', '1234', '测试', '2023-07-03 17:59:30', '2023-07-03 17:59:37', 1);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '上级部门id',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT ',' COMMENT '树结构',
  `order_num` int NULL DEFAULT 1 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `status` int NULL DEFAULT 1 COMMENT '状态（0停用 1正常）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '逻辑删除（0未删除 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组织架构' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '启源科技', 0, '', 1, '张清', '18852011234', '18852011234@henrybk.com', 1, '2023-05-31 10:58:37', '2023-06-14 17:14:59', 0);
INSERT INTO `sys_dept` VALUES (2, '深圳分公司', 1, '', 3, '李安', '15432221254', '15432221254@henrybk.com', 1, '2023-05-31 11:02:29', '2023-05-31 13:40:19', 0);
INSERT INTO `sys_dept` VALUES (3, '广州分公司', 1, '', 4, '王凯', '19925366542', '19925366542@henrybk.com', 1, '2023-05-31 11:04:21', '2023-05-31 13:40:25', 0);
INSERT INTO `sys_dept` VALUES (4, '信息科技部', 2, '', 1, '黄兴', '', '', 1, '2023-05-31 11:05:30', '2023-05-31 12:54:33', 0);
INSERT INTO `sys_dept` VALUES (5, '人力资源部', 2, '', 2, '李娜', '', '', 1, '2023-05-31 11:06:44', '2023-05-31 12:54:45', 0);
INSERT INTO `sys_dept` VALUES (6, '财务会计部', 2, '', 3, '', '', '', 1, '2023-05-31 11:07:04', '2023-05-31 11:08:57', 0);
INSERT INTO `sys_dept` VALUES (7, '市场拓展部', 1, '', 1, '李师师', '2122334', '23111@wqq.com', 1, '2023-05-31 11:07:51', '2023-06-20 15:48:05', 0);
INSERT INTO `sys_dept` VALUES (8, '市场拓展部', 2, '', 4, '', '', '', 0, '2023-05-31 11:08:39', '2023-06-20 16:47:12', 0);
INSERT INTO `sys_dept` VALUES (9, '总裁室', 1, '', 1, '', '', '', 1, '2023-05-31 13:40:12', '2023-05-31 13:40:12', 0);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编号',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父编号',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典类型',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `is_default` int NULL DEFAULT 0 COMMENT '是否默认（0否 1是）',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态（0停用 1正常）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '逻辑删除（0未删除 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (10, 0, '上海市', '310000', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (11, 10, '上海城区', '310100', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (12, 0, '河南省', '410000', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (13, 12, '洛阳市', '410300', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (14, 12, '三门峡市', '411200', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (15, 0, '湖北省', '420000', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (16, 15, '十堰市', '420300', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (17, 15, '襄阳市', '420600', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (18, 0, '广东省', '440000', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (19, 18, '广州市', '440100', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (20, 18, '深圳市', '440300', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (21, 20, '南山区', '440305', 'dm_china_district', 1, 0, 1, NULL, '2023-05-24 17:51:00', '2023-06-13 14:17:27', 0);
INSERT INTO `sys_dict_data` VALUES (22, 20, '宝安区', '440306', 'dm_china_district', 4, 0, 1, NULL, '2023-05-24 17:51:00', '2023-06-13 14:21:47', 0);
INSERT INTO `sys_dict_data` VALUES (23, 18, '珠海市', '440400', 'dm_china_district', 0, 0, 1, NULL, '2023-05-24 17:51:00', '2023-05-24 17:51:00', 0);
INSERT INTO `sys_dict_data` VALUES (26, 20, '福田区', '440304', 'dm_china_district', 2, 0, 1, NULL, '2023-06-13 14:11:10', '2023-06-13 14:22:00', 0);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典类型',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态（0停用 1正常）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '逻辑删除（0未删除 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '中国地区', 'dm_china_district', 1, '中国行政区', '2023-06-12 15:54:48', '2023-07-03 17:01:50', 0);

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '登录账号',
  `session_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会话编号',
  `login_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '登录IP',
  `login_address` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '登录地址',
  `browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录浏览器',
  `operating_system` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录操作系统',
  `login_time` datetime NULL DEFAULT NULL COMMENT '登录时间',
  `status` int NULL DEFAULT NULL COMMENT '登录状态（0失败 1成功）',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '登录日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL COMMENT '菜单编号',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单编号',
  `order_num` int NULL DEFAULT NULL COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `menu_type` int NULL DEFAULT NULL COMMENT '菜单类型（0目录 1菜单 2按钮）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `always_show` int NULL DEFAULT 0 COMMENT '是否显示根路由（0隐藏 1显示）',
  `hidden` int NULL DEFAULT 0 COMMENT '是否隐藏菜单（0显示 1隐藏）',
  `status` int NULL DEFAULT 1 COMMENT '菜单状态（0停用 1正常）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '逻辑删除（0未删除 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (10, '首页', 0, 1, NULL, 'Layout', 0, NULL, 'dashboard', 0, 0, 1, '首页目录', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (98, '系统管理', 0, 8, 'system', 'Layout', 0, NULL, 'system', 0, 0, 1, '系统管理目录', '2023-05-20 00:03:34', '2023-06-13 17:32:45', 0);
INSERT INTO `sys_menu` VALUES (99, '系统监控', 0, 9, 'monitor', 'Layout', 0, NULL, 'monitor', 0, 0, 1, NULL, '2023-06-13 17:30:05', '2023-06-13 17:38:03', 0);
INSERT INTO `sys_menu` VALUES (1001, '主控台', 10, 1, 'dashboard', 'dashboard/index', 1, NULL, 'dashboard', 0, 0, 1, '首页菜单', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (9801, '用户管理', 98, 1, 'user', 'system/user/index', 1, NULL, 'user', 0, 0, 1, '用户管理菜单', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (9802, '角色管理', 98, 2, 'role', 'system/role/index', 1, NULL, 'peoples', 0, 0, 1, '角色管理菜单', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (9803, '菜单管理', 98, 3, 'menu', 'system/menu/index', 1, NULL, 'tree-table', 0, 0, 1, '菜单管理菜单', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (9804, '部门管理', 98, 4, 'dept', 'system/dept/index', 1, NULL, 'tree', 0, 0, 1, '部门管理菜单', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (9805, '岗位管理', 98, 5, 'post', 'system/post/index', 1, NULL, 'post', 0, 0, 1, '岗位管理菜单', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (9806, '字典管理', 98, 6, 'dictType', 'system/dict/index', 1, NULL, 'dict', 0, 0, 1, '字典管理菜单', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (9807, '字典数据', 98, 7, 'dictData', 'system/dict/dictData', 1, NULL, 'dictdata', 0, 1, 1, '字典数据菜单', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (9808, '配置管理', 98, 8, 'config', 'system/config/index', 1, NULL, 'setting', 0, 0, 1, NULL, '2023-07-03 17:36:27', '2023-07-03 17:36:27', 0);
INSERT INTO `sys_menu` VALUES (9901, '登录日志', 99, 1, 'login', 'monitor/login/index', 1, NULL, 'loginlog', 0, 0, 1, '登录日志菜单', '2023-06-13 17:39:44', '2023-06-13 17:39:44', 0);
INSERT INTO `sys_menu` VALUES (9902, '操作日志', 99, 2, 'oper', 'monitor/oper/index', 1, NULL, 'operlog', 0, 0, 1, '操作日志菜单', '2023-06-13 17:40:31', '2023-06-13 17:40:31', 0);
INSERT INTO `sys_menu` VALUES (980101, '用户添加', 9801, 1, NULL, NULL, 2, 'system.user.add', NULL, 0, 0, 1, '用户新增按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980102, '用户删除', 9801, 2, NULL, NULL, 2, 'system.user.del', NULL, 0, 0, 1, '用户删除按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980103, '用户修改', 9801, 3, NULL, NULL, 2, 'system.user.edit', NULL, 0, 0, 1, '用户修改按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980104, '用户查询', 9801, 4, NULL, NULL, 2, 'system.user.query', NULL, 0, 0, 1, '用户查询按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980105, '用户导入', 9801, 5, NULL, NULL, 2, 'system.user.upoad', NULL, 0, 0, 0, '用户导入按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980106, '用户导出', 9801, 6, NULL, NULL, 2, 'system.user.download', NULL, 0, 0, 1, '用户导出按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980107, '重置密码', 9801, 7, NULL, NULL, 2, 'system.user.restPwd', NULL, 0, 0, 1, '用户重置密码按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980108, '分配角色', 9801, 8, NULL, NULL, 2, 'system.user.assignRole', NULL, 0, 0, 1, '用户分配角色按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980201, '角色添加', 9802, 1, NULL, NULL, 2, 'system.role.add', NULL, 0, 0, 1, '角色新增按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980202, '角色删除', 9802, 2, NULL, NULL, 2, 'system.role.del', NULL, 0, 0, 1, '角色删除按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980203, '角色修改', 9802, 3, NULL, NULL, 2, 'system.role.edit', NULL, 0, 0, 1, '角色修改按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980204, '角色查询', 9802, 4, NULL, NULL, 2, 'system.role.query', NULL, 0, 0, 1, '角色查询按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980205, '角色导入', 9802, 5, NULL, NULL, 2, 'system.role.upoad', NULL, 0, 0, 0, '角色导入按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980206, '角色导出', 9802, 6, NULL, NULL, 2, 'system.role.download', NULL, 0, 0, 1, '角色导出按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980207, '分配菜单', 9802, 7, 'assignMenu', 'system/role/assignMenu', 2, 'system.role.assignMenu', 'el-icon-user-solid', 0, 0, 1, '角色分配菜单按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980208, '分配用户', 9802, 8, 'assignUser', 'system/role/assignUser', 2, 'system.role.assignUser', 'el-icon-user-solid', 0, 0, 1, '角色分配用户按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980301, '菜单添加', 9803, 1, NULL, NULL, 2, 'system.menu.add', NULL, 0, 0, 1, '菜单新增按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980302, '菜单删除', 9803, 2, NULL, NULL, 2, 'system.menu.del', NULL, 0, 0, 1, '菜单删除按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980303, '菜单修改', 9803, 3, NULL, NULL, 2, 'system.menu.edit', NULL, 0, 0, 1, '菜单修改按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980304, '菜单查询', 9803, 4, NULL, NULL, 2, 'system.menu.query', NULL, 0, 0, 1, '菜单查询按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980401, '部门添加', 9804, 1, NULL, NULL, 2, 'system.dept.add', NULL, 0, 0, 1, '部门新增按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980402, '部门删除', 9804, 2, NULL, NULL, 2, 'system.dept.del', NULL, 0, 0, 1, '部门删除按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980403, '部门修改', 9804, 3, NULL, NULL, 2, 'system.dept.edit', NULL, 0, 0, 1, '部门修改按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980404, '部门查询', 9804, 4, NULL, NULL, 2, 'system.dept.query', NULL, 0, 0, 1, '部门查询按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980405, '部门导入', 9804, 5, NULL, NULL, 2, 'system.dept.upoad', NULL, 0, 0, 0, '部门导入按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980406, '部门导出', 9804, 6, NULL, NULL, 2, 'system.dept.download', NULL, 0, 0, 1, '部门导出按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980501, '岗位添加', 9805, 1, NULL, NULL, 2, 'system.post.add', NULL, 0, 0, 1, '岗位新增按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980502, '岗位删除', 9805, 2, NULL, NULL, 2, 'system.post.del', NULL, 0, 0, 1, '岗位删除按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980503, '岗位修改', 9805, 3, NULL, NULL, 2, 'system.post.edit', NULL, 0, 0, 1, '岗位修改按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980504, '岗位查询', 9805, 4, NULL, NULL, 2, 'system.post.query', NULL, 0, 0, 1, '岗位查询按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980505, '岗位导入', 9805, 5, NULL, NULL, 2, 'system.post.upoad', NULL, 0, 0, 0, '岗位导入按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980506, '岗位导出', 9805, 6, NULL, NULL, 2, 'system.post.download', NULL, 0, 0, 1, '岗位导出按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980601, '字典类型添加', 9806, 1, NULL, NULL, 2, 'system.dictType.add', NULL, 0, 0, 1, '字典类型新增按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980602, '字典类型删除', 9806, 2, NULL, NULL, 2, 'system.dictType.del', NULL, 0, 0, 1, '字典类型删除按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980603, '字典类型修改', 9806, 3, NULL, NULL, 2, 'system.dictType.edit', NULL, 0, 0, 1, '字典类型修改按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980604, '字典类型查询', 9806, 4, NULL, NULL, 2, 'system.dictType.query', NULL, 0, 0, 1, '字典类型查询按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980605, '字典类型导入', 9806, 5, NULL, NULL, 2, 'system.dictType.upoad', NULL, 0, 0, 0, '字典类型导入按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980606, '字典类型导出', 9806, 6, NULL, NULL, 2, 'system.dictType.download', NULL, 0, 0, 1, '字典类型导出按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980607, '字典类型详情', 9806, 7, NULL, NULL, 2, 'system.dictType.detail', NULL, 0, 0, 1, '字典类型详情按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980701, '字典数据添加', 9807, 1, NULL, NULL, 2, 'system.dictData.add', NULL, 0, 0, 1, '字典数据新增按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980702, '字典数据删除', 9807, 2, NULL, NULL, 2, 'system.dictData.del', NULL, 0, 0, 1, '字典数据删除按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980703, '字典数据修改', 9807, 3, NULL, NULL, 2, 'system.dictData.edit', NULL, 0, 0, 1, '字典数据修改按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980704, '字典数据查询', 9807, 4, NULL, NULL, 2, 'system.dictData.query', NULL, 0, 0, 1, '字典数据查询按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980705, '字典数据导入', 9807, 5, NULL, NULL, 2, 'system.dictData.upoad', NULL, 0, 0, 0, '字典数据导入按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980706, '字典数据导出', 9807, 6, NULL, NULL, 2, 'system.dictData.download', NULL, 0, 0, 1, '字典数据导出按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980707, '字典数据刷新', 9807, 7, NULL, NULL, 2, 'system.dictData.refresh', NULL, 0, 0, 1, '字典数据刷新按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980801, '配置添加', 9808, 1, NULL, NULL, 2, 'system.config.add', NULL, 0, 0, 1, '配置新增按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980802, '配置删除', 9808, 2, NULL, NULL, 2, 'system.config.del', NULL, 0, 0, 1, '配置删除按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980803, '配置修改', 9808, 3, NULL, NULL, 2, 'system.config.edit', NULL, 0, 0, 1, '配置修改按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);
INSERT INTO `sys_menu` VALUES (980804, '配置查询', 9808, 4, NULL, NULL, 2, 'system.config.query', NULL, 0, 0, 1, '配置查询按钮', '2023-05-20 00:03:34', '2023-05-20 00:03:41', 0);

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模块标题',
  `business_type` int NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除...）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
  `operator_type` int NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '返回参数',
  `status` int NULL DEFAULT 1 COMMENT '操作状态（0异常 1正常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位编号',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '岗位名称',
  `order_num` int NULL DEFAULT NULL COMMENT '显示顺序',
  `status` int NOT NULL DEFAULT 1 COMMENT '岗位状态（0停用 1正常）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '逻辑删除（0未删除 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'partner', '合作商员工', 1, 1, '外包人员', '2023-05-25 13:36:56', '2023-05-25 13:36:59', '0');
INSERT INTO `sys_post` VALUES (2, 'user', '普通员工', 2, 1, '普通员工', '2023-05-25 15:07:39', '2023-05-25 15:07:42', '0');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, 1, '人事员工', '2023-05-26 09:29:28', '2023-06-20 17:28:16', '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码',
  `order_num` int NULL DEFAULT NULL COMMENT '显示顺序',
  `data_scope` int NULL DEFAULT 1 COMMENT '数据范围（1全部数据权限 2自定数据权限 3本部门数据权限 4本部门及以下数据权限）',
  `status` int NOT NULL DEFAULT 1 COMMENT '角色状态（0停用 1正常）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '逻辑删除（0未删除 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'super admin', 1, 1, 1, '我是一条备注信息1', '2023-05-24 14:11:40', '2023-06-20 14:16:23', 0);
INSERT INTO `sys_role` VALUES (2, '管理员', 'admin', 2, 1, 1, '我是一条备注信息', '2023-05-24 14:11:40', '2023-05-24 14:11:43', 0);
INSERT INTO `sys_role` VALUES (3, '员工', 'yaungong', 3, 1, 1, '我是一条备注信息', '2023-05-24 14:11:40', '2023-05-24 14:11:43', 0);
INSERT INTO `sys_role` VALUES (4, '实习生', 'shixisheng', 4, 1, 1, '我是一条备注信息', '2023-05-24 14:11:40', '2023-06-20 15:02:07', 0);
INSERT INTO `sys_role` VALUES (5, '菜鸟', 'cainiao', 5, 1, 1, NULL, '2023-06-26 16:45:02', '2023-06-26 16:45:05', 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint NOT NULL COMMENT '角色编号',
  `menu_id` bigint NOT NULL COMMENT '菜单编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '逻辑删除（0未删除 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (null, 2, 98, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 9801, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980101, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980102, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980103, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980104, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980105, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980106, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980107, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980108, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 9802, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980201, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980202, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980203, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980204, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980205, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980206, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980207, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980208, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 9803, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980301, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980302, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980303, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980304, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 9804, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980401, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980402, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980403, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980404, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980405, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980406, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 9805, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980501, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980502, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980503, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980504, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980505, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980506, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 99, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 9901, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 9902, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 9806, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980601, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980602, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980603, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980604, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980605, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980606, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980607, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 9807, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980701, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980702, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980703, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980704, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980705, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980706, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980707, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 10, '2023-07-03 16:19:53', '2023-07-03 16:19:55', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 1001, '2023-07-03 16:19:47', '2023-07-03 16:19:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 9808, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980801, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980802, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980803, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);
INSERT INTO `sys_role_menu` VALUES (null, 2, 980804, '2023-06-20 15:02:50', '2023-06-20 15:02:50', 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门编号',
  `post_id` bigint NULL DEFAULT NULL COMMENT '岗位编号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `gender` int NULL DEFAULT 2 COMMENT '用户性别（0女 1男 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `status` int NOT NULL DEFAULT 1 COMMENT '帐号状态（0停用 1正常）',
  `pwd_err_num` int NULL DEFAULT 0 COMMENT '密码输入错误次数(初期为0，登录成功后变为0，密码输入错误后累计，累计5次，该用户将被锁定，密码重置或用户解锁后，变为0)',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '逻辑删除（0未删除 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'SUPERADMIN', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '超级管理员', '超级管理员', 1, 2, '18956544556@163.com', '18956544556', 1, '/static/boy.gif', 1, 0, '127.0.0.1', '2023-05-24 14:07:47', '我是备注', '2023-05-24 14:07:57', '2023-06-07 09:36:04', 0);
INSERT INTO `sys_user` VALUES (2, 'ADMIN', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '管理员', '管理员1', 4, 2, '18956544556@163.com', '18956544556', 2, '/static/boy.gif', 1, 0, '127.0.0.1', '2023-07-03 18:03:22', NULL, '2023-05-24 14:07:57', '2023-07-03 18:03:22', 0);
INSERT INTO `sys_user` VALUES (3, 'TEST', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '测试用户', '测试用户', 5, 5, '15785447445@qq.com', '15785447445', 2, NULL, 1, 0, '127.0.0.1', '2023-07-03 16:51:57', '测试12', '2023-06-19 10:59:53', '2023-07-03 16:51:57', 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `role_id` bigint NOT NULL COMMENT '角色编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '逻辑删除（0未删除 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 2, 2, '2023-05-24 14:14:28', '2023-05-24 14:14:28', 0);
INSERT INTO `sys_user_role` VALUES (2, 3, 3, '2023-05-24 14:14:28', '2023-05-24 14:14:28', 0);

SET FOREIGN_KEY_CHECKS = 1;
