package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneSubtaskDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.List;

@Repository("SubtaskRepository_STUB")
public class SubtaskRepository_STUB implements ISubtaskRepository{
    @Override
    public boolean isUserAuthorized(int userId, int projectId) {
        return false;
    }

    @Override
    public User getUserFromId(int id) {
        return null;
    }

    @Override
    public void createSubtask(Subtask subtask, int taskId) {

    }

    @Override
    public Subtask getSubtaskById(int id) {
        return null;
    }

    @Override
    public List<Subtask> getSubtasksByTaskId(int taskId) {
        return null;
    }

    @Override
    public void editSubtask(Subtask subtask) {

    }

    @Override
    public int getProjectIdBySubtaskId(int subtaskId) {
        return 0;
    }

    @Override
    public void deleteSubtask(int subtaskId) throws LoginException {

    }

    @Override
    public TaskAndSubtaskDTO getTaskAndSubTask(int taskId) {
        return null;
    }

    @Override
    public int getTaskIdBySubtaskId(int subtaskId) throws LoginException {
        return 0;
    }

    @Override
    public LocalDate getStartDateSubtask(int subtaskId) {
        return null;
    }

    @Override
    public LocalDate getEndDateSubtask(int subTaskId) {
        return null;
    }

    @Override
    public void doneSubtask(int subtaskId) {

    }

    @Override
    public List<DoneSubtaskDTO> getAllDoneSubtask(int taskId) {
        return null;
    }
}
