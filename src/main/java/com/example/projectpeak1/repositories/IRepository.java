package com.example.projectpeak1.repositories;


import com.example.projectpeak1.entities.TestUser;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepository {
    TestUser login(String email, String password);
}
