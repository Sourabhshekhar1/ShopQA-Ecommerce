package com.shopqa.servlet;

import com.shopqa.dao.CartDAO;
import com.shopqa.dao.ProductDAO;
import com.shopqa.model.Cart;
import com.shopqa.model.CartItem;
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

public class CartServlet extends HttpServlet {
    private final CartDAO cartDAO = new CartDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = resolveCart(req);
        List<CartItem> items = cartDAO.getCartItems(cart.getCartId());
        req.setAttribute("cart", cart);
        req.setAttribute("cartItems", items);
        req.setAttribute("cartProducts", productMap(items));
        req.setAttribute("cartTotal", cartDAO.getCartTotal(cart.getCartId()));
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            Cart cart = resolveCart(req);
            cartDAO.addItem(cart.getCartId(), parseInt(req.getParameter("productId"), 0), parseQuantity(req));
        } else if ("update".equals(action)) {
            int cartItemId = parseInt(req.getParameter("cartItemId"), 0);
            int quantity = parseQuantity(req);
            if (quantity <= 0) {
                cartDAO.removeItem(cartItemId);
            } else {
                cartDAO.updateItemQty(cartItemId, quantity);
            }
        } else if ("remove".equals(action)) {
            cartDAO.removeItem(parseInt(req.getParameter("cartItemId"), 0));
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private Cart resolveCart(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Cart cart;
        if (SessionUtil.isLoggedIn(session)) {
            cart = cartDAO.getCartByUserId(SessionUtil.getCurrentUserId(session));
        } else {
            cart = cartDAO.getCartBySessionId(session.getId());
        }
        session.setAttribute("cartId", cart.getCartId());
        return cart;
    }

    private Map<Integer, Product> productMap(List<CartItem> items) {
        Map<Integer, Product> products = new HashMap<>();
        for (CartItem item : items) {
            products.put(item.getProductId(), productDAO.getById(item.getProductId()));
        }
        return products;
    }

    private int parseInt(String value, int fallback) {
        try {
            return value == null || value.isBlank() ? fallback : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private int parseQuantity(HttpServletRequest req) {
        String quantity = req.getParameter("quantity");
        if (quantity == null) {
            quantity = req.getParameter("qty");
        }
        return parseInt(quantity, 1);
    }
}

