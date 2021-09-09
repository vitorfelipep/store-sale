
CREATE TABLE IF NOT EXISTS store_order (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT(50) NOT NULL,
    customer_id BIGINT(50) NOT NULL,
    inventory_id BIGINT(50) NOT NULL,
    store_id BIGINT(50) NOT NULL,
    status_order VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    tracking_code VARCHAR(255) NULL,
    order_date TIMESTAMP NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    change_date TIMESTAMP NULL DEFAULT NULL,
    creation_user VARCHAR (255) NOT NULL,
    change_user VARCHAR (255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
