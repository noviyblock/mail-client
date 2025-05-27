<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<div class="auth-container">
    <h2>Login</h2>

    <c:if test="${not empty error}">
        <div class="alert error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/auth/login" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit" class="btn">Login</button>
    </form>

    <p class="auth-link">Don't have an account?
        <a href="${pageContext.request.contextPath}/auth/register">Register here</a>
    </p>
</div>
<%@ include file="/WEB-INF/views/shared/footer.jsp" %>