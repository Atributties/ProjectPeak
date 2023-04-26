package com.example.projectpeak1.repositories;

import com.example.projectpeak1.entities.TestUser;
import com.example.projectpeak1.utility.DbManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository("dbRepository")
public class DbRepository implements IRepository {

    @Override
    public TestUser login(String email, String password) {
        try {
            Connection con = DbManager.getConnection();
            String SQL = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                TestUser user = new TestUser(email, password);
                return user;
            }
        } catch (SQLException ex) {
        }
        return null;
    }

}
