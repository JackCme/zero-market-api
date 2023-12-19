drop table if exists cart_item;

drop table if exists cart_info;

drop table if exists order_item;

drop table if exists order_info;

drop table if exists product;

drop table if exists user_account;


-- product: table
CREATE TABLE `product` (
                           `product_id` int NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) DEFAULT NULL,
                           `in_stock` int DEFAULT NULL,
                           `price` int DEFAULT NULL,
                           PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품정보';

-- user_account: table
CREATE TABLE `user_account` (
                                `uid` int NOT NULL AUTO_INCREMENT,
                                `email` varchar(100) NOT NULL,
                                `password` varchar(255) NOT NULL,
                                PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원정보 테이블';

-- cart_info: table
CREATE TABLE `cart_info` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `item_count` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`cart_id`),
  KEY `cart_info_user_account_uid_fk` (`user_id`),
  CONSTRAINT `cart_info_user_account_uid_fk` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원 장바구니 정보';

-- cart_item: table
CREATE TABLE `cart_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cart_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `product_cnt` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `cart_product_product_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `cart_product_user_cart_cart_id_fk` FOREIGN KEY (`cart_id`) REFERENCES `cart_info` (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='장바구니 상품정보';

-- order_info: table
CREATE TABLE `order_info` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `order_info_user_account_uid_fk` (`user_id`),
  CONSTRAINT `order_info_user_account_uid_fk` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문정보';

-- order_item: table
CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `count` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_item_product_product_id_fk` (`product_id`),
  CONSTRAINT `order_item_order_info_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `order_info` (`order_id`),
  CONSTRAINT `order_item_product_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문 상품정보';


