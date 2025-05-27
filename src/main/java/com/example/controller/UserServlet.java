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

public class UserServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String action = req.getPathInfo();
        if (action == null) action = "";

        try {
            switch (action) {
                case "/profile":
                    showProfile(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            handleError(req, resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        String action = req.getPathInfo();
        if (action == null) action = "";

        try {
            switch (action) {
                case "/update":
                    updateProfile(req, resp, currentUser);
                    break;
                case "/delete":
                    deleteAccount(req, resp, currentUser);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            handleError(req, resp, e);
        }
    }

    private void showProfile(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/user/edit.jsp").forward(req, resp);
    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp, User currentUser)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User updatedUser = new User(username, password, email);
        updatedUser.setId(currentUser.getId());

        try {
            // Check if username or email is changed and already taken
            if (!username.equals(currentUser.getUsername()) &&
                    userService.isUsernameTaken(username)) {
                throw new IllegalArgumentException("Username already taken");
            }

            if (!email.equals(currentUser.getEmail()) &&
                    userService.isEmailTaken(email)) {
                throw new IllegalArgumentException("Email already taken");
            }

            userService.updateUser(updatedUser);

            // Update session
            req.getSession().setAttribute("user", updatedUser);
            req.setAttribute("success", "Profile updated successfully");
            showProfile(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("user", updatedUser);
            showEditForm(req, resp);
        }
    }

    private void deleteAccount(HttpServletRequest req, HttpServletResponse resp, User user)
            throws IOException {

        userService.deleteUser(user.getId());
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/auth/register");
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, Exception e)
            throws ServletException, IOException {

        e.printStackTrace();
        req.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
        req.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(req, resp);
    }
}