CREATE DATABASE IF NOT EXISTS shopqa_db;
USE shopqa_db;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

CREATE TABLE categories (
  category_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
  product_id INT PRIMARY KEY AUTO_INCREMENT,
  category_id INT,
  name VARCHAR(200) NOT NULL,
  description TEXT,
  price DECIMAL(10,2) NOT NULL,
  stock_qty INT DEFAULT 0,
  image_url VARCHAR(500),
  is_active TINYINT(1) DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_products_categories FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE users (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  full_name VARCHAR(150) NOT NULL,
  email VARCHAR(200) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role ENUM('customer','admin') DEFAULT 'customer',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cart (
  cart_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  session_id VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_cart_users FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE cart_items (
  cart_item_id INT PRIMARY KEY AUTO_INCREMENT,
  cart_id INT,
  product_id INT,
  quantity INT NOT NULL DEFAULT 1,
  added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_cart_items_cart FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
  CONSTRAINT fk_cart_items_products FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE orders (
  order_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  total_amount DECIMAL(10,2) NOT NULL,
  status ENUM('pending','processing','shipped','delivered','cancelled') DEFAULT 'pending',
  shipping_name VARCHAR(150),
  shipping_address TEXT,
  shipping_city VARCHAR(100),
  shipping_zip VARCHAR(20),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_orders_users FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE order_items (
  order_item_id INT PRIMARY KEY AUTO_INCREMENT,
  order_id INT,
  product_id INT,
  quantity INT NOT NULL,
  unit_price DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_order_items_orders FOREIGN KEY (order_id) REFERENCES orders(order_id),
  CONSTRAINT fk_order_items_products FOREIGN KEY (product_id) REFERENCES products(product_id)
);

INSERT INTO categories (name, description) VALUES
  ('Electronics', 'Gadgets, devices, and accessories'),
  ('Clothing', 'Everyday apparel and essentials'),
  ('Books', 'Fiction, learning, and reference books');

INSERT INTO products (category_id, name, description, price, stock_qty, image_url, is_active) VALUES
  (1, 'Wireless Headphones', 'Comfortable Bluetooth headphones with long battery life.', 79.99, 25, 'images/placeholder.png', 1),
  (1, 'USB-C Dock', 'Compact dock with HDMI, USB, and charging support.', 49.99, 40, 'images/placeholder.png', 1),
  (2, 'Cotton T-Shirt', 'Soft crew-neck T-shirt for everyday wear.', 19.99, 80, 'images/placeholder.png', 1),
  (2, 'Denim Jacket', 'Classic denim jacket with durable stitching.', 64.50, 18, 'images/placeholder.png', 1),
  (3, 'Clean Code Handbook', 'Practical patterns for writing readable software.', 34.95, 30, 'images/placeholder.png', 1),
  (3, 'Mystery Novel', 'A suspenseful page-turner for weekend reading.', 14.99, 45, 'images/placeholder.png', 1);

INSERT INTO users (full_name, email, password_hash, role) VALUES
  ('ShopQA Admin', 'admin@shopqa.com', 'e86f78a8a3caf0b60d8e74e5942aa6d86dc150cd3c03338aef25b7d2d7e3acc7', 'admin');

