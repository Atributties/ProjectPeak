package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneSubtaskDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.utility.DbManager;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository("SubtaskRepository_DB")
public class SubtaskRepository_DB implements ISubtaskRepository {


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
                user1 = new User(userId, fullName, email, userPassword);
            }
            return user1;

        } catch (SQLException ex) {
            return null;
        }
    }


    //__________________PROJECT DATA_______________________________
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




    //__________________SUBTASK DATA_________________________________
    @Override
    public void createSubtask(Subtask subtask, int taskId) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "INSERT INTO subtask (name, description, start_date, end_date, status, task_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, subtask.getSubTaskName());
            ps.setString(2, subtask.getSubTaskDescription());
            ps.setDate(3, Date.valueOf(subtask.getSubTaskStartDate()));
            ps.setDate(4, Date.valueOf(subtask.getSubTaskEndDate()));
            ps.setString(5, subtask.getStatus());
            ps.setInt(6, taskId);
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);


            Subtask subtask1 = new Subtask(subtask.getSubTaskName(), subtask.getSubTaskDescription(), subtask.getSubTaskStartDate(), subtask.getSubTaskEndDate(), subtask.getStatus(), taskId);
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
                String subTaskName = rs.getString("name");
                String subTaskDescription = rs.getString("description");
                LocalDate subTaskStartDate = rs.getDate("start_date").toLocalDate();
                LocalDate subTaskEndDate = rs.getDate("end_date").toLocalDate();
                String subTaskStatus = rs.getString("status");
                int taskId = rs.getInt("task_id");
                task = new Subtask(id, subTaskName, subTaskDescription, subTaskStartDate, subTaskEndDate, subTaskStatus, taskId);

            }
            return task;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
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
            ps.setInt(6, subtask.getSubTaskId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void deleteSubtask(int subtaskId) throws LoginException {
        try (Connection con = DbManager.getConnection()) {
            // delete the project record
            String SQL = "DELETE FROM Subtask WHERE subtask_id = ?";
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
    @Override
    public int getTaskIdBySubtaskId(int subtaskId) throws LoginException {
        try (Connection con = DbManager.getConnection()) {
            String SQL = "SELECT task_id FROM Subtask WHERE subtask_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setInt(1, subtaskId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("task_id");
                } else {
                    System.out.println("Subtask not found in the database for the provided subtaskId: " + subtaskId);
                    throw new LoginException("Subtask not found");
                }
            }
        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }
    }
    @Override
    public LocalDate getStartDateSubtask(int subtaskId) {
        LocalDate startDate = null;

        try (Connection con = DbManager.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT start_date FROM Subtask WHERE subtask_id = ?")) {

            statement.setInt(1, subtaskId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                startDate = resultSet.getDate("start_date").toLocalDate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return startDate;
    }
    @Override
    public LocalDate getEndDateSubtask(int subTaskId) {
        LocalDate endDate = null;

        try (Connection con = DbManager.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT end_date FROM Subtask WHERE subtask_id = ?")) {

            statement.setInt(1, subTaskId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                endDate = resultSet.getDate("end_date").toLocalDate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return endDate;
    }
    @Override
    public void doneSubtask(int subtaskId) {
        try (Connection conn = DbManager.getConnection()) {
            // Move subtasks to DoneSubtask table
            String moveSubtasksQuery = "INSERT INTO DoneSubtask (subtask_id, name, description, start_date, end_date, status, task_id) " +
                    "SELECT subtask_id, name, description, start_date, end_date, status, task_id " +
                    "FROM Subtask " +
                    "WHERE subtask_id = ?;";

            try (PreparedStatement moveSubtasksStmt = conn.prepareStatement(moveSubtasksQuery)) {
                moveSubtasksStmt.setInt(1, subtaskId);
                moveSubtasksStmt.executeUpdate();
            }

            // Remove subtasks from Subtask table
            String deleteSubtasksQuery = "DELETE FROM Subtask WHERE subtask_id = ?";

            try (PreparedStatement deleteSubtasksStmt = conn.prepareStatement(deleteSubtasksQuery)) {
                deleteSubtasksStmt.setInt(1, subtaskId);
                deleteSubtasksStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<DoneSubtaskDTO> getAllDoneSubtask(int taskId) {
        List<DoneSubtaskDTO> doneSubtaskDTOS = new ArrayList<>();

        try (Connection conn = DbManager.getConnection()) {
            String query = "SELECT * FROM DoneSubtask WHERE task_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DoneSubtaskDTO doneSubtaskDTO = new DoneSubtaskDTO();
                doneSubtaskDTO.setSubTaskId(rs.getInt("subtask_id"));
                doneSubtaskDTO.setSubTaskName(rs.getString("name"));
                doneSubtaskDTO.setSubTaskDescription(rs.getString("description"));
                doneSubtaskDTO.setSubTaskStartDate(rs.getDate("start_date").toLocalDate());
                doneSubtaskDTO.setSubTaskEndDate(rs.getDate("end_date").toLocalDate());
                doneSubtaskDTO.setSubtaskCompletedDate(rs.getDate("subtask_completed_date").toLocalDate());
                doneSubtaskDTO.setStatus(rs.getString("status"));
                doneSubtaskDTO.setTaskId(rs.getInt("task_id"));

                doneSubtaskDTOS.add(doneSubtaskDTO);


            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return doneSubtaskDTOS;
    }



}
