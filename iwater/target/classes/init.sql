CREATE DATABASE `paas` /*!40100 DEFAULT CHARACTER SET utf8 */;


CREATE TABLE `b_user` (
  `user_id` varchar(100) NOT NULL DEFAULT '' COMMENT 'id',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `pass_word` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `china_name` varchar(100) NOT NULL DEFAULT '' COMMENT '中文名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone_number` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `tel` varchar(20) DEFAULT NULL COMMENT '座机电话',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `dept` varchar(100) DEFAULT NULL COMMENT '所属部门',
  `user_desc` varchar(200) DEFAULT NULL COMMENT '??',
  `status` varchar(2) DEFAULT NULL COMMENT '状态(1:启用，0:停用）',
  `role` varchar(2) DEFAULT NULL COMMENT '角色(1：管理员 2:非管理员)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
