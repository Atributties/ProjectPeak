package com.example.projectpeak1;

import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.DbRepository;
import com.example.projectpeak1.utility.LoginException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LoginApplicationTests {


    @Autowired
    private DbRepository DbRepository;

    @Test
    public void testLogin() throws LoginException, javax.security.auth.login.LoginException {
        // Arrange
        String email = "test@gmail.com";
        String password = "testPassword";
        User expectedUser = new User(email, password);

        // Act
        User actualUser = DbRepository.login(email, password);

        // Assert
        assertNotNull(actualUser);
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }
}

