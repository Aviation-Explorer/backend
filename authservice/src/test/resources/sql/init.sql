CREATE TABLE IF NOT EXISTS users (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL UNIQUE,
    phone_number VARCHAR(16),
    age INT(11),
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO users (name, surname, email, phone_number, age, password) VALUES
('John', 'Doe', 'user@email.com', '1234567890', 30, '$2y$10$1Rhj.OwzkrlCBKxQ64OMUuuDFnb74FWS0zUlirvT56F3vwXPfYV7W');
