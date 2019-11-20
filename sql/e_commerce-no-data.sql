-- --------------------------------------------------------
-- 主机:                           192.168.0.5
-- 服务器版本:                        5.7.27 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  10.2.0.5639
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 e_commerce 的数据库结构
CREATE DATABASE IF NOT EXISTS `e_commerce` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `e_commerce`;

-- 导出  表 e_commerce.e_brand 结构
CREATE TABLE IF NOT EXISTS `e_brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
  `name` varchar(50) NOT NULL COMMENT '品牌名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='品牌表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_carousel_ad 结构
CREATE TABLE IF NOT EXISTS `e_carousel_ad` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '轮播广告ID',
  `image` varchar(50) NOT NULL COMMENT '轮播广告图片',
  `show` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否展示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='轮播广告表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_cart_item 结构
CREATE TABLE IF NOT EXISTS `e_cart_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `commodity_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员ID',
  `parameter` text NOT NULL COMMENT '加入购物车时选择的商品参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='购物车项表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_comment 结构
CREATE TABLE IF NOT EXISTS `e_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(200) DEFAULT '' COMMENT '评论内容',
  `comment_date` datetime NOT NULL COMMENT '评论时间',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '评论等级(0:好评，1:中评，2:差评)',
  `member_id` bigint(20) DEFAULT NULL COMMENT '评论人ID',
  `commodity_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='商品评论表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_commodity 结构
CREATE TABLE IF NOT EXISTS `e_commodity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `price` double NOT NULL DEFAULT '0' COMMENT '商品价格',
  `market_price` double NOT NULL DEFAULT '0' COMMENT '市场价格',
  `store` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品库存',
  `purchase` int(11) NOT NULL DEFAULT '0' COMMENT '商品购买量',
  `click` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品点击量',
  `point` int(11) NOT NULL DEFAULT '0' COMMENT '购买商品赠送的积分',
  `put_shelves` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否上架',
  `boutique` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为精品',
  `new_product` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为新品',
  `hot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为热销商品',
  `description` text COMMENT '商品描述',
  `home_picture` varchar(50) DEFAULT NULL COMMENT '商品首页图片',
  `sort_id` bigint(20) DEFAULT NULL COMMENT '商品所属分类ID',
  `brand_id` bigint(20) DEFAULT NULL COMMENT '商品所属品牌ID',
  `parameter_id` bigint(20) DEFAULT NULL COMMENT '商品可选参数ID',
  `screenshots` varchar(255) NOT NULL COMMENT '商品截图组',
  `put_shelves_data` datetime NOT NULL COMMENT '商品上架时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='商品表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_member 结构
CREATE TABLE IF NOT EXISTS `e_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `email` varchar(50) NOT NULL COMMENT '电子邮箱',
  `balance` double unsigned NOT NULL DEFAULT '0' COMMENT '余额',
  `point` int(11) NOT NULL DEFAULT '0' COMMENT '购物积分',
  `level_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '会员等级ID',
  `freeze` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否未锁定',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='会员表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_order 结构
CREATE TABLE IF NOT EXISTS `e_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `total_amount` double NOT NULL DEFAULT '0' COMMENT '实付金额',
  `total_commodity` int(11) NOT NULL DEFAULT '0' COMMENT '商品总数',
  `order_status` int(11) NOT NULL DEFAULT '0' COMMENT '订单状态',
  `actual_amount` double NOT NULL DEFAULT '0' COMMENT '商品实际总价',
  `order_number` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后更新时间',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员ID',
  `receiver_id` bigint(20) DEFAULT NULL COMMENT '收货地址ID',
  `comments` varchar(200) DEFAULT NULL COMMENT '买家留言',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_number` (`order_number`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_order_item 结构
CREATE TABLE IF NOT EXISTS `e_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `unit_price` double NOT NULL DEFAULT '0' COMMENT '商品单价',
  `count` int(11) NOT NULL DEFAULT '0' COMMENT '成交数量',
  `actual_amount` double NOT NULL DEFAULT '0' COMMENT '实付金额',
  `parameter` varchar(255) NOT NULL COMMENT '购买时选择的商品参数',
  `commodity_id` bigint(20) DEFAULT '0' COMMENT '商品ID',
  `order_id` bigint(20) DEFAULT '0' COMMENT '订单ID',
  `order_item_status` int(11) NOT NULL COMMENT '订单项状态',
  `preferential_way` int(11) NOT NULL DEFAULT '0' COMMENT '优惠方式，默认不优惠',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COMMENT='订单项表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_parameter 结构
CREATE TABLE IF NOT EXISTS `e_parameter` (
  `id` bigint(20) NOT NULL COMMENT '对应商品ID',
  `parameter_json` text NOT NULL COMMENT '参数JSON',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品可选参数表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_receiver 结构
CREATE TABLE IF NOT EXISTS `e_receiver` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '收货人姓名',
  `area_address` varchar(255) NOT NULL COMMENT '区域地址',
  `detailed_address` varchar(255) NOT NULL COMMENT '详细地址',
  `phone` varchar(20) NOT NULL COMMENT '电话号码',
  `zipCode` varchar(10) DEFAULT NULL COMMENT '邮政编码',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员ID',
  `default_address` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为默认收货地址',
  `effective` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='收货地址表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_sort 结构
CREATE TABLE IF NOT EXISTS `e_sort` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品分类ID',
  `name` varchar(50) NOT NULL COMMENT '商品分类名称',
  `parent_sort_id` bigint(20) DEFAULT NULL COMMENT '商品分类父ID',
  `image` varchar(50) DEFAULT NULL COMMENT '商品分类图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=utf8 COMMENT='商品分类表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_spike_commodity 结构
CREATE TABLE IF NOT EXISTS `e_spike_commodity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL DEFAULT '0' COMMENT '商品价格',
  `store` int(11) NOT NULL DEFAULT '0' COMMENT '商品库存',
  `description` text COMMENT '商品描述',
  `start_time` datetime NOT NULL COMMENT '秒杀商品结束时间',
  `session_id` bigint(20) DEFAULT NULL COMMENT '商品所属秒杀场次ID',
  `data_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否有效',
  `commodity_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='秒杀商品表';

-- 数据导出被取消选择。

-- 导出  表 e_commerce.e_spike_session 结构
CREATE TABLE IF NOT EXISTS `e_spike_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '秒杀场次名称',
  `description` varchar(100) NOT NULL COMMENT '活动描述',
  `data_flag` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='秒杀场次表';

-- 数据导出被取消选择。

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
