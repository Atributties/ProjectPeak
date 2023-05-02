package com.example.projectpeak1.repositories;


import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;

@Repository
public class StubRepository implements IRepository {
    @Override
    public User login(String email, String password) {
        return null;
    }
    @Override
    public User createUser(User user) {
        return null;
    }

}
