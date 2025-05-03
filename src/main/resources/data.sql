CREATE TABLE IF NOT EXISTS USERS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    mobile VARCHAR(15)
);

INSERT INTO USERS (name, email, mobile) VALUES ('John Doe', 'john@example.com', '1234567890');
INSERT INTO USERS (name, email, mobile) VALUES ('Jane Smith', 'jane@example.com', '9876543210');
INSERT INTO USERS (name, email, mobile) VALUES ('Bob Johnson', 'bob@example.com', '5556667777');
