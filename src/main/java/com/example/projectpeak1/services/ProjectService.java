package com.example.projectpeak1.services;


import com.example.projectpeak1.dto.DoneProjectDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.IProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
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
        List<Project> list = projectRepository.getAllProjectById(userId);

        for (Project project : list ) {
            int daysToStart = getDaysToStartProject(project.getProjectId());
            project.setDaysToStart(daysToStart);
            int daysForProject = getDaysForProject(project.getProjectId());
            project.setDaysForProject(daysForProject);
            int daysLeft = getDaysLeftProject(project.getProjectId());
            project.setDaysLeft(daysLeft);
        }
        return list;
    }

    public List<TaskAndSubtaskDTO> getTaskAndSubTask(int projectId){
        List<TaskAndSubtaskDTO> list = projectRepository.getTaskAndSubTaskList(projectId);

        for (TaskAndSubtaskDTO taskAndSubtaskDTO : list) {
            int daysToStartTask = getDaysToStartTask(taskAndSubtaskDTO.getId());
            taskAndSubtaskDTO.setDaysToStart(daysToStartTask);
            int daysForTask = getDaysForTask(taskAndSubtaskDTO.getId());
            taskAndSubtaskDTO.setDaysTask(daysForTask);
            int daysLeft = getDaysLeftTask(taskAndSubtaskDTO.getId());
            taskAndSubtaskDTO.setDaysLeft(daysLeft);
        }
        return list;
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
        Project originalProject = projectRepository.getProjectById(project.getProjectId());
        // Check if the start date or end date has changed
        if (!originalProject.getProjectStartDate().equals(project.getProjectStartDate())
                || !originalProject.getProjectEndDate().equals(project.getProjectEndDate())) {

            // Update the project
            projectRepository.updateProject(project);

            // Update task and subtask dates
            updateTaskAndSubtaskDates(project, originalProject);
        } else {
            // Only update the project without updating task and subtask dates
            projectRepository.updateProject(project);
        }
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


    public void updateTaskAndSubtaskDates(Project project, Project originalProject) {
        LocalDate originalStartDate = originalProject.getProjectStartDate();
        LocalDate originalEndDate = originalProject.getProjectEndDate();
        LocalDate newStartDate = project.getProjectStartDate();
        LocalDate newEndDate = project.getProjectEndDate();

        // Calculate the difference between the original and new project start dates
        Period startDateDifference = Period.between(originalStartDate, newStartDate);

        // Calculate the difference between the original and new project end dates
        Period endDateDifference = Period.between(originalEndDate, newEndDate);

        List<TaskAndSubtaskDTO> list = projectRepository.getTaskAndSubTaskList(project.getProjectId());

        for (TaskAndSubtaskDTO task : list) {
            // Update task start and end dates based on the project's start and end date differences
            LocalDate taskStartDate = task.getStartDate().plus(startDateDifference);
            LocalDate taskEndDate = task.getEndDate().plus(endDateDifference);
            task.setStartDate(taskStartDate);
            task.setEndDate(taskEndDate);

            // Update subtask start and end dates based on the task's start and end date differences
            for (Subtask subtask : task.getSubTaskList()) {
                LocalDate subtaskStartDate = subtask.getSubTaskStartDate().plus(startDateDifference);
                LocalDate subtaskEndDate = subtask.getSubTaskEndDate().plus(endDateDifference);
                subtask.setSubTaskStartDate(subtaskStartDate);
                subtask.setSubTaskEndDate(subtaskEndDate);
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

    public void addMemberToProject(int projectId, int memberUserId) throws LoginException {
        //Check if the user exit or return exception
        if (memberUserId != 0) {
            projectRepository.addMemberToProject(projectId, memberUserId);
        } else {
            throw new LoginException("user dosen't exist");
        }

    }


    public int getUserIdByEmail(String email) {
        return projectRepository.getUserIdByEmail(email);
    }


    public List<String> getAllEmailsOnProject(int projectId) {
        return projectRepository.getAllEmailsOnProject(projectId);
    }

    public List<List<Object>> getTaskAndSubtaskDisplayGantt(int projectId) {
        List<TaskAndSubtaskDTO> list = projectRepository.getTaskAndSubTaskList(projectId);

        List<List<Object>> chartData = new ArrayList<>();
        for (TaskAndSubtaskDTO taskAndSubtaskDTO : list) {
            List<Object> row = new ArrayList<>();
            row.add(taskAndSubtaskDTO.getName());
            row.add(taskAndSubtaskDTO.getName());
            row.add(taskAndSubtaskDTO.getStartDate());
            row.add(taskAndSubtaskDTO.getEndDate());
            row.add(null);
            row.add(0);
            row.add(null);
            chartData.add(row);
        }
        return chartData;
    }

}
