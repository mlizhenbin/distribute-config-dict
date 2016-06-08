CREATE TABLE `lr_config_dict` (
  `id` int(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `config_type` varchar(50) NOT NULL COMMENT '配置类型',
  `key` varchar(50) NOT NULL COMMENT '配置KEY',
  `value` varchar(255) NOT NULL COMMENT '配置value',
  `config_desc` varchar(255) NOT NULL COMMENT '配置描述',
  `valid_flag` char(1) NOT NULL COMMENT '审核状态',
  `order_id` int(8) NOT NULL DEFAULT '0' COMMENT '排序值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_type` (`config_type`,`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置字典表'