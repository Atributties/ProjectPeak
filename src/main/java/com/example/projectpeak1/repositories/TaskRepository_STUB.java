package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneSubtaskDTO;
import com.example.projectpeak1.dto.DoneTaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Repository("TaskRepository_STUB")
public class TaskRepository_STUB implements ITaskRepository {
    TestData_STUB testDataStub;

    @Autowired
    public TaskRepository_STUB(TestData_STUB testDataStub) {
        this.testDataStub = testDataStub;
    }
    @Override
    public boolean isUserAuthorized(int userId, int projectId) {
        HashMap<Integer, Integer> projectMembers = testDataStub.getProjectMembers();

        return projectMembers.containsKey(userId) && projectMembers.get(userId) == projectId;
    }
    @Override
    public User getUserFromId(int id) {
        List<User> users = testDataStub.getUsers();

        for (User user : users) {
            if(user.getUserId() == id){
                return user;
            }
        }
        return null;
    }


    @Override
    public void createTask(Task task, int projectId) {
        task.setProjectId(projectId);
        testDataStub.addTask(task);
    }

    @Override
    public Task getTaskById(int id) {
        List<Task> tasks = testDataStub.getTasks();
        for (Task task : tasks) {
            if(task.getTaskId() == id){
                return task;
            }
        }
        return null;
    }

    @Override
    public void deleteTask(int taskId) throws LoginException {
        List<Task> tasks = testDataStub.getTasks();
        for (Task task : tasks) {
            if(task.getTaskId() == taskId) {
                testDataStub.getTasks().remove(task);
            }
        }
    }

    @Override
    public Project getProjectById(int id) {
        List<Project> projects = testDataStub.getProjects();
        for (Project project : projects) {
            if(project.getProjectId() == id){
                return project;
            }
        }
        return null;
    }

    @Override
    public void editTask(Task task) {
        List<Task> tasks = testDataStub.getTasks();
        for (Task task1 : tasks) {
            if(task1.getTaskId() == task.getTaskId()) {
                testDataStub.getTasks().remove(task1);
                testDataStub.getTasks().add(task);
            }
        }
    }

    @Override
    public int getProjectIdByTaskId(int taskId) {
        List<Task> tasks = testDataStub.getTasks();
        for (Task task : tasks) {
            if(task.getTaskId() == taskId){
                return task.getProjectId();
            }
        }
        return 0;
    }

    @Override
    public List<Subtask> getSubtasksByTaskId(int taskId) {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        List<Subtask> allSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            if(subtask.getTaskId() == taskId){
                allSubtasks.add(subtask);
            }
        }
        return allSubtasks;
    }

    @Override
    public void updateSubtaskDates(Subtask subtask) {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        for (Subtask subtask1 : subtasks) {
            if(subtask1.getSubTaskId() == subtask.getSubTaskId()){
                subtask1.setSubTaskStartDate(subtask.getSubTaskStartDate());
                subtask1.setSubTaskEndDate(subtask.getSubTaskEndDate());
                testDataStub.getSubtasks().remove(subtask);
                testDataStub.addSubtask(subtask1);
            }
        }
    }

    @Override
    public void doneAllSubtask(int id) {
        List<Subtask> subtasks = testDataStub.getSubtasks();

        for (Subtask subtask : subtasks) {
            if(subtask.getTaskId() == id) {
                testDataStub.addDoneSubtask(subtask);
                testDataStub.getSubtasks().remove(subtask);
            }
        }
    }

    @Override
    public void doneTask(int taskId) {
        List<Task> tasks = testDataStub.getTasks();

        for (Task task : tasks) {
            if(task.getTaskId() == taskId) {
                testDataStub.addDoneTask(task);
                testDataStub.getTasks().remove(task);
            }
        }
    }

    @Override
    public List<DoneTaskDTO> getAllDoneTask(int id) {
        List<Task> tasks = testDataStub.getTasks();
        List<DoneTaskDTO> doneTaskDTOS = new ArrayList<>();

        for (Task task : tasks) {
            if(task.getTaskId() == id) {
                DoneTaskDTO doneTaskDTO = new DoneTaskDTO(task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getTaskStartDate(), task.getTaskEndDate(), task.getStatus(), task.getProjectId());
                doneTaskDTOS.add(doneTaskDTO);
            }

        }
        return doneTaskDTOS;
    }
}
