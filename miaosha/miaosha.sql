创建商品表

CREATE TABLE goods(

id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',

goods_name varchar(16) DEFAULT NULL COMMENT '商品名称',

goods_title varchar(64) DEFAULT NULL COMMENT '商品标题',

goods_img varchar(64) DEFAULT NULL COMMENT '商品图片',

goods_detail longtext COMMENT '商品详情介绍',

goods_price decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',

goods_stock int(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',

PRIMARY KEY(id)

)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;



插入商品表数据

INSERT INTO goods VALUES(1,'iphoneX','Apple iPhone X 银色 移动联通电信4G手机','/img/iphonex.png','Apple iPhone X(A1865)',8765.00,10000),(2,'华为 meta9','华为 meta9 4GB+32GB 月光银 移动联通电信4G手机','/img/meta10.png','华为 meta9 4GB+32GB 月光银 移动联通电信4G手机',3212.00,-1);



秒杀商品表

CREATE TABLE miaosha_goods(

id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表',

goods_id bigint(20) DEFAULT NULL COMMENT '商品id',

miaosha_price decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价',

stock_count int(11) DEFAULT NULL COMMENT '库存数量',

start_date datetime DEFAULT NULL COMMENT '秒杀开始时间',

end_date datetime DEFAULT NULL COMMENT '秒杀结束时间',

PRIMARY KEY(id)

)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;



插入秒杀商品表

INSERT INTO miaosha_goods VALUES (1,1,0.01,4,'2019-06-05 11:30:00','2019-06-05 11:40:00'),(2,2,0.01,9,'2019-06-05 11:30:00','2019-06-05 11:40:00');



创建订单表

CREATE TABLE order_info(

id bigint(20) NOT NULL AUTO_INCREMENT,

user_id bigint(20) DEFAULT NULL COMMENT '用户id',

goods_id bigint(20) DEFAULT NULL COMMENT '商品id',

delivery_addr_id bigint(20) DEFAULT NULL COMMENT '收货地址id',

goods_name varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',

goods_count int(11) DEFAULT '0' COMMENT '商品数量',

goods_price decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',

order_channel tinyint(4) DEFAULT '0' COMMENT '1pc, 2android, 3ios',

status tinyint(4) DEFAULT '0' COMMENT '订单状态， 0新建未支付， 1已支付， 2已发货， 3已收货， 4已退款， 5已完成',

create_date datetime DEFAULT NULL COMMENT '订单创建的时间',

pay_date datetime DEFAULT NULL COMMENT '支付时间',

PRIMARY KEY (id)

)ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;



创建秒杀订单表

CREATE TABLE miaosha_order(

id bigint(20) NOT NULL AUTO_INCREMENT,

user_id bigint(20) DEFAULT NULL COMMENT '用户id',

order_id bigint(20) DEFAULT NULL COMMENT '订单id',

goods_id bigint(20) DEFAULT NULL COMMENT '商品id',

PRIMARY KEY(id)

)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

作者：小y同学hh
链接：https://www.jianshu.com/p/a74455734183
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。