CREATE TABLE IF NOT EXISTS product_tracking (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (255) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    tracking_id BIGINT(20) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    change_date TIMESTAMP NULL DEFAULT NULL,
    FOREIGN KEY (tracking_id) REFERENCES tracking_order(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
