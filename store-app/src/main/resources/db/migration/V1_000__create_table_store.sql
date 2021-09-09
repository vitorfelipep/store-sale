CREATE TABLE IF NOT EXISTS store (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    cep INTEGER(25) NOT NULL,
    street VARCHAR(255) NOT NULL,
    address_number VARCHAR(10) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state_address VARCHAR(2) NOT NULL,
    country VARCHAR(50) NOT NULL,
    reference_point VARCHAR(50) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    change_date TIMESTAMP NULL DEFAULT NULL,
    creation_user VARCHAR(255) NOT NULL,
    change_user VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

