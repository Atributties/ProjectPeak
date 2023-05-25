package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneTaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.util.List;
@Repository("TaskRepository_STUB")
public class TaskRepository_STUB implements ITaskRepository {
    @Override
    public boolean isUserAuthorized(int userId, int projectId) {
        return false;
    }

    @Override
    public User getUserFromId(int id) {
        return null;
    }

    @Override
    public void createTask(Task task, int projectId) {

    }

    @Override
    public Task getTaskById(int id) {
        return null;
    }

    @Override
    public void deleteTask(int taskId) throws LoginException {

    }

    @Override
    public Project getProjectById(int id) {
        return null;
    }

    @Override
    public void editTask(Task task) {

    }

    @Override
    public int getProjectIdByTaskId(int taskId) {
        return 0;
    }

    @Override
    public List<Subtask> getSubtasksByTaskId(int taskId) {
        return null;
    }

    @Override
    public void updateSubtaskDates(Subtask subtask) {

    }

    @Override
    public void doneAllSubtask(int id) {

    }

    @Override
    public void doneTask(int taskId) {

    }

    @Override
    public List<DoneTaskDTO> getAllDoneTask(int id) {
        return null;
    }
}
