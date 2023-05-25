package com.example.projectpeak1.repositories;

import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;

@Repository
public interface IUserRepository {
    User getUserFromId(int id);
    void updateUser(User user);

    void deleteUser(int userId);
}
