package com.example.projectpeak1.services;


import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.IRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class LoginService {

    IRepository repository;

    public LoginService(ApplicationContext context, @Value("${projectPeak.repository.impl}") String impl) {
        repository = (IRepository) context.getBean(impl);
    }


    public User login(String email, String password) throws LoginException {
        return repository.login(email, password);
    }

    public User createUser(User user) throws LoginException {
        return repository.createUser(user);
    }



}
