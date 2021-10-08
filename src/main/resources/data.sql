DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS products;

CREATE TABLE products (
id INT AUTO_INCREMENT  PRIMARY KEY,
SKU VARCHAR(12) NOT NULL UNIQUE,
NAME VARCHAR(250) NOT NULL,
BRAND VARCHAR(250) NOT NULL,
SIZE VARCHAR(250) DEFAULT NULL,
PRICE FLOAT(1) NOT NULL,
PRINCIPAL_IMAGE VARCHAR(250) NOT NULL
);

CREATE TABLE images (
                          id INT AUTO_INCREMENT  PRIMARY KEY,
                          URL VARCHAR(250) NOT NULL,
                          FK_PRODUCT INT,
                          PRIMARY KEY (id),
                          FOREIGN KEY (FK_PRODUCT) REFERENCES products(id)
);


INSERT INTO products (SKU, NAME, BRAND,SIZE,PRICE,PRINCIPAL_IMAGE) VALUES
('FAL-8406270', '500 Zapatilla Urbana Mujer', 'Ferrari','20 cm',20,'https://falabella.scene7.com/is/image/Falabella/principal1.png'),
('FAL-88195228', 'Bicicleta  Aro 29', 'Honda','10 m',300,'https://falabella.scene7.com/is/image/Falabella/principal2.jpg'),
('FAL-88189852', 'Camisa Manga Corta Hombre', 'Toyota','100 m',576.21,'https://falabella.scene7.com/is/image/Falabella/Principal3.jpg');

INSERT INTO images (URL,FK_PRODUCT) VALUES
('https://falabella.scene7.com/is/image/Falabella/1',1),
('https://falabella.scene7.com/is/image/Falabella/2',1),
('https://falabella.scene7.com/is/image/Falabella/3',1),
('https://falabella.scene7.com/is/image/hola',1),
('https://falabella.scene7.com/is/image/holadenuevo',2),
('https://falabella.scene7.com/is/image/hallooo',2),
('https://falabella.scene7.com/is/image/anyone.jpg',2),
('https://falabella.scene7.com/is/image/imagendel3.png',3);


