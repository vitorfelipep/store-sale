CREATE TABLE IF NOT EXISTS order_item (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT(50) NOT NULL,
    product_id BIGINT(50) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    change_date TIMESTAMP NULL DEFAULT NULL,
    creation_user VARCHAR (255) NOT NULL,
    change_user VARCHAR (255),
    FOREIGN KEY (order_id) REFERENCES store_order(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
