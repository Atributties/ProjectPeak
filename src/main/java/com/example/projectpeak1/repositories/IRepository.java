package com.example.projectpeak1.repositories;


import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;

@Repository
public interface IRepository {
    User login(String email, String password) throws LoginException;

    User createUser(User user) throws LoginException;


    void editUser(User user) throws LoginException;

    User getUserFromId(int id);

    void deleteUser(int userId) throws LoginException;
}
