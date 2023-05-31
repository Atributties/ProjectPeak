USE projectpeak;

-- Users
INSERT INTO User (fullname, email, user_password) VALUES 
('John Doe', 'john.doe@example.com', 'password1'),
('Jane Doe', 'jane.doe@example.com', 'password2');

-- Projects
INSERT INTO Project (name, description, start_date, end_date, user_id) VALUES 
('Project 1', 'This is project 1', '2023-07-22', '2023-08-22', 1),
('Project 2', 'This is project 2', '2023-08-29', '2023-09-23', 2);

-- ProjectMember 
INSERT INTO ProjectMember (project_id, user_id) VALUES 
(1, 1),
(2, 2);

-- Tasks
INSERT INTO Task (name, description, start_date, end_date, status, project_id) VALUES 
('Task 1.1', 'This is task 1.1 for project 1', '2023-07-22', '2023-07-24', 'Not Started', 1),
('Task 1.2', 'This is task 1.2 for project 1', '2023-07-22', '2023-07-26', 'Not Started', 1),
('Task 1.3', 'This is task 1.3 for project 1', '2023-07-26', '2023-07-28', 'Not Started', 1),
('Task 1.4', 'This is task 1.4 for project 1', '2023-07-27', '2023-08-03', 'Not Started', 1),
('Task 1.5', 'This is task 1.5 for project 1', '2023-07-31', '2023-08-06', 'Not Started', 1),
('Task 1.6', 'This is task 1.6 for project 1', '2023-08-10', '2023-08-22', 'Not Started', 1),
('Task 1.1', 'This is task 1.1 for project 2', '2023-07-22', '2023-07-24', 'Not Started', 2),
('Task 1.2', 'This is task 1.2 for project 2', '2023-07-22', '2023-07-26', 'Not Started', 2),
('Task 1.3', 'This is task 1.3 for project 2', '2023-07-26', '2023-07-28', 'Not Started', 2),
('Task 1.4', 'This is task 1.4 for project 2', '2023-07-27', '2023-08-03', 'Not Started', 2),
('Task 1.5', 'This is task 1.5 for project 2', '2023-07-31', '2023-08-06', 'Not Started', 2),
('Task 1.6', 'This is task 1.6 for project 2', '2023-08-10', '2023-08-22', 'Not Started', 2);


-- Subtasks
INSERT INTO Subtask (name, description, start_date, end_date, status, task_id) VALUES 
('Subtask 1.1.1', 'This is subtask 1 for task 1.1', '2023-07-22', '2023-07-23', 'Not Started', 1),
('Subtask 1.1.2', 'This is subtask 2 for task 1.1', '2023-07-23', '2023-07-24', 'Not Started', 1),
('Subtask 1.1.3', 'This is subtask 3 for task 1.1', '2023-07-22', '2023-07-24', 'Not Started', 1),
('Subtask 1.1.1', 'This is subtask 1 for task 1.2', '2023-07-22', '2023-07-25', 'Not Started', 2),
('Subtask 1.1.2', 'This is subtask 2 for task 1.2', '2023-07-23', '2023-07-26', 'Not Started', 2),
('Subtask 1.1.3', 'This is subtask 3 for task 1.2', '2023-07-25', '2023-07-26', 'Not Started', 2),
('Subtask 1.1.1', 'This is subtask 1 for task 1.3', '2023-07-26', '2023-07-27', 'Not Started', 3),
('Subtask 1.1.2', 'This is subtask 2 for task 1.3', '2023-07-26', '2023-07-28', 'Not Started', 3),
('Subtask 1.1.3', 'This is subtask 3 for task 1.3', '2023-07-27', '2023-07-28', 'Not Started', 3),
('Subtask 1.1.1', 'This is subtask 1 for task 1.4', '2023-07-27', '2023-07-28', 'Not Started', 4),
('Subtask 1.1.2', 'This is subtask 2 for task 1.4', '2023-07-29', '2023-08-01', 'Not Started', 4),
('Subtask 1.1.3', 'This is subtask 3 for task 1.4', '2023-08-01', '2023-08-03', 'Not Started', 4),
('Subtask 1.1.1', 'This is subtask 1 for task 1.5', '2023-07-31', '2023-08-01', 'Not Started', 5),
('Subtask 1.1.2', 'This is subtask 2 for task 1.5', '2023-08-01', '2023-08-03', 'Not Started', 5),
('Subtask 1.1.3', 'This is subtask 3 for task 1.5', '2023-08-03', '2023-08-06', 'Not Started', 5),
('Subtask 1.1.1', 'This is subtask 1 for task 1.6', '2023-08-10', '2023-08-15', 'Not Started', 6),
('Subtask 1.1.2', 'This is subtask 2 for task 1.6', '2023-08-15', '2023-08-17', 'Not Started', 6),
('Subtask 1.1.3', 'This is subtask 3 for task 1.6', '2023-08-17', '2023-08-22', 'Not Started', 6),
('Subtask 1.1.1', 'This is subtask 1 for task 1.1', '2023-07-22', '2023-07-23', 'Not Started', 7),
('Subtask 1.1.2', 'This is subtask 2 for task 1.1', '2023-07-23', '2023-07-24', 'Not Started', 7),
('Subtask 1.1.3', 'This is subtask 3 for task 1.1', '2023-07-22', '2023-07-24', 'Not Started', 7),
('Subtask 1.1.1', 'This is subtask 1 for task 1.2', '2023-07-22', '2023-07-25', 'Not Started', 8),
('Subtask 1.1.2', 'This is subtask 2 for task 1.2', '2023-07-23', '2023-07-26', 'Not Started', 8),
('Subtask 1.1.3', 'This is subtask 3 for task 1.2', '2023-07-25', '2023-07-26', 'Not Started', 8),
('Subtask 1.1.1', 'This is subtask 1 for task 1.3', '2023-07-26', '2023-07-27', 'Not Started', 9),
('Subtask 1.1.2', 'This is subtask 2 for task 1.3', '2023-07-26', '2023-07-28', 'Not Started', 9),
('Subtask 1.1.3', 'This is subtask 3 for task 1.3', '2023-07-27', '2023-07-28', 'Not Started', 9),
('Subtask 1.1.1', 'This is subtask 1 for task 1.4', '2023-07-27', '2023-07-28', 'Not Started', 10),
('Subtask 1.1.2', 'This is subtask 2 for task 1.4', '2023-07-29', '2023-08-01', 'Not Started', 10),
('Subtask 1.1.3', 'This is subtask 3 for task 1.4', '2023-08-01', '2023-08-03', 'Not Started', 10),
('Subtask 1.1.1', 'This is subtask 1 for task 1.5', '2023-07-31', '2023-08-01', 'Not Started', 11),
('Subtask 1.1.2', 'This is subtask 2 for task 1.5', '2023-08-01', '2023-08-03', 'Not Started', 11),
('Subtask 1.1.3', 'This is subtask 3 for task 1.5', '2023-08-03', '2023-08-06', 'Not Started', 11),
('Subtask 1.1.1', 'This is subtask 1 for task 1.6', '2023-08-10', '2023-08-15', 'Not Started', 12),
('Subtask 1.1.2', 'This is subtask 2 for task 1.6', '2023-08-15', '2023-08-17', 'Not Started', 12),
('Subtask 1.1.3', 'This is subtask 3 for task 1.6', '2023-08-17', '2023-08-22', 'Not Started', 12);

