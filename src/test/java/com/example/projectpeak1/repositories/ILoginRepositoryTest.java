package com.example.projectpeak1.repositories;

import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.utility.LoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ILoginRepositoryTest {

    @InjectMocks
    private LoginRepository_DB loginRepository;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void testLoginSuccess() throws LoginException, SQLException, javax.security.auth.login.LoginException {
        // Arrange
        String email = "john.doe@example.com";
        String password = "password1";
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("user_id")).thenReturn(1);

        // Act
        User user = loginRepository.login(email, password);

        // Assert
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(1, user.getUserId());
    }

    @Test
    void testLoginFailure() {
        LoginRepository_DB loginRepository = new LoginRepository_DB();
        String email = "test@example.com";
        String password = "incorrectPassword";

        try {
            loginRepository.login(email, password);
        } catch (javax.security.auth.login.LoginException e) {

            assertEquals("Wrong email or password", e.getMessage());
            return;
        }

        throw new AssertionError("Expected LoginException, but no exception was thrown.");
    }
}
