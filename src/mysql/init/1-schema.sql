DROP DATABASE IF EXISTS projectPeak;
CREATE DATABASE projectPeak;
USE projectPeak;

CREATE TABLE User (
  user_id INT AUTO_INCREMENT,
  fullname VARCHAR(255),
  email VARCHAR(255),
  user_password VARCHAR(255),
  role VARCHAR(255),
  PRIMARY KEY(user_id)
);

CREATE TABLE Project (
  project_id INT AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  user_id INT,
  PRIMARY KEY(project_id),
  FOREIGN KEY(user_id) REFERENCES User(user_id)
);

CREATE TABLE Task (
  task_id INT AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  status VARCHAR(255),
  project_id INT,
  PRIMARY KEY(task_id),
  FOREIGN KEY(project_id) REFERENCES Project(project_id)
);

CREATE TABLE Subtask (
  subtask_id INT AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  status VARCHAR(255),
  task_id INT,
  PRIMARY KEY(subtask_id),
  FOREIGN KEY(task_id) REFERENCES Task(task_id)
);

CREATE TABLE DoneProject (
  done_project_id INT AUTO_INCREMENT,
  project_id INT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  project_completed_date DATE,
  project_expected_days INT,
  project_used_days INT,
  user_id INT,
  PRIMARY KEY(done_project_id),
  FOREIGN KEY(user_id) REFERENCES User(user_id),
  FOREIGN KEY(project_id) REFERENCES Project(project_id)
);

CREATE TABLE DoneTask (
  done_task_id INT AUTO_INCREMENT,
  task_id INT,
  done_project_id INT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  task_completed_date DATE,
  task_expected_days INT,
  task_used_days INT,
  status VARCHAR(255),
  PRIMARY KEY(done_task_id),
  FOREIGN KEY(task_id) REFERENCES Task(task_id),
  FOREIGN KEY(done_project_id) REFERENCES DoneProject(done_project_id)
);

CREATE TABLE DoneSubtask (
  done_subtask_id INT AUTO_INCREMENT,
  subtask_id INT,
  done_task_id INT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  subtask_completed_date DATE,
  subtask_expected_days INT,
  subtask_used_days INT,
  status VARCHAR(255),
  PRIMARY KEY(done_subtask_id),
  FOREIGN KEY(subtask_id) REFERENCES Subtask(subtask_id),
  FOREIGN KEY(done_task_id) REFERENCES DoneTask(done_task_id)
);