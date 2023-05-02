package com.example.projectpeak1.repositories;

import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.utility.DbManager;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository("dbRepository")
public class DbRepository implements IRepository {

    @Override
    public User login(String email, String password) throws LoginException {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(email, password);
                return user;
            } else {
                throw new LoginException("Wrong email or password");
            }
        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }

    }

    @Override
    public User createUser(User user) throws LoginException {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "INSERT INTO user (email, user_password, fullname) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            User user1 = new User(user.getEmail(), user.getPassword());
            user.setUserId(id);
            return user1;
        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }
    }



}
