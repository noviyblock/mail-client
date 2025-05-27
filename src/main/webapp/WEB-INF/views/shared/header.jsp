<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mail Client - <c:out value="${pageTitle}"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        header {
            background: #2c3e50;
            color: white;
            padding: 15px 0;
            position: sticky;
            top: 0;
            z-index: 100;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .header-container {
            width: 90%;
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        nav ul {
            display: flex;
            list-style: none;
            margin: 0;
            padding: 0;
        }
        nav li {
            margin-left: 20px;
        }
        nav a {
            color: white;
            text-decoration: none;
            font-weight: 500;
        }
    </style>
</head>
<body>
<header>
    <div class="header-container">
        <h1>Mail Client</h1>
        <nav>
            <ul>
                <c:if test="${not empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/email/inbox">Inbox</a></li>
                    <li><a href="${pageContext.request.contextPath}/email/compose">Compose</a></li>
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
<main class="container">