
package com.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    // You can exclude some paths like login.html, register.html, static resources, etc.
    private static final String[] excludedUrls = {
        "/login.html",
        "/register.html",
        "/login",        // login servlet path
        "/api/",         // if you have public APIs
        "/css/",         // static resources example
        "/js/",
        "/images/"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Allow excluded URLs without login check
        for (String exclude : excludedUrls) {
            if (path.startsWith(exclude)) {
                chain.doFilter(request, response);
                return;
            }
        }

        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("userId") != null);

        if (loggedIn) {
            // User logged in, continue to requested resource
            chain.doFilter(request, response);
        } else {
            // User not logged in, redirect to login page
            res.sendRedirect(req.getContextPath() + "/login.html");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) { 
        // Optional initialization code
    }

    @Override
    public void destroy() { 
        // Optional cleanup code
    }
}
