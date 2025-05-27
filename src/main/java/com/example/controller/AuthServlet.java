package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        if (action == null || action.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        switch (action) {
            case "/login":
                req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
                break;
            case "/register":
                req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
                break;
            case "/logout":
                HttpSession session = req.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                resp.sendRedirect(req.getContextPath() + "/auth/login");
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        switch (action) {
            case "/login":
                handleLogin(req, resp);
                break;
            case "/register":
                handleRegister(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Optional<User> userOpt = userService.authenticate(username, password);

        if (userOpt.isPresent()) {
            HttpSession session = req.getSession();
            session.setAttribute("user", userOpt.get());
            resp.sendRedirect(req.getContextPath() + "/email/inbox");
        } else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        if (userService.registerUser(user)) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        } else {
            req.setAttribute("error", "Username or email already exists");
            req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
        }
    }
}