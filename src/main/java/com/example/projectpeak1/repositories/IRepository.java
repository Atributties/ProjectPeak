package com.example.projectpeak1.repositories;


import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
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


    List<TaskAndSubtaskDTO> getTaskAndSubTaskList(int projectId);

    void deleteTask(int taskId) throws LoginException;

    User getUserFromTaskId(int taskId);

    void createSubtask(Subtask subtask, int taskId);

    Subtask getSubtaskById(int subtaskId);

    List<Subtask> getSubtasksByTaskId(int taskId);

    void editSubtask(Subtask subtask);

    int getProjectIdBySubtaskId(int subtaskId);

    void deleteSubtask(int subtaskId) throws LoginException;

    TaskAndSubtaskDTO getTaskAndSubTask(int id);

    int getProjectIdByTaskId(int taskId);

    void editTask(Task task);

    Project getProjectByTaskId(int taskId);

    int getTaskIdBySubtaskId(int subtaskId) throws LoginException;

    void updateUser(User user);

    LocalDate getStartDateProject(int projectId);

    LocalDate getStartDateTask(int taskId);

    LocalDate getStartDateSubtask(int subTaskId);

    LocalDate getEndDateTask(int taskId);

    LocalDate getEndDateProject(int projectId);

    LocalDate getEndDateSubtask(int subTaskId);

    void updateTaskAndSubtaskDates(TaskAndSubtaskDTO task);

    void updateSubtaskDates(Subtask subtask);
}
