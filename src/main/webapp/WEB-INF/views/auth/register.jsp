<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<c:set var="pageTitle" value="Register" />

<div class="auth-form">
    <h2>Register</h2>

    <c:if test="${not empty error}">
        <div class="alert error">${error}</div>
    </c:if>

    <c:if test="${not empty success}">
        <div class="alert success">
                ${success} <a href="${pageContext.request.contextPath}/auth/login">Login now</a>
        </div>
    </c:if>

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

    <p class="auth-link">Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Login here</a></p>
</div>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>