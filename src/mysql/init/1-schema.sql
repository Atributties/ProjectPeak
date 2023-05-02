DROP DATABASE IF EXISTS projectpeak;
CREATE DATABASE projectpeak;
USE projectpeak;

CREATE TABLE User (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255)
);

CREATE TABLE Project (
     id INT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(255),
     description VARCHAR(255),
     start_date DATE,
     end_date DATE,
     user_id INT,
     FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE Task (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(255),
    start_date DATE,
    end_date DATE,
    status VARCHAR(255),
    project_id INT,
    FOREIGN KEY (project_id) REFERENCES Project(id)
);

CREATE TABLE Subtask (
     id INT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(255),
     description VARCHAR(255),
     start_date DATE,
     end_date DATE,
     status VARCHAR(255),
     task_id INT,
     FOREIGN KEY (task_id) REFERENCES Task(id)
);