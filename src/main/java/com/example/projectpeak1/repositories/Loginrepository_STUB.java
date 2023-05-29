package com.example.projectpeak1.repositories;

import com.example.projectpeak1.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("LoginRepository_STUB")
public class Loginrepository_STUB implements ILoginRepository{


    TestData_STUB testDataStub;

    @Autowired
    public Loginrepository_STUB(TestData_STUB testDataStub) {
        this.testDataStub = testDataStub;
    }

    @Override
    public User login(String email, String password) throws LoginException {
        List<User> users = testDataStub.getUsers();
        for(User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;

            }
        }
        throw new LoginException("Wrong email or password");
    }

    @Override
    public void createUser(User user) throws LoginException {
        if(doesUserExist(user.getEmail())){
            throw new LoginException();
        } else{
            testDataStub.addUser(user);
        }


    }

    public boolean doesUserExist(String email){
        for(User user : testDataStub.getUsers()){
            return user.getEmail().equals(email);
        }
        return false;
    }
}

