-- Insert 5 Categories
INSERT INTO categories (name, icon) VALUES ('Electronics', 'laptop-icon.png');
INSERT INTO categories (name, icon) VALUES ('Smartphones', 'phone-icon.png');
INSERT INTO categories (name, icon) VALUES ('Fashion', 'shirt-icon.png');
INSERT INTO categories (name, icon) VALUES ('Home Appliances', 'home-icon.png');
INSERT INTO categories (name, icon) VALUES ('Books', 'book-icon.png');

-- Insert 15 Products with varied prices
INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('MacBook Pro M3', 1999.99, 10, 'Apple M3 Pro Laptop', 'macbook.jpg', 1);
INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('Dell XPS 15', 1499.50, 15, 'High performance Windows Laptop', 'dell_xps.jpg', 1);
INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('iPad Air M2', 599.00, 20, 'Apple 11-inch tablet', 'ipad.jpg', 1);

INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('iPhone 15 Pro Max', 1199.99, 25, 'Titanium smartphone', 'iphone15.jpg', 2);
INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('Samsung Galaxy S24 Ultra', 1299.99, 18, 'AI Powered flagship', 's24.jpg', 2);
INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('Google Pixel 8 Pro', 899.00, 12, 'Google AI Smartphone', 'pixel8.jpg', 2);

INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('Men Cotton T-Shirt', 19.99, 100, '100% Organic Cotton', 'tshirt.jpg', 3);
INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('Denim Jacket', 79.50, 40, 'Classic Blue Denim', 'jacket.jpg', 3);
INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('Running Shoes', 120.00, 30, 'Lightweight sport sneakers', 'shoes.jpg', 3);

INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('Air Conditioner 12000 BTU', 450.00, 8, 'Energy efficient AC unit', 'ac.jpg', 4);
INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('Robot Vacuum Cleaner', 299.99, 14, 'Smart automated vacuum', 'robot_vac.jpg', 4);
INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('Espresso Coffee Machine', 180.00, 22, 'High pressure Italian brewer', 'coffee.jpg', 4);

INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('Clean Code', 35.00, 50, 'A Handbook of Agile Software Craftsmanship', 'cleancode.jpg', 5);
INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('Spring Boot in Action', 42.50, 45, 'Comprehensive Spring Boot guide', 'springboot.jpg', 5);
INSERT INTO products (name, price, quantity, description, images, category_id) VALUES ('System Design Interview', 39.99, 60, 'An Insider Guide to Tech Interviews', 'sysdesign.jpg', 5);
