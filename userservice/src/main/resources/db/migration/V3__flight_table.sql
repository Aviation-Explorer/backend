CREATE TABLE IF NOT EXISTS aviation_user_flight (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    aviation_user_email VARCHAR(64),
    departure_airport VARCHAR(128) NOT NULL,
    arrival_airport VARCHAR(128) NOT NULL,
    flight_date DATE,
    departure_terminal VARCHAR(16),
    departure_gate VARCHAR(8),
    arrival_terminal VARCHAR(16),
    arrival_gate VARCHAR(8),
    FOREIGN KEY (aviation_user_email) REFERENCES aviation_user(email)
);
