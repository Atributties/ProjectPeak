package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneTaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.util.List;

@Repository("TaskRepository")
public interface ITaskRepository {

    boolean isUserAuthorized(int userId, int projectId);

    User getUserFromId(int id);

    void createTask(Task task, int projectId);

    Task getTaskById(int id);

    void deleteTask(int taskId) throws LoginException;

    Project getProjectById(int id);

    void editTask(Task task);

    int getProjectIdByTaskId(int taskId);

    List<Subtask> getSubtasksByTaskId(int taskId);

    void updateSubtaskDates(Subtask subtask);

    void doneAllSubtask(int id);

    void doneTask(int taskId);

    List<DoneTaskDTO> getAllDoneTask(int id);
}
