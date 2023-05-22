USE projectPeak;


-- Users
INSERT INTO User (fullname, email, user_password, role) VALUES
    ('John Doe', 'john.doe@example.com', 'password1', 'Admin'),
    ('Jane Doe', 'jane.doe@example.com', 'password2', 'User');

-- Projects
INSERT INTO Project (name, description, start_date, end_date, user_id) VALUES
    ('Project 1', 'This is project 1', '2023-01-01', '2023-06-01', 1),
    ('Project 2', 'This is project 2', '2023-02-01', '2023-07-01', 2);

-- Tasks
INSERT INTO Task (name, description, start_date, end_date, status, project_id) VALUES
    ('Task 1.1', 'This is task 1.1 for project 1', '2023-01-01', '2023-02-01', 'Completed', 1),
    ('Task 1.2', 'This is task 1.2 for project 1', '2023-02-01', '2023-03-01', 'Completed', 1),
    ('Task 2.1', 'This is task 2.1 for project 2', '2023-02-01', '2023-03-01', 'In progress', 2);
-- Subtasks
INSERT INTO Subtask (name, description, start_date, end_date, status, task_id) VALUES
    ('Subtask 1.1.1', 'This is subtask 1 for task 1.1', '2023-01-01', '2023-01-15', 'Completed', 1),
    ('Subtask 1.1.2', 'This is subtask 2 for task 1.1', '2023-01-15', '2023-02-01', 'Completed', 1),
    ('Subtask 2.1.1', 'This is subtask 1 for task 2.1', '2023-02-01', '2023-02-15', 'In progress', 3);

-- DoneProjects
INSERT INTO DoneProject (project_id, name, description, start_date, end_date, project_completed_date, project_expected_days, project_used_days, user_id) VALUES
    (1, 'Project 1', 'This is project 1', '2023-01-01', '2023-06-01', '2023-03-01', 180, 60, 1);

-- DoneTasks
INSERT INTO DoneTask (task_id, done_project_id, name, description, start_date, end_date, task_completed_date, task_expected_days, task_used_days, status) VALUES
    (1, 1, 'Task 1.1', 'This is task 1.1 for project 1', '2023-01-01', '2023-02-01', '2023-02-01', 31, 31, 'Completed');

-- DoneSubtasks
INSERT INTO DoneSubtask (subtask_id, done_task_id, name, description, start_date, end_date, subtask_completed_date, subtask_expected_days, subtask_used_days, status) VALUES
    (1, 1, 'Subtask 1.1.1', 'This is subtask 1 for task 1.1', '2023-01-01', '2023-01-15', '2023-01-15', 15, 15, 'Completed');