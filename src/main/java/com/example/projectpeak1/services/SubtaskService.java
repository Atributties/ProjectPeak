package com.example.projectpeak1.services;


import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.IRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class SubtaskService {


    IRepository repository;

    public SubtaskService(ApplicationContext context, @Value("${projectPeak.repository.impl}") String impl) {
        repository = (IRepository) context.getBean(impl);
    }

    public void createTask(Subtask subtask, int taskId) {
        repository.createSubtask(subtask, taskId);
    }

    public Subtask getSubtaskById(int subtaskId) {
        return repository.getSubtaskById(subtaskId);
    }

    public void editSubtask(Subtask subtask) {
        repository.editSubtask(subtask);
    }

    public int getProjectIdBtSubtaskId(int subtaskId) {
        return repository.getProjectIdBySubtaskId(subtaskId);
    }

    public void deleteSubtask(int subtaskId) throws LoginException {
        repository.deleteSubtask(subtaskId);
    }


    public TaskAndSubtaskDTO getTaskAndSubTaskById(int taskId) {
        return repository.getTaskAndSubTask(taskId);
    }
    public User getUserFromId(int id){
        return repository.getUserFromId(id);
    }

    public int getTaskIdBySubtaskId(int subtaskId) throws LoginException {
        return repository.getTaskIdBySubtaskId(subtaskId);
    }
}
