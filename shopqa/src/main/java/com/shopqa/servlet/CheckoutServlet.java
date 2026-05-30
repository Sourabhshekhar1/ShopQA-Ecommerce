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

public class CheckoutServlet extends HttpServlet {
    private final CartDAO cartDAO = new CartDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (!SessionUtil.isLoggedIn(session)) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }
        Cart cart = cartDAO.getCartByUserId(SessionUtil.getCurrentUserId(session));
        List<CartItem> items = cartDAO.getCartItems(cart.getCartId());
        req.setAttribute("cart", cart);
        req.setAttribute("cartItems", items);
        req.setAttribute("cartProducts", productMap(items));
        req.setAttribute("cartTotal", cartDAO.getCartTotal(cart.getCartId()));
        req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
    }

    private Map<Integer, Product> productMap(List<CartItem> items) {
        Map<Integer, Product> products = new HashMap<>();
        for (CartItem item : items) {
            products.put(item.getProductId(), productDAO.getById(item.getProductId()));
        }
        return products;
    }
}

