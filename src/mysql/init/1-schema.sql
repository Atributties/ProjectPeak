CREATE DATABASE projectpeak;
USE projectpeak;

DROP TABLE IF EXISTS projectpeak;

CREATE TABLE user (
  user_id INT AUTO_INCREMENT,
  fullname VARCHAR(255),
  email VARCHAR(255),
  user_password VARCHAR(255),
  PRIMARY KEY(user_id)
);

CREATE TABLE project (
  project_id INT AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  user_id INT,
  PRIMARY KEY(project_id)
);

CREATE TABLE projectmember (
  project_member_id INT AUTO_INCREMENT,
  project_id INT,
  user_id INT,
  PRIMARY KEY(project_member_id),
  FOREIGN KEY(project_id) REFERENCES project(project_id) ON DELETE CASCADE,
  FOREIGN KEY(user_id) REFERENCES user(user_id)
);

CREATE TABLE task (
  task_id INT AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  status VARCHAR(255),
  project_id INT,
  PRIMARY KEY(task_id),
  FOREIGN KEY(project_id) REFERENCES project(project_id)
);

CREATE TABLE subtask (
  subtask_id INT AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  status VARCHAR(255),
  task_id INT,
  PRIMARY KEY(subtask_id),
  FOREIGN KEY(task_id) REFERENCES task(task_id)
);

CREATE TABLE doneproject (
  project_id INT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  project_completed_date DATE DEFAULT (CURRENT_DATE),
  user_id INT,
  PRIMARY KEY(project_id)
);

CREATE TABLE donetask (
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

CREATE TABLE donesubtask (
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


