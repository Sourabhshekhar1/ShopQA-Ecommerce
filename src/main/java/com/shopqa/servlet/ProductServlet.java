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

public class ProductServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isBlank()) {
            Product product = productDAO.getById(Integer.parseInt(id));
            if (product == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("product", product);
            req.getRequestDispatcher("/WEB-INF/views/productDetail.jsp").forward(req, resp);
            return;
        }

        String categoryId = req.getParameter("categoryId");
        String search = req.getParameter("search");
        List<Product> products;
        if (categoryId != null && !categoryId.isBlank()) {
            products = productDAO.getByCategory(Integer.parseInt(categoryId));
            req.setAttribute("selectedCategoryId", Integer.parseInt(categoryId));
        } else if (search != null && !search.isBlank()) {
            products = productDAO.search(search);
            req.setAttribute("searchTerm", search);
            req.setAttribute("search", search);
        } else {
            products = productDAO.getAllActive();
        }
        req.setAttribute("products", products);
        req.setAttribute("categories", categoryDAO.getAll());
        req.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(req, resp);
    }
}

