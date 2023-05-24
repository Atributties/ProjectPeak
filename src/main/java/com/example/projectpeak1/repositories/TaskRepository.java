package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneTaskDTO;
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

@Repository
public class TaskRepository implements ITaskRepository {


    //__________________USER DATA__________________________________
    @Override
    public boolean isUserAuthorized(int userId, int projectId){
        try {
            Connection con = DbManager.getConnection();
            String query = "SELECT COUNT(*) FROM ProjectMember WHERE project_id = ? AND user_id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, projectId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // User is authorized if a matching row exists in ProjectMember
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // User is not authorized or not logged in
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
    public void createTask(Task task, int projectId) {
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


            Task createdTask = new Task(task.getTaskName(), task.getTaskDescription(), task.getTaskStartDate(), task.getTaskEndDate(), task.getStatus(), projectId);
            createdTask.setTaskId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //__________________PROJECT DATA_______________________________
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



    //__________________TASK AND SUBTASK DATA_________________________________
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
            ps.setInt(6, task.getTaskId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public List<Subtask> getSubtasksByTaskId(int taskId) {
        List<Subtask> subtasks = new ArrayList<>();

        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM Subtask WHERE task_id = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, taskId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int subtaskId = rs.getInt("subtask_id");
                String subTaskName = rs.getString("name");
                String subTaskDescription = rs.getString("description");
                LocalDate subTaskStartDate = rs.getDate("start_date").toLocalDate();
                LocalDate subTaskEndDate = rs.getDate("end_date").toLocalDate();
                String subTaskStatus = rs.getString("status");
                int retrievedTaskId = rs.getInt("task_id");

                Subtask subtask = new Subtask(subtaskId, subTaskName, subTaskDescription, subTaskStartDate, subTaskEndDate, subTaskStatus, retrievedTaskId);
                subtasks.add(subtask);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return subtasks;
    }
    @Override
    public void updateSubtaskDates(Subtask subtask) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "UPDATE Subtask SET start_date = ?, end_date = ? WHERE subtask_id = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setDate(1, java.sql.Date.valueOf(subtask.getSubTaskStartDate()));
            ps.setDate(2, java.sql.Date.valueOf(subtask.getSubTaskEndDate()));
            ps.setInt(3, subtask.getSubTaskId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void doneAllSubtask(int id) {
        try (Connection conn = DbManager.getConnection()) {
            // Move subtasks to DoneSubtask table
            String moveSubtasksQuery = "INSERT INTO DoneSubtask (subtask_id, task_id, name, description, start_date, end_date, status) " +
                    "SELECT subtask_id,task_id, name, description, start_date, end_date, status  " +
                    "FROM Subtask " +
                    "WHERE task_id IN (SELECT task_id FROM Task WHERE project_id = ?)";

            try (PreparedStatement moveSubtasksStmt = conn.prepareStatement(moveSubtasksQuery)) {
                moveSubtasksStmt.setInt(1, id);
                moveSubtasksStmt.executeUpdate();
            }

            // Remove subtasks from Subtask table
            String deleteSubtasksQuery = "DELETE FROM Subtask WHERE task_id IN (SELECT task_id FROM Task WHERE project_id = ?)";

            try (PreparedStatement deleteSubtasksStmt = conn.prepareStatement(deleteSubtasksQuery)) {
                deleteSubtasksStmt.setInt(1, id);
                deleteSubtasksStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void doneTask(int taskId) {
        try (Connection conn = DbManager.getConnection()) {
            // Move tasks to DoneTask table
            String moveTasksQuery = "INSERT INTO DoneTask (task_id, name, description, start_date, end_date, status, project_id) " +
                    "SELECT task_id, name, description, start_date, end_date, status, project_id FROM Task WHERE task_id = ?;";

            try (PreparedStatement moveTasksStmt = conn.prepareStatement(moveTasksQuery)) {
                moveTasksStmt.setInt(1, taskId);
                moveTasksStmt.executeUpdate();
            }

            // Remove tasks from Task table
            String deleteTasksQuery = "DELETE FROM Task WHERE task_id = ?";

            try (PreparedStatement deleteTasksStmt = conn.prepareStatement(deleteTasksQuery)) {
                deleteTasksStmt.setInt(1, taskId);
                deleteTasksStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<DoneTaskDTO> getAllDoneTask(int id) {
        List<DoneTaskDTO> doneTaskDTOS = new ArrayList<>();

        try (Connection conn = DbManager.getConnection()) {
            String query = "SELECT * FROM DoneTask WHERE project_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DoneTaskDTO doneTaskDTO = new DoneTaskDTO();
                doneTaskDTO.setTaskId(rs.getInt("task_id"));
                doneTaskDTO.setTaskName(rs.getString("name"));
                doneTaskDTO.setTaskDescription(rs.getString("description"));
                doneTaskDTO.setTaskStartDate(rs.getDate("start_date").toLocalDate());
                doneTaskDTO.setTaskEndDate(rs.getDate("end_date").toLocalDate());
                doneTaskDTO.setTaskCompletedDate(rs.getDate("task_completed_date").toLocalDate());
                doneTaskDTO.setStatus(rs.getString("status"));
                doneTaskDTO.setProjectId(rs.getInt("project_id"));

                doneTaskDTOS.add(doneTaskDTO);


            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return doneTaskDTOS;
    }
}
