package com.shopqa.dao;

import com.shopqa.model.Cart;
import com.shopqa.model.CartItem;
import com.shopqa.util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public Cart getCartByUserId(int userId) {
        Cart cart = findCart("SELECT * FROM cart WHERE user_id = ?", userId);
        return cart != null ? cart : createCart(userId, null);
    }

    public Cart getCartBySessionId(String sessionId) {
        Cart cart = findCartBySession(sessionId);
        return cart != null ? cart : createCart(null, sessionId);
    }

    public List<CartItem> getCartItems(int cartId) {
        String sql = "SELECT ci.*, p.name, p.price, p.image_url "
            + "FROM cart_items ci "
            + "JOIN products p ON ci.product_id = p.product_id "
            + "WHERE ci.cart_id = ?";
        List<CartItem> items = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapCartItem(rs));
                }
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load cart items", e);
        }
    }

    public boolean addItem(int cartId, int productId, int qty) {
        String selectSql = "SELECT * FROM cart_items WHERE cart_id = ? AND product_id = ?";
        String updateSql = "UPDATE cart_items SET quantity = quantity + ? WHERE cart_item_id = ?";
        String insertSql = "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(selectSql)) {
            select.setInt(1, cartId);
            select.setInt(2, productId);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement update = conn.prepareStatement(updateSql)) {
                        update.setInt(1, qty);
                        update.setInt(2, rs.getInt("cart_item_id"));
                        return update.executeUpdate() > 0;
                    }
                }
            }
            try (PreparedStatement insert = conn.prepareStatement(insertSql)) {
                insert.setInt(1, cartId);
                insert.setInt(2, productId);
                insert.setInt(3, qty);
                return insert.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to add cart item", e);
        }
    }

    public boolean updateItemQty(int cartItemId, int qty) {
        if (qty <= 0) {
            return removeItem(cartItemId);
        }
        String sql = "UPDATE cart_items SET quantity = ? WHERE cart_item_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, qty);
            stmt.setInt(2, cartItemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update cart item", e);
        }
    }

    public boolean removeItem(int cartItemId) {
        String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartItemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to remove cart item", e);
        }
    }

    public boolean clearCart(int cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to clear cart", e);
        }
    }

    public BigDecimal getCartTotal(int cartId) {
        String sql = "SELECT SUM(ci.quantity * p.price) AS total FROM cart_items ci "
            + "JOIN products p ON ci.product_id = p.product_id "
            + "WHERE ci.cart_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal("total");
                    return total == null ? BigDecimal.ZERO : total;
                }
                return BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to calculate cart total", e);
        }
    }

    private Cart findCart(String sql, int userId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapCart(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find cart", e);
        }
    }

    private Cart findCartBySession(String sessionId) {
        String sql = "SELECT * FROM cart WHERE session_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sessionId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapCart(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find session cart", e);
        }
    }

    private Cart createCart(Integer userId, String sessionId) {
        String sql = "INSERT INTO cart (user_id, session_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (userId == null) {
                stmt.setObject(1, null);
            } else {
                stmt.setInt(1, userId);
            }
            stmt.setString(2, sessionId);
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                int id = keys.next() ? keys.getInt(1) : 0;
                return new Cart(id, userId, sessionId, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create cart", e);
        }
    }

    private Cart mapCart(ResultSet rs) throws SQLException {
        int userId = rs.getInt("user_id");
        Integer nullableUserId = rs.wasNull() ? null : userId;
        return new Cart(
            rs.getInt("cart_id"),
            nullableUserId,
            rs.getString("session_id"),
            rs.getTimestamp("created_at")
        );
    }

    private CartItem mapCartItem(ResultSet rs) throws SQLException {
        return new CartItem(
            rs.getInt("cart_item_id"),
            rs.getInt("cart_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"),
            rs.getTimestamp("added_at")
        );
    }
}

