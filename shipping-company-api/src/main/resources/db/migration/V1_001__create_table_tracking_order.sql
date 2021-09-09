
CREATE TABLE IF NOT EXISTS tracking_order (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT(50) NOT NULL,
    tracking_code VARCHAR(255) NOT NULL,
    status_shipping VARCHAR(50) NOT NULL,
    total_order DECIMAL(10,2) NOT NULL,
    sender_id BIGINT(20) NOT NULL,
    addressee_id BIGINT(20) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    change_date TIMESTAMP NULL DEFAULT NULL,
    FOREIGN KEY (sender_id) REFERENCES target_address(id),
    FOREIGN KEY (addressee_id) REFERENCES target_address(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
