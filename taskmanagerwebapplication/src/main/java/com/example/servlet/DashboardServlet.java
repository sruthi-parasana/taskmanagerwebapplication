
package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            // Not logged in, redirect to login page
            resp.sendRedirect(req.getContextPath() + "/login.html");
            return;
        }

        String username = (String) session.getAttribute("userId");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Simple HTML page with personalized welcome and task section placeholder
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Dashboard</title></head><body>");
        out.println("<h1>Welcome to Dashboard, " + username + "!</h1>");

        // Task placeholder - you can fetch tasks dynamically and list here
        out.println("<p>Your tasks will appear here.</p>");

        out.println("<a href='" + req.getContextPath() + "/logout'>Logout</a>");
        out.println("</body></html>");
    }
}
