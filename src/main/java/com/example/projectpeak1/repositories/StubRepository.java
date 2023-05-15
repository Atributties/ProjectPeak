package com.example.projectpeak1.repositories;


import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class StubRepository implements IRepository {

    //_____________LOGIN CONTROLLER________________

    //_____________PROJECT CONTROLLER______________

    //_____________SUBTASK CONTROLLER______________

    //_____________TASK CONTROLLER_________________





    //Test data USERS
    private User user1 = new User(1, "John Doe", "johndoe@example.com", "password", "admin");
    private User user2 = new User(2, "Jane Doe", "janedoe@example.com", "password", "user");
    private User user3 = new User(3, "Bob Smith", "bobsmith@example.com", "password", "user");


    private List<User> users = new ArrayList<>(Arrays.asList(user1,user2,user3));


    @Override
    public User login(String email, String password) {
        return null;
    }
    @Override
    public User createUser(User user){
        User newUser = new User(user.getEmail(), user.getFullName(), user.getPassword());
        users.add(newUser);
        return newUser;
    }

    @Override
    public void editUser(User user){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == user.getUserId()) {
                users.set(i, user);
                return;
            }
        }

    }

    @Override
    public User getUserFromId(int id) {
        for (User user : users) {
            if(user.getUserId() == id){
                return user;
            }
        }
        return null;
    }

    @Override
    public void deleteUser(int userId) {
        // Find the user with the specified ID
        for (User user : users) {
            if(user.getUserId() == userId){
                users.remove(user);
            }
        }
    }

    @Override
    public void createProject(Project list, int projectId) {
    }

    @Override
    public List<Project> getAllProjectById(int userId) {
        return null;
    }

    @Override
    public void deleteProject(int id) throws LoginException {
    }

    @Override
    public Project getProjectById(int id) {
        return null;
    }

    @Override
    public void updateProject(Project project) {

    }
    @Override
    public void createTask(Task task, int projectId){
    }

    @Override
    public List<Task> getAllTaskById(int id) {
        return null;
    }

    @Override
    public Task getTaskById(int id) {
        return null;
    }


    @Override
    public List<TaskAndSubtaskDTO> getTaskAndSubTaskList(int projectId) {
        return null;
    }

    @Override
    public void deleteTask(int taskId) throws LoginException {

    }

    @Override
    public User getUserFromTaskId(int taskId) {
        return null;
    }

    @Override
    public void createSubtask(Subtask subtask, int taskId) {

    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
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
    public TaskAndSubtaskDTO getTaskAndSubTask(int id) {
        return null;
    }

    @Override
    public int getProjectIdByTaskId(int taskId) {
        return 0;
    }

    @Override
    public void editTask(Task task) {

    }

    @Override
    public Project getProjectByTaskId(int taskId) {
        return null;
    }

    @Override
    public int getTaskIdBySubtaskId(int subtaskId) {
        return 0;
    }

    @Override
    public void updateUser(User user) {

    }
}
