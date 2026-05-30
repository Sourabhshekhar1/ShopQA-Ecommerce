package com.shopqa.servlet;

import com.shopqa.dao.CategoryDAO;
import com.shopqa.dao.OrderDAO;
import com.shopqa.dao.ProductDAO;
import com.shopqa.model.Product;
import com.shopqa.util.DBUtil;
import com.shopqa.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtil.isAdmin(req.getSession(false))) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }
        String action = req.getParameter("action");
        if ("products".equals(action)) {
            req.setAttribute("products", productDAO.getAll());
            req.setAttribute("categories", categoryDAO.getAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/manageProducts.jsp").forward(req, resp);
        } else if ("orders".equals(action)) {
            req.setAttribute("orders", orderDAO.getAllOrders());
            req.getRequestDispatcher("/WEB-INF/views/admin/manageOrders.jsp").forward(req, resp);
        } else {
            req.setAttribute("totalProducts", count("SELECT COUNT(*) FROM products"));
            req.setAttribute("totalOrders", count("SELECT COUNT(*) FROM orders"));
            req.setAttribute("totalUsers", count("SELECT COUNT(*) FROM users"));
            req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!SessionUtil.isAdmin(req.getSession(false))) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }
        String action = req.getParameter("action");
        if ("addProduct".equals(action)) {
            productDAO.createProduct(productFromRequest(req, false));
            resp.sendRedirect(req.getContextPath() + "/admin?action=products");
        } else if ("editProduct".equals(action)) {
            Product p = productFromRequest(req, true);
            productDAO.updateProduct(p);
            resp.sendRedirect(req.getContextPath() + "/admin?action=products");
        } else if ("deleteProduct".equals(action)) {
            productDAO.deleteProduct(Integer.parseInt(req.getParameter("productId")));
            resp.sendRedirect(req.getContextPath() + "/admin?action=products");
        } else if ("updateStatus".equals(action)) {
            orderDAO.updateStatus(Integer.parseInt(req.getParameter("orderId")), req.getParameter("status"));
            resp.sendRedirect(req.getContextPath() + "/admin?action=orders");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin?action=dashboard");
        }
    }

    private Product productFromRequest(HttpServletRequest req, boolean includeId) {
        Product p = new Product();
        if (includeId) {
            p.setProductId(Integer.parseInt(req.getParameter("productId")));
        }
        p.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
        p.setName(req.getParameter("name"));
        p.setDescription(req.getParameter("description"));
        p.setPrice(new BigDecimal(req.getParameter("price")));
        p.setStockQty(Integer.parseInt(req.getParameter("stockQty")));
        String imageUrl = req.getParameter("imageUrl");
        p.setImageUrl(imageUrl == null || imageUrl.isBlank() ? "images/placeholder.png" : imageUrl);
        p.setActive(req.getParameter("isActive") == null || "1".equals(req.getParameter("isActive")));
        return p;
    }

    private int count(String sql) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load admin count", e);
        }
    }
}

