CREATE TABLE cards (id INT AUTO_INCREMENT PRIMARY KEY,
number BIGINT AUTO_INCREMENT(1234123412341234, 1) NOT NULL,
status BOOLEAN NOT NULL);

CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY,
login VARCHAR(255) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL);

CREATE TABLE accounts (id INT AUTO_INCREMENT PRIMARY KEY,
number BIGINT AUTO_INCREMENT NOT NULL,
money INT NOT NULL);

CREATE TABLE transactions (id INT AUTO_INCREMENT PRIMARY KEY,
from_user_id INT NOT NULL,
to_user_id INT NOT NULL,
counts INT NOT NULL,
status BOOLEAN NOT NULL,
FOREIGN KEY (from_user_id) REFERENCES users(id) ON DELETE CASCADE,
FOREIGN KEY (to_user_id) REFERENCES users(id) ON DELETE CASCADE);

CREATE TABLE roles (id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT NOT NULL,
role VARCHAR(255) NOT NULL,
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE);

CREATE TABLE AccountToUser (id INT AUTO_INCREMENT PRIMARY KEY,
account_id INT NOT NULL,
user_id INT NOT NULL,
FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE);

CREATE TABLE CardsToAccount (id INT AUTO_INCREMENT PRIMARY KEY,
account_id INT NOT NULL,
card_id INT NOT NULL,
FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE);

INSERT INTO cards (status) VALUES (TRUE), (TRUE), (TRUE);
INSERT INTO users (login, password) VALUES ('admin@gmail.com', 'password'), ('user@gmail.com', 'password');
INSERT INTO accounts (money) VALUES (10000), (250000);
INSERT INTO transactions (from_user_id, to_user_id, counts, status) VALUES (1,2,5000,TRUE);
INSERT INTO roles (user_id, role) VALUES (1,'USER'), (2,'USER'), (2,'ADMIN');
INSERT INTO AccountToUser (account_id, user_id) VALUES (1,1), (2,2);
INSERT INTO CardsToAccount (account_id, card_id) VALUES (1,1), (2,2);


