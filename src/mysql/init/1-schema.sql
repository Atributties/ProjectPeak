CREATE DATABASE IF NOT EXISTS projectpeak;
USE projectpeak;

DROP TABLE IF EXISTS PROJECTMEMBER;
DROP TABLE IF EXISTS SUBTASK;
DROP TABLE IF EXISTS TASK;
DROP TABLE IF EXISTS PROJECT;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS DONEPROJECT;
DROP TABLE IF EXISTS DONETASK;
DROP TABLE IF EXISTS DONESUBTASK;

CREATE TABLE USER (
  user_id INT AUTO_INCREMENT,
  fullname VARCHAR(255),
  email VARCHAR(255),
  user_password VARCHAR(255),
  PRIMARY KEY(user_id)
);

CREATE TABLE PROJECT (
  project_id INT AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  user_id INT,
  PRIMARY KEY(project_id),
  FOREIGN KEY(user_id) REFERENCES USER(user_id)
);

CREATE TABLE PROJECTMEMBER (
  project_member_id INT AUTO_INCREMENT,
  project_id INT,
  user_id INT,
  PRIMARY KEY(project_member_id),
  FOREIGN KEY(project_id) REFERENCES PROJECT(project_id) ON DELETE CASCADE,
  FOREIGN KEY(user_id) REFERENCES USER(user_id)
);

CREATE TABLE TASK (
  task_id INT AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  status VARCHAR(255),
  project_id INT,
  PRIMARY KEY(task_id),
  FOREIGN KEY(project_id) REFERENCES PROJECT(project_id)
);

CREATE TABLE SUBTASK (
  subtask_id INT AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  status VARCHAR(255),
  task_id INT,
  PRIMARY KEY(subtask_id),
  FOREIGN KEY(task_id) REFERENCES TASK(task_id)
);

CREATE TABLE DONEPROJECT (
  project_id INT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  project_completed_date DATE DEFAULT (CURRENT_DATE),
  user_id INT,
  PRIMARY KEY(project_id)
);

CREATE TABLE DONETASK (
  task_id INT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  task_completed_date DATE DEFAULT (CURRENT_DATE),
  status VARCHAR(255),
  project_id INT,
  PRIMARY KEY(task_id)
);

CREATE TABLE DONESUBTASK (
  subtask_id INT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  subtask_completed_date DATE DEFAULT (CURRENT_DATE),
  status VARCHAR(255),
  task_id INT,
  PRIMARY KEY(subtask_id)
);
