
package com.example.dao;

import com.example.model.User;
import com.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO {

    // Find user by username
    public static User findByUsername(String username) {
        String sql = "SELECT * FROM app_users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error in findByUsername: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Find user by username and password
    public static User findByCredentials(String username, String password) {
        String sql = "SELECT * FROM app_users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error in findByCredentials: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Create new user and return user id (or -1 if failed)
    public static int createUser(String username, String password) {
        String sql = "INSERT INTO app_users (username, password) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, password);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // return new user ID
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in createUser: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
}
