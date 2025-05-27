package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Разрешаем доступ к статическим ресурсам и страницам авторизации
        if (path.startsWith("/auth/") || path.startsWith("/css/") || path.startsWith("/js/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (!loggedIn && !path.equals("/") && !path.equals("/index.jsp")) {
            res.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        chain.doFilter(request, response);
    }
}