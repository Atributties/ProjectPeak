package com.example.projectpeak1.repositories;

import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserRepository_STUB")
public class UserRepository_STUB implements IUserRepository{

    TestData_STUB testDataStub;
    @Autowired
    public UserRepository_STUB(TestData_STUB testDataStub) {
        this.testDataStub = testDataStub;
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
    public void updateUser(User user) {
        List<User> users = testDataStub.getUsers();
        for (int i = 0; i < users.size(); i++) {
            User existingUser = users.get(i);
            if (existingUser.getUserId() == user.getUserId()) {
                users.set(i, user);
                break;
            }
        }
    }


    @Override
    public void deleteUser(int userId) {
        List<User> users = testDataStub.getUsers();
        for (User user : users) {
            if(user.getUserId() == userId) {
                testDataStub.getUsers().remove(user);
            }
        }
    }
}
