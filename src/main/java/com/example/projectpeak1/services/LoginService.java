package com.example.projectpeak1.services;


import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.ILoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class LoginService {

    ILoginRepository loginRepository;

    public LoginService(ILoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }


    public User login(String email, String password) throws LoginException {
        return loginRepository.login(email, password);
    }

    public void createUser(User user) throws LoginException {
        loginRepository.createUser(user);
    }



}
