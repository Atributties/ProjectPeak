package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneSubtaskDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.List;

@Repository("SubtaskRepository")
public interface ISubtaskRepository {
    boolean isUserAuthorized(int userId, int projectId);

    User getUserFromId(int id);

    void createSubtask(Subtask subtask, int taskId);

    Subtask getSubtaskById(int id);

    List<Subtask> getSubtasksByTaskId(int taskId);

    void editSubtask(Subtask subtask);

    int getProjectIdBySubtaskId(int subtaskId);

    void deleteSubtask(int subtaskId) throws LoginException;

    TaskAndSubtaskDTO getTaskAndSubTask(int taskId);

    int getTaskIdBySubtaskId(int subtaskId) throws LoginException;

    LocalDate getStartDateSubtask(int subtaskId);

    LocalDate getEndDateSubtask(int subTaskId);

    void doneSubtask(int subtaskId);

    List<DoneSubtaskDTO> getAllDoneSubtask(int taskId);
}
