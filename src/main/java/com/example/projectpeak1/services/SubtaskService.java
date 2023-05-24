package com.example.projectpeak1.services;


import com.example.projectpeak1.dto.DoneSubtaskDTO;
import com.example.projectpeak1.dto.DoneTaskDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.User;

import com.example.projectpeak1.repositories.ISubtaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubtaskService {


    ISubtaskRepository subtaskRepository;

    public SubtaskService(ISubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    public boolean isUserAuthorized(int userId, int projectId){
        return subtaskRepository.isUserAuthorized(userId, projectId);
    }

    public User getUserFromId(int id){
        return subtaskRepository.getUserFromId(id);
    }

    public void createSubtask(Subtask subtask, int taskId) {
        subtaskRepository.createSubtask(subtask, taskId);
    }

    public Subtask getSubtaskById(int subtaskId) {
        return subtaskRepository.getSubtaskById(subtaskId);
    }

    public void editSubtask(Subtask subtask) {
        subtaskRepository.editSubtask(subtask);
    }

    public int getProjectIdBtSubtaskId(int subtaskId) {
        return subtaskRepository.getProjectIdBySubtaskId(subtaskId);
    }

    public void deleteSubtask(int subtaskId) throws LoginException {
        subtaskRepository.deleteSubtask(subtaskId);
    }


    public TaskAndSubtaskDTO getTaskAndSubTaskById(int taskId) {
        return subtaskRepository.getTaskAndSubTask(taskId);
    }


    public int getTaskIdBySubtaskId(int subtaskId) throws LoginException {
        return subtaskRepository.getTaskIdBySubtaskId(subtaskId);
    }
    public int getDaysToStartSubtask(int subTaskId) {
        LocalDate startDate = subtaskRepository.getStartDateSubtask(subTaskId);
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(currentDate, startDate);
    }


    public int getDaysForSubtask(int subTaskId) {
        LocalDate startDate = subtaskRepository.getStartDateSubtask(subTaskId);
        LocalDate endDate = subtaskRepository.getEndDateSubtask(subTaskId);
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public int getDaysLeftSubtask(int subTaskId) {

        LocalDate endDate = subtaskRepository.getEndDateSubtask(subTaskId);
        LocalDate startDate = subtaskRepository.getStartDateSubtask(subTaskId);
        LocalDate currentDate = LocalDate.now();

        if(currentDate.isBefore(startDate)){
            return 0;
        }else {
            return (int) ChronoUnit.DAYS.between(currentDate, endDate);
        }

    }

    public void doneSubtask(int id){
        subtaskRepository.doneSubtask(id);
    }

    public List<DoneSubtaskDTO> getAllDoneSubtask(int taskId) {
        List<DoneSubtaskDTO> doneSubtaskDTOS = new ArrayList<>();

        List<DoneSubtaskDTO> listFromDatabase = subtaskRepository.getAllDoneSubtask(taskId);
        // Iterate over each done project and calculate the expected and used days
        for (DoneSubtaskDTO doneSubtaskDTO : listFromDatabase) {

            LocalDate subtaskStartDate = doneSubtaskDTO.getSubTaskStartDate();
            LocalDate subtaskEndDate = doneSubtaskDTO.getSubTaskEndDate();
            LocalDate subtaskCompletedDate = doneSubtaskDTO.getSubtaskCompletedDate();

            long calculateExpectedDays = ChronoUnit.DAYS.between(subtaskStartDate, subtaskEndDate);
            long calculateUsedDays = ChronoUnit.DAYS.between(subtaskStartDate, subtaskCompletedDate);

            doneSubtaskDTO.setSubtaskExpectedDays((int) calculateExpectedDays);
            doneSubtaskDTO.setSubtaskUsedDays((int) calculateUsedDays);

            doneSubtaskDTOS.add(doneSubtaskDTO);
        }

        return doneSubtaskDTOS;
    }
}
