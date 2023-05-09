package com.example.projectpeak1.repositories;


import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.SubTask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.util.List;

@Repository
public interface IRepository {
    User login(String email, String password) throws LoginException;

    User createUser(User user) throws LoginException;


    void editUser(User user) throws LoginException;

    User getUserFromId(int id);

    void deleteUser(int userId) throws LoginException;


    Project createProject(Project list, int projectId);

    List<Project> getAllProjectById(int userId);

    void deleteProject(int id) throws LoginException;

    Project getProjectById(int id);

    void updateProject(Project project);

    public Task createTask(Task task, int projectId);

    List<Task> getAllTaskById(int id);

    Task getTaskById(int id);

    void editTask(Task task);

    SubTask createsubTask(SubTask subTask, int projectId);
}
