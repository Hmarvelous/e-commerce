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

-- 正在导出表  e_commerce.e_brand 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `e_brand` DISABLE KEYS */;
INSERT INTO `e_brand` (`id`, `name`) VALUES
	(2, '华为'),
	(1, '小米');
/*!40000 ALTER TABLE `e_brand` ENABLE KEYS */;

-- 导出  表 e_commerce.e_carousel_ad 结构
CREATE TABLE IF NOT EXISTS `e_carousel_ad` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '轮播广告ID',
  `image` varchar(50) NOT NULL COMMENT '轮播广告图片',
  `show` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否展示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='轮播广告表';

-- 正在导出表  e_commerce.e_carousel_ad 的数据：~7 rows (大约)
/*!40000 ALTER TABLE `e_carousel_ad` DISABLE KEYS */;
INSERT INTO `e_carousel_ad` (`id`, `image`, `show`) VALUES
	(1, 'bg-1.webp', b'1'),
	(2, 'bg-2.jpg', b'1'),
	(3, 'bg-3.jpg', b'1'),
	(4, 'bg-4.jpg', b'1'),
	(5, 'bg-5.webp', b'1'),
	(6, 'bg-6.webp', b'1'),
	(7, 'bg-7.webp', b'0');
/*!40000 ALTER TABLE `e_carousel_ad` ENABLE KEYS */;

-- 导出  表 e_commerce.e_cart_item 结构
CREATE TABLE IF NOT EXISTS `e_cart_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `commodity_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员ID',
  `parameter` text NOT NULL COMMENT '加入购物车时选择的商品参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='购物车项表';

-- 正在导出表  e_commerce.e_cart_item 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `e_cart_item` DISABLE KEYS */;
INSERT INTO `e_cart_item` (`id`, `count`, `commodity_id`, `member_id`, `parameter`) VALUES
	(3, 5, 2, 1, '[{"parameter":{"name":"M"},"name":"尺码"},{"parameter":{"name":"灰色"},"name":"颜色"}]'),
	(4, 2, 4, 1, '[{"parameter":{"name":"M"},"name":"尺码"},{"parameter":{"name":"灰色"},"name":"颜色"}]'),
	(5, 3, 2, 1, '[{"parameter":{"name":"M"},"name":"尺码"},{"parameter":{"name":"银色"},"name":"颜色"}]');
/*!40000 ALTER TABLE `e_cart_item` ENABLE KEYS */;

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

-- 正在导出表  e_commerce.e_comment 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `e_comment` DISABLE KEYS */;
INSERT INTO `e_comment` (`id`, `content`, `comment_date`, `level`, `member_id`, `commodity_id`) VALUES
	(1, '衣服比我预期的要好，这个价位值了！有需求的可直接入手，不必多虑。肯定好评！', '2019-10-12 10:31:57', 1, 1, 2),
	(2, '布料柔软有弹性，穿着也很舒适。', '2019-10-12 10:32:13', 1, 1, 2),
	(3, '回购不下十次了。真的很好，工薪朋友最爱。', '2019-10-12 10:32:22', 0, 1, 2),
	(4, '衣服太差了，果断差评。', '2019-10-12 15:07:00', 2, 1, 2),
	(5, '很好的一件商品', '2016-10-15 20:12:45', 0, 1, 2),
	(6, '多好的一个评价啊', '2019-10-20 21:34:21', 0, 1, 3),
	(7, '这个一定要好评', '2019-10-21 09:35:04', 0, 1, 3),
	(8, '来个中评吧', '2019-10-21 10:02:36', 1, 1, 3),
	(9, '这么差的衣服也敢拿来卖？', '2019-10-22 13:56:31', 2, 1, 2),
	(10, '这么好用，果断好评', '2019-10-31 14:18:45', 0, 1, 4),
	(11, '还好好的还好好的还好好的还好好的还好好的还好好的', '2019-11-04 10:58:41', 0, 1, 2);
/*!40000 ALTER TABLE `e_comment` ENABLE KEYS */;

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

-- 正在导出表  e_commerce.e_commodity 的数据：~7 rows (大约)
/*!40000 ALTER TABLE `e_commodity` DISABLE KEYS */;
INSERT INTO `e_commodity` (`id`, `name`, `price`, `market_price`, `store`, `purchase`, `click`, `point`, `put_shelves`, `boutique`, `new_product`, `hot`, `description`, `home_picture`, `sort_id`, `brand_id`, `parameter_id`, `screenshots`, `put_shelves_data`) VALUES
	(1, '【包邮包税】港版雅培4段心美力婴幼儿牛奶粉4段 900g 香港直邮', 216, 216, 45, 0, 0, 5, b'1', b'0', b'1', b'1', '香港直邮， 包邮包税价， 4-7天到货', '20191009_48563258.jpg', NULL, NULL, 1, '20191009_48563258.jpg', '2019-10-09 10:58:16'),
	(2, '吉普盾2019新款短袖t恤男印花夏季圆领潮流宽松美式休闲半袖t恤男', 136, 136, 45, 0, 0, 5, b'1', b'1', b'0', b'1', '初夏上新 棉弹舒适 清爽透气 多色可选', '20191009_78955463.jpg', 230, NULL, 1, '20191009_78955463.jpg,20191009_45896347.jpg,20191009_48563258.jpg,20191009_48563759.jpg,20191009_48569512.jpg', '2019-10-09 10:58:16'),
	(3, '先锋取暖器DF1207家用欧式快热炉取暖器电暖气居浴两用防水电暖器', 103, 105, 45, 0, 0, 5, b'1', b'1', b'1', b'0', '简单一点的描述', '20191009_78565421.jpg', 230, NULL, 1, '20191009_78955463.jpg', '2019-10-09 10:58:16'),
	(4, 'Haier/海尔HBL-P08D1B多功能料理机家用小型宝宝辅食机奶昔绞肉机', 269, 269, 43, 0, 0, 5, b'1', b'0', b'1', b'1', '简单一点的描述', '20191009_48563759.jpg', 230, NULL, 1, '20191009_48563759.jpg', '2019-10-09 10:58:16'),
	(5, 'HP/惠普 G200黑色 CF/LOL/守望先锋专业电竞游戏鼠标', 99, 99, 45, 0, 0, 5, b'1', b'1', b'0', b'1', '惠普电竞游戏 大鼠标 精准灵敏 宏设置', '20191009_15874695.jpg', 230, NULL, 1, '20191009_15874695.jpg', '2019-10-09 10:58:16'),
	(6, '得力7102固体胶 儿童胶棒学生固体胶水 办公文具 中号21克', 1, 1, 10, 0, 0, 5, b'1', b'1', b'1', b'1', '【得力正品 品质保证】 一支价', '20191009_48569512.jpg', 231, NULL, 1, '20191009_48569512.jpg', '2019-10-09 10:58:16'),
	(7, '【6期免息】Huawei/华为P30 Pro曲面屏超感光徕卡四摄变焦双景录像980智能手机p30pro', 4988, 4988, 45, 0, 0, 5, b'1', b'1', b'1', b'1', '4000万超感光徕卡四摄', '20191009_45896347.jpg', NULL, NULL, 1, '20191009_45896347.jpg', '2019-10-09 10:58:16');
/*!40000 ALTER TABLE `e_commodity` ENABLE KEYS */;

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

-- 正在导出表  e_commerce.e_member 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `e_member` DISABLE KEYS */;
INSERT INTO `e_member` (`id`, `username`, `password`, `email`, `balance`, `point`, `level_id`, `freeze`) VALUES
	(1, 'admin', '9d9abb1877cd9d99270dd7ddfac59119', '914160789@qq.com', 3447, 85, 0, b'1'),
	(5, 'admin1', '9d9abb1877cd9d99270dd7ddfac59119', '123456', 0, 0, 0, b'1'),
	(6, 'admin2', '9d9abb1877cd9d99270dd7ddfac59119', '123456', 0, 0, 0, b'1'),
	(7, 'admin3', '9d9abb1877cd9d99270dd7ddfac59119', '123456', 0, 0, 0, b'1'),
	(8, 'admin4', '9d9abb1877cd9d99270dd7ddfac59119', '123456', 0, 0, 0, b'1');
/*!40000 ALTER TABLE `e_member` ENABLE KEYS */;

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

-- 正在导出表  e_commerce.e_order 的数据：~17 rows (大约)
/*!40000 ALTER TABLE `e_order` DISABLE KEYS */;
INSERT INTO `e_order` (`id`, `total_amount`, `total_commodity`, `order_status`, `actual_amount`, `order_number`, `create_time`, `update_time`, `member_id`, `receiver_id`, `comments`) VALUES
	(34, 380, 3, 5, 380, 201910181615415787, '2019-10-18 16:15:41', '2019-10-18 16:15:41', 1, 1, ''),
	(35, 411, 3, 1, 411, 201910181843558991, '2019-10-18 18:43:56', '2019-10-18 18:43:56', 1, 1, ''),
	(36, 104, 1, 4, 104, 201910182024132234, '2019-10-18 20:24:13', '2019-10-18 20:24:13', 1, 1, ''),
	(37, 104, 1, 4, 104, 201910182025226169, '2019-10-18 20:25:22', '2019-10-18 20:25:22', 1, 1, ''),
	(38, 681, 4, 1, 681, 201910182025525303, '2019-10-18 20:25:52', '2019-10-18 20:25:52', 1, 2, ''),
	(39, 411, 3, 4, 411, 201910182026118004, '2019-10-18 20:26:11', '2019-10-18 20:26:11', 1, 1, ''),
	(40, 951, 5, 1, 951, 201910182026241757, '2019-10-18 20:26:25', '2019-10-18 20:26:25', 1, 1, ''),
	(41, 540, 2, 1, 540, 201910182026367046, '2019-10-18 20:26:36', '2019-10-18 20:26:36', 1, 1, ''),
	(42, 951, 5, 4, 951, 201910201649386085, '2019-10-20 16:49:38', '2019-10-20 16:49:38', 1, 1, ''),
	(46, 2438, 11, 1, 2438, 201910220954232554, '2019-10-22 09:54:23', '2019-11-04 10:57:35', 1, 2, ''),
	(47, 955, 6, 1, 955, 201910271656359638, '2019-10-27 16:56:35', '2019-10-27 16:56:35', 1, 1, ''),
	(63, 1, 1, 1, 137, 201910311630453368, '2019-10-31 16:30:45', '2019-10-31 16:30:45', 1, 1, ''),
	(65, 1, 1, 1, 137, 201910311953359635, '2019-10-31 19:53:35', '2019-10-31 19:53:44', 1, 1, ''),
	(66, 1, 1, 5, 137, 201910312119267672, '2019-10-31 21:19:27', '2019-10-31 21:19:27', 1, 1, ''),
	(67, 1, 1, 5, 137, 201910312132282439, '2019-10-31 21:32:28', '2019-10-31 21:32:28', 1, 1, ''),
	(68, 1, 1, 1, 137, 201910312155522077, '2019-10-31 21:55:52', '2019-11-01 10:34:24', 1, 1, ''),
	(71, 1, 1, 4, 141, 201911041056452186, '2019-11-04 10:56:46', '2019-11-04 10:56:46', 1, 1, ''),
	(72, 540, 2, 1, 540, 201911041108113865, '2019-11-04 11:08:11', '2019-11-04 11:08:11', 1, 1, '');
/*!40000 ALTER TABLE `e_order` ENABLE KEYS */;

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

-- 正在导出表  e_commerce.e_order_item 的数据：~22 rows (大约)
/*!40000 ALTER TABLE `e_order_item` DISABLE KEYS */;
INSERT INTO `e_order_item` (`id`, `unit_price`, `count`, `actual_amount`, `parameter`, `commodity_id`, `order_id`, `order_item_status`, `preferential_way`) VALUES
	(51, 137, 2, 274, '[{"name":"M"},{"name":"银色"}]', 2, 34, 0, 0),
	(52, 106, 1, 106, '[{"name":"L"},{"name":"银色"}]', 3, 34, 0, 0),
	(53, 137, 3, 411, '[{"name":"M"},{"name":"灰色"}]', 2, 35, 1, 0),
	(54, 104, 1, 104, '[{"name":"M"},{"name":"灰色"}]', 3, 36, 4, 0),
	(55, 104, 1, 104, '[{"name":"M"},{"name":"灰色"}]', 3, 37, 4, 0),
	(56, 137, 3, 411, '[{"name":"M"},{"name":"灰色"}]', 2, 38, 1, 0),
	(57, 270, 1, 270, '[{"name":"M"},{"name":"灰色"}]', 4, 38, 1, 0),
	(58, 137, 3, 411, '[{"name":"M"},{"name":"灰色"}]', 2, 39, 4, 0),
	(59, 137, 3, 411, '[{"name":"M"},{"name":"灰色"}]', 2, 40, 1, 0),
	(60, 270, 2, 540, '[{"name":"M"},{"name":"灰色"}]', 4, 40, 1, 0),
	(61, 270, 2, 540, '[{"name":"M"},{"name":"灰色"}]', 4, 41, 1, 0),
	(62, 137, 3, 411, '[{"name":"M"},{"name":"灰色"}]', 2, 42, 3, 0),
	(63, 270, 2, 540, '[{"name":"M"},{"name":"灰色"}]', 4, 42, 4, 0),
	(64, 137, 4, 548, '[{"name":"M"},{"name":"灰色"}]', 2, 46, 0, 0),
	(65, 270, 7, 1890, '[{"name":"M"},{"name":"灰色"}]', 4, 46, 0, 0),
	(66, 137, 5, 685, '[{"name":"M"},{"name":"灰色"}]', 2, 47, 0, 0),
	(67, 270, 1, 270, '[{"name":"M"},{"name":"灰色"}]', 4, 47, 0, 0),
	(81, 1, 1, 1, '[{"name":"M"},{"name":"灰色"}]', 2, 63, 1, 1),
	(83, 1, 1, 1, '[{"name":"M"},{"name":"灰色"}]', 2, 65, 1, 1),
	(84, 1, 1, 1, '[{"name":"M"},{"name":"灰色"}]', 2, 66, 5, 1),
	(85, 1, 1, 1, '[{"name":"M"},{"name":"灰色"}]', 2, 67, 5, 1),
	(86, 1, 1, 1, '[{"name":"M"},{"name":"灰色"}]', 2, 68, 1, 1),
	(87, 1, 1, 1, '[{"name":"X"},{"name":"灰色"}]', 2, 71, 4, 1),
	(88, 270, 2, 540, '[{"name":"M"},{"name":"灰色"}]', 4, 72, 1, 0);
/*!40000 ALTER TABLE `e_order_item` ENABLE KEYS */;

-- 导出  表 e_commerce.e_parameter 结构
CREATE TABLE IF NOT EXISTS `e_parameter` (
  `id` bigint(20) NOT NULL COMMENT '对应商品ID',
  `parameter_json` text NOT NULL COMMENT '参数JSON',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品可选参数表';

-- 正在导出表  e_commerce.e_parameter 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `e_parameter` DISABLE KEYS */;
INSERT INTO `e_parameter` (`id`, `parameter_json`) VALUES
	(1, '[{"name":"尺码","parameters":[{"name":"M","price":1.00},{"name":"L","price":3.00},{"name":"X","price":5.00}]},{"name":"颜色","parameters":[{"name":"灰色","price":0.00},{"name":"银色","price":0.00},{"name":"蓝色","price":0.00}]}]');
/*!40000 ALTER TABLE `e_parameter` ENABLE KEYS */;

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

-- 正在导出表  e_commerce.e_receiver 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `e_receiver` DISABLE KEYS */;
INSERT INTO `e_receiver` (`id`, `name`, `area_address`, `detailed_address`, `phone`, `zipCode`, `member_id`, `default_address`, `effective`) VALUES
	(1, '岳凡', '江苏省/徐州市/云龙区', '亚东小区4--2--502', '13291052776', NULL, 1, b'1', b'1'),
	(2, '张黄宇', '陕西省/西安市/未央区', '草滩八路中心小区5排35号', '13700278269', NULL, 1, b'0', b'1'),
	(3, '张黄宇', '陕西省/西安市/未央区', '六村堡街道草滩八路中心小区23号楼1单元306室', '13700278269', NULL, 1, b'0', b'1');
/*!40000 ALTER TABLE `e_receiver` ENABLE KEYS */;

-- 导出  表 e_commerce.e_sort 结构
CREATE TABLE IF NOT EXISTS `e_sort` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品分类ID',
  `name` varchar(50) NOT NULL COMMENT '商品分类名称',
  `parent_sort_id` bigint(20) DEFAULT NULL COMMENT '商品分类父ID',
  `image` varchar(50) DEFAULT NULL COMMENT '商品分类图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=utf8 COMMENT='商品分类表';

-- 正在导出表  e_commerce.e_sort 的数据：~244 rows (大约)
/*!40000 ALTER TABLE `e_sort` DISABLE KEYS */;
INSERT INTO `e_sort` (`id`, `name`, `parent_sort_id`, `image`) VALUES
	(1, '女装 /内衣', NULL, 'sort_skirt.png'),
	(2, '男装 /运动户外', NULL, 'sort_shirt.png'),
	(3, '女鞋 /男鞋 /箱包', NULL, 'sort_shoe.png'),
	(4, '美妆 /个人护理', NULL, 'sort_lipstick.png'),
	(5, '腕表 /眼镜 /珠宝饰品', NULL, 'sort_diamond.png'),
	(6, '手机 /数码 /电脑办公', NULL, 'sort_phone.png'),
	(7, '母婴玩具', NULL, 'sort_cart.png'),
	(8, '零食 /茶酒 /进口食品', NULL, 'sort_noodles.png'),
	(9, '生鲜水果', NULL, 'sort_cherry.png'),
	(10, '大家电 /生活电器', NULL, 'sort_oven.png'),
	(11, '家具建材', NULL, 'sort_furniture.png'),
	(12, '汽车 /配件 /用品', NULL, 'sort_car.png'),
	(13, '家纺 /家饰 /鲜花', NULL, 'sort_sofa.png'),
	(14, '医药保健', NULL, 'sort_medicine.png'),
	(15, '厨具 /收纳 /宠物', NULL, 'sort_cup.png'),
	(16, '图书音像', NULL, 'sort_book.png'),
	(17, '当季流行', 1, NULL),
	(18, '精选上装', 1, NULL),
	(19, '浪漫裙装', 1, NULL),
	(20, '女士下装', 1, NULL),
	(21, '特色女装', 1, NULL),
	(22, '文胸塑身', 1, NULL),
	(23, '家居服', 1, NULL),
	(24, '内裤背心', 1, NULL),
	(25, '当季流行', 2, NULL),
	(26, '男士外套', 2, NULL),
	(27, '男士内搭', 2, NULL),
	(28, '男士裤装', 2, NULL),
	(29, '特色男装', 2, NULL),
	(30, '运动鞋', 2, NULL),
	(31, '运动服', 2, NULL),
	(32, '户外鞋服', 2, NULL),
	(33, '推荐女鞋', 3, NULL),
	(34, '潮流男鞋', 3, NULL),
	(35, '女单鞋', 3, NULL),
	(36, '特色鞋', 3, NULL),
	(37, '潮流女包', 3, NULL),
	(38, '精品男包', 3, NULL),
	(39, '功能箱包', 3, NULL),
	(40, '护肤品', 4, NULL),
	(41, '彩妆', 4, NULL),
	(42, '男士护肤', 4, NULL),
	(43, '肤质推选', 4, NULL),
	(44, '美发护发', 4, NULL),
	(45, '口腔护理', 4, NULL),
	(46, '身体女性', 4, NULL),
	(47, '黄金首饰', 5, NULL),
	(48, '钻石彩宝', 5, NULL),
	(49, '珍珠玉翠', 5, NULL),
	(50, '潮流饰品', 5, NULL),
	(51, '腕表', 5, NULL),
	(52, '眼镜', 5, NULL),
	(53, '烟具', 5, NULL),
	(54, '童装', 7, NULL),
	(55, '婴儿服', 7, NULL),
	(56, '童鞋', 7, NULL),
	(57, '车床用品', 7, NULL),
	(58, '喂养', 7, NULL),
	(59, '洗护', 7, NULL),
	(60, '玩具', 7, NULL),
	(61, '天猫动漫', 7, NULL),
	(62, '奶粉', 7, NULL),
	(63, '纸尿裤', 7, NULL),
	(64, '孕产', 7, NULL),
	(65, '热门手机', 6, NULL),
	(66, '特色手机', 6, NULL),
	(67, '手机优惠', 6, NULL),
	(68, '电脑整机', 6, NULL),
	(69, '智能数码', 6, NULL),
	(70, '游戏组装', 6, NULL),
	(71, '硬件存储', 6, NULL),
	(72, '摄影摄像', 6, NULL),
	(73, '影音娱乐', 6, NULL),
	(74, '办公文教', 6, NULL),
	(75, '数码配件', 6, NULL),
	(76, '进口食品', 8, NULL),
	(77, '休闲零食', 8, NULL),
	(78, '酒类', 8, NULL),
	(79, '茶叶', 8, NULL),
	(80, '乳品冲饮', 8, NULL),
	(81, '粮油速食', 8, NULL),
	(82, '生鲜水果', 8, NULL),
	(83, '新鲜蔬菜', 9, NULL),
	(84, '冰淇淋', 9, NULL),
	(85, '蛋类', 9, NULL),
	(86, '肉类', 9, NULL),
	(87, '海鲜水产', 9, NULL),
	(88, '新鲜水果', 9, NULL),
	(89, '精选干货', 9, NULL),
	(90, '平板电视', 10, NULL),
	(91, '空调', 10, NULL),
	(92, '冰箱', 10, NULL),
	(93, '洗衣机', 10, NULL),
	(94, '厨房大电', 10, NULL),
	(95, '热水器', 10, NULL),
	(96, '中式厨房', 10, NULL),
	(97, '西式厨房', 10, NULL),
	(98, '生活电器', 10, NULL),
	(99, '个护健康', 10, NULL),
	(100, '精品推荐', 10, NULL),
	(101, '成套家具', 11, NULL),
	(102, '客厅餐厅', 11, NULL),
	(103, '卧室家具', 11, NULL),
	(104, '书法儿童', 11, NULL),
	(105, '家装主材', 11, NULL),
	(106, '厨房卫浴', 11, NULL),
	(107, '灯饰照明', 11, NULL),
	(108, '五金工具', 11, NULL),
	(109, '全屋定制', 11, NULL),
	(110, '装修设计', 11, NULL),
	(111, '整车', 12, NULL),
	(112, '座垫脚垫', 12, NULL),
	(113, '机油轮胎', 12, NULL),
	(114, '电子导航', 12, NULL),
	(115, '车载电器', 12, NULL),
	(116, '维修保养', 12, NULL),
	(117, '美容清洗', 12, NULL),
	(118, '汽车装饰', 12, NULL),
	(119, '安全自驾', 12, NULL),
	(120, '外饰改装', 12, NULL),
	(121, '汽车服务', 12, NULL),
	(122, '当季热卖', 13, NULL),
	(123, '床上用品', 13, NULL),
	(124, '居家布艺', 13, NULL),
	(125, '家居饰品', 13, NULL),
	(126, '鲜花绿植', 13, NULL),
	(127, '保健品', 14, NULL),
	(128, '滋补品', 14, NULL),
	(129, '医药', 14, NULL),
	(130, '医疗器械', 14, NULL),
	(131, '隐形眼镜', 14, NULL),
	(132, '医疗服务', 14, NULL),
	(133, '厨房烹饪', 15, NULL),
	(134, '餐饮具', 15, NULL),
	(135, '家具礼品', 15, NULL),
	(136, '收纳清洁', 15, NULL),
	(137, '纸品清洁', 15, NULL),
	(138, '宠物用品', 15, NULL),
	(139, '儿童读物', 16, NULL),
	(140, '畅销小说', 16, NULL),
	(141, '文学文艺', 16, NULL),
	(142, '社科生活', 16, NULL),
	(143, '育儿百科', 16, NULL),
	(144, '学习考试', 16, NULL),
	(145, '教材教辅', 16, NULL),
	(146, '经管励志', 16, NULL),
	(147, '期刊杂志', 16, NULL),
	(148, '进口原装', 16, NULL),
	(149, '大牌乐器', 16, NULL),
	(150, '女装新品', 17, NULL),
	(151, '商场同款', 17, NULL),
	(152, '仙女连衣裙', 17, NULL),
	(153, 'T恤', 17, NULL),
	(154, '衬衫', 17, NULL),
	(155, '时髦外套', 17, NULL),
	(156, '休闲裤', 17, NULL),
	(157, '牛仔裤', 17, NULL),
	(158, '无痕文胸', 17, NULL),
	(159, '运动文胸', 17, NULL),
	(160, '潮流家居服', 17, NULL),
	(161, '百搭船袜', 17, NULL),
	(162, '毛妮外套', 18, NULL),
	(163, '羽绒服', 18, NULL),
	(164, '锦服', 18, NULL),
	(165, '丝绒卫衣', 18, NULL),
	(166, '毛针织衫', 18, NULL),
	(167, '皮毛一体', 18, NULL),
	(168, '皮草', 18, NULL),
	(169, '毛衣', 18, NULL),
	(170, '衬衫', 18, NULL),
	(171, '卫衣', 18, NULL),
	(172, '针织衫', 18, NULL),
	(173, 'T恤', 18, NULL),
	(174, '短外套', 18, NULL),
	(175, '小西装', 18, NULL),
	(176, '风衣', 18, NULL),
	(177, '连衣裙', 19, NULL),
	(178, '半身裙', 19, NULL),
	(179, 'A字裙', 19, NULL),
	(180, '荷叶边裙', 19, NULL),
	(181, '大摆裙', 19, NULL),
	(182, '包臀裙', 19, NULL),
	(183, '百褶裙', 19, NULL),
	(184, '长袖连衣裙', 19, NULL),
	(185, '棉麻连衣裙', 19, NULL),
	(186, '牛仔裙', 19, NULL),
	(187, '蕾丝连衣裙', 19, NULL),
	(188, '真丝连衣裙', 19, NULL),
	(189, '印花连衣裙', 19, NULL),
	(190, '休闲裤', 20, NULL),
	(191, '阔腿裤', 20, NULL),
	(192, '牛仔裤', 20, NULL),
	(193, '打底裤', 20, NULL),
	(194, '开叉运动裤', 20, NULL),
	(195, '哈伦裤', 20, NULL),
	(196, '背带裤', 20, NULL),
	(197, '小脚裤', 20, NULL),
	(198, '西装裤', 20, NULL),
	(199, '短裤', 20, NULL),
	(200, '时尚套装', 21, NULL),
	(201, '休闲套装', 21, NULL),
	(202, '日系女装', 21, NULL),
	(203, '精选妈妈装', 21, NULL),
	(204, '大码女装', 21, NULL),
	(205, '职业套装', 21, NULL),
	(206, '优雅旗袍', 21, NULL),
	(207, '精致礼服', 21, NULL),
	(208, '婚纱', 21, NULL),
	(209, '唐装', 21, NULL),
	(210, '小码女装', 21, NULL),
	(211, '光面文胸', 22, NULL),
	(212, '运动文胸', 22, NULL),
	(213, '美背文胸', 22, NULL),
	(214, '聚拢文胸', 22, NULL),
	(215, '大杯文胸', 22, NULL),
	(216, '轻薄塑身', 22, NULL),
	(217, '春夏家居服', 23, NULL),
	(218, '纯棉家居服', 23, NULL),
	(219, '莫代尔家居服', 23, NULL),
	(220, '真丝家居服', 23, NULL),
	(221, '春夏睡裙', 23, NULL),
	(222, '男士家居服', 23, NULL),
	(223, '情侣家居服', 23, NULL),
	(224, '性感睡裙', 23, NULL),
	(225, '男士内裤', 24, NULL),
	(226, '女士内裤', 24, NULL),
	(227, '男士内裤多条装', 24, NULL),
	(228, '女士内裤多条装', 24, NULL),
	(229, '莫代尔内裤', 24, NULL),
	(230, '当季新品', 25, NULL),
	(231, '商场同款', 25, NULL),
	(232, '羽绒服', 25, NULL),
	(233, '潮流卫衣', 25, NULL),
	(234, '牛仔衬衫', 25, NULL),
	(235, '修身夹克', 25, NULL),
	(236, '保暖棉服', 25, NULL),
	(237, '温暖的针织衫', 25, NULL),
	(238, '修身西装', 25, NULL),
	(239, '秋冬风衣', 25, NULL),
	(240, '束脚裤', 25, NULL),
	(241, '破洞牛仔裤', 25, NULL),
	(242, '跑步鞋', 25, NULL),
	(243, '休闲鞋', 25, NULL),
	(244, '篮球鞋', 25, NULL),
	(245, '自行车', 25, NULL),
	(246, '平衡车', 25, NULL),
	(247, '帆布鞋', 25, NULL),
	(248, '运动套装', 25, NULL),
	(249, '运动卫衣', 25, NULL),
	(250, '鱼竿', 25, NULL),
	(251, '冲锋衣', 25, NULL),
	(252, '跑步机', 25, NULL),
	(253, '电动车', 25, NULL);
/*!40000 ALTER TABLE `e_sort` ENABLE KEYS */;

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

-- 正在导出表  e_commerce.e_spike_commodity 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `e_spike_commodity` DISABLE KEYS */;
INSERT INTO `e_spike_commodity` (`id`, `price`, `store`, `description`, `start_time`, `session_id`, `data_flag`, `commodity_id`) VALUES
	(1, 1, 100, '无线鼠标描述', '2019-11-03 11:00:00', 1, b'1', 1),
	(2, 1, 99, '无线键盘描述', '2019-11-04 10:56:00', 1, b'1', 2),
	(3, 1, 100, '特色男士球衣描述', '2019-11-11 10:00:00', 2, b'1', 3);
/*!40000 ALTER TABLE `e_spike_commodity` ENABLE KEYS */;

-- 导出  表 e_commerce.e_spike_session 结构
CREATE TABLE IF NOT EXISTS `e_spike_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '秒杀场次名称',
  `description` varchar(100) NOT NULL COMMENT '活动描述',
  `data_flag` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='秒杀场次表';

-- 正在导出表  e_commerce.e_spike_session 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `e_spike_session` DISABLE KEYS */;
INSERT INTO `e_spike_session` (`id`, `name`, `description`, `data_flag`) VALUES
	(1, '第一场秒杀', '<a>11月3日&nbsp;-&nbsp;4日</a>&nbsp;<a>11:00</a>整点开始', b'1'),
	(2, '第二场秒杀', '<a>11月11日&nbsp;-&nbsp;11日</a>&nbsp;<a>10:00</a>整点开始', b'1');
/*!40000 ALTER TABLE `e_spike_session` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
