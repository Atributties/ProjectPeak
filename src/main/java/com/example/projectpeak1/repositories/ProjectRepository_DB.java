package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneProjectDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.utility.DbManager;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository("ProjectRepository_DB")
public class ProjectRepository_DB implements IProjectRepository {

    //__________________USER DATA__________________________________
    @Override
    public boolean isUserAuthorized(int userId, int projectId){
        try {
            Connection con = DbManager.getConnection();
            String query = "SELECT COUNT(*) FROM projectmember WHERE project_id = ? AND user_id = ?";
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
            String SQL = "SELECT * FROM user WHERE user_id = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            User user1 = null;
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("fullname");
                String email = rs.getString("email");
                String userPassword = rs.getString("user_password");
                user1 = new User(userId, fullName, email, userPassword);
            }

            return user1;

        } catch (SQLException ex) {
            return null;
        }
    }
    @Override
    public int getUserIdByEmail(String email) {
        int userId = 0;

        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT user_id FROM user WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("user_id");
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle the exception appropriately
        }

        return userId;
    }





    //__________________PROJECT DATA_______________________________
    @Override
    public List<Project> getAllProjectById(int userId) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT p.* FROM project p " +
                    "INNER JOIN projectmember pm ON p.project_id = pm.project_id " +
                    "WHERE pm.user_id = ?";
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
                int projectUserId = rs.getInt("user_id");
                Project project = new Project(projectId, projectName, projectDescription, projectStartDate, projectEndDate, projectUserId);
                list.add(project);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
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
    public void createProject(Project project, int userId) {
        try {
            Connection con = DbManager.getConnection();
            String projectSQL = "INSERT INTO project (name, description, start_date, end_date, user_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement projectPs = con.prepareStatement(projectSQL, PreparedStatement.RETURN_GENERATED_KEYS);

            projectPs.setString(1, project.getProjectName());
            projectPs.setString(2, project.getProjectDescription());
            projectPs.setDate(3, Date.valueOf(project.getProjectStartDate()));
            projectPs.setDate(4, Date.valueOf(project.getProjectEndDate()));
            projectPs.setInt(5, userId);
            projectPs.executeUpdate();
            ResultSet projectIds = projectPs.getGeneratedKeys();
            projectIds.next();
            int projectId = projectIds.getInt(1);

            String projectMemberSQL = "INSERT INTO projectMember (project_id, user_id) VALUES (?, ?)";
            PreparedStatement projectMemberPs = con.prepareStatement(projectMemberSQL);
            projectMemberPs.setInt(1, projectId);
            projectMemberPs.setInt(2, userId);
            projectMemberPs.executeUpdate();

            Project createdProject = new Project(project.getProjectName(), project.getProjectDescription(), project.getProjectStartDate(), project.getProjectEndDate(), userId);
            createdProject.setProjectId(projectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteProject(int projectId) throws LoginException {
        try (Connection con = DbManager.getConnection()) {
            // Delete project from ProjectMember
            String deleteProjectMemberSQL = "DELETE FROM projectmember WHERE project_id = ?";
            try (PreparedStatement deleteProjectMemberStmt = con.prepareStatement(deleteProjectMemberSQL)) {
                deleteProjectMemberStmt.setInt(1, projectId);
                deleteProjectMemberStmt.executeUpdate();
            }

            // Delete subtasks
            String deleteSubtasksSQL = "DELETE FROM subtask WHERE task_id IN (SELECT task_id FROM task WHERE project_id = ?)";
            try (PreparedStatement deleteSubtasksStmt = con.prepareStatement(deleteSubtasksSQL)) {
                deleteSubtasksStmt.setInt(1, projectId);
                deleteSubtasksStmt.executeUpdate();
            }

            // Delete tasks
            String deleteTasksSQL = "DELETE FROM task WHERE project_id = ?";
            try (PreparedStatement deleteTasksStmt = con.prepareStatement(deleteTasksSQL)) {
                deleteTasksStmt.setInt(1, projectId);
                deleteTasksStmt.executeUpdate();
            }

            // Delete the project record
            String deleteProjectSQL = "DELETE FROM project WHERE project_id = ?";
            try (PreparedStatement deleteProjectStmt = con.prepareStatement(deleteProjectSQL)) {
                deleteProjectStmt.setInt(1, projectId);
                deleteProjectStmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
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
    @Override
    public LocalDate getStartDateProject(int projectId) {
        LocalDate startDate = null;

        try (Connection con = DbManager.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT start_date FROM project WHERE project_id = ?")) {

            statement.setInt(1, projectId);
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
    public LocalDate getEndDateProject(int projectId) {
        LocalDate endDate = null;

        try (Connection con = DbManager.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT end_date FROM project WHERE project_id = ?")) {

            statement.setInt(1, projectId);
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
    public List<String> getAllEmailsOnProject(int projectId) {
        List<String> emails = new ArrayList<>();

        String sql = "SELECT DISTINCT user.email FROM user " +
                "JOIN projectmember ON user.user_id = projectmember.user_id " +
                "WHERE projectmember.project_id = ?";
        Connection con = DbManager.getConnection();

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                String email = resultSet.getString("email");
                emails.add(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emails;
    }
    @Override
    public void doneProject(int projectId) {
        try (Connection conn = DbManager.getConnection()) {
            // Move project to DoneProject table
            String moveProjectQuery = "INSERT INTO doneproject (project_id, name, description, start_date, end_date, user_id) " +
                    "SELECT project_id, name, description, start_date, end_date, user_id " +
                    "FROM project " +
                    "WHERE project_id = ?";

            try (PreparedStatement moveProjectStmt = conn.prepareStatement(moveProjectQuery)) {
                moveProjectStmt.setInt(1, projectId);
                moveProjectStmt.executeUpdate();
            }

            // Remove project from Project table
            String deleteProjectQuery = "DELETE FROM project WHERE project_id = ?";

            try (PreparedStatement deleteProjectStmt = conn.prepareStatement(deleteProjectQuery)) {
                deleteProjectStmt.setInt(1, projectId);
                deleteProjectStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    @Override
    public List<DoneProjectDTO> getDoneProjectsByUserId(int userId) {
        List<DoneProjectDTO> doneProjects = new ArrayList<>();

        try (Connection conn = DbManager.getConnection()) {
            String query = "SELECT * FROM doneproject WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DoneProjectDTO doneProject = new DoneProjectDTO();
                doneProject.setProjectId(rs.getInt("project_id"));
                doneProject.setProjectName(rs.getString("name"));
                doneProject.setProjectDescription(rs.getString("description"));
                doneProject.setProjectStartDate(rs.getDate("start_date").toLocalDate());
                doneProject.setProjectEndDate(rs.getDate("end_date").toLocalDate());
                doneProject.setProjectCompletedDate(rs.getDate("project_completed_date").toLocalDate());
                doneProject.setUserId(rs.getInt("user_id"));

                doneProjects.add(doneProject);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return doneProjects;
    }

    @Override
    public void addMemberToProject(int projectId, int memberUserId) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "INSERT INTO projectmember (project_id, user_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, projectId);
            ps.setInt(2, memberUserId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle the exception appropriately
        }
    }





    //__________________TASK AND SUBTASK DATA_________________________________
    @Override
    public List<TaskAndSubtaskDTO> getTaskAndSubTaskList(int projectId) {
        try {
            List<TaskAndSubtaskDTO> taskAndSubtaskDTOs = new ArrayList<>();
            Connection con = DbManager.getConnection();
            String taskSql = "SELECT * FROM task WHERE project_id = ?;";
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

                String subtaskSql = "SELECT * FROM subtask WHERE task_id = ?;";
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
    public LocalDate getStartDateTask(int taskId) {
        LocalDate startDate = null;

        try (Connection con = DbManager.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT start_date FROM task WHERE task_id = ?")) {

            statement.setInt(1, taskId);
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
    public LocalDate getEndDateTask(int taskId) {
        LocalDate endDate = null;

        try (Connection con = DbManager.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT end_date FROM task WHERE task_id = ?")) {

            statement.setInt(1, taskId);
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
    public void updateTaskAndSubtaskDates(TaskAndSubtaskDTO task) {
        try (Connection con = DbManager.getConnection()) {
            String updateTaskQuery = "UPDATE task SET start_date = ?, end_date = ? WHERE task_id = ?";
            String updateSubtaskQuery = "UPDATE subtask SET start_date = ?, end_date = ? WHERE subtask_id = ?";

            // Update task
            PreparedStatement taskStatement = con.prepareStatement(updateTaskQuery);
            taskStatement.setDate(1, Date.valueOf(task.getStartDate()));
            taskStatement.setDate(2, Date.valueOf(task.getEndDate()));
            taskStatement.setInt(3, task.getId());
            taskStatement.executeUpdate();
            taskStatement.close();

            // Update subtasks
            for (Subtask subtask : task.getSubTaskList()) {
                PreparedStatement subtaskStatement = con.prepareStatement(updateSubtaskQuery);
                subtaskStatement.setDate(1, Date.valueOf(subtask.getSubTaskStartDate()));
                subtaskStatement.setDate(2, Date.valueOf(subtask.getSubTaskEndDate()));
                subtaskStatement.setInt(3, subtask.getSubTaskId());
                subtaskStatement.executeUpdate();
                subtaskStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void doneAllSubtask(int id) {
        try (Connection conn = DbManager.getConnection()) {
            // Move subtasks to DoneSubtask table
            String moveSubtasksQuery = "INSERT INTO doneSubtask (subtask_id, task_id, name, description, start_date, end_date, status) " +
                    "SELECT subtask_id,task_id, name, description, start_date, end_date, status  " +
                    "FROM subtask " +
                    "WHERE task_id IN (SELECT task_id FROM task WHERE project_id = ?)";

            try (PreparedStatement moveSubtasksStmt = conn.prepareStatement(moveSubtasksQuery)) {
                moveSubtasksStmt.setInt(1, id);
                moveSubtasksStmt.executeUpdate();
            }

            // Remove subtasks from Subtask table
            String deleteSubtasksQuery = "DELETE FROM subtask WHERE task_id IN (SELECT task_id FROM task WHERE project_id = ?)";

            try (PreparedStatement deleteSubtasksStmt = conn.prepareStatement(deleteSubtasksQuery)) {
                deleteSubtasksStmt.setInt(1, id);
                deleteSubtasksStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void doneAllTask(int projectId) {
        try (Connection conn = DbManager.getConnection()) {
            // Move tasks to DoneTask table
            String moveTasksQuery = "INSERT INTO doneTask (task_id, name, description, start_date, end_date, status, project_id) " +
                    "SELECT task_id, name, description, start_date, end_date, status, project_id " +
                    "FROM task " +
                    "WHERE project_id = ?";

            try (PreparedStatement moveTasksStmt = conn.prepareStatement(moveTasksQuery)) {
                moveTasksStmt.setInt(1, projectId);
                moveTasksStmt.executeUpdate();
            }

            // Remove tasks from Task table
            String deleteTasksQuery = "DELETE FROM task WHERE project_id = ?";

            try (PreparedStatement deleteTasksStmt = conn.prepareStatement(deleteTasksQuery)) {
                deleteTasksStmt.setInt(1, projectId);
                deleteTasksStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
