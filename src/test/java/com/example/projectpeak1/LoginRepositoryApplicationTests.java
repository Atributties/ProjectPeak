package com.example.projectpeak1;

import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.DbRepository;
import com.example.projectpeak1.utility.LoginException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginRepositoryApplicationTests {

    @Autowired
    private DbRepository dbRepository;

    @Test
    public void testLoginSuccess() throws LoginException, javax.security.auth.login.LoginException {
        User user = dbRepository.login("johndoe@example.com", "password123");
        assertNotNull(user);
        assertEquals(user.getEmail(), "johndoe@example.com");
    }

    @Test
    public void testLoginFailure() throws LoginException {
        try {
            dbRepository.login("johndoe@example.com", "wrongpassword");
            fail("Expected LoginException was not thrown");
        } catch (javax.security.auth.login.LoginException e) {
            // expected behavior
        }
    }

    @Test
    public void testCreateUser() throws LoginException, javax.security.auth.login.LoginException {
        User user = new User("newuser@test.com", "password", "New User");
        User createdUser = dbRepository.createUser(user);
        assertNotNull(createdUser);
        assertNotNull(createdUser.getUserId());
        assertEquals(createdUser.getEmail(), user.getEmail());
    }

    @Test
    public void testGetUserFromId() {
        User user = dbRepository.getUserFromId(1);
        assertNotNull(user);
        assertEquals(user.getEmail(), "johndoe@example.com");
    }

    @Test
    public void testEditUser() throws LoginException, javax.security.auth.login.LoginException {
        User user = dbRepository.getUserFromId(1);
        user.setFullName("New Name");
        dbRepository.editUser(user);
        User updatedUser = dbRepository.getUserFromId(1);
        assertNotNull(updatedUser);
        assertEquals(updatedUser.getFullName(), "New Name");
    }

    @Test
    public void testDeleteUser() throws LoginException, javax.security.auth.login.LoginException {
        dbRepository.deleteUser(1);
        User deletedUser = dbRepository.getUserFromId(1);
        assertNull(deletedUser);
    }
}

