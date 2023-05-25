package com.example.projectpeak1.repositories;

import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;

@Repository("LoginRepository_STUB")
public class Loginrepository_STUB implements ILoginRepository{
    @Override
    public User login(String email, String password) throws LoginException {
        return null;
    }

    @Override
    public void createUser(User user) throws LoginException {

    }
}
