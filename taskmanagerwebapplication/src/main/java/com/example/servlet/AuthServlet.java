
package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Handles registration and login: /api/register and /api/login
@WebServlet(urlPatterns = {"/api/register", "/api/login"})
public class AuthServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath(); // more robust than getRequestURI
        resp.setContentType("application/json");
        Map<String, Object> res = new HashMap<>();

        try (BufferedReader reader = req.getReader()) {
            Map<String, Object> body = gson.fromJson(reader, Map.class);

            // Basic form validation
            String username = body.get("username") != null ? body.get("username").toString().trim() : null;
            String password = body.get("password") != null ? body.get("password").toString().trim() : null;

            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.put("error", "username and password required");
                resp.getWriter().write(gson.toJson(res));
                return;
            }

            // Registration endpoint
            if ("/api/register".equals(servletPath)) {
                if (UserDAO.findByUsername(username) != null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    res.put("error", "user already exists");
                    resp.getWriter().write(gson.toJson(res));
                    return;
                }
                int userId = UserDAO.createUser(username, password);
                res.put("message", "registered");
                res.put("userId", userId);
                resp.getWriter().write(gson.toJson(res));
                return;
            }

            // Login endpoint
            if ("/api/login".equals(servletPath)) {
                User user = UserDAO.findByCredentials(username, password);
                if (user == null) {
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.put("error", "invalid credentials");
                    resp.getWriter().write(gson.toJson(res));
                    return;
                }

                // Set up session
                HttpSession session = req.getSession(true);
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());

                res.put("message", "ok");
                res.put("userId", user.getId());
                res.put("username", user.getUsername());
                resp.getWriter().write(gson.toJson(res));
                return;
            }

            // If endpoint is not recognized
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.put("error", "unknown endpoint");
            resp.getWriter().write(gson.toJson(res));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.put("error", "server error: " + e.getMessage());
            resp.getWriter().write(gson.toJson(res));
            e.printStackTrace();
        }
    }
}
