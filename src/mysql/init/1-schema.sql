DROP DATABASE IF EXISTS projectPeak;
CREATE DATABASE projectPeak;
USE projectPeak;

CREATE TABLE User (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  fullname VARCHAR(255),
  email VARCHAR(255),
  user_password VARCHAR(255),
  role VARCHAR(255)
);

CREATE TABLE Project (
  id INT PRIMARY KEY,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Task (
  id INT PRIMARY KEY,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  status VARCHAR(255),
  project_id INT,
  FOREIGN KEY (project_id) REFERENCES Project(id)
);

CREATE TABLE Subtask (
  id INT PRIMARY KEY,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  status VARCHAR(255),
  task_id INT,
  FOREIGN KEY (task_id) REFERENCES Task(id)
);