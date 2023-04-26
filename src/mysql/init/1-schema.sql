DROP DATABASE IF EXISTS projectpeak;
CREATE DATABASE projectpeak;
USE projectpeak;

CREATE TABLE user (
    user_id INT AUTO_INCREMENT,
    fullname VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY(user_id)
);