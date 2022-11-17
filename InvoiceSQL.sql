DROP TABLE IF EXISTS invoice_detail;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS invoice;
DROP TABLE IF EXISTS customer;

CREATE TABLE customer(
customer_id INT AUTO_INCREMENT NOT NULL,
customer_name VARCHAR(128),
address VARCHAR(128),
phone_number VARCHAR(128),
PRIMARY KEY (customer_id)
);


CREATE TABLE invoice(
invoice_id INT AUTO_INCREMENT NOT NULL,
customer_id INT NOT NULL,
total_price DECIMAL (9,2),
open_date TEXT,
close_date TEXT,
comments TEXT,
PRIMARY KEY (invoice_id),
FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);

CREATE TABLE inventory(
inventory_id INT AUTO_INCREMENT NOT NULL,
item_name VARCHAR(128),
stock int,
item_price DECIMAL (9,2),
PRIMARY KEY (inventory_id)
);

CREATE TABLE invoice_detail (
invoice_id INT NOT NULL,
inventory_id INT NOT NULL,
quantity INT NOT NULL,
FOREIGN KEY (invoice_id) REFERENCES invoice (invoice_id) ON DELETE CASCADE,
FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id) ON DELETE CASCADE,
UNIQUE KEY (invoice_id,inventory_id)
);
