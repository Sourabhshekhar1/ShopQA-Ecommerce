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
        int cartId = resolveCartId(session);
        List<CartItem> items = cartDAO.getCartItems(cartId);
        req.setAttribute("cartItems", items);
        req.setAttribute("cartProducts", productMap(items));
        req.setAttribute("cartTotal", cartDAO.getCartTotal(cartId));
        req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
    }

    private int resolveCartId(HttpSession session) {
        Object cartId = session.getAttribute("cartId");
        if (cartId != null) {
            return cartId instanceof Integer ? (Integer) cartId : Integer.parseInt(cartId.toString());
        }
        Cart cart = cartDAO.getCartByUserId(SessionUtil.getCurrentUserId(session));
        session.setAttribute("cartId", cart.getCartId());
        return cart.getCartId();
    }

    private Map<Integer, Product> productMap(List<CartItem> items) {
        Map<Integer, Product> products = new HashMap<>();
        for (CartItem item : items) {
            products.put(item.getProductId(), productDAO.getById(item.getProductId()));
        }
        return products;
    }
}

