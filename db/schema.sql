CREATE TABLE Product (
   id INT PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   price DOUBLE PRECISION NOT NULL,
   creation_datetime TIMESTAMP NOT NULL
);

CREATE TABLE Product_category (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    product_id INT NOT NULL,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES Product(id) ON DELETE CASCADE
);
