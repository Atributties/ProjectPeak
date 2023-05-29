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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
        HashMap<Integer, List<Integer>> projectMembers = testDataStub.getProjectMembers();

        if (projectMembers.containsKey(userId)) {
            List<Integer> projectIds = projectMembers.get(userId);
            return projectIds.contains(projectId);
        }

        return false;
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
    public void doneAllSubtask(int projectId) {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        List<Task> tasks = testDataStub.getTasks();

        Iterator<Subtask> subtaskIterator = subtasks.iterator();
        while (subtaskIterator.hasNext()) {
            Subtask subtask = subtaskIterator.next();
            for (Task task : tasks) {
                if (task.getProjectId() == projectId && subtask.getTaskId() == task.getTaskId()) {
                    DoneSubtaskDTO doneSubtask = new DoneSubtaskDTO();
                    doneSubtask.setSubTaskId(subtask.getSubTaskId());
                    doneSubtask.setSubTaskName(subtask.getSubTaskName());
                    doneSubtask.setSubTaskDescription(subtask.getSubTaskDescription());
                    doneSubtask.setSubTaskStartDate(subtask.getSubTaskStartDate());
                    doneSubtask.setSubTaskEndDate(subtask.getSubTaskEndDate());
                    doneSubtask.setSubtaskCompletedDate(LocalDate.now());
                    doneSubtask.setStatus(subtask.getStatus());
                    doneSubtask.setTaskId(subtask.getTaskId());

                    testDataStub.addDoneSubtask(doneSubtask);
                    subtaskIterator.remove();
                    break;
                }
            }
        }
    }


    @Override
    public void doneTask(int taskId) {
        List<Task> tasks = testDataStub.getTasks();
        Task taskToRemove = null;

        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                taskToRemove = task;
                break;
            }
        }

        if (taskToRemove != null) {
            DoneTaskDTO doneTask = new DoneTaskDTO();
            doneTask.setTaskId(taskToRemove.getTaskId());
            doneTask.setTaskName(taskToRemove.getTaskName());
            doneTask.setTaskDescription(taskToRemove.getTaskDescription());
            doneTask.setTaskStartDate(taskToRemove.getTaskStartDate());
            doneTask.setTaskEndDate(taskToRemove.getTaskEndDate());
            doneTask.setTaskCompletedDate(LocalDate.now()); // Set current date
            doneTask.setStatus(taskToRemove.getStatus());
            doneTask.setProjectId(taskToRemove.getProjectId());

            testDataStub.addDoneTask(doneTask);
            testDataStub.getTasks().remove(taskToRemove);
        }
    }



    @Override
    public List<DoneTaskDTO> getAllDoneTask(int id) {
        List<DoneTaskDTO> tasks = testDataStub.getDoneTask();
        List<DoneTaskDTO> doneTaskDTOS = new ArrayList<>();

        for (DoneTaskDTO task : tasks) {
            if (task.getProjectId() == id) {
                doneTaskDTOS.add(task);
            }

        }
        return doneTaskDTOS;
    }

}
