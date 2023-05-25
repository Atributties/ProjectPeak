package com.example.projectpeak1.repositories;

import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;

@Repository
public interface ILoginRepository {
    User login(String email, String password) throws LoginException;

    void createUser(User user) throws LoginException;
}
