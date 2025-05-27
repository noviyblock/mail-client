<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<div class="container">
    <h2>${folder eq 'inbox' ? 'Inbox' : 'Sent'}</h2>

    <table class="email-table">
        <thead>
        <tr>
            <th>${folder eq 'inbox' ? 'From' : 'To'}</th>
            <th>Subject</th>
            <th>Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${emails}" var="email">
            <tr class="${not email.read and folder eq 'inbox' ? 'email-unread' : ''}">
                <td>${folder eq 'inbox' ? email.sender : email.recipient}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/email/view?id=${email.id}">
                            ${email.subject}
                    </a>
                </td>
                <td>${email.sentDate}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/email/delete" method="post" style="display: inline;">
                        <input type="hidden" name="id" value="${email.id}">
                        <input type="hidden" name="folder" value="${folder}">
                        <button type="submit" class="btn" style="background-color: #e74c3c;">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/views/shared/footer.jsp" %>