package com.example.projectpeak1.services;

import com.example.projectpeak1.dto.DoneTaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class TaskService {
    ITaskRepository taskRepository;



    public TaskService(ITaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public boolean isUserAuthorized(int userId, int projectId){
        return taskRepository.isUserAuthorized(userId, projectId);
    }

    public User getUserFromId(int id){
        return taskRepository.getUserFromId(id);
    }

    public void createTask(Task task, int projectId){
        taskRepository.createTask(task, projectId);
    }

    public Task getTaskById(int id){
        return taskRepository.getTaskById(id);
    }


    public void deleteTask(int taskId) throws LoginException {
        taskRepository.deleteTask(taskId);
    }



    public Project getProjectFromId(int projectId) {
        return taskRepository.getProjectById(projectId);
    }

    public void updateTask(Task task) {
        taskRepository.editTask(task);
    }

    public int getProjectIdFromTaskId(int taskId) {
        return taskRepository.getProjectIdByTaskId(taskId);
    }

    public void updateSubtaskDates(Task task, Task originalTask) {
        // Calculate the period between the original and updated project start dates
        Period startDateDifference = Period.between(originalTask.getTaskStartDate(), task.getTaskStartDate());

        // Calculate the period between the original and updated project end dates
        Period endDateDifference = Period.between(originalTask.getTaskEndDate(), task.getTaskEndDate());

        if (endDateDifference.isZero()) {
            // Only the start date has changed, so use the start date difference
            endDateDifference = startDateDifference;
        } else {
            // Only update the end date, so reset the start date difference to zero
            startDateDifference = Period.ZERO;
            endDateDifference = Period.ZERO;
        }

        List<Subtask> list = taskRepository.getSubtasksByTaskId(task.getTaskId());

        for (Subtask subtask : list) {
            // Update subtask start and end dates based on the task's start and end date differences
            subtask.setSubTaskStartDate(subtask.getSubTaskStartDate().plusDays(startDateDifference.getDays()));
            subtask.setSubTaskEndDate(subtask.getSubTaskEndDate().plusDays(endDateDifference.getDays()));

            taskRepository.updateSubtaskDates(subtask);
        }
    }

    public void doneTask(int id){
        int projectId = taskRepository.getProjectIdByTaskId(id);

        taskRepository.doneAllSubtask(projectId);
        taskRepository.doneTask(id);
    }

    public List<DoneTaskDTO> seeAllDoneTask(int id) {
        List<DoneTaskDTO> doneTaskDTOS = new ArrayList<>();

        List<DoneTaskDTO> listFromDatabase = taskRepository.getAllDoneTask(id);
        // Iterate over each done project and calculate the expected and used days
        for (DoneTaskDTO doneTaskDTO : listFromDatabase) {

            LocalDate taskStartDate = doneTaskDTO.getTaskStartDate();
            LocalDate taskEndDate = doneTaskDTO.getTaskEndDate();
            LocalDate taskCompletedDate = doneTaskDTO.getTaskCompletedDate();

            long calculateExpectedDays = ChronoUnit.DAYS.between(taskStartDate, taskEndDate);
            long calculateUsedDays = ChronoUnit.DAYS.between(taskStartDate, taskCompletedDate);

            doneTaskDTO.setTaskExpectedDays((int) calculateExpectedDays);
            doneTaskDTO.setTaskUsedDays((int) calculateUsedDays);

            doneTaskDTOS.add(doneTaskDTO);
        }

        return doneTaskDTOS;
    }
}

