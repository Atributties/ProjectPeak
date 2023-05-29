package com.example.projectpeak1.services;


import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.ILoginRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class LoginService {

    ILoginRepository loginRepository;

    public LoginService(ApplicationContext context, @Value("${loginrepository.impl}") String impl) {
        this.loginRepository = (ILoginRepository) context.getBean(impl);
    }


    public User login(String email, String password) throws LoginException {
        return loginRepository.login(email, password);
    }

    public void createUser(User user) throws LoginException {
        loginRepository.createUser(user);
    }

    public boolean doesUserExist(String email) throws LoginException {
        return loginRepository.doesUserExist(email);
    }

}
