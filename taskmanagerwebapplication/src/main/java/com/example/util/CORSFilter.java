
package com.example.util;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Allow your frontend origin for cross-origin requests
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        // Allow HTTP methods your frontend uses
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // Allow headers like Content-Type, Authorization etc.
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        // Allow cookies to be sent/received in cross-origin requests
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // Cache preflight response for an hour
        response.setHeader("Access-Control-Max-Age", "3600");

        // If OPTIONS method, respond with OK status immediately
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Continue with next filters
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No init needed
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
}
