<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<header>
    <div class="header-container">
        <div class="logo">Mail Client</div>
        <nav>
            <ul>
                <c:if test="${not empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/email/inbox">Inbox</a></li>
                    <li><a href="${pageContext.request.contextPath}/email/sent">Sent</a></li>
                    <li><a href="${pageContext.request.contextPath}/email/compose">Compose</a></li>
                    <li><a href="${pageContext.request.contextPath}/user/profile">Profile</a></li>
                    <li><a href="${pageContext.request.contextPath}/auth/logout">Logout</a></li>
                </c:if>
                <c:if test="${empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/auth/login">Login</a></li>
                    <li><a href="${pageContext.request.contextPath}/auth/register">Register</a></li>
                </c:if>
            </ul>
        </nav>
    </div>
</header>

<c:if test="${not empty message}">
    <div class="container">
        <div class="alert alert-success">
                ${message}
        </div>
    </div>
</c:if>

<c:if test="${not empty error}">
    <div class="container">
        <div class="alert alert-error">
                ${error}
        </div>
    </div>
</c:if>