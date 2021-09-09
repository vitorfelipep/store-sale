CREATE TABLE IF NOT EXISTS product (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    catalog_id BIGINT(20) not null,
    creation_date TIMESTAMP NOT NULL,
    change_date TIMESTAMP NULL DEFAULT NULL,
    creation_user VARCHAR (255) NOT NULL,
    change_user VARCHAR (255),
    FOREIGN KEY (catalog_id) REFERENCES store_catalog(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
