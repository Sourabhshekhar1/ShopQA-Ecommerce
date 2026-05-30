package com.shopqa.dao;

import com.shopqa.model.Order;
import com.shopqa.model.OrderItem;
import com.shopqa.util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public int createOrderFromCart(int userId, int cartId, String shippingName, String shippingAddress, String city, String zip) {
        String orderSql = "INSERT INTO orders (user_id, total_amount, shipping_name, shipping_address, shipping_city, shipping_zip) VALUES (?, ?, ?, ?, ?, ?)";
        String itemsSql = "SELECT ci.product_id, ci.quantity, p.price FROM cart_items ci JOIN products p ON ci.product_id = p.product_id WHERE ci.cart_id = ?";
        String orderItemSql = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        String stockSql = "UPDATE products SET stock_qty = stock_qty - ? WHERE product_id = ?";
        BigDecimal total = new CartDAO().getCartTotal(cartId);

        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int orderId;
                try (PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                    orderStmt.setInt(1, userId);
                    orderStmt.setBigDecimal(2, total);
                    orderStmt.setString(3, shippingName);
                    orderStmt.setString(4, shippingAddress);
                    orderStmt.setString(5, city);
                    orderStmt.setString(6, zip);
                    orderStmt.executeUpdate();
                    try (ResultSet keys = orderStmt.getGeneratedKeys()) {
                        orderId = keys.next() ? keys.getInt(1) : 0;
                    }
                }

                try (PreparedStatement itemsStmt = conn.prepareStatement(itemsSql);
                     PreparedStatement orderItemStmt = conn.prepareStatement(orderItemSql);
                     PreparedStatement stockStmt = conn.prepareStatement(stockSql)) {
                    itemsStmt.setInt(1, cartId);
                    try (ResultSet rs = itemsStmt.executeQuery()) {
                        while (rs.next()) {
                            int productId = rs.getInt("product_id");
                            int quantity = rs.getInt("quantity");

                            orderItemStmt.setInt(1, orderId);
                            orderItemStmt.setInt(2, productId);
                            orderItemStmt.setInt(3, quantity);
                            orderItemStmt.setBigDecimal(4, rs.getBigDecimal("price"));
                            orderItemStmt.addBatch();

                            stockStmt.setInt(1, quantity);
                            stockStmt.setInt(2, productId);
                            stockStmt.addBatch();
                        }
                        orderItemStmt.executeBatch();
                        stockStmt.executeBatch();
                    }
                }

                conn.commit();
                return orderId;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create order", e);
        }
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapOrder(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load order", e);
        }
    }

    public List<Order> getOrdersByUser(int userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load user orders", e);
        }
    }

    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(mapOrder(rs));
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load orders", e);
        }
    }

    public boolean updateStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update order status", e);
        }
    }

    public List<OrderItem> getOrderItems(int orderId) {
        String sql = "SELECT oi.*, p.name FROM order_items oi "
            + "JOIN products p ON oi.product_id = p.product_id "
            + "WHERE oi.order_id = ?";
        List<OrderItem> items = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapOrderItem(rs));
                }
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load order items", e);
        }
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        int userId = rs.getInt("user_id");
        Integer nullableUserId = rs.wasNull() ? null : userId;
        return new Order(
            rs.getInt("order_id"),
            nullableUserId,
            rs.getBigDecimal("total_amount"),
            rs.getString("status"),
            rs.getString("shipping_name"),
            rs.getString("shipping_address"),
            rs.getString("shipping_city"),
            rs.getString("shipping_zip"),
            rs.getTimestamp("created_at")
        );
    }

    private OrderItem mapOrderItem(ResultSet rs) throws SQLException {
        return new OrderItem(
            rs.getInt("order_item_id"),
            rs.getInt("order_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"),
            rs.getBigDecimal("unit_price")
        );
    }
}

