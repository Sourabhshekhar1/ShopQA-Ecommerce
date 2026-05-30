package com.shopqa.servlet;

import com.shopqa.dao.CartDAO;
import com.shopqa.dao.OrderDAO;
import com.shopqa.dao.ProductDAO;
import com.shopqa.model.Cart;
import com.shopqa.model.Order;
import com.shopqa.model.OrderItem;
import com.shopqa.model.Product;
import com.shopqa.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServlet extends HttpServlet {
    private final CartDAO cartDAO = new CartDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (!SessionUtil.isLoggedIn(session)) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }
        int userId = SessionUtil.getCurrentUserId(session);
        Cart cart = cartDAO.getCartByUserId(userId);
        int orderId = orderDAO.createOrderFromCart(
            userId,
            cart.getCartId(),
            req.getParameter("shippingName"),
            req.getParameter("shippingAddress"),
            req.getParameter("shippingCity"),
            req.getParameter("shippingZip")
        );
        cartDAO.clearCart(cart.getCartId());
        resp.sendRedirect(req.getContextPath() + "/orderConfirmation?orderId=" + orderId);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (!SessionUtil.isLoggedIn(session)) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        Order order = orderDAO.getOrderById(orderId);
        List<OrderItem> items = orderDAO.getOrderItems(orderId);
        req.setAttribute("order", order);
        req.setAttribute("orderItems", items);
        req.setAttribute("orderProducts", productMap(items));
        req.getRequestDispatcher("/WEB-INF/views/orderConfirmation.jsp").forward(req, resp);
    }

    private Map<Integer, Product> productMap(List<OrderItem> items) {
        Map<Integer, Product> products = new HashMap<>();
        for (OrderItem item : items) {
            products.put(item.getProductId(), productDAO.getById(item.getProductId()));
        }
        return products;
    }
}

