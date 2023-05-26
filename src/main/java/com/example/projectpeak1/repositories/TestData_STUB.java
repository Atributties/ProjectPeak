package com.example.projectpeak1.repositories;

import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class TestData_STUB {



    List<Project> doneProjects = new ArrayList<>();
    List<Task> doneTask = new ArrayList<>();
    List<Subtask> doneSubtask = new ArrayList<>();


    // Create users
    User user1 = new User(1, "John Doe", "john.doe@example.com", "password1");
    User user2 = new User(2, "Jane Doe", "jane.doe@example.com", "password2");
    List<User> users = new ArrayList<>(Arrays.asList(user1,user2));



    // Create projects
    LocalDate startDate1 = LocalDate.of(2023, 5, 22);
    LocalDate endDate1 = LocalDate.of(2023, 6, 22);
    Project project1 = new Project(1, "Project 1", "This is project 1", startDate1, endDate1, user1.getUserId());
    LocalDate startDate2 = LocalDate.of(2023, 5, 29);
    LocalDate endDate2 = LocalDate.of(2023, 7, 23);
    Project project2 = new Project(2, "Project 2", "This is project 2", startDate2, endDate2, user2.getUserId());
    List<Project> projects = new ArrayList<>(Arrays.asList(project1,project2));


    HashMap<Integer, Integer> projectMembers = new HashMap<>(Map.of(
            user1.getUserId(), project1.getProjectId(),
            user2.getUserId(), project2.getProjectId()));




    // Create tasks
    LocalDate startDate3 = LocalDate.of(2023, 4, 22);
    LocalDate endDate3 = LocalDate.of(2023, 4, 26);
    Task task1 = new Task(1, "Task", "This is task 1.1 for project 1", startDate3, endDate3, "Completed", project1.getProjectId());
    LocalDate startDate4 = LocalDate.of(2023, 4, 23);
    LocalDate endDate4 = LocalDate.of(2023, 4, 24);
    Task task2 = new Task(2, "Task", "This is task 1.2 for project 1", startDate4, endDate4, "Completed", project1.getProjectId());
    LocalDate startDate5 = LocalDate.of(2023, 4, 29);
    LocalDate endDate5 = LocalDate.of(2023, 5, 15);
    Task task3 = new Task(3, "Task", "This is task 2.1 for project 2", startDate5, endDate5, "In progress", project2.getProjectId());
    List<Task> tasks = new ArrayList<>(Arrays.asList(task1,task2, task3));
    // Create subtasks
    LocalDate startDate6 = LocalDate.of(2023, 4, 22);
    LocalDate endDate6 = LocalDate.of(2023, 4, 23);
    Subtask subtask1 = new Subtask(1, "Subtask 1.1.1", "This is subtask 1 for task 1.1", startDate6, endDate6, "Completed", task1.getTaskId());
    LocalDate startDate7 = LocalDate.of(2023, 4, 23);
    LocalDate endDate7 = LocalDate.of(2023, 4, 26);
    Subtask subtask2 = new Subtask(2, "Subtask 1.1.2", "This is subtask 2 for task 1.1", startDate7, endDate7, "Completed", task1.getTaskId());
    LocalDate startDate8 = LocalDate.of(2023, 1, 1);
    LocalDate endDate8 = LocalDate.of(2023, 1, 15);
    Subtask subtask3 = new Subtask(3, "Subtask 2.1.1", "This is subtask 1 for task 2.1", startDate8, endDate8, "In progress", task3.getTaskId());
    List<Subtask> subtasks = new ArrayList<>(Arrays.asList(subtask1,subtask2, subtask3));





    // Getters for the lists
    public List<User> getUsers() {
        return users;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    // Add methods for adding new objects to the lists
    public void addUser(User user) {
        users.add(user);
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }



    public void addDoneProject(Project project) {
        doneProjects.add(project);
    }
    public void addDoneTask(Task task) {
        doneTask.add(task);
    }
    public void addDoneSubtask(Subtask subtask) {
        doneSubtask.add(subtask);
    }

    public List<Project> getDoneProjects() {
        return doneProjects;
    }

    public List<Task> getDoneTask() {
        return doneTask;
    }

    public List<Subtask> getDoneSubtask() {
        return doneSubtask;
    }

    public HashMap<Integer, Integer> getProjectMembers() {
        return projectMembers;
    }






}











