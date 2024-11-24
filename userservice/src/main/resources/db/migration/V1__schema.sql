DROP TABLE IF EXISTS aviation_user;

CREATE TABLE aviation_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    email VARCHAR(64) UNIQUE NOT NULL,
    phone_number VARCHAR(16),
    age INTEGER
);
