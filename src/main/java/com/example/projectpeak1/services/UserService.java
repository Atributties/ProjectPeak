package com.example.projectpeak1.services;


import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.IRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class UserService {

    IRepository repository;

    public UserService(ApplicationContext context, @Value("${projectPeak.repository.impl}") String impl) {
        repository = (IRepository) context.getBean(impl);
    }
    public User getUserFromId(int userId) {
        return repository.getUserFromId(userId);
    }

    public void updateUser(User user) {
        repository.updateUser(user);
    }

    public void deleteUser(int userIdPath) throws LoginException {
        repository.deleteUser(userIdPath);
    }
}
