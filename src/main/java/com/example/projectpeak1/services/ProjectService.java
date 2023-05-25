package com.example.projectpeak1.services;


import com.example.projectpeak1.dto.DoneProjectDTO;
import com.example.projectpeak1.dto.DoneSubtaskDTO;
import com.example.projectpeak1.dto.DoneTaskDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.ILoginRepository;
import com.example.projectpeak1.repositories.IProjectRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {


    IProjectRepository projectRepository;

    public ProjectService(ApplicationContext context, @Value("${projectrepository.impl}") String impl) {
        this.projectRepository = (IProjectRepository) context.getBean(impl);
    }

    public boolean isUserAuthorized(int userId, int projectId){
        return projectRepository.isUserAuthorized(userId, projectId);
    }


    public User getUserFromId(int id){
        return projectRepository.getUserFromId(id);
    }

    public List<Project> getAllProjectById(int userId){
        return projectRepository.getAllProjectById(userId);
    }

    public List<TaskAndSubtaskDTO> getTaskAndSubTask(int projectId){
        return projectRepository.getTaskAndSubTaskList(projectId);
    }

    public Project getProjectById(int id){
        return projectRepository.getProjectById(id);
    }

    public void createProject(Project list, int projectId){
        projectRepository.createProject(list, projectId);
    }

    public void deleteProject(int id) throws LoginException {
        projectRepository.deleteProject(id);
    }

    public void updateProject(Project project){
        projectRepository.updateProject(project);
    }


    public int getDaysToStartProject(int projectId) {

        LocalDate startDate = projectRepository.getStartDateProject(projectId);
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(currentDate, startDate);

    }

    public int getDaysToStartTask(int taskId) {
        LocalDate startDate = projectRepository.getStartDateTask(taskId);
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(currentDate, startDate);

    }


    public int getDaysForTask(int taskId) {
        LocalDate startDate = projectRepository.getStartDateTask(taskId);
        LocalDate endDate = projectRepository.getEndDateTask(taskId);

        return (int) ChronoUnit.DAYS.between(startDate, endDate);

    }

    public int getDaysForProject(int projectId) {
        LocalDate startDate = projectRepository.getStartDateProject(projectId);
        LocalDate endDate = projectRepository.getEndDateProject(projectId);
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public int getDaysLeftProject(int projectId) {
        LocalDate endDate = projectRepository.getEndDateProject(projectId);
        LocalDate startDate = projectRepository.getStartDateProject(projectId);
        LocalDate currentDate = LocalDate.now();

        if(currentDate.isBefore(startDate)){
            return 0;
        }else {
            return (int) ChronoUnit.DAYS.between(currentDate, endDate);
        }

    }

    public int getDaysLeftTask(int taskId) {

        LocalDate endDate = projectRepository.getEndDateTask(taskId);
        LocalDate startDate = projectRepository.getStartDateTask(taskId);
        LocalDate currentDate = LocalDate.now();

        if(currentDate.isBefore(startDate)){
            return 0;
        }else {
            return (int) ChronoUnit.DAYS.between(currentDate, endDate);
        }
    }

    public void updateTaskAndSubtaskDates(Project project, Project originalProject) {
        // Calculate the period between the original and updated project start dates
        Period startDateDifference = Period.between(originalProject.getProjectStartDate(), project.getProjectStartDate());

        // Calculate the period between the original and updated project end dates
        Period endDateDifference = Period.between(originalProject.getProjectEndDate(), project.getProjectEndDate());

        if (endDateDifference.isZero()) {
            // Only the start date has changed, so use the start date difference
            endDateDifference = startDateDifference;
        } else {
            // Only update the end date, so reset the start date difference to zero
            startDateDifference = Period.ZERO;
            endDateDifference = Period.ZERO;
        }

        List<TaskAndSubtaskDTO> list = projectRepository.getTaskAndSubTaskList(project.getProjectId());

        for (TaskAndSubtaskDTO task : list) {
            // Update task start and end dates based on the project's start and end date differences
            task.setStartDate(task.getStartDate().plusDays(startDateDifference.getDays()));
            task.setEndDate(task.getEndDate().plusDays(endDateDifference.getDays()));

            // Update subtask start and end dates based on the task's start and end date differences
            for (Subtask subtask : task.getSubTaskList()) {
                subtask.setSubTaskStartDate(subtask.getSubTaskStartDate().plusDays(startDateDifference.getDays()));
                subtask.setSubTaskEndDate(subtask.getSubTaskEndDate().plusDays(endDateDifference.getDays()));
            }
            projectRepository.updateTaskAndSubtaskDates(task);
        }
    }

    public void doneProject(int id) {
        projectRepository.doneAllSubtask(id);

        projectRepository.doneAllTask(id);

        projectRepository.doneProject(id);
    }


    public List<DoneProjectDTO> seeAllDoneProjects(int userId) {
        List<DoneProjectDTO> doneProjectDTOList = new ArrayList<>();

        List<DoneProjectDTO> listFromDatabase = projectRepository.getDoneProjectsByUserId(userId);
        // Iterate over each done project and calculate the expected and used days
        for (DoneProjectDTO doneProject : listFromDatabase) {

            LocalDate projectStartDate = doneProject.getProjectStartDate();
            LocalDate projectEndDate = doneProject.getProjectEndDate();
            LocalDate projectCompletedDate = doneProject.getProjectCompletedDate();

            long calculateExpectedDays = ChronoUnit.DAYS.between(projectStartDate, projectEndDate);
            long calculateUsedDays = ChronoUnit.DAYS.between(projectStartDate, projectCompletedDate);

            doneProject.setProjectExpectedDays((int) calculateExpectedDays);
            doneProject.setProjectUsedDays((int) calculateUsedDays);

            doneProjectDTOList.add(doneProject);
        }

        return doneProjectDTOList;
    }

    public void addMemberToProject(int projectId, int memberUserId) {
        projectRepository.addMemberToProject(projectId, memberUserId);
    }


    public int getUserIdByEmail(String email) {
        return projectRepository.getUserIdByEmail(email);
    }


    public List<String> getAllEmailsOnProject(int projectId) {
        return projectRepository.getAllEmailsOnProject(projectId);
    }

}
