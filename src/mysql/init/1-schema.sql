DROP DATABASE IF EXISTS wishlist;
CREATE DATABASE wishlist;
USE wishlist;

CREATE TABLE USER (
                      USER_ID INT AUTO_INCREMENT,
                      FULLNAME VARCHAR(255),
                      EMAIL VARCHAR(255),
                      USER_PASSWORD VARCHAR(255),
                      PRIMARY KEY(USER_ID)
);