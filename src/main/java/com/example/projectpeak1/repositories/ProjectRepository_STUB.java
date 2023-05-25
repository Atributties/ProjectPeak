package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneProjectDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.List;

@Repository("ProjectRepository_STUB")
public class ProjectRepository_STUB implements IProjectRepository{
    @Override
    public boolean isUserAuthorized(int userId, int projectId) {
        return false;
    }

    @Override
    public User getUserFromId(int id) {
        return null;
    }

    @Override
    public List<Project> getAllProjectById(int userId) {
        return null;
    }

    @Override
    public List<TaskAndSubtaskDTO> getTaskAndSubTaskList(int projectId) {
        return null;
    }

    @Override
    public Project getProjectById(int id) {
        return null;
    }

    @Override
    public void createProject(Project project, int userId) {

    }

    @Override
    public void deleteProject(int projectId) throws LoginException {

    }

    @Override
    public void updateProject(Project project) {

    }

    @Override
    public LocalDate getStartDateProject(int projectId) {
        return null;
    }

    @Override
    public LocalDate getStartDateTask(int taskId) {
        return null;
    }

    @Override
    public LocalDate getEndDateTask(int taskId) {
        return null;
    }

    @Override
    public LocalDate getEndDateProject(int projectId) {
        return null;
    }

    @Override
    public void updateTaskAndSubtaskDates(TaskAndSubtaskDTO task) {

    }

    @Override
    public void doneAllSubtask(int id) {

    }

    @Override
    public void doneProject(int projectId) {

    }

    @Override
    public void doneAllTask(int projectId) {

    }

    @Override
    public List<DoneProjectDTO> getDoneProjectsByUserId(int userId) {
        return null;
    }

    @Override
    public void addMemberToProject(int projectId, int memberUserId) {

    }

    @Override
    public int getUserIdByEmail(String email) {
        return 0;
    }

    @Override
    public List<String> getAllEmailsOnProject(int projectId) {
        return null;
    }
}
