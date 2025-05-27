<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mail Client</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1>Welcome to Mail Client</h1>
    <div class="actions">
        <a href="${pageContext.request.contextPath}/auth/login" class="btn">Login</a>
        <a href="${pageContext.request.contextPath}/auth/register" class="btn">Register</a>
    </div>
</div>
</body>
</html>