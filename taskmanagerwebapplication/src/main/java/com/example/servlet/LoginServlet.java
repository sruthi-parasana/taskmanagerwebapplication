package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Hardcoded demo authentication
    private boolean authenticate(String username, String password) {
        return "alice".equals(username) && "pass123".equals(password);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println("Login attempt: username=" + username + " password=" + password);

        if (authenticate(username, password)) {
            // Successful login
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", username); // store in session

            // Redirect to dashboard (GET request)
            resp.sendRedirect(req.getContextPath() + "/dashboard.html");
        } else {
            // Login failed
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("/login.html").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Forward GET to login page
        req.getRequestDispatcher("/login.html").forward(req, resp);
    }
}
