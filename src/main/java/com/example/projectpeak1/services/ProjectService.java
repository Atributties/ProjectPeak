package com.example.projectpeak1.services;


import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.IRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ProjectService {


    IRepository repository;

    public ProjectService(ApplicationContext context, @Value("${projectPeak.repository.impl}") String impl) {
        repository = (IRepository) context.getBean(impl);
    }


    public User getUserFromId(int id){
        return repository.getUserFromId(id);
    }

    public List<Project> getAllProjectById(int userId){
        return repository.getAllProjectById(userId);
    }

    public List<TaskAndSubtaskDTO> getTaskAndSubTask(int projectId){
        return repository.getTaskAndSubTaskList(projectId);
    }

    public Project getProjectById(int id){
        return repository.getProjectById(id);
    }

    public void createProject(Project list, int projectId){
        repository.createProject(list, projectId);
    }

    public void deleteProject(int id) throws LoginException {
        repository.deleteProject(id);
    }

    public void updateProject(Project project){
        repository.updateProject(project);
    }


    public int getDaysToStartProject(int projectId) {

        LocalDate startDate = repository.getStartDateProject(projectId);
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(currentDate, startDate);

    }

    public int getDaysToStartTask(int taskId) {
        LocalDate startDate = repository.getStartDateTask(taskId);
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(currentDate, startDate);

    }


    public int getDaysForTask(int taskId) {
        LocalDate startDate = repository.getStartDateTask(taskId);
        LocalDate endDate = repository.getEndDateTask(taskId);

        return (int) ChronoUnit.DAYS.between(startDate, endDate);

    }

    public int getDaysForProject(int projectId) {
        LocalDate startDate = repository.getStartDateProject(projectId);
        LocalDate endDate = repository.getEndDateProject(projectId);
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public int getDaysLeftProject(int projectId) {
        LocalDate endDate = repository.getEndDateProject(projectId);
        LocalDate startDate = repository.getStartDateProject(projectId);
        LocalDate currentDate = LocalDate.now();

        if(currentDate.isBefore(startDate)){
            return 0;
        }else {
            return (int) ChronoUnit.DAYS.between(currentDate, endDate);
        }

    }

    public int getDaysLeftTask(int taskId) {

        LocalDate endDate = repository.getEndDateTask(taskId);
        LocalDate startDate = repository.getStartDateTask(taskId);
        LocalDate currentDate = LocalDate.now();

        if(currentDate.isBefore(startDate)){
            return 0;
        }else {
            return (int) ChronoUnit.DAYS.between(currentDate, endDate);
        }
    }
}
