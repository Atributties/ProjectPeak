package com.example.projectpeak1.repositories;

import com.example.projectpeak1.entities.User;
import org.springframework.stereotype.Repository;

@Repository("UserRepository_STUB")
public class UserRepository_STUB implements IUserRepository{
    @Override
    public User getUserFromId(int id) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(int userId) {

    }
}
