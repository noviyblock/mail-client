<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<div class="container">
    <h2>Register</h2>
    <form action="${pageContext.request.contextPath}/auth/register" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit" class="btn">Register</button>
    </form>
    <p>Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Login here</a></p>
</div>
<%@ include file="/WEB-INF/views/shared/footer.jsp" %>