package com.shopqa.dao;

import com.shopqa.model.Category;
import com.shopqa.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public List<Category> getAll() {
        String sql = "SELECT * FROM categories ORDER BY name";
        List<Category> categories = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(mapCategory(rs));
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load categories", e);
        }
    }

    public Category getById(int id) {
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapCategory(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load category", e);
        }
    }

    public boolean create(Category c) {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getDescription());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create category", e);
        }
    }

    private Category mapCategory(ResultSet rs) throws SQLException {
        return new Category(
            rs.getInt("category_id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getTimestamp("created_at")
        );
    }
}

