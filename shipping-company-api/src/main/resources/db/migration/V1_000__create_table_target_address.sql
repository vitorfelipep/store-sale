SET GLOBAL sql_mode='';
CREATE TABLE IF NOT EXISTS target_address (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    cnpj_cpf VARCHAR(14) NOT NULL,
    name VARCHAR(50) NOT NULL,
    type_target_address VARCHAR(255) NOT NULL,
    cep INTEGER(25) NOT NULL,
    street VARCHAR(255) NOT NULL,
    address_number VARCHAR(10) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state_address VARCHAR(2) NOT NULL,
    country VARCHAR(50) NOT NULL,
    reference_point VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

