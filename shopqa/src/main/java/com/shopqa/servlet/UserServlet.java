package com.shopqa.servlet;

import com.shopqa.dao.UserDAO;
import com.shopqa.model.User;
import com.shopqa.util.PasswordUtil;
import com.shopqa.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class UserServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("login".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        } else if ("register".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        } else if ("logout".equals(action)) {
            SessionUtil.invalidate(req.getSession(false));
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("login".equals(action)) {
            login(req, resp);
        } else if ("register".equals(action)) {
            register(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userDAO.findByEmail(req.getParameter("email"));
        if (user != null && PasswordUtil.verifyPassword(req.getParameter("password"), user.getPasswordHash())) {
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("fullName", user.getFullName());
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        req.setAttribute("error", "Invalid email or password");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        if (password == null || !password.equals(confirmPassword)) {
            req.setAttribute("error", "Passwords do not match");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        String email = req.getParameter("email");
        if (userDAO.findByEmail(email) != null) {
            req.setAttribute("error", "Email already registered");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setFullName(req.getParameter("fullName"));
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setRole("customer");
        userDAO.createUser(user);
        resp.sendRedirect(req.getContextPath() + "/user?action=login");
    }
}

