DROP DATABASE IF EXISTS player_card_db;
CREATE DATABASE player_card_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE player_card_db;

CREATE TABLE members(
 id INT AUTO_INCREMENT PRIMARY KEY,
 account VARCHAR(50) NOT NULL UNIQUE,
 password VARCHAR(100) NOT NULL,
 name VARCHAR(100) NOT NULL,
 email VARCHAR(100), phone VARCHAR(30), role VARCHAR(20) DEFAULT 'USER',
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE player_cards(
 id INT AUTO_INCREMENT PRIMARY KEY,
 card_name VARCHAR(100) NOT NULL, player_name VARCHAR(100) NOT NULL,
 sport VARCHAR(50), team VARCHAR(100), card_year INT, brand VARCHAR(100),
 card_number VARCHAR(50), grade VARCHAR(20), price DECIMAL(10,2) DEFAULT 0,
 quantity INT DEFAULT 0, description TEXT, image_path VARCHAR(255),
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE favorites(id INT AUTO_INCREMENT PRIMARY KEY, member_id INT, card_id INT, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(member_id) REFERENCES members(id) ON DELETE CASCADE, FOREIGN KEY(card_id) REFERENCES player_cards(id) ON DELETE CASCADE);
CREATE TABLE cart_items(id INT AUTO_INCREMENT PRIMARY KEY, member_id INT, card_id INT, quantity INT DEFAULT 1, price DECIMAL(10,2), created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(member_id) REFERENCES members(id) ON DELETE CASCADE, FOREIGN KEY(card_id) REFERENCES player_cards(id) ON DELETE CASCADE);
CREATE TABLE orders(id INT AUTO_INCREMENT PRIMARY KEY, member_id INT, total DECIMAL(10,2), status VARCHAR(30) DEFAULT '未付款', created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(member_id) REFERENCES members(id));
CREATE TABLE order_items(id INT AUTO_INCREMENT PRIMARY KEY, order_id INT, card_id INT, quantity INT, price DECIMAL(10,2), FOREIGN KEY(order_id) REFERENCES orders(id) ON DELETE CASCADE, FOREIGN KEY(card_id) REFERENCES player_cards(id));
CREATE TABLE scan_records(id INT AUTO_INCREMENT PRIMARY KEY, member_id INT, image_path VARCHAR(255), keyword VARCHAR(100), result_text TEXT, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(member_id) REFERENCES members(id) ON DELETE CASCADE);
CREATE TABLE price_history(id INT AUTO_INCREMENT PRIMARY KEY, card_id INT, market_price DECIMAL(10,2), record_date DATE, note VARCHAR(255), FOREIGN KEY(card_id) REFERENCES player_cards(id) ON DELETE CASCADE);
CREATE TABLE sports_categories(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50));
CREATE TABLE grading_companies(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50));
CREATE TABLE trade_posts(id INT AUTO_INCREMENT PRIMARY KEY, member_id INT, card_id INT, title VARCHAR(100), content TEXT, status VARCHAR(20) DEFAULT 'OPEN', created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE login_logs(id INT AUTO_INCREMENT PRIMARY KEY, member_id INT, account VARCHAR(50), success BOOLEAN, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE admin_logs(id INT AUTO_INCREMENT PRIMARY KEY, admin_id INT, action VARCHAR(255), created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

INSERT INTO members(account,password,name,email,phone,role) VALUES ('admin','1234','系統管理員','admin@cardarena.com','0900000000','ADMIN'),('user','1234','一般會員','user@cardarena.com','0911111111','USER');
INSERT INTO sports_categories(name) VALUES ('Basketball'),('Baseball'),('Football'),('Soccer'),('Pokemon');
INSERT INTO grading_companies(name) VALUES ('PSA'),('BGS'),('SGC'),('CGC');
INSERT INTO player_cards(card_name,player_name,sport,team,card_year,brand,card_number,grade,price,quantity,description,image_path) VALUES
('Michael Jordan PSA10','Michael Jordan','Basketball','Chicago Bulls',1998,'Upper Deck','MJ-001','PSA10',50000,2,'經典籃球球員卡','mj.jpg'),
('Shohei Ohtani PSA10','Shohei Ohtani','Baseball','Los Angeles Dodgers',2024,'Topps','SHO-001','PSA10',15000,5,'大谷翔平球員卡','ohtani.jpg'),
('Stephen Curry PSA9','Stephen Curry','Basketball','Golden State Warriors',2023,'Panini','CUR-001','PSA9',12000,3,'Curry 三分球收藏卡','curry.jpg');
INSERT INTO price_history(card_id,market_price,record_date,note) VALUES (1,48000,'2026-01-01','年初行情'),(1,50000,'2026-06-01','近期成交'),(2,13000,'2026-01-01','年初行情'),(2,15000,'2026-06-01','近期成交');
