
package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Invalidate the session if exists
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Redirect to login page after logout
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}
