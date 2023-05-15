package com.example.projectpeak1.services;


import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.IRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
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






}
