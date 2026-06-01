package com.shopqa.dao;

import com.shopqa.model.User;
import com.shopqa.util.DBUtil;
import com.shopqa.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapUser(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find user by email", e);
        }
    }

    public User findById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapUser(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find user by id", e);
        }
    }

    public boolean createUser(User user) {
        String sql = "INSERT INTO users (full_name, email, password_hash, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, PasswordUtil.hashPassword(user.getPasswordHash()));
            stmt.setString(4, user.getRole() == null ? "customer" : user.getRole());
            boolean created = stmt.executeUpdate() > 0;
            if (created) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
            }
            return created;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create user", e);
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET full_name = ?, email = ? WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update user", e);
        }
    }

    public boolean updateProfilePhoto(int userId, String photoPath) {
        String sql = "UPDATE users SET profile_photo = ? WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, photoPath);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update profile photo", e);
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User(
            rs.getInt("user_id"),
            rs.getString("full_name"),
            rs.getString("email"),
            rs.getString("password_hash"),
            rs.getString("role"),
            rs.getTimestamp("created_at")
        );
        user.setProfilePhoto(rs.getString("profile_photo"));
        return user;
    }
}

