package com.shopqa.dao;

import com.shopqa.model.Product;
import com.shopqa.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> getAllActive() {
        String sql = "SELECT * FROM products WHERE is_active = 1 ORDER BY created_at DESC";
        return list(sql);
    }

    public List<Product> getAll() {
        String sql = "SELECT * FROM products ORDER BY created_at DESC";
        return list(sql);
    }

    public Product getById(int id) {
        String sql = "SELECT * FROM products WHERE product_id = ? AND is_active = 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapProduct(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load product", e);
        }
    }

    public List<Product> getByCategory(int categoryId) {
        String sql = "SELECT * FROM products WHERE category_id = ? AND is_active = 1";
        List<Product> products = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load products by category", e);
        }
    }

    public List<Product> search(String keyword) {
        String sql = "SELECT * FROM products WHERE is_active = 1 AND (name LIKE ? OR description LIKE ?)";
        List<Product> products = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            stmt.setString(1, like);
            stmt.setString(2, like);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to search products", e);
        }
    }

    public boolean createProduct(Product p) {
        String sql = "INSERT INTO products (category_id, name, description, price, stock_qty, image_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            bindProduct(stmt, p);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create product", e);
        }
    }

    public boolean updateProduct(Product p) {
        String sql = "UPDATE products SET category_id = ?, name = ?, description = ?, price = ?, stock_qty = ?, image_url = ? WHERE product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            bindProduct(stmt, p);
            stmt.setInt(7, p.getProductId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update product", e);
        }
    }

    public boolean deleteProduct(int id) {
        String sql = "UPDATE products SET is_active = 0 WHERE product_id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete product", e);
        }
    }

    private List<Product> list(String sql) {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load products", e);
        }
    }

    private void bindProduct(PreparedStatement stmt, Product p) throws SQLException {
        if (p.getCategoryId() == null) {
            stmt.setNull(1, Types.INTEGER);
        } else {
            stmt.setInt(1, p.getCategoryId());
        }
        stmt.setString(2, p.getName());
        stmt.setString(3, p.getDescription());
        stmt.setBigDecimal(4, p.getPrice());
        stmt.setInt(5, p.getStockQty());
        stmt.setString(6, p.getImageUrl());
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        int categoryId = rs.getInt("category_id");
        Integer nullableCategoryId = rs.wasNull() ? null : categoryId;
        return new Product(
            rs.getInt("product_id"),
            nullableCategoryId,
            rs.getString("name"),
            rs.getString("description"),
            rs.getBigDecimal("price"),
            rs.getInt("stock_qty"),
            rs.getString("image_url"),
            rs.getBoolean("is_active"),
            rs.getTimestamp("created_at")
        );
    }
}

