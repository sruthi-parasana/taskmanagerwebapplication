package com.example.dao;

import com.example.model.Task;
import com.example.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public static List<Task> findByUserId(int userId) throws Exception {
        List<Task> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM app_tasks WHERE user_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Task t = new Task();
                        t.setId(rs.getInt("id"));
                        t.setUserId(rs.getInt("user_id"));
                        t.setTitle(rs.getString("title"));
                        t.setDescription(rs.getString("description"));
                        t.setDueDate(rs.getDate("due_date"));
                        t.setStatus(rs.getString("status"));
                        list.add(t);
                    }
                }
            }
        }
        return list;
    }

    public static Task createTask(Task t) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO app_tasks (user_id, title, description, due_date, status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, t.getUserId());
                ps.setString(2, t.getTitle());
                ps.setString(3, t.getDescription());

                // Handle possible null dueDate
                if (t.getDueDate() != null) {
                    ps.setDate(4, t.getDueDate());
                } else {
                    ps.setNull(4, Types.DATE);
                }

                ps.setString(5, t.getStatus());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) t.setId(rs.getInt(1));
                }
            }
        }
        return t;
    }

    public static Task findById(int taskId, int userId) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM app_tasks WHERE id=? AND user_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, taskId);
                ps.setInt(2, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Task t = new Task();
                        t.setId(rs.getInt("id"));
                        t.setUserId(rs.getInt("user_id"));
                        t.setTitle(rs.getString("title"));
                        t.setDescription(rs.getString("description"));
                        t.setDueDate(rs.getDate("due_date"));
                        t.setStatus(rs.getString("status"));
                        return t;
                    }
                }
            }
        }
        return null;
    }

    public static boolean updateTask(Task t) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE app_tasks SET title=?, description=?, due_date=?, status=? WHERE id=? AND user_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, t.getTitle());
                ps.setString(2, t.getDescription());
                if (t.getDueDate() != null) {
                    ps.setDate(3, t.getDueDate());
                } else {
                    ps.setNull(3, Types.DATE);
                }
                ps.setString(4, t.getStatus());
                ps.setInt(5, t.getId());
                ps.setInt(6, t.getUserId());
                return ps.executeUpdate() > 0;
            }
        }
    }

    public static boolean deleteTask(int taskId, int userId) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM app_tasks WHERE id=? AND user_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, taskId);
                ps.setInt(2, userId);
                return ps.executeUpdate() > 0;
            }
        }
    }
}
