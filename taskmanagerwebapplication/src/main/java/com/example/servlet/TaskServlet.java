package com.example.servlet;

import com.example.dao.TaskDAO;
import com.example.model.Task;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/tasks")
public class TaskServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"not logged in\"}");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        try {
            List<Task> tasks = TaskDAO.findByUserId(userId);
            String json = gson.toJson(tasks);
            resp.getWriter().write(json);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"server error\"}");
            e.printStackTrace();
        }
    }
}
