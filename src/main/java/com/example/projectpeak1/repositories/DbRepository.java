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
    @Override
    public User getUserFromId(int id) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM USER WHERE USER_ID = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            User user1 = null;
            if (rs.next()) {
                int userId = rs.getInt("USER_ID");
                String fullName = rs.getString("FULLNAME");
                String email = rs.getString("EMAIL");
                String userPassword = rs.getString("USER_PASSWORD");
                String userRole = rs.getString("USER_PASSWORD");
                user1 = new User(userId, email, fullName, userPassword, userRole);

            }

            return user1;

        } catch (SQLException ex) {
            return null;
        }
    }
    @Override
    public void editUser(User user) throws LoginException {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "UPDATE USER SET USER_ID = ?, FULLNAME = ?, EMAIL = ?, USER_PASSWORD = ? WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getUserId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }
    }



    @Override
    public void deleteUser(int userId) throws LoginException {
        try (Connection con = DbManager.getConnection()) {
            // delete all wishes associated with the user
            String SQL = "DELETE FROM WISH WHERE WISHLIST_ID IN " +
                    "(SELECT WISHLIST_ID FROM WISHLIST WHERE USER_ID = ?)";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // delete all wishlists associated with the user
            SQL = "DELETE FROM WISHLIST WHERE USER_ID = ?";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // delete the user record
            SQL = "DELETE FROM USER WHERE USER_ID = ?";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }
    }



}
