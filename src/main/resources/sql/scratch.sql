CREATE DATABASE binary_bank_system;

USE binary_bank_system;

CREATE TABLE users (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(300) NOT NULL,
    salt VARCHAR(300) NOT NULL
);

CREATE TABLE accounts (
     account_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     user_id INT NOT NULL,
     balance DECIMAL(7,2) NOT NULL DEFAULT 0.00,
     account_type VARCHAR(20) NOT NULL DEFAULT '',
     interest_rate DECIMAL(4,2) NOT NULL DEFAULT 0.00,
     minimum_payment DECIMAL(7,2) DEFAULT 0.00,
     credit_limit DECIMAL(7,2) DEFAULT 0.00,
     FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE transactions (
    transaction_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    from_id INT  NOT NULL,
    to_id INT  NOT NULL,
    amount DECIMAL(7,2) NOT NULL DEFAULT 0.0,
    _timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transaction_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    transaction_type VARCHAR(50) NOT NULL DEFAULT '',
    FOREIGN KEY(from_id) REFERENCES accounts(account_id),
    FOREIGN KEY(to_id) REFERENCES accounts(account_id)
);

ALTER TABLE transactions MODIFY COLUMN from_id INT NULL;
ALTER TABLE transactions DROP FOREIGN KEY transactions_ibfk_1;