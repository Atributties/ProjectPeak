package com.example.projectpeak1.services;


import com.example.projectpeak1.dto.DoneProjectDTO;
import com.example.projectpeak1.dto.DoneTaskDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.IRepository;
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
    IRepository repository;

    public TaskService(ApplicationContext context, @Value("${projectPeak.repository.impl}") String impl) {
        repository = (IRepository) context.getBean(impl);
    }

    public void createTask(Task task, int projectId){
        repository.createTask(task, projectId);
    }

    public Task getTaskById(int id){
        return repository.getTaskById(id);
    }

    public TaskAndSubtaskDTO getTaskAndSubTask(int id){
        return repository.getTaskAndSubTask(id);
    }



    public void deleteTask(int taskId) throws LoginException {
        repository.deleteTask(taskId);
    }

    public User getUserFromId(int id){
        return repository.getUserFromId(id);
    }

    public Project getProjectFromId(int projectId) {
        return repository.getProjectById(projectId);
    }

    public void updateTask(Task task) {
        repository.editTask(task);
    }

    public int getProjectIdFromTaskId(int taskId) {
        return repository.getProjectIdByTaskId(taskId);
    }

    public Project getProjectByTaskId(int taskId) {
        return repository.getProjectByTaskId(taskId);
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

        List<Subtask> list = repository.getSubtasksByTaskId(task.getTaskId());

        for (Subtask subtask : list) {
            // Update subtask start and end dates based on the task's start and end date differences
            subtask.setSubTaskStartDate(subtask.getSubTaskStartDate().plusDays(startDateDifference.getDays()));
            subtask.setSubTaskEndDate(subtask.getSubTaskEndDate().plusDays(endDateDifference.getDays()));

            repository.updateSubtaskDates(subtask);
        }
    }

    public void doneTask(int id){
        int projectId = repository.getProjectIdByTaskId(id);

        repository.doneAllSubtask(projectId);
        repository.doneTask(id);
    }

    public List<DoneTaskDTO> seeAllDoneTask(int id) {
        List<DoneTaskDTO> doneTaskDTOS = new ArrayList<>();

        List<DoneTaskDTO> listFromDatabase = repository.getAllDoneTask(id);
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

