<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<div class="container">
    <h2>User Profile</h2>

    <div class="profile-container">
        <div class="profile-info">
            <h3>Account Information</h3>
            <form action="${pageContext.request.contextPath}/user/update" method="post">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" value="${user.username}" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${user.email}" required>
                </div>
                <div class="form-group">
                    <label for="password">New Password (leave blank to keep current):</label>
                    <input type="password" id="password" name="password">
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Confirm New Password:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword">
                </div>
                <button type="submit" class="btn">Update Profile</button>
            </form>
        </div>

        <div class="profile-actions">
            <h3>Account Actions</h3>
            <form action="${pageContext.request.contextPath}/user/delete" method="post"
                  onsubmit="return confirm('Are you sure you want to delete your account? This cannot be undone!');">
                <button type="submit" class="btn btn-danger">Delete Account</button>
            </form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/shared/footer.jsp" %>