DROP TABLE IF EXISTS sale_transaction;
CREATE TABLE sale_transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_id CHAR NOT NULL,
    date DATE NOT NULL,
    sale DECIMAL NOT NULL,
    tax DECIMAL NOT NULL,
    state CHAR NOT NULL,
    zipcode CHAR NOT NULL
);

DROP TABLE IF EXISTS sale_summary;
CREATE TABLE sale_summary (
    id INT AUTO_INCREMENT PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_sale DECIMAL NOT NULL,
    total_tax DECIMAL NOT NULL,
    valid_rows INT NOT NULL,
    invalid_rows INT NOT NULL
);
