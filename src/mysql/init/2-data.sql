USE projectPeak;

('John Doe', 'johndoe@example.com', 'password123', 'admin'),
('Jane Doe', 'janedoe@example.com', 'password456', 'user');

INSERT INTO Project (name, description, start_date, end_date, user_id)
VALUES
    ('Project 1', 'Description for project 1', '2022-01-01', '2022-06-30', 1),
    ('Project 2', 'Description for project 2', '2022-02-01', '2022-07-31', 2);

INSERT INTO Task (name, description, start_date, end_date, status, project_id)
VALUES
    ('Task 1', 'Description for task 1', '2022-01-01', '2022-01-31', 'in progress', 1),
    ('Task 2', 'Description for task 2', '2022-02-01', '2022-02-28', 'not started', 1),
    ('Task 3', 'Description for task 3', '2022-02-01', '2022-02-28', 'in progress', 2),
    ('Task 4', 'Description for task 4', '2022-03-01', '2022-03-31', 'not started', 2);

INSERT INTO Subtask (name, description, start_date, end_date, status, task_id)
VALUES
    ('Subtask 1', 'Description for subtask 1', '2022-01-01', '2022-01-15', 'complete', 1),
    ('Subtask 2', 'Description for subtask 2', '2022-01-16', '2022-01-31', 'in progress', 1),
    ('Subtask 3', 'Description for subtask 3', '2022-02-01', '2022-02-14', 'complete', 2),
    ('Subtask 4', 'Description for subtask 4', '2022-02-15', '2022-02-28', 'not started', 2),
    ('Subtask 5', 'Description for subtask 5', '2022-03-01', '2022-03-15', 'in progress', 3),
    ('Subtask 6', 'Description for subtask 6', '2022-03-16', '2022-03-31', 'not started', 3),
    ('Subtask 7', 'Description for subtask 7', '2022-04-01', '2022-04-15', 'not started', 4);