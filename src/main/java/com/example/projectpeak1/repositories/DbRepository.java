package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.utility.DbManager;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Repository("dbRepository")
public class DbRepository implements IRepository {


    //_____________LOGIN CONTROLLER________________

    @Override
    public User login(String email, String password) throws LoginException {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM user WHERE email = ? AND user_password = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("user_id");
                User user = new User(email, password);
                user.setUserId(id);
                return user;
            } else {
                throw new LoginException("Wrong email or password");
            }
        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }

    }

    @Override
    public User createUser(User user) throws LoginException {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "INSERT INTO user (email, user_password, fullname) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            User user1 = new User(user.getEmail(), user.getPassword());
            user.setUserId(id);
            return user1;
        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }
    }
    @Override
    public User getUserFromId(int id) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM USER WHERE USER_ID = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            User user1 = null;
            if (rs.next()) {
                int userId = rs.getInt("USER_ID");
                String fullName = rs.getString("FULLNAME");
                String email = rs.getString("EMAIL");
                String userPassword = rs.getString("USER_PASSWORD");
                String userRole = rs.getString("USER_PASSWORD");
                user1 = new User(userId, fullName, email, userPassword, userRole);
            }

            return user1;

        } catch (SQLException ex) {
            return null;
        }
    }
    @Override
    public void editUser(User user) throws LoginException {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "UPDATE USER SET USER_ID = ?, FULLNAME = ?, EMAIL = ?, USER_PASSWORD = ? WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getUserId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }
    }
    @Override
    public void deleteUser(int userId) throws LoginException {
        try (Connection con = DbManager.getConnection()) {
            // delete all wishes associated with the user
            String SQL = "DELETE FROM WISH WHERE WISHLIST_ID IN " +
                    "(SELECT WISHLIST_ID FROM WISHLIST WHERE USER_ID = ?)";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // delete all wishlists associated with the user
            SQL = "DELETE FROM WISHLIST WHERE USER_ID = ?";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // delete the user record
            SQL = "DELETE FROM USER WHERE USER_ID = ?";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }
    }



    //_____________PROJECT CONTROLLER______________

    public void createProject(Project project, int userId) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "INSERT INTO Project (name, description, start_date, end_date, user_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, project.getProjectName());
            ps.setString(2, project.getProjectDescription());
            ps.setDate(3, Date.valueOf(project.getProjectStartDate()));
            ps.setDate(4, Date.valueOf(project.getProjectEndDate()));
            ps.setInt(5, userId);
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);


            Project createdProject = new Project(project.getProjectName(), project.getProjectDescription(), project.getProjectStartDate(), project.getProjectEndDate(), project.getUserId());
            createdProject.setProjectId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Project> getAllProjectById(int userId) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM project WHERE user_id = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            List<Project> list = new ArrayList<>();
            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                String projectName = rs.getString("name");
                String projectDescription = rs.getString("description");
                LocalDate projectStartDate = rs.getDate("start_date").toLocalDate();
                LocalDate projectEndDate = rs.getDate("end_date").toLocalDate();
                Project project = new Project(projectId, projectName, projectDescription, projectStartDate, projectEndDate, userId);
                list.add(project);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteProject(int projectId) throws LoginException {
        try (Connection con = DbManager.getConnection()) {
            // delete the project record
            String SQL = "DELETE FROM project WHERE project_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setInt(1, projectId);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }
    }

    @Override
    public Project getProjectById(int id) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM project WHERE project_id = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Project project = null;
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String projectName = rs.getString("name");
                String projectDescription = rs.getString("description");
                LocalDate projectStartDate = rs.getDate("start_date").toLocalDate();
                LocalDate projectEndDate = rs.getDate("end_date").toLocalDate();
                project = new Project(id, projectName, projectDescription, projectStartDate, projectEndDate, userId);
            }
            return project;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateProject(Project project) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "UPDATE project SET name = ?, description = ?, start_date = ?, end_date = ? WHERE project_id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, project.getProjectName());
            ps.setString(2, project.getProjectDescription());
            ps.setDate(3, Date.valueOf(project.getProjectStartDate()));
            ps.setDate(4, Date.valueOf(project.getProjectEndDate()));
            ps.setInt(5, project.getProjectId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }





    //_____________TASK CONTROLLER_________________
    @Override
    public void createTask(Task task, int projectId) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "INSERT INTO Task (task_number, name, description, start_date, end_date, status, project_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, task.getTaskNumber());
            ps.setString(2, task.getTaskName());
            ps.setString(3, task.getTaskDescription());
            ps.setDate(4, Date.valueOf(task.getTaskStartDate()));
            ps.setDate(5, Date.valueOf(task.getTaskEndDate()));
            ps.setString(6, task.getStatus());
            ps.setInt(7, projectId);
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);


            Task createdTask = new Task(task.getTaskNumber(), task.getTaskName(), task.getTaskDescription(), task.getTaskStartDate(), task.getTaskEndDate(), task.getStatus(), projectId);
            createdTask.setTaskId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> getAllTaskById(int id) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM Task WHERE project_id = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            List<Task> taskList = new ArrayList<>();

            while (rs.next()) {
                int taskId = rs.getInt("task_id");
                String taskName = rs.getString("name");
                String taskDescription = rs.getString("description");
                LocalDate taskStartDate = rs.getDate("start_date").toLocalDate();
                LocalDate taskEndDate = rs.getDate("end_date").toLocalDate();
                String taskStatus = rs.getString("status");
                Task task = new Task(taskId, taskName, taskDescription, taskStartDate, taskEndDate, taskStatus, id);
                taskList.add(task);
            }
            return taskList;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Task getTaskById(int id) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM Task WHERE task_id = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Task task = new Task();

            if (rs.next()) {
                int taskId = rs.getInt("task_id");
                String taskName = rs.getString("name");
                String taskDescription = rs.getString("description");
                LocalDate taskStartDate = rs.getDate("start_date").toLocalDate();
                LocalDate taskEndDate = rs.getDate("end_date").toLocalDate();
                String taskStatus = rs.getString("status");
                int projectId = rs.getInt("project_id");
                task = new Task(taskId, taskName, taskDescription, taskStartDate, taskEndDate, taskStatus, projectId);

            }
            return task;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }



    @Override
    public List<TaskAndSubtaskDTO> getTaskAndSubTaskList(int projectId) {
        try {
            List<TaskAndSubtaskDTO> taskAndSubtaskDTOs = new ArrayList<>();
            Connection con = DbManager.getConnection();
            String taskSql = "SELECT * FROM Task WHERE project_id = ?;";
            PreparedStatement taskPs = con.prepareStatement(taskSql);
            taskPs.setInt(1, projectId);
            ResultSet taskRs = taskPs.executeQuery();

            while (taskRs.next()) {
                int taskId = taskRs.getInt("task_id");
                String taskName = taskRs.getString("name");
                String taskDescription = taskRs.getString("description");
                LocalDate taskStartDate = taskRs.getDate("start_date").toLocalDate();
                LocalDate taskEndDate = taskRs.getDate("end_date").toLocalDate();
                String taskStatus = taskRs.getString("status");

                String subtaskSql = "SELECT * FROM Subtask WHERE task_id = ?;";
                PreparedStatement subtaskPs = con.prepareStatement(subtaskSql);
                subtaskPs.setInt(1, taskId);
                ResultSet subtaskRs = subtaskPs.executeQuery();

                List<Subtask> subtasks = new ArrayList<>();
                while (subtaskRs.next()) {
                    int subtaskId = subtaskRs.getInt("subtask_id");
                    String subtaskName = subtaskRs.getString("name");
                    String subtaskDescription = subtaskRs.getString("description");
                    LocalDate subtaskStartDate = subtaskRs.getDate("start_date").toLocalDate();
                    LocalDate subtaskEndDate = subtaskRs.getDate("end_date").toLocalDate();
                    String subtaskStatus = subtaskRs.getString("status");
                    Subtask sub1 = new Subtask(subtaskId, subtaskName, subtaskDescription, subtaskStartDate, subtaskEndDate, subtaskStatus, taskId);
                    subtasks.add(sub1);

                }
                TaskAndSubtaskDTO taskAndSubtaskDTO = new TaskAndSubtaskDTO(taskId, taskName, taskDescription, taskStartDate, taskEndDate, taskStatus, projectId, subtasks);

                taskAndSubtaskDTOs.add(taskAndSubtaskDTO);
            }
            return taskAndSubtaskDTOs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteTask(int taskId) throws LoginException {
        try (Connection con = DbManager.getConnection()) {
            // Delete the subtasks linked to the task
            String deleteSubtasksSQL = "DELETE FROM Subtask WHERE task_id = ?";
            try (PreparedStatement deleteSubtasksStmt = con.prepareStatement(deleteSubtasksSQL)) {
                deleteSubtasksStmt.setInt(1, taskId);
                deleteSubtasksStmt.executeUpdate();
            }

            // Delete the task
            String deleteTaskSQL = "DELETE FROM Task WHERE task_id = ?";
            try (PreparedStatement deleteTaskStmt = con.prepareStatement(deleteTaskSQL)) {
                deleteTaskStmt.setInt(1, taskId);
                deleteTaskStmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }
    }

    @Override
    public User getUserFromTaskId(int taskId) {
        return null;
    }


    //_____________SUBTASK CONTROLLER______________

    @Override
    public void createSubtask(Subtask subtask, int taskId) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "INSERT INTO subtask (subtask_number, name, description, start_date, end_date, status, task_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, subtask.getSubtaskNumber());
            ps.setString(2, subtask.getSubTaskName());
            ps.setString(3, subtask.getSubTaskDescription());
            ps.setDate(4, Date.valueOf(subtask.getSubTaskStartDate()));
            ps.setDate(5, Date.valueOf(subtask.getSubTaskEndDate()));
            ps.setString(6, subtask.getStatus());
            ps.setInt(7, taskId);
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);


            Subtask subtask1 = new Subtask( subtask.getSubtaskNumber(), subtask.getSubTaskName(), subtask.getSubTaskDescription(), subtask.getSubTaskStartDate(), subtask.getSubTaskEndDate(), subtask.getStatus(), taskId);
            subtask1.setTaskId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Subtask getSubtaskById(int id) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM Subtask WHERE subtask_id = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Subtask task = new Subtask();

            if (rs.next()) {
                int subTaskId = rs.getInt("subtask_id");
                int subTaskNumber = rs.getInt("subtask_number");
                String subTaskName = rs.getString("name");
                String subTaskDescription = rs.getString("description");
                LocalDate subTaskStartDate = rs.getDate("start_date").toLocalDate();
                LocalDate subTaskEndDate = rs.getDate("end_date").toLocalDate();
                String subTaskStatus = rs.getString("status");
                int taskId = rs.getInt("task_id");
                task = new Subtask(subTaskId, subTaskNumber, subTaskName, subTaskDescription, subTaskStartDate, subTaskEndDate, subTaskStatus, taskId);

            }
            return task;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void editSubtask(Subtask subtask) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "UPDATE subtask SET name = ?, description = ?, start_date = ?, end_date = ?, status = ? WHERE subtask_id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, subtask.getSubTaskName());
            ps.setString(2, subtask.getSubTaskDescription());
            ps.setDate(3, Date.valueOf(subtask.getSubTaskStartDate()));
            ps.setDate(4, Date.valueOf(subtask.getSubTaskEndDate()));
            ps.setString(5, subtask.getStatus());
            ps.setInt(6, subtask.getTaskId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getProjectIdBySubtaskId(int subtaskId) {
        int projectId = 0;

        try {
            Connection con = DbManager.getConnection();
            String sql = "SELECT P.project_id FROM Project P " +
                    "INNER JOIN Task T ON P.project_id = T.project_id " +
                    "INNER JOIN Subtask S ON T.task_id = S.task_id " +
                    "WHERE S.subtask_id = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, subtaskId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                projectId = resultSet.getInt("project_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectId;
    }

    @Override
    public void deleteSubtask(int subtaskId) throws LoginException {
        try (Connection con = DbManager.getConnection()) {
            // delete the project record
            String SQL = "DELETE FROM subtask WHERE subtask_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setInt(1, subtaskId);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }
    }

    @Override
    public TaskAndSubtaskDTO getTaskAndSubTask(int taskId) {
        try {
            Connection con = DbManager.getConnection();

            // Retrieve the task
            String taskSql = "SELECT * FROM Task WHERE task_id = ?;";
            PreparedStatement taskPs = con.prepareStatement(taskSql);
            taskPs.setInt(1, taskId);
            ResultSet taskRs = taskPs.executeQuery();

            if (taskRs.next()) {
                int projectId = taskRs.getInt("project_id");
                String taskName = taskRs.getString("name");
                String taskDescription = taskRs.getString("description");
                LocalDate taskStartDate = taskRs.getDate("start_date").toLocalDate();
                LocalDate taskEndDate = taskRs.getDate("end_date").toLocalDate();
                String taskStatus = taskRs.getString("status");

                // Retrieve the subtasks for the task
                String subtaskSql = "SELECT * FROM Subtask WHERE task_id = ?;";
                PreparedStatement subtaskPs = con.prepareStatement(subtaskSql);
                subtaskPs.setInt(1, taskId);
                ResultSet subtaskRs = subtaskPs.executeQuery();

                List<Subtask> subtasks = new ArrayList<>();
                while (subtaskRs.next()) {
                    int subtaskId = subtaskRs.getInt("subtask_id");
                    String subtaskName = subtaskRs.getString("name");
                    String subtaskDescription = subtaskRs.getString("description");
                    LocalDate subtaskStartDate = subtaskRs.getDate("start_date").toLocalDate();
                    LocalDate subtaskEndDate = subtaskRs.getDate("end_date").toLocalDate();
                    String subtaskStatus = subtaskRs.getString("status");
                    subtasks.add(new Subtask(subtaskId, subtaskName, subtaskDescription, subtaskStartDate, subtaskEndDate, subtaskStatus, taskId));
                }

                return new TaskAndSubtaskDTO(taskId, taskName, taskDescription, taskStartDate, taskEndDate, taskStatus, projectId, subtasks);
            }

            // No task found for the provided task ID
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }




    public void addTask(Task task, int projectId) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "INSERT INTO Task (name, description, start_date, end_date, status, project_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getTaskDescription());
            ps.setDate(3, Date.valueOf(task.getTaskStartDate()));
            ps.setDate(4, Date.valueOf(task.getTaskEndDate()));
            ps.setString(5, task.getStatus());
            ps.setInt(6, projectId);
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            task.setTaskId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getProjectIdByTaskId(int taskId) {
        int projectId = 0;

        try {
            Connection con = DbManager.getConnection();
            String sql = "SELECT project_id FROM Task WHERE task_id = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, taskId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                projectId = resultSet.getInt("project_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectId;
    }

    @Override
    public void editTask(Task task) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "UPDATE Task SET name = ?, description = ?, start_date = ?, end_date = ?, status = ? WHERE task_id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getTaskDescription());
            ps.setDate(3, Date.valueOf(task.getTaskStartDate()));
            ps.setDate(4, Date.valueOf(task.getTaskEndDate()));
            ps.setString(5, task.getStatus());
            ps.setInt(6, task.getProjectId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Project getProjectByTaskId(int taskId) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM project WHERE project_id = (SELECT project_id FROM task WHERE task_id = ?);";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, taskId);
            ResultSet rs = ps.executeQuery();
            Project project = null;
            if (rs.next()) {
                int projectId = rs.getInt("project_id");
                int userId = rs.getInt("user_id");
                String projectName = rs.getString("name");
                String projectDescription = rs.getString("description");
                LocalDate projectStartDate = rs.getDate("start_date").toLocalDate();
                LocalDate projectEndDate = rs.getDate("end_date").toLocalDate();
                project = new Project(projectId, projectName, projectDescription, projectStartDate, projectEndDate, userId);
            }
            return project;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }



}
