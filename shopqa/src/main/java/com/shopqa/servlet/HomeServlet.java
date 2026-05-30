package com.shopqa.servlet;

import com.shopqa.dao.CategoryDAO;
import com.shopqa.dao.ProductDAO;
import com.shopqa.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class HomeServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productDAO.getAllActive();
        req.setAttribute("products", products.size() > 8 ? products.subList(0, 8) : products);
        req.setAttribute("categories", categoryDAO.getAll());
        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
    }
}

