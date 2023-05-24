package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneProjectDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.List;

@Repository("ProjectRepository")
public interface IProjectRepository {
    boolean isUserAuthorized(int userId, int projectId);

    User getUserFromId(int id);

    List<Project> getAllProjectById(int userId);

    List<TaskAndSubtaskDTO> getTaskAndSubTaskList(int projectId);

    Project getProjectById(int id);

    void createProject(Project project, int userId);

    void deleteProject(int projectId) throws LoginException;

    void updateProject(Project project);

    LocalDate getStartDateProject(int projectId);

    LocalDate getStartDateTask(int taskId);

    LocalDate getEndDateTask(int taskId);

    LocalDate getEndDateProject(int projectId);

    void updateTaskAndSubtaskDates(TaskAndSubtaskDTO task);

    void doneAllSubtask(int id);

    void doneProject(int projectId);

    void doneAllTask(int projectId);

    List<DoneProjectDTO> getDoneProjectsByUserId(int userId);

    void addMemberToProject(int projectId, int memberUserId);

    int getUserIdByEmail(String email);

    List<String> getAllEmailsOnProject(int projectId);
}
