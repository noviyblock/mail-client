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

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String action = req.getPathInfo();

        if (action == null || action.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/email/inbox");
            return;
        }

        switch (action) {
            case "/profile":
                req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        String action = req.getPathInfo();

        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/email/inbox");
            return;
        }

        switch (action) {
            case "/update":
                handleUpdateProfile(req, resp, currentUser);
                break;
            case "/delete":
                handleDeleteAccount(req, resp, currentUser);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleUpdateProfile(HttpServletRequest req, HttpServletResponse resp, User currentUser) throws IOException, ServletException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        currentUser.setUsername(username);
        currentUser.setEmail(email);
        if (password != null && !password.isEmpty()) {
            currentUser.setPassword(password);
        }

        if (userService.updateUserProfile(currentUser)) {
            req.getSession().setAttribute("user", currentUser);
            req.getSession().setAttribute("message", "Profile updated successfully");
            resp.sendRedirect(req.getContextPath() + "/user/profile");
        } else {
            req.setAttribute("error", "Failed to update profile");
            req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
        }
    }

    private void handleDeleteAccount(HttpServletRequest req, HttpServletResponse resp, User currentUser) throws IOException {
        if (userService.deleteUser(currentUser.getId())) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/auth/register");
        } else {
            req.getSession().setAttribute("error", "Failed to delete account");
            resp.sendRedirect(req.getContextPath() + "/user/profile");
        }
    }
}