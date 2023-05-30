# Contributing to Project Peak

Thank you for considering contributing to Project Peak! We welcome all contributions, including bug fixes, feature implementations, and documentation improvements.
Here are some guidelines for contributing. 

## Technologies Used

#### Project Peak is built using the following technologies:

* Java
* Spring Boot
* Spring Web
* MySQL
* HTML
* CSS
* Tailwind
* Thymeleaf
* Docker
* Tomcat

## Getting Started
We are using MySQL database, here are a few steps to get MySQL up and running on your computer 

Download MySQL: https://dev.mysql.com/downloads/mysql/ verson: 8.0.30
Download MySQL Workbench: https://dev.mysql.com/downloads/workbench/ version: 8.0.30

Now, on MySQL Workbench open a new tab and run this script: 

```
DROP DATABASE IF EXISTS projectPeak;
CREATE DATABASE projectPeak;
USE projectPeak;

CREATE TABLE User (
  user_id INT AUTO_INCREMENT,
  fullname VARCHAR(255),
  email VARCHAR(255),
  user_password VARCHAR(255),
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

CREATE TABLE ProjectMember (
  project_member_id INT AUTO_INCREMENT,
  project_id INT,
  user_id INT,
  PRIMARY KEY(project_member_id),
  FOREIGN KEY(project_id) REFERENCES Project(project_id) ON DELETE CASCADE,
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
  project_id INT,
  name VARCHAR(255),
  description VARCHAR(255),
  start_date DATE,
  end_date DATE,
  project_completed_date DATE DEFAULT (CURRENT_DATE),
  user_id INT,
  PRIMARY KEY(project_id)
);


CREATE TABLE DoneTask (
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

CREATE TABLE DoneSubtask (
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

-- Insert data into User table
INSERT INTO User (fullname, email, user_password, role) 
VALUES 
('John Doe', 'john@example.com', 'john123', 'admin'),
('Jane Smith', 'jane@example.com', 'jane123', 'user');

-- Insert data into Project table
INSERT INTO Project (name, description, start_date, end_date, user_id) 
VALUES 
('Project 1', 'This is project 1', '2023-06-02', '2023-06-04', 1),
('Project 2', 'This is project 2', '2023-06-03', '2023-06-05', 2);

-- Insert data into Task table
INSERT INTO Task (name, description, start_date, end_date, status, project_id) 
VALUES 
('Task 1', 'This is task 1', '2023-06-02', '2023-06-04', 'In Progress', 1),
('Task 2', 'This is task 2', '2023-06-03', '2023-06-05', 'Not Started', 2);

-- Insert data into Subtask table
INSERT INTO Subtask (name, description, start_date, end_date, status, task_id) 
VALUES 
('Subtask 1', 'This is subtask 1', '2023-06-02', '2023-06-04', 'In Progress', 1),
('Subtask 2', 'This is subtask 2', '2023-06-03', '2023-06-05', 'Not Started', 2);

-- Insert data into DoneProject table
INSERT INTO DoneProject (project_id, name, description, start_date, end_date, user_id) 
VALUES 
(1, 'Done Project 1', 'This is a completed project', '2023-06-02', '2023-06-04', 1);

-- Insert data into DoneTask table
INSERT INTO DoneTask (task_id, name, description, start_date, end_date, status, project_id) 
VALUES 
(1, 'Done Task 1', 'This is a completed task', '2023-06-02', '2023-06-04', 'Completed', 1);

-- Insert data into DoneSubtask table
INSERT INTO DoneSubtask (subtask_id, name, description, start_date, end_date, status, task_id) 
VALUES 
(1, 'Done Subtask 1', 'This is a completed subtask', '2023-06-02', '2023-06-04', 'Completed', 1);
```
Now, you have access to MySQL database, and you can now create a user in Project Peak! 
Feel free to try our test client also

#### To get started contributing to project Peak, you'll need to:

1. Fork the repository
2. Clone your forked repository to your local machine
3. Create a new branch for your contribution
4. Make changes and commit them to your branch
5. Push your branch to your forked repository
6. Open a pull request

## Pull Requests

#### Before submitting a pull request, please make sure:

* Your code follows our code style guidelines
* Your code passes our unit tests
* You have updated the project documentation as necessary

## Code Style Guidelines

* We follow the Google Java Style Guide for Java code
* We follow the HTML and CSS Style Guide for HTML and CSS code

## Unit Tests

We use JUnit 5 for unit testing. To run the unit tests, run mvn test.

## Reporting Bugs

If you find a bug in Project Peak, please report it by opening an issue in the project's GitHub repository. Be sure to include a detailed description of the bug, steps to reproduce it, and any relevant code or error messages.

## Feature Requests

If you have an idea for a new feature for Project Peak, please open an issue in the project's GitHub repository. Be sure to include a detailed description of the feature, as well as any relevant use cases or code examples.

Thank you for contributing to Project Peak!
