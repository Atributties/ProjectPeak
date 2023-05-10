package com.example.projectpeak1.repositories;


import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.util.List;

@Repository
public interface IRepository {




    //_____________LOGIN CONTROLLER________________

    //_____________PROJECT CONTROLLER______________

    //_____________SUBTASK CONTROLLER______________

    //_____________TASK CONTROLLER_________________
    User login(String email, String password) throws LoginException;

    User createUser(User user) throws LoginException;


    void editUser(User user) throws LoginException;

    User getUserFromId(int id);

    void deleteUser(int userId) throws LoginException;


    void createProject(Project list, int projectId);

    List<Project> getAllProjectById(int userId);

    void deleteProject(int id) throws LoginException;

    Project getProjectById(int id);

    void updateProject(Project project);

    void createTask(Task task, int projectId);

    List<Task> getAllTaskById(int id);

    Task getTaskById(int id);

    void editTask(Task task);

    List<TaskAndSubtaskDTO> getTaskAndSubTask(int projectId);

    void deleteTask(int taskId) throws LoginException;

    void createSubtask(Subtask subtask, int taskId);

    Subtask getSubtaskById(int subtaskId);

    void editSubtask(Subtask subtask);

    int getProjectIdBySubtaskId(int subtaskId);

    void deleteSubtask(int subtaskId) throws LoginException;
}
