package com.example.projectpeak1.repositories;

import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.utility.DbManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("UserRepository_DB")
public class UserRepository_DB implements IUserRepository {

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
                user1 = new User(userId, fullName, email, userPassword);
            }

            return user1;

        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "UPDATE User SET fullname = ?, email = ?, user_password = ? WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void deleteUser(int userId) {
        try (Connection con = DbManager.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM User WHERE user_id = ?")) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
