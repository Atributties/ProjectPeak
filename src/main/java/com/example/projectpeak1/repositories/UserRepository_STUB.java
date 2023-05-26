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
        for (User user1 : users) {
            if(user1.getUserId() == user.getUserId()) {
                testDataStub.getUsers().remove(user1);
                testDataStub.getUsers().add(user);
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
