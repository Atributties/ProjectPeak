package com.example.projectpeak1.services;


import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class UserService {

    IUserRepository userRepository;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User getUserFromId(int userId) {
        return userRepository.getUserFromId(userId);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void deleteUser(int userIdPath) throws LoginException {
        userRepository.deleteUser(userIdPath);
    }


}
